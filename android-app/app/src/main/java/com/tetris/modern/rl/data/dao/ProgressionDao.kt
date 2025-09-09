package com.tetris.modern.rl.data.dao

import androidx.room.*
import com.tetris.modern.rl.data.entities.ProgressionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressionDao {
    @Query("SELECT * FROM progression WHERE id = 1")
    fun getProgression(): Flow<ProgressionEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProgression(progression: ProgressionEntity)
    
    @Query("UPDATE progression SET currentXp = :xp, totalXp = :totalXp WHERE id = 1")
    suspend fun updateXp(xp: Int, totalXp: Int)
    
    @Query("UPDATE progression SET currentLevel = :level, currentRank = :rank WHERE id = 1")
    suspend fun updateLevelAndRank(level: Int, rank: String)
}