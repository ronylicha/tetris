package com.tetris.modern.rl.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b(\b\u0086\b\u0018\u00002\u00020\u0001B}\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\r\u0012\u0006\u0010\u000e\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\u0013J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010)\u001a\u00020\u0011H\u00c6\u0003J\u0010\u0010*\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\t\u0010+\u001a\u00020\u0005H\u00c6\u0003J\t\u0010,\u001a\u00020\u0007H\u00c6\u0003J\t\u0010-\u001a\u00020\u0005H\u00c6\u0003J\t\u0010.\u001a\u00020\u0005H\u00c6\u0003J\t\u0010/\u001a\u00020\u0007H\u00c6\u0003J\u0010\u00100\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0011\u00101\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\rH\u00c6\u0003J\t\u00102\u001a\u00020\u0007H\u00c6\u0003J\u0094\u0001\u00103\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00072\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00072\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001\u00a2\u0006\u0002\u00104J\u0013\u00105\u001a\u00020\u00112\b\u00106\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00107\u001a\u00020\u0007H\u00d6\u0001J\t\u00108\u001a\u00020\u0005H\u00d6\u0001R\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\u0010\u001a\u00020\u00118\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018R\u0016\u0010\t\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0016\u0010\n\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0016\u0010\b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0018R\u001e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001eR\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b%\u0010\u0015R\u0016\u0010\u000e\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001e\u00a8\u00069"}, d2 = {"Lcom/tetris/modern/rl/network/models/DailyChallenge;", "", "id", "", "date", "", "seed", "", "mode", "goalType", "goalValue", "timeLimit", "modifiers", "", "xpReward", "bonusReward", "completed", "", "bestScore", "(JLjava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/lang/Integer;Ljava/util/List;ILjava/lang/String;ZLjava/lang/Integer;)V", "getBestScore", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBonusReward", "()Ljava/lang/String;", "getCompleted", "()Z", "getDate", "getGoalType", "getGoalValue", "()I", "getId", "()J", "getMode", "getModifiers", "()Ljava/util/List;", "getSeed", "getTimeLimit", "getXpReward", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;ILjava/lang/String;Ljava/lang/String;ILjava/lang/Integer;Ljava/util/List;ILjava/lang/String;ZLjava/lang/Integer;)Lcom/tetris/modern/rl/network/models/DailyChallenge;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class DailyChallenge {
    @com.google.gson.annotations.SerializedName(value = "id")
    private final long id = 0L;
    @com.google.gson.annotations.SerializedName(value = "date")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String date = null;
    @com.google.gson.annotations.SerializedName(value = "seed")
    private final int seed = 0;
    @com.google.gson.annotations.SerializedName(value = "mode")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String mode = null;
    @com.google.gson.annotations.SerializedName(value = "goal_type")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String goalType = null;
    @com.google.gson.annotations.SerializedName(value = "goal_value")
    private final int goalValue = 0;
    @com.google.gson.annotations.SerializedName(value = "time_limit")
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer timeLimit = null;
    @com.google.gson.annotations.SerializedName(value = "modifiers")
    @org.jetbrains.annotations.Nullable
    private final java.util.List<java.lang.String> modifiers = null;
    @com.google.gson.annotations.SerializedName(value = "xp_reward")
    private final int xpReward = 0;
    @com.google.gson.annotations.SerializedName(value = "bonus_reward")
    @org.jetbrains.annotations.Nullable
    private final java.lang.String bonusReward = null;
    @com.google.gson.annotations.SerializedName(value = "completed")
    private final boolean completed = false;
    @com.google.gson.annotations.SerializedName(value = "best_score")
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer bestScore = null;
    
    public DailyChallenge(long id, @org.jetbrains.annotations.NotNull
    java.lang.String date, int seed, @org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    java.lang.String goalType, int goalValue, @org.jetbrains.annotations.Nullable
    java.lang.Integer timeLimit, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> modifiers, int xpReward, @org.jetbrains.annotations.Nullable
    java.lang.String bonusReward, boolean completed, @org.jetbrains.annotations.Nullable
    java.lang.Integer bestScore) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDate() {
        return null;
    }
    
    public final int getSeed() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGoalType() {
        return null;
    }
    
    public final int getGoalValue() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getTimeLimit() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> getModifiers() {
        return null;
    }
    
    public final int getXpReward() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getBonusReward() {
        return null;
    }
    
    public final boolean getCompleted() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getBestScore() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component10() {
        return null;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.DailyChallenge copy(long id, @org.jetbrains.annotations.NotNull
    java.lang.String date, int seed, @org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    java.lang.String goalType, int goalValue, @org.jetbrains.annotations.Nullable
    java.lang.Integer timeLimit, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> modifiers, int xpReward, @org.jetbrains.annotations.Nullable
    java.lang.String bonusReward, boolean completed, @org.jetbrains.annotations.Nullable
    java.lang.Integer bestScore) {
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