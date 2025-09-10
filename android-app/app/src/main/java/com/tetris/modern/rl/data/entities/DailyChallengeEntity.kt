package com.tetris.modern.rl.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "daily_challenges")
data class DailyChallengeEntity(
    @PrimaryKey
    val date: String, // YYYY-MM-DD format
    val seed: Long,
    val modifiers: String, // JSON list
    val targetScore: Int,
    val isCompleted: Boolean = false,
    val playerScore: Int = 0,
    val completionTime: Long? = null,
    val attempts: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0
)