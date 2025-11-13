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
import com.tetris.modern.rl.playgames.PlayGamesManager;
import com.tetris.modern.rl.auth.AuthManager;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00ac\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b$\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0007\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001BW\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020,H\u0002J\u0006\u0010I\u001a\u00020GJ\u0006\u0010J\u001a\u00020GJ\b\u0010K\u001a\u00020GH\u0002J\b\u0010L\u001a\u00020GH\u0002J\u0006\u0010M\u001a\u00020GJ\u0006\u0010N\u001a\u00020GJ\b\u0010O\u001a\u00020GH\u0002J\b\u0010P\u001a\u00020GH\u0014J\u0006\u0010Q\u001a\u00020GJ\u0006\u0010R\u001a\u00020GJ\u0006\u0010S\u001a\u00020GJ\u0006\u0010T\u001a\u00020GJ\u0006\u0010U\u001a\u00020GJ\u0006\u0010V\u001a\u00020GJ\u0006\u0010W\u001a\u00020GJ\u0006\u0010X\u001a\u00020GJ\u000e\u0010Y\u001a\u00020GH\u0086@\u00a2\u0006\u0002\u0010ZJ\u000e\u0010[\u001a\u00020G2\u0006\u0010\\\u001a\u00020\"J\b\u0010]\u001a\u00020GH\u0002J\b\u0010^\u001a\u00020GH\u0002J\u0006\u0010_\u001a\u00020GJ\u000e\u0010`\u001a\u00020G2\u0006\u0010a\u001a\u00020\u0019J\u0006\u0010b\u001a\u00020GJ\u000e\u0010c\u001a\u00020G2\u0006\u0010d\u001a\u00020\"J\u0006\u0010e\u001a\u00020GJ\u000e\u0010f\u001a\u00020GH\u0086@\u00a2\u0006\u0002\u0010ZJ\u0006\u0010g\u001a\u00020GJ\u0006\u0010h\u001a\u00020GJ\u0006\u0010i\u001a\u00020GJ\u000e\u0010j\u001a\u00020G2\u0006\u0010k\u001a\u00020lJ\u000e\u0010m\u001a\u00020G2\u0006\u0010n\u001a\u00020\u0019J\u000e\u0010o\u001a\u00020G2\u0006\u0010p\u001a\u00020\"J\u000e\u0010q\u001a\u00020G2\u0006\u0010r\u001a\u00020lJ\u000e\u0010s\u001a\u00020G2\u0006\u0010t\u001a\u00020\u0019J\u000e\u0010u\u001a\u00020G2\u0006\u0010v\u001a\u00020wJ\u000e\u0010x\u001a\u00020G2\u0006\u0010v\u001a\u00020wJ\u000e\u0010y\u001a\u00020G2\u0006\u0010z\u001a\u00020lJ\u000e\u0010{\u001a\u00020G2\u0006\u0010v\u001a\u00020wJ\u000e\u0010|\u001a\u00020G2\u0006\u0010}\u001a\u00020wJ\u000e\u0010~\u001a\u00020G2\u0006\u0010n\u001a\u00020\u0019R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\'0&\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0017\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00190!\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010$R\u0017\u00100\u001a\b\u0012\u0004\u0012\u00020\u00190!\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010$R\u0017\u00101\u001a\b\u0012\u0004\u0012\u00020\u00190+\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010.R\u0017\u00102\u001a\b\u0012\u0004\u0012\u00020\u00190!\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010$R\u0019\u00103\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010504\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010<\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010=04\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u00107R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u001d\u0010A\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020B0&04\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u00107R\u0017\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00190!\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u0010$\u00a8\u0006\u007f"}, d2 = {"Lcom/tetris/modern/rl/ui/viewmodels/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "tetrisEngine", "Lcom/tetris/modern/rl/game/TetrisEngine;", "audioManager", "Lcom/tetris/modern/rl/audio/AudioManager;", "scoreRepository", "Lcom/tetris/modern/rl/data/repository/ScoreRepository;", "settingsRepository", "Lcom/tetris/modern/rl/data/repository/SettingsRepository;", "statisticsRepository", "Lcom/tetris/modern/rl/data/repository/StatisticsRepository;", "progressionRepository", "Lcom/tetris/modern/rl/data/repository/ProgressionRepository;", "playerProgression", "Lcom/tetris/modern/rl/progression/PlayerProgression;", "achievementSystem", "Lcom/tetris/modern/rl/achievements/AchievementSystem;", "playGamesManager", "Lcom/tetris/modern/rl/playgames/PlayGamesManager;", "authManager", "Lcom/tetris/modern/rl/auth/AuthManager;", "(Lcom/tetris/modern/rl/game/TetrisEngine;Lcom/tetris/modern/rl/audio/AudioManager;Lcom/tetris/modern/rl/data/repository/ScoreRepository;Lcom/tetris/modern/rl/data/repository/SettingsRepository;Lcom/tetris/modern/rl/data/repository/StatisticsRepository;Lcom/tetris/modern/rl/data/repository/ProgressionRepository;Lcom/tetris/modern/rl/progression/PlayerProgression;Lcom/tetris/modern/rl/achievements/AchievementSystem;Lcom/tetris/modern/rl/playgames/PlayGamesManager;Lcom/tetris/modern/rl/auth/AuthManager;)V", "_isGooglePlaySignedIn", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getAchievementSystem", "()Lcom/tetris/modern/rl/achievements/AchievementSystem;", "getAudioManager", "()Lcom/tetris/modern/rl/audio/AudioManager;", "getAuthManager", "()Lcom/tetris/modern/rl/auth/AuthManager;", "controlType", "Landroidx/compose/runtime/MutableState;", "", "getControlType", "()Landroidx/compose/runtime/MutableState;", "gameModes", "", "Lcom/tetris/modern/rl/game/modes/GameMode;", "getGameModes", "()Ljava/util/List;", "gameState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/tetris/modern/rl/game/models/GameState;", "getGameState", "()Lkotlinx/coroutines/flow/StateFlow;", "isDarkMode", "isFirstLaunch", "isGooglePlaySignedIn", "isMuted", "overallStatistics", "Lkotlinx/coroutines/flow/Flow;", "Lcom/tetris/modern/rl/data/entities/StatisticsEntity;", "getOverallStatistics", "()Lkotlinx/coroutines/flow/Flow;", "getPlayGamesManager", "()Lcom/tetris/modern/rl/playgames/PlayGamesManager;", "getPlayerProgression", "()Lcom/tetris/modern/rl/progression/PlayerProgression;", "settings", "Lcom/tetris/modern/rl/data/entities/SettingsEntity;", "getSettings", "getTetrisEngine", "()Lcom/tetris/modern/rl/game/TetrisEngine;", "topScores", "Lcom/tetris/modern/rl/data/entities/ScoreEntity;", "getTopScores", "useDynamicColors", "getUseDynamicColors", "handleGameOver", "", "state", "hardDrop", "holdPiece", "initializeProgressionSync", "loadSettings", "moveLeft", "moveRight", "observeGameState", "onCleared", "onPause", "onResume", "pauseGame", "refreshLeaderboard", "resumeGame", "returnToMenu", "rotateClockwise", "rotateCounterClockwise", "saveProgressionIfNeeded", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveScore", "playerName", "saveSettings", "selectAndPlayGameMusic", "setFirstLaunchComplete", "setGooglePlaySignedIn", "signedIn", "softDrop", "startGame", "modeName", "syncLeaderboard", "syncOnResume", "toggleDarkMode", "toggleDynamicColors", "toggleMute", "updateARR", "arr", "", "updateAutoHold", "enabled", "updateControlType", "type", "updateDAS", "das", "updateGhostPiece", "show", "updateMasterVolume", "volume", "", "updateMusicVolume", "updateNextPieces", "count", "updateSfxVolume", "updateTouchSensitivity", "sensitivity", "updateVibration", "app_debug"})
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
    private final com.tetris.modern.rl.playgames.PlayGamesManager playGamesManager = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.auth.AuthManager authManager = null;
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
    com.tetris.modern.rl.achievements.AchievementSystem achievementSystem, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.playgames.PlayGamesManager playGamesManager, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.auth.AuthManager authManager) {
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
    public final com.tetris.modern.rl.playgames.PlayGamesManager getPlayGamesManager() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.auth.AuthManager getAuthManager() {
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
    
    public final void refreshLeaderboard() {
    }
    
    /**
     * Bidirectional sync:
     * 1. Upload local unsynced scores to API
     * 2. Download all scores from API and merge with local
     */
    public final void syncLeaderboard() {
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
    
    /**
     * Sync data when app resumes
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object syncOnResume(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void onPause() {
    }
    
    /**
     * Save progression if needed - called from MainActivity.onPause()
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object saveProgressionIfNeeded(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Initialize progression sync on app startup
     */
    private final void initializeProgressionSync() {
    }
    
    @java.lang.Override
    protected void onCleared() {
    }
}