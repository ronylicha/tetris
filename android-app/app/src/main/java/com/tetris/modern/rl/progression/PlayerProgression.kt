package com.tetris.modern.rl.progression

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.pow

@Singleton
class PlayerProgression @Inject constructor() {
    
    companion object {
        const val MAX_LEVEL = 100
        const val XP_PER_LEVEL_BASE = 1000
        const val XP_PER_LEVEL_MULTIPLIER = 1.15f
        
        // XP Rewards
        const val XP_PER_LINE = 10
        const val XP_PER_TETRIS = 100
        const val XP_PER_T_SPIN = 50
        const val XP_PER_PERFECT_CLEAR = 200
        const val XP_PER_COMBO = 15
        const val XP_PER_LEVEL_UP = 100
        const val XP_DAILY_CHALLENGE = 500
        const val XP_ACHIEVEMENT = 250
    }
    
    data class ProgressionState(
        val level: Int = 1,
        val currentXP: Int = 0,
        val totalXP: Int = 0,
        val xpToNextLevel: Int = XP_PER_LEVEL_BASE,
        val rank: Rank = Rank.NOVICE,
        val unlockedThemes: Set<String> = setOf("default"),
        val unlockedMusic: Set<String> = setOf("theme_a"),
        val unlockedPieceStyles: Set<String> = setOf("standard"),
        val unlockedEffects: Set<String> = setOf("basic"),
        val statistics: PlayerStatistics = PlayerStatistics()
    )
    
    enum class Rank(val minLevel: Int, val displayName: String, val color: String) {
        NOVICE(1, "Novice", "#808080"),
        APPRENTICE(10, "Apprentice", "#CD7F32"),
        ADEPT(20, "Adept", "#C0C0C0"),
        EXPERT(30, "Expert", "#FFD700"),
        MASTER(40, "Master", "#E5E4E2"),
        GRANDMASTER(50, "Grandmaster", "#50C878"),
        LEGEND(60, "Legend", "#FF4500"),
        MYTHIC(70, "Mythic", "#9966CC"),
        DIVINE(85, "Divine", "#00FFFF"),
        ETERNAL(100, "Eternal", "#FFD700")
    }
    
    data class PlayerStatistics(
        val totalGamesPlayed: Int = 0,
        val totalLinesCleared: Int = 0,
        val totalScore: Long = 0,
        val highestScore: Int = 0,
        val totalTSpins: Int = 0,
        val totalTetrises: Int = 0,
        val totalPerfectClears: Int = 0,
        val longestCombo: Int = 0,
        val totalPlayTime: Long = 0,
        val favoriteMode: String = "Classic",
        val winRate: Float = 0f
    )
    
    data class Unlockable(
        val id: String,
        val name: String,
        val type: UnlockableType,
        val requiredLevel: Int,
        val description: String,
        val previewUrl: String? = null
    )
    
    enum class UnlockableType {
        THEME, MUSIC, PIECE_STYLE, EFFECT
    }
    
    private val _progressionState = MutableStateFlow(ProgressionState())
    val progressionState: StateFlow<ProgressionState> = _progressionState.asStateFlow()
    
    private val unlockables = listOf(
        // Themes
        Unlockable("cyberpunk", "Cyberpunk", UnlockableType.THEME, 1, "Neon lights and digital rain"),
        Unlockable("retro", "Retro 8-bit", UnlockableType.THEME, 5, "Classic arcade nostalgia"),
        Unlockable("nature", "Nature", UnlockableType.THEME, 10, "Calming natural colors"),
        Unlockable("minimal", "Minimal", UnlockableType.THEME, 15, "Clean and simple design"),
        Unlockable("galaxy", "Galaxy", UnlockableType.THEME, 25, "Cosmic space theme"),
        Unlockable("matrix", "Matrix", UnlockableType.THEME, 35, "Digital rain effect"),
        Unlockable("rainbow", "Rainbow", UnlockableType.THEME, 50, "Vibrant rainbow colors"),
        
        // Music
        Unlockable("chiptune", "Chiptune", UnlockableType.MUSIC, 3, "8-bit retro music"),
        Unlockable("synthwave", "Synthwave", UnlockableType.MUSIC, 8, "80s electronic vibes"),
        Unlockable("orchestral", "Orchestral", UnlockableType.MUSIC, 20, "Epic classical score"),
        Unlockable("jazz", "Jazz", UnlockableType.MUSIC, 30, "Smooth jazz rhythms"),
        Unlockable("metal", "Metal", UnlockableType.MUSIC, 40, "Heavy metal intensity"),
        Unlockable("lofi", "Lo-fi", UnlockableType.MUSIC, 60, "Relaxing lo-fi beats"),
        
        // Piece Styles
        Unlockable("glass", "Glass", UnlockableType.PIECE_STYLE, 7, "Transparent glass pieces"),
        Unlockable("pixel", "Pixel", UnlockableType.PIECE_STYLE, 12, "Chunky pixel art"),
        Unlockable("hologram", "Hologram", UnlockableType.PIECE_STYLE, 22, "Futuristic holograms"),
        Unlockable("crystal", "Crystal", UnlockableType.PIECE_STYLE, 32, "Shimmering crystals"),
        Unlockable("animated", "Animated", UnlockableType.PIECE_STYLE, 45, "Living pieces"),
        
        // Effects
        Unlockable("particles", "Particles", UnlockableType.EFFECT, 5, "Particle explosions"),
        Unlockable("trails", "Trails", UnlockableType.EFFECT, 10, "Motion trails"),
        Unlockable("explosions", "Explosions", UnlockableType.EFFECT, 18, "Explosive line clears"),
        Unlockable("lightning", "Lightning", UnlockableType.EFFECT, 28, "Electric effects"),
        Unlockable("shatter", "Shatter", UnlockableType.EFFECT, 38, "Shattering animations"),
        Unlockable("quantum", "Quantum", UnlockableType.EFFECT, 55, "Quantum particle effects")
    )
    
    fun addXP(amount: Int, source: String = "gameplay") {
        val currentState = _progressionState.value
        var newXP = currentState.currentXP + amount
        var newTotalXP = currentState.totalXP + amount
        var newLevel = currentState.level
        var leveledUp = false
        
        // Check for level up
        while (newXP >= currentState.xpToNextLevel && newLevel < MAX_LEVEL) {
            newXP -= currentState.xpToNextLevel
            newLevel++
            leveledUp = true
            
            Timber.d("Level up! New level: $newLevel")
            checkUnlocks(newLevel)
        }
        
        // Calculate XP needed for next level
        val xpToNext = calculateXPForLevel(newLevel)
        
        // Update rank
        val newRank = calculateRank(newLevel)
        
        _progressionState.value = currentState.copy(
            level = newLevel,
            currentXP = newXP,
            totalXP = newTotalXP,
            xpToNextLevel = xpToNext,
            rank = newRank
        )
        
        if (leveledUp) {
            onLevelUp(newLevel)
        }
        
        Timber.d("Added $amount XP from $source. Total: $newTotalXP")
    }
    
    private fun calculateXPForLevel(level: Int): Int {
        return (XP_PER_LEVEL_BASE * XP_PER_LEVEL_MULTIPLIER.toDouble().pow((level - 1).toDouble())).toInt()
    }
    
    private fun calculateRank(level: Int): Rank {
        return Rank.values()
            .sortedByDescending { it.minLevel }
            .first { level >= it.minLevel }
    }
    
    private fun checkUnlocks(level: Int) {
        val currentState = _progressionState.value
        val newUnlocks = unlockables.filter { 
            it.requiredLevel == level 
        }
        
        val themes = currentState.unlockedThemes.toMutableSet()
        val music = currentState.unlockedMusic.toMutableSet()
        val pieceStyles = currentState.unlockedPieceStyles.toMutableSet()
        val effects = currentState.unlockedEffects.toMutableSet()
        
        newUnlocks.forEach { unlock ->
            when (unlock.type) {
                UnlockableType.THEME -> themes.add(unlock.id)
                UnlockableType.MUSIC -> music.add(unlock.id)
                UnlockableType.PIECE_STYLE -> pieceStyles.add(unlock.id)
                UnlockableType.EFFECT -> effects.add(unlock.id)
            }
            
            Timber.d("Unlocked: ${unlock.name} (${unlock.type})")
        }
        
        _progressionState.value = currentState.copy(
            unlockedThemes = themes,
            unlockedMusic = music,
            unlockedPieceStyles = pieceStyles,
            unlockedEffects = effects
        )
    }
    
    private fun onLevelUp(newLevel: Int) {
        // Trigger level up effects and notifications
        when (newLevel) {
            10, 20, 30, 40, 50, 60, 70, 85, 100 -> {
                // Major milestone reached
                Timber.d("Major milestone reached: Level $newLevel")
            }
        }
    }
    
    fun updateStatistics(stats: PlayerStatistics) {
        _progressionState.value = _progressionState.value.copy(statistics = stats)
    }
    
    fun processGameResults(
        score: Int,
        lines: Int,
        level: Int,
        gameMode: String,
        tSpins: Int = 0,
        tetrises: Int = 0,
        perfectClears: Int = 0,
        maxCombo: Int = 0,
        duration: Long = 0
    ) {
        // Calculate XP earned
        var xpEarned = 0
        xpEarned += lines * XP_PER_LINE
        xpEarned += tetrises * XP_PER_TETRIS
        xpEarned += tSpins * XP_PER_T_SPIN
        xpEarned += perfectClears * XP_PER_PERFECT_CLEAR
        xpEarned += maxCombo * XP_PER_COMBO
        xpEarned += (level - 1) * XP_PER_LEVEL_UP
        
        // Bonus XP based on score
        xpEarned += score / 100
        
        addXP(xpEarned, "game_complete")
        
        // Update statistics
        val currentStats = _progressionState.value.statistics
        val newStats = currentStats.copy(
            totalGamesPlayed = currentStats.totalGamesPlayed + 1,
            totalLinesCleared = currentStats.totalLinesCleared + lines,
            totalScore = currentStats.totalScore + score,
            highestScore = maxOf(currentStats.highestScore, score),
            totalTSpins = currentStats.totalTSpins + tSpins,
            totalTetrises = currentStats.totalTetrises + tetrises,
            totalPerfectClears = currentStats.totalPerfectClears + perfectClears,
            longestCombo = maxOf(currentStats.longestCombo, maxCombo),
            totalPlayTime = currentStats.totalPlayTime + duration
        )
        updateStatistics(newStats)
    }
    
    fun getUnlockedItems(): Map<UnlockableType, List<Unlockable>> {
        val currentState = _progressionState.value
        return unlockables
            .filter { unlock ->
                when (unlock.type) {
                    UnlockableType.THEME -> unlock.id in currentState.unlockedThemes
                    UnlockableType.MUSIC -> unlock.id in currentState.unlockedMusic
                    UnlockableType.PIECE_STYLE -> unlock.id in currentState.unlockedPieceStyles
                    UnlockableType.EFFECT -> unlock.id in currentState.unlockedEffects
                }
            }
            .groupBy { it.type }
    }
    
    fun getAvailableUnlocks(currentLevel: Int): List<Unlockable> {
        return unlockables.filter { it.requiredLevel <= currentLevel }
    }
    
    fun getNextUnlocks(): List<Unlockable> {
        val currentLevel = _progressionState.value.level
        return unlockables
            .filter { it.requiredLevel > currentLevel }
            .sortedBy { it.requiredLevel }
            .take(3)
    }
    
    fun reset() {
        _progressionState.value = ProgressionState()
    }
}