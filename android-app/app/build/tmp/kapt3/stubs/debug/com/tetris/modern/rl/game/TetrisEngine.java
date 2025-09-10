package com.tetris.modern.rl.game;

import com.tetris.modern.rl.audio.AudioManager;
import com.tetris.modern.rl.game.models.GameState;
import com.tetris.modern.rl.game.models.Piece;
import com.tetris.modern.rl.game.models.PieceType;
import com.tetris.modern.rl.game.modes.GameMode;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\u0011\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u001c\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0007\u0018\u0000 U2\u00020\u0001:\u0001UB\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u001dH\u0002J\b\u0010\'\u001a\u00020\rH\u0002J \u0010(\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020\t2\u0006\u0010+\u001a\u00020\tH\u0002J\u0010\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020\u000bH\u0002J\b\u0010.\u001a\u00020%H\u0002J\b\u0010/\u001a\u0004\u0018\u00010\u000bJ\b\u00100\u001a\u0004\u0018\u00010\u000bJ\u0011\u00101\u001a\b\u0012\u0004\u0012\u00020302\u00a2\u0006\u0002\u00104J2\u00105\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d0\u001c062\u0006\u00107\u001a\u00020\u001a2\u0006\u00108\u001a\u00020\u001d2\u0006\u00109\u001a\u00020\u001dH\u0002J\u0006\u0010:\u001a\u00020%J\u0006\u0010;\u001a\u00020%J\u0010\u0010<\u001a\u00020\t2\u0006\u0010-\u001a\u00020\u000bH\u0002J\u0010\u0010=\u001a\u00020%2\u0006\u0010-\u001a\u00020\u000bH\u0002J\u0006\u0010>\u001a\u00020%J\u0006\u0010?\u001a\u00020%J\u0006\u0010@\u001a\u00020%J\b\u0010A\u001a\u00020%H\u0002J\b\u0010B\u001a\u00020%H\u0002J\u0006\u0010C\u001a\u00020%J\u0006\u0010D\u001a\u00020%J\u0006\u0010E\u001a\u00020%J\u0006\u0010F\u001a\u00020%J\b\u0010G\u001a\u00020%H\u0002J\u000e\u0010H\u001a\u00020%2\u0006\u0010I\u001a\u00020\u0011J\b\u0010J\u001a\u00020%H\u0002J\u0006\u0010K\u001a\u00020%J\u001a\u0010L\u001a\u0004\u0018\u00010\u000b2\u0006\u0010M\u001a\u00020\u000b2\u0006\u0010N\u001a\u00020\u000bH\u0002J\u0010\u0010O\u001a\u00020%2\u0006\u0010P\u001a\u00020\rH\u0002J \u0010Q\u001a\u00020%2\u0006\u0010)\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020\t2\u0006\u0010+\u001a\u00020\tH\u0002J\u0014\u0010R\u001a\u00020S*\u00020S2\u0006\u0010T\u001a\u00020\u001dH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006V"}, d2 = {"Lcom/tetris/modern/rl/game/TetrisEngine;", "", "audioManager", "Lcom/tetris/modern/rl/audio/AudioManager;", "(Lcom/tetris/modern/rl/audio/AudioManager;)V", "_gameState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/tetris/modern/rl/game/models/GameState;", "canHold", "", "currentPiece", "Lcom/tetris/modern/rl/game/models/Piece;", "dropTimer", "", "gameLoopJob", "Lkotlinx/coroutines/Job;", "gameMode", "Lcom/tetris/modern/rl/game/modes/GameMode;", "gameStartTime", "gameState", "Lkotlinx/coroutines/flow/StateFlow;", "getGameState", "()Lkotlinx/coroutines/flow/StateFlow;", "grid", "Lcom/tetris/modern/rl/game/Grid;", "heldPiece", "Lcom/tetris/modern/rl/game/models/PieceType;", "lastPiecePosition", "Lkotlin/Pair;", "", "lockMoveCount", "lockTimer", "pieceBag", "Lcom/tetris/modern/rl/game/PieceBag;", "tSpinDetector", "Lcom/tetris/modern/rl/game/TSpinDetector;", "adjustMusicTempo", "", "level", "calculateDropInterval", "calculateScore", "lines", "isTSpin", "isTSpinMini", "canMoveDown", "piece", "gameOver", "getCurrentPieceState", "getGhostPiecePosition", "getGridState", "", "", "()[[I", "getWallKickOffsets", "", "type", "fromRotation", "toRotation", "hardDrop", "holdPiece", "isValidPosition", "lockPiece", "moveLeft", "moveRight", "pauseGame", "resetGame", "resetLockIfNeeded", "resumeGame", "rotateClockwise", "rotateCounterClockwise", "softDrop", "spawnNewPiece", "startGame", "mode", "startGameLoop", "triggerVictory", "tryWallKicks", "originalPiece", "rotatedPiece", "update", "deltaTime", "updateStatistics", "pow", "", "n", "Companion", "app_debug"})
public final class TetrisEngine {
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.audio.AudioManager audioManager = null;
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 20;
    public static final int HIDDEN_ROWS = 4;
    public static final int TOTAL_HEIGHT = 24;
    public static final long INITIAL_DROP_INTERVAL = 1000L;
    public static final long MIN_DROP_INTERVAL = 50L;
    public static final long LOCK_DELAY = 500L;
    public static final int MAX_LOCK_MOVES = 15;
    public static final int POINTS_SINGLE = 100;
    public static final int POINTS_DOUBLE = 300;
    public static final int POINTS_TRIPLE = 500;
    public static final int POINTS_TETRIS = 800;
    public static final int POINTS_T_SPIN = 400;
    public static final int POINTS_T_SPIN_SINGLE = 800;
    public static final int POINTS_T_SPIN_DOUBLE = 1200;
    public static final int POINTS_T_SPIN_TRIPLE = 1600;
    public static final int POINTS_PERFECT_CLEAR = 3000;
    public static final int POINTS_COMBO_MULTIPLIER = 50;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.tetris.modern.rl.game.models.GameState> _gameState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.game.models.GameState> gameState = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.game.Grid grid = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.game.PieceBag pieceBag = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.game.TSpinDetector tSpinDetector = null;
    @org.jetbrains.annotations.Nullable
    private kotlinx.coroutines.Job gameLoopJob;
    private long dropTimer = 0L;
    private long lockTimer = 0L;
    private int lockMoveCount = 0;
    @org.jetbrains.annotations.Nullable
    private kotlin.Pair<java.lang.Integer, java.lang.Integer> lastPiecePosition;
    private long gameStartTime = 0L;
    @org.jetbrains.annotations.Nullable
    private com.tetris.modern.rl.game.models.Piece currentPiece;
    @org.jetbrains.annotations.Nullable
    private com.tetris.modern.rl.game.models.PieceType heldPiece;
    private boolean canHold = true;
    @org.jetbrains.annotations.Nullable
    private com.tetris.modern.rl.game.modes.GameMode gameMode;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.game.TetrisEngine.Companion Companion = null;
    
    @javax.inject.Inject
    public TetrisEngine(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.audio.AudioManager audioManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.game.models.GameState> getGameState() {
        return null;
    }
    
    public final void startGame(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.modes.GameMode mode) {
    }
    
    private final void resetGame() {
    }
    
    private final void startGameLoop() {
    }
    
    private final void update(long deltaTime) {
    }
    
    private final long calculateDropInterval() {
        return 0L;
    }
    
    private final void spawnNewPiece() {
    }
    
    public final void moveLeft() {
    }
    
    public final void moveRight() {
    }
    
    public final void rotateClockwise() {
    }
    
    public final void rotateCounterClockwise() {
    }
    
    public final void softDrop() {
    }
    
    public final void hardDrop() {
    }
    
    public final void holdPiece() {
    }
    
    private final boolean canMoveDown(com.tetris.modern.rl.game.models.Piece piece) {
        return false;
    }
    
    private final boolean isValidPosition(com.tetris.modern.rl.game.models.Piece piece) {
        return false;
    }
    
    private final com.tetris.modern.rl.game.models.Piece tryWallKicks(com.tetris.modern.rl.game.models.Piece originalPiece, com.tetris.modern.rl.game.models.Piece rotatedPiece) {
        return null;
    }
    
    private final java.util.List<kotlin.Pair<java.lang.Integer, java.lang.Integer>> getWallKickOffsets(com.tetris.modern.rl.game.models.PieceType type, int fromRotation, int toRotation) {
        return null;
    }
    
    private final void resetLockIfNeeded() {
    }
    
    private final void lockPiece(com.tetris.modern.rl.game.models.Piece piece) {
    }
    
    private final int calculateScore(int lines, boolean isTSpin, boolean isTSpinMini) {
        return 0;
    }
    
    private final void updateStatistics(int lines, boolean isTSpin, boolean isTSpinMini) {
    }
    
    public final void pauseGame() {
    }
    
    public final void resumeGame() {
    }
    
    private final void gameOver() {
    }
    
    public final void triggerVictory() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final int[][] getGridState() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tetris.modern.rl.game.models.Piece getCurrentPieceState() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tetris.modern.rl.game.models.Piece getGhostPiecePosition() {
        return null;
    }
    
    private final void adjustMusicTempo(int level) {
    }
    
    private final float pow(float $this$pow, int n) {
        return 0.0F;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u000f\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/tetris/modern/rl/game/TetrisEngine$Companion;", "", "()V", "GRID_HEIGHT", "", "GRID_WIDTH", "HIDDEN_ROWS", "INITIAL_DROP_INTERVAL", "", "LOCK_DELAY", "MAX_LOCK_MOVES", "MIN_DROP_INTERVAL", "POINTS_COMBO_MULTIPLIER", "POINTS_DOUBLE", "POINTS_PERFECT_CLEAR", "POINTS_SINGLE", "POINTS_TETRIS", "POINTS_TRIPLE", "POINTS_T_SPIN", "POINTS_T_SPIN_DOUBLE", "POINTS_T_SPIN_SINGLE", "POINTS_T_SPIN_TRIPLE", "TOTAL_HEIGHT", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}