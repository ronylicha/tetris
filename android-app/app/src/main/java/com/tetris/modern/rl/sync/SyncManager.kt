package com.tetris.modern.rl.sync

import android.content.Context
import androidx.work.*
import com.tetris.modern.rl.auth.AuthManager
import com.tetris.modern.rl.auth.SyncStrategy
import com.tetris.modern.rl.data.dao.ScoreDao
import com.tetris.modern.rl.data.entities.ScoreEntity
import com.tetris.modern.rl.network.ApiService
import com.tetris.modern.rl.network.models.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
    private val scoreDao: ScoreDao,
    private val authManager: AuthManager
) {
    
    sealed class SyncState {
        object Idle : SyncState()
        object Syncing : SyncState()
        data class Success(val message: String) : SyncState()
        data class Error(val message: String) : SyncState()
        data class Conflict(
            val localData: SyncData,
            val remoteData: SyncData
        ) : SyncState()
    }
    
    data class SyncData(
        val scores: List<ScoreEntity>,
        val totalScore: Long,
        val gamesPlayed: Int,
        val highestScore: Int,
        val totalLines: Int,
        val lastSyncTime: Long
    )
    
    private val _syncState = MutableStateFlow<SyncState>(SyncState.Idle)
    val syncState: StateFlow<SyncState> = _syncState.asStateFlow()
    
    private val _lastSyncTime = MutableStateFlow(0L)
    val lastSyncTime: StateFlow<Long> = _lastSyncTime.asStateFlow()
    
    init {
        schedulePeriodicSync()
    }
    
    private fun schedulePeriodicSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            15, TimeUnit.MINUTES // Sync every 15 minutes
        )
            .setConstraints(constraints)
            .addTag("periodic_sync")
            .build()
        
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "tetris_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }
    
    suspend fun performSync(strategy: SyncStrategy = SyncStrategy.MERGE_BOTH): Result<SyncState> {
        val userId = authManager.getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        return try {
            _syncState.value = SyncState.Syncing
            
            // Get local data
            val localScores = scoreDao.getAllScores().first()
            val localData = SyncData(
                scores = localScores,
                totalScore = localScores.sumOf { it.score.toLong() },
                gamesPlayed = localScores.size,
                highestScore = localScores.maxOfOrNull { it.score } ?: 0,
                totalLines = localScores.sumOf { it.lines },
                lastSyncTime = _lastSyncTime.value
            )
            
            // Get remote data
            val remoteResponse = apiService.syncData(
                token = "Bearer ${authManager.authState.value}",
                syncRequest = SyncDataRequest(
                    playerId = userId,
                    lastSyncTime = _lastSyncTime.value
                )
            )
            
            if (!remoteResponse.isSuccessful || remoteResponse.body() == null) {
                val error = SyncState.Error("Failed to fetch remote data")
                _syncState.value = error
                return Result.failure(Exception("Sync failed"))
            }
            
            val remoteData = remoteResponse.body()!!.let { response ->
                SyncData(
                    scores = response.scores.map { it.toEntity() },
                    totalScore = response.totalScore,
                    gamesPlayed = response.gamesPlayed,
                    highestScore = response.highestScore,
                    totalLines = response.totalLines,
                    lastSyncTime = response.lastSyncTime
                )
            }
            
            // Check for conflicts
            if (hasConflict(localData, remoteData)) {
                when (strategy) {
                    SyncStrategy.KEEP_LOCAL -> uploadLocalData(localData)
                    SyncStrategy.KEEP_REMOTE -> downloadRemoteData(remoteData)
                    SyncStrategy.MERGE_BOTH -> mergeData(localData, remoteData)
                }
            } else {
                // No conflict, just sync unsynced data
                syncUnsyncedScores()
            }
            
            _lastSyncTime.value = System.currentTimeMillis()
            val success = SyncState.Success("Sync completed successfully")
            _syncState.value = success
            Result.success(success)
            
        } catch (e: Exception) {
            val error = SyncState.Error("Sync error: ${e.message}")
            _syncState.value = error
            Result.failure(e)
        }
    }
    
    private fun hasConflict(local: SyncData, remote: SyncData): Boolean {
        // Check if there are differences that need resolution
        return local.lastSyncTime > 0 && remote.lastSyncTime > 0 &&
               local.lastSyncTime != remote.lastSyncTime &&
               (local.highestScore != remote.highestScore || 
                local.gamesPlayed != remote.gamesPlayed)
    }
    
    private suspend fun uploadLocalData(localData: SyncData) {
        val userId = authManager.getCurrentUserId() ?: return
        
        // Upload all local scores to server
        localData.scores.forEach { score ->
            try {
                apiService.submitScore(
                    ScoreSubmitRequest(
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
                )
                
                // Mark as synced
                scoreDao.markAsSynced(score.id)
                
            } catch (e: Exception) {
                // Continue with other scores even if one fails
            }
        }
    }
    
    private suspend fun downloadRemoteData(remoteData: SyncData) {
        // Clear local and replace with remote
        scoreDao.deleteAllScores()
        
        // Insert remote scores
        remoteData.scores.forEach { score ->
            scoreDao.insertScore(score.copy(isSynced = true))
        }
    }
    
    private suspend fun mergeData(localData: SyncData, remoteData: SyncData) {
        // Intelligent merge strategy
        val mergedScores = mutableListOf<ScoreEntity>()
        
        // Group scores by game mode
        val localByMode = localData.scores.groupBy { it.gameMode }
        val remoteByMode = remoteData.scores.groupBy { it.gameMode }
        
        val allModes = (localByMode.keys + remoteByMode.keys).distinct()
        
        for (mode in allModes) {
            val localModeScores = localByMode[mode] ?: emptyList()
            val remoteModeScores = remoteByMode[mode] ?: emptyList()
            
            // Keep best score for each mode
            val bestLocal = localModeScores.maxByOrNull { it.score }
            val bestRemote = remoteModeScores.maxByOrNull { it.score }
            
            when {
                bestLocal == null -> mergedScores.addAll(remoteModeScores)
                bestRemote == null -> mergedScores.addAll(localModeScores)
                else -> {
                    // Keep the better score
                    if (bestLocal.score >= bestRemote.score) {
                        mergedScores.addAll(localModeScores)
                        // Also keep unique remote scores
                        val uniqueRemote = remoteModeScores.filter { remote ->
                            localModeScores.none { local ->
                                local.score == remote.score && 
                                local.date == remote.date
                            }
                        }
                        mergedScores.addAll(uniqueRemote)
                    } else {
                        mergedScores.addAll(remoteModeScores)
                        // Also keep unique local scores
                        val uniqueLocal = localModeScores.filter { local ->
                            remoteModeScores.none { remote ->
                                remote.score == local.score && 
                                remote.date == local.date
                            }
                        }
                        mergedScores.addAll(uniqueLocal)
                    }
                }
            }
        }
        
        // Clear and insert merged data
        scoreDao.deleteAllScores()
        mergedScores.forEach { score ->
            scoreDao.insertScore(score)
        }
        
        // Upload merged data to server
        uploadLocalData(SyncData(
            scores = mergedScores,
            totalScore = mergedScores.sumOf { it.score.toLong() },
            gamesPlayed = mergedScores.size,
            highestScore = mergedScores.maxOfOrNull { it.score } ?: 0,
            totalLines = mergedScores.sumOf { it.lines },
            lastSyncTime = System.currentTimeMillis()
        ))
    }
    
    private suspend fun syncUnsyncedScores() {
        val userId = authManager.getCurrentUserId() ?: return
        val unsyncedScores = scoreDao.getUnsyncedScores().first()
        
        unsyncedScores.forEach { score ->
            try {
                val response = apiService.submitScore(
                    ScoreSubmitRequest(
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
                )
                
                if (response.isSuccessful) {
                    scoreDao.markAsSynced(score.id)
                }
            } catch (e: Exception) {
                // Continue with other scores
            }
        }
    }
    
    fun showSyncConflictDialog(
        localData: SyncData,
        remoteData: SyncData,
        onStrategySelected: (SyncStrategy) -> Unit
    ) {
        _syncState.value = SyncState.Conflict(localData, remoteData)
        // The UI will handle showing the dialog based on this state
    }
    
    suspend fun resolveSyncConflict(strategy: SyncStrategy) {
        val conflictState = _syncState.value as? SyncState.Conflict ?: return
        
        when (strategy) {
            SyncStrategy.KEEP_LOCAL -> uploadLocalData(conflictState.localData)
            SyncStrategy.KEEP_REMOTE -> downloadRemoteData(conflictState.remoteData)
            SyncStrategy.MERGE_BOTH -> mergeData(conflictState.localData, conflictState.remoteData)
        }
        
        _syncState.value = SyncState.Success("Conflict resolved")
    }
    
    fun clearSyncState() {
        _syncState.value = SyncState.Idle
    }
}

// Extension function to convert API response to entity
private fun LeaderboardEntry.toEntity(): ScoreEntity {
    return ScoreEntity(
        id = 0, // Will be auto-generated
        playerName = this.playerName,
        score = this.score,
        level = this.level,
        lines = this.lines,
        gameMode = this.gameMode,
        date = java.util.Date(this.date),
        duration = this.duration,
        isSynced = true,
        timestamp = System.currentTimeMillis()
    )
}