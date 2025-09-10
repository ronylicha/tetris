package com.tetris.modern.rl.game.modes;

import com.tetris.modern.rl.game.models.GameState;
import com.tetris.modern.rl.puzzles.Puzzle;
import com.tetris.modern.rl.puzzles.PuzzleManager;

/**
 * Puzzle Mode - Complete 150 challenging puzzles
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000bH\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0014\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u000fH\u0016J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u000e\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u0006J\b\u0010\u001a\u001a\u00020\u0014H\u0002J\b\u0010\u001b\u001a\u0004\u0018\u00010\u000fJ\u0018\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/tetris/modern/rl/game/modes/PuzzleMode;", "Lcom/tetris/modern/rl/game/modes/GameMode;", "()V", "currentPuzzle", "Lcom/tetris/modern/rl/puzzles/Puzzle;", "hintsUsed", "", "movesUsed", "puzzleManager", "Lcom/tetris/modern/rl/puzzles/PuzzleManager;", "startTime", "", "calculateStars", "completionTime", "getDescription", "", "getModeUI", "", "getObjective", "handleLineClears", "", "lines", "isTSpin", "", "loadPuzzle", "puzzleId", "onPuzzleCompleted", "requestHint", "update", "deltaTime", "gameState", "Lcom/tetris/modern/rl/game/models/GameState;", "app_debug"})
public final class PuzzleMode extends com.tetris.modern.rl.game.modes.GameMode {
    @org.jetbrains.annotations.Nullable
    private com.tetris.modern.rl.puzzles.Puzzle currentPuzzle;
    @org.jetbrains.annotations.NotNull
    private com.tetris.modern.rl.puzzles.PuzzleManager puzzleManager;
    private int movesUsed = 0;
    private int hintsUsed = 0;
    private long startTime = 0L;
    
    public PuzzleMode() {
        super(null);
    }
    
    public final void loadPuzzle(int puzzleId) {
    }
    
    @java.lang.Override
    public void update(long deltaTime, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.GameState gameState) {
    }
    
    @java.lang.Override
    public void handleLineClears(int lines, boolean isTSpin) {
    }
    
    private final void onPuzzleCompleted() {
    }
    
    private final int calculateStars(long completionTime) {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String requestHint() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String getObjective() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.util.Map<java.lang.String, java.lang.String> getModeUI() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String getDescription() {
        return null;
    }
}