package com.tetris.modern.rl.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0012\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\u0011\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bH\u00c6\u0003J9\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bH\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00032\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\tH\u00d6\u0001R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001b"}, d2 = {"Lcom/tetris/modern/rl/network/models/DailyChallengeResultResponse;", "", "success", "", "rank", "", "streak", "rewards", "", "", "(ZIILjava/util/List;)V", "getRank", "()I", "getRewards", "()Ljava/util/List;", "getStreak", "getSuccess", "()Z", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class DailyChallengeResultResponse {
    @com.google.gson.annotations.SerializedName(value = "success")
    private final boolean success = false;
    @com.google.gson.annotations.SerializedName(value = "rank")
    private final int rank = 0;
    @com.google.gson.annotations.SerializedName(value = "streak")
    private final int streak = 0;
    @com.google.gson.annotations.SerializedName(value = "rewards")
    @org.jetbrains.annotations.Nullable
    private final java.util.List<java.lang.String> rewards = null;
    
    public DailyChallengeResultResponse(boolean success, int rank, int streak, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> rewards) {
        super();
    }
    
    public final boolean getSuccess() {
        return false;
    }
    
    public final int getRank() {
        return 0;
    }
    
    public final int getStreak() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> getRewards() {
        return null;
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.DailyChallengeResultResponse copy(boolean success, int rank, int streak, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> rewards) {
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