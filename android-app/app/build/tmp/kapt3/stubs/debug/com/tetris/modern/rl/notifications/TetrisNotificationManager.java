package com.tetris.modern.rl.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.tetris.modern.rl.MainActivity;
import com.tetris.modern.rl.R;
import com.tetris.modern.rl.data.repository.ScoreSubmitResult;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tJ\b\u0010\n\u001a\u00020\u0006H\u0002J)\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\tH\u0002\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\tH\u0002J%\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0016\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\tJ\u0006\u0010\u001c\u001a\u00020\u0006J\u001e\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\tJ\u001e\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\tJ\u0016\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\"J\u001e\u0010#\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/tetris/modern/rl/notifications/TetrisNotificationManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "cancelAllNotifications", "", "cancelNotification", "notificationId", "", "createNotificationChannels", "createShareIntent", "Landroid/app/PendingIntent;", "score", "gameMode", "", "rank", "(ILjava/lang/String;Ljava/lang/Integer;)Landroid/app/PendingIntent;", "formatScore", "handleScoreSubmitResult", "result", "Lcom/tetris/modern/rl/data/repository/ScoreSubmitResult;", "previousBest", "(Lcom/tetris/modern/rl/data/repository/ScoreSubmitResult;Ljava/lang/String;Ljava/lang/Integer;)V", "showAchievementUnlockedNotification", "achievementName", "achievementDescription", "xpReward", "showDailyChallengeNotification", "showGlobalRecordNotification", "showNewPersonalBestNotification", "showSyncCompleteNotification", "syncedCount", "success", "", "showTop10EntryNotification", "Companion", "app_debug"})
public final class TetrisNotificationManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CHANNEL_ID_RECORDS = "tetris_records";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CHANNEL_ID_SYNC = "tetris_sync";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CHANNEL_ID_DAILY = "tetris_daily";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CHANNEL_ID_ACHIEVEMENTS = "tetris_achievements";
    public static final int NOTIFICATION_ID_PERSONAL_BEST = 1001;
    public static final int NOTIFICATION_ID_GLOBAL_RECORD = 1002;
    public static final int NOTIFICATION_ID_TOP_10 = 1003;
    public static final int NOTIFICATION_ID_SYNC = 2001;
    public static final int NOTIFICATION_ID_DAILY_CHALLENGE = 3001;
    public static final int NOTIFICATION_ID_ACHIEVEMENT = 4001;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.notifications.TetrisNotificationManager.Companion Companion = null;
    
    @javax.inject.Inject
    public TetrisNotificationManager(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    private final void createNotificationChannels() {
    }
    
    public final void showNewPersonalBestNotification(@org.jetbrains.annotations.NotNull
    java.lang.String gameMode, int score, int previousBest) {
    }
    
    public final void showGlobalRecordNotification(@org.jetbrains.annotations.NotNull
    java.lang.String gameMode, int score, int rank) {
    }
    
    public final void showTop10EntryNotification(@org.jetbrains.annotations.NotNull
    java.lang.String gameMode, int score, int rank) {
    }
    
    public final void showSyncCompleteNotification(int syncedCount, boolean success) {
    }
    
    public final void showDailyChallengeNotification() {
    }
    
    public final void showAchievementUnlockedNotification(@org.jetbrains.annotations.NotNull
    java.lang.String achievementName, @org.jetbrains.annotations.NotNull
    java.lang.String achievementDescription, int xpReward) {
    }
    
    public final void handleScoreSubmitResult(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.repository.ScoreSubmitResult result, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, @org.jetbrains.annotations.Nullable
    java.lang.Integer previousBest) {
    }
    
    private final android.app.PendingIntent createShareIntent(int score, java.lang.String gameMode, java.lang.Integer rank) {
        return null;
    }
    
    private final java.lang.String formatScore(int score) {
        return null;
    }
    
    public final void cancelNotification(int notificationId) {
    }
    
    public final void cancelAllNotifications() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/tetris/modern/rl/notifications/TetrisNotificationManager$Companion;", "", "()V", "CHANNEL_ID_ACHIEVEMENTS", "", "CHANNEL_ID_DAILY", "CHANNEL_ID_RECORDS", "CHANNEL_ID_SYNC", "NOTIFICATION_ID_ACHIEVEMENT", "", "NOTIFICATION_ID_DAILY_CHALLENGE", "NOTIFICATION_ID_GLOBAL_RECORD", "NOTIFICATION_ID_PERSONAL_BEST", "NOTIFICATION_ID_SYNC", "NOTIFICATION_ID_TOP_10", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}