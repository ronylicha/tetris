package com.tetris.modern.rl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tetris.modern.rl.auth.AuthManager
import com.tetris.modern.rl.auth.SyncStrategy
import com.tetris.modern.rl.data.entities.ScoreEntity
import com.tetris.modern.rl.data.repository.ScoreRepository
import com.tetris.modern.rl.sync.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class EnhancedLeaderboardViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository,
    private val authManager: AuthManager,
    private val syncManager: SyncManager
) : ViewModel() {
    
    data class LeaderboardUiState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val selectedMode: String? = null,
        val selectedPeriod: ScoreRepository.TimePeriod = ScoreRepository.TimePeriod.ALL_TIME,
        val searchQuery: String = "",
        val top10Scores: List<ScoreEntity> = emptyList(),
        val playerStats: PlayerStatsUi? = null
    )
    
    data class PlayerStatsUi(
        val rank: Int,
        val gamesPlayed: Int,
        val averageScore: Double,
        val bestScore: Int,
        val totalLines: Int
    )
    
    private val _uiState = MutableStateFlow(LeaderboardUiState())
    val uiState: StateFlow<LeaderboardUiState> = _uiState.asStateFlow()
    
    val authState: StateFlow<AuthManager.AuthState> = authManager.authState
    val syncState: StateFlow<SyncManager.SyncState> = syncManager.syncState
    
    private val _searchQuery = MutableStateFlow("")
    private val _selectedMode = MutableStateFlow<String?>(null)
    private val _selectedPeriod = MutableStateFlow(ScoreRepository.TimePeriod.ALL_TIME)
    
    // Paged scores with reactive updates
    val pagedScores: Flow<PagingData<ScoreEntity>> = combine(
        _searchQuery.debounce(300),
        _selectedMode,
        _selectedPeriod
    ) { query, mode, _ ->
        Pair(mode, query.takeIf { it.isNotEmpty() })
    }.flatMapLatest { (mode, query) ->
        scoreRepository.getScoresPaged(mode, query)
    }.cachedIn(viewModelScope)
    
    init {
        loadTop10()
        observeFilters()
        loadPlayerStats()
    }
    
    private fun observeFilters() {
        viewModelScope.launch {
            combine(
                _selectedMode,
                _selectedPeriod
            ) { mode, period ->
                Pair(mode, period)
            }.collect { (mode, period) ->
                _uiState.update { 
                    it.copy(
                        selectedMode = mode,
                        selectedPeriod = period
                    )
                }
                loadTop10()
            }
        }
        
        viewModelScope.launch {
            _searchQuery.collect { query ->
                _uiState.update { it.copy(searchQuery = query) }
            }
        }
    }
    
    private fun loadTop10() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                scoreRepository.getTop10(_selectedMode.value).collect { scores ->
                    _uiState.update { 
                        it.copy(
                            top10Scores = scores,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Failed to load leaderboard: ${e.message}"
                    )
                }
            }
        }
    }
    
    private fun loadPlayerStats() {
        viewModelScope.launch {
            val currentUser = (authState.value as? AuthManager.AuthState.Authenticated)
            currentUser?.let { user ->
                try {
                    val stats = scoreRepository.getPlayerStats(
                        user.displayName,
                        _selectedMode.value
                    )
                    
                    stats?.let {
                        val bestScore = scoreRepository.getPlayerBestScores(user.displayName)
                            .first()
                            .maxByOrNull { it.score }
                        
                        val rank = bestScore?.let { 
                            scoreRepository.getPlayerStats(user.displayName, _selectedMode.value)
                                ?.highestScore?.let { score ->
                                    // This would need a proper rank query
                                    0 // Placeholder
                                }
                        } ?: 0
                        
                        _uiState.update { state ->
                            state.copy(
                                playerStats = PlayerStatsUi(
                                    rank = rank,
                                    gamesPlayed = it.gamesPlayed,
                                    averageScore = it.averageScore,
                                    bestScore = it.highestScore,
                                    totalLines = it.totalLines
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    // Silent fail for stats
                }
            }
        }
    }
    
    fun updateSearch(query: String) {
        _searchQuery.value = query
    }
    
    fun selectMode(mode: String?) {
        _selectedMode.value = mode
    }
    
    fun selectPeriod(period: ScoreRepository.TimePeriod) {
        _selectedPeriod.value = period
        
        // Reload scores for the new period
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                scoreRepository.getScoresByPeriod(
                    _selectedMode.value,
                    period,
                    100
                ).collect { scores ->
                    _uiState.update { 
                        it.copy(
                            top10Scores = scores.take(10),
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Failed to load scores: ${e.message}"
                    )
                }
            }
        }
    }
    
    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                // Refresh from server
                scoreRepository.refreshLeaderboard(_selectedMode.value)
                
                // Reload local data
                loadTop10()
                loadPlayerStats()
                
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Refresh failed: ${e.message}"
                    )
                }
            }
        }
    }
    
    fun syncScores() {
        viewModelScope.launch {
            syncManager.performSync(authManager.syncPreference.value)
        }
    }
    
    fun resolveSyncConflict(strategy: SyncStrategy) {
        viewModelScope.launch {
            syncManager.resolveSyncConflict(strategy)
            // Reload data after sync
            loadTop10()
            loadPlayerStats()
        }
    }
}