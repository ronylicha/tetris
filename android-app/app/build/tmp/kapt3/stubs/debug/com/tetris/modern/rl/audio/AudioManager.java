package com.tetris.modern.rl.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import com.tetris.modern.rl.R;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.*;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0016\n\u0002\b\u0002\b\u0007\u0018\u0000 F2\u00020\u0001:\u0001FB\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0015J\u0010\u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\bH\u0002J\b\u0010#\u001a\u00020\u001fH\u0002J\b\u0010$\u001a\u00020\u001fH\u0002J\u0010\u0010%\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\'H\u0016J\u0010\u0010(\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\'H\u0016J\u0010\u0010)\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\'H\u0016J\u0006\u0010*\u001a\u00020\u001fJ\u000e\u0010+\u001a\u00020\u001f2\u0006\u0010,\u001a\u00020\u0015J\u0006\u0010-\u001a\u00020\u001fJ\u000e\u0010.\u001a\u00020\u001f2\u0006\u0010/\u001a\u00020\u0015J\u0018\u00100\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\b2\b\b\u0002\u00101\u001a\u00020\nJ\u0018\u00102\u001a\u00020\u001f2\u0006\u00103\u001a\u00020\u00152\b\b\u0002\u00104\u001a\u00020\u0015J\u0006\u00105\u001a\u00020\u001fJ\u0006\u00106\u001a\u00020\u001fJ\u0006\u00107\u001a\u00020\u001fJ\u000e\u00108\u001a\u00020\u001f2\u0006\u00109\u001a\u00020\u0011J\u000e\u0010:\u001a\u00020\u001f2\u0006\u0010;\u001a\u00020\nJ\u000e\u0010<\u001a\u00020\u001f2\u0006\u00109\u001a\u00020\u0011J\u000e\u0010=\u001a\u00020\u001f2\u0006\u0010>\u001a\u00020\nJ\u0006\u0010?\u001a\u00020\u001fJ\u0010\u0010@\u001a\u00020\u001f2\b\b\u0002\u0010A\u001a\u00020BJ\u000e\u0010C\u001a\u00020\u001f2\u0006\u0010D\u001a\u00020ER\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0018\u001a\u00020\u00198BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006G"}, d2 = {"Lcom/tetris/modern/rl/audio/AudioManager;", "Landroidx/lifecycle/DefaultLifecycleObserver;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "audioScope", "Lkotlinx/coroutines/CoroutineScope;", "currentMusic", "", "isMuted", "", "isVibrationEnabled", "mediaPlayer", "Landroid/media/MediaPlayer;", "musicJob", "Lkotlinx/coroutines/Job;", "musicVolume", "", "sfxVolume", "soundMap", "", "", "soundPool", "Landroid/media/SoundPool;", "vibrator", "Landroid/os/Vibrator;", "getVibrator", "()Landroid/os/Vibrator;", "vibrator$delegate", "Lkotlin/Lazy;", "adjustMusicTempo", "", "level", "getMusicResourceId", "musicTrack", "initializeSoundPool", "loadSoundEffects", "onDestroy", "owner", "Landroidx/lifecycle/LifecycleOwner;", "onStart", "onStop", "pauseMusic", "playComboFeedback", "combo", "playGameOverFeedback", "playLineClearFeedback", "lines", "playMusic", "loop", "playSound", "soundId", "priority", "playTSpinFeedback", "release", "resumeMusic", "setMusicVolume", "volume", "setMuted", "muted", "setSfxVolume", "setVibrationEnabled", "enabled", "stopMusic", "vibrate", "duration", "", "vibratePattern", "pattern", "", "Companion", "app_debug"})
public final class AudioManager implements androidx.lifecycle.DefaultLifecycleObserver {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope audioScope = null;
    @org.jetbrains.annotations.Nullable
    private kotlinx.coroutines.Job musicJob;
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
    @org.jetbrains.annotations.Nullable
    private android.media.SoundPool soundPool;
    @org.jetbrains.annotations.NotNull
    private final java.util.Map<java.lang.Integer, java.lang.Integer> soundMap = null;
    @org.jetbrains.annotations.Nullable
    private android.media.MediaPlayer mediaPlayer;
    @org.jetbrains.annotations.Nullable
    private java.lang.String currentMusic;
    private boolean isMuted = false;
    private float musicVolume = 0.7F;
    private float sfxVolume = 0.8F;
    private boolean isVibrationEnabled = true;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy vibrator$delegate = null;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.audio.AudioManager.Companion Companion = null;
    
    @javax.inject.Inject
    public AudioManager(@dagger.hilt.android.qualifiers.ApplicationContext
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
    
    public final void playMusic(@org.jetbrains.annotations.NotNull
    java.lang.String musicTrack, boolean loop) {
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
    
    public final void playComboFeedback(int combo) {
    }
    
    public final void playGameOverFeedback() {
    }
    
    public final void adjustMusicTempo(int level) {
    }
    
    private final int getMusicResourceId(java.lang.String musicTrack) {
        return 0;
    }
    
    public final void release() {
    }
    
    @java.lang.Override
    public void onDestroy(@org.jetbrains.annotations.NotNull
    androidx.lifecycle.LifecycleOwner owner) {
    }
    
    @java.lang.Override
    public void onStop(@org.jetbrains.annotations.NotNull
    androidx.lifecycle.LifecycleOwner owner) {
    }
    
    @java.lang.Override
    public void onStart(@org.jetbrains.annotations.NotNull
    androidx.lifecycle.LifecycleOwner owner) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u000f\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0010X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/tetris/modern/rl/audio/AudioManager$Companion;", "", "()V", "MUSIC_CHIPTUNE", "", "MUSIC_GAME_OVER", "MUSIC_JAZZ", "MUSIC_MENU", "MUSIC_METAL", "MUSIC_ORCHESTRAL", "MUSIC_SYNTHWAVE", "MUSIC_THEME_A", "MUSIC_THEME_B", "MUSIC_THEME_C", "MUSIC_VICTORY", "SOUND_COMBO", "", "SOUND_COUNTDOWN", "SOUND_DROP", "SOUND_GAME_OVER", "SOUND_HOLD", "SOUND_LEVEL_UP", "SOUND_LINE_CLEAR", "SOUND_MENU_BACK", "SOUND_MENU_SELECT", "SOUND_MOVE", "SOUND_PAUSE", "SOUND_POWER_UP", "SOUND_ROTATE", "SOUND_TETRIS", "SOUND_T_SPIN", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}