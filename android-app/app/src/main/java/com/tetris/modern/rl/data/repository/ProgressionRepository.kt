package com.tetris.modern.rl.data.repository

import com.google.gson.Gson
import com.tetris.modern.rl.data.dao.ProgressionDao
import com.tetris.modern.rl.data.entities.ProgressionEntity
import com.tetris.modern.rl.network.ApiService
import com.tetris.modern.rl.network.models.*
import com.tetris.modern.rl.progression.PlayerProgression
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressionRepository @Inject constructor(
    private val progressionDao: ProgressionDao,
    private val apiService: ApiService,
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
                    val request = ProgressionUpdateRequest(
                        playerId = "player1", // TODO: Get actual player ID
                        xpGained = xpGained,
                        source = source,
                        gameData = gameData
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
                                newXP = currentState.currentXP,
                                levelUp = false,
                                unlocks = null
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
                            newXP = currentState.currentXP,
                            levelUp = false,
                            unlocks = null
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
                // Get server progression
                val response = apiService.getProgression("player1") // TODO: Get actual player ID
                
                if (response.isSuccessful && response.body()?.success == true) {
                    val serverData = response.body()?.progression
                    serverData?.let { sd ->
                        // Compare with local and merge
                        val localData = progressionDao.getProgression().firstOrNull()
                        
                        if (localData == null || sd.totalXP > localData.totalXp) {
                            // Server has more progress, update local
                            val updatedEntity = ProgressionEntity(
                                currentLevel = sd.level,
                                currentXp = sd.currentXP,
                                totalXp = sd.totalXP,
                                currentRank = sd.rank,
                                unlockedThemes = gson.toJson(sd.unlockedItems.themes),
                                unlockedMusic = gson.toJson(sd.unlockedItems.music),
                                unlockedPieceStyles = gson.toJson(sd.unlockedItems.pieceStyles),
                                unlockedEffects = gson.toJson(sd.unlockedItems.effects),
                                statistics = gson.toJson(sd.statistics)
                            )
                            progressionDao.updateProgression(updatedEntity)
                            
                            // Update in-memory state
                            playerProgression.reset()
                            repeat(sd.totalXP) {
                                playerProgression.addXP(1, "sync")
                            }
                        } else if (localData.totalXp > sd.totalXP) {
                            // Local has more progress, push to server
                            val localTotalXP = localData.totalXp
                            val request = ProgressionSyncRequest(
                                playerId = "player1", // TODO: Get actual player ID
                                totalXP = localTotalXP,
                                level = localData.currentLevel,
                                rank = localData.currentRank,
                                unlockedItems = listOf() // TODO: Parse from JSON
                            )
                            apiService.syncProgression(request)
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to sync progression")
            }
        }
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