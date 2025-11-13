package com.tetris.modern.rl.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "daily_streaks")
data class DailyStreakEntity(
    @PrimaryKey
    val id: Int = 1, // Single row for the current user
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastLoginDate: Date? = null,
    val totalDaysPlayed: Int = 0,
    val lastCompletedChallengeDate: Date? = null,
    val dailyChallengesCompleted: Int = 0,
    val missedDays: Int = 0,
    val streakStartDate: Date? = null,
    val isStreakActive: Boolean = false
)