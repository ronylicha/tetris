package com.tetris.modern.rl.audio;

import android.app.*;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import com.tetris.modern.rl.MainActivity;
import com.tetris.modern.rl.R;
import timber.log.Timber;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u0000 \"2\u00020\u0001:\u0002\"#B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\fH\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u0006H\u0002J\u0012\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\fH\u0016J\b\u0010\u0018\u001a\u00020\fH\u0016J\"\u0010\u0019\u001a\u00020\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0012H\u0016J\u0006\u0010\u001c\u001a\u00020\fJ\u000e\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0006\u0010 \u001a\u00020\fJ\u0006\u0010!\u001a\u00020\fR\u0012\u0010\u0003\u001a\u00060\u0004R\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/tetris/modern/rl/audio/MusicService;", "Landroid/app/Service;", "()V", "binder", "Lcom/tetris/modern/rl/audio/MusicService$MusicBinder;", "currentMusic", "", "isPlaying", "", "mediaPlayer", "Landroid/media/MediaPlayer;", "changeMusic", "", "track", "createNotification", "Landroid/app/Notification;", "createNotificationChannel", "getMusicResource", "", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "pauseMusic", "setVolume", "volume", "", "startMusic", "stopMusic", "Companion", "MusicBinder", "app_debug"})
public final class MusicService extends android.app.Service {
    @org.jetbrains.annotations.Nullable
    private android.media.MediaPlayer mediaPlayer;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.audio.MusicService.MusicBinder binder = null;
    @org.jetbrains.annotations.NotNull
    private java.lang.String currentMusic = "theme";
    private boolean isPlaying = false;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CHANNEL_ID = "TetrisMusicChannel";
    public static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.audio.MusicService.Companion Companion = null;
    
    public MusicService() {
        super();
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    private final void createNotificationChannel() {
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    public final void startMusic() {
    }
    
    public final void pauseMusic() {
    }
    
    public final void stopMusic() {
    }
    
    public final void changeMusic(@org.jetbrains.annotations.NotNull
    java.lang.String track) {
    }
    
    public final void setVolume(float volume) {
    }
    
    private final int getMusicResource(java.lang.String track) {
        return 0;
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/tetris/modern/rl/audio/MusicService$Companion;", "", "()V", "CHANNEL_ID", "", "NOTIFICATION_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/tetris/modern/rl/audio/MusicService$MusicBinder;", "Landroid/os/Binder;", "(Lcom/tetris/modern/rl/audio/MusicService;)V", "getService", "Lcom/tetris/modern/rl/audio/MusicService;", "app_debug"})
    public final class MusicBinder extends android.os.Binder {
        
        public MusicBinder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.audio.MusicService getService() {
            return null;
        }
    }
}