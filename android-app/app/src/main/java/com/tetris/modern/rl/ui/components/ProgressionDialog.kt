package com.tetris.modern.rl.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tetris.modern.rl.progression.PlayerProgression
import com.tetris.modern.rl.ui.theme.*

@Composable
fun ProgressionDialog(
    progression: PlayerProgression,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Surface)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Player Progression",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
                
                // Level & XP
                Card(
                    colors = CardDefaults.cardColors(containerColor = BackgroundDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Level", color = TextSecondary)
                            Text(
                                progression.progressionState.value.level.toString(),
                                color = AccentCyan,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        LinearProgressIndicator(
                            progress = progression.progressionState.value.currentXP.toFloat() / progression.progressionState.value.xpToNextLevel,
                            modifier = Modifier.fillMaxWidth(),
                            color = Primary,
                            trackColor = Primary.copy(alpha = 0.2f)
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("XP", color = TextSecondary, fontSize = 12.sp)
                            Text(
                                "${progression.progressionState.value.currentXP} / ${progression.progressionState.value.xpToNextLevel}",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Rank", color = TextSecondary)
                            Text(
                                progression.progressionState.value.rank.displayName,
                                color = AccentMagenta,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                
                // Stats
                Card(
                    colors = CardDefaults.cardColors(containerColor = BackgroundDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Statistics", fontWeight = FontWeight.Bold, color = TextPrimary)
                        StatRow("Total Games", progression.progressionState.value.statistics.totalGamesPlayed.toString())
                        StatRow("Total Score", progression.progressionState.value.statistics.totalScore.toString())
                        StatRow("Total Lines", progression.progressionState.value.statistics.totalLinesCleared.toString())
                        StatRow("Best Score", progression.progressionState.value.statistics.highestScore.toString())
                        StatRow("Play Time", formatPlayTime(progression.progressionState.value.statistics.totalPlayTime))
                    }
                }
                
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextSecondary, fontSize = 14.sp)
        Text(value, color = TextPrimary, fontSize = 14.sp)
    }
}

fun formatPlayTime(millis: Long): String {
    val totalSeconds = millis / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    
    return when {
        hours > 0 -> "${hours}h ${minutes}m"
        minutes > 0 -> "${minutes}m"
        else -> "0m"
    }
}