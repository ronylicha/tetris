package com.tetris.modern.rl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetris.modern.rl.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun EnhancedTouchControls(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val gameState by viewModel.gameState.collectAsState()
    
    // State management
    var lastMoveTime by remember { mutableStateOf(0L) }
    var softDropJob by remember { mutableStateOf<Job?>(null) }
    var holdJob by remember { mutableStateOf<Job?>(null) }
    
    // Configuration
    val moveThreshold = with(density) { 30.dp.toPx() }
    val swipeUpThreshold = with(density) { 80.dp.toPx() }
    val swipeDownThreshold = with(density) { 20.dp.toPx() }
    val holdDuration = 900L // ms
    val moveDelay = 150L // ms between moves
    
    if (gameState.isPlaying && !gameState.isPaused && !gameState.isGameOver) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            // Simple tap = Rotate
                            viewModel.tetrisEngine.rotateClockwise()
                        },
                        onLongPress = {
                            // Long press = Hold piece
                            viewModel.tetrisEngine.holdPiece()
                        }
                    )
                }
                .pointerInput(Unit) {
                    var startY = 0f
                    var startX = 0f
                    var accumulatedX = 0f
                    var accumulatedY = 0f
                    
                    detectDragGestures(
                        onDragStart = { offset ->
                            startY = offset.y
                            startX = offset.x
                            accumulatedX = 0f
                            accumulatedY = 0f
                            softDropJob?.cancel()
                        },
                        onDragEnd = {
                            softDropJob?.cancel()
                            
                            // Check for swipe up (hard drop) at the end
                            if (accumulatedY < -swipeUpThreshold && abs(accumulatedX) < swipeUpThreshold / 2) {
                                viewModel.tetrisEngine.hardDrop()
                            }
                        },
                        onDrag = { change, _ ->
                            val deltaX = change.position.x - startX
                            val deltaY = change.position.y - startY
                            accumulatedX += change.position.x - change.previousPosition.x
                            accumulatedY += change.position.y - change.previousPosition.y
                            
                            val currentTime = System.currentTimeMillis()
                            
                            // Horizontal movement (left/right)
                            if (abs(deltaX) > moveThreshold) {
                                if (currentTime - lastMoveTime > moveDelay) {
                                    if (deltaX > 0) {
                                        viewModel.tetrisEngine.moveRight()
                                    } else {
                                        viewModel.tetrisEngine.moveLeft()
                                    }
                                    startX = change.position.x
                                    lastMoveTime = currentTime
                                }
                            }
                            
                            // Vertical movement down (soft drop)
                            if (deltaY > swipeDownThreshold && abs(deltaX) < moveThreshold) {
                                if (softDropJob?.isActive != true) {
                                    softDropJob = coroutineScope.launch {
                                        while (true) {
                                            viewModel.tetrisEngine.softDrop()
                                            delay(50) // Fast soft drop
                                        }
                                    }
                                }
                            } else {
                                softDropJob?.cancel()
                            }
                        }
                    )
                }
        ) {
            // Show controls hint at the beginning
            LaunchedEffect(Unit) {
                delay(5000) // Hide after 5 seconds
            }
        }
    }
}