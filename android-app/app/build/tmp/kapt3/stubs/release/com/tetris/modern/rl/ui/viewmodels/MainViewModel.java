package com.tetris.modern.rl.ui.viewmodels;

import androidx.lifecycle.ViewModel;
import com.tetris.modern.rl.audio.AudioManager;
import com.tetris.modern.rl.data.database.TetrisDatabase;
import com.tetris.modern.rl.data.entities.ScoreEntity;
import com.tetris.modern.rl.data.repository.ScoreRepository;
import com.tetris.modern.rl.data.repository.SettingsRepository;
import com.tetris.modern.rl.data.repository.StatisticsRepository;
import com.tetris.modern.rl.data.repository.ProgressionRepository;
import com.tetris.modern.rl.game.TetrisEngine;
import com.tetris.modern.rl.game.modes.*;
import com.tetris.modern.rl.achievements.AchievementSystem;
import com.tetris.modern.rl.progression.PlayerProgression;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u001e\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0007\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001BG\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020&H\u0002J\u0006\u0010A\u001a\u00020?J\u0006\u0010B\u001a\u00020?J\b\u0010C\u001a\u00020?H\u0002J\u0006\u0010D\u001a\u00020?J\u0006\u0010E\u001a\u00020?J\b\u0010F\u001a\u00020?H\u0002J\b\u0010G\u001a\u00020?H\u0014J\u0006\u0010H\u001a\u00020?J\u0006\u0010I\u001a\u00020?J\u0006\u0010J\u001a\u00020?J\u0006\u0010K\u001a\u00020?J\u0006\u0010L\u001a\u00020?J\u0006\u0010M\u001a\u00020?J\u0006\u0010N\u001a\u00020?J\u000e\u0010O\u001a\u00020?2\u0006\u0010P\u001a\u00020\u001cJ\b\u0010Q\u001a\u00020?H\u0002J\b\u0010R\u001a\u00020?H\u0002J\u0006\u0010S\u001a\u00020?J\u000e\u0010T\u001a\u00020?2\u0006\u0010U\u001a\u00020\u0015J\u0006\u0010V\u001a\u00020?J\u000e\u0010W\u001a\u00020?2\u0006\u0010X\u001a\u00020\u001cJ\u0006\u0010Y\u001a\u00020?J\u0006\u0010Z\u001a\u00020?J\u0006\u0010[\u001a\u00020?J\u000e\u0010\\\u001a\u00020?2\u0006\u0010]\u001a\u00020^J\u000e\u0010_\u001a\u00020?2\u0006\u0010`\u001a\u00020\u0015J\u000e\u0010a\u001a\u00020?2\u0006\u0010b\u001a\u00020\u001cJ\u000e\u0010c\u001a\u00020?2\u0006\u0010d\u001a\u00020^J\u000e\u0010e\u001a\u00020?2\u0006\u0010f\u001a\u00020\u0015J\u000e\u0010g\u001a\u00020?2\u0006\u0010h\u001a\u00020iJ\u000e\u0010j\u001a\u00020?2\u0006\u0010h\u001a\u00020iJ\u000e\u0010k\u001a\u00020?2\u0006\u0010l\u001a\u00020^J\u000e\u0010m\u001a\u00020?2\u0006\u0010h\u001a\u00020iJ\u000e\u0010n\u001a\u00020?2\u0006\u0010o\u001a\u00020iJ\u000e\u0010p\u001a\u00020?2\u0006\u0010`\u001a\u00020\u0015R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 \u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0017\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001eR\u0017\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001eR\u0017\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00150%\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010(R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001eR\u0019\u0010-\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010/0.\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u00104\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001050.\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u00101R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u001d\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020:0 0.\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u00101R\u0017\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010\u001e\u00a8\u0006q"}, d2 = {"Lcom/tetris/modern/rl/ui/viewmodels/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "tetrisEngine", "Lcom/tetris/modern/rl/game/TetrisEngine;", "audioManager", "Lcom/tetris/modern/rl/audio/AudioManager;", "scoreRepository", "Lcom/tetris/modern/rl/data/repository/ScoreRepository;", "settingsRepository", "Lcom/tetris/modern/rl/data/repository/SettingsRepository;", "statisticsRepository", "Lcom/tetris/modern/rl/data/repository/StatisticsRepository;", "progressionRepository", "Lcom/tetris/modern/rl/data/repository/ProgressionRepository;", "playerProgression", "Lcom/tetris/modern/rl/progression/PlayerProgression;", "achievementSystem", "Lcom/tetris/modern/rl/achievements/AchievementSystem;", "(Lcom/tetris/modern/rl/game/TetrisEngine;Lcom/tetris/modern/rl/audio/AudioManager;Lcom/tetris/modern/rl/data/repository/ScoreRepository;Lcom/tetris/modern/rl/data/repository/SettingsRepository;Lcom/tetris/modern/rl/data/repository/StatisticsRepository;Lcom/tetris/modern/rl/data/repository/ProgressionRepository;Lcom/tetris/modern/rl/progression/PlayerProgression;Lcom/tetris/modern/rl/achievements/AchievementSystem;)V", "_isGooglePlaySignedIn", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getAchievementSystem", "()Lcom/tetris/modern/rl/achievements/AchievementSystem;", "getAudioManager", "()Lcom/tetris/modern/rl/audio/AudioManager;", "controlType", "Landroidx/compose/runtime/MutableState;", "", "getControlType", "()Landroidx/compose/runtime/MutableState;", "gameModes", "", "Lcom/tetris/modern/rl/game/modes/GameMode;", "getGameModes", "()Ljava/util/List;", "gameState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/tetris/modern/rl/game/models/GameState;", "getGameState", "()Lkotlinx/coroutines/flow/StateFlow;", "isDarkMode", "isFirstLaunch", "isGooglePlaySignedIn", "isMuted", "overallStatistics", "Lkotlinx/coroutines/flow/Flow;", "Lcom/tetris/modern/rl/data/entities/StatisticsEntity;", "getOverallStatistics", "()Lkotlinx/coroutines/flow/Flow;", "getPlayerProgression", "()Lcom/tetris/modern/rl/progression/PlayerProgression;", "settings", "Lcom/tetris/modern/rl/data/entities/SettingsEntity;", "getSettings", "getTetrisEngine", "()Lcom/tetris/modern/rl/game/TetrisEngine;", "topScores", "Lcom/tetris/modern/rl/data/entities/ScoreEntity;", "getTopScores", "useDynamicColors", "getUseDynamicColors", "handleGameOver", "", "state", "hardDrop", "holdPiece", "loadSettings", "moveLeft", "moveRight", "observeGameState", "onCleared", "onPause", "onResume", "pauseGame", "resumeGame", "returnToMenu", "rotateClockwise", "rotateCounterClockwise", "saveScore", "playerName", "saveSettings", "selectAndPlayGameMusic", "setFirstLaunchComplete", "setGooglePlaySignedIn", "signedIn", "softDrop", "startGame", "modeName", "toggleDarkMode", "toggleDynamicColors", "toggleMute", "updateARR", "arr", "", "updateAutoHold", "enabled", "updateControlType", "type", "updateDAS", "das", "updateGhostPiece", "show", "updateMasterVolume", "volume", "", "updateMusicVolume", "updateNextPieces", "count", "updateSfxVolume", "updateTouchSensitivity", "sensitivity", "updateVibration", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.game.TetrisEngine tetrisEngine = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.audio.AudioManager audioManager = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.repository.ScoreRepository scoreRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.repository.SettingsRepository settingsRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.repository.StatisticsRepository statisticsRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.repository.ProgressionRepository progressionRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.progression.PlayerProgression playerProgression = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.achievements.AchievementSystem achievementSystem = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.runtime.MutableState<java.lang.Boolean> isDarkMode = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.runtime.MutableState<java.lang.Boolean> useDynamicColors = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.runtime.MutableState<java.lang.Boolean> isMuted = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.runtime.MutableState<java.lang.String> controlType = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.runtime.MutableState<java.lang.Boolean> isFirstLaunch = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isGooglePlaySignedIn = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isGooglePlaySignedIn = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.game.models.GameState> gameState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> topScores = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.SettingsEntity> settings = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.StatisticsEntity> overallStatistics = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.game.modes.GameMode> gameModes = null;
    
    @javax.inject.Inject
    public MainViewModel(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.TetrisEngine tetrisEngine, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.audio.AudioManager audioManager, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.repository.ScoreRepository scoreRepository, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.repository.SettingsRepository settingsRepository, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.repository.StatisticsRepository statisticsRepository, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.repository.ProgressionRepository progressionRepository, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.progression.PlayerProgression playerProgression, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.achievements.AchievementSystem achievementSystem) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.game.TetrisEngine getTetrisEngine() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.audio.AudioManager getAudioManager() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.progression.PlayerProgression getPlayerProgression() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.achievements.AchievementSystem getAchievementSystem() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.runtime.MutableState<java.lang.Boolean> isDarkMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.runtime.MutableState<java.lang.Boolean> getUseDynamicColors() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.runtime.MutableState<java.lang.Boolean> isMuted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.runtime.MutableState<java.lang.String> getControlType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.runtime.MutableState<java.lang.Boolean> isFirstLaunch() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isGooglePlaySignedIn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.game.models.GameState> getGameState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getTopScores() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.SettingsEntity> getSettings() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.StatisticsEntity> getOverallStatistics() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.game.modes.GameMode> getGameModes() {
        return null;
    }
    
    private final void observeGameState() {
    }
    
    private final void handleGameOver(com.tetris.modern.rl.game.models.GameState state) {
    }
    
    private final void loadSettings() {
    }
    
    public final void startGame(@org.jetbrains.annotations.NotNull
    java.lang.String modeName) {
    }
    
    private final void selectAndPlayGameMusic() {
    }
    
    public final void pauseGame() {
    }
    
    public final void resumeGame() {
    }
    
    public final void moveLeft() {
    }
    
    public final void moveRight() {
    }
    
    public final void rotateClockwise() {
    }
    
    public final void rotateCounterClockwise() {
    }
    
    public final void softDrop() {
    }
    
    public final void hardDrop() {
    }
    
    public final void holdPiece() {
    }
    
    public final void saveScore(@org.jetbrains.annotations.NotNull
    java.lang.String playerName) {
    }
    
    public final void toggleDarkMode() {
    }
    
    public final void toggleDynamicColors() {
    }
    
    public final void toggleMute() {
    }
    
    private final void saveSettings() {
    }
    
    public final void updateMasterVolume(float volume) {
    }
    
    public final void updateMusicVolume(float volume) {
    }
    
    public final void updateSfxVolume(float volume) {
    }
    
    public final void updateVibration(boolean enabled) {
    }
    
    public final void updateTouchSensitivity(float sensitivity) {
    }
    
    public final void updateDAS(int das) {
    }
    
    public final void updateARR(int arr) {
    }
    
    public final void updateGhostPiece(boolean show) {
    }
    
    public final void updateNextPieces(int count) {
    }
    
    public final void updateAutoHold(boolean enabled) {
    }
    
    public final void setGooglePlaySignedIn(boolean signedIn) {
    }
    
    public final void updateControlType(@org.jetbrains.annotations.NotNull
    java.lang.String type) {
    }
    
    public final void setFirstLaunchComplete() {
    }
    
    public final void returnToMenu() {
    }
    
    public final void onResume() {
    }
    
    public final void onPause() {
    }
    
    @java.lang.Override
    protected void onCleared() {
    }
}