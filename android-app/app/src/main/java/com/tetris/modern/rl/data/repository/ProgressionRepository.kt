package com.tetris.modern.rl.data.repository

import android.content.Context
import com.google.gson.Gson
import com.tetris.modern.rl.auth.AuthManager
import com.tetris.modern.rl.data.dao.ProgressionDao
import com.tetris.modern.rl.data.dao.ScoreDao
import com.tetris.modern.rl.data.entities.ProgressionEntity
import com.tetris.modern.rl.network.ApiService
import com.tetris.modern.rl.network.models.*
import com.tetris.modern.rl.progression.PlayerProgression
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressionRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val progressionDao: ProgressionDao,
    private val scoreDao: ScoreDao,
    private val apiService: ApiService,
    private val authManager: AuthManager,
    private val playerProgression: PlayerProgression
) {
    private val gson = Gson()
    
    /**
     * Get current player progression
     */
    suspend fun getProgression(): ProgressionEntity? {
        return withContext(Dispatchers.IO) {
            progressionDao.getProgression().firstOrNull()
        }
    }
    
    /**
     * Save current progression state to database (without adding XP)
     */
    suspend fun saveProgression() {
        withContext(Dispatchers.IO) {
            val currentState = playerProgression.progressionState.value
            val entity = ProgressionEntity(
                currentLevel = currentState.level,
                currentXp = currentState.currentXP,
                totalXp = currentState.totalXP,
                currentRank = currentState.rank.displayName,
                unlockedThemes = gson.toJson(currentState.unlockedThemes),
                unlockedMusic = gson.toJson(currentState.unlockedMusic),
                unlockedPieceStyles = gson.toJson(currentState.unlockedPieceStyles),
                unlockedEffects = gson.toJson(currentState.unlockedEffects),
                statistics = gson.toJson(currentState.statistics)
            )
            progressionDao.updateProgression(entity)
            Timber.d("Progression saved to database")
        }
    }
    
    /**
     * Update progression locally and sync with server
     */
    suspend fun updateProgression(
        xpGained: Int,
        source: String,
        gameData: Map<String, Any>? = null
    ): Result<ProgressionUpdateResponse> {
        return withContext(Dispatchers.IO) {
            try {
                // Update local progression first
                playerProgression.addXP(xpGained, source)
                
                // Save to local database
                val currentState = playerProgression.progressionState.value
                val entity = ProgressionEntity(
                    currentLevel = currentState.level,
                    currentXp = currentState.currentXP,
                    totalXp = currentState.totalXP,
                    currentRank = currentState.rank.displayName,
                    unlockedThemes = gson.toJson(currentState.unlockedThemes),
                    unlockedMusic = gson.toJson(currentState.unlockedMusic),
                    unlockedPieceStyles = gson.toJson(currentState.unlockedPieceStyles),
                    unlockedEffects = gson.toJson(currentState.unlockedEffects),
                    statistics = gson.toJson(currentState.statistics)
                )
                progressionDao.updateProgression(entity)
                
                // Try to sync with server
                try {
                    val playerId = authManager.getCurrentUserId() ?: "guest_${android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID)}"
                    val request = ProgressionUpdateRequest(
                        playerId = playerId,
                        xpEarned = xpGained,
                        source = source
                    )
                    
                    val response = apiService.updateProgression(request)
                    
                    if (response.isSuccessful && response.body() != null) {
                        Timber.d("Progression synced. Level: ${response.body()?.newLevel}")
                        Result.success(response.body()!!)
                    } else {
                        // Server sync failed, but local update succeeded
                        Result.success(
                            ProgressionUpdateResponse(
                                success = true,
                                newLevel = currentState.level,
                                newXp = currentState.currentXP,
                                levelUp = false,
                                newUnlocks = null,
                                message = null
                            )
                        )
                    }
                } catch (e: Exception) {
                    Timber.w("Failed to sync progression with server: ${e.message}")
                    // Return success with local data
                    Result.success(
                        ProgressionUpdateResponse(
                            success = true,
                            newLevel = currentState.level,
                            newXp = currentState.currentXP,
                            levelUp = false,
                            newUnlocks = null,
                            message = null
                        )
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to update progression")
                Result.failure(e)
            }
        }
    }
    
    /**
     * Sync progression with server
     */
    suspend fun syncProgression() {
        withContext(Dispatchers.IO) {
            try {
                val playerId = authManager.getCurrentUserId() ?: return@withContext
                
                // Get local state
                val currentState = playerProgression.progressionState.value
                val localProgression = progressionDao.getProgression().firstOrNull()
                
                // Get server progression first to check if we need to merge
                val getResponse = apiService.getProgression(playerId)
                
                if (getResponse.isSuccessful && getResponse.body()?.success == true) {
                    val serverData = getResponse.body()!!
                    
                    // Decide sync strategy based on XP comparison
                    if (serverData.totalXp > currentState.totalXP) {
                        // Server has more progress, update local
                        Timber.d("Server has more XP (${serverData.totalXp} vs ${currentState.totalXP}), updating local")
                        
                        val updatedEntity = ProgressionEntity(
                            currentLevel = serverData.level,
                            currentXp = serverData.currentXp,
                            totalXp = serverData.totalXp,
                            currentRank = serverData.rank,
                            unlockedThemes = gson.toJson(serverData.unlocks?.get("themes") ?: listOf("default")),
                            unlockedMusic = gson.toJson(serverData.unlocks?.get("music") ?: listOf("classic")),
                            unlockedPieceStyles = gson.toJson(serverData.unlocks?.get("piece_styles") ?: listOf("neon")),
                            unlockedEffects = gson.toJson(serverData.unlocks?.get("effects") ?: listOf("basic")),
                            statistics = gson.toJson(serverData.statistics ?: mapOf<String, Any>())
                        )
                        progressionDao.updateProgression(updatedEntity)
                        
                        // Update in-memory state
                        // Note: This is a simplified update, you might want to properly merge the data
                        playerProgression.reset()
                        playerProgression.addXP(serverData.totalXp, "sync")
                        
                    } else if (currentState.totalXP > serverData.totalXp) {
                        // Local has more progress, push to server
                        Timber.d("Local has more XP (${currentState.totalXP} vs ${serverData.totalXp}), pushing to server")
                        
                        pushProgressionToServer()
                    } else {
                        Timber.d("Progression is already in sync")
                    }
                } else {
                    // Server doesn't have data or error, push local data
                    Timber.d("No server data found, pushing local progression")
                    pushProgressionToServer()
                }
                
            } catch (e: Exception) {
                Timber.e(e, "Failed to sync progression")
            }
        }
    }
    
    /**
     * Push local progression to server
     */
    private suspend fun pushProgressionToServer() {
        try {
            val playerId = authManager.getCurrentUserId() ?: return
            val currentState = playerProgression.progressionState.value
            
            // Get statistics from database
            val scores = scoreDao.getAllScores().first()
            val stats = currentState.statistics
            
            val statistics = mapOf(
                "total_games_played" to stats.totalGamesPlayed,
                "total_score" to stats.totalScore,
                "total_lines" to stats.totalLinesCleared,
                "highest_score" to stats.highestScore,
                "total_tspins" to stats.totalTSpins,
                "total_tetrises" to stats.totalTetrises,
                "perfect_clears" to stats.totalPerfectClears,
                "longest_combo" to stats.longestCombo,
                "total_play_time" to stats.totalPlayTime
            )
            
            val request = ProgressionSyncRequest(
                action = "sync_progression",
                playerId = playerId,
                level = currentState.level,
                currentXp = currentState.currentXP,
                totalXp = currentState.totalXP,
                rank = currentState.rank.displayName,
                unlockedThemes = currentState.unlockedThemes.toList(),
                unlockedMusic = currentState.unlockedMusic.toList(),
                unlockedPieceStyles = currentState.unlockedPieceStyles.toList(),
                unlockedEffects = currentState.unlockedEffects.toList(),
                statistics = statistics,
                totalGamesPlayed = stats.totalGamesPlayed,
                totalPlayTime = stats.totalPlayTime,
                favoriteMode = stats.favoriteMode,
                dailyStreak = playerProgression.progressionState.value.dailyStreak,
                dailyChallengesCompleted = playerProgression.progressionState.value.dailyChallengesCompleted
            )
            
            val response = apiService.syncProgression(request)
            
            if (response.isSuccessful) {
                Timber.d("Progression successfully synced to server")
            } else {
                Timber.w("Failed to sync progression: ${response.message()}")
            }
            
        } catch (e: Exception) {
            Timber.e(e, "Error pushing progression to server")
        }
    }
    
    /**
     * Sync progression with API - call this after each game
     */
    suspend fun syncWithAPI(userId: String) {
        syncProgression()
    }
    
    /**
     * Get unlocks for a specific level
     */
    suspend fun getUnlocksForLevel(level: Int): List<String> {
        return withContext(Dispatchers.IO) {
            val unlocks = mutableListOf<String>()
            
            // Check theme unlocks
            when (level) {
                5 -> unlocks.add("Retro Theme")
                10 -> unlocks.add("Nature Theme")
                15 -> unlocks.add("Minimal Theme")
                25 -> unlocks.add("Galaxy Theme")
                35 -> unlocks.add("Matrix Theme")
                50 -> unlocks.add("Rainbow Theme")
            }
            
            // Check music unlocks
            when (level) {
                3 -> unlocks.add("Chiptune Music")
                8 -> unlocks.add("Synthwave Music")
                20 -> unlocks.add("Orchestral Music")
                30 -> unlocks.add("Jazz Music")
                40 -> unlocks.add("Metal Music")
                60 -> unlocks.add("Lo-fi Music")
            }
            
            // Check piece style unlocks
            when (level) {
                7 -> unlocks.add("Glass Pieces")
                12 -> unlocks.add("Pixel Pieces")
                22 -> unlocks.add("Hologram Pieces")
                32 -> unlocks.add("Crystal Pieces")
                45 -> unlocks.add("Animated Pieces")
            }
            
            // Check effect unlocks
            when (level) {
                5 -> unlocks.add("Particle Effects")
                10 -> unlocks.add("Trail Effects")
                18 -> unlocks.add("Explosion Effects")
                28 -> unlocks.add("Lightning Effects")
                38 -> unlocks.add("Shatter Effects")
                55 -> unlocks.add("Quantum Effects")
            }
            
            unlocks
        }
    }
    
    /**
     * Reset progression
     */
    suspend fun resetProgression() {
        withContext(Dispatchers.IO) {
            playerProgression.reset()
            val entity = ProgressionEntity(
                currentLevel = 1,
                currentXp = 0,
                totalXp = 0,
                currentRank = "Novice",
                unlockedThemes = gson.toJson(setOf("cyberpunk")),
                unlockedMusic = gson.toJson(setOf("default")),
                unlockedPieceStyles = gson.toJson(setOf("default")),
                unlockedEffects = gson.toJson(setOf("default")),
                statistics = gson.toJson(mapOf<String, Any>())
            )
            progressionDao.updateProgression(entity)
        }
    }
}