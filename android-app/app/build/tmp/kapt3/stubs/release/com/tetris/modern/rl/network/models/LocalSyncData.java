package com.tetris.modern.rl.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B_\u0012\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0003\u0012\u0016\b\u0002\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0001\u0018\u00010\f\u00a2\u0006\u0002\u0010\rJ\u0011\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u0011\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0003H\u00c6\u0003J\u0011\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0003H\u00c6\u0003J\u0017\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u00c6\u0003Jc\u0010\u001b\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00032\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u00032\u0016\b\u0002\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\bH\u00d6\u0001R\u001e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u001e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR$\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0001\u0018\u00010\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\""}, d2 = {"Lcom/tetris/modern/rl/network/models/LocalSyncData;", "", "scores", "", "Lcom/tetris/modern/rl/network/models/ScoreSubmitRequest;", "progression", "Lcom/tetris/modern/rl/network/models/ProgressionData;", "achievements", "", "puzzles", "Lcom/tetris/modern/rl/network/models/PuzzleProgressData;", "settings", "", "(Ljava/util/List;Lcom/tetris/modern/rl/network/models/ProgressionData;Ljava/util/List;Ljava/util/List;Ljava/util/Map;)V", "getAchievements", "()Ljava/util/List;", "getProgression", "()Lcom/tetris/modern/rl/network/models/ProgressionData;", "getPuzzles", "getScores", "getSettings", "()Ljava/util/Map;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"})
public final class LocalSyncData {
    @com.google.gson.annotations.SerializedName(value = "scores")
    @org.jetbrains.annotations.Nullable
    private final java.util.List<com.tetris.modern.rl.network.models.ScoreSubmitRequest> scores = null;
    @com.google.gson.annotations.SerializedName(value = "progression")
    @org.jetbrains.annotations.Nullable
    private final com.tetris.modern.rl.network.models.ProgressionData progression = null;
    @com.google.gson.annotations.SerializedName(value = "achievements")
    @org.jetbrains.annotations.Nullable
    private final java.util.List<java.lang.String> achievements = null;
    @com.google.gson.annotations.SerializedName(value = "puzzles")
    @org.jetbrains.annotations.Nullable
    private final java.util.List<com.tetris.modern.rl.network.models.PuzzleProgressData> puzzles = null;
    @com.google.gson.annotations.SerializedName(value = "settings")
    @org.jetbrains.annotations.Nullable
    private final java.util.Map<java.lang.String, java.lang.Object> settings = null;
    
    public LocalSyncData(@org.jetbrains.annotations.Nullable
    java.util.List<com.tetris.modern.rl.network.models.ScoreSubmitRequest> scores, @org.jetbrains.annotations.Nullable
    com.tetris.modern.rl.network.models.ProgressionData progression, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> achievements, @org.jetbrains.annotations.Nullable
    java.util.List<com.tetris.modern.rl.network.models.PuzzleProgressData> puzzles, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> settings) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.tetris.modern.rl.network.models.ScoreSubmitRequest> getScores() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tetris.modern.rl.network.models.ProgressionData getProgression() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> getAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.tetris.modern.rl.network.models.PuzzleProgressData> getPuzzles() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Map<java.lang.String, java.lang.Object> getSettings() {
        return null;
    }
    
    public LocalSyncData() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.tetris.modern.rl.network.models.ScoreSubmitRequest> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tetris.modern.rl.network.models.ProgressionData component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.tetris.modern.rl.network.models.PuzzleProgressData> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Map<java.lang.String, java.lang.Object> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.LocalSyncData copy(@org.jetbrains.annotations.Nullable
    java.util.List<com.tetris.modern.rl.network.models.ScoreSubmitRequest> scores, @org.jetbrains.annotations.Nullable
    com.tetris.modern.rl.network.models.ProgressionData progression, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> achievements, @org.jetbrains.annotations.Nullable
    java.util.List<com.tetris.modern.rl.network.models.PuzzleProgressData> puzzles, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> settings) {
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