package com.tetris.modern.rl;

import android.os.Build;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.core.view.WindowCompat;
import com.tetris.modern.rl.ui.viewmodels.SettingsViewModel;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.PlayGames;
import com.tetris.modern.rl.ui.viewmodels.MainViewModel;
import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u0011H\u0014J\b\u0010\u0015\u001a\u00020\u0011H\u0014J\b\u0010\u0016\u001a\u00020\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0017"}, d2 = {"Lcom/tetris/modern/rl/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "gamesSignInClient", "Lcom/google/android/gms/games/GamesSignInClient;", "settingsViewModel", "Lcom/tetris/modern/rl/ui/viewmodels/SettingsViewModel;", "getSettingsViewModel", "()Lcom/tetris/modern/rl/ui/viewmodels/SettingsViewModel;", "settingsViewModel$delegate", "Lkotlin/Lazy;", "viewModel", "Lcom/tetris/modern/rl/ui/viewmodels/MainViewModel;", "getViewModel", "()Lcom/tetris/modern/rl/ui/viewmodels/MainViewModel;", "viewModel$delegate", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onResume", "signInSilently", "app_debug"})
public final class MainActivity extends androidx.activity.ComponentActivity {
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy settingsViewModel$delegate = null;
    private com.google.android.gms.games.GamesSignInClient gamesSignInClient;
    
    public MainActivity() {
        super(0);
    }
    
    private final com.tetris.modern.rl.ui.viewmodels.MainViewModel getViewModel() {
        return null;
    }
    
    private final com.tetris.modern.rl.ui.viewmodels.SettingsViewModel getSettingsViewModel() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void signInSilently() {
    }
    
    @java.lang.Override
    protected void onResume() {
    }
    
    @java.lang.Override
    protected void onPause() {
    }
}