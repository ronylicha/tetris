package com.tetris.modern.rl.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customizations")
data class CustomizationEntity(
    @PrimaryKey
    val itemId: String,
    val type: String, // theme, music, pieceStyle, effect
    val name: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val unlockLevel: Int = 0,
    val previewImage: String? = null,
    val data: String? = null // JSON for additional data
)