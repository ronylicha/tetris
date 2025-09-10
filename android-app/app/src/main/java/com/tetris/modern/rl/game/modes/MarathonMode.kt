package com.tetris.modern.rl.game.modes

import com.tetris.modern.rl.game.models.GameState

/**
 * Marathon Mode - Survive 150 lines with increasing difficulty
 */
class MarathonMode : GameMode("Marathon") {
    
    companion object {
        const val TARGET_LINES = 150
        val CHECKPOINTS = listOf(25, 50, 75, 100, 125, 150)
    }
    
    private var linesCleared = 0
    private var currentCheckpoint = 0
    private var difficultyMultiplier = 1.0f
    
    override fun initialize(engine: com.tetris.modern.rl.game.TetrisEngine) {
        super.initialize(engine)
        linesCleared = 0
        currentCheckpoint = 0
        difficultyMultiplier = 1.0f
    }
    
    override fun update(deltaTime: Long, gameState: GameState) {
        // Difficulty increases at checkpoints
        val nextCheckpoint = CHECKPOINTS.getOrNull(currentCheckpoint)
        if (nextCheckpoint != null && linesCleared >= nextCheckpoint) {
            currentCheckpoint++
            difficultyMultiplier += 0.2f
            // Increase game speed
        }
    }
    
    override fun handleLineClears(lines: Int, isTSpin: Boolean) {
        linesCleared += lines
        
        // Check win condition
        if (linesCleared >= TARGET_LINES) {
            // Marathon completed!
            engine?.triggerVictory()
        }
        
        // Bonus points for reaching checkpoints
        val checkpoint = CHECKPOINTS.find { it == linesCleared }
        if (checkpoint != null) {
            // Award checkpoint bonus
        }
    }
    
    override fun getObjective(): String {
        return "Survive 150 lines with increasing difficulty"
    }
    
    override fun getModeUI(): Map<String, String> {
        val progress = "$linesCleared / $TARGET_LINES"
        val nextCheckpoint = CHECKPOINTS.firstOrNull { it > linesCleared } ?: TARGET_LINES
        return mapOf(
            "Progress" to progress,
            "Next Checkpoint" to "$nextCheckpoint lines",
            "Difficulty" to "×${String.format("%.1f", difficultyMultiplier)}"
        )
    }
    
    override fun checkWinCondition(gameState: GameState): Boolean {
        return linesCleared >= TARGET_LINES
    }
    
    override fun getDescription(): String {
        return "A true test of endurance. Clear 150 lines through increasingly difficult checkpoints."
    }
}