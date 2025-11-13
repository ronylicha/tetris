package com.tetris.modern.rl.data.dao

import androidx.room.*
import com.tetris.modern.rl.data.entities.DailyStreakEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface DailyStreakDao {
    
    @Query("SELECT * FROM daily_streaks WHERE id = 1")
    fun getStreak(): Flow<DailyStreakEntity?>
    
    @Query("SELECT * FROM daily_streaks WHERE id = 1")
    suspend fun getStreakOnce(): DailyStreakEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateStreak(streak: DailyStreakEntity)
    
    @Query("UPDATE daily_streaks SET currentStreak = :streak, lastLoginDate = :date, isStreakActive = :active WHERE id = 1")
    suspend fun updateCurrentStreak(streak: Int, date: Date, active: Boolean)
    
    @Query("UPDATE daily_streaks SET longestStreak = :streak WHERE id = 1 AND :streak > longestStreak")
    suspend fun updateLongestStreak(streak: Int)
    
    @Query("UPDATE daily_streaks SET dailyChallengesCompleted = dailyChallengesCompleted + 1, lastCompletedChallengeDate = :date WHERE id = 1")
    suspend fun incrementDailyChallenges(date: Date)
    
    @Query("UPDATE daily_streaks SET totalDaysPlayed = totalDaysPlayed + 1 WHERE id = 1")
    suspend fun incrementTotalDaysPlayed()
    
    @Query("UPDATE daily_streaks SET missedDays = missedDays + 1, isStreakActive = 0, currentStreak = 0 WHERE id = 1")
    suspend fun resetStreak()
    
    @Query("DELETE FROM daily_streaks")
    suspend fun clearAll()
}