package com.tetris.modern.rl.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unlockables")
data class UnlockableEntity(
    @PrimaryKey
    val id: String,
    val type: String, // "theme", "music", "pieceStyle", "effect"
    val name: String,
    val description: String,
    val requiredLevel: Int,
    val requiredXP: Int = 0,
    val requiredAchievement: String? = null,
    val cost: Int = 0, // For potential in-game currency
    val isUnlocked: Boolean = false,
    val isSelected: Boolean = false,
    val metadata: String? = null, // JSON string for additional data
    val iconResource: String? = null,
    val previewResource: String? = null,
    val rarity: String = "common", // common, rare, epic, legendary
    val orderIndex: Int = 0,
    val unlockedAt: Long? = null,
    val usageCount: Int = 0
)