package com.tetris.modern.rl.game.modes;

import com.tetris.modern.rl.game.TetrisEngine;
import com.tetris.modern.rl.game.models.GameState;

/**
 * Abstract base class for all game modes
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0003H&J\u0014\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0003H&J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000eH&J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u001b\u001a\u00020\u000eH\u0016J\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u000f\u001a\u00020\u0010H&R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006 "}, d2 = {"Lcom/tetris/modern/rl/game/modes/GameMode;", "", "name", "", "(Ljava/lang/String;)V", "engine", "Lcom/tetris/modern/rl/game/TetrisEngine;", "getEngine", "()Lcom/tetris/modern/rl/game/TetrisEngine;", "setEngine", "(Lcom/tetris/modern/rl/game/TetrisEngine;)V", "getName", "()Ljava/lang/String;", "checkWinCondition", "", "gameState", "Lcom/tetris/modern/rl/game/models/GameState;", "getDescription", "getModeUI", "", "getObjective", "handleLineClears", "", "lines", "", "isTSpin", "initialize", "isAvailableOnMobile", "onGameOver", "update", "deltaTime", "", "app_debug"})
public abstract class GameMode {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String name = null;
    @org.jetbrains.annotations.Nullable
    private com.tetris.modern.rl.game.TetrisEngine engine;
    
    public GameMode(@org.jetbrains.annotations.NotNull
    java.lang.String name) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    protected final com.tetris.modern.rl.game.TetrisEngine getEngine() {
        return null;
    }
    
    protected final void setEngine(@org.jetbrains.annotations.Nullable
    com.tetris.modern.rl.game.TetrisEngine p0) {
    }
    
    /**
     * Initialize the game mode with the engine
     */
    public void initialize(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.TetrisEngine engine) {
    }
    
    /**
     * Update the game mode logic
     */
    public abstract void update(long deltaTime, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.GameState gameState);
    
    /**
     * Handle line clears specific to this mode
     */
    public abstract void handleLineClears(int lines, boolean isTSpin);
    
    /**
     * Get the objective text for this mode
     */
    @org.jetbrains.annotations.NotNull
    public abstract java.lang.String getObjective();
    
    /**
     * Get mode-specific UI elements
     */
    @org.jetbrains.annotations.NotNull
    public java.util.Map<java.lang.String, java.lang.String> getModeUI() {
        return null;
    }
    
    /**
     * Check if the mode has a win condition and if it's met
     */
    public boolean checkWinCondition(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.GameState gameState) {
        return false;
    }
    
    /**
     * Handle game over for this mode
     */
    public void onGameOver(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.GameState gameState) {
    }
    
    /**
     * Get the description of this mode
     */
    @org.jetbrains.annotations.NotNull
    public abstract java.lang.String getDescription();
    
    /**
     * Check if this mode is available on mobile
     */
    public boolean isAvailableOnMobile() {
        return false;
    }
}