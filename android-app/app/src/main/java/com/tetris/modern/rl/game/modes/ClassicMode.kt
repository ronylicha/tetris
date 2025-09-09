package com.tetris.modern.rl.game.modes

import com.tetris.modern.rl.game.models.GameState

/**
 * Classic endless Tetris mode
 */
class ClassicMode : GameMode("Classic") {
    
    private var linesClearedThisLevel = 0
    private val linesPerLevel = 10
    
    override fun update(deltaTime: Long, gameState: GameState) {
        // Classic mode doesn't have special update logic
    }
    
    override fun handleLineClears(lines: Int, isTSpin: Boolean) {
        linesClearedThisLevel += lines
        
        // Check for level up
        if (linesClearedThisLevel >= linesPerLevel) {
            linesClearedThisLevel -= linesPerLevel
            // Level up is handled in the engine based on total lines
        }
    }
    
    override fun getObjective(): String {
        return "Endless mode - Survive as long as possible!"
    }
    
    override fun getModeUI(): Map<String, String> {
        val gameState = engine?.gameState?.value
        val nextLevelLines = ((gameState?.level ?: 1) * linesPerLevel) - (gameState?.lines ?: 0)
        return mapOf(
            "Mode" to "Endless",
            "Next Level" to "$nextLevelLines lines"
        )
    }
    
    override fun getDescription(): String {
        return "The classic Tetris experience. Clear lines to increase your level and score. The game speeds up as you progress."
    }
}