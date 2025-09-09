package com.tetris.modern.rl.puzzles

import com.tetris.modern.rl.game.TetrisEngine
import com.tetris.modern.rl.game.models.PieceType

data class Puzzle(
    val id: Int,
    val name: String,
    val description: String,
    val objective: PuzzleObjective,
    val objectiveShort: String,
    val difficulty: PuzzleDifficulty,
    val maxPieces: Int,
    val targetTime: Long, // in milliseconds
    val initialGrid: Array<IntArray>,
    val availablePieces: List<PieceType>,
    val constraints: Set<PuzzleConstraint> = emptySet(),
    val hints: List<String> = emptyList(),
    val solution: List<PuzzleMove>? = null
) {
    private var linesCleared = 0
    private var piecesUsed = 0
    private var completed = false
    
    enum class PuzzleObjective {
        CLEAR_ALL,           // Clear entire grid
        CLEAR_SPECIFIC,      // Clear specific lines
        CLEAR_TARGET,        // Clear X lines
        CASCADE,             // Create cascade effect
        PATTERN,             // Create specific pattern
        SURVIVAL,            // Survive for duration
        SPEED,              // Complete within time
        NO_ROTATION,        // Complete without rotating
        CHAIN,              // Consecutive line clears
        PERFECT_CLEAR,      // End with empty grid
        T_SPIN_ONLY,        // Clear lines only with T-spins
        TETRIS_ONLY         // Clear lines only with Tetrises
    }
    
    enum class PuzzleDifficulty(val stars: Int, val color: String) {
        EASY(1, "#4CAF50"),
        MEDIUM(2, "#FFC107"),
        HARD(3, "#FF9800"),
        EXPERT(4, "#F44336"),
        MASTER(5, "#9C27B0")
    }
    
    enum class PuzzleConstraint {
        NO_HOLD,            // Can't use hold
        NO_SOFT_DROP,       // Can't soft drop
        NO_HARD_DROP,       // Can't hard drop
        NO_ROTATION,        // Can't rotate pieces
        MIRROR_MODE,        // Controls are mirrored
        INVISIBLE_PIECES,   // Pieces become invisible
        LIMITED_PIECES,     // Limited piece types
        SPEED_UP,          // Increased drop speed
        ONE_CHANCE         // No mistakes allowed
    }
    
    data class PuzzleMove(
        val pieceType: PieceType,
        val x: Int,
        val y: Int,
        val rotation: Int
    )
    
    fun setupGrid(engine: TetrisEngine) {
        // Load initial grid configuration into engine
        // This would set up the puzzle's starting state
    }
    
    fun onLinesClear(lines: Int, isTSpin: Boolean) {
        linesCleared += lines
        
        when (objective) {
            PuzzleObjective.CLEAR_ALL -> {
                // Check if grid is empty
            }
            PuzzleObjective.CLEAR_TARGET -> {
                // Check if target lines reached
            }
            PuzzleObjective.T_SPIN_ONLY -> {
                if (!isTSpin && lines > 0) {
                    // Fail - cleared lines without T-spin
                }
            }
            PuzzleObjective.TETRIS_ONLY -> {
                if (lines > 0 && lines != 4) {
                    // Fail - cleared lines but not a Tetris
                }
            }
            else -> {}
        }
    }
    
    fun onPiecePlaced(piece: PieceType) {
        piecesUsed++
        
        if (piecesUsed > maxPieces) {
            // Puzzle failed - too many pieces used
        }
    }
    
    fun isCompleted(): Boolean {
        return completed
    }
    
    fun checkCompletion(): Boolean {
        completed = when (objective) {
            PuzzleObjective.CLEAR_ALL -> linesCleared >= initialGrid.size
            PuzzleObjective.CLEAR_TARGET -> linesCleared >= getTargetLines()
            PuzzleObjective.PERFECT_CLEAR -> linesCleared > 0 && isGridEmpty()
            else -> false
        }
        return completed
    }
    
    private fun getTargetLines(): Int {
        // Extract target from objective description
        return when (id) {
            in 1..10 -> 1
            in 11..30 -> 2
            in 31..60 -> 3
            in 61..100 -> 4
            else -> 5
        }
    }
    
    private fun isGridEmpty(): Boolean {
        // Check if grid is completely empty
        return true // Placeholder
    }
    
    fun getHint(level: Int): String {
        return when (level) {
            1 -> hints.getOrNull(0) ?: "Try placing the piece in column ${solution?.firstOrNull()?.x ?: 4}"
            2 -> hints.getOrNull(1) ?: "Rotate the piece ${solution?.firstOrNull()?.rotation ?: 0} times"
            3 -> hints.getOrNull(2) ?: "Full solution: ${solution?.firstOrNull()?.let { "Place ${it.pieceType} at (${it.x}, ${it.y}) with rotation ${it.rotation}" } ?: "No solution available"}"
            else -> "No more hints available"
        }
    }
}