package com.tetris.modern.rl.game.models

/**
 * Represents the current state of the game
 */
data class GameState(
    val score: Int = 0,
    val lines: Int = 0,
    val level: Int = 1,
    val combo: Int = 0,
    val isPlaying: Boolean = false,
    val isPaused: Boolean = false,
    val isGameOver: Boolean = false,
    val gameMode: String = "classic",
    val heldPiece: PieceType? = null,
    val nextPieces: List<PieceType> = emptyList(),
    val statistics: Map<String, Int> = emptyMap(),
    val gameDuration: Long = 0L,
    val piecesPlaced: Int = 0,
    val isVictory: Boolean = false,
    
    // Mode-specific data
    val targetLines: Int? = null, // For Sprint/Marathon modes
    val remainingTime: Long? = null, // For time-limited modes
    val currentRound: Int? = null, // For Battle mode
    val opponentLines: Int? = null, // For Battle/2P modes
    val powerUps: List<PowerUpType>? = null, // For PowerUp mode
    val puzzleId: Int? = null, // For Puzzle mode
    val puzzleObjective: String? = null, // For Puzzle mode
    val modeInfo: Map<String, String> = emptyMap(), // Mode-specific display info
    val modeObjective: String = "" // Mode objective description
)

/**
 * Power-up types for PowerUp mode
 */
enum class PowerUpType {
    SLOW_TIME,
    LINE_BOMB,
    GHOST_MODE,
    LIGHTNING,
    PRECISION,
    DOUBLE_SCORE,
    SHUFFLE,
    MAGNET
}