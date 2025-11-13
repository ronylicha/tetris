package com.tetris.modern.rl.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Context

@AndroidEntryPoint
class TetrisFirebaseMessagingService : FirebaseMessagingService() {
    
    @Inject
    lateinit var notificationManager: TetrisNotificationManager
    
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("FCM Token: $token")
        
        // Send token to server for push notifications
        sendTokenToServer(token)
    }
    
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        
        Timber.d("FCM Message received: ${message.data}")
        
        // Handle different notification types
        when (message.data["type"]) {
            "new_record" -> handleNewRecordNotification(message.data)
            "beaten_by_friend" -> handleBeatenByFriendNotification(message.data)
            "daily_challenge" -> handleDailyChallengeNotification(message.data)
            "achievement" -> handleAchievementNotification(message.data)
            "tournament" -> handleTournamentNotification(message.data)
            else -> handleGenericNotification(message)
        }
    }
    
    private fun handleNewRecordNotification(data: Map<String, String>) {
        val playerName = data["player_name"] ?: return
        val gameMode = data["game_mode"] ?: return
        val score = data["score"]?.toIntOrNull() ?: return
        val rank = data["rank"]?.toIntOrNull() ?: return
        
        if (rank <= 10) {
            notificationManager.showGlobalRecordNotification(gameMode, score, rank)
        }
    }
    
    private fun handleBeatenByFriendNotification(data: Map<String, String>) {
        val friendName = data["friend_name"] ?: return
        val gameMode = data["game_mode"] ?: return
        val theirScore = data["their_score"]?.toIntOrNull() ?: return
        val yourScore = data["your_score"]?.toIntOrNull() ?: return
        
        // Custom notification for when a friend beats your score
        // This would need a new notification type in TetrisNotificationManager
    }
    
    private fun handleDailyChallengeNotification(data: Map<String, String>) {
        notificationManager.showDailyChallengeNotification()
    }
    
    private fun handleAchievementNotification(data: Map<String, String>) {
        val achievementName = data["achievement_name"] ?: return
        val achievementDescription = data["achievement_description"] ?: ""
        val xpReward = data["xp_reward"]?.toIntOrNull() ?: 100
        
        notificationManager.showAchievementUnlockedNotification(
            achievementName,
            achievementDescription,
            xpReward
        )
    }
    
    private fun handleTournamentNotification(data: Map<String, String>) {
        // Handle tournament-related notifications
        val tournamentName = data["tournament_name"] ?: return
        val startTime = data["start_time"] ?: return
        
        // Show tournament notification
    }
    
    private fun handleGenericNotification(message: RemoteMessage) {
        // Handle generic notification with title and body
        message.notification?.let { notification ->
            // Use Android's default notification handling
        }
    }
    
    private fun sendTokenToServer(token: String) {
        // Send token to backend server
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // For now, just store locally
                val playerId = "anonymous"
                
                // Store token locally for now
                val prefs = getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
                prefs.edit().putString("fcm_token", token).apply()
                
                // Send to server when API endpoint is available
                // apiService.updateFcmToken(playerId, token)
                
                Timber.d("FCM token saved: $token for player: $playerId")
            } catch (e: Exception) {
                Timber.e(e, "Failed to send FCM token to server")
            }
        }
    }
}