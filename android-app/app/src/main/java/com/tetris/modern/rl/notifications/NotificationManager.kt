package com.tetris.modern.rl.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tetris.modern.rl.MainActivity
import com.tetris.modern.rl.R
import com.tetris.modern.rl.data.repository.ScoreSubmitResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TetrisNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    companion object {
        const val CHANNEL_ID_RECORDS = "tetris_records"
        const val CHANNEL_ID_SYNC = "tetris_sync"
        const val CHANNEL_ID_DAILY = "tetris_daily"
        const val CHANNEL_ID_ACHIEVEMENTS = "tetris_achievements"
        
        const val NOTIFICATION_ID_PERSONAL_BEST = 1001
        const val NOTIFICATION_ID_GLOBAL_RECORD = 1002
        const val NOTIFICATION_ID_TOP_10 = 1003
        const val NOTIFICATION_ID_SYNC = 2001
        const val NOTIFICATION_ID_DAILY_CHALLENGE = 3001
        const val NOTIFICATION_ID_ACHIEVEMENT = 4001
    }
    
    init {
        createNotificationChannels()
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            
            // Records channel
            val recordsChannel = NotificationChannel(
                CHANNEL_ID_RECORDS,
                "New Records",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for new personal and global records"
                enableLights(true)
                lightColor = Color.CYAN
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 250, 250, 250)
            }
            
            // Sync channel
            val syncChannel = NotificationChannel(
                CHANNEL_ID_SYNC,
                "Sync Status",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notifications for data synchronization"
                enableLights(false)
                enableVibration(false)
            }
            
            // Daily challenge channel
            val dailyChannel = NotificationChannel(
                CHANNEL_ID_DAILY,
                "Daily Challenges",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Daily challenge reminders and results"
                enableLights(true)
                lightColor = Color.MAGENTA
            }
            
            // Achievements channel
            val achievementsChannel = NotificationChannel(
                CHANNEL_ID_ACHIEVEMENTS,
                "Achievements",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Achievement unlocks and progress"
                enableLights(true)
                lightColor = Color.YELLOW
                enableVibration(true)
            }
            
            notificationManager?.let {
                it.createNotificationChannel(recordsChannel)
                it.createNotificationChannel(syncChannel)
                it.createNotificationChannel(dailyChannel)
                it.createNotificationChannel(achievementsChannel)
            }
        }
    }
    
    fun showNewPersonalBestNotification(
        gameMode: String,
        score: Int,
        previousBest: Int
    ) {
        val improvement = score - previousBest
        val percentImprovement = ((score - previousBest) * 100.0 / previousBest).toInt()
        
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("open_leaderboard", true)
            putExtra("game_mode", gameMode)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_RECORDS)
            .setSmallIcon(R.drawable.ic_trophy)
            .setContentTitle("🎉 New Personal Best!")
            .setContentText("You scored ${formatScore(score)} in ${gameMode.capitalize()} mode!")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Amazing! You beat your previous record by ${formatScore(improvement)} (+$percentImprovement%)! Your new personal best in ${gameMode.capitalize()} mode is ${formatScore(score)}.")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setColor(Color.CYAN)
            .addAction(
                R.drawable.ic_share,
                "Share",
                createShareIntent(score, gameMode)
            )
            .addAction(
                R.drawable.ic_leaderboard,
                "View Leaderboard",
                pendingIntent
            )
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID_PERSONAL_BEST, notification)
        }
    }
    
    fun showGlobalRecordNotification(
        gameMode: String,
        score: Int,
        rank: Int
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("open_leaderboard", true)
            putExtra("game_mode", gameMode)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val title = when (rank) {
            1 -> "🏆 NEW WORLD RECORD!"
            in 2..3 -> "🥈 Top 3 Global Score!"
            in 4..10 -> "🌟 Top 10 Global Score!"
            else -> "⭐ High Score Achievement!"
        }
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_RECORDS)
            .setSmallIcon(R.drawable.ic_trophy)
            .setContentTitle(title)
            .setContentText("Rank #$rank worldwide with ${formatScore(score)} points!")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Incredible! You're now ranked #$rank globally in ${gameMode.capitalize()} mode with ${formatScore(score)} points! You're among the best Tetris players in the world!")
            )
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setColor(Color.YELLOW)
            .addAction(
                R.drawable.ic_share,
                "Share Achievement",
                createShareIntent(score, gameMode, rank)
            )
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID_GLOBAL_RECORD, notification)
        }
    }
    
    fun showTop10EntryNotification(
        gameMode: String,
        score: Int,
        rank: Int
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("open_leaderboard", true)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_RECORDS)
            .setSmallIcon(R.drawable.ic_star)
            .setContentTitle("🌟 You're in the Top 10!")
            .setContentText("Rank #$rank in ${gameMode.capitalize()} mode!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setColor(Color.MAGENTA)
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID_TOP_10, notification)
        }
    }
    
    fun showSyncCompleteNotification(
        syncedCount: Int,
        success: Boolean
    ) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_SYNC)
            .setSmallIcon(if (success) R.drawable.ic_sync_complete else R.drawable.ic_sync_error)
            .setContentTitle(if (success) "Sync Complete" else "Sync Failed")
            .setContentText(
                if (success) "$syncedCount scores synchronized"
                else "Failed to sync scores. Will retry later."
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true)
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID_SYNC, notification)
        }
    }
    
    fun showDailyChallengeNotification() {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("open_daily_challenge", true)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_DAILY)
            .setSmallIcon(R.drawable.ic_calendar)
            .setContentTitle("🎮 Daily Challenge Available!")
            .setContentText("A new challenge awaits! Keep your streak alive!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setColor(Color.MAGENTA)
            .addAction(
                R.drawable.ic_play,
                "Play Now",
                pendingIntent
            )
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID_DAILY_CHALLENGE, notification)
        }
    }
    
    fun showAchievementUnlockedNotification(
        achievementName: String,
        achievementDescription: String,
        xpReward: Int
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("open_achievements", true)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_ACHIEVEMENTS)
            .setSmallIcon(R.drawable.ic_achievement)
            .setContentTitle("🏅 Achievement Unlocked!")
            .setContentText(achievementName)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("$achievementDescription\n+$xpReward XP earned!")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setColor(Color.YELLOW)
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID_ACHIEVEMENT + System.currentTimeMillis().toInt(), notification)
        }
    }
    
    fun handleScoreSubmitResult(result: ScoreSubmitResult, gameMode: String, previousBest: Int?) {
        // Show appropriate notifications based on the result
        if (result.isPersonalBest && previousBest != null) {
            showNewPersonalBestNotification(gameMode, result.localRank, previousBest)
        }
        
        if (result.isGlobalRecord && result.serverRank != null) {
            when (result.serverRank) {
                1 -> showGlobalRecordNotification(gameMode, result.localRank, result.serverRank)
                in 2..10 -> showTop10EntryNotification(gameMode, result.localRank, result.serverRank)
            }
        }
    }
    
    private fun createShareIntent(score: Int, gameMode: String, rank: Int? = null): PendingIntent {
        val shareText = if (rank != null) {
            "🎮 I just ranked #$rank globally in Tetris ${gameMode.capitalize()} mode with ${formatScore(score)} points! Can you beat my score? 🏆"
        } else {
            "🎮 I just scored ${formatScore(score)} points in Tetris ${gameMode.capitalize()} mode! Can you beat my score? 🎯"
        }
        
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        
        val chooserIntent = Intent.createChooser(shareIntent, "Share your achievement")
        
        return PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            chooserIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
    
    private fun formatScore(score: Int): String {
        return "%,d".format(score)
    }
    
    fun cancelNotification(notificationId: Int) {
        with(NotificationManagerCompat.from(context)) {
            cancel(notificationId)
        }
    }
    
    fun cancelAllNotifications() {
        with(NotificationManagerCompat.from(context)) {
            cancelAll()
        }
    }
}