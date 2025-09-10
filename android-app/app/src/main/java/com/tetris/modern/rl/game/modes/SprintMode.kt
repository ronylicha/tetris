package com.tetris.modern.rl.game.modes

import com.tetris.modern.rl.game.models.GameState

/**
 * Sprint Mode - Clear 40 lines as fast as possible
 */
class SprintMode : GameMode("Sprint") {
    
    companion object {
        const val TARGET_LINES = 40
    }
    
    private var startTime = 0L
    private var linesCleared = 0
    
    override fun initialize(engine: com.tetris.modern.rl.game.TetrisEngine) {
        super.initialize(engine)
        startTime = System.currentTimeMillis()
        linesCleared = 0
    }
    
    override fun update(deltaTime: Long, gameState: GameState) {
        // Track lines from game state
        linesCleared = gameState.lines
        
        // Check win condition
        if (linesCleared >= TARGET_LINES && !gameState.isGameOver) {
            val completionTime = System.currentTimeMillis() - startTime
            // Trigger victory!
            engine?.triggerVictory()
        }
    }
    
    override fun handleLineClears(lines: Int, isTSpin: Boolean) {
        // Lines are tracked through game state, no need to update here
    }
    
    override fun getObjective(): String {
        return "Clear 40 lines as fast as possible!"
    }
    
    override fun getModeUI(): Map<String, String> {
        val remaining = maxOf(0, TARGET_LINES - linesCleared)
        val elapsedTime = if (startTime > 0) {
            (System.currentTimeMillis() - startTime) / 1000
        } else {
            0L
        }
        return mapOf(
            "Lines Left" to "$remaining / $TARGET_LINES",
            "Time" to formatTime(elapsedTime)
        )
    }
    
    override fun checkWinCondition(gameState: GameState): Boolean {
        return linesCleared >= TARGET_LINES
    }
    
    override fun getDescription(): String {
        return "Race against the clock to clear 40 lines. Perfect for quick competitive sessions."
    }
    
    private fun formatTime(seconds: Long): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }
}