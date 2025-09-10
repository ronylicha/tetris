package com.tetris.modern.rl.data.repository

import com.tetris.modern.rl.data.dao.ScoreDao
import com.tetris.modern.rl.data.entities.ScoreEntity
import com.tetris.modern.rl.network.ApiService
import com.tetris.modern.rl.network.NoConnectivityException
import com.tetris.modern.rl.network.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreRepository @Inject constructor(
    private val scoreDao: ScoreDao,
    private val apiService: ApiService
) {
    
    /**
     * Get top scores with offline fallback
     */
    fun getTopScores(gameMode: String? = null, limit: Int = 100): Flow<List<ScoreEntity>> {
        return if (gameMode != null) {
            scoreDao.getTopScoresByMode(gameMode, limit)
        } else {
            scoreDao.getTopScores(limit)
        }
    }
    
    /**
     * Submit score to server and save locally
     */
    suspend fun saveScore(score: ScoreEntity): Result<Long> {
        return withContext(Dispatchers.IO) {
            try {
                // Save locally first
                val localId = scoreDao.insertScore(score)
                
                // Try to submit to server
                try {
                    val request = ScoreSubmitRequest(
                        playerId = score.playerName, // Use player ID from auth
                        playerName = score.playerName,
                        score = score.score,
                        lines = score.lines,
                        level = score.level,
                        gameMode = score.gameMode,
                        duration = score.duration
                    )
                    
                    val response = apiService.submitScore(request)
                    
                    if (response.isSuccessful && response.body()?.success == true) {
                        // Update local score with server ID
                        response.body()?.scoreId?.let { serverId ->
                            scoreDao.markAsSynced(listOf(localId))
                        }
                        
                        Timber.d("Score submitted successfully. Rank: ${response.body()?.rank}")
                    }
                } catch (e: NoConnectivityException) {
                    Timber.w("No connection. Score saved locally for later sync")
                } catch (e: Exception) {
                    Timber.e(e, "Failed to submit score to server")
                }
                
                Result.success(localId)
            } catch (e: Exception) {
                Timber.e(e, "Failed to save score")
                Result.failure(e)
            }
        }
    }
    
    /**
     * Sync unsynced scores with server
     */
    suspend fun syncScores() {
        withContext(Dispatchers.IO) {
            try {
                val unsyncedScores = scoreDao.getUnsyncedScores()
                
                unsyncedScores.forEach { score ->
                    try {
                        val request = ScoreSubmitRequest(
                            playerId = score.playerName,
                            playerName = score.playerName,
                            score = score.score,
                            lines = score.lines,
                            level = score.level,
                            gameMode = score.gameMode,
                            duration = score.duration
                        )
                        
                        val response = apiService.submitScore(request)
                        
                        if (response.isSuccessful && response.body()?.success == true) {
                            response.body()?.scoreId?.let { serverId ->
                                scoreDao.markAsSynced(listOf(score.id))
                            }
                        }
                    } catch (e: Exception) {
                        Timber.e(e, "Failed to sync score ${score.id}")
                    }
                }
                
                Timber.d("Synced ${unsyncedScores.size} scores")
            } catch (e: Exception) {
                Timber.e(e, "Failed to sync scores")
            }
        }
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
}