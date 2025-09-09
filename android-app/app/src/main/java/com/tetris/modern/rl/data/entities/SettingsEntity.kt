package com.tetris.modern.rl.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey
    val id: Int = 1,
    val masterVolume: Float = 1.0f,
    val musicVolume: Float = 0.8f,
    val sfxVolume: Float = 1.0f,
    val vibrationEnabled: Boolean = true,
    val touchSensitivity: Float = 1.0f,
    val selectedTheme: String = "cyberpunk",
    val selectedMusic: String = "default",
    val selectedPieceStyle: String = "default",
    val selectedEffects: String = "default",
    val showGhostPiece: Boolean = true,
    val showNextPieces: Int = 4,
    val autoHold: Boolean = false,
    val das: Int = 100,
    val arr: Int = 20,
    val isDarkMode: Boolean = true,
    val useDynamicColors: Boolean = false,
    val isMuted: Boolean = false,
    val controlType: String = "touch", // "touch" or "pad"
    val isFirstLaunch: Boolean = true
)