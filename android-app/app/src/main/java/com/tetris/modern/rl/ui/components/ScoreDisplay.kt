package com.tetris.modern.rl.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetris.modern.rl.ui.theme.NeonColors
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ScoreDisplay(
    score: Int,
    lines: Int,
    level: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A2E)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Score
            ScoreItem(
                label = "SCORE",
                value = NumberFormat.getNumberInstance(Locale.US).format(score),
                color = NeonColors.Cyan
            )
            
            // Lines
            ScoreItem(
                label = "LINES",
                value = lines.toString(),
                color = NeonColors.Magenta
            )
            
            // Level
            ScoreItem(
                label = "LEVEL",
                value = level.toString(),
                color = NeonColors.Yellow
            )
        }
    }
}

@Composable
private fun ScoreItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}