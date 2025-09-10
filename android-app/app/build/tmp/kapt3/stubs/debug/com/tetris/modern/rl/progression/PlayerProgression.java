package com.tetris.modern.rl.progression;

import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 .2\u00020\u0001:\u0006./0123B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0010H\u0002J\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0010H\u0002J\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0010H\u0002J\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0019\u001a\u00020\u0010J\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\u0018\u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u001cJ\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u0010H\u0002JX\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010#\u001a\u00020\u00122\b\b\u0002\u0010$\u001a\u00020\u00102\b\b\u0002\u0010%\u001a\u00020\u00102\b\b\u0002\u0010&\u001a\u00020\u00102\b\b\u0002\u0010\'\u001a\u00020\u00102\b\b\u0002\u0010(\u001a\u00020)J\u0006\u0010*\u001a\u00020\u000eJ\u000e\u0010+\u001a\u00020\u000e2\u0006\u0010,\u001a\u00020-R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/tetris/modern/rl/progression/PlayerProgression;", "", "()V", "_progressionState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/tetris/modern/rl/progression/PlayerProgression$ProgressionState;", "progressionState", "Lkotlinx/coroutines/flow/StateFlow;", "getProgressionState", "()Lkotlinx/coroutines/flow/StateFlow;", "unlockables", "", "Lcom/tetris/modern/rl/progression/PlayerProgression$Unlockable;", "addXP", "", "amount", "", "source", "", "calculateRank", "Lcom/tetris/modern/rl/progression/PlayerProgression$Rank;", "level", "calculateXPForLevel", "checkUnlocks", "getAvailableUnlocks", "currentLevel", "getNextUnlocks", "getUnlockedItems", "", "Lcom/tetris/modern/rl/progression/PlayerProgression$UnlockableType;", "onLevelUp", "newLevel", "processGameResults", "score", "lines", "gameMode", "tSpins", "tetrises", "perfectClears", "maxCombo", "duration", "", "reset", "updateStatistics", "stats", "Lcom/tetris/modern/rl/progression/PlayerProgression$PlayerStatistics;", "Companion", "PlayerStatistics", "ProgressionState", "Rank", "Unlockable", "UnlockableType", "app_debug"})
public final class PlayerProgression {
    public static final int MAX_LEVEL = 100;
    public static final int XP_PER_LEVEL_BASE = 1000;
    public static final float XP_PER_LEVEL_MULTIPLIER = 1.15F;
    public static final int XP_PER_LINE = 10;
    public static final int XP_PER_TETRIS = 100;
    public static final int XP_PER_T_SPIN = 50;
    public static final int XP_PER_PERFECT_CLEAR = 200;
    public static final int XP_PER_COMBO = 15;
    public static final int XP_PER_LEVEL_UP = 100;
    public static final int XP_DAILY_CHALLENGE = 500;
    public static final int XP_ACHIEVEMENT = 250;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.tetris.modern.rl.progression.PlayerProgression.ProgressionState> _progressionState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.progression.PlayerProgression.ProgressionState> progressionState = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.progression.PlayerProgression.Unlockable> unlockables = null;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.progression.PlayerProgression.Companion Companion = null;
    
    @javax.inject.Inject
    public PlayerProgression() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.progression.PlayerProgression.ProgressionState> getProgressionState() {
        return null;
    }
    
    public final void addXP(int amount, @org.jetbrains.annotations.NotNull
    java.lang.String source) {
    }
    
    private final int calculateXPForLevel(int level) {
        return 0;
    }
    
    private final com.tetris.modern.rl.progression.PlayerProgression.Rank calculateRank(int level) {
        return null;
    }
    
    private final void checkUnlocks(int level) {
    }
    
    private final void onLevelUp(int newLevel) {
    }
    
    public final void updateStatistics(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.progression.PlayerProgression.PlayerStatistics stats) {
    }
    
    public final void processGameResults(int score, int lines, int level, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, int tSpins, int tetrises, int perfectClears, int maxCombo, long duration) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<com.tetris.modern.rl.progression.PlayerProgression.UnlockableType, java.util.List<com.tetris.modern.rl.progression.PlayerProgression.Unlockable>> getUnlockedItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.progression.PlayerProgression.Unlockable> getAvailableUnlocks(int currentLevel) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.progression.PlayerProgression.Unlockable> getNextUnlocks() {
        return null;
    }
    
    public final void reset() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/tetris/modern/rl/progression/PlayerProgression$Companion;", "", "()V", "MAX_LEVEL", "", "XP_ACHIEVEMENT", "XP_DAILY_CHALLENGE", "XP_PER_COMBO", "XP_PER_LEVEL_BASE", "XP_PER_LEVEL_MULTIPLIER", "", "XP_PER_LEVEL_UP", "XP_PER_LINE", "XP_PER_PERFECT_CLEAR", "XP_PER_TETRIS", "XP_PER_T_SPIN", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001Bs\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0006\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u000eH\u00c6\u0003J\t\u0010#\u001a\u00020\u0010H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0006H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0006H\u00c6\u0003Jw\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u00c6\u0001J\u0013\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u00020\u0003H\u00d6\u0001J\t\u00101\u001a\u00020\u000eH\u00d6\u0001R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0011\u0010\f\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 \u00a8\u00062"}, d2 = {"Lcom/tetris/modern/rl/progression/PlayerProgression$PlayerStatistics;", "", "totalGamesPlayed", "", "totalLinesCleared", "totalScore", "", "highestScore", "totalTSpins", "totalTetrises", "totalPerfectClears", "longestCombo", "totalPlayTime", "favoriteMode", "", "winRate", "", "(IIJIIIIIJLjava/lang/String;F)V", "getFavoriteMode", "()Ljava/lang/String;", "getHighestScore", "()I", "getLongestCombo", "getTotalGamesPlayed", "getTotalLinesCleared", "getTotalPerfectClears", "getTotalPlayTime", "()J", "getTotalScore", "getTotalTSpins", "getTotalTetrises", "getWinRate", "()F", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
    public static final class PlayerStatistics {
        private final int totalGamesPlayed = 0;
        private final int totalLinesCleared = 0;
        private final long totalScore = 0L;
        private final int highestScore = 0;
        private final int totalTSpins = 0;
        private final int totalTetrises = 0;
        private final int totalPerfectClears = 0;
        private final int longestCombo = 0;
        private final long totalPlayTime = 0L;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String favoriteMode = null;
        private final float winRate = 0.0F;
        
        public PlayerStatistics(int totalGamesPlayed, int totalLinesCleared, long totalScore, int highestScore, int totalTSpins, int totalTetrises, int totalPerfectClears, int longestCombo, long totalPlayTime, @org.jetbrains.annotations.NotNull
        java.lang.String favoriteMode, float winRate) {
            super();
        }
        
        public final int getTotalGamesPlayed() {
            return 0;
        }
        
        public final int getTotalLinesCleared() {
            return 0;
        }
        
        public final long getTotalScore() {
            return 0L;
        }
        
        public final int getHighestScore() {
            return 0;
        }
        
        public final int getTotalTSpins() {
            return 0;
        }
        
        public final int getTotalTetrises() {
            return 0;
        }
        
        public final int getTotalPerfectClears() {
            return 0;
        }
        
        public final int getLongestCombo() {
            return 0;
        }
        
        public final long getTotalPlayTime() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getFavoriteMode() {
            return null;
        }
        
        public final float getWinRate() {
            return 0.0F;
        }
        
        public PlayerStatistics() {
            super();
        }
        
        public final int component1() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component10() {
            return null;
        }
        
        public final float component11() {
            return 0.0F;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final long component3() {
            return 0L;
        }
        
        public final int component4() {
            return 0;
        }
        
        public final int component5() {
            return 0;
        }
        
        public final int component6() {
            return 0;
        }
        
        public final int component7() {
            return 0;
        }
        
        public final int component8() {
            return 0;
        }
        
        public final long component9() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.progression.PlayerProgression.PlayerStatistics copy(int totalGamesPlayed, int totalLinesCleared, long totalScore, int highestScore, int totalTSpins, int totalTetrises, int totalPerfectClears, int longestCombo, long totalPlayTime, @org.jetbrains.annotations.NotNull
        java.lang.String favoriteMode, float winRate) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0081\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0010H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\bH\u00c6\u0003J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\u0085\u0001\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u00c6\u0001J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020\u0003H\u00d6\u0001J\t\u0010/\u001a\u00020\u000bH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0013\u00a8\u00060"}, d2 = {"Lcom/tetris/modern/rl/progression/PlayerProgression$ProgressionState;", "", "level", "", "currentXP", "totalXP", "xpToNextLevel", "rank", "Lcom/tetris/modern/rl/progression/PlayerProgression$Rank;", "unlockedThemes", "", "", "unlockedMusic", "unlockedPieceStyles", "unlockedEffects", "statistics", "Lcom/tetris/modern/rl/progression/PlayerProgression$PlayerStatistics;", "(IIIILcom/tetris/modern/rl/progression/PlayerProgression$Rank;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Lcom/tetris/modern/rl/progression/PlayerProgression$PlayerStatistics;)V", "getCurrentXP", "()I", "getLevel", "getRank", "()Lcom/tetris/modern/rl/progression/PlayerProgression$Rank;", "getStatistics", "()Lcom/tetris/modern/rl/progression/PlayerProgression$PlayerStatistics;", "getTotalXP", "getUnlockedEffects", "()Ljava/util/Set;", "getUnlockedMusic", "getUnlockedPieceStyles", "getUnlockedThemes", "getXpToNextLevel", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
    public static final class ProgressionState {
        private final int level = 0;
        private final int currentXP = 0;
        private final int totalXP = 0;
        private final int xpToNextLevel = 0;
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.progression.PlayerProgression.Rank rank = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.Set<java.lang.String> unlockedThemes = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.Set<java.lang.String> unlockedMusic = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.Set<java.lang.String> unlockedPieceStyles = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.Set<java.lang.String> unlockedEffects = null;
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.progression.PlayerProgression.PlayerStatistics statistics = null;
        
        public ProgressionState(int level, int currentXP, int totalXP, int xpToNextLevel, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.progression.PlayerProgression.Rank rank, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedThemes, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedMusic, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedPieceStyles, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedEffects, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.progression.PlayerProgression.PlayerStatistics statistics) {
            super();
        }
        
        public final int getLevel() {
            return 0;
        }
        
        public final int getCurrentXP() {
            return 0;
        }
        
        public final int getTotalXP() {
            return 0;
        }
        
        public final int getXpToNextLevel() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.progression.PlayerProgression.Rank getRank() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> getUnlockedThemes() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> getUnlockedMusic() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> getUnlockedPieceStyles() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> getUnlockedEffects() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.progression.PlayerProgression.PlayerStatistics getStatistics() {
            return null;
        }
        
        public ProgressionState() {
            super();
        }
        
        public final int component1() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.progression.PlayerProgression.PlayerStatistics component10() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final int component3() {
            return 0;
        }
        
        public final int component4() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.progression.PlayerProgression.Rank component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> component8() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.progression.PlayerProgression.ProgressionState copy(int level, int currentXP, int totalXP, int xpToNextLevel, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.progression.PlayerProgression.Rank rank, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedThemes, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedMusic, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedPieceStyles, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedEffects, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.progression.PlayerProgression.PlayerStatistics statistics) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/tetris/modern/rl/progression/PlayerProgression$Rank;", "", "minLevel", "", "displayName", "", "color", "(Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;)V", "getColor", "()Ljava/lang/String;", "getDisplayName", "getMinLevel", "()I", "NOVICE", "APPRENTICE", "ADEPT", "EXPERT", "MASTER", "GRANDMASTER", "LEGEND", "MYTHIC", "DIVINE", "ETERNAL", "app_debug"})
    public static enum Rank {
        /*public static final*/ NOVICE /* = new NOVICE(0, null, null) */,
        /*public static final*/ APPRENTICE /* = new APPRENTICE(0, null, null) */,
        /*public static final*/ ADEPT /* = new ADEPT(0, null, null) */,
        /*public static final*/ EXPERT /* = new EXPERT(0, null, null) */,
        /*public static final*/ MASTER /* = new MASTER(0, null, null) */,
        /*public static final*/ GRANDMASTER /* = new GRANDMASTER(0, null, null) */,
        /*public static final*/ LEGEND /* = new LEGEND(0, null, null) */,
        /*public static final*/ MYTHIC /* = new MYTHIC(0, null, null) */,
        /*public static final*/ DIVINE /* = new DIVINE(0, null, null) */,
        /*public static final*/ ETERNAL /* = new ETERNAL(0, null, null) */;
        private final int minLevel = 0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String displayName = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String color = null;
        
        Rank(int minLevel, java.lang.String displayName, java.lang.String color) {
        }
        
        public final int getMinLevel() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDisplayName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getColor() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.progression.PlayerProgression.Rank> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003JG\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\bH\u00d6\u0001J\t\u0010 \u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006!"}, d2 = {"Lcom/tetris/modern/rl/progression/PlayerProgression$Unlockable;", "", "id", "", "name", "type", "Lcom/tetris/modern/rl/progression/PlayerProgression$UnlockableType;", "requiredLevel", "", "description", "previewUrl", "(Ljava/lang/String;Ljava/lang/String;Lcom/tetris/modern/rl/progression/PlayerProgression$UnlockableType;ILjava/lang/String;Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "getId", "getName", "getPreviewUrl", "getRequiredLevel", "()I", "getType", "()Lcom/tetris/modern/rl/progression/PlayerProgression$UnlockableType;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
    public static final class Unlockable {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String id = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.progression.PlayerProgression.UnlockableType type = null;
        private final int requiredLevel = 0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String description = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String previewUrl = null;
        
        public Unlockable(@org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.progression.PlayerProgression.UnlockableType type, int requiredLevel, @org.jetbrains.annotations.NotNull
        java.lang.String description, @org.jetbrains.annotations.Nullable
        java.lang.String previewUrl) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.progression.PlayerProgression.UnlockableType getType() {
            return null;
        }
        
        public final int getRequiredLevel() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDescription() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getPreviewUrl() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.progression.PlayerProgression.UnlockableType component3() {
            return null;
        }
        
        public final int component4() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.progression.PlayerProgression.Unlockable copy(@org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.progression.PlayerProgression.UnlockableType type, int requiredLevel, @org.jetbrains.annotations.NotNull
        java.lang.String description, @org.jetbrains.annotations.Nullable
        java.lang.String previewUrl) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/tetris/modern/rl/progression/PlayerProgression$UnlockableType;", "", "(Ljava/lang/String;I)V", "THEME", "MUSIC", "PIECE_STYLE", "EFFECT", "app_debug"})
    public static enum UnlockableType {
        /*public static final*/ THEME /* = new THEME() */,
        /*public static final*/ MUSIC /* = new MUSIC() */,
        /*public static final*/ PIECE_STYLE /* = new PIECE_STYLE() */,
        /*public static final*/ EFFECT /* = new EFFECT() */;
        
        UnlockableType() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.progression.PlayerProgression.UnlockableType> getEntries() {
            return null;
        }
    }
}