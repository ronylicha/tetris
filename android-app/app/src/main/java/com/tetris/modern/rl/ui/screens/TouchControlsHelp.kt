package com.tetris.modern.rl.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.tetris.modern.rl.R

@Composable
fun TouchControlsHelpDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1A2E)
            ),
            border = BorderStroke(2.dp, Color(0xFF00FFFF))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.touch_controls),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF00FFFF),
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Control instructions
                ControlHelpRow(emoji = "👆", action = stringResource(R.string.tap), description = stringResource(R.string.rotate_piece))
                ControlHelpRow(emoji = "👈👉", action = stringResource(R.string.swipe_left_right), description = stringResource(R.string.move_piece))
                ControlHelpRow(emoji = "👇", action = stringResource(R.string.swipe_down), description = stringResource(R.string.soft_drop))
                ControlHelpRow(emoji = "⬇️⬇️", action = stringResource(R.string.double_tap_down), description = stringResource(R.string.hard_drop))
                ControlHelpRow(emoji = "🤏", action = stringResource(R.string.long_press), description = stringResource(R.string.hold_piece))
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FF00)
                    )
                ) {
                    Text(
                        stringResource(R.string.got_it),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun ControlHelpRow(
    emoji: String,
    action: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White.copy(alpha = 0.05f),
                shape = MaterialTheme.shapes.small
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = emoji,
            fontSize = 24.sp,
            modifier = Modifier.width(40.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = action,
                color = Color(0xFF00FFFF),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }
    }
}