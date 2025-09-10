package com.tetris.modern.rl.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.tetris.modern.rl.ui.controls.TouchControlsManager
import com.tetris.modern.rl.ui.theme.NeonColors
import com.tetris.modern.rl.ui.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameControls(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val touchManager = remember { TouchControlsManager(viewModel) }
    var showVirtualButtons by remember { mutableStateOf(true) }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        // Touch gesture area (invisible, covers entire control area)
        touchManager.GameTouchControls(
            modifier = Modifier.fillMaxSize(),
            onGesture = { gesture ->
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                touchManager.handleSwipeGesture(gesture)
            }
        )
        
        // Virtual button controls overlay
        if (showVirtualButtons) {
            VirtualGameControls(
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
        
        // Toggle button for virtual controls
        IconButton(
            onClick = { showVirtualButtons = !showVirtualButtons },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(32.dp)
                .alpha(0.5f)
        ) {
            Icon(
                imageVector = if (showVirtualButtons) 
                    Icons.Default.TouchApp else Icons.Default.Gamepad,
                contentDescription = "Toggle Controls",
                tint = Color.White
            )
        }
    }
}

@Composable
fun VirtualGameControls(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()
    
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        // Left side - Movement controls
        Box(
            modifier = Modifier.weight(1f)
        ) {
            DirectionalPad(
                onLeft = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.moveLeft()
                },
                onRight = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.moveRight()
                },
                onDown = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.softDrop()
                },
                onUp = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.hardDrop()
                }
            )
        }
        
        // Right side - Action buttons
        Box(
            modifier = Modifier.weight(1f)
        ) {
            ActionButtons(
                onRotateCW = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.rotateClockwise()
                },
                onRotateCCW = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.rotateCounterClockwise()
                },
                onHold = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.holdPiece()
                },
                onPause = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.pauseGame()
                }
            )
        }
    }
}

@Composable
fun DirectionalPad(
    onLeft: () -> Unit,
    onRight: () -> Unit,
    onDown: () -> Unit,
    onUp: () -> Unit
) {
    val buttonSize = 60.dp
    val centerButtonSize = 40.dp
    
    Box(
        modifier = Modifier.size(180.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background circle
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xFF1A1A2E).copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(NeonColors.Cyan, NeonColors.Magenta)
                    ),
                    shape = CircleShape
                )
        )
        
        // Direction buttons
        DPadButton(
            icon = Icons.Default.KeyboardArrowLeft,
            onClick = onLeft,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
                .size(buttonSize)
        )
        
        DPadButton(
            icon = Icons.Default.KeyboardArrowRight,
            onClick = onRight,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
                .size(buttonSize)
        )
        
        DPadButton(
            icon = Icons.Default.KeyboardArrowDown,
            onClick = onDown,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
                .size(buttonSize)
        )
        
        DPadButton(
            icon = Icons.Default.KeyboardDoubleArrowUp,
            onClick = onUp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp)
                .size(buttonSize)
        )
        
        // Center indicator
        Box(
            modifier = Modifier
                .size(centerButtonSize)
                .background(
                    Color(0xFF2A2A3E).copy(alpha = 0.5f),
                    shape = CircleShape
                )
        )
    }
}

@Composable
fun DPadButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    
    Box(
        modifier = modifier
            .scale(scale)
            .background(
                color = if (isPressed) 
                    NeonColors.Cyan.copy(alpha = 0.3f)
                else 
                    Color(0xFF2A2A3E).copy(alpha = 0.6f),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = if (isPressed) NeonColors.Cyan else Color.White.copy(alpha = 0.3f),
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isPressed) NeonColors.Cyan else Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun ActionButtons(
    onRotateCW: () -> Unit,
    onRotateCCW: () -> Unit,
    onHold: () -> Unit,
    onPause: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End
    ) {
        // Top row - Pause and Hold
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActionButton(
                icon = Icons.Default.Pause,
                onClick = onPause,
                color = Color.Gray,
                size = 40.dp
            )
            
            ActionButton(
                icon = Icons.Default.SwapVert,
                onClick = onHold,
                color = NeonColors.Yellow,
                size = 50.dp
            )
        }
        
        // Bottom row - Rotation buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActionButton(
                icon = Icons.Default.RotateLeft,
                onClick = onRotateCCW,
                color = NeonColors.Magenta,
                size = 60.dp
            )
            
            ActionButton(
                icon = Icons.Default.RotateRight,
                onClick = onRotateCW,
                color = NeonColors.Cyan,
                size = 70.dp
            )
        }
    }
}

@Composable
fun ActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    color: Color,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    
    val glow by animateFloatAsState(
        targetValue = if (isPressed) 1f else 0f,
        animationSpec = tween(100)
    )
    
    Box(
        modifier = modifier
            .size(size)
            .scale(scale)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.3f + glow * 0.3f),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        color.copy(alpha = 0.8f + glow * 0.2f),
                        color.copy(alpha = 0.4f)
                    )
                ),
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(size * 0.5f)
        )
    }
}