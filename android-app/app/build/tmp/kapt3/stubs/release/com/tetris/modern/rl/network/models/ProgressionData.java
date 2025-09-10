package com.tetris.modern.rl.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\fH\u00c6\u0003JO\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u00c6\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010$\u001a\u00020\u0005H\u00d6\u0001J\t\u0010%\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0016\u0010\u000b\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006&"}, d2 = {"Lcom/tetris/modern/rl/network/models/ProgressionData;", "", "playerId", "", "level", "", "currentXP", "totalXP", "rank", "unlockedItems", "Lcom/tetris/modern/rl/network/models/UnlockedItems;", "statistics", "Lcom/tetris/modern/rl/network/models/PlayerStatistics;", "(Ljava/lang/String;IIILjava/lang/String;Lcom/tetris/modern/rl/network/models/UnlockedItems;Lcom/tetris/modern/rl/network/models/PlayerStatistics;)V", "getCurrentXP", "()I", "getLevel", "getPlayerId", "()Ljava/lang/String;", "getRank", "getStatistics", "()Lcom/tetris/modern/rl/network/models/PlayerStatistics;", "getTotalXP", "getUnlockedItems", "()Lcom/tetris/modern/rl/network/models/UnlockedItems;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "app_release"})
public final class ProgressionData {
    @com.google.gson.annotations.SerializedName(value = "player_id")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String playerId = null;
    @com.google.gson.annotations.SerializedName(value = "level")
    private final int level = 0;
    @com.google.gson.annotations.SerializedName(value = "current_xp")
    private final int currentXP = 0;
    @com.google.gson.annotations.SerializedName(value = "total_xp")
    private final int totalXP = 0;
    @com.google.gson.annotations.SerializedName(value = "rank")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String rank = null;
    @com.google.gson.annotations.SerializedName(value = "unlocked_items")
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.network.models.UnlockedItems unlockedItems = null;
    @com.google.gson.annotations.SerializedName(value = "statistics")
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.network.models.PlayerStatistics statistics = null;
    
    public ProgressionData(@org.jetbrains.annotations.NotNull
    java.lang.String playerId, int level, int currentXP, int totalXP, @org.jetbrains.annotations.NotNull
    java.lang.String rank, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.UnlockedItems unlockedItems, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.PlayerStatistics statistics) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPlayerId() {
        return null;
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
    public final com.tetris.modern.rl.network.models.UnlockedItems getUnlockedItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.PlayerStatistics getStatistics() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
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
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.UnlockedItems component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.PlayerStatistics component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.ProgressionData copy(@org.jetbrains.annotations.NotNull
    java.lang.String playerId, int level, int currentXP, int totalXP, @org.jetbrains.annotations.NotNull
    java.lang.String rank, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.UnlockedItems unlockedItems, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.PlayerStatistics statistics) {
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