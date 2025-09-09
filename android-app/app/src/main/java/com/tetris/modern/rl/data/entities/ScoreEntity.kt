package com.tetris.modern.rl.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "scores")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val playerName: String,
    val score: Int,
    val level: Int,
    val lines: Int,
    val gameMode: String,
    val date: Date = Date(),
    val duration: Long = 0,
    val isSynced: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)