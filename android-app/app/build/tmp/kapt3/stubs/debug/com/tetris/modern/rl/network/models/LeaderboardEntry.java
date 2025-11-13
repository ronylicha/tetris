package com.tetris.modern.rl.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001Bo\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0011\u0010#\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0010H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0005H\u00c6\u0003J\t\u0010*\u001a\u00020\fH\u00c6\u0003J\t\u0010+\u001a\u00020\u0005H\u00c6\u0003J\u0083\u0001\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u0010\b\u0002\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0010H\u00c6\u0001J\u0013\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u00020\u0003H\u00d6\u0001J\t\u00101\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\r\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u000b\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\n\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0018R\u001e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00108\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 \u00a8\u00062"}, d2 = {"Lcom/tetris/modern/rl/network/models/LeaderboardEntry;", "", "rank", "", "playerId", "", "playerName", "score", "lines", "level", "gameMode", "duration", "", "date", "modeData", "specialAchievements", "", "(ILjava/lang/String;Ljava/lang/String;IIILjava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getDate", "()Ljava/lang/String;", "getDuration", "()J", "getGameMode", "getLevel", "()I", "getLines", "getModeData", "getPlayerId", "getPlayerName", "getRank", "getScore", "getSpecialAchievements", "()Ljava/util/List;", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class LeaderboardEntry {
    @com.google.gson.annotations.SerializedName(value = "rank")
    private final int rank = 0;
    @com.google.gson.annotations.SerializedName(value = "player_id")
    @org.jetbrains.annotations.Nullable
    private final java.lang.String playerId = null;
    @com.google.gson.annotations.SerializedName(value = "player_name")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String playerName = null;
    @com.google.gson.annotations.SerializedName(value = "score")
    private final int score = 0;
    @com.google.gson.annotations.SerializedName(value = "lines")
    private final int lines = 0;
    @com.google.gson.annotations.SerializedName(value = "level")
    private final int level = 0;
    @com.google.gson.annotations.SerializedName(value = "game_mode")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String gameMode = null;
    @com.google.gson.annotations.SerializedName(value = "game_duration")
    private final long duration = 0L;
    @com.google.gson.annotations.SerializedName(value = "date_achieved")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String date = null;
    @com.google.gson.annotations.SerializedName(value = "mode_data")
    @org.jetbrains.annotations.Nullable
    private final java.lang.String modeData = null;
    @com.google.gson.annotations.SerializedName(value = "special_achievements")
    @org.jetbrains.annotations.Nullable
    private final java.util.List<java.lang.String> specialAchievements = null;
    
    public LeaderboardEntry(int rank, @org.jetbrains.annotations.Nullable
    java.lang.String playerId, @org.jetbrains.annotations.NotNull
    java.lang.String playerName, int score, int lines, int level, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, long duration, @org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.Nullable
    java.lang.String modeData, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> specialAchievements) {
        super();
    }
    
    public final int getRank() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getPlayerId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPlayerName() {
        return null;
    }
    
    public final int getScore() {
        return 0;
    }
    
    public final int getLines() {
        return 0;
    }
    
    public final int getLevel() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGameMode() {
        return null;
    }
    
    public final long getDuration() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getModeData() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> getSpecialAchievements() {
        return null;
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
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
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component7() {
        return null;
    }
    
    public final long component8() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.LeaderboardEntry copy(int rank, @org.jetbrains.annotations.Nullable
    java.lang.String playerId, @org.jetbrains.annotations.NotNull
    java.lang.String playerName, int score, int lines, int level, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, long duration, @org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.Nullable
    java.lang.String modeData, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> specialAchievements) {
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