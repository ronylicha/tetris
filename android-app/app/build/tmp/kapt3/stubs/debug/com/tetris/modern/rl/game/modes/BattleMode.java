package com.tetris.modern.rl.game.modes;

import com.tetris.modern.rl.ai.TetrisAI;
import com.tetris.modern.rl.game.models.GameState;
import kotlinx.coroutines.Dispatchers;

/**
 * Battle Mode - Face off against AI opponents
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001)B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0006H\u0002J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006H\u0002J\b\u0010\u0012\u001a\u00020\u000fH\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0014\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0014H\u0016J\u0018\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u000fH\u0002J\b\u0010\u001f\u001a\u00020\u000fH\u0002J\b\u0010 \u001a\u00020\u000fH\u0002J\u000e\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u0004J\b\u0010#\u001a\u00020\u000fH\u0002J\u0018\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020(H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/tetris/modern/rl/game/modes/BattleMode;", "Lcom/tetris/modern/rl/game/modes/GameMode;", "()V", "aiDifficulty", "Lcom/tetris/modern/rl/game/modes/BattleMode$AIDifficulty;", "aiGarbageLines", "", "aiOpponent", "Lcom/tetris/modern/rl/ai/TetrisAI;", "aiWins", "currentRound", "maxRounds", "playerGarbageLines", "playerWins", "applyGarbageToPlayer", "", "lines", "calculateGarbage", "endRound", "getDescription", "", "getModeUI", "", "getObjective", "handleLineClears", "isTSpin", "", "initialize", "engine", "Lcom/tetris/modern/rl/game/TetrisEngine;", "onBattleLost", "onBattleWon", "resetBattle", "setDifficulty", "difficulty", "startNewRound", "update", "deltaTime", "", "gameState", "Lcom/tetris/modern/rl/game/models/GameState;", "AIDifficulty", "app_debug"})
public final class BattleMode extends com.tetris.modern.rl.game.modes.GameMode {
    @org.jetbrains.annotations.Nullable
    private com.tetris.modern.rl.ai.TetrisAI aiOpponent;
    @org.jetbrains.annotations.NotNull
    private com.tetris.modern.rl.game.modes.BattleMode.AIDifficulty aiDifficulty = com.tetris.modern.rl.game.modes.BattleMode.AIDifficulty.NORMAL;
    private int playerGarbageLines = 0;
    private int aiGarbageLines = 0;
    private int currentRound = 1;
    private int playerWins = 0;
    private int aiWins = 0;
    private final int maxRounds = 3;
    
    public BattleMode() {
        super(null);
    }
    
    public final void setDifficulty(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.modes.BattleMode.AIDifficulty difficulty) {
    }
    
    @java.lang.Override
    public void initialize(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.TetrisEngine engine) {
    }
    
    private final void resetBattle() {
    }
    
    @java.lang.Override
    public void update(long deltaTime, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.GameState gameState) {
    }
    
    @java.lang.Override
    public void handleLineClears(int lines, boolean isTSpin) {
    }
    
    private final int calculateGarbage(int lines) {
        return 0;
    }
    
    private final void applyGarbageToPlayer(int lines) {
    }
    
    private final void endRound() {
    }
    
    private final void startNewRound() {
    }
    
    private final void onBattleWon() {
    }
    
    private final void onBattleLost() {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\r\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013\u00a8\u0006\u0014"}, d2 = {"Lcom/tetris/modern/rl/game/modes/BattleMode$AIDifficulty;", "", "level", "", "difficultyName", "", "speed", "", "(Ljava/lang/String;IILjava/lang/String;F)V", "getDifficultyName", "()Ljava/lang/String;", "getLevel", "()I", "getSpeed", "()F", "EASY", "NORMAL", "HARD", "EXPERT", "MASTER", "app_debug"})
    public static enum AIDifficulty {
        /*public static final*/ EASY /* = new EASY(0, null, 0.0F) */,
        /*public static final*/ NORMAL /* = new NORMAL(0, null, 0.0F) */,
        /*public static final*/ HARD /* = new HARD(0, null, 0.0F) */,
        /*public static final*/ EXPERT /* = new EXPERT(0, null, 0.0F) */,
        /*public static final*/ MASTER /* = new MASTER(0, null, 0.0F) */;
        private final int level = 0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String difficultyName = null;
        private final float speed = 0.0F;
        
        AIDifficulty(int level, java.lang.String difficultyName, float speed) {
        }
        
        public final int getLevel() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDifficultyName() {
            return null;
        }
        
        public final float getSpeed() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.game.modes.BattleMode.AIDifficulty> getEntries() {
            return null;
        }
    }
}