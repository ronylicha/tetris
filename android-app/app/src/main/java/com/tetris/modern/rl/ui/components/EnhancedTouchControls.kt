package com.tetris.modern.rl.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetris.modern.rl.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

enum class SwipeDirection {
    LEFT, RIGHT, UP, DOWN
}

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
    
    // Visual feedback states
    var touchPosition by remember { mutableStateOf(Offset.Zero) }
    var showTapFeedback by remember { mutableStateOf(false) }
    var showSwipeFeedback by remember { mutableStateOf<SwipeDirection?>(null) }
    var showHoldFeedback by remember { mutableStateOf(false) }
    
    // Animation values
    val tapAnimationProgress = remember { Animatable(0f) }
    val holdAnimationProgress = remember { Animatable(0f) }
    
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
                        onTap = { offset ->
                            // Simple tap = Rotate
                            viewModel.tetrisEngine.rotateClockwise()
                            
                            // Show tap feedback
                            touchPosition = offset
                            showTapFeedback = true
                            coroutineScope.launch {
                                tapAnimationProgress.animateTo(
                                    targetValue = 1f,
                                    animationSpec = tween(200)
                                )
                                delay(100)
                                tapAnimationProgress.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(100)
                                )
                                showTapFeedback = false
                            }
                        },
                        onLongPress = { offset ->
                            // Long press = Hold piece
                            viewModel.tetrisEngine.holdPiece()
                            
                            // Show hold feedback
                            touchPosition = offset
                            showHoldFeedback = true
                            coroutineScope.launch {
                                holdAnimationProgress.animateTo(
                                    targetValue = 1f,
                                    animationSpec = tween(300)
                                )
                                delay(200)
                                holdAnimationProgress.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(200)
                                )
                                showHoldFeedback = false
                            }
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
                                        showSwipeFeedback = SwipeDirection.RIGHT
                                    } else {
                                        viewModel.tetrisEngine.moveLeft()
                                        showSwipeFeedback = SwipeDirection.LEFT
                                    }
                                    startX = change.position.x
                                    lastMoveTime = currentTime
                                    touchPosition = change.position
                                    
                                    // Clear swipe feedback after short delay
                                    coroutineScope.launch {
                                        delay(150)
                                        showSwipeFeedback = null
                                    }
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
            // Visual feedback overlay
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                // Tap feedback
                if (showTapFeedback && touchPosition != Offset.Zero) {
                    val radius = 40.dp.toPx() * tapAnimationProgress.value
                    val alpha = (1f - tapAnimationProgress.value) * 0.5f
                    
                    drawCircle(
                        color = Color.Cyan.copy(alpha = alpha),
                        radius = radius,
                        center = touchPosition
                    )
                    
                    drawCircle(
                        color = Color.Cyan.copy(alpha = alpha * 0.5f),
                        radius = radius,
                        center = touchPosition,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
                
                // Hold feedback
                if (showHoldFeedback && touchPosition != Offset.Zero) {
                    val maxRadius = 60.dp.toPx()
                    val radius = maxRadius * holdAnimationProgress.value
                    val alpha = 0.3f * holdAnimationProgress.value
                    
                    // Draw pulsing circles
                    for (i in 0..2) {
                        val ringRadius = radius * (1f - i * 0.3f)
                        val ringAlpha = alpha * (1f - i * 0.3f)
                        
                        drawCircle(
                            color = Color.Magenta.copy(alpha = ringAlpha),
                            radius = ringRadius,
                            center = touchPosition,
                            style = Stroke(width = 3.dp.toPx())
                        )
                    }
                }
                
                // Swipe feedback
                showSwipeFeedback?.let { direction ->
                    val arrowSize = 30.dp.toPx()
                    val arrowOffset = when (direction) {
                        SwipeDirection.LEFT -> Offset(-arrowSize, 0f)
                        SwipeDirection.RIGHT -> Offset(arrowSize, 0f)
                        SwipeDirection.UP -> Offset(0f, -arrowSize)
                        SwipeDirection.DOWN -> Offset(0f, arrowSize)
                    }
                    
                    if (touchPosition != Offset.Zero) {
                        drawLine(
                            color = Color.Green.copy(alpha = 0.7f),
                            start = touchPosition,
                            end = touchPosition + arrowOffset,
                            strokeWidth = 4.dp.toPx()
                        )
                        
                        // Draw arrowhead
                        val arrowHeadSize = 10.dp.toPx()
                        val endPoint = touchPosition + arrowOffset
                        
                        when (direction) {
                            SwipeDirection.LEFT -> {
                                drawLine(
                                    color = Color.Green.copy(alpha = 0.7f),
                                    start = endPoint,
                                    end = endPoint + Offset(arrowHeadSize, -arrowHeadSize),
                                    strokeWidth = 3.dp.toPx()
                                )
                                drawLine(
                                    color = Color.Green.copy(alpha = 0.7f),
                                    start = endPoint,
                                    end = endPoint + Offset(arrowHeadSize, arrowHeadSize),
                                    strokeWidth = 3.dp.toPx()
                                )
                            }
                            SwipeDirection.RIGHT -> {
                                drawLine(
                                    color = Color.Green.copy(alpha = 0.7f),
                                    start = endPoint,
                                    end = endPoint + Offset(-arrowHeadSize, -arrowHeadSize),
                                    strokeWidth = 3.dp.toPx()
                                )
                                drawLine(
                                    color = Color.Green.copy(alpha = 0.7f),
                                    start = endPoint,
                                    end = endPoint + Offset(-arrowHeadSize, arrowHeadSize),
                                    strokeWidth = 3.dp.toPx()
                                )
                            }
                            SwipeDirection.UP -> {
                                drawLine(
                                    color = Color.Green.copy(alpha = 0.7f),
                                    start = endPoint,
                                    end = endPoint + Offset(-arrowHeadSize, arrowHeadSize),
                                    strokeWidth = 3.dp.toPx()
                                )
                                drawLine(
                                    color = Color.Green.copy(alpha = 0.7f),
                                    start = endPoint,
                                    end = endPoint + Offset(arrowHeadSize, arrowHeadSize),
                                    strokeWidth = 3.dp.toPx()
                                )
                            }
                            SwipeDirection.DOWN -> {
                                drawLine(
                                    color = Color.Green.copy(alpha = 0.7f),
                                    start = endPoint,
                                    end = endPoint + Offset(-arrowHeadSize, -arrowHeadSize),
                                    strokeWidth = 3.dp.toPx()
                                )
                                drawLine(
                                    color = Color.Green.copy(alpha = 0.7f),
                                    start = endPoint,
                                    end = endPoint + Offset(arrowHeadSize, -arrowHeadSize),
                                    strokeWidth = 3.dp.toPx()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}