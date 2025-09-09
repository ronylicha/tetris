package com.tetris.modern.rl.game.modes;

import com.tetris.modern.rl.game.models.GameState;

/**
 * Zen Mode - Relaxing endless mode with detailed statistics
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0002J\b\u0010\u0010\u001a\u00020\u000eH\u0016J\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00130\u0012J\u0014\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\u0012H\u0016J\b\u0010\u0015\u001a\u00020\u000eH\u0016J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0017H\u0002J\u0018\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\b2\u0006\u0010!\u001a\u00020\"H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/tetris/modern/rl/game/modes/ZenMode;", "Lcom/tetris/modern/rl/game/modes/GameMode;", "()V", "maxCombo", "", "perfectClears", "piecesPlaced", "startTime", "", "tSpins", "tetrises", "totalMoves", "totalRotations", "formatTime", "", "seconds", "getDescription", "getDetailedStatistics", "", "", "getModeUI", "getObjective", "handleLineClears", "", "lines", "isTSpin", "", "initialize", "engine", "Lcom/tetris/modern/rl/game/TetrisEngine;", "resetStatistics", "update", "deltaTime", "gameState", "Lcom/tetris/modern/rl/game/models/GameState;", "app_release"})
public final class ZenMode extends com.tetris.modern.rl.game.modes.GameMode {
    private long startTime = 0L;
    private int piecesPlaced = 0;
    private int tSpins = 0;
    private int tetrises = 0;
    private int perfectClears = 0;
    private int maxCombo = 0;
    private int totalRotations = 0;
    private int totalMoves = 0;
    
    public ZenMode() {
        super(null);
    }
    
    @java.lang.Override
    public void initialize(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.TetrisEngine engine) {
    }
    
    private final void resetStatistics() {
    }
    
    @java.lang.Override
    public void update(long deltaTime, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.GameState gameState) {
    }
    
    @java.lang.Override
    public void handleLineClears(int lines, boolean isTSpin) {
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
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.Object> getDetailedStatistics() {
        return null;
    }
    
    private final java.lang.String formatTime(long seconds) {
        return null;
    }
}