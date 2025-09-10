package com.tetris.modern.rl.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey
    val achievementId: String,
    val name: String,
    val description: String,
    val category: String,
    val xpReward: Int,
    val isUnlocked: Boolean = false,
    val unlockedDate: Date? = null,
    val progress: Float = 0f,
    val maxProgress: Float = 1f
)