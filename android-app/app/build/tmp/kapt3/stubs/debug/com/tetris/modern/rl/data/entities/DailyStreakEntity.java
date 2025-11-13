package com.tetris.modern.rl.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u001c\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001Bo\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u000eH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003Js\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\r\u001a\u00020\u000eH\u00c6\u0001J\u0013\u0010\'\u001a\u00020\u000e2\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010)\u001a\u00020\u0003H\u00d6\u0001J\t\u0010*\u001a\u00020+H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0014R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011\u00a8\u0006,"}, d2 = {"Lcom/tetris/modern/rl/data/entities/DailyStreakEntity;", "", "id", "", "currentStreak", "longestStreak", "lastLoginDate", "Ljava/util/Date;", "totalDaysPlayed", "lastCompletedChallengeDate", "dailyChallengesCompleted", "missedDays", "streakStartDate", "isStreakActive", "", "(IIILjava/util/Date;ILjava/util/Date;IILjava/util/Date;Z)V", "getCurrentStreak", "()I", "getDailyChallengesCompleted", "getId", "()Z", "getLastCompletedChallengeDate", "()Ljava/util/Date;", "getLastLoginDate", "getLongestStreak", "getMissedDays", "getStreakStartDate", "getTotalDaysPlayed", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
@androidx.room.Entity(tableName = "daily_streaks")
public final class DailyStreakEntity {
    @androidx.room.PrimaryKey
    private final int id = 0;
    private final int currentStreak = 0;
    private final int longestStreak = 0;
    @org.jetbrains.annotations.Nullable
    private final java.util.Date lastLoginDate = null;
    private final int totalDaysPlayed = 0;
    @org.jetbrains.annotations.Nullable
    private final java.util.Date lastCompletedChallengeDate = null;
    private final int dailyChallengesCompleted = 0;
    private final int missedDays = 0;
    @org.jetbrains.annotations.Nullable
    private final java.util.Date streakStartDate = null;
    private final boolean isStreakActive = false;
    
    public DailyStreakEntity(int id, int currentStreak, int longestStreak, @org.jetbrains.annotations.Nullable
    java.util.Date lastLoginDate, int totalDaysPlayed, @org.jetbrains.annotations.Nullable
    java.util.Date lastCompletedChallengeDate, int dailyChallengesCompleted, int missedDays, @org.jetbrains.annotations.Nullable
    java.util.Date streakStartDate, boolean isStreakActive) {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    public final int getCurrentStreak() {
        return 0;
    }
    
    public final int getLongestStreak() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date getLastLoginDate() {
        return null;
    }
    
    public final int getTotalDaysPlayed() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date getLastCompletedChallengeDate() {
        return null;
    }
    
    public final int getDailyChallengesCompleted() {
        return 0;
    }
    
    public final int getMissedDays() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date getStreakStartDate() {
        return null;
    }
    
    public final boolean isStreakActive() {
        return false;
    }
    
    public DailyStreakEntity() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date component6() {
        return null;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.entities.DailyStreakEntity copy(int id, int currentStreak, int longestStreak, @org.jetbrains.annotations.Nullable
    java.util.Date lastLoginDate, int totalDaysPlayed, @org.jetbrains.annotations.Nullable
    java.util.Date lastCompletedChallengeDate, int dailyChallengesCompleted, int missedDays, @org.jetbrains.annotations.Nullable
    java.util.Date streakStartDate, boolean isStreakActive) {
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