package com.tetris.modern.rl.game.modes;

import com.tetris.modern.rl.game.models.GameState;

/**
 * Classic endless Tetris mode
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0014\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\tH\u0016J\b\u0010\n\u001a\u00020\u0007H\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/tetris/modern/rl/game/modes/ClassicMode;", "Lcom/tetris/modern/rl/game/modes/GameMode;", "()V", "linesClearedThisLevel", "", "linesPerLevel", "getDescription", "", "getModeUI", "", "getObjective", "handleLineClears", "", "lines", "isTSpin", "", "update", "deltaTime", "", "gameState", "Lcom/tetris/modern/rl/game/models/GameState;", "app_release"})
public final class ClassicMode extends com.tetris.modern.rl.game.modes.GameMode {
    private int linesClearedThisLevel = 0;
    private final int linesPerLevel = 10;
    
    public ClassicMode() {
        super(null);
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
}