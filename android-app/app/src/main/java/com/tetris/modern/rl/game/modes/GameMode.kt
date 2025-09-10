package com.tetris.modern.rl.game.modes

import com.tetris.modern.rl.game.TetrisEngine
import com.tetris.modern.rl.game.models.GameState

/**
 * Abstract base class for all game modes
 */
abstract class GameMode(val name: String) {
    
    protected var engine: TetrisEngine? = null
    
    /**
     * Initialize the game mode with the engine
     */
    open fun initialize(engine: TetrisEngine) {
        this.engine = engine
    }
    
    /**
     * Update the game mode logic
     */
    abstract fun update(deltaTime: Long, gameState: GameState)
    
    /**
     * Handle line clears specific to this mode
     */
    abstract fun handleLineClears(lines: Int, isTSpin: Boolean)
    
    /**
     * Get the objective text for this mode
     */
    abstract fun getObjective(): String
    
    /**
     * Get mode-specific UI elements
     */
    open fun getModeUI(): Map<String, String> {
        return emptyMap()
    }
    
    /**
     * Check if the mode has a win condition and if it's met
     */
    open fun checkWinCondition(gameState: GameState): Boolean {
        return false
    }
    
    /**
     * Handle game over for this mode
     */
    open fun onGameOver(gameState: GameState) {
        // Override in subclasses for mode-specific game over handling
    }
    
    /**
     * Get the description of this mode
     */
    abstract fun getDescription(): String
    
    /**
     * Check if this mode is available on mobile
     */
    open fun isAvailableOnMobile(): Boolean {
        return true
    }
}