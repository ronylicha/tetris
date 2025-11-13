package com.tetris.modern.rl.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tetris.modern.rl.auth.AuthManager
import com.tetris.modern.rl.data.dao.PlayerStats
import com.tetris.modern.rl.data.dao.ScoreDao
import com.tetris.modern.rl.data.entities.ScoreEntity
import com.tetris.modern.rl.network.ApiService
import com.tetris.modern.rl.network.NoConnectivityException
import com.tetris.modern.rl.network.models.*
import com.tetris.modern.rl.sync.SyncManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreRepository @Inject constructor(
    private val scoreDao: ScoreDao,
    private val apiService: ApiService,
    private val authManager: AuthManager,
    private val syncManager: SyncManager
) {
    
    enum class TimePeriod {
        DAY, WEEK, MONTH, ALL_TIME
    }
    
    /**
     * Get top scores with filtering, pagination, and offline fallback
     */
    fun getTopScores(
        gameMode: String? = null,
        limit: Int = 100,
        offset: Int = 0
    ): Flow<List<ScoreEntity>> {
        return scoreDao.getTopScores(gameMode, limit, offset)
    }
    
    /**
     * Get paged scores for infinite scroll
     */
    fun getScoresPaged(
        gameMode: String? = null,
        searchQuery: String? = null
    ): Flow<PagingData<ScoreEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                scoreDao.getScoresPaged(gameMode, searchQuery)
            }
        ).flow
    }
    
    /**
     * Get scores by time period
     */
    fun getScoresByPeriod(
        gameMode: String? = null,
        period: TimePeriod,
        limit: Int = 100
    ): Flow<List<ScoreEntity>> {
        val calendar = Calendar.getInstance()
        
        val startDate = when (period) {
            TimePeriod.DAY -> {
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                calendar.time
            }
            TimePeriod.WEEK -> {
                calendar.add(Calendar.WEEK_OF_YEAR, -1)
                calendar.time
            }
            TimePeriod.MONTH -> {
                calendar.add(Calendar.MONTH, -1)
                calendar.time
            }
            TimePeriod.ALL_TIME -> Date(0)
        }
        
        return scoreDao.getScoresByPeriod(gameMode, startDate, limit)
    }
    
    /**
     * Get top 10 for quick display
     */
    fun getTop10(gameMode: String? = null): Flow<List<ScoreEntity>> {
        return scoreDao.getTop10(gameMode)
    }
    
    /**
     * Submit score to server and save locally with record detection
     */
    suspend fun saveScore(score: ScoreEntity): Result<ScoreSubmitResult> {
        return withContext(Dispatchers.IO) {
            try {
                // Check if it's a personal best
                val isPersonalBest = scoreDao.isPersonalBest(
                    score.playerName,
                    score.gameMode,
                    score.score
                )
                
                // Save locally first
                val localId = scoreDao.insertScore(score)
                
                // Get player rank
                val rank = scoreDao.getPlayerRank(score.score, score.gameMode)
                
                // Try to submit to server if authenticated
                var serverRank: Int? = null
                var isGlobalRecord = false
                
                if (authManager.isAuthenticated()) {
                    try {
                        val userId = authManager.getCurrentUserId() ?: score.playerName
                        
                        val request = ScoreSubmitRequest(
                            playerId = userId,
                            playerName = score.playerName,
                            score = score.score,
                            lines = score.lines,
                            level = score.level,
                            gameMode = score.gameMode,
                            duration = score.duration,
                            date = score.date.time,
                            modeData = null
                        )
                        
                        val response = apiService.submitScore(request)
                        
                        if (response.isSuccessful && response.body()?.success == true) {
                            scoreDao.markAsSynced(localId)
                            serverRank = response.body()?.rank
                            isGlobalRecord = response.body()?.isNewRecord ?: false
                            
                            Timber.d("Score submitted. Rank: $serverRank, Record: $isGlobalRecord")
                        }
                    } catch (e: NoConnectivityException) {
                        Timber.w("No connection. Score saved locally for later sync")
                    } catch (e: Exception) {
                        Timber.e(e, "Failed to submit score to server")
                    }
                }
                
                Result.success(
                    ScoreSubmitResult(
                        localId = localId,
                        localRank = rank,
                        serverRank = serverRank,
                        isPersonalBest = isPersonalBest,
                        isGlobalRecord = isGlobalRecord
                    )
                )
            } catch (e: Exception) {
                Timber.e(e, "Failed to save score")
                Result.failure(e)
            }
        }
    }
    
    /**
     * Sync scores with server using SyncManager
     */
    suspend fun syncScores() {
        syncManager.performSync(authManager.syncPreference.value)
    }
    
    /**
     * Search players by name
     */
    suspend fun searchPlayers(query: String): List<String> {
        return withContext(Dispatchers.IO) {
            scoreDao.searchPlayers(query)
        }
    }
    
    /**
     * Get player statistics
     */
    suspend fun getPlayerStats(
        playerName: String,
        gameMode: String? = null
    ): PlayerStats? {
        return withContext(Dispatchers.IO) {
            try {
                scoreDao.getPlayerStats(playerName, gameMode)
            } catch (e: Exception) {
                Timber.e(e, "Failed to get player stats")
                null
            }
        }
    }
    
    /**
     * Get player's best scores per mode
     */
    fun getPlayerBestScores(playerName: String): Flow<List<ScoreEntity>> {
        return scoreDao.getPlayerBestScores(playerName)
    }
    
    /**
     * Fetch leaderboard from server
     */
    suspend fun fetchLeaderboard(gameMode: String? = null): Result<List<LeaderboardEntry>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getLeaderboard(mode = gameMode)
                
                if (response.isSuccessful && response.body()?.success == true) {
                    Result.success(response.body()?.leaderboard ?: emptyList())
                } else {
                    Result.failure(Exception("Failed to fetch leaderboard"))
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to fetch leaderboard")
                Result.failure(e)
            }
        }
    }
    
    /**
     * Get player's personal scores
     */
    fun getPlayerScores(playerName: String): Flow<List<ScoreEntity>> {
        return scoreDao.getPlayerScores(playerName)
    }
    
    /**
     * Clean up old scores (older than 30 days)
     */
    suspend fun cleanOldScores() {
        withContext(Dispatchers.IO) {
            val thirtyDaysAgo = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L)
            scoreDao.deleteOldScores(thirtyDaysAgo)
        }
    }
    
    /**
     * Get unsynced scores from local database
     */
    suspend fun getUnsyncedScores(): List<ScoreEntity> {
        return withContext(Dispatchers.IO) {
            try {
                scoreDao.getUnsyncedScores().first()
            } catch (e: Exception) {
                Timber.e(e, "Failed to get unsynced scores")
                emptyList()
            }
        }
    }
    
    /**
     * Force refresh leaderboard from server
     */
    suspend fun refreshLeaderboard(gameMode: String? = null) {
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getLeaderboard(
                    mode = gameMode,
                    limit = 100,
                    offset = 0
                )
                
                if (response.isSuccessful && response.body()?.success == true) {
                    // Cache server leaderboard locally
                    val entries = response.body()?.leaderboard ?: emptyList()
                    Timber.d("Refreshed leaderboard: ${entries.size} entries from API")
                    
                    // Clear old synced scores to avoid duplicates
                    // Keep only unsynced local scores
                    scoreDao.deleteAllSyncedScores()
                    
                    // Convert API entries to local ScoreEntity and save them
                    entries.forEach { entry ->
                        try {
                            // Parse date from string format "yyyy-MM-dd HH:mm:ss"
                            val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
                            val parsedDate = try {
                                dateFormat.parse(entry.date) ?: java.util.Date()
                            } catch (e: Exception) {
                                java.util.Date()
                            }
                            
                            val scoreEntity = ScoreEntity(
                                id = 0, // Auto-generate
                                playerName = entry.playerName,
                                score = entry.score,
                                lines = entry.lines,
                                level = entry.level,
                                gameMode = entry.gameMode,
                                duration = entry.duration,
                                date = parsedDate,
                                isSynced = true
                            )
                            scoreDao.insertScore(scoreEntity)
                        } catch (e: Exception) {
                            Timber.e(e, "Failed to save leaderboard entry locally: ${entry.playerName}")
                        }
                    }
                    
                    Timber.d("Successfully synced ${entries.size} scores from API")
                } else {
                    Timber.e("Failed to refresh leaderboard: ${response.body()?.message}")
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to refresh leaderboard")
            }
        }
    }
}

data class ScoreSubmitResult(
    val localId: Long,
    val localRank: Int,
    val serverRank: Int?,
    val isPersonalBest: Boolean,
    val isGlobalRecord: Boolean
)