package com.tetris.modern.rl.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tetris.modern.rl.auth.SyncStrategy
import com.tetris.modern.rl.sync.SyncManager
import com.tetris.modern.rl.ui.theme.NeonColors
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SyncConflictDialog(
    localData: SyncManager.SyncData,
    remoteData: SyncManager.SyncData,
    onStrategySelected: (SyncStrategy) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedStrategy by remember { mutableStateOf<SyncStrategy?>(null) }
    var showDetails by remember { mutableStateOf(false) }
    
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E2340)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Icon(
                    Icons.Default.Sync,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = NeonColors.cyan
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Data Sync Conflict",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonColors.cyan
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Your local data differs from the cloud. Choose how to resolve:",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Data comparison
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Local data card
                    DataCard(
                        modifier = Modifier.weight(1f),
                        title = "Local Data",
                        data = localData,
                        isSelected = selectedStrategy == SyncStrategy.KEEP_LOCAL,
                        color = NeonColors.purple,
                        onClick = { selectedStrategy = SyncStrategy.KEEP_LOCAL }
                    )
                    
                    // Remote data card
                    DataCard(
                        modifier = Modifier.weight(1f),
                        title = "Cloud Data",
                        data = remoteData,
                        isSelected = selectedStrategy == SyncStrategy.KEEP_REMOTE,
                        color = NeonColors.cyan,
                        onClick = { selectedStrategy = SyncStrategy.KEEP_REMOTE }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Merge option
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedStrategy == SyncStrategy.MERGE_BOTH)
                            NeonColors.green.copy(alpha = 0.2f)
                        else Color.White.copy(alpha = 0.05f)
                    ),
                    onClick = { selectedStrategy = SyncStrategy.MERGE_BOTH }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Merge,
                            contentDescription = null,
                            tint = NeonColors.green
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Merge Both",
                                fontWeight = FontWeight.Bold,
                                color = NeonColors.green
                            )
                            Text(
                                text = "Keep best scores from both",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                        
                        RadioButton(
                            selected = selectedStrategy == SyncStrategy.MERGE_BOTH,
                            onClick = { selectedStrategy = SyncStrategy.MERGE_BOTH },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = NeonColors.green
                            )
                        )
                    }
                }
                
                // Show details button
                TextButton(
                    onClick = { showDetails = !showDetails },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        if (showDetails) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (showDetails) "Hide Details" else "Show Details")
                }
                
                // Details section
                AnimatedVisibility(
                    visible = showDetails,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        DetailedComparison(localData, remoteData)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = {
                            selectedStrategy?.let { onStrategySelected(it) }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = selectedStrategy != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (selectedStrategy) {
                                SyncStrategy.KEEP_LOCAL -> NeonColors.purple
                                SyncStrategy.KEEP_REMOTE -> NeonColors.cyan
                                SyncStrategy.MERGE_BOTH -> NeonColors.green
                                null -> Color.Gray
                            }
                        )
                    ) {
                        Text("Apply")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DataCard(
    modifier: Modifier = Modifier,
    title: String,
    data: SyncManager.SyncData,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                color.copy(alpha = 0.2f) 
            else Color.White.copy(alpha = 0.05f)
        ),
        border = if (isSelected) BorderStroke(2.dp, color) else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    if (title == "Local Data") Icons.Default.PhoneAndroid else Icons.Default.Cloud,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            DataStat("Games", data.gamesPlayed.toString())
            DataStat("Best", formatScore(data.highestScore))
            DataStat("Lines", data.totalLines.toString())
            DataStat("Updated", formatTime(data.lastSyncTime))
            
            Spacer(modifier = Modifier.height(8.dp))
            
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = color
                )
            )
        }
    }
}

@Composable
private fun DataStat(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun DetailedComparison(
    localData: SyncManager.SyncData,
    remoteData: SyncManager.SyncData
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "Detailed Comparison",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            ComparisonRow("Total Score", localData.totalScore, remoteData.totalScore)
            ComparisonRow("Games Played", localData.gamesPlayed.toLong(), remoteData.gamesPlayed.toLong())
            ComparisonRow("Highest Score", localData.highestScore.toLong(), remoteData.highestScore.toLong())
            ComparisonRow("Total Lines", localData.totalLines.toLong(), remoteData.totalLines.toLong())
            ComparisonRow("Unique Scores", localData.scores.size.toLong(), remoteData.scores.size.toLong())
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Merge Strategy Info",
                fontWeight = FontWeight.Bold,
                color = NeonColors.green,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            
            Text(
                text = "• Merging will keep the best scores from both sources\n" +
                      "• Duplicate scores will be removed\n" +
                      "• Statistics will be combined\n" +
                      "• No data will be lost",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun ComparisonRow(
    label: String,
    localValue: Long,
    remoteValue: Long
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.weight(1f)
        )
        
        Text(
            text = formatNumber(localValue),
            fontSize = 11.sp,
            color = NeonColors.purple,
            modifier = Modifier.width(80.dp),
            textAlign = TextAlign.End
        )
        
        Icon(
            when {
                localValue > remoteValue -> Icons.Default.KeyboardArrowUp
                localValue < remoteValue -> Icons.Default.KeyboardArrowDown
                else -> Icons.Default.DragHandle
            },
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .padding(horizontal = 4.dp),
            tint = when {
                localValue > remoteValue -> NeonColors.green
                localValue < remoteValue -> Color.Red
                else -> Color.Gray
            }
        )
        
        Text(
            text = formatNumber(remoteValue),
            fontSize = 11.sp,
            color = NeonColors.cyan,
            modifier = Modifier.width(80.dp),
            textAlign = TextAlign.End
        )
    }
}

private fun formatScore(score: Int): String {
    return when {
        score >= 1_000_000 -> "${score / 1_000_000}M"
        score >= 1_000 -> "${score / 1_000}K"
        else -> score.toString()
    }
}

private fun formatNumber(value: Long): String {
    return NumberFormat.getInstance().format(value)
}

private fun formatTime(timestamp: Long): String {
    if (timestamp == 0L) return "Never"
    
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 60_000 -> "Just now"
        diff < 3_600_000 -> "${diff / 60_000}m ago"
        diff < 86_400_000 -> "${diff / 3_600_000}h ago"
        diff < 604_800_000 -> "${diff / 86_400_000}d ago"
        else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(timestamp))
    }
}