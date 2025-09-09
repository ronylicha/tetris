package com.tetris.modern.rl.game.modes

import com.tetris.modern.rl.ai.TetrisAI
import com.tetris.modern.rl.game.models.GameState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Battle Mode - Face off against AI opponents
 */
class BattleMode : GameMode("Battle") {
    
    enum class AIDifficulty(val level: Int, val difficultyName: String, val speed: Float) {
        EASY(1, "Easy", 0.5f),
        NORMAL(2, "Normal", 0.7f),
        HARD(3, "Hard", 0.85f),
        EXPERT(4, "Expert", 0.95f),
        MASTER(5, "Master", 1.0f)
    }
    
    private var aiOpponent: TetrisAI? = null
    private var aiDifficulty = AIDifficulty.NORMAL
    private var playerGarbageLines = 0
    private var aiGarbageLines = 0
    private var currentRound = 1
    private var playerWins = 0
    private var aiWins = 0
    private val maxRounds = 3
    
    fun setDifficulty(difficulty: AIDifficulty) {
        aiDifficulty = difficulty
        aiOpponent = TetrisAI(difficulty.level)
    }
    
    override fun initialize(engine: com.tetris.modern.rl.game.TetrisEngine) {
        super.initialize(engine)
        aiOpponent = TetrisAI(aiDifficulty.level)
        resetBattle()
    }
    
    private fun resetBattle() {
        playerGarbageLines = 0
        aiGarbageLines = 0
        currentRound = 1
        playerWins = 0
        aiWins = 0
    }
    
    override fun update(deltaTime: Long, gameState: GameState) {
        // Update AI opponent
        CoroutineScope(Dispatchers.Default).launch {
            aiOpponent?.update(deltaTime)
        }
        
        // Check if AI cleared lines
        aiOpponent?.getClearedLines()?.let { aiLines ->
            if (aiLines > 0) {
                // Send garbage to player
                val garbage = calculateGarbage(aiLines)
                playerGarbageLines += garbage
                applyGarbageToPlayer(garbage)
            }
        }
        
        // Check round end conditions
        if (aiOpponent?.isGameOver() == true) {
            playerWins++
            endRound()
        }
    }
    
    override fun handleLineClears(lines: Int, isTSpin: Boolean) {
        // Calculate garbage to send to AI
        var garbage = calculateGarbage(lines)
        
        // Bonus garbage for special moves
        if (isTSpin) {
            garbage += 2
        }
        if (lines == 4) { // Tetris
            garbage += 1
        }
        
        aiGarbageLines += garbage
        aiOpponent?.receiveGarbage(garbage)
    }
    
    private fun calculateGarbage(lines: Int): Int {
        return when (lines) {
            1 -> 0
            2 -> 1
            3 -> 2
            4 -> 4
            else -> lines
        }
    }
    
    private fun applyGarbageToPlayer(lines: Int) {
        // Add garbage lines to player's grid
        engine?.let { eng ->
            // Implementation would add gray lines to bottom of grid
        }
    }
    
    private fun endRound() {
        if (currentRound >= maxRounds || playerWins > maxRounds / 2 || aiWins > maxRounds / 2) {
            // Battle complete
            if (playerWins > aiWins) {
                onBattleWon()
            } else {
                onBattleLost()
            }
        } else {
            // Start next round
            currentRound++
            startNewRound()
        }
    }
    
    private fun startNewRound() {
        playerGarbageLines = 0
        aiGarbageLines = 0
        aiOpponent?.reset()
    }
    
    private fun onBattleWon() {
        // Handle victory
    }
    
    private fun onBattleLost() {
        // Handle defeat
    }
    
    override fun getObjective(): String {
        return "Defeat the AI opponent - Best of $maxRounds"
    }
    
    override fun getModeUI(): Map<String, String> {
        return mapOf(
            "Round" to "$currentRound / $maxRounds",
            "Score" to "$playerWins - $aiWins",
            "AI" to aiDifficulty.name,
            "Garbage Sent" to "$aiGarbageLines",
            "Garbage Received" to "$playerGarbageLines"
        )
    }
    
    override fun getDescription(): String {
        return "Battle against AI opponents of varying difficulty. Send garbage lines and outlast your opponent!"
    }
}