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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\u00a8\u0006\b"}, d2 = {"dangerLevel", "", "Lcom/tetris/modern/rl/game/models/GameState;", "getDangerLevel", "(Lcom/tetris/modern/rl/game/models/GameState;)F", "isPowerUpActive", "", "(Lcom/tetris/modern/rl/game/models/GameState;)Z", "app_debug"})
public final class EnhancedAudioManagerKt {
    
    public static final float getDangerLevel(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.GameState $this$dangerLevel) {
        return 0.0F;
    }
    
    public static final boolean isPowerUpActive(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.GameState $this$isPowerUpActive) {
        return false;
    }
}