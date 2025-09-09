package com.tetris.modern.rl.audio

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.tetris.modern.rl.MainActivity
import com.tetris.modern.rl.R
import timber.log.Timber

class MusicService : Service() {
    
    private var mediaPlayer: MediaPlayer? = null
    private val binder = MusicBinder()
    private var currentMusic: String = "theme"
    private var isPlaying = false
    
    companion object {
        const val CHANNEL_ID = "TetrisMusicChannel"
        const val NOTIFICATION_ID = 1001
    }
    
    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        Timber.d("MusicService created")
    }
    
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "PLAY" -> startMusic()
            "PAUSE" -> pauseMusic()
            "STOP" -> stopMusic()
            "CHANGE_TRACK" -> {
                val track = intent.getStringExtra("track") ?: "theme"
                changeMusic(track)
            }
        }
        return START_NOT_STICKY
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Tetris Music",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Background music for Tetris"
                setSound(null, null)
            }
            
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Modern Tetris")
            .setContentText("Playing: $currentMusic")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }
    
    fun startMusic() {
        if (mediaPlayer == null) {
            val resourceId = getMusicResource(currentMusic)
            if (resourceId != 0) {
                mediaPlayer = MediaPlayer.create(this, resourceId)?.apply {
                    isLooping = true
                    setVolume(0.7f, 0.7f)
                }
            }
        }
        
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
                isPlaying = true
                startForeground(NOTIFICATION_ID, createNotification())
                Timber.d("Music started: $currentMusic")
            }
        }
    }
    
    fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isPlaying = false
                stopForeground(STOP_FOREGROUND_REMOVE)
                Timber.d("Music paused")
            }
        }
    }
    
    fun stopMusic() {
        mediaPlayer?.let {
            it.stop()
            it.release()
            mediaPlayer = null
            isPlaying = false
            stopForeground(STOP_FOREGROUND_REMOVE)
            Timber.d("Music stopped")
        }
    }
    
    fun changeMusic(track: String) {
        if (currentMusic != track) {
            stopMusic()
            currentMusic = track
            startMusic()
        }
    }
    
    fun setVolume(volume: Float) {
        mediaPlayer?.setVolume(volume, volume)
    }
    
    private fun getMusicResource(track: String): Int {
        return when (track) {
            "theme" -> R.raw.music_theme
            "chiptune" -> R.raw.music_chiptune
            "synthwave" -> R.raw.music_synthwave
            "orchestral" -> R.raw.music_orchestral
            "jazz" -> R.raw.music_jazz
            "metal" -> R.raw.music_metal
            else -> R.raw.music_theme
        }
    }
    
    override fun onDestroy() {
        stopMusic()
        super.onDestroy()
        Timber.d("MusicService destroyed")
    }
}