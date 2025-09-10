package com.tetris.modern.rl.ui.components

import androidx.compose.foundation.background
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
import com.tetris.modern.rl.ui.theme.*

@Composable
fun GameStats(
    score: Int,
    level: Int,
    lines: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatItem(
                label = "SCORE",
                value = score.toString(),
                color = AccentCyan
            )
            StatItem(
                label = "LEVEL",
                value = level.toString(),
                color = AccentMagenta
            )
            StatItem(
                label = "LINES",
                value = lines.toString(),
                color = AccentYellow
            )
        }
    }
}

@Composable
fun StatItem(
    label: String,
    value: String,
    color: Color
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 24.sp,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}