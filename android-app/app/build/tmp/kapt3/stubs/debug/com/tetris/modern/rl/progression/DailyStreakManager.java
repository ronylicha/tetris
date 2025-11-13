package com.tetris.modern.rl.progression;

import com.tetris.modern.rl.data.dao.DailyStreakDao;
import com.tetris.modern.rl.data.entities.DailyStreakEntity;
import kotlinx.coroutines.flow.Flow;
import timber.log.Timber;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00162\u00020\u0001:\u0002\u0016\u0017B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\fJ\u0018\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0002J\u000e\u0010\u0012\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\u0014\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\fR\u0019\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/tetris/modern/rl/progression/DailyStreakManager;", "", "dailyStreakDao", "Lcom/tetris/modern/rl/data/dao/DailyStreakDao;", "(Lcom/tetris/modern/rl/data/dao/DailyStreakDao;)V", "currentStreak", "Lkotlinx/coroutines/flow/Flow;", "Lcom/tetris/modern/rl/data/entities/DailyStreakEntity;", "getCurrentStreak", "()Lkotlinx/coroutines/flow/Flow;", "checkDailyLogin", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "completeDailyChallenge", "getDaysBetween", "startDate", "Ljava/util/Date;", "endDate", "getStreakStats", "Lcom/tetris/modern/rl/progression/DailyStreakManager$StreakStats;", "resetAllStreaks", "", "Companion", "StreakStats", "app_debug"})
public final class DailyStreakManager {
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.dao.DailyStreakDao dailyStreakDao = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.Integer> STREAK_MILESTONES = null;
    public static final int DAILY_LOGIN_XP = 50;
    public static final int STREAK_BONUS_XP = 10;
    public static final int MILESTONE_BONUS_XP = 500;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.DailyStreakEntity> currentStreak = null;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.progression.DailyStreakManager.Companion Companion = null;
    
    @javax.inject.Inject
    public DailyStreakManager(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.dao.DailyStreakDao dailyStreakDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.DailyStreakEntity> getCurrentStreak() {
        return null;
    }
    
    /**
     * Check and update daily login streak
     * Returns the XP earned from the daily login
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object checkDailyLogin(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    /**
     * Mark daily challenge as completed
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object completeDailyChallenge(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    /**
     * Get streak statistics
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getStreakStats(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.tetris.modern.rl.progression.DailyStreakManager.StreakStats> $completion) {
        return null;
    }
    
    /**
     * Calculate days between two dates
     */
    private final int getDaysBetween(java.util.Date startDate, java.util.Date endDate) {
        return 0;
    }
    
    /**
     * Reset all streak data (for testing or user request)
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object resetAllStreaks(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/tetris/modern/rl/progression/DailyStreakManager$Companion;", "", "()V", "DAILY_LOGIN_XP", "", "MILESTONE_BONUS_XP", "STREAK_BONUS_XP", "STREAK_MILESTONES", "", "getSTREAK_MILESTONES", "()Ljava/util/List;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.Integer> getSTREAK_MILESTONES() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0019\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\tH\u00c6\u0003J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013JV\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010\u001eJ\u0013\u0010\u001f\u001a\u00020\t2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\"\u001a\u00020#H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0015\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\r\u00a8\u0006$"}, d2 = {"Lcom/tetris/modern/rl/progression/DailyStreakManager$StreakStats;", "", "currentStreak", "", "longestStreak", "totalDaysPlayed", "dailyChallengesCompleted", "missedDays", "isActive", "", "nextMilestone", "(IIIIIZLjava/lang/Integer;)V", "getCurrentStreak", "()I", "getDailyChallengesCompleted", "()Z", "getLongestStreak", "getMissedDays", "getNextMilestone", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getTotalDaysPlayed", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(IIIIIZLjava/lang/Integer;)Lcom/tetris/modern/rl/progression/DailyStreakManager$StreakStats;", "equals", "other", "hashCode", "toString", "", "app_debug"})
    public static final class StreakStats {
        private final int currentStreak = 0;
        private final int longestStreak = 0;
        private final int totalDaysPlayed = 0;
        private final int dailyChallengesCompleted = 0;
        private final int missedDays = 0;
        private final boolean isActive = false;
        @org.jetbrains.annotations.Nullable
        private final java.lang.Integer nextMilestone = null;
        
        public StreakStats(int currentStreak, int longestStreak, int totalDaysPlayed, int dailyChallengesCompleted, int missedDays, boolean isActive, @org.jetbrains.annotations.Nullable
        java.lang.Integer nextMilestone) {
            super();
        }
        
        public final int getCurrentStreak() {
            return 0;
        }
        
        public final int getLongestStreak() {
            return 0;
        }
        
        public final int getTotalDaysPlayed() {
            return 0;
        }
        
        public final int getDailyChallengesCompleted() {
            return 0;
        }
        
        public final int getMissedDays() {
            return 0;
        }
        
        public final boolean isActive() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Integer getNextMilestone() {
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
        
        public final int component4() {
            return 0;
        }
        
        public final int component5() {
            return 0;
        }
        
        public final boolean component6() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Integer component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.progression.DailyStreakManager.StreakStats copy(int currentStreak, int longestStreak, int totalDaysPlayed, int dailyChallengesCompleted, int missedDays, boolean isActive, @org.jetbrains.annotations.Nullable
        java.lang.Integer nextMilestone) {
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
}