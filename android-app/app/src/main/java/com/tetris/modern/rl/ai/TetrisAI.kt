package com.tetris.modern.rl.ai

import com.tetris.modern.rl.game.Grid
import com.tetris.modern.rl.game.TetrisEngine
import com.tetris.modern.rl.game.models.Piece
import com.tetris.modern.rl.game.models.PieceType
import kotlinx.coroutines.*
import kotlin.math.*
import kotlin.random.Random

/**
 * Advanced Tetris AI with 5 difficulty levels
 * Implements T-spin detection, combo tracking, and optimal pathfinding
 */
class TetrisAI(private val difficultyLevel: Int = 2) {
    
    companion object {
        // AI Difficulty Levels
        const val LEVEL_EASY = 1      // Beginner - Makes frequent mistakes
        const val LEVEL_NORMAL = 2     // Normal - Good placement, occasional mistakes
        const val LEVEL_HARD = 3       // Hard - Very good placement, rare mistakes
        const val LEVEL_EXPERT = 4     // Expert - Near-optimal play
        const val LEVEL_MASTER = 5     // Master - Perfect play with advanced strategies
        
        // Evaluation weights for different metrics
        private val WEIGHTS = mapOf(
            "height" to -0.510066,
            "holes" to -0.35663,
            "bumpiness" to -0.184483,
            "lines" to 0.760666,
            "wells" to -0.35663,
            "deepHoles" to -0.7,
            "transitions" to -0.3,
            "tSpinSetup" to 0.4,
            "tetrisWell" to 0.3
        )
    }
    
    private var grid = Grid(TetrisEngine.GRID_WIDTH, TetrisEngine.TOTAL_HEIGHT)
    private var currentPiece: Piece? = null
    private var nextPieces = listOf<PieceType>()
    private var holdPiece: PieceType? = null
    private var canHold = true
    
    private var linesCleared = 0
    private var score = 0
    private var isGameOver = false
    
    private val thinkingDelay: Long
    private val errorRate: Float
    private val lookAheadDepth: Int
    private val useTSpins: Boolean
    private val useHold: Boolean
    private val comboTracking: Boolean
    
    init {
        // Configure AI based on difficulty level
        when (difficultyLevel) {
            LEVEL_EASY -> {
                thinkingDelay = 500L
                errorRate = 0.3f
                lookAheadDepth = 0
                useTSpins = false
                useHold = false
                comboTracking = false
            }
            LEVEL_NORMAL -> {
                thinkingDelay = 300L
                errorRate = 0.15f
                lookAheadDepth = 1
                useTSpins = false
                useHold = true
                comboTracking = false
            }
            LEVEL_HARD -> {
                thinkingDelay = 150L
                errorRate = 0.05f
                lookAheadDepth = 2
                useTSpins = true
                useHold = true
                comboTracking = true
            }
            LEVEL_EXPERT -> {
                thinkingDelay = 50L
                errorRate = 0.01f
                lookAheadDepth = 3
                useTSpins = true
                useHold = true
                comboTracking = true
            }
            LEVEL_MASTER -> {
                thinkingDelay = 10L
                errorRate = 0f
                lookAheadDepth = 4
                useTSpins = true
                useHold = true
                comboTracking = true
            }
            else -> {
                thinkingDelay = 300L
                errorRate = 0.15f
                lookAheadDepth = 1
                useTSpins = false
                useHold = true
                comboTracking = false
            }
        }
    }
    
    data class Move(
        val x: Int,
        val rotation: Int,
        val useHold: Boolean = false,
        val score: Double = 0.0,
        val isTSpin: Boolean = false
    )
    
    suspend fun update(deltaTime: Long) {
        if (isGameOver) return
        
        currentPiece?.let { piece ->
            // Add thinking delay based on difficulty
            delay(thinkingDelay)
            
            // Calculate best move
            val bestMove = findBestMove(piece)
            
            // Apply error rate
            val finalMove = if (Random.nextFloat() < errorRate) {
                // Make a mistake - choose a random move instead
                findRandomMove(piece)
            } else {
                bestMove
            }
            
            // Execute the move
            executeMove(finalMove)
        }
    }
    
    private fun findBestMove(piece: Piece): Move {
        val moves = mutableListOf<Move>()
        
        // Try all possible positions and rotations
        for (rotation in 0..3) {
            for (x in -2..TetrisEngine.GRID_WIDTH + 2) {
                val testPiece = piece.copy(x = x, rotation = rotation)
                
                if (isValidPlacement(testPiece)) {
                    // Drop the piece to find landing position
                    val landedPiece = dropPiece(testPiece)
                    
                    // Evaluate the position
                    val score = evaluatePosition(landedPiece)
                    
                    // Check for T-spin
                    val isTSpin = useTSpins && checkTSpin(landedPiece)
                    
                    moves.add(Move(x, rotation, false, score, isTSpin))
                }
            }
        }
        
        // Try using hold if available
        if (useHold && canHold && holdPiece != null) {
            val heldPieceMoves = findMovesForPiece(
                Piece(holdPiece!!, TetrisEngine.GRID_WIDTH / 2, 0, 0)
            )
            heldPieceMoves.forEach { move ->
                moves.add(move.copy(useHold = true))
            }
        }
        
        // Look ahead if configured
        if (lookAheadDepth > 0 && nextPieces.isNotEmpty()) {
            moves.forEach { move ->
                val futureScore = evaluateFuture(move, lookAheadDepth)
                moves[moves.indexOf(move)] = move.copy(score = move.score + futureScore * 0.3)
            }
        }
        
        // Prioritize T-spins and special moves
        if (useTSpins) {
            moves.filter { it.isTSpin }.forEach { move ->
                moves[moves.indexOf(move)] = move.copy(score = move.score * 1.5)
            }
        }
        
        // Return the best move
        return moves.maxByOrNull { it.score } ?: Move(4, 0)
    }
    
    private fun findRandomMove(piece: Piece): Move {
        val validMoves = mutableListOf<Move>()
        
        for (rotation in 0..3) {
            for (x in 0 until TetrisEngine.GRID_WIDTH) {
                val testPiece = piece.copy(x = x, rotation = rotation)
                if (isValidPlacement(testPiece)) {
                    validMoves.add(Move(x, rotation))
                }
            }
        }
        
        return validMoves.randomOrNull() ?: Move(4, 0)
    }
    
    private fun findMovesForPiece(piece: Piece): List<Move> {
        val moves = mutableListOf<Move>()
        
        for (rotation in 0..3) {
            for (x in 0 until TetrisEngine.GRID_WIDTH) {
                val testPiece = piece.copy(x = x, rotation = rotation)
                if (isValidPlacement(testPiece)) {
                    val score = evaluatePosition(dropPiece(testPiece))
                    moves.add(Move(x, rotation, false, score))
                }
            }
        }
        
        return moves
    }
    
    private fun evaluatePosition(piece: Piece): Double {
        // Create a copy of the grid with the piece placed
        val testGrid = grid.copy()
        testGrid.placePiece(piece)
        
        // Calculate evaluation metrics
        val height = testGrid.getAggregateHeight()
        val holes = testGrid.getHoles()
        val bumpiness = testGrid.getBumpiness()
        val wells = testGrid.getWells()
        val lines = countCompleteLines(testGrid)
        val deepHoles = countDeepHoles(testGrid)
        val transitions = countTransitions(testGrid)
        
        // Calculate weighted score
        var score = 0.0
        score += WEIGHTS["height"]!! * height
        score += WEIGHTS["holes"]!! * holes
        score += WEIGHTS["bumpiness"]!! * bumpiness
        score += WEIGHTS["wells"]!! * wells
        score += WEIGHTS["lines"]!! * lines * lines // Square for emphasis
        score += WEIGHTS["deepHoles"]!! * deepHoles
        score += WEIGHTS["transitions"]!! * transitions
        
        // Advanced strategies for higher difficulties
        if (difficultyLevel >= LEVEL_HARD) {
            // Check for T-spin setups
            if (piece.type == PieceType.T && checkTSpinSetup(testGrid, piece)) {
                score += WEIGHTS["tSpinSetup"]!! * 100
            }
            
            // Check for Tetris well
            if (checkTetrisWell(testGrid)) {
                score += WEIGHTS["tetrisWell"]!! * 50
            }
        }
        
        // Combo tracking
        if (comboTracking && lines > 0) {
            score += lines * 10 // Bonus for maintaining combos
        }
        
        return score
    }
    
    private fun evaluateFuture(move: Move, depth: Int): Double {
        if (depth <= 0 || nextPieces.isEmpty()) return 0.0
        
        // Simulate placing the piece
        val testGrid = grid.copy()
        val testPiece = currentPiece?.copy(x = move.x, rotation = move.rotation)
        testPiece?.let {
            testGrid.placePiece(dropPiece(it))
        }
        
        // Evaluate next piece placement
        val nextPiece = Piece(nextPieces.first(), TetrisEngine.GRID_WIDTH / 2, 0, 0)
        val nextMoves = findMovesForPiece(nextPiece)
        
        return nextMoves.maxOfOrNull { it.score } ?: 0.0
    }
    
    private fun isValidPlacement(piece: Piece): Boolean {
        val shape = piece.getCurrentShape()
        
        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] != 0) {
                    val gridX = piece.x + col
                    val gridY = piece.y + row
                    
                    if (gridX < 0 || gridX >= TetrisEngine.GRID_WIDTH || 
                        gridY >= TetrisEngine.TOTAL_HEIGHT) {
                        return false
                    }
                    
                    if (gridY >= 0 && grid.getCell(gridX, gridY) != null) {
                        return false
                    }
                }
            }
        }
        
        return true
    }
    
    private fun dropPiece(piece: Piece): Piece {
        var droppedPiece = piece.copy()
        while (isValidPlacement(droppedPiece.copy(y = droppedPiece.y + 1))) {
            droppedPiece.y++
        }
        return droppedPiece
    }
    
    private fun countCompleteLines(testGrid: Grid): Int {
        var lines = 0
        for (y in 0 until TetrisEngine.TOTAL_HEIGHT) {
            var complete = true
            for (x in 0 until TetrisEngine.GRID_WIDTH) {
                if (testGrid.getCell(x, y) == null) {
                    complete = false
                    break
                }
            }
            if (complete) lines++
        }
        return lines
    }
    
    private fun countDeepHoles(testGrid: Grid): Int {
        var deepHoles = 0
        
        for (x in 0 until TetrisEngine.GRID_WIDTH) {
            var blockFound = false
            var holesBelow = 0
            
            for (y in 0 until TetrisEngine.TOTAL_HEIGHT) {
                if (testGrid.getCell(x, y) != null) {
                    blockFound = true
                    if (holesBelow > 2) { // Deep hole is 3+ cells deep
                        deepHoles += holesBelow
                    }
                    holesBelow = 0
                } else if (blockFound) {
                    holesBelow++
                }
            }
        }
        
        return deepHoles
    }
    
    private fun countTransitions(testGrid: Grid): Int {
        var transitions = 0
        
        // Horizontal transitions
        for (y in 0 until TetrisEngine.TOTAL_HEIGHT) {
            for (x in 0 until TetrisEngine.GRID_WIDTH - 1) {
                val current = testGrid.getCell(x, y) != null
                val next = testGrid.getCell(x + 1, y) != null
                if (current != next) transitions++
            }
        }
        
        // Vertical transitions
        for (x in 0 until TetrisEngine.GRID_WIDTH) {
            for (y in 0 until TetrisEngine.TOTAL_HEIGHT - 1) {
                val current = testGrid.getCell(x, y) != null
                val next = testGrid.getCell(x, y + 1) != null
                if (current != next) transitions++
            }
        }
        
        return transitions
    }
    
    private fun checkTSpin(piece: Piece): Boolean {
        if (piece.type != PieceType.T) return false
        
        // Check corners for T-spin detection
        val corners = listOf(
            Pair(piece.x - 1, piece.y - 1),
            Pair(piece.x + 1, piece.y - 1),
            Pair(piece.x - 1, piece.y + 1),
            Pair(piece.x + 1, piece.y + 1)
        )
        
        var filledCorners = 0
        for ((x, y) in corners) {
            if (x < 0 || x >= TetrisEngine.GRID_WIDTH || y >= TetrisEngine.TOTAL_HEIGHT || 
                grid.getCell(x, y) != null) {
                filledCorners++
            }
        }
        
        return filledCorners >= 3
    }
    
    private fun checkTSpinSetup(testGrid: Grid, piece: Piece): Boolean {
        // Check if the current position sets up a future T-spin
        if (difficultyLevel < LEVEL_HARD) return false
        
        // Look for T-spin slots in the grid
        for (y in 1 until TetrisEngine.TOTAL_HEIGHT - 1) {
            for (x in 1 until TetrisEngine.GRID_WIDTH - 1) {
                if (testGrid.getCell(x, y) == null) {
                    // Check if this could be a T-spin slot
                    val corners = listOf(
                        testGrid.getCell(x - 1, y - 1) != null,
                        testGrid.getCell(x + 1, y - 1) != null,
                        testGrid.getCell(x - 1, y + 1) != null,
                        testGrid.getCell(x + 1, y + 1) != null
                    )
                    
                    if (corners.count { it } >= 3) {
                        return true
                    }
                }
            }
        }
        
        return false
    }
    
    private fun checkTetrisWell(testGrid: Grid): Boolean {
        // Check if there's a well suitable for Tetris
        for (x in listOf(0, TetrisEngine.GRID_WIDTH - 1)) {
            var wellDepth = 0
            
            for (y in TetrisEngine.TOTAL_HEIGHT - 1 downTo 0) {
                if (testGrid.getCell(x, y) == null) {
                    wellDepth++
                } else {
                    break
                }
            }
            
            if (wellDepth >= 4) {
                return true
            }
        }
        
        return false
    }
    
    private fun executeMove(move: Move) {
        // This would send commands to the game engine
        // In practice, this would be handled by the Battle mode
    }
    
    fun reset() {
        grid.clear()
        currentPiece = null
        nextPieces = emptyList()
        holdPiece = null
        canHold = true
        linesCleared = 0
        score = 0
        isGameOver = false
    }
    
    fun receiveGarbage(lines: Int) {
        // Handle incoming garbage lines
        // Add gray lines to the bottom of the grid
    }
    
    fun getClearedLines(): Int {
        val cleared = linesCleared
        linesCleared = 0
        return cleared
    }
    
    fun isGameOver(): Boolean {
        return isGameOver
    }
    
    fun getScore(): Int {
        return score
    }
}