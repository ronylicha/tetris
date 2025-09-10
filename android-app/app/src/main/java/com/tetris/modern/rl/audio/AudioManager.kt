package com.tetris.modern.rl.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.tetris.modern.rl.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    companion object {
        // Sound effect IDs
        const val SOUND_MOVE = 1
        const val SOUND_ROTATE = 2
        const val SOUND_DROP = 3
        const val SOUND_LINE_CLEAR = 4
        const val SOUND_TETRIS = 5
        const val SOUND_LEVEL_UP = 6
        const val SOUND_GAME_OVER = 7
        const val SOUND_HOLD = 8
        const val SOUND_T_SPIN = 9
        const val SOUND_COMBO = 10
        const val SOUND_POWER_UP = 11
        const val SOUND_MENU_SELECT = 12
        const val SOUND_MENU_BACK = 13
        const val SOUND_PAUSE = 14
        const val SOUND_COUNTDOWN = 15
        
        // Music tracks
        const val MUSIC_THEME_A = "theme_a"
        const val MUSIC_THEME_B = "theme_b"
        const val MUSIC_THEME_C = "theme_c"
        const val MUSIC_MENU = "menu"
        const val MUSIC_VICTORY = "victory"
        const val MUSIC_GAME_OVER = "game_over"
        const val MUSIC_CHIPTUNE = "chiptune"
        const val MUSIC_SYNTHWAVE = "synthwave"
        const val MUSIC_ORCHESTRAL = "orchestral"
        const val MUSIC_JAZZ = "jazz"
        const val MUSIC_METAL = "metal"
    }
    
    private var soundPool: SoundPool? = null
    private val soundMap = mutableMapOf<Int, Int>()
    private var mediaPlayer: MediaPlayer? = null
    private var currentMusic: String? = null
    
    private var isMuted = false
    private var musicVolume = 0.7f
    private var sfxVolume = 0.8f
    private var isVibrationEnabled = true
    
    private val vibrator: Vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }
    
    init {
        initializeSoundPool()
        // Delay sound loading to ensure sound pool is ready
        loadSoundEffects()
    }
    
    private fun initializeSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        
        soundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(audioAttributes)
            .build()
    }
    
    private fun loadSoundEffects() {
        try {
            // Load all sound effects synchronously to ensure they're ready
            soundPool?.setOnLoadCompleteListener { soundPool, sampleId, status ->
                if (status == 0) {
                    Timber.d("Sound loaded successfully: $sampleId")
                } else {
                    Timber.e("Failed to load sound: $sampleId")
                }
            }
            
            // Load all sound effects
            soundMap[SOUND_MOVE] = soundPool?.load(context, R.raw.sfx_move, 1) ?: 0
            soundMap[SOUND_ROTATE] = soundPool?.load(context, R.raw.sfx_rotate, 1) ?: 0
            soundMap[SOUND_DROP] = soundPool?.load(context, R.raw.sfx_drop, 1) ?: 0
            soundMap[SOUND_LINE_CLEAR] = soundPool?.load(context, R.raw.sfx_line_clear, 1) ?: 0
            soundMap[SOUND_TETRIS] = soundPool?.load(context, R.raw.sfx_tetris, 1) ?: 0
            soundMap[SOUND_LEVEL_UP] = soundPool?.load(context, R.raw.sfx_level_up, 1) ?: 0
            soundMap[SOUND_GAME_OVER] = soundPool?.load(context, R.raw.sfx_game_over, 1) ?: 0
            soundMap[SOUND_HOLD] = soundPool?.load(context, R.raw.sfx_hold, 1) ?: 0
            soundMap[SOUND_T_SPIN] = soundPool?.load(context, R.raw.sfx_t_spin, 1) ?: 0
            soundMap[SOUND_COMBO] = soundPool?.load(context, R.raw.sfx_combo, 1) ?: 0
            soundMap[SOUND_POWER_UP] = soundPool?.load(context, R.raw.sfx_power_up, 1) ?: 0
            soundMap[SOUND_MENU_SELECT] = soundPool?.load(context, R.raw.sfx_menu_select, 1) ?: 0
            soundMap[SOUND_MENU_BACK] = soundPool?.load(context, R.raw.sfx_menu_back, 1) ?: 0
            soundMap[SOUND_PAUSE] = soundPool?.load(context, R.raw.sfx_pause, 1) ?: 0
            soundMap[SOUND_COUNTDOWN] = soundPool?.load(context, R.raw.sfx_countdown, 1) ?: 0
            
            Timber.d("Sound effects loading initiated with ${soundMap.size} sounds")
        } catch (e: Exception) {
            Timber.e(e, "Error loading sound effects")
        }
    }
    
    fun playSound(soundId: Int, priority: Int = 1) {
        if (isMuted) return
        
        soundMap[soundId]?.let { id ->
            if (id != 0) {
                val streamId = soundPool?.play(
                    id,
                    sfxVolume,
                    sfxVolume,
                    priority,
                    0, // no loop
                    1.0f // normal playback rate
                )
                Timber.d("Playing sound $soundId with stream ID: $streamId")
            } else {
                Timber.w("Sound $soundId not loaded")
            }
        } ?: Timber.w("Sound $soundId not found in map")
    }
    
    fun playMusic(musicTrack: String, loop: Boolean = true) {
        if (isMuted || currentMusic == musicTrack) return
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Stop current music
                stopMusic()
                
                // Load and play new music
                val resourceId = getMusicResourceId(musicTrack)
                if (resourceId != 0) {
                    mediaPlayer = MediaPlayer.create(context, resourceId)?.apply {
                        isLooping = loop
                        setVolume(musicVolume, musicVolume)
                        start()
                    }
                    currentMusic = musicTrack
                    Timber.d("Playing music: $musicTrack")
                }
            } catch (e: Exception) {
                Timber.e(e, "Error playing music: $musicTrack")
            }
        }
    }
    
    fun stopMusic() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
        currentMusic = null
    }
    
    fun pauseMusic() {
        mediaPlayer?.apply {
            if (isPlaying) {
                pause()
            }
        }
    }
    
    fun resumeMusic() {
        if (isMuted) return
        
        mediaPlayer?.apply {
            if (!isPlaying) {
                start()
            }
        }
    }
    
    fun setMusicVolume(volume: Float) {
        musicVolume = volume.coerceIn(0f, 1f)
        mediaPlayer?.setVolume(musicVolume, musicVolume)
    }
    
    fun setSfxVolume(volume: Float) {
        sfxVolume = volume.coerceIn(0f, 1f)
    }
    
    fun setMuted(muted: Boolean) {
        isMuted = muted
        if (muted) {
            pauseMusic()
        } else {
            resumeMusic()
        }
    }
    
    fun setVibrationEnabled(enabled: Boolean) {
        isVibrationEnabled = enabled
    }
    
    fun vibrate(duration: Long = 50) {
        if (!isVibrationEnabled || isMuted) return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    duration,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }
    
    fun vibratePattern(pattern: LongArray) {
        if (!isVibrationEnabled || isMuted) return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createWaveform(pattern, -1)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, -1)
        }
    }
    
    fun playLineClearFeedback(lines: Int) {
        when (lines) {
            1 -> {
                playSound(SOUND_LINE_CLEAR)
                vibrate(50)
            }
            2 -> {
                playSound(SOUND_LINE_CLEAR)
                vibrate(75)
            }
            3 -> {
                playSound(SOUND_LINE_CLEAR)
                vibrate(100)
            }
            4 -> {
                playSound(SOUND_TETRIS)
                vibratePattern(longArrayOf(0, 50, 50, 100))
            }
        }
    }
    
    fun playTSpinFeedback() {
        playSound(SOUND_T_SPIN)
        vibratePattern(longArrayOf(0, 30, 30, 30, 30, 50))
    }
    
    fun playComboFeedback(combo: Int) {
        playSound(SOUND_COMBO)
        val vibrationIntensity = (50 + combo * 10).coerceAtMost(200).toLong()
        vibrate(vibrationIntensity)
    }
    
    fun playGameOverFeedback() {
        playSound(SOUND_GAME_OVER)
        playMusic(MUSIC_GAME_OVER, loop = false)
        vibratePattern(longArrayOf(0, 200, 100, 200, 100, 500))
    }
    
    fun adjustMusicTempo(level: Int) {
        // Adjust playback speed based on level
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mediaPlayer?.apply {
                val speed = 1.0f + (level - 1) * 0.02f // 2% faster per level
                playbackParams = playbackParams.setSpeed(speed.coerceIn(1.0f, 2.0f))
            }
        }
    }
    
    private fun getMusicResourceId(musicTrack: String): Int {
        // Map music track names to resource IDs
        return when (musicTrack) {
            MUSIC_THEME_A -> R.raw.music_theme_a
            MUSIC_THEME_B -> R.raw.music_theme_b
            MUSIC_THEME_C -> R.raw.music_theme_c
            MUSIC_MENU -> R.raw.music_menu_music
            MUSIC_VICTORY -> R.raw.music_victory
            MUSIC_GAME_OVER -> R.raw.music_game_over_music
            MUSIC_CHIPTUNE -> R.raw.music_chiptune
            MUSIC_SYNTHWAVE -> R.raw.music_synthwave
            MUSIC_ORCHESTRAL -> R.raw.music_orchestral
            MUSIC_JAZZ -> R.raw.music_jazz
            MUSIC_METAL -> R.raw.music_metal
            else -> 0
        }
    }
    
    fun release() {
        stopMusic()
        soundPool?.release()
        soundPool = null
        soundMap.clear()
    }
}