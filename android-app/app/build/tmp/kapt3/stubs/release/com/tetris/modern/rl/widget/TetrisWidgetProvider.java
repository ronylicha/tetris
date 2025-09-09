package com.tetris.modern.rl.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.tetris.modern.rl.MainActivity;
import com.tetris.modern.rl.R;
import kotlinx.coroutines.Dispatchers;
import timber.log.Timber;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016\u00a8\u0006\u0011"}, d2 = {"Lcom/tetris/modern/rl/widget/TetrisWidgetProvider;", "Landroid/appwidget/AppWidgetProvider;", "()V", "onDisabled", "", "context", "Landroid/content/Context;", "onEnabled", "onReceive", "intent", "Landroid/content/Intent;", "onUpdate", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetIds", "", "Companion", "app_release"})
public final class TetrisWidgetProvider extends android.appwidget.AppWidgetProvider {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_PLAY_CLICKED = "com.tetris.modern.PLAY_CLICKED";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_REFRESH = "com.tetris.modern.REFRESH_WIDGET";
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.widget.TetrisWidgetProvider.Companion Companion = null;
    
    public TetrisWidgetProvider() {
        super();
    }
    
    @java.lang.Override
    public void onUpdate(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.appwidget.AppWidgetManager appWidgetManager, @org.jetbrains.annotations.NotNull
    int[] appWidgetIds) {
    }
    
    @java.lang.Override
    public void onEnabled(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
    
    @java.lang.Override
    public void onDisabled(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
    
    @java.lang.Override
    public void onReceive(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.content.Intent intent) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\nJ%\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007H\u0000\u00a2\u0006\u0002\b\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/tetris/modern/rl/widget/TetrisWidgetProvider$Companion;", "", "()V", "ACTION_PLAY_CLICKED", "", "ACTION_REFRESH", "getGamesPlayed", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHighScore", "getTotalLines", "updateAppWidget", "", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetId", "updateAppWidget$app_release", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void updateAppWidget$app_release(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        android.appwidget.AppWidgetManager appWidgetManager, int appWidgetId) {
        }
        
        private final java.lang.Object getHighScore(android.content.Context context, kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
            return null;
        }
        
        private final java.lang.Object getTotalLines(android.content.Context context, kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
            return null;
        }
        
        private final java.lang.Object getGamesPlayed(android.content.Context context, kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
            return null;
        }
    }
}