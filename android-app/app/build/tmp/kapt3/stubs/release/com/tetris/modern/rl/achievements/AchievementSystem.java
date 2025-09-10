package com.tetris.modern.rl.achievements;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001:\u0006234567B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00110\u000b2\u0006\u0010\u001e\u001a\u00020\u001fJ$\u0010!\u001a \u0012\u0004\u0012\u00020\"\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\t0#0\u000b0\u0007J\u0006\u0010$\u001a\u00020%J\u0006\u0010&\u001a\u00020\u001cJ\u0006\u0010\'\u001a\u00020\u001cJ\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u001c0\u0007J\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u001c0\u0007J\u0006\u0010+\u001a\u00020\u001cJ\b\u0010,\u001a\u00020-H\u0002J\u0006\u0010.\u001a\u00020-J\u0010\u0010/\u001a\u00020-2\u0006\u0010\u001d\u001a\u00020\u0011H\u0002J\u000e\u00100\u001a\u00020-2\u0006\u00101\u001a\u00020\u001cR \u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001d\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000f\u00a8\u00068"}, d2 = {"Lcom/tetris/modern/rl/achievements/AchievementSystem;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_achievementProgress", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "Lcom/tetris/modern/rl/achievements/AchievementSystem$AchievementProgress;", "_unlockedAchievements", "", "achievementProgress", "Lkotlinx/coroutines/flow/StateFlow;", "getAchievementProgress", "()Lkotlinx/coroutines/flow/StateFlow;", "achievements", "Lcom/tetris/modern/rl/achievements/AchievementSystem$Achievement;", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "liveNotifications", "Landroidx/compose/runtime/snapshots/SnapshotStateList;", "Lcom/tetris/modern/rl/achievements/AchievementSystem$AchievementNotification;", "getLiveNotifications", "()Landroidx/compose/runtime/snapshots/SnapshotStateList;", "unlockedAchievements", "getUnlockedAchievements", "calculateProgress", "", "achievement", "stats", "Lcom/tetris/modern/rl/achievements/AchievementSystem$GameStats;", "checkAchievements", "getAchievementsByCategory", "Lcom/tetris/modern/rl/achievements/AchievementSystem$AchievementCategory;", "Lkotlin/Pair;", "getCompletionPercentage", "", "getTotalAchievementPoints", "getTotalTrophiesCount", "getTrophyCount", "Lcom/tetris/modern/rl/achievements/AchievementSystem$Trophy;", "getUnlockedTrophies", "getUnlockedTrophiesCount", "initializeAchievements", "", "reset", "showLiveNotification", "updateDailyStreak", "streakDays", "Achievement", "AchievementCategory", "AchievementNotification", "AchievementProgress", "GameStats", "Trophy", "app_release"})
public final class AchievementSystem {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.runtime.snapshots.SnapshotStateList<com.tetris.modern.rl.achievements.AchievementSystem.AchievementNotification> liveNotifications = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope coroutineScope = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.achievements.AchievementSystem.Achievement> achievements = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, com.tetris.modern.rl.achievements.AchievementSystem.AchievementProgress>> _achievementProgress = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.tetris.modern.rl.achievements.AchievementSystem.AchievementProgress>> achievementProgress = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _unlockedAchievements = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> unlockedAchievements = null;
    
    @javax.inject.Inject
    public AchievementSystem(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.runtime.snapshots.SnapshotStateList<com.tetris.modern.rl.achievements.AchievementSystem.AchievementNotification> getLiveNotifications() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.tetris.modern.rl.achievements.AchievementSystem.AchievementProgress>> getAchievementProgress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.String>> getUnlockedAchievements() {
        return null;
    }
    
    private final void initializeAchievements() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.achievements.AchievementSystem.Achievement> checkAchievements(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.achievements.AchievementSystem.GameStats stats) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<com.tetris.modern.rl.achievements.AchievementSystem.Trophy, java.lang.Integer> getTrophyCount() {
        return null;
    }
    
    public final int getTotalTrophiesCount() {
        return 0;
    }
    
    public final int getUnlockedTrophiesCount() {
        return 0;
    }
    
    private final int calculateProgress(com.tetris.modern.rl.achievements.AchievementSystem.Achievement achievement, com.tetris.modern.rl.achievements.AchievementSystem.GameStats stats) {
        return 0;
    }
    
    public final void updateDailyStreak(int streakDays) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<com.tetris.modern.rl.achievements.AchievementSystem.AchievementCategory, java.util.List<kotlin.Pair<com.tetris.modern.rl.achievements.AchievementSystem.Achievement, com.tetris.modern.rl.achievements.AchievementSystem.AchievementProgress>>> getAchievementsByCategory() {
        return null;
    }
    
    public final int getTotalAchievementPoints() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<com.tetris.modern.rl.achievements.AchievementSystem.Trophy, java.lang.Integer> getUnlockedTrophies() {
        return null;
    }
    
    private final void showLiveNotification(com.tetris.modern.rl.achievements.AchievementSystem.Achievement achievement) {
    }
    
    public final float getCompletionPercentage() {
        return 0.0F;
    }
    
    public final void reset() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001e\b\u0086\b\u0018\u00002\u00020\u0001Bc\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000b\u0012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0\u0010\u00a2\u0006\u0002\u0010\u0012J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u0010&\u001a\u00020\u000bH\u00c6\u0003J\t\u0010\'\u001a\u00020\rH\u00c6\u0003J\t\u0010(\u001a\u00020\u000bH\u00c6\u0003J\u0015\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0\u0010H\u00c6\u0003Jq\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000b2\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0\u0010H\u00c6\u0001J\u0013\u0010+\u001a\u00020\r2\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010-\u001a\u00020\u000bH\u00d6\u0001J\t\u0010.\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001b\u00a8\u0006/"}, d2 = {"Lcom/tetris/modern/rl/achievements/AchievementSystem$Achievement;", "", "id", "", "name", "description", "category", "Lcom/tetris/modern/rl/achievements/AchievementSystem$AchievementCategory;", "trophy", "Lcom/tetris/modern/rl/achievements/AchievementSystem$Trophy;", "xpReward", "", "isSecret", "", "progressMax", "unlockCondition", "Lkotlin/Function1;", "Lcom/tetris/modern/rl/achievements/AchievementSystem$GameStats;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/tetris/modern/rl/achievements/AchievementSystem$AchievementCategory;Lcom/tetris/modern/rl/achievements/AchievementSystem$Trophy;IZILkotlin/jvm/functions/Function1;)V", "getCategory", "()Lcom/tetris/modern/rl/achievements/AchievementSystem$AchievementCategory;", "getDescription", "()Ljava/lang/String;", "getId", "()Z", "getName", "getProgressMax", "()I", "getTrophy", "()Lcom/tetris/modern/rl/achievements/AchievementSystem$Trophy;", "getUnlockCondition", "()Lkotlin/jvm/functions/Function1;", "getXpReward", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_release"})
    public static final class Achievement {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String id = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String description = null;
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.achievements.AchievementSystem.AchievementCategory category = null;
        @org.jetbrains.annotations.Nullable
        private final com.tetris.modern.rl.achievements.AchievementSystem.Trophy trophy = null;
        private final int xpReward = 0;
        private final boolean isSecret = false;
        private final int progressMax = 0;
        @org.jetbrains.annotations.NotNull
        private final kotlin.jvm.functions.Function1<com.tetris.modern.rl.achievements.AchievementSystem.GameStats, java.lang.Boolean> unlockCondition = null;
        
        public Achievement(@org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String description, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.achievements.AchievementSystem.AchievementCategory category, @org.jetbrains.annotations.Nullable
        com.tetris.modern.rl.achievements.AchievementSystem.Trophy trophy, int xpReward, boolean isSecret, int progressMax, @org.jetbrains.annotations.NotNull
        kotlin.jvm.functions.Function1<? super com.tetris.modern.rl.achievements.AchievementSystem.GameStats, java.lang.Boolean> unlockCondition) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDescription() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.achievements.AchievementSystem.AchievementCategory getCategory() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.tetris.modern.rl.achievements.AchievementSystem.Trophy getTrophy() {
            return null;
        }
        
        public final int getXpReward() {
            return 0;
        }
        
        public final boolean isSecret() {
            return false;
        }
        
        public final int getProgressMax() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final kotlin.jvm.functions.Function1<com.tetris.modern.rl.achievements.AchievementSystem.GameStats, java.lang.Boolean> getUnlockCondition() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.achievements.AchievementSystem.AchievementCategory component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.tetris.modern.rl.achievements.AchievementSystem.Trophy component5() {
            return null;
        }
        
        public final int component6() {
            return 0;
        }
        
        public final boolean component7() {
            return false;
        }
        
        public final int component8() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final kotlin.jvm.functions.Function1<com.tetris.modern.rl.achievements.AchievementSystem.GameStats, java.lang.Boolean> component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.achievements.AchievementSystem.Achievement copy(@org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String description, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.achievements.AchievementSystem.AchievementCategory category, @org.jetbrains.annotations.Nullable
        com.tetris.modern.rl.achievements.AchievementSystem.Trophy trophy, int xpReward, boolean isSecret, int progressMax, @org.jetbrains.annotations.NotNull
        kotlin.jvm.functions.Function1<? super com.tetris.modern.rl.achievements.AchievementSystem.GameStats, java.lang.Boolean> unlockCondition) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2 = {"Lcom/tetris/modern/rl/achievements/AchievementSystem$AchievementCategory;", "", "(Ljava/lang/String;I)V", "BEGINNER", "LINES", "SCORE", "COMBO", "SPECIAL", "MODES", "DAILY", "SECRET", "app_release"})
    public static enum AchievementCategory {
        /*public static final*/ BEGINNER /* = new BEGINNER() */,
        /*public static final*/ LINES /* = new LINES() */,
        /*public static final*/ SCORE /* = new SCORE() */,
        /*public static final*/ COMBO /* = new COMBO() */,
        /*public static final*/ SPECIAL /* = new SPECIAL() */,
        /*public static final*/ MODES /* = new MODES() */,
        /*public static final*/ DAILY /* = new DAILY() */,
        /*public static final*/ SECRET /* = new SECRET() */;
        
        AchievementCategory() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.achievements.AchievementSystem.AchievementCategory> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0019"}, d2 = {"Lcom/tetris/modern/rl/achievements/AchievementSystem$AchievementNotification;", "", "achievement", "Lcom/tetris/modern/rl/achievements/AchievementSystem$Achievement;", "timestamp", "", "id", "", "(Lcom/tetris/modern/rl/achievements/AchievementSystem$Achievement;JLjava/lang/String;)V", "getAchievement", "()Lcom/tetris/modern/rl/achievements/AchievementSystem$Achievement;", "getId", "()Ljava/lang/String;", "getTimestamp", "()J", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"})
    public static final class AchievementNotification {
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.achievements.AchievementSystem.Achievement achievement = null;
        private final long timestamp = 0L;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String id = null;
        
        public AchievementNotification(@org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.achievements.AchievementSystem.Achievement achievement, long timestamp, @org.jetbrains.annotations.NotNull
        java.lang.String id) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.achievements.AchievementSystem.Achievement getAchievement() {
            return null;
        }
        
        public final long getTimestamp() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.achievements.AchievementSystem.Achievement component1() {
            return null;
        }
        
        public final long component2() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.achievements.AchievementSystem.AchievementNotification copy(@org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.achievements.AchievementSystem.Achievement achievement, long timestamp, @org.jetbrains.annotations.NotNull
        java.lang.String id) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\tH\u00c6\u0003J3\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000fR\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001b"}, d2 = {"Lcom/tetris/modern/rl/achievements/AchievementSystem$AchievementProgress;", "", "achievementId", "", "currentProgress", "", "isUnlocked", "", "unlockedDate", "Ljava/util/Date;", "(Ljava/lang/String;IZLjava/util/Date;)V", "getAchievementId", "()Ljava/lang/String;", "getCurrentProgress", "()I", "()Z", "getUnlockedDate", "()Ljava/util/Date;", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "toString", "app_release"})
    public static final class AchievementProgress {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String achievementId = null;
        private final int currentProgress = 0;
        private final boolean isUnlocked = false;
        @org.jetbrains.annotations.Nullable
        private final java.util.Date unlockedDate = null;
        
        public AchievementProgress(@org.jetbrains.annotations.NotNull
        java.lang.String achievementId, int currentProgress, boolean isUnlocked, @org.jetbrains.annotations.Nullable
        java.util.Date unlockedDate) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getAchievementId() {
            return null;
        }
        
        public final int getCurrentProgress() {
            return 0;
        }
        
        public final boolean isUnlocked() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.util.Date getUnlockedDate() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final boolean component3() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.util.Date component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.achievements.AchievementSystem.AchievementProgress copy(@org.jetbrains.annotations.NotNull
        java.lang.String achievementId, int currentProgress, boolean isUnlocked, @org.jetbrains.annotations.Nullable
        java.util.Date unlockedDate) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b!\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B}\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u000bH\u00c6\u0003J\t\u0010,\u001a\u00020\rH\u00c6\u0003J\u0081\u0001\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u00020\u0003H\u00d6\u0001J\t\u00102\u001a\u00020\u000bH\u00d6\u0001R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017\u00a8\u00063"}, d2 = {"Lcom/tetris/modern/rl/achievements/AchievementSystem$GameStats;", "", "score", "", "lines", "level", "tSpins", "tetrises", "perfectClears", "maxCombo", "gameMode", "", "duration", "", "totalGamesPlayed", "totalLinesCleared", "highestScore", "(IIIIIIILjava/lang/String;JIII)V", "getDuration", "()J", "getGameMode", "()Ljava/lang/String;", "getHighestScore", "()I", "getLevel", "getLines", "getMaxCombo", "getPerfectClears", "getScore", "getTSpins", "getTetrises", "getTotalGamesPlayed", "getTotalLinesCleared", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_release"})
    public static final class GameStats {
        private final int score = 0;
        private final int lines = 0;
        private final int level = 0;
        private final int tSpins = 0;
        private final int tetrises = 0;
        private final int perfectClears = 0;
        private final int maxCombo = 0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String gameMode = null;
        private final long duration = 0L;
        private final int totalGamesPlayed = 0;
        private final int totalLinesCleared = 0;
        private final int highestScore = 0;
        
        public GameStats(int score, int lines, int level, int tSpins, int tetrises, int perfectClears, int maxCombo, @org.jetbrains.annotations.NotNull
        java.lang.String gameMode, long duration, int totalGamesPlayed, int totalLinesCleared, int highestScore) {
            super();
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
        
        public final int getTSpins() {
            return 0;
        }
        
        public final int getTetrises() {
            return 0;
        }
        
        public final int getPerfectClears() {
            return 0;
        }
        
        public final int getMaxCombo() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getGameMode() {
            return null;
        }
        
        public final long getDuration() {
            return 0L;
        }
        
        public final int getTotalGamesPlayed() {
            return 0;
        }
        
        public final int getTotalLinesCleared() {
            return 0;
        }
        
        public final int getHighestScore() {
            return 0;
        }
        
        public GameStats() {
            super();
        }
        
        public final int component1() {
            return 0;
        }
        
        public final int component10() {
            return 0;
        }
        
        public final int component11() {
            return 0;
        }
        
        public final int component12() {
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
        
        public final int component6() {
            return 0;
        }
        
        public final int component7() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component8() {
            return null;
        }
        
        public final long component9() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.achievements.AchievementSystem.GameStats copy(int score, int lines, int level, int tSpins, int tetrises, int perfectClears, int maxCombo, @org.jetbrains.annotations.NotNull
        java.lang.String gameMode, long duration, int totalGamesPlayed, int totalLinesCleared, int highestScore) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/tetris/modern/rl/achievements/AchievementSystem$Trophy;", "", "displayName", "", "color", "xpReward", "", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;I)V", "getColor", "()Ljava/lang/String;", "getDisplayName", "getXpReward", "()I", "BRONZE", "SILVER", "GOLD", "PLATINUM", "app_release"})
    public static enum Trophy {
        /*public static final*/ BRONZE /* = new BRONZE(null, null, 0) */,
        /*public static final*/ SILVER /* = new SILVER(null, null, 0) */,
        /*public static final*/ GOLD /* = new GOLD(null, null, 0) */,
        /*public static final*/ PLATINUM /* = new PLATINUM(null, null, 0) */;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String displayName = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String color = null;
        private final int xpReward = 0;
        
        Trophy(java.lang.String displayName, java.lang.String color, int xpReward) {
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDisplayName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getColor() {
            return null;
        }
        
        public final int getXpReward() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.achievements.AchievementSystem.Trophy> getEntries() {
            return null;
        }
    }
}