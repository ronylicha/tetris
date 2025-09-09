package com.tetris.modern.rl.data.dao

import androidx.room.*
import com.tetris.modern.rl.data.entities.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): Flow<List<AchievementEntity>>
    
    @Query("SELECT * FROM achievements WHERE isUnlocked = 1")
    fun getUnlockedAchievements(): Flow<List<AchievementEntity>>
    
    @Query("SELECT * FROM achievements WHERE category = :category")
    fun getAchievementsByCategory(category: String): Flow<List<AchievementEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAchievement(achievement: AchievementEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAchievements(achievements: List<AchievementEntity>)
    
    @Query("UPDATE achievements SET isUnlocked = 1, unlockedDate = :date WHERE achievementId = :id")
    suspend fun unlockAchievement(id: String, date: Long)
    
    @Query("UPDATE achievements SET progress = :progress WHERE achievementId = :id")
    suspend fun updateProgress(id: String, progress: Float)
}