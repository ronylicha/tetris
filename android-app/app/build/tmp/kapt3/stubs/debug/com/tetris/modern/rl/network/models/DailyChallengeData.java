package com.tetris.modern.rl.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\u0011\u0010 \u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u0007H\u00c6\u0003J]\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00052\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u0007H\u00c6\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020\tH\u00d6\u0001J\t\u0010&\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\n\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\b\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0016\u0010\u000b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015\u00a8\u0006\'"}, d2 = {"Lcom/tetris/modern/rl/network/models/DailyChallengeData;", "", "date", "", "seed", "", "modifiers", "", "targetScore", "", "targetLines", "timeLimit", "leaderboard", "Lcom/tetris/modern/rl/network/models/DailyChallengeScore;", "(Ljava/lang/String;JLjava/util/List;IIJLjava/util/List;)V", "getDate", "()Ljava/lang/String;", "getLeaderboard", "()Ljava/util/List;", "getModifiers", "getSeed", "()J", "getTargetLines", "()I", "getTargetScore", "getTimeLimit", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class DailyChallengeData {
    @com.google.gson.annotations.SerializedName(value = "date")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String date = null;
    @com.google.gson.annotations.SerializedName(value = "seed")
    private final long seed = 0L;
    @com.google.gson.annotations.SerializedName(value = "modifiers")
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> modifiers = null;
    @com.google.gson.annotations.SerializedName(value = "target_score")
    private final int targetScore = 0;
    @com.google.gson.annotations.SerializedName(value = "target_lines")
    private final int targetLines = 0;
    @com.google.gson.annotations.SerializedName(value = "time_limit")
    private final long timeLimit = 0L;
    @com.google.gson.annotations.SerializedName(value = "leaderboard")
    @org.jetbrains.annotations.Nullable
    private final java.util.List<com.tetris.modern.rl.network.models.DailyChallengeScore> leaderboard = null;
    
    public DailyChallengeData(@org.jetbrains.annotations.NotNull
    java.lang.String date, long seed, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> modifiers, int targetScore, int targetLines, long timeLimit, @org.jetbrains.annotations.Nullable
    java.util.List<com.tetris.modern.rl.network.models.DailyChallengeScore> leaderboard) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDate() {
        return null;
    }
    
    public final long getSeed() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getModifiers() {
        return null;
    }
    
    public final int getTargetScore() {
        return 0;
    }
    
    public final int getTargetLines() {
        return 0;
    }
    
    public final long getTimeLimit() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.tetris.modern.rl.network.models.DailyChallengeScore> getLeaderboard() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final long component6() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.tetris.modern.rl.network.models.DailyChallengeScore> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.DailyChallengeData copy(@org.jetbrains.annotations.NotNull
    java.lang.String date, long seed, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> modifiers, int targetScore, int targetLines, long timeLimit, @org.jetbrains.annotations.Nullable
    java.util.List<com.tetris.modern.rl.network.models.DailyChallengeScore> leaderboard) {
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