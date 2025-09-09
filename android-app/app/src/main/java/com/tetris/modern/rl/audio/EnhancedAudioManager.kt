package com.tetris.modern.rl.audio

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.media.SoundPool
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.tetris.modern.rl.R
import com.tetris.modern.rl.game.models.GameState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class EnhancedAudioManager @Inject constructor(
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
        
        // Dynamic layers
        const val LAYER_TENSION = "tension"
        const val LAYER_CELEBRATION = "celebration"
        const val LAYER_DANGER = "danger"
        
        // Effects
        const val EFFECT_FILTER_SWEEP = "filter_sweep"
        const val EFFECT_ECHO = "echo"
        const val EFFECT_DISTORTION = "distortion"
    }
    
    private var soundPool: SoundPool? = null
    private val soundMap = mutableMapOf<Int, Int>()
    private var currentPlayer: MediaPlayer? = null
    private var nextPlayer: MediaPlayer? = null
    private var currentMusic: String? = null
    
    private var isMuted = false
    private var musicVolume = 0.7f
    private var sfxVolume = 0.8f
    private var isVibrationEnabled = true
    
    // Dynamic tempo system
    private var baseTempo = 120f
    private var currentTempo = baseTempo
    private val handler = Handler(Looper.getMainLooper())
    
    // Adaptive music layers
    private val activeLayers = mutableSetOf<String>()
    private val layerPlayers = mutableMapOf<String, MediaPlayer>()
    
    // Combo pitch scaling
    private var comboCount = 0
    private val semitoneRatio = 1.05946f // 12th root of 2
    
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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Load all professional sound effects
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
                
                Timber.d("Enhanced sound effects loaded successfully")
            } catch (e: Exception) {
                Timber.e(e, "Error loading sound effects")
            }
        }
    }
    
    fun playSound(soundId: Int, priority: Int = 1) {
        if (isMuted) return
        
        soundMap[soundId]?.let { id ->
            soundPool?.play(
                id,
                sfxVolume,
                sfxVolume,
                priority,
                0, // no loop
                1.0f // normal playback rate
            )
        }
    }
    
    fun playComboSound(combo: Int) {
        if (isMuted) return
        
        comboCount = combo
        val pitchMultiplier = semitoneRatio.pow(combo.coerceAtMost(12))
        
        soundMap[SOUND_COMBO]?.let { id ->
            soundPool?.play(
                id,
                sfxVolume,
                sfxVolume,
                1,
                0,
                pitchMultiplier
            )
        }
        
        // Escalating vibration
        val vibrationIntensity = (50 + combo * 15).coerceAtMost(200).toLong()
        vibrate(vibrationIntensity)
    }
    
    fun playMusic(musicTrack: String, loop: Boolean = true) {
        if (isMuted || currentMusic == musicTrack) return
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Prepare next player for crossfade
                val resourceId = getMusicResourceId(musicTrack)
                if (resourceId != 0) {
                    nextPlayer = MediaPlayer.create(context, resourceId)?.apply {
                        isLooping = loop
                        setVolume(0f, 0f) // Start silent for fade-in
                        start()
                    }
                    
                    // Crossfade between tracks
                    if (currentPlayer != null && nextPlayer != null) {
                        crossfadeTo(musicTrack, 1000)
                    } else {
                        // No current track, just fade in
                        nextPlayer?.let {
                            fadeIn(it, 500)
                            currentPlayer = it
                            nextPlayer = null
                        }
                    }
                    
                    currentMusic = musicTrack
                    Timber.d("Playing enhanced music: $musicTrack")
                }
            } catch (e: Exception) {
                Timber.e(e, "Error playing music: $musicTrack")
            }
        }
    }
    
    private fun crossfadeTo(newTrack: String, duration: Long = 1000) {
        val fadeOutAnimator = ObjectAnimator.ofFloat(0f, 1f).apply {
            this.duration = duration
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                currentPlayer?.setVolume(
                    musicVolume * (1f - value),
                    musicVolume * (1f - value)
                )
            }
        }
        
        val fadeInAnimator = ObjectAnimator.ofFloat(0f, 1f).apply {
            this.duration = duration
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                nextPlayer?.setVolume(
                    musicVolume * value,
                    musicVolume * value
                )
            }
        }
        
        AnimatorSet().apply {
            playTogether(fadeOutAnimator, fadeInAnimator)
            start()
        }
        
        // Swap players after crossfade
        handler.postDelayed({
            currentPlayer?.release()
            currentPlayer = nextPlayer
            nextPlayer = null
        }, duration)
    }
    
    private fun fadeIn(player: MediaPlayer, duration: Long) {
        ObjectAnimator.ofFloat(0f, 1f).apply {
            this.duration = duration
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                player.setVolume(musicVolume * value, musicVolume * value)
            }
            start()
        }
    }
    
    fun adjustTempoForLevel(level: Int) {
        currentTempo = baseTempo * (1 + (level * 0.02f))
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            currentPlayer?.apply {
                if (isPlaying) {
                    playbackParams = playbackParams.setSpeed(currentTempo / baseTempo)
                    Timber.d("Tempo adjusted to: $currentTempo BPM")
                }
            }
        }
    }
    
    fun updateMusicIntensity(gameState: GameState) {
        when {
            gameState.dangerLevel > 0.8 -> {
                if (!activeLayers.contains(LAYER_DANGER)) {
                    addMusicLayer(LAYER_DANGER)
                    increaseTempo(1.2f)
                }
            }
            gameState.combo > 5 -> {
                if (!activeLayers.contains(LAYER_CELEBRATION)) {
                    addMusicLayer(LAYER_CELEBRATION)
                }
            }
            gameState.isPowerUpActive -> {
                applyEffect(EFFECT_FILTER_SWEEP)
            }
        }
        
        // Remove layers when conditions no longer met
        if (gameState.dangerLevel < 0.5 && activeLayers.contains(LAYER_DANGER)) {
            removeMusicLayer(LAYER_DANGER)
            resetTempo()
        }
        
        if (gameState.combo == 0 && activeLayers.contains(LAYER_CELEBRATION)) {
            removeMusicLayer(LAYER_CELEBRATION)
        }
    }
    
    private fun addMusicLayer(layer: String) {
        if (activeLayers.contains(layer)) return
        
        // Load and play additional layer
        val resourceId = when (layer) {
            LAYER_DANGER -> R.raw.music_theme_c // Intense version
            LAYER_CELEBRATION -> R.raw.music_victory // Victory elements
            else -> 0
        }
        
        if (resourceId != 0) {
            MediaPlayer.create(context, resourceId)?.apply {
                isLooping = true
                setVolume(musicVolume * 0.3f, musicVolume * 0.3f)
                start()
                layerPlayers[layer] = this
            }
            activeLayers.add(layer)
            Timber.d("Added music layer: $layer")
        }
    }
    
    private fun removeMusicLayer(layer: String) {
        layerPlayers[layer]?.apply {
            if (isPlaying) stop()
            release()
        }
        layerPlayers.remove(layer)
        activeLayers.remove(layer)
        Timber.d("Removed music layer: $layer")
    }
    
    private fun applyEffect(effect: String) {
        when (effect) {
            EFFECT_FILTER_SWEEP -> {
                // Simulate filter sweep with volume automation
                handler.post {
                    ObjectAnimator.ofFloat(0f, 1f, 0f).apply {
                        duration = 2000
                        addUpdateListener { animation ->
                            val value = animation.animatedValue as Float
                            val volume = musicVolume * (0.5f + value * 0.5f)
                            currentPlayer?.setVolume(volume, volume)
                        }
                        start()
                    }
                }
            }
        }
    }
    
    private fun increaseTempo(multiplier: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            currentPlayer?.apply {
                playbackParams = playbackParams.setSpeed(multiplier)
            }
        }
    }
    
    private fun resetTempo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            currentPlayer?.apply {
                playbackParams = playbackParams.setSpeed(currentTempo / baseTempo)
            }
        }
    }
    
    fun duckMusic(amount: Float = 0.3f, duration: Long = 200) {
        val originalVolume = musicVolume
        currentPlayer?.setVolume(musicVolume * amount, musicVolume * amount)
        
        handler.postDelayed({
            currentPlayer?.setVolume(originalVolume, originalVolume)
        }, duration)
    }
    
    fun stopMusic() {
        // Clean up all layers
        activeLayers.forEach { removeMusicLayer(it) }
        
        currentPlayer?.apply {
            if (isPlaying) stop()
            release()
        }
        currentPlayer = null
        currentMusic = null
    }
    
    fun pauseMusic() {
        currentPlayer?.apply {
            if (isPlaying) pause()
        }
        layerPlayers.values.forEach { it.pause() }
    }
    
    fun resumeMusic() {
        if (isMuted) return
        
        currentPlayer?.apply {
            if (!isPlaying) start()
        }
        layerPlayers.values.forEach { it.start() }
    }
    
    fun setMusicVolume(volume: Float) {
        musicVolume = volume.coerceIn(0f, 1f)
        currentPlayer?.setVolume(musicVolume, musicVolume)
        layerPlayers.values.forEach {
            it.setVolume(musicVolume * 0.3f, musicVolume * 0.3f)
        }
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
                duckMusic(0.5f, 800) // Duck music during epic Tetris sound
                vibratePattern(longArrayOf(0, 50, 50, 100))
            }
        }
    }
    
    fun playTSpinFeedback() {
        playSound(SOUND_T_SPIN)
        vibratePattern(longArrayOf(0, 30, 30, 30, 30, 50))
    }
    
    fun playGameOverFeedback() {
        stopMusic() // Stop all music
        playSound(SOUND_GAME_OVER)
        playMusic(MUSIC_GAME_OVER, loop = false)
        vibratePattern(longArrayOf(0, 200, 100, 200, 100, 500))
    }
    
    private fun getMusicResourceId(musicTrack: String): Int {
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
    
    // Extension for Float power
    private fun Float.pow(exponent: Int): Float {
        return Math.pow(this.toDouble(), exponent.toDouble()).toFloat()
    }
}

// Extension properties for GameState
val GameState.dangerLevel: Float
    get() {
        // Calculate danger based on how high pieces are stacked
        // Use the lines cleared as a proxy for grid fullness
        val gridFullness = (20 - (20 - lines.coerceAtMost(20))) / 20f
        return gridFullness.coerceIn(0f, 1f)
    }

val GameState.isPowerUpActive: Boolean
    get() = false // Placeholder, implement based on actual power-up system