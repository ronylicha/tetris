package com.tetris.modern.rl.game.modes

import com.tetris.modern.rl.game.models.GameState
import com.tetris.modern.rl.puzzles.Puzzle
import com.tetris.modern.rl.puzzles.PuzzleManager

/**
 * Puzzle Mode - Complete 150 challenging puzzles
 */
class PuzzleMode : GameMode("Puzzle") {
    
    private var currentPuzzle: Puzzle? = null
    private var puzzleManager: PuzzleManager = PuzzleManager()
    private var movesUsed = 0
    private var hintsUsed = 0
    private var startTime = 0L
    
    fun loadPuzzle(puzzleId: Int) {
        currentPuzzle = puzzleManager.getPuzzle(puzzleId)
        currentPuzzle?.let { puzzle ->
            // Initialize grid with puzzle configuration
            engine?.let { eng ->
                // Load puzzle grid state
                puzzle.setupGrid(eng)
            }
            movesUsed = 0
            hintsUsed = 0
            startTime = System.currentTimeMillis()
        }
    }
    
    override fun update(deltaTime: Long, gameState: GameState) {
        currentPuzzle?.let { puzzle ->
            // Check puzzle-specific conditions
            when (puzzle.objective) {
                Puzzle.PuzzleObjective.CLEAR_ALL -> {
                    // Check if all lines are cleared
                }
                Puzzle.PuzzleObjective.CLEAR_SPECIFIC -> {
                    // Check if specific lines are cleared
                }
                Puzzle.PuzzleObjective.CLEAR_TARGET -> {
                    // Check if target lines are cleared
                }
                Puzzle.PuzzleObjective.CASCADE -> {
                    // Check for cascade clears
                }
                Puzzle.PuzzleObjective.PATTERN -> {
                    // Check for pattern completion
                }
                Puzzle.PuzzleObjective.SURVIVAL -> {
                    // Check survival time
                }
                Puzzle.PuzzleObjective.SPEED -> {
                    // Check completion speed
                }
                Puzzle.PuzzleObjective.NO_ROTATION -> {
                    // Check no rotation constraint
                }
                Puzzle.PuzzleObjective.CHAIN -> {
                    // Check chain completion
                }
                Puzzle.PuzzleObjective.PERFECT_CLEAR -> {
                    // Check perfect clear
                }
                Puzzle.PuzzleObjective.T_SPIN_ONLY -> {
                    // Check T-spin only requirement
                }
                Puzzle.PuzzleObjective.TETRIS_ONLY -> {
                    // Check Tetris only requirement
                }
            }
        }
    }
    
    override fun handleLineClears(lines: Int, isTSpin: Boolean) {
        currentPuzzle?.let { puzzle ->
            // Track progress towards puzzle objective
            puzzle.onLinesClear(lines, isTSpin)
            
            // Check if puzzle is completed
            if (puzzle.isCompleted()) {
                onPuzzleCompleted()
            }
        }
    }
    
    private fun onPuzzleCompleted() {
        val completionTime = System.currentTimeMillis() - startTime
        val stars = calculateStars(completionTime)
        
        // Save puzzle progress
        puzzleManager.markPuzzleCompleted(
            currentPuzzle?.id ?: 0,
            stars,
            completionTime,
            movesUsed,
            hintsUsed
        )
    }
    
    private fun calculateStars(completionTime: Long): Int {
        currentPuzzle?.let { puzzle ->
            var stars = 3
            
            // Deduct for hints used
            stars -= hintsUsed.coerceAtMost(2)
            
            // Deduct for excessive moves
            if (movesUsed > puzzle.maxPieces * 1.5) {
                stars--
            }
            
            // Bonus for speed
            if (completionTime < puzzle.targetTime * 0.5) {
                stars = (stars + 1).coerceAtMost(3)
            }
            
            return stars.coerceAtLeast(1)
        }
        return 1
    }
    
    fun requestHint(): String? {
        if (hintsUsed >= 3) return null
        
        currentPuzzle?.let { puzzle ->
            hintsUsed++
            return puzzle.getHint(hintsUsed)
        }
        return null
    }
    
    override fun getObjective(): String {
        return currentPuzzle?.description ?: "Select a puzzle to begin"
    }
    
    override fun getModeUI(): Map<String, String> {
        return currentPuzzle?.let { puzzle ->
            mapOf(
                "Puzzle" to "#${puzzle.id}",
                "Objective" to puzzle.objectiveShort,
                "Moves" to "$movesUsed / ${puzzle.maxPieces}",
                "Hints" to "$hintsUsed / 3"
            )
        } ?: emptyMap()
    }
    
    override fun getDescription(): String {
        return "Challenge yourself with 150 unique puzzles. Each puzzle has specific objectives and constraints."
    }
}