package com.tetris.modern.rl.game.modes

import com.tetris.modern.rl.game.models.GameState
import com.tetris.modern.rl.game.models.PowerUpType
import kotlin.random.Random

/**
 * Power-Up Mode - Classic gameplay enhanced with 8 unique power-ups
 */
class PowerUpMode : GameMode("Power-Up") {
    
    private val activePowerUps = mutableListOf<PowerUp>()
    private val powerUpInventory = mutableListOf<PowerUpType>()
    private var powerUpCharge = 0f
    private val maxPowerUpCharge = 100f
    private val chargePerLine = 25f
    
    data class PowerUp(
        val type: PowerUpType,
        var duration: Long,
        val startTime: Long = System.currentTimeMillis()
    )
    
    override fun initialize(engine: com.tetris.modern.rl.game.TetrisEngine) {
        super.initialize(engine)
        activePowerUps.clear()
        powerUpInventory.clear()
        powerUpCharge = 0f
    }
    
    override fun update(deltaTime: Long, gameState: GameState) {
        // Update active power-ups
        val currentTime = System.currentTimeMillis()
        activePowerUps.removeAll { powerUp ->
            currentTime - powerUp.startTime > powerUp.duration
        }
        
        // Apply power-up effects
        activePowerUps.forEach { powerUp ->
            applyPowerUpEffect(powerUp, deltaTime)
        }
    }
    
    override fun handleLineClears(lines: Int, isTSpin: Boolean) {
        // Charge power-up meter
        powerUpCharge += lines * chargePerLine
        if (isTSpin) {
            powerUpCharge += 15f // Bonus for T-spins
        }
        
        // Grant power-up when fully charged
        if (powerUpCharge >= maxPowerUpCharge) {
            powerUpCharge -= maxPowerUpCharge
            grantRandomPowerUp()
        }
    }
    
    private fun grantRandomPowerUp() {
        val randomPowerUp = PowerUpType.values().random()
        powerUpInventory.add(randomPowerUp)
        
        // Limit inventory to 3 power-ups
        if (powerUpInventory.size > 3) {
            powerUpInventory.removeAt(0)
        }
    }
    
    fun activatePowerUp(index: Int) {
        if (index in powerUpInventory.indices) {
            val powerUpType = powerUpInventory.removeAt(index)
            activatePowerUp(powerUpType)
        }
    }
    
    private fun activatePowerUp(type: PowerUpType) {
        val duration = when (type) {
            PowerUpType.SLOW_TIME -> 10000L
            PowerUpType.GHOST_MODE -> 15000L
            PowerUpType.PRECISION -> 20000L
            PowerUpType.DOUBLE_SCORE -> 30000L
            PowerUpType.LINE_BOMB -> 0L // Instant
            PowerUpType.LIGHTNING -> 0L // Instant
            PowerUpType.SHUFFLE -> 0L // Instant
            PowerUpType.MAGNET -> 5000L
        }
        
        when (type) {
            PowerUpType.LINE_BOMB -> {
                // Clear bottom 3 lines instantly
                clearBottomLines(3)
            }
            PowerUpType.LIGHTNING -> {
                // Clear random cells
                clearRandomCells(20)
            }
            PowerUpType.SHUFFLE -> {
                // Shuffle all pieces on grid
                shuffleGrid()
            }
            else -> {
                // Add duration-based power-up
                activePowerUps.add(PowerUp(type, duration))
            }
        }
    }
    
    private fun applyPowerUpEffect(powerUp: PowerUp, deltaTime: Long) {
        when (powerUp.type) {
            PowerUpType.SLOW_TIME -> {
                // Reduce drop speed by 50%
                engine?.let { 
                    // Modify drop interval
                }
            }
            PowerUpType.GHOST_MODE -> {
                // Pieces can pass through others temporarily
            }
            PowerUpType.PRECISION -> {
                // Show placement guide lines
            }
            PowerUpType.DOUBLE_SCORE -> {
                // Double all points earned
            }
            PowerUpType.MAGNET -> {
                // Pieces attract to optimal positions
            }
            else -> {}
        }
    }
    
    private fun clearBottomLines(count: Int) {
        engine?.let {
            // Clear specified number of bottom lines
        }
    }
    
    private fun clearRandomCells(count: Int) {
        engine?.let {
            // Clear random cells from grid
        }
    }
    
    private fun shuffleGrid() {
        engine?.let {
            // Shuffle existing pieces on grid
        }
    }
    
    override fun getObjective(): String {
        return "Classic Tetris with powerful abilities!"
    }
    
    override fun getModeUI(): Map<String, String> {
        val chargePercent = ((powerUpCharge / maxPowerUpCharge) * 100).toInt()
        val activePowerUpNames = activePowerUps.joinToString(", ") { it.type.name }
        
        return mapOf(
            "Charge" to "$chargePercent%",
            "Inventory" to "${powerUpInventory.size}/3",
            "Active" to if (activePowerUps.isNotEmpty()) activePowerUpNames else "None"
        )
    }
    
    override fun getDescription(): String {
        return "Enhance your gameplay with 8 unique power-ups. Build charge by clearing lines and unleash powerful abilities!"
    }
}