package com.tetris.modern.rl.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_states")
data class GameStateEntity(
    @PrimaryKey
    val id: Int = 1,
    val grid: String, // JSON representation
    val currentPiece: String, // JSON
    val nextPieces: String, // JSON
    val heldPiece: String?, // JSON
    val score: Int,
    val level: Int,
    val lines: Int,
    val gameMode: String,
    val isPaused: Boolean,
    val timestamp: Long
)