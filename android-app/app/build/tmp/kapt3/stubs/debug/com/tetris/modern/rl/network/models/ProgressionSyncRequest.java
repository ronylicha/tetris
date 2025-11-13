package com.tetris.modern.rl.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b+\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u00b9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u0012\u0016\b\u0002\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0017J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u00c6\u0003J\u0017\u0010/\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0010H\u00c6\u0003J\t\u00100\u001a\u00020\u0006H\u00c6\u0003J\t\u00101\u001a\u00020\u0013H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0006H\u00c6\u0003J\t\u00104\u001a\u00020\u0006H\u00c6\u0003J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0006H\u00c6\u0003J\t\u00107\u001a\u00020\u0006H\u00c6\u0003J\t\u00108\u001a\u00020\u0006H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u00c6\u0003J\u000f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u00c6\u0003J\u000f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u00c6\u0003J\u00cf\u0001\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u00032\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\u0016\b\u0002\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00062\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00062\b\b\u0002\u0010\u0016\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010>\u001a\u00020?2\b\u0010@\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010A\u001a\u00020\u0006H\u00d6\u0001J\t\u0010B\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u0016\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0016\u0010\u0015\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0016\u0010\u0014\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R$\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00108\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0016\u0010\u0011\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\u0016\u0010\u0012\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0016\u0010\b\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001bR\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010)R\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010)R\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010)\u00a8\u0006C"}, d2 = {"Lcom/tetris/modern/rl/network/models/ProgressionSyncRequest;", "", "action", "", "playerId", "level", "", "currentXp", "totalXp", "rank", "unlockedThemes", "", "unlockedMusic", "unlockedPieceStyles", "unlockedEffects", "statistics", "", "totalGamesPlayed", "totalPlayTime", "", "favoriteMode", "dailyStreak", "dailyChallengesCompleted", "(Ljava/lang/String;Ljava/lang/String;IIILjava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/Map;IJLjava/lang/String;II)V", "getAction", "()Ljava/lang/String;", "getCurrentXp", "()I", "getDailyChallengesCompleted", "getDailyStreak", "getFavoriteMode", "getLevel", "getPlayerId", "getRank", "getStatistics", "()Ljava/util/Map;", "getTotalGamesPlayed", "getTotalPlayTime", "()J", "getTotalXp", "getUnlockedEffects", "()Ljava/util/List;", "getUnlockedMusic", "getUnlockedPieceStyles", "getUnlockedThemes", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class ProgressionSyncRequest {
    @com.google.gson.annotations.SerializedName(value = "action")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String action = null;
    @com.google.gson.annotations.SerializedName(value = "player_id")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String playerId = null;
    @com.google.gson.annotations.SerializedName(value = "level")
    private final int level = 0;
    @com.google.gson.annotations.SerializedName(value = "current_xp")
    private final int currentXp = 0;
    @com.google.gson.annotations.SerializedName(value = "total_xp")
    private final int totalXp = 0;
    @com.google.gson.annotations.SerializedName(value = "rank")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String rank = null;
    @com.google.gson.annotations.SerializedName(value = "unlocked_themes")
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> unlockedThemes = null;
    @com.google.gson.annotations.SerializedName(value = "unlocked_music")
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> unlockedMusic = null;
    @com.google.gson.annotations.SerializedName(value = "unlocked_piece_styles")
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> unlockedPieceStyles = null;
    @com.google.gson.annotations.SerializedName(value = "unlocked_effects")
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> unlockedEffects = null;
    @com.google.gson.annotations.SerializedName(value = "statistics")
    @org.jetbrains.annotations.Nullable
    private final java.util.Map<java.lang.String, java.lang.Object> statistics = null;
    @com.google.gson.annotations.SerializedName(value = "total_games_played")
    private final int totalGamesPlayed = 0;
    @com.google.gson.annotations.SerializedName(value = "total_play_time")
    private final long totalPlayTime = 0L;
    @com.google.gson.annotations.SerializedName(value = "favorite_mode")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String favoriteMode = null;
    @com.google.gson.annotations.SerializedName(value = "daily_streak")
    private final int dailyStreak = 0;
    @com.google.gson.annotations.SerializedName(value = "daily_challenges_completed")
    private final int dailyChallengesCompleted = 0;
    
    public ProgressionSyncRequest(@org.jetbrains.annotations.NotNull
    java.lang.String action, @org.jetbrains.annotations.NotNull
    java.lang.String playerId, int level, int currentXp, int totalXp, @org.jetbrains.annotations.NotNull
    java.lang.String rank, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedThemes, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedMusic, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedPieceStyles, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedEffects, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> statistics, int totalGamesPlayed, long totalPlayTime, @org.jetbrains.annotations.NotNull
    java.lang.String favoriteMode, int dailyStreak, int dailyChallengesCompleted) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAction() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPlayerId() {
        return null;
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
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getUnlockedThemes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getUnlockedMusic() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getUnlockedPieceStyles() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getUnlockedEffects() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Map<java.lang.String, java.lang.Object> getStatistics() {
        return null;
    }
    
    public final int getTotalGamesPlayed() {
        return 0;
    }
    
    public final long getTotalPlayTime() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFavoriteMode() {
        return null;
    }
    
    public final int getDailyStreak() {
        return 0;
    }
    
    public final int getDailyChallengesCompleted() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Map<java.lang.String, java.lang.Object> component11() {
        return null;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final long component13() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component14() {
        return null;
    }
    
    public final int component15() {
        return 0;
    }
    
    public final int component16() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.ProgressionSyncRequest copy(@org.jetbrains.annotations.NotNull
    java.lang.String action, @org.jetbrains.annotations.NotNull
    java.lang.String playerId, int level, int currentXp, int totalXp, @org.jetbrains.annotations.NotNull
    java.lang.String rank, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedThemes, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedMusic, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedPieceStyles, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedEffects, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> statistics, int totalGamesPlayed, long totalPlayTime, @org.jetbrains.annotations.NotNull
    java.lang.String favoriteMode, int dailyStreak, int dailyChallengesCompleted) {
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