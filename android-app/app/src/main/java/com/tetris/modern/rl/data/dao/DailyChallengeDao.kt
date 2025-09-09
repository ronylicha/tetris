package com.tetris.modern.rl.data.dao

import androidx.room.*
import com.tetris.modern.rl.data.entities.DailyChallengeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyChallengeDao {
    @Query("SELECT * FROM daily_challenges WHERE date = :date")
    fun getTodaysChallenge(date: String): Flow<DailyChallengeEntity?>
    
    @Query("SELECT * FROM daily_challenges ORDER BY date DESC")
    fun getAllChallenges(): Flow<List<DailyChallengeEntity>>
    
    @Query("SELECT * FROM daily_challenges WHERE isCompleted = 1 ORDER BY date DESC")
    fun getCompletedChallenges(): Flow<List<DailyChallengeEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateChallenge(challenge: DailyChallengeEntity)
    
    @Query("SELECT MAX(currentStreak) FROM daily_challenges")
    suspend fun getBestStreak(): Int
    
    @Query("SELECT currentStreak FROM daily_challenges ORDER BY date DESC LIMIT 1")
    suspend fun getCurrentStreak(): Int
}