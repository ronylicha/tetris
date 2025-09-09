package com.tetris.modern.rl.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statistics")
data class StatisticsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameMode: String,
    val totalGamesPlayed: Int = 0,
    val totalLinesCleared: Int = 0,
    val totalScore: Long = 0,
    val highScore: Int = 0,
    val bestCombo: Int = 0,
    val totalPiecesPlaced: Int = 0,
    val totalTSpins: Int = 0,
    val totalTetrisClears: Int = 0,
    val totalPerfectClears: Int = 0,
    val totalTimePlayed: Long = 0, // in milliseconds
    val averageScore: Float = 0f,
    val averageLinesPerGame: Float = 0f,
    val winRate: Float = 0f, // for battle modes
    val longestSurvivalTime: Long = 0, // in milliseconds
    val fastestSprint40: Long = 0, // in milliseconds
    val highestLevel: Int = 0,
    val totalXPEarned: Int = 0,
    val favoriteMode: String? = null,
    val lastPlayedTimestamp: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)