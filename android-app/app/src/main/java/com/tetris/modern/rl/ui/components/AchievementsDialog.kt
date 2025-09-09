package com.tetris.modern.rl.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tetris.modern.rl.achievements.AchievementSystem
import com.tetris.modern.rl.ui.theme.*

@Composable
fun AchievementsDialog(
    achievementSystem: AchievementSystem,
    onDismiss: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf(AchievementSystem.AchievementCategory.BEGINNER) }
    val achievements by achievementSystem.achievementProgress.collectAsState()
    val categorizedAchievements = achievementSystem.getAchievementsByCategory()
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Achievements",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Progress bar
                val completion = achievementSystem.getCompletionPercentage()
                LinearProgressIndicator(
                    progress = completion / 100f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    color = Primary,
                    trackColor = Primary.copy(alpha = 0.2f)
                )
                
                Text(
                    text = "${completion.toInt()}% Complete",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Category tabs
                ScrollableTabRow(
                    selectedTabIndex = AchievementSystem.AchievementCategory.values().indexOf(selectedCategory),
                    containerColor = BackgroundDark,
                    contentColor = TextPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    AchievementSystem.AchievementCategory.values().forEach { category ->
                        Tab(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            text = { 
                                Text(
                                    category.name.lowercase().capitalize(), 
                                    fontSize = 12.sp
                                ) 
                            }
                        )
                    }
                }
                
                // Achievements list
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    categorizedAchievements[selectedCategory]?.let { categoryAchievements ->
                        items(categoryAchievements) { (achievement, progress) ->
                            AchievementCard(achievement, progress)
                        }
                    }
                }
                
                // Trophy summary
                val trophies = achievementSystem.getUnlockedTrophies()
                if (trophies.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        trophies.forEach { (trophy, count) ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "🏆",
                                    fontSize = 24.sp,
                                    color = Color(android.graphics.Color.parseColor(trophy.color))
                                )
                                Text(
                                    text = "$count",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
                
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun AchievementCard(
    achievement: AchievementSystem.Achievement,
    progress: AchievementSystem.AchievementProgress
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (progress.isUnlocked) {
                Primary.copy(alpha = 0.1f)
            } else {
                BackgroundDark
            }
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                imageVector = getAchievementIcon(achievement),
                contentDescription = null,
                tint = if (progress.isUnlocked) AccentCyan else TextSecondary,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = achievement.name,
                    fontWeight = FontWeight.Medium,
                    color = if (progress.isUnlocked) TextPrimary else TextSecondary
                )
                Text(
                    text = achievement.description,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
                if (achievement.progressMax > 1) {
                    LinearProgressIndicator(
                        progress = progress.currentProgress.toFloat() / achievement.progressMax,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        color = Primary,
                        trackColor = Primary.copy(alpha = 0.2f)
                    )
                    Text(
                        text = "${progress.currentProgress} / ${achievement.progressMax}",
                        fontSize = 10.sp,
                        color = TextSecondary
                    )
                }
            }
            
            // XP reward and trophy
            Column(horizontalAlignment = Alignment.End) {
                if (progress.isUnlocked) {
                    Text(
                        text = "+${achievement.xpReward} XP",
                        color = AccentYellow,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                achievement.trophy?.let { trophy ->
                    Text(
                        text = "🏆",
                        color = Color(android.graphics.Color.parseColor(trophy.color)),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

fun getAchievementIcon(achievement: AchievementSystem.Achievement): ImageVector = when {
    achievement.name.contains("Tetris") -> Icons.Default.Star
    achievement.name.contains("Line") -> Icons.Default.ViewAgenda
    achievement.name.contains("Score") -> Icons.Default.EmojiEvents
    achievement.name.contains("Combo") -> Icons.Default.LocalFireDepartment
    achievement.name.contains("Speed") -> Icons.Default.Speed
    achievement.name.contains("Level") -> Icons.Default.TrendingUp
    achievement.trophy != null -> Icons.Default.WorkspacePremium
    else -> Icons.Default.CheckCircle
}