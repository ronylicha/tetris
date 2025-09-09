package com.tetris.modern.rl.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.tetris.modern.rl.MainActivity
import com.tetris.modern.rl.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class TetrisWidgetProvider : AppWidgetProvider() {
    
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
    
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Timber.d("Tetris widget enabled")
    }
    
    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Timber.d("Tetris widget disabled")
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        
        when (intent.action) {
            ACTION_PLAY_CLICKED -> {
                val launchIntent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("start_game", true)
                }
                context.startActivity(launchIntent)
            }
            ACTION_REFRESH -> {
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val thisWidget = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID
                )
                if (thisWidget != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    updateAppWidget(context, appWidgetManager, thisWidget)
                }
            }
        }
    }
    
    companion object {
        const val ACTION_PLAY_CLICKED = "com.tetris.modern.PLAY_CLICKED"
        const val ACTION_REFRESH = "com.tetris.modern.REFRESH_WIDGET"
        
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            // Create RemoteViews
            val views = RemoteViews(context.packageName, R.layout.widget_tetris)
            
            // Load stats asynchronously
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // For now, use placeholder data
                    val highScore = getHighScore(context)
                    val totalLines = getTotalLines(context)
                    val gamesPlayed = getGamesPlayed(context)
                    
                    CoroutineScope(Dispatchers.Main).launch {
                        // Update text views
                        views.setTextViewText(R.id.widget_high_score, "High Score: $highScore")
                        views.setTextViewText(R.id.widget_total_lines, "Lines: $totalLines")
                        views.setTextViewText(R.id.widget_games_played, "Games: $gamesPlayed")
                        
                        // Set up play button
                        val playIntent = Intent(context, TetrisWidgetProvider::class.java).apply {
                            action = ACTION_PLAY_CLICKED
                        }
                        val playPendingIntent = PendingIntent.getBroadcast(
                            context,
                            0,
                            playIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                        views.setOnClickPendingIntent(R.id.widget_play_button, playPendingIntent)
                        
                        // Set up refresh button
                        val refreshIntent = Intent(context, TetrisWidgetProvider::class.java).apply {
                            action = ACTION_REFRESH
                            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                        }
                        val refreshPendingIntent = PendingIntent.getBroadcast(
                            context,
                            appWidgetId,
                            refreshIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                        views.setOnClickPendingIntent(R.id.widget_refresh_button, refreshPendingIntent)
                        
                        // Set up widget click to open app
                        val appIntent = Intent(context, MainActivity::class.java)
                        val appPendingIntent = PendingIntent.getActivity(
                            context,
                            0,
                            appIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                        views.setOnClickPendingIntent(R.id.widget_container, appPendingIntent)
                        
                        // Update the widget
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    }
                } catch (e: Exception) {
                    Timber.e(e, "Error updating widget")
                }
            }
        }
        
        private suspend fun getHighScore(context: Context): Int {
            // TODO: Load from database
            return context.getSharedPreferences("tetris_prefs", Context.MODE_PRIVATE)
                .getInt("high_score", 0)
        }
        
        private suspend fun getTotalLines(context: Context): Int {
            // TODO: Load from database
            return context.getSharedPreferences("tetris_prefs", Context.MODE_PRIVATE)
                .getInt("total_lines", 0)
        }
        
        private suspend fun getGamesPlayed(context: Context): Int {
            // TODO: Load from database
            return context.getSharedPreferences("tetris_prefs", Context.MODE_PRIVATE)
                .getInt("games_played", 0)
        }
    }
}