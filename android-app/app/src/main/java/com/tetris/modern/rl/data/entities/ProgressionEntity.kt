package com.tetris.modern.rl.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progression")
data class ProgressionEntity(
    @PrimaryKey
    val id: Int = 1,
    val currentLevel: Int = 1,
    val currentXp: Int = 0,
    val totalXp: Int = 0,
    val currentRank: String = "Novice",
    val unlockedThemes: String, // JSON list
    val unlockedMusic: String, // JSON list
    val unlockedPieceStyles: String, // JSON list
    val unlockedEffects: String, // JSON list
    val statistics: String // JSON map
)