package com.tetris.modern.rl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tetris.modern.rl.ui.theme.BackgroundDark
import com.tetris.modern.rl.ui.theme.NeonColors
import com.tetris.modern.rl.ui.viewmodels.MainViewModel
import java.text.NumberFormat
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

@Composable
fun StatsScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val statistics by viewModel.overallStatistics.collectAsState(initial = null)
    val progressionState by viewModel.playerProgression.progressionState.collectAsState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Back button and title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { 
                    viewModel.audioManager.playSound(com.tetris.modern.rl.audio.AudioManager.SOUND_MENU_BACK)
                    navController.navigateUp() 
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    "Statistics",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            // Overall Stats Card
            StatsCard(
                title = "Overall Performance",
                icon = Icons.Default.Stars,
                color = NeonColors.Yellow
            ) {
                val totalGames = statistics?.totalGamesPlayed ?: 0
                val totalScore = statistics?.totalScore ?: 0L
                val totalLines = statistics?.totalLinesCleared ?: 0
                val totalTime = statistics?.totalTimePlayed ?: 0L
                
                StatItem("Total Games", totalGames.toString())
                StatItem("Total Score", NumberFormat.getNumberInstance(Locale.US).format(totalScore))
                StatItem("Total Lines", totalLines.toString())
                StatItem("Play Time", formatPlayTime(totalTime))
                StatItem("Level", progressionState.level.toString())
                StatItem("Rank", progressionState.rank.displayName)
            }
            
            // Best Records Card
            StatsCard(
                title = "Best Records",
                icon = Icons.Default.EmojiEvents,
                color = NeonColors.Cyan
            ) {
                val highScore = statistics?.highScore ?: 0
                val bestCombo = statistics?.bestCombo ?: 0
                val fastestSprint = statistics?.fastestSprint40 ?: 0L
                val highestLevel = statistics?.highestLevel ?: 0
                
                StatItem("High Score", NumberFormat.getNumberInstance(Locale.US).format(highScore))
                StatItem("Best Combo", "${bestCombo}x")
                StatItem("Highest Level", highestLevel.toString())
                if (fastestSprint > 0) {
                    StatItem("Sprint 40L", formatTime(fastestSprint))
                }
            }
            
            // Gameplay Stats Card
            StatsCard(
                title = "Gameplay Statistics",
                icon = Icons.Default.Analytics,
                color = NeonColors.Magenta
            ) {
                val totalPieces = statistics?.totalPiecesPlaced ?: 0
                val totalTSpins = statistics?.totalTSpins ?: 0
                val totalTetrises = statistics?.totalTetrisClears ?: 0
                val perfectClears = statistics?.totalPerfectClears ?: 0
                val avgScore = statistics?.averageScore ?: 0f
                val avgLines = statistics?.averageLinesPerGame ?: 0f
                
                StatItem("Pieces Placed", totalPieces.toString())
                StatItem("T-Spins", totalTSpins.toString())
                StatItem("Tetrises", totalTetrises.toString())
                StatItem("Perfect Clears", perfectClears.toString())
                StatItem("Avg Score/Game", NumberFormat.getNumberInstance(Locale.US).format(avgScore.toInt()))
                StatItem("Avg Lines/Game", String.format("%.1f", avgLines))
            }
            
            // Achievements Progress Card
            StatsCard(
                title = "Achievements",
                icon = Icons.Default.WorkspacePremium,
                color = NeonColors.Green
            ) {
                val achievementProgressMap = viewModel.achievementSystem.achievementProgress.collectAsState().value
                val unlockedCount = achievementProgressMap.values.count { it.isUnlocked }
                val totalAchievements = achievementProgressMap.size
                val trophyCount = viewModel.achievementSystem.getUnlockedTrophiesCount()
                val totalTrophies = viewModel.achievementSystem.getTotalTrophiesCount()
                
                StatItem("Achievements", "$unlockedCount / $totalAchievements")
                StatItem("Trophies", "$trophyCount / $totalTrophies")
                StatItem("Completion", String.format("%.1f%%", (unlockedCount.toFloat() / totalAchievements.coerceAtLeast(1)) * 100))
            }
        }
    }
}

@Composable
fun StatsCard(
    title: String,
    icon: ImageVector,
    color: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A2E).copy(alpha = 0.9f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            content()
        }
    }
}

@Composable
fun StatItem(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

fun formatTime(millis: Long): String {
    val duration = millis.milliseconds
    val minutes = duration.inWholeMinutes
    val seconds = duration.minus(minutes.milliseconds).inWholeSeconds
    val ms = duration.minus(minutes.milliseconds).minus(seconds.milliseconds).inWholeMilliseconds / 10
    return String.format("%02d:%02d.%02d", minutes, seconds, ms)
}

fun formatPlayTime(millis: Long): String {
    val duration = millis.milliseconds
    val hours = duration.inWholeHours
    val minutes = duration.minus(hours.milliseconds).inWholeMinutes
    return if (hours > 0) {
        "${hours}h ${minutes}m"
    } else {
        "${minutes}m"
    }
}