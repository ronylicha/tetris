package com.tetris.modern.rl.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001Bq\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u000e\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u0015\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u000eH\u00c6\u0003J\u0087\u0001\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u000eH\u00c6\u0001J\u0013\u0010\'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020\u0003H\u00d6\u0001J\t\u0010+\u001a\u00020\u0007H\u00d6\u0001R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\"\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019\u00a8\u0006,"}, d2 = {"Lcom/tetris/modern/rl/network/models/ProgressionServerData;", "", "level", "", "currentXP", "totalXP", "rank", "", "unlockedThemes", "", "unlockedMusic", "unlockedPieceStyles", "unlockedEffects", "statistics", "", "(IIILjava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/Map;)V", "getCurrentXP", "()I", "getLevel", "getRank", "()Ljava/lang/String;", "getStatistics", "()Ljava/util/Map;", "getTotalXP", "getUnlockedEffects", "()Ljava/util/List;", "getUnlockedMusic", "getUnlockedPieceStyles", "getUnlockedThemes", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class ProgressionServerData {
    @com.google.gson.annotations.SerializedName(value = "level")
    private final int level = 0;
    @com.google.gson.annotations.SerializedName(value = "current_xp")
    private final int currentXP = 0;
    @com.google.gson.annotations.SerializedName(value = "total_xp")
    private final int totalXP = 0;
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
    @org.jetbrains.annotations.NotNull
    private final java.util.Map<java.lang.String, java.lang.Object> statistics = null;
    
    public ProgressionServerData(int level, int currentXP, int totalXP, @org.jetbrains.annotations.NotNull
    java.lang.String rank, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedThemes, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedMusic, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedPieceStyles, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedEffects, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends java.lang.Object> statistics) {
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
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.Object> getStatistics() {
        return null;
    }
    
    public final int component1() {
        return 0;
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
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component6() {
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
    public final java.util.Map<java.lang.String, java.lang.Object> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.ProgressionServerData copy(int level, int currentXP, int totalXP, @org.jetbrains.annotations.NotNull
    java.lang.String rank, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedThemes, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedMusic, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedPieceStyles, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> unlockedEffects, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends java.lang.Object> statistics) {
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