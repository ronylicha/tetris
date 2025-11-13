package com.tetris.modern.rl.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u001f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001Bi\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e\u0012\u0006\u0010\u0010\u001a\u00020\n\u00a2\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070\u000eH\u00c6\u0003J\t\u0010#\u001a\u00020\nH\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\nH\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00070\u000eH\u00c6\u0003J\u0083\u0001\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\nH\u00c6\u0001J\u0013\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u00020\u0003H\u00d6\u0001J\t\u00101\u001a\u00020\u0007H\u00d6\u0001R\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0016\u0010\u0010\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0013\u00a8\u00062"}, d2 = {"Lcom/tetris/modern/rl/network/models/ProgressionData;", "", "level", "", "currentXp", "totalXp", "rank", "", "gamesPlayed", "totalScore", "", "totalLines", "highestScore", "achievements", "", "unlocks", "lastSync", "(IIILjava/lang/String;IJIILjava/util/List;Ljava/util/List;J)V", "getAchievements", "()Ljava/util/List;", "getCurrentXp", "()I", "getGamesPlayed", "getHighestScore", "getLastSync", "()J", "getLevel", "getRank", "()Ljava/lang/String;", "getTotalLines", "getTotalScore", "getTotalXp", "getUnlocks", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class ProgressionData {
    @com.google.gson.annotations.SerializedName(value = "level")
    private final int level = 0;
    @com.google.gson.annotations.SerializedName(value = "current_xp")
    private final int currentXp = 0;
    @com.google.gson.annotations.SerializedName(value = "total_xp")
    private final int totalXp = 0;
    @com.google.gson.annotations.SerializedName(value = "rank")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String rank = null;
    @com.google.gson.annotations.SerializedName(value = "games_played")
    private final int gamesPlayed = 0;
    @com.google.gson.annotations.SerializedName(value = "total_score")
    private final long totalScore = 0L;
    @com.google.gson.annotations.SerializedName(value = "total_lines")
    private final int totalLines = 0;
    @com.google.gson.annotations.SerializedName(value = "highest_score")
    private final int highestScore = 0;
    @com.google.gson.annotations.SerializedName(value = "achievements")
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> achievements = null;
    @com.google.gson.annotations.SerializedName(value = "unlocks")
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> unlocks = null;
    @com.google.gson.annotations.SerializedName(value = "last_sync")
    private final long lastSync = 0L;
    
    public ProgressionData(int level, int currentXp, int totalXp, @org.jetbrains.annotations.NotNull
    java.lang.String rank, int gamesPlayed, long totalScore, int totalLines, int highestScore, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> achievements, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlocks, long lastSync) {
        super();
    }
    
    public final int getLevel() {
        return 0;
    }
    
    public final int getCurrentXp() {
        return 0;
    }
    
    public final int getTotalXp() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRank() {
        return null;
    }
    
    public final int getGamesPlayed() {
        return 0;
    }
    
    public final long getTotalScore() {
        return 0L;
    }
    
    public final int getTotalLines() {
        return 0;
    }
    
    public final int getHighestScore() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getUnlocks() {
        return null;
    }
    
    public final long getLastSync() {
        return 0L;
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component10() {
        return null;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final long component6() {
        return 0L;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.ProgressionData copy(int level, int currentXp, int totalXp, @org.jetbrains.annotations.NotNull
    java.lang.String rank, int gamesPlayed, long totalScore, int totalLines, int highestScore, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> achievements, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlocks, long lastSync) {
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