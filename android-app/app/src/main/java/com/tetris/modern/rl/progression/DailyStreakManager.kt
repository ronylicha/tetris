package com.tetris.modern.rl.progression

import com.tetris.modern.rl.data.dao.DailyStreakDao
import com.tetris.modern.rl.data.entities.DailyStreakEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyStreakManager @Inject constructor(
    private val dailyStreakDao: DailyStreakDao
) {
    
    companion object {
        // Streak milestones for rewards
        val STREAK_MILESTONES = listOf(3, 7, 14, 30, 60, 100, 365)
        
        // XP rewards for streaks
        const val DAILY_LOGIN_XP = 50
        const val STREAK_BONUS_XP = 10 // Per day of streak
        const val MILESTONE_BONUS_XP = 500
    }
    
    val currentStreak: Flow<DailyStreakEntity?> = dailyStreakDao.getStreak()
    
    /**
     * Check and update daily login streak
     * Returns the XP earned from the daily login
     */
    suspend fun checkDailyLogin(): Int {
        var xpEarned = 0
        val currentDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        
        val streak = dailyStreakDao.getStreakOnce() ?: DailyStreakEntity()
        
        if (streak.lastLoginDate == null) {
            // First login ever
            val newStreak = DailyStreakEntity(
                currentStreak = 1,
                longestStreak = 1,
                lastLoginDate = currentDate,
                totalDaysPlayed = 1,
                streakStartDate = currentDate,
                isStreakActive = true
            )
            dailyStreakDao.insertOrUpdateStreak(newStreak)
            xpEarned = DAILY_LOGIN_XP
            Timber.d("First login - starting streak")
        } else {
            val daysSinceLastLogin = getDaysBetween(streak.lastLoginDate, currentDate)
            
            when {
                daysSinceLastLogin == 0 -> {
                    // Already logged in today
                    Timber.d("Already logged in today")
                    return 0
                }
                daysSinceLastLogin == 1 -> {
                    // Consecutive day - continue streak
                    val newStreakCount = streak.currentStreak + 1
                    val newLongestStreak = maxOf(streak.longestStreak, newStreakCount)
                    
                    val updatedStreak = streak.copy(
                        currentStreak = newStreakCount,
                        longestStreak = newLongestStreak,
                        lastLoginDate = currentDate,
                        totalDaysPlayed = streak.totalDaysPlayed + 1,
                        isStreakActive = true
                    )
                    dailyStreakDao.insertOrUpdateStreak(updatedStreak)
                    
                    // Calculate XP with streak bonus
                    xpEarned = DAILY_LOGIN_XP + (newStreakCount * STREAK_BONUS_XP)
                    
                    // Check for milestone rewards
                    if (newStreakCount in STREAK_MILESTONES) {
                        xpEarned += MILESTONE_BONUS_XP
                        Timber.d("Streak milestone reached: $newStreakCount days!")
                    }
                    
                    Timber.d("Streak continued: $newStreakCount days")
                }
                else -> {
                    // Streak broken - reset
                    val updatedStreak = DailyStreakEntity(
                        currentStreak = 1,
                        longestStreak = streak.longestStreak,
                        lastLoginDate = currentDate,
                        totalDaysPlayed = streak.totalDaysPlayed + 1,
                        missedDays = streak.missedDays + (daysSinceLastLogin - 1),
                        streakStartDate = currentDate,
                        isStreakActive = true,
                        lastCompletedChallengeDate = streak.lastCompletedChallengeDate,
                        dailyChallengesCompleted = streak.dailyChallengesCompleted
                    )
                    dailyStreakDao.insertOrUpdateStreak(updatedStreak)
                    
                    xpEarned = DAILY_LOGIN_XP
                    Timber.d("Streak broken after ${streak.currentStreak} days. Starting new streak.")
                }
            }
        }
        
        return xpEarned
    }
    
    /**
     * Mark daily challenge as completed
     */
    suspend fun completeDailyChallenge(): Int {
        val currentDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        
        val streak = dailyStreakDao.getStreakOnce() ?: return 0
        
        // Check if already completed today
        if (streak.lastCompletedChallengeDate != null) {
            val daysSinceLastChallenge = getDaysBetween(streak.lastCompletedChallengeDate, currentDate)
            if (daysSinceLastChallenge == 0) {
                Timber.d("Daily challenge already completed today")
                return 0
            }
        }
        
        dailyStreakDao.incrementDailyChallenges(currentDate)
        
        // Bonus XP for completing daily challenge
        val challengeXP = 100 + (streak.currentStreak * 5)
        Timber.d("Daily challenge completed! XP earned: $challengeXP")
        
        return challengeXP
    }
    
    /**
     * Get streak statistics
     */
    suspend fun getStreakStats(): StreakStats {
        val streak = dailyStreakDao.getStreakOnce() ?: DailyStreakEntity()
        
        return StreakStats(
            currentStreak = streak.currentStreak,
            longestStreak = streak.longestStreak,
            totalDaysPlayed = streak.totalDaysPlayed,
            dailyChallengesCompleted = streak.dailyChallengesCompleted,
            missedDays = streak.missedDays,
            isActive = streak.isStreakActive,
            nextMilestone = STREAK_MILESTONES.firstOrNull { it > streak.currentStreak }
        )
    }
    
    /**
     * Calculate days between two dates
     */
    private fun getDaysBetween(startDate: Date, endDate: Date): Int {
        val diff = endDate.time - startDate.time
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
    }
    
    /**
     * Reset all streak data (for testing or user request)
     */
    suspend fun resetAllStreaks() {
        dailyStreakDao.clearAll()
        Timber.d("All streak data reset")
    }
    
    data class StreakStats(
        val currentStreak: Int,
        val longestStreak: Int,
        val totalDaysPlayed: Int,
        val dailyChallengesCompleted: Int,
        val missedDays: Int,
        val isActive: Boolean,
        val nextMilestone: Int?
    )
}