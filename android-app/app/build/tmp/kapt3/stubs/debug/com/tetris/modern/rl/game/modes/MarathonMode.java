package com.tetris.modern.rl.game.modes;

import com.tetris.modern.rl.game.models.GameState;

/**
 * Marathon Mode - Survive 150 lines with increasing difficulty
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u0014\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\u000fH\u0016J\b\u0010\u0010\u001a\u00020\rH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\tH\u0016J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/tetris/modern/rl/game/modes/MarathonMode;", "Lcom/tetris/modern/rl/game/modes/GameMode;", "()V", "currentCheckpoint", "", "difficultyMultiplier", "", "linesCleared", "checkWinCondition", "", "gameState", "Lcom/tetris/modern/rl/game/models/GameState;", "getDescription", "", "getModeUI", "", "getObjective", "handleLineClears", "", "lines", "isTSpin", "initialize", "engine", "Lcom/tetris/modern/rl/game/TetrisEngine;", "update", "deltaTime", "", "Companion", "app_debug"})
public final class MarathonMode extends com.tetris.modern.rl.game.modes.GameMode {
    public static final int TARGET_LINES = 150;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.Integer> CHECKPOINTS = null;
    private int linesCleared = 0;
    private int currentCheckpoint = 0;
    private float difficultyMultiplier = 1.0F;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.game.modes.MarathonMode.Companion Companion = null;
    
    public MarathonMode() {
        super(null);
    }
    
    @java.lang.Override
    public void initialize(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.TetrisEngine engine) {
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
    public boolean checkWinCondition(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.GameState gameState) {
        return false;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String getDescription() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/tetris/modern/rl/game/modes/MarathonMode$Companion;", "", "()V", "CHECKPOINTS", "", "", "getCHECKPOINTS", "()Ljava/util/List;", "TARGET_LINES", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.Integer> getCHECKPOINTS() {
            return null;
        }
    }
}