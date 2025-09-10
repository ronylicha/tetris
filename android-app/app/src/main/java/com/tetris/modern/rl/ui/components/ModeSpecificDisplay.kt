package com.tetris.modern.rl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetris.modern.rl.ui.theme.*

@Composable
fun ModeSpecificDisplay(
    gameMode: String,
    modeInfo: Map<String, String>,
    objective: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A2E).copy(alpha = 0.95f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Mode name with gradient (smaller)
            Text(
                text = gameMode.uppercase(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = androidx.compose.ui.text.TextStyle(
                    brush = Brush.horizontalGradient(
                        colors = listOf(NeonColors.Cyan, NeonColors.Magenta)
                    )
                )
            )
            
            // Objective (more compact)
            if (objective.isNotEmpty()) {
                Text(
                    text = objective,
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1
                )
                
                Divider(
                    color = Color.White.copy(alpha = 0.2f),
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
            
            // Mode-specific information (compact)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                modeInfo.forEach { (label, value) ->
                    ModeInfoRow(label = label, value = value)
                }
            }
        }
    }
}

@Composable
private fun ModeInfoRow(
    label: String,
    value: String
) {
    // Special layout for timer to make it more readable
    val isTimer = label.contains("Time", ignoreCase = true)
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isTimer) Arrangement.Center else Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isTimer) {
            // Timer gets special centered treatment (compact)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = value,
                    fontSize = 16.sp,
                    color = NeonColors.Cyan,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        } else {
            // Other stats use standard layout (compact)
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.6f),
                fontWeight = FontWeight.Normal,
                maxLines = 1
            )
            
            Spacer(modifier = Modifier.width(4.dp))
            
            // Highlight important values with color
            val valueColor = when {
                label.contains("Lines", ignoreCase = true) -> NeonColors.Yellow
                label.contains("Level", ignoreCase = true) -> NeonColors.Magenta
                label.contains("Score", ignoreCase = true) -> NeonColors.Green
                else -> Color.White
            }
            
            Text(
                text = value,
                fontSize = 13.sp,
                color = valueColor,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}