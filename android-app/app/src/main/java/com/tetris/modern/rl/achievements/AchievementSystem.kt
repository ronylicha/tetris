package com.tetris.modern.rl.achievements

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementSystem @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    // Live notifications list for UI display
    val liveNotifications = mutableStateListOf<AchievementNotification>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    
    data class AchievementNotification(
        val achievement: Achievement,
        val timestamp: Long = System.currentTimeMillis(),
        val id: String = java.util.UUID.randomUUID().toString()
    )
    
    enum class AchievementCategory {
        BEGINNER, LINES, SCORE, COMBO, SPECIAL, MODES, DAILY, SECRET
    }
    
    enum class Trophy(val displayName: String, val color: String, val xpReward: Int) {
        BRONZE("Bronze", "#CD7F32", 500),
        SILVER("Silver", "#C0C0C0", 1000),
        GOLD("Gold", "#FFD700", 2000),
        PLATINUM("Platinum", "#E5E4E2", 5000)
    }
    
    data class Achievement(
        val id: String,
        val name: String,
        val description: String,
        val category: AchievementCategory,
        val trophy: Trophy? = null,
        val xpReward: Int = 250,
        val isSecret: Boolean = false,
        val progressMax: Int = 1,
        val unlockCondition: (GameStats) -> Boolean
    )
    
    data class AchievementProgress(
        val achievementId: String,
        val currentProgress: Int = 0,
        val isUnlocked: Boolean = false,
        val unlockedDate: Date? = null
    )
    
    data class GameStats(
        val score: Int = 0,
        val lines: Int = 0,
        val level: Int = 1,
        val tSpins: Int = 0,
        val tetrises: Int = 0,
        val perfectClears: Int = 0,
        val maxCombo: Int = 0,
        val gameMode: String = "",
        val duration: Long = 0,
        val totalGamesPlayed: Int = 0,
        val totalLinesCleared: Int = 0,
        val highestScore: Int = 0
    )
    
    private val achievements = listOf(
        // BEGINNER Achievements
        Achievement(
            "first_line", "First Line", "Clear your first line",
            AchievementCategory.BEGINNER, xpReward = 100
        ) { it.totalLinesCleared >= 1 },
        
        Achievement(
            "first_tetris", "Tetris!", "Clear 4 lines at once",
            AchievementCategory.BEGINNER, xpReward = 200
        ) { it.tetrises >= 1 },
        
        Achievement(
            "level_10", "Double Digits", "Reach level 10",
            AchievementCategory.BEGINNER, xpReward = 300
        ) { it.level >= 10 },
        
        Achievement(
            "first_tspin", "Spin Master", "Perform your first T-Spin",
            AchievementCategory.BEGINNER, xpReward = 400
        ) { it.tSpins >= 1 },
        
        // LINES Achievements
        Achievement(
            "lines_100", "Century", "Clear 100 lines total",
            AchievementCategory.LINES, progressMax = 100
        ) { it.totalLinesCleared >= 100 },
        
        Achievement(
            "lines_500", "Half Grand", "Clear 500 lines total",
            AchievementCategory.LINES, Trophy.BRONZE, progressMax = 500
        ) { it.totalLinesCleared >= 500 },
        
        Achievement(
            "lines_1000", "Millennium", "Clear 1000 lines total",
            AchievementCategory.LINES, Trophy.SILVER, progressMax = 1000
        ) { it.totalLinesCleared >= 1000 },
        
        Achievement(
            "lines_5000", "Line Master", "Clear 5000 lines total",
            AchievementCategory.LINES, Trophy.GOLD, progressMax = 5000
        ) { it.totalLinesCleared >= 5000 },
        
        // SCORE Achievements
        Achievement(
            "score_10k", "Five Figures", "Score 10,000 points",
            AchievementCategory.SCORE
        ) { it.highestScore >= 10000 },
        
        Achievement(
            "score_50k", "High Scorer", "Score 50,000 points",
            AchievementCategory.SCORE, Trophy.BRONZE
        ) { it.highestScore >= 50000 },
        
        Achievement(
            "score_100k", "Six Figures", "Score 100,000 points",
            AchievementCategory.SCORE, Trophy.SILVER
        ) { it.highestScore >= 100000 },
        
        Achievement(
            "score_500k", "Half Million", "Score 500,000 points",
            AchievementCategory.SCORE, Trophy.GOLD
        ) { it.highestScore >= 500000 },
        
        Achievement(
            "score_1m", "Millionaire", "Score 1,000,000 points",
            AchievementCategory.SCORE, Trophy.PLATINUM
        ) { it.highestScore >= 1000000 },
        
        // COMBO Achievements
        Achievement(
            "combo_5", "Combo Starter", "Achieve a 5x combo",
            AchievementCategory.COMBO
        ) { it.maxCombo >= 5 },
        
        Achievement(
            "combo_10", "Combo Expert", "Achieve a 10x combo",
            AchievementCategory.COMBO, Trophy.BRONZE
        ) { it.maxCombo >= 10 },
        
        Achievement(
            "combo_20", "Combo Master", "Achieve a 20x combo",
            AchievementCategory.COMBO, Trophy.SILVER
        ) { it.maxCombo >= 20 },
        
        // SPECIAL Achievements
        Achievement(
            "perfect_clear", "Perfectionist", "Achieve a perfect clear",
            AchievementCategory.SPECIAL, Trophy.BRONZE
        ) { it.perfectClears >= 1 },
        
        Achievement(
            "tspin_triple", "Triple Spin", "Clear 3 lines with a T-Spin",
            AchievementCategory.SPECIAL, Trophy.SILVER
        ) { it.tSpins >= 1 && it.lines >= 3 },
        
        Achievement(
            "back_to_back", "Back to Back", "Perform consecutive Tetrises",
            AchievementCategory.SPECIAL
        ) { it.tetrises >= 2 },
        
        Achievement(
            "all_clear_10", "Clean Slate", "Get 10 perfect clears total",
            AchievementCategory.SPECIAL, Trophy.GOLD, progressMax = 10
        ) { it.perfectClears >= 10 },
        
        // MODES Achievements
        Achievement(
            "sprint_complete", "Sprinter", "Complete Sprint mode",
            AchievementCategory.MODES
        ) { it.gameMode == "Sprint" && it.lines >= 40 },
        
        Achievement(
            "marathon_complete", "Marathoner", "Complete Marathon mode",
            AchievementCategory.MODES, Trophy.BRONZE
        ) { it.gameMode == "Marathon" && it.lines >= 150 },
        
        Achievement(
            "zen_1hour", "Zen Master", "Play Zen mode for 1 hour",
            AchievementCategory.MODES
        ) { it.gameMode == "Zen" && it.duration >= 3600000 },
        
        Achievement(
            "battle_victory", "Victor", "Win a Battle mode match",
            AchievementCategory.MODES
        ) { it.gameMode == "Battle" },
        
        Achievement(
            "puzzle_50", "Puzzle Solver", "Complete 50 puzzles",
            AchievementCategory.MODES, Trophy.SILVER, progressMax = 50
        ) { it.gameMode == "Puzzle" },
        
        // DAILY Achievements
        Achievement(
            "daily_streak_3", "Consistent", "3-day daily challenge streak",
            AchievementCategory.DAILY, progressMax = 3
        ) { false }, // Checked separately
        
        Achievement(
            "daily_streak_7", "Dedicated", "7-day daily challenge streak",
            AchievementCategory.DAILY, Trophy.BRONZE, progressMax = 7
        ) { false },
        
        Achievement(
            "daily_streak_30", "Committed", "30-day daily challenge streak",
            AchievementCategory.DAILY, Trophy.SILVER, progressMax = 30
        ) { false },
        
        Achievement(
            "daily_streak_100", "Centurion", "100-day daily challenge streak",
            AchievementCategory.DAILY, Trophy.PLATINUM, progressMax = 100
        ) { false },
        
        // SECRET Achievements
        Achievement(
            "secret_matrix", "The One", "Enter the Matrix",
            AchievementCategory.SECRET, isSecret = true, xpReward = 1000
        ) { it.score == 101010 },
        
        Achievement(
            "secret_impossible", "Impossible", "Achieve the impossible",
            AchievementCategory.SECRET, Trophy.PLATINUM, isSecret = true, xpReward = 5000
        ) { it.level >= 50 && it.perfectClears >= 5 },
        
        Achievement(
            "secret_speed", "Speed Demon", "Clear 40 lines in under 1 minute",
            AchievementCategory.SECRET, Trophy.GOLD, isSecret = true
        ) { it.gameMode == "Sprint" && it.lines >= 40 && it.duration < 60000 }
    )
    
    private val _achievementProgress = MutableStateFlow<Map<String, AchievementProgress>>(emptyMap())
    val achievementProgress: StateFlow<Map<String, AchievementProgress>> = _achievementProgress.asStateFlow()
    
    private val _unlockedAchievements = MutableStateFlow<List<String>>(emptyList())
    val unlockedAchievements: StateFlow<List<String>> = _unlockedAchievements.asStateFlow()
    
    init {
        initializeAchievements()
    }
    
    private fun initializeAchievements() {
        val progress = mutableMapOf<String, AchievementProgress>()
        achievements.forEach { achievement ->
            progress[achievement.id] = AchievementProgress(achievement.id)
        }
        _achievementProgress.value = progress
    }
    
    fun checkAchievements(stats: GameStats): List<Achievement> {
        Timber.d("checkAchievements called with stats - Score: ${stats.score}, Lines: ${stats.lines}, Level: ${stats.level}")
        Timber.d("Stats - Tetrises: ${stats.tetrises}, T-Spins: ${stats.tSpins}, MaxCombo: ${stats.maxCombo}")
        Timber.d("Stats - TotalLines: ${stats.totalLinesCleared}, TotalGames: ${stats.totalGamesPlayed}")
        
        val newlyUnlocked = mutableListOf<Achievement>()
        val currentProgress = _achievementProgress.value.toMutableMap()
        
        achievements.forEach { achievement ->
            val progress = currentProgress[achievement.id] ?: return@forEach
            
            if (!progress.isUnlocked && achievement.unlockCondition(stats)) {
                // Achievement unlocked!
                currentProgress[achievement.id] = progress.copy(
                    isUnlocked = true,
                    unlockedDate = Date(),
                    currentProgress = achievement.progressMax
                )
                newlyUnlocked.add(achievement)
                
                Timber.d("Achievement unlocked: ${achievement.name} (${achievement.id})")
                
                // Show live notification
                showLiveNotification(achievement)
            } else if (!progress.isUnlocked && achievement.progressMax > 1) {
                // Update progress for progressive achievements
                val newProgress = calculateProgress(achievement, stats)
                if (newProgress > progress.currentProgress) {
                    currentProgress[achievement.id] = progress.copy(
                        currentProgress = newProgress.coerceAtMost(achievement.progressMax)
                    )
                    
                    if (newProgress >= achievement.progressMax) {
                        currentProgress[achievement.id] = progress.copy(
                            isUnlocked = true,
                            unlockedDate = Date(),
                            currentProgress = achievement.progressMax
                        )
                        newlyUnlocked.add(achievement)
                    }
                }
            }
        }
        
        _achievementProgress.value = currentProgress
        _unlockedAchievements.value = currentProgress
            .filter { it.value.isUnlocked }
            .map { it.key }
        
        Timber.d("checkAchievements completed - Checked ${achievements.size} achievements, Unlocked ${newlyUnlocked.size} new")
        return newlyUnlocked
    }
    
    fun getTrophyCount(): Map<Trophy, Int> {
        val unlockedTrophies = mutableMapOf<Trophy, Int>()
        Trophy.values().forEach { trophy ->
            unlockedTrophies[trophy] = 0
        }
        
        achievements.forEach { achievement ->
            val progress = _achievementProgress.value[achievement.id]
            if (progress?.isUnlocked == true && achievement.trophy != null) {
                unlockedTrophies[achievement.trophy] = unlockedTrophies[achievement.trophy]!! + 1
            }
        }
        
        return unlockedTrophies
    }
    
    fun getTotalTrophiesCount(): Int {
        return achievements.count { it.trophy != null }
    }
    
    fun getUnlockedTrophiesCount(): Int {
        return achievements.count { achievement ->
            val progress = _achievementProgress.value[achievement.id]
            progress?.isUnlocked == true && achievement.trophy != null
        }
    }
    
    private fun calculateProgress(achievement: Achievement, stats: GameStats): Int {
        return when (achievement.id) {
            "lines_100" -> stats.totalLinesCleared.coerceAtMost(100)
            "lines_500" -> stats.totalLinesCleared.coerceAtMost(500)
            "lines_1000" -> stats.totalLinesCleared.coerceAtMost(1000)
            "lines_5000" -> stats.totalLinesCleared.coerceAtMost(5000)
            "all_clear_10" -> stats.perfectClears.coerceAtMost(10)
            "puzzle_50" -> 0 // Would need separate puzzle tracking
            else -> 0
        }
    }
    
    fun updateDailyStreak(streakDays: Int) {
        val currentProgress = _achievementProgress.value.toMutableMap()
        
        val streakAchievements = mapOf(
            "daily_streak_3" to 3,
            "daily_streak_7" to 7,
            "daily_streak_30" to 30,
            "daily_streak_100" to 100
        )
        
        streakAchievements.forEach { (achievementId, requiredDays) ->
            val progress = currentProgress[achievementId] ?: return@forEach
            
            if (!progress.isUnlocked && streakDays >= requiredDays) {
                currentProgress[achievementId] = progress.copy(
                    isUnlocked = true,
                    unlockedDate = Date(),
                    currentProgress = requiredDays
                )
            } else if (!progress.isUnlocked) {
                currentProgress[achievementId] = progress.copy(
                    currentProgress = streakDays.coerceAtMost(requiredDays)
                )
            }
        }
        
        _achievementProgress.value = currentProgress
    }
    
    fun getAchievementsByCategory(): Map<AchievementCategory, List<Pair<Achievement, AchievementProgress>>> {
        val progress = _achievementProgress.value
        return achievements
            .filter { !it.isSecret || progress[it.id]?.isUnlocked == true }
            .map { achievement ->
                achievement to (progress[achievement.id] ?: AchievementProgress(achievement.id))
            }
            .groupBy { it.first.category }
    }
    
    fun getTotalAchievementPoints(): Int {
        val progress = _achievementProgress.value
        return achievements.sumOf { achievement ->
            if (progress[achievement.id]?.isUnlocked == true) {
                achievement.xpReward
            } else {
                0
            }
        }
    }
    
    fun getUnlockedTrophies(): Map<Trophy, Int> {
        val progress = _achievementProgress.value
        return achievements
            .filter { progress[it.id]?.isUnlocked == true && it.trophy != null }
            .groupBy { it.trophy!! }
            .mapValues { it.value.size }
    }
    
    private fun showLiveNotification(achievement: Achievement) {
        // Add to live notifications list
        val notification = AchievementNotification(achievement)
        liveNotifications.add(notification)
        
        // Play achievement sound (using system notification sound)
        try {
            val notification = android.app.Notification.Builder(context)
                .setDefaults(android.app.Notification.DEFAULT_SOUND)
                .build()
            val uri = android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = android.media.RingtoneManager.getRingtone(context, uri)
            ringtone?.play()
        } catch (e: Exception) {
            Timber.e(e, "Failed to play achievement sound")
        }
        
        // Show toast notification as fallback
        Toast.makeText(
            context,
            "🏆 ${achievement.name} Unlocked!",
            Toast.LENGTH_LONG
        ).show()
        
        // Auto-remove notification after 5 seconds
        coroutineScope.launch {
            delay(5000)
            liveNotifications.removeIf { it.id == notification.id }
        }
    }
    
    fun getCompletionPercentage(): Float {
        val totalAchievements = achievements.filter { !it.isSecret }.size
        val unlockedCount = _unlockedAchievements.value
            .count { id -> achievements.find { it.id == id }?.isSecret != true }
        
        return if (totalAchievements > 0) {
            (unlockedCount.toFloat() / totalAchievements) * 100
        } else {
            0f
        }
    }
    
    fun reset() {
        initializeAchievements()
        _unlockedAchievements.value = emptyList()
    }
}