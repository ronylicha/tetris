package com.tetris.modern.rl.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.tetris.modern.rl.R
import kotlinx.coroutines.delay

@Composable
fun TutorialScreen(
    onComplete: () -> Unit,
    onSkip: () -> Unit
) {
    var currentStep by remember { mutableStateOf(0) }
    val animatedScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.95f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .scale(animatedScale)
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1A2E)
            ),
            border = BorderStroke(3.dp, Color(0xFF00FFFF))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                when (currentStep) {
                    0 -> WelcomeStep()
                    1 -> MovementStep()
                    2 -> RotationStep()
                    3 -> DropStep()
                    4 -> HoldStep()
                    5 -> TipsStep()
                }
                
                // Progress indicators
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(6) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .padding(horizontal = 2.dp)
                                .background(
                                    if (index <= currentStep) Color(0xFF00FF00) 
                                    else Color.White.copy(alpha = 0.3f),
                                    shape = MaterialTheme.shapes.small
                                )
                        )
                    }
                }
                
                // Navigation buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = onSkip,
                        enabled = currentStep < 5
                    ) {
                        Text(
                            stringResource(R.string.skip),
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                    
                    Button(
                        onClick = {
                            if (currentStep < 5) {
                                currentStep++
                            } else {
                                onComplete()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00FF00)
                        )
                    ) {
                        Text(
                            if (currentStep < 5) stringResource(R.string.next_step) else stringResource(R.string.start_playing),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeStep() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "🎮",
            fontSize = 64.sp
        )
        Text(
            text = stringResource(R.string.welcome_to_tetris),
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF00FFFF),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.lets_learn_controls),
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MovementStep() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "👈 👉",
            fontSize = 48.sp
        )
        Text(
            text = stringResource(R.string.move_pieces),
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF00FFFF),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.move_pieces_desc),
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RotationStep() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "👆",
            fontSize = 48.sp
        )
        Text(
            text = stringResource(R.string.rotate),
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF00FFFF),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.rotate_desc),
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DropStep() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "👇",
            fontSize = 48.sp
        )
        Text(
            text = stringResource(R.string.drop_pieces),
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF00FFFF),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.drop_pieces_desc),
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun HoldStep() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "🤏",
            fontSize = 48.sp
        )
        Text(
            text = stringResource(R.string.hold_piece_title),
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF00FFFF),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.hold_piece_desc),
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TipsStep() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "💡",
            fontSize = 48.sp
        )
        Text(
            text = stringResource(R.string.pro_tips),
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF00FFFF),
            fontWeight = FontWeight.Bold
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.tip_clear_lines),
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Text(
                text = stringResource(R.string.tip_use_hold),
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Text(
                text = stringResource(R.string.tip_watch_preview),
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Text(
                text = stringResource(R.string.tip_achievements),
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }
    }
}