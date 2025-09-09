package com.tetris.modern.rl.game.modes

import com.tetris.modern.rl.game.models.GameState

/**
 * Local 2-player battle mode - DESKTOP ONLY
 * This mode is not available on mobile due to screen space constraints
 */
class Battle2PMode : GameMode("Battle 2P") {
    
    override fun update(deltaTime: Long, gameState: GameState) {
        // Not implemented on mobile
    }
    
    override fun handleLineClears(lines: Int, isTSpin: Boolean) {
        // Not implemented on mobile
    }
    
    override fun getObjective(): String {
        return "Local 2-player battle - Desktop only"
    }
    
    override fun getDescription(): String {
        return "Battle against another player locally with split-screen. This mode requires a larger screen and is only available on desktop."
    }
    
    /**
     * This mode is NOT available on mobile devices
     */
    override fun isAvailableOnMobile(): Boolean {
        return false // Disabled for mobile
    }
}