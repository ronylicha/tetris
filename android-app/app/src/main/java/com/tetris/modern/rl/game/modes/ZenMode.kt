package com.tetris.modern.rl.game.modes

import com.tetris.modern.rl.game.models.GameState

/**
 * Zen Mode - Relaxing endless mode with detailed statistics
 */
class ZenMode : GameMode("Zen") {
    
    private var startTime = 0L
    private var piecesPlaced = 0
    private var tSpins = 0
    private var tetrises = 0
    private var perfectClears = 0
    private var maxCombo = 0
    private var totalRotations = 0
    private var totalMoves = 0
    
    override fun initialize(engine: com.tetris.modern.rl.game.TetrisEngine) {
        super.initialize(engine)
        startTime = System.currentTimeMillis()
        resetStatistics()
    }
    
    private fun resetStatistics() {
        piecesPlaced = 0
        tSpins = 0
        tetrises = 0
        perfectClears = 0
        maxCombo = 0
        totalRotations = 0
        totalMoves = 0
    }
    
    override fun update(deltaTime: Long, gameState: GameState) {
        // Update max combo
        if (gameState.combo > maxCombo) {
            maxCombo = gameState.combo
        }
        
        // Track statistics from game state
        gameState.statistics["tspins"]?.let { tSpins = it }
        gameState.statistics["tetrises"]?.let { tetrises = it }
    }
    
    override fun handleLineClears(lines: Int, isTSpin: Boolean) {
        // Track special moves
        if (isTSpin) {
            tSpins++
        }
        if (lines == 4) {
            tetrises++
        }
        
        // No win condition in Zen mode - play forever
    }
    
    override fun getObjective(): String {
        return "Relax and play without pressure. Track your statistics!"
    }
    
    override fun getModeUI(): Map<String, String> {
        val timeElapsed = (System.currentTimeMillis() - startTime) / 1000
        val piecesPerMinute = if (timeElapsed > 0) (piecesPlaced * 60) / timeElapsed else 0
        
        return mapOf(
            "Time" to formatTime(timeElapsed),
            "PPM" to "$piecesPerMinute",
            "T-Spins" to "$tSpins",
            "Tetrises" to "$tetrises",
            "Max Combo" to "$maxCombo"
        )
    }
    
    override fun getDescription(): String {
        return "No pressure, no timer. Play at your own pace and track detailed statistics. Perfect for practice and relaxation."
    }
    
    fun getDetailedStatistics(): Map<String, Any> {
        val timeElapsed = (System.currentTimeMillis() - startTime) / 1000
        return mapOf(
            "Duration" to formatTime(timeElapsed),
            "Pieces Placed" to piecesPlaced,
            "Pieces Per Minute" to if (timeElapsed > 0) (piecesPlaced * 60) / timeElapsed else 0,
            "T-Spins" to tSpins,
            "Tetrises" to tetrises,
            "Perfect Clears" to perfectClears,
            "Max Combo" to maxCombo,
            "Total Rotations" to totalRotations,
            "Total Moves" to totalMoves,
            "Average APM" to if (timeElapsed > 0) ((totalRotations + totalMoves) * 60) / timeElapsed else 0
        )
    }
    
    private fun formatTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, secs)
        } else {
            String.format("%02d:%02d", minutes, secs)
        }
    }
}