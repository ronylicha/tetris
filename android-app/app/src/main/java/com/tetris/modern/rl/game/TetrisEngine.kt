package com.tetris.modern.rl.game

import com.tetris.modern.rl.audio.AudioManager
import com.tetris.modern.rl.game.models.GameState
import com.tetris.modern.rl.game.models.Piece
import com.tetris.modern.rl.game.models.PieceType
import com.tetris.modern.rl.game.modes.GameMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max
import kotlin.math.min

@Singleton
class TetrisEngine @Inject constructor(
    private val audioManager: AudioManager
) {
    
    companion object {
        const val GRID_WIDTH = 10
        const val GRID_HEIGHT = 20
        const val HIDDEN_ROWS = 4 // Extra rows above visible area for piece spawning
        const val TOTAL_HEIGHT = GRID_HEIGHT + HIDDEN_ROWS
        
        const val INITIAL_DROP_INTERVAL = 1000L // 1 second
        const val MIN_DROP_INTERVAL = 50L
        const val LOCK_DELAY = 500L
        const val MAX_LOCK_MOVES = 15
        
        const val POINTS_SINGLE = 100
        const val POINTS_DOUBLE = 300
        const val POINTS_TRIPLE = 500
        const val POINTS_TETRIS = 800
        const val POINTS_T_SPIN = 400
        const val POINTS_T_SPIN_SINGLE = 800
        const val POINTS_T_SPIN_DOUBLE = 1200
        const val POINTS_T_SPIN_TRIPLE = 1600
        const val POINTS_PERFECT_CLEAR = 3000
        const val POINTS_COMBO_MULTIPLIER = 50
    }
    
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()
    
    private val grid = Grid(GRID_WIDTH, TOTAL_HEIGHT)
    private val pieceBag = PieceBag()
    private val tSpinDetector = TSpinDetector()
    
    private var gameLoopJob: Job? = null
    private var dropTimer = 0L
    private var lockTimer = 0L
    private var lockMoveCount = 0
    private var lastPiecePosition: Pair<Int, Int>? = null
    private var gameStartTime = 0L
    
    private var currentPiece: Piece? = null
    private var heldPiece: PieceType? = null
    private var canHold = true
    
    private var gameMode: GameMode? = null
    
    fun startGame(mode: GameMode) {
        Timber.d("Starting game with mode: ${mode.name}")
        
        gameMode = mode
        resetGame()
        
        mode.initialize(this)
        gameStartTime = System.currentTimeMillis()
        
        _gameState.value = _gameState.value.copy(
            isPlaying = true,
            isPaused = false,
            gameMode = mode.name,
            modeObjective = mode.getObjective(),
            modeInfo = mode.getModeUI()
        )
        startGameLoop()
    }
    
    private fun resetGame() {
        grid.clear()
        pieceBag.reset()
        
        currentPiece = null
        heldPiece = null
        canHold = true
        
        dropTimer = 0L
        lockTimer = 0L
        lockMoveCount = 0
        lastPiecePosition = null
        
        _gameState.value = GameState()
    }
    
    private fun startGameLoop() {
        gameLoopJob?.cancel()
        gameLoopJob = CoroutineScope(Dispatchers.Default).launch {
            var lastTime = System.nanoTime()
            val frameTimeNanos = 1_000_000_000L / 60 // 16.67ms for 60 FPS
            
            while (_gameState.value.isPlaying) {
                val currentTime = System.nanoTime()
                val deltaTimeNanos = currentTime - lastTime
                
                if (!_gameState.value.isPaused && deltaTimeNanos >= frameTimeNanos) {
                    val deltaTimeMs = deltaTimeNanos / 1_000_000L
                    withContext(Dispatchers.Main) {
                        update(deltaTimeMs)
                    }
                    lastTime = currentTime
                } else {
                    // Precise timing to maintain 60 FPS
                    val sleepTime = (frameTimeNanos - deltaTimeNanos) / 1_000_000L
                    if (sleepTime > 0) {
                        delay(sleepTime.coerceAtMost(16))
                    } else {
                        yield() // Let other coroutines run
                    }
                }
            }
        }
    }
    
    private fun update(deltaTime: Long) {
        if (currentPiece == null) {
            spawnNewPiece()
        }
        
        currentPiece?.let { piece ->
            // Handle gravity
            dropTimer += deltaTime
            val dropInterval = calculateDropInterval()
            
            if (dropTimer >= dropInterval) {
                dropTimer = 0L
                
                if (canMoveDown(piece)) {
                    piece.y++
                    lockTimer = 0L
                    
                    // Check if piece position changed significantly
                    val currentPos = Pair(piece.x, piece.y)
                    if (currentPos != lastPiecePosition) {
                        lockMoveCount = 0
                        lastPiecePosition = currentPos
                    }
                } else {
                    // Piece can't move down, start lock delay
                    // Lock timer accumulates outside the drop interval check
                }
            }
            
            // Handle lock delay when piece is on ground
            if (!canMoveDown(piece)) {
                lockTimer += deltaTime
                
                if (lockTimer >= LOCK_DELAY) {
                    lockPiece(piece)
                }
            }
        }
        
        // Update game mode
        gameMode?.update(deltaTime, _gameState.value)
        
        // Update mode-specific info
        gameMode?.let { mode ->
            _gameState.value = _gameState.value.copy(
                modeInfo = mode.getModeUI(),
                modeObjective = mode.getObjective()
            )
        }
    }
    
    private fun calculateDropInterval(): Long {
        val level = _gameState.value.level
        val baseInterval = INITIAL_DROP_INTERVAL
        val speedMultiplier = 0.8f.pow(level - 1)
        return max(MIN_DROP_INTERVAL, (baseInterval * speedMultiplier).toLong())
    }
    
    private fun spawnNewPiece() {
        val nextType = pieceBag.getNext()
        currentPiece = Piece(
            type = nextType,
            x = GRID_WIDTH / 2 - 1,
            y = HIDDEN_ROWS - 2, // Spawn in hidden area
            rotation = 0
        )
        
        // Update next pieces display
        val nextPieces = pieceBag.preview(5)
        _gameState.value = _gameState.value.copy(nextPieces = nextPieces)
        
        // Check if spawn is blocked (game over)
        currentPiece?.let { piece ->
            if (!isValidPosition(piece)) {
                gameOver()
            }
        }
        
        canHold = true
    }
    
    fun moveLeft() {
        currentPiece?.let { piece ->
            val newPiece = piece.copy(x = piece.x - 1)
            if (isValidPosition(newPiece)) {
                currentPiece = newPiece
                audioManager.playSound(AudioManager.SOUND_MOVE)
                resetLockIfNeeded()
            }
        }
    }
    
    fun moveRight() {
        currentPiece?.let { piece ->
            val newPiece = piece.copy(x = piece.x + 1)
            if (isValidPosition(newPiece)) {
                currentPiece = newPiece
                audioManager.playSound(AudioManager.SOUND_MOVE)
                resetLockIfNeeded()
            }
        }
    }
    
    fun rotateClockwise() {
        currentPiece?.let { piece ->
            val newRotation = (piece.rotation + 1) % 4
            val rotatedPiece = piece.copy(rotation = newRotation)
            
            // Try rotation with wall kicks
            val kickedPiece = tryWallKicks(piece, rotatedPiece)
            if (kickedPiece != null) {
                currentPiece = kickedPiece
                audioManager.playSound(AudioManager.SOUND_ROTATE)
                resetLockIfNeeded()
            }
        }
    }
    
    fun rotateCounterClockwise() {
        currentPiece?.let { piece ->
            val newRotation = (piece.rotation + 3) % 4 // +3 is same as -1 mod 4
            val rotatedPiece = piece.copy(rotation = newRotation)
            
            // Try rotation with wall kicks
            val kickedPiece = tryWallKicks(piece, rotatedPiece)
            if (kickedPiece != null) {
                currentPiece = kickedPiece
                resetLockIfNeeded()
            }
        }
    }
    
    fun softDrop() {
        currentPiece?.let { piece ->
            if (canMoveDown(piece)) {
                piece.y++
                _gameState.value = _gameState.value.copy(
                    score = _gameState.value.score + 1
                )
            }
        }
    }
    
    fun hardDrop() {
        currentPiece?.let { piece ->
            var dropDistance = 0
            while (canMoveDown(piece)) {
                piece.y++
                dropDistance++
            }
            
            _gameState.value = _gameState.value.copy(
                score = _gameState.value.score + (dropDistance * 2)
            )
            
            audioManager.playSound(AudioManager.SOUND_DROP)
            lockPiece(piece)
        }
    }
    
    fun holdPiece() {
        if (!canHold) return
        
        currentPiece?.let { piece ->
            val pieceToHold = piece.type
            
            if (heldPiece != null) {
                // Swap with held piece
                currentPiece = Piece(
                    type = heldPiece!!,
                    x = GRID_WIDTH / 2 - 1,
                    y = HIDDEN_ROWS - 2,
                    rotation = 0
                )
            } else {
                // Hold current piece and spawn new one
                currentPiece = null
                spawnNewPiece()
            }
            
            heldPiece = pieceToHold
            canHold = false
            audioManager.playSound(AudioManager.SOUND_HOLD)
            
            _gameState.value = _gameState.value.copy(heldPiece = heldPiece)
        }
    }
    
    private fun canMoveDown(piece: Piece): Boolean {
        val testPiece = piece.copy(y = piece.y + 1)
        return isValidPosition(testPiece)
    }
    
    private fun isValidPosition(piece: Piece): Boolean {
        val shape = piece.getCurrentShape()
        
        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] != 0) {
                    val gridX = piece.x + col
                    val gridY = piece.y + row
                    
                    // Check bounds
                    if (gridX < 0 || gridX >= GRID_WIDTH || 
                        gridY >= TOTAL_HEIGHT) {
                        return false
                    }
                    
                    // Check collision with placed pieces
                    if (gridY >= 0 && grid.getCell(gridX, gridY) != null) {
                        return false
                    }
                }
            }
        }
        
        return true
    }
    
    private fun tryWallKicks(originalPiece: Piece, rotatedPiece: Piece): Piece? {
        // First try the basic rotation
        if (isValidPosition(rotatedPiece)) {
            return rotatedPiece
        }
        
        // Get wall kick offsets based on piece type and rotation
        val wallKicks = getWallKickOffsets(originalPiece.type, originalPiece.rotation, rotatedPiece.rotation)
        
        for ((offsetX, offsetY) in wallKicks) {
            val kickedPiece = rotatedPiece.copy(
                x = rotatedPiece.x + offsetX,
                y = rotatedPiece.y + offsetY
            )
            
            if (isValidPosition(kickedPiece)) {
                return kickedPiece
            }
        }
        
        return null
    }
    
    private fun getWallKickOffsets(type: PieceType, fromRotation: Int, toRotation: Int): List<Pair<Int, Int>> {
        // Simplified SRS wall kick data
        return when (type) {
            PieceType.I -> {
                // I-piece has special wall kicks
                listOf(
                    Pair(-2, 0), Pair(1, 0), Pair(-2, -1), Pair(1, 2)
                )
            }
            PieceType.O -> {
                // O-piece doesn't need wall kicks (doesn't rotate)
                emptyList()
            }
            else -> {
                // Standard SRS wall kicks for J, L, S, T, Z
                listOf(
                    Pair(-1, 0), Pair(-1, 1), Pair(0, -2), Pair(-1, -2)
                )
            }
        }
    }
    
    private fun resetLockIfNeeded() {
        if (lockTimer > 0 && lockMoveCount < MAX_LOCK_MOVES) {
            lockTimer = 0L
            lockMoveCount++
        }
    }
    
    private fun lockPiece(piece: Piece) {
        // Place piece on grid
        grid.placePiece(piece)
        
        // Check for T-spin
        val isTSpin = tSpinDetector.detectTSpin(piece, grid)
        val isTSpinMini = tSpinDetector.detectTSpinMini(piece, grid)
        
        // Clear completed lines
        val clearedLines = grid.clearLines()
        
        // Calculate score
        val score = calculateScore(clearedLines.size, isTSpin, isTSpinMini)
        
        // Update statistics
        updateStatistics(clearedLines.size, isTSpin, isTSpinMini)
        
        // Check for perfect clear
        if (grid.isPerfectClear()) {
            _gameState.value = _gameState.value.copy(
                score = _gameState.value.score + POINTS_PERFECT_CLEAR
            )
        }
        
        // Update combo and play sounds with vibration feedback
        if (clearedLines.isNotEmpty()) {
            _gameState.value = _gameState.value.copy(
                combo = _gameState.value.combo + 1,
                score = _gameState.value.score + score + (_gameState.value.combo * POINTS_COMBO_MULTIPLIER)
            )
            
            // Play appropriate sound and vibration feedback
            when {
                isTSpin -> {
                    audioManager.playTSpinFeedback()
                }
                else -> {
                    audioManager.playLineClearFeedback(clearedLines.size)
                }
            }
            
            if (_gameState.value.combo > 3) {
                audioManager.playComboFeedback(_gameState.value.combo)
            }
        } else {
            _gameState.value = _gameState.value.copy(combo = 0)
        }
        
        // Update level
        val newLines = _gameState.value.lines + clearedLines.size
        val newLevel = (newLines / 10) + 1
        
        // Play level up sound and adjust music tempo if level increased
        if (newLevel > _gameState.value.level) {
            audioManager.playSound(AudioManager.SOUND_LEVEL_UP)
            // Adjust music tempo based on level (2% faster per level)
            adjustMusicTempo(newLevel)
        }
        
        _gameState.value = _gameState.value.copy(
            lines = newLines,
            level = newLevel,
            piecesPlaced = _gameState.value.piecesPlaced + 1
        )
        
        Timber.d("Lines cleared: ${clearedLines.size}, Total lines: $newLines, Level: $newLevel")
        
        // Let game mode handle line clears
        gameMode?.handleLineClears(clearedLines.size, isTSpin)
        
        // Spawn next piece
        currentPiece = null
        lockTimer = 0L
        lockMoveCount = 0
        canHold = true
        
        // Force spawn next piece immediately
        spawnNewPiece()
    }
    
    private fun calculateScore(lines: Int, isTSpin: Boolean, isTSpinMini: Boolean): Int {
        val level = _gameState.value.level
        
        return when {
            isTSpin -> when (lines) {
                0 -> POINTS_T_SPIN
                1 -> POINTS_T_SPIN_SINGLE
                2 -> POINTS_T_SPIN_DOUBLE
                3 -> POINTS_T_SPIN_TRIPLE
                else -> 0
            }
            isTSpinMini -> POINTS_T_SPIN / 2
            else -> when (lines) {
                1 -> POINTS_SINGLE
                2 -> POINTS_DOUBLE
                3 -> POINTS_TRIPLE
                4 -> POINTS_TETRIS
                else -> 0
            }
        } * level
    }
    
    private fun updateStatistics(lines: Int, isTSpin: Boolean, isTSpinMini: Boolean) {
        val stats = _gameState.value.statistics.toMutableMap()
        
        if (isTSpin) {
            stats["tspins"] = (stats["tspins"] ?: 0) + 1
            Timber.d("T-Spin detected! Total T-Spins: ${stats["tspins"]}")
        }
        
        if (isTSpinMini) {
            stats["tspinMinis"] = (stats["tspinMinis"] ?: 0) + 1
        }
        
        if (lines == 4) {
            stats["tetrises"] = (stats["tetrises"] ?: 0) + 1
            Timber.d("Tetris! Total Tetrises: ${stats["tetrises"]}")
        }
        
        if (_gameState.value.combo > (stats["maxCombo"] ?: 0)) {
            stats["maxCombo"] = _gameState.value.combo
            Timber.d("New max combo: ${stats["maxCombo"]}")
        }
        
        // Track perfect clears
        if (grid.isPerfectClear()) {
            stats["perfectClears"] = (stats["perfectClears"] ?: 0) + 1
            Timber.d("Perfect clear! Total: ${stats["perfectClears"]}")
        }
        
        _gameState.value = _gameState.value.copy(statistics = stats)
        Timber.d("Statistics updated - Tetrises: ${stats["tetrises"]}, T-Spins: ${stats["tspins"]}, Max Combo: ${stats["maxCombo"]}")
    }
    
    fun pauseGame() {
        _gameState.value = _gameState.value.copy(isPaused = true)
        audioManager.playSound(AudioManager.SOUND_PAUSE)
        audioManager.pauseMusic()
    }
    
    fun resumeGame() {
        // Force a state update to trigger UI recomposition
        currentPiece?.let { piece ->
            // Update the state with a new piece reference to force UI update
            _gameState.value = _gameState.value.copy(
                isPaused = false,
                // Just toggle the pause state, the piece will update on next frame
                gameDuration = _gameState.value.gameDuration + 1 // Small change to force recomposition
            )
        } ?: run {
            _gameState.value = _gameState.value.copy(isPaused = false)
        }
        audioManager.resumeMusic()
    }
    
    private fun gameOver() {
        val finalStats = _gameState.value.statistics
        Timber.d("Game Over! Final score: ${_gameState.value.score}, Lines: ${_gameState.value.lines}")
        Timber.d("Final Statistics - Tetrises: ${finalStats["tetrises"]}, T-Spins: ${finalStats["tspins"]}, Max Combo: ${finalStats["maxCombo"]}, Perfect Clears: ${finalStats["perfectClears"]}")
        
        gameLoopJob?.cancel()
        
        val gameDuration = System.currentTimeMillis() - gameStartTime
        
        _gameState.value = _gameState.value.copy(
            isPlaying = false,
            isGameOver = true,
            gameDuration = gameDuration
        )
        
        gameMode?.onGameOver(_gameState.value)
    }
    
    fun triggerVictory() {
        Timber.d("Victory! Final score: ${_gameState.value.score}")
        
        gameLoopJob?.cancel()
        
        val gameDuration = System.currentTimeMillis() - gameStartTime
        
        _gameState.value = _gameState.value.copy(
            isPlaying = false,
            isGameOver = true,
            gameDuration = gameDuration,
            isVictory = true
        )
        
        // Play victory sound
        audioManager.playSound(AudioManager.SOUND_LEVEL_UP)
        audioManager.playMusic(AudioManager.MUSIC_VICTORY, loop = false)
        
        gameMode?.onGameOver(_gameState.value)
    }
    
    fun getGridState(): Array<IntArray> {
        return grid.getVisibleGrid()
    }
    
    fun getCurrentPieceState(): Piece? {
        return currentPiece
    }
    
    fun getGhostPiecePosition(): Piece? {
        return currentPiece?.let { piece ->
            var ghostPiece = piece.copy()
            while (canMoveDown(ghostPiece)) {
                ghostPiece = ghostPiece.copy(y = ghostPiece.y + 1)
            }
            ghostPiece
        }
    }
    
    private fun adjustMusicTempo(level: Int) {
        // Adjust music playback speed based on level
        // Start at normal speed (1.0x), increase 2% per level
        val speedMultiplier = 1.0f + ((level - 1) * 0.02f)
        
        // Cap at 1.5x speed (level 26+) to keep it playable
        val finalSpeed = speedMultiplier.coerceAtMost(1.5f)
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                // This would need to be implemented in AudioManager
                // For now, we'll log it
                Timber.d("Music tempo adjusted to ${finalSpeed}x for level $level")
            } catch (e: Exception) {
                Timber.e(e, "Failed to adjust music tempo")
            }
        }
    }
    
    private fun Float.pow(n: Int): Float {
        var result = 1f
        repeat(n) { result *= this }
        return result
    }
}