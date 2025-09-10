package com.tetris.modern.rl.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetris.modern.rl.audio.AudioManager
import com.tetris.modern.rl.data.database.TetrisDatabase
import com.tetris.modern.rl.data.entities.ScoreEntity
import com.tetris.modern.rl.data.repository.ScoreRepository
import com.tetris.modern.rl.data.repository.SettingsRepository
import com.tetris.modern.rl.data.repository.StatisticsRepository
import com.tetris.modern.rl.data.repository.ProgressionRepository
import com.tetris.modern.rl.game.TetrisEngine
import com.tetris.modern.rl.game.modes.*
import com.tetris.modern.rl.achievements.AchievementSystem
import com.tetris.modern.rl.progression.PlayerProgression
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val tetrisEngine: TetrisEngine,
    val audioManager: AudioManager,
    private val scoreRepository: ScoreRepository,
    private val settingsRepository: SettingsRepository,
    private val statisticsRepository: StatisticsRepository,
    private val progressionRepository: ProgressionRepository,
    val playerProgression: PlayerProgression,
    val achievementSystem: AchievementSystem
) : ViewModel() {
    
    // UI State
    val isDarkMode = mutableStateOf(true)
    val useDynamicColors = mutableStateOf(false)
    val isMuted = mutableStateOf(false)
    val controlType = mutableStateOf("touch")
    val isFirstLaunch = mutableStateOf(true)
    
    // Google Play Games
    private val _isGooglePlaySignedIn = MutableStateFlow(false)
    val isGooglePlaySignedIn: StateFlow<Boolean> = _isGooglePlaySignedIn.asStateFlow()
    
    // Game State
    val gameState = tetrisEngine.gameState
    
    // Scores
    val topScores = scoreRepository.getTopScores()
    
    // Settings
    val settings = settingsRepository.getSettings()
    
    // Statistics
    val overallStatistics = statisticsRepository.getStatisticsByMode("overall")
    
    // Available game modes
    val gameModes = listOf(
        ClassicMode(),
        SprintMode(),
        MarathonMode(),
        ZenMode(),
        PuzzleMode(),
        BattleMode(),
        PowerUpMode(),
        Battle2PMode(),
        DailyChallengeMode()
    )
    
    init {
        loadSettings()
        observeGameState()
        // Start menu music when app starts
        audioManager.playMusic(AudioManager.MUSIC_MENU)
    }
    
    private fun observeGameState() {
        viewModelScope.launch {
            var wasGameOver = false
            Timber.d("Starting game state observation")
            gameState.collect { state ->
                Timber.d("Game state collected - isGameOver: ${state.isGameOver}, wasGameOver: $wasGameOver")
                if (state.isGameOver && !wasGameOver) {
                    Timber.d("Game over detected! Score: ${state.score}, Lines: ${state.lines}")
                    // Game just ended, save stats and progression
                    handleGameOver(state)
                    wasGameOver = true
                } else if (!state.isGameOver) {
                    wasGameOver = false
                }
            }
        }
    }
    
    private fun handleGameOver(state: com.tetris.modern.rl.game.models.GameState) {
        viewModelScope.launch {
            try {
                Timber.d("handleGameOver called with score: ${state.score}, lines: ${state.lines}")
                
                // Calculate statistics from the game
                val tetrises = state.statistics["tetrises"] ?: 0
                val tSpins = state.statistics["tspins"] ?: 0
                val perfectClears = 0 // Could be tracked in statistics
                val maxCombo = state.statistics["maxCombo"] ?: 0
                
                Timber.d("Statistics - Tetrises: $tetrises, T-Spins: $tSpins, Max Combo: $maxCombo")
                
                // Check achievements
                val currentProgression = progressionRepository.getProgression()
                val currentStats = playerProgression.progressionState.value.statistics
                
                val gameStats = com.tetris.modern.rl.achievements.AchievementSystem.GameStats(
                    score = state.score,
                    lines = state.lines,
                    level = state.level,
                    tSpins = tSpins,
                    tetrises = tetrises,
                    perfectClears = perfectClears,
                    maxCombo = maxCombo,
                    gameMode = state.gameMode,
                    duration = state.gameDuration,
                    totalGamesPlayed = currentStats.totalGamesPlayed + 1,
                    totalLinesCleared = currentStats.totalLinesCleared + state.lines,
                    highestScore = maxOf(currentStats.highestScore, state.score)
                )
                
                Timber.d("Checking achievements with GameStats - Score: ${gameStats.score}, Lines: ${gameStats.lines}, Level: ${gameStats.level}")
                Timber.d("GameStats - Tetrises: ${gameStats.tetrises}, T-Spins: ${gameStats.tSpins}, Max Combo: ${gameStats.maxCombo}")
                Timber.d("GameStats - Total Lines: ${gameStats.totalLinesCleared}, Total Games: ${gameStats.totalGamesPlayed}")
                
                val unlockedAchievements = achievementSystem.checkAchievements(gameStats)
                
                if (unlockedAchievements.isNotEmpty()) {
                    Timber.d("Achievements unlocked: ${unlockedAchievements.map { it.name }}")
                    // Play achievement sound
                    audioManager.playSound(com.tetris.modern.rl.audio.AudioManager.SOUND_POWER_UP)
                    
                    // Add XP for each achievement
                    unlockedAchievements.forEach { achievement ->
                        playerProgression.addXP(achievement.xpReward, "achievement_${achievement.id}")
                    }
                }
                
                // Update player progression with XP
                playerProgression.processGameResults(
                    score = state.score,
                    lines = state.lines,
                    level = state.level,
                    gameMode = state.gameMode,
                    tSpins = tSpins,
                    tetrises = tetrises,
                    perfectClears = perfectClears,
                    maxCombo = maxCombo,
                    duration = state.gameDuration
                )
                
                // Record statistics in database
                statisticsRepository.recordGameStats(
                    gameMode = state.gameMode,
                    lines = state.lines,
                    score = state.score,
                    playTime = state.gameDuration,
                    pieces = state.piecesPlaced,
                    tetrises = tetrises,
                    tSpins = tSpins,
                    perfectClears = perfectClears,
                    maxCombo = maxCombo,
                    level = state.level
                )
                
                // Save progression to database (XP already added by processGameResults)
                progressionRepository.saveProgression()
                
                Timber.d("Game stats saved - Score: ${state.score}, Lines: ${state.lines}, XP earned")
            } catch (e: Exception) {
                Timber.e(e, "Failed to save game statistics")
            }
        }
    }
    
    private fun loadSettings() {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                settings?.let {
                    isDarkMode.value = it.isDarkMode
                    useDynamicColors.value = it.useDynamicColors
                    isMuted.value = it.isMuted
                    controlType.value = it.controlType
                    isFirstLaunch.value = it.isFirstLaunch
                }
            }
        }
    }
    
    fun startGame(modeName: String) {
        audioManager.playSound(AudioManager.SOUND_MENU_SELECT)
        
        // Stop menu music and start game music
        audioManager.stopMusic()
        selectAndPlayGameMusic()
        
        // Map mode IDs to actual mode names
        val modeMapping = mapOf(
            "classic" to "Classic",
            "sprint" to "Sprint",
            "marathon" to "Marathon",
            "zen" to "Zen",
            "puzzle" to "Puzzle",
            "battle" to "Battle",
            "powerup" to "PowerUp",
            "battle2p" to "Battle2P",
            "daily" to "Daily Challenge"
        )
        
        val actualModeName = modeMapping[modeName.lowercase()] ?: modeName
        val mode = gameModes.find { it.name == actualModeName }
        
        if (mode != null) {
            tetrisEngine.startGame(mode)
            Timber.d("Started game with mode: $actualModeName")
        } else {
            Timber.e("Unknown game mode: $modeName (mapped to: $actualModeName)")
        }
    }
    
    private fun selectAndPlayGameMusic() {
        val unlockedMusic = playerProgression.progressionState.value.unlockedMusic
        
        // Always start with normal speed Theme A
        // Speed will increase with level via adjustMusicTempo
        audioManager.playMusic(AudioManager.MUSIC_THEME_A)
        Timber.d("Playing game music: Theme A (normal speed)")
    }
    
    fun pauseGame() {
        tetrisEngine.pauseGame()
    }
    
    fun resumeGame() {
        tetrisEngine.resumeGame()
    }
    
    fun moveLeft() {
        tetrisEngine.moveLeft()
    }
    
    fun moveRight() {
        tetrisEngine.moveRight()
    }
    
    fun rotateClockwise() {
        tetrisEngine.rotateClockwise()
    }
    
    fun rotateCounterClockwise() {
        tetrisEngine.rotateCounterClockwise()
    }
    
    fun softDrop() {
        tetrisEngine.softDrop()
    }
    
    fun hardDrop() {
        tetrisEngine.hardDrop()
    }
    
    fun holdPiece() {
        tetrisEngine.holdPiece()
    }
    
    fun saveScore(playerName: String) {
        viewModelScope.launch {
            val state = gameState.value
            val score = ScoreEntity(
                playerName = playerName,
                score = state.score,
                lines = state.lines,
                level = state.level,
                gameMode = state.gameMode,
                duration = state.gameDuration
            )
            
            scoreRepository.saveScore(score)
            Timber.d("Score saved: ${score.score} points")
        }
    }
    
    fun toggleDarkMode() {
        isDarkMode.value = !isDarkMode.value
        saveSettings()
    }
    
    fun toggleDynamicColors() {
        useDynamicColors.value = !useDynamicColors.value
        saveSettings()
    }
    
    fun toggleMute() {
        isMuted.value = !isMuted.value
        audioManager.setMuted(isMuted.value)
        saveSettings()
    }
    
    private fun saveSettings() {
        viewModelScope.launch {
            settingsRepository.getSettings().firstOrNull()?.let { currentSettings ->
                settingsRepository.updateSettings(
                    currentSettings.copy(
                        isDarkMode = isDarkMode.value,
                        useDynamicColors = useDynamicColors.value,
                        isMuted = isMuted.value
                    )
                )
            }
        }
    }
    
    fun updateMasterVolume(volume: Float) {
        viewModelScope.launch {
            settingsRepository.updateMasterVolume(volume)
        }
    }
    
    fun updateMusicVolume(volume: Float) {
        audioManager.setMusicVolume(volume)
        viewModelScope.launch {
            settingsRepository.getSettings().firstOrNull()?.let {
                settingsRepository.updateSettings(it.copy(musicVolume = volume))
            }
        }
    }
    
    fun updateSfxVolume(volume: Float) {
        audioManager.setSfxVolume(volume)
        viewModelScope.launch {
            settingsRepository.getSettings().firstOrNull()?.let {
                settingsRepository.updateSettings(it.copy(sfxVolume = volume))
            }
        }
    }
    
    fun updateVibration(enabled: Boolean) {
        audioManager.setVibrationEnabled(enabled)
        viewModelScope.launch {
            settingsRepository.getSettings().firstOrNull()?.let {
                settingsRepository.updateSettings(it.copy(vibrationEnabled = enabled))
            }
        }
    }
    
    fun updateTouchSensitivity(sensitivity: Float) {
        viewModelScope.launch {
            settingsRepository.getSettings().firstOrNull()?.let {
                settingsRepository.updateSettings(it.copy(touchSensitivity = sensitivity))
            }
        }
    }
    
    fun updateDAS(das: Int) {
        viewModelScope.launch {
            settingsRepository.getSettings().firstOrNull()?.let {
                settingsRepository.updateSettings(it.copy(das = das))
            }
        }
    }
    
    fun updateARR(arr: Int) {
        viewModelScope.launch {
            settingsRepository.getSettings().firstOrNull()?.let {
                settingsRepository.updateSettings(it.copy(arr = arr))
            }
        }
    }
    
    fun updateGhostPiece(show: Boolean) {
        viewModelScope.launch {
            settingsRepository.getSettings().firstOrNull()?.let {
                settingsRepository.updateSettings(it.copy(showGhostPiece = show))
            }
        }
    }
    
    fun updateNextPieces(count: Int) {
        viewModelScope.launch {
            settingsRepository.getSettings().firstOrNull()?.let {
                settingsRepository.updateSettings(it.copy(showNextPieces = count))
            }
        }
    }
    
    fun updateAutoHold(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.getSettings().firstOrNull()?.let {
                settingsRepository.updateSettings(it.copy(autoHold = enabled))
            }
        }
    }
    
    fun setGooglePlaySignedIn(signedIn: Boolean) {
        _isGooglePlaySignedIn.value = signedIn
    }
    
    fun updateControlType(type: String) {
        controlType.value = type
        viewModelScope.launch {
            settingsRepository.updateControlType(type)
        }
    }
    
    fun setFirstLaunchComplete() {
        isFirstLaunch.value = false
        viewModelScope.launch {
            settingsRepository.updateFirstLaunch(false)
        }
    }
    
    fun returnToMenu() {
        // Stop game and return to menu
        audioManager.stopMusic()
        audioManager.playMusic(AudioManager.MUSIC_MENU)
        audioManager.playSound(AudioManager.SOUND_MENU_BACK)
    }
    
    fun onResume() {
        // Handle app resume
        if (!gameState.value.isPlaying) {
            // If we're in the menu, play menu music
            audioManager.playMusic(AudioManager.MUSIC_MENU)
        }
    }
    
    fun onPause() {
        // Handle app pause
        if (gameState.value.isPlaying && !gameState.value.isPaused) {
            pauseGame()
        }
        audioManager.pauseMusic()
    }
    
    override fun onCleared() {
        super.onCleared()
        // Clean up resources if needed
    }
}