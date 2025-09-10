package com.tetris.modern.rl.audio;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import com.tetris.modern.rl.R;
import com.tetris.modern.rl.game.models.GameState;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0016\n\u0002\b\u0004\b\u0007\u0018\u0000 Z2\u00020\u0001:\u0001ZB\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0007H\u0002J\u000e\u0010\'\u001a\u00020%2\u0006\u0010(\u001a\u00020\u000bJ\u0010\u0010)\u001a\u00020%2\u0006\u0010*\u001a\u00020\u0007H\u0002J\u001a\u0010+\u001a\u00020%2\u0006\u0010,\u001a\u00020\u00072\b\b\u0002\u0010-\u001a\u00020.H\u0002J\u001a\u0010/\u001a\u00020%2\b\b\u0002\u00100\u001a\u00020\t2\b\b\u0002\u0010-\u001a\u00020.J\u0018\u00101\u001a\u00020%2\u0006\u00102\u001a\u00020\u000e2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u00103\u001a\u00020\u000b2\u0006\u00104\u001a\u00020\u0007H\u0002J\u0010\u00105\u001a\u00020%2\u0006\u00106\u001a\u00020\tH\u0002J\b\u00107\u001a\u00020%H\u0002J\b\u00108\u001a\u00020%H\u0002J\u0006\u00109\u001a\u00020%J\u000e\u0010:\u001a\u00020%2\u0006\u0010;\u001a\u00020\u000bJ\u0006\u0010<\u001a\u00020%J\u000e\u0010=\u001a\u00020%2\u0006\u0010>\u001a\u00020\u000bJ\u0018\u0010?\u001a\u00020%2\u0006\u00104\u001a\u00020\u00072\b\b\u0002\u0010@\u001a\u00020\u0013J\u0018\u0010A\u001a\u00020%2\u0006\u0010B\u001a\u00020\u000b2\b\b\u0002\u0010C\u001a\u00020\u000bJ\u0006\u0010D\u001a\u00020%J\u0006\u0010E\u001a\u00020%J\u0010\u0010F\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0007H\u0002J\b\u0010G\u001a\u00020%H\u0002J\u0006\u0010H\u001a\u00020%J\u000e\u0010I\u001a\u00020%2\u0006\u0010J\u001a\u00020\tJ\u000e\u0010K\u001a\u00020%2\u0006\u0010L\u001a\u00020\u0013J\u000e\u0010M\u001a\u00020%2\u0006\u0010J\u001a\u00020\tJ\u000e\u0010N\u001a\u00020%2\u0006\u0010O\u001a\u00020\u0013J\u0006\u0010P\u001a\u00020%J\u000e\u0010Q\u001a\u00020%2\u0006\u0010R\u001a\u00020SJ\u0010\u0010T\u001a\u00020%2\b\b\u0002\u0010-\u001a\u00020.J\u000e\u0010U\u001a\u00020%2\u0006\u0010V\u001a\u00020WJ\u0014\u0010X\u001a\u00020\t*\u00020\t2\u0006\u0010Y\u001a\u00020\u000bH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000e0\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u001e\u001a\u00020\u001f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b \u0010!\u00a8\u0006["}, d2 = {"Lcom/tetris/modern/rl/audio/EnhancedAudioManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "activeLayers", "", "", "baseTempo", "", "comboCount", "", "currentMusic", "currentPlayer", "Landroid/media/MediaPlayer;", "currentTempo", "handler", "Landroid/os/Handler;", "isMuted", "", "isVibrationEnabled", "layerPlayers", "", "musicVolume", "nextPlayer", "semitoneRatio", "sfxVolume", "soundMap", "soundPool", "Landroid/media/SoundPool;", "vibrator", "Landroid/os/Vibrator;", "getVibrator", "()Landroid/os/Vibrator;", "vibrator$delegate", "Lkotlin/Lazy;", "addMusicLayer", "", "layer", "adjustTempoForLevel", "level", "applyEffect", "effect", "crossfadeTo", "newTrack", "duration", "", "duckMusic", "amount", "fadeIn", "player", "getMusicResourceId", "musicTrack", "increaseTempo", "multiplier", "initializeSoundPool", "loadSoundEffects", "pauseMusic", "playComboSound", "combo", "playGameOverFeedback", "playLineClearFeedback", "lines", "playMusic", "loop", "playSound", "soundId", "priority", "playTSpinFeedback", "release", "removeMusicLayer", "resetTempo", "resumeMusic", "setMusicVolume", "volume", "setMuted", "muted", "setSfxVolume", "setVibrationEnabled", "enabled", "stopMusic", "updateMusicIntensity", "gameState", "Lcom/tetris/modern/rl/game/models/GameState;", "vibrate", "vibratePattern", "pattern", "", "pow", "exponent", "Companion", "app_release"})
public final class EnhancedAudioManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    public static final int SOUND_MOVE = 1;
    public static final int SOUND_ROTATE = 2;
    public static final int SOUND_DROP = 3;
    public static final int SOUND_LINE_CLEAR = 4;
    public static final int SOUND_TETRIS = 5;
    public static final int SOUND_LEVEL_UP = 6;
    public static final int SOUND_GAME_OVER = 7;
    public static final int SOUND_HOLD = 8;
    public static final int SOUND_T_SPIN = 9;
    public static final int SOUND_COMBO = 10;
    public static final int SOUND_POWER_UP = 11;
    public static final int SOUND_MENU_SELECT = 12;
    public static final int SOUND_MENU_BACK = 13;
    public static final int SOUND_PAUSE = 14;
    public static final int SOUND_COUNTDOWN = 15;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_THEME_A = "theme_a";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_THEME_B = "theme_b";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_THEME_C = "theme_c";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_MENU = "menu";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_VICTORY = "victory";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_GAME_OVER = "game_over";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_CHIPTUNE = "chiptune";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_SYNTHWAVE = "synthwave";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_ORCHESTRAL = "orchestral";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_JAZZ = "jazz";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_METAL = "metal";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LAYER_TENSION = "tension";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LAYER_CELEBRATION = "celebration";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LAYER_DANGER = "danger";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EFFECT_FILTER_SWEEP = "filter_sweep";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EFFECT_ECHO = "echo";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EFFECT_DISTORTION = "distortion";
    @org.jetbrains.annotations.Nullable
    private android.media.SoundPool soundPool;
    @org.jetbrains.annotations.NotNull
    private final java.util.Map<java.lang.Integer, java.lang.Integer> soundMap = null;
    @org.jetbrains.annotations.Nullable
    private android.media.MediaPlayer currentPlayer;
    @org.jetbrains.annotations.Nullable
    private android.media.MediaPlayer nextPlayer;
    @org.jetbrains.annotations.Nullable
    private java.lang.String currentMusic;
    private boolean isMuted = false;
    private float musicVolume = 0.7F;
    private float sfxVolume = 0.8F;
    private boolean isVibrationEnabled = true;
    private float baseTempo = 120.0F;
    private float currentTempo;
    @org.jetbrains.annotations.NotNull
    private final android.os.Handler handler = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> activeLayers = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Map<java.lang.String, android.media.MediaPlayer> layerPlayers = null;
    private int comboCount = 0;
    private final float semitoneRatio = 1.05946F;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy vibrator$delegate = null;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.audio.EnhancedAudioManager.Companion Companion = null;
    
    @javax.inject.Inject
    public EnhancedAudioManager(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    private final android.os.Vibrator getVibrator() {
        return null;
    }
    
    private final void initializeSoundPool() {
    }
    
    private final void loadSoundEffects() {
    }
    
    public final void playSound(int soundId, int priority) {
    }
    
    public final void playComboSound(int combo) {
    }
    
    public final void playMusic(@org.jetbrains.annotations.NotNull
    java.lang.String musicTrack, boolean loop) {
    }
    
    private final void crossfadeTo(java.lang.String newTrack, long duration) {
    }
    
    private final void fadeIn(android.media.MediaPlayer player, long duration) {
    }
    
    public final void adjustTempoForLevel(int level) {
    }
    
    public final void updateMusicIntensity(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.GameState gameState) {
    }
    
    private final void addMusicLayer(java.lang.String layer) {
    }
    
    private final void removeMusicLayer(java.lang.String layer) {
    }
    
    private final void applyEffect(java.lang.String effect) {
    }
    
    private final void increaseTempo(float multiplier) {
    }
    
    private final void resetTempo() {
    }
    
    public final void duckMusic(float amount, long duration) {
    }
    
    public final void stopMusic() {
    }
    
    public final void pauseMusic() {
    }
    
    public final void resumeMusic() {
    }
    
    public final void setMusicVolume(float volume) {
    }
    
    public final void setSfxVolume(float volume) {
    }
    
    public final void setMuted(boolean muted) {
    }
    
    public final void setVibrationEnabled(boolean enabled) {
    }
    
    public final void vibrate(long duration) {
    }
    
    public final void vibratePattern(@org.jetbrains.annotations.NotNull
    long[] pattern) {
    }
    
    public final void playLineClearFeedback(int lines) {
    }
    
    public final void playTSpinFeedback() {
    }
    
    public final void playGameOverFeedback() {
    }
    
    private final int getMusicResourceId(java.lang.String musicTrack) {
        return 0;
    }
    
    public final void release() {
    }
    
    private final float pow(float $this$pow, int exponent) {
        return 0.0F;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u000f\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0016X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/tetris/modern/rl/audio/EnhancedAudioManager$Companion;", "", "()V", "EFFECT_DISTORTION", "", "EFFECT_ECHO", "EFFECT_FILTER_SWEEP", "LAYER_CELEBRATION", "LAYER_DANGER", "LAYER_TENSION", "MUSIC_CHIPTUNE", "MUSIC_GAME_OVER", "MUSIC_JAZZ", "MUSIC_MENU", "MUSIC_METAL", "MUSIC_ORCHESTRAL", "MUSIC_SYNTHWAVE", "MUSIC_THEME_A", "MUSIC_THEME_B", "MUSIC_THEME_C", "MUSIC_VICTORY", "SOUND_COMBO", "", "SOUND_COUNTDOWN", "SOUND_DROP", "SOUND_GAME_OVER", "SOUND_HOLD", "SOUND_LEVEL_UP", "SOUND_LINE_CLEAR", "SOUND_MENU_BACK", "SOUND_MENU_SELECT", "SOUND_MOVE", "SOUND_PAUSE", "SOUND_POWER_UP", "SOUND_ROTATE", "SOUND_TETRIS", "SOUND_T_SPIN", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}