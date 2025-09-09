package com.tetris.modern.rl.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "puzzle_progress")
data class PuzzleProgressEntity(
    @PrimaryKey
    val puzzleId: Int,
    val isCompleted: Boolean = false,
    val bestScore: Int = 0,
    val stars: Int = 0,
    val attempts: Int = 0,
    val completionTime: Long? = null,
    val hintsUsed: Int = 0
)