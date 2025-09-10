package com.tetris.modern.rl.ui.controls

import android.view.MotionEvent
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.tetris.modern.rl.ui.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * Advanced touch control system for Tetris gameplay
 * Supports swipes, taps, long press, and multi-touch gestures
 */
class TouchControlsManager(
    private val viewModel: MainViewModel
) {
    companion object {
        // Gesture thresholds
        const val SWIPE_THRESHOLD = 50f // pixels
        const val SWIPE_VELOCITY_THRESHOLD = 300f // pixels per second
        const val TAP_TIMEOUT = 200L // milliseconds
        const val LONG_PRESS_TIMEOUT = 800L // milliseconds for hold
        const val DOUBLE_TAP_TIMEOUT = 300L // milliseconds
        
        // Soft drop parameters
        const val SOFT_DROP_INITIAL_DELAY = 100L
        const val SOFT_DROP_REPEAT_DELAY = 50L
        const val SOFT_DROP_ACCELERATION = 0.9f
        
        // Gesture zones (as percentage of screen)
        const val LEFT_ZONE = 0.3f
        const val RIGHT_ZONE = 0.7f
        const val BOTTOM_ZONE = 0.8f
    }
    
    private var lastTapTime = 0L
    private var tapCount = 0
    private var isLongPressing = false
    private var isSoftDropping = false
    private var softDropSpeed = SOFT_DROP_INITIAL_DELAY
    
    private val gestureHistory = mutableListOf<GestureData>()
    
    data class GestureData(
        val type: GestureType,
        val startPoint: Offset,
        val endPoint: Offset,
        val timestamp: Long,
        val velocity: Float = 0f
    )
    
    enum class GestureType {
        TAP, DOUBLE_TAP, LONG_PRESS,
        SWIPE_LEFT, SWIPE_RIGHT, SWIPE_DOWN, SWIPE_UP,
        DRAG_LEFT, DRAG_RIGHT, DRAG_DOWN,
        PINCH, ROTATE
    }
    
    @Composable
    fun GameTouchControls(
        modifier: Modifier = Modifier,
        onGesture: (GestureType) -> Unit = {}
    ) {
        val density = LocalDensity.current
        var touchStartPoint by remember { mutableStateOf(Offset.Zero) }
        var touchStartTime by remember { mutableStateOf(0L) }
        var isDragging by remember { mutableStateOf(false) }
        var lastDragPosition by remember { mutableStateOf(Offset.Zero) }
        
        Box(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    coroutineScope {
                        awaitEachGesture {
                            val down = awaitFirstDown()
                            touchStartPoint = down.position
                            touchStartTime = System.currentTimeMillis()
                            isDragging = false
                            lastDragPosition = down.position
                            
                            // Start long press detection
                            launch {
                                delay(LONG_PRESS_TIMEOUT)
                                if (!isDragging) {
                                    handleLongPress(touchStartPoint)
                                    onGesture(GestureType.LONG_PRESS)
                                }
                            }
                            
                            // Start soft drop detection
                            launch {
                                val screenHeight = size.height.toFloat()
                                if (touchStartPoint.y > screenHeight * BOTTOM_ZONE) {
                                    startSoftDrop()
                                }
                            }
                            
                            do {
                                val event = awaitPointerEvent()
                                val currentPosition = event.changes.firstOrNull()?.position ?: Offset.Zero
                                
                                when (event.type) {
                                    PointerEventType.Move -> {
                                        val distance = (currentPosition - touchStartPoint).getDistance()
                                        
                                        if (distance > SWIPE_THRESHOLD) {
                                            isDragging = true
                                            handleDrag(
                                                lastDragPosition,
                                                currentPosition,
                                                onGesture
                                            )
                                            lastDragPosition = currentPosition
                                        }
                                    }
                                    
                                    PointerEventType.Release -> {
                                        val endTime = System.currentTimeMillis()
                                        val duration = endTime - touchStartTime
                                        val endPosition = currentPosition
                                        
                                        stopSoftDrop()
                                        
                                        if (!isDragging && !isLongPressing) {
                                            when {
                                                duration < TAP_TIMEOUT -> {
                                                    handleTap(endPosition, onGesture)
                                                }
                                                else -> {
                                                    // Check for swipe
                                                    val swipeGesture = detectSwipe(
                                                        touchStartPoint,
                                                        endPosition,
                                                        duration
                                                    )
                                                    swipeGesture?.let { onGesture(it) }
                                                }
                                            }
                                        }
                                    }
                                }
                            } while (event.changes.any { it.pressed })
                        }
                    }
                }
                .pointerInput(Unit) {
                    // Multi-touch gestures (pinch, rotate)
                    detectTransformGestures { centroid, pan, zoom, rotation ->
                        when {
                            zoom != 1f -> {
                                // Pinch gesture - could be used for zoom in puzzle mode
                                onGesture(GestureType.PINCH)
                            }
                            rotation != 0f -> {
                                // Rotation gesture - rotate piece
                                if (rotation > 15f) {
                                    viewModel.rotateClockwise()
                                } else if (rotation < -15f) {
                                    viewModel.rotateCounterClockwise()
                                }
                            }
                        }
                    }
                }
        )
    }
    
    private fun handleTap(position: Offset, onGesture: (GestureType) -> Unit) {
        val currentTime = System.currentTimeMillis()
        
        if (currentTime - lastTapTime < DOUBLE_TAP_TIMEOUT) {
            // Double tap detected
            tapCount++
            if (tapCount >= 2) {
                onGesture(GestureType.DOUBLE_TAP)
                viewModel.rotateCounterClockwise()
                tapCount = 0
            }
        } else {
            // Single tap
            tapCount = 1
            onGesture(GestureType.TAP)
            viewModel.rotateClockwise()
        }
        
        lastTapTime = currentTime
    }
    
    private fun handleLongPress(position: Offset) {
        if (!isLongPressing) {
            isLongPressing = true
            viewModel.holdPiece()
            // Vibrate for feedback
            // TODO: Add vibration feedback
        }
    }
    
    private fun handleDrag(
        startPosition: Offset,
        endPosition: Offset,
        onGesture: (GestureType) -> Unit
    ) {
        val deltaX = endPosition.x - startPosition.x
        val deltaY = endPosition.y - startPosition.y
        
        when {
            abs(deltaX) > abs(deltaY) -> {
                // Horizontal drag
                if (deltaX > SWIPE_THRESHOLD) {
                    onGesture(GestureType.DRAG_RIGHT)
                    viewModel.moveRight()
                } else if (deltaX < -SWIPE_THRESHOLD) {
                    onGesture(GestureType.DRAG_LEFT)
                    viewModel.moveLeft()
                }
            }
            deltaY > SWIPE_THRESHOLD -> {
                // Downward drag - continuous soft drop
                onGesture(GestureType.DRAG_DOWN)
                if (!isSoftDropping) {
                    CoroutineScope(Dispatchers.Main).launch {
                        startSoftDrop()
                    }
                }
            }
        }
    }
    
    private fun detectSwipe(
        startPoint: Offset,
        endPoint: Offset,
        duration: Long
    ): GestureType? {
        val deltaX = endPoint.x - startPoint.x
        val deltaY = endPoint.y - startPoint.y
        val distance = sqrt(deltaX * deltaX + deltaY * deltaY)
        val velocity = distance / duration * 1000 // pixels per second
        
        if (velocity < SWIPE_VELOCITY_THRESHOLD) {
            return null
        }
        
        val angle = atan2(deltaY, deltaX)
        
        return when {
            abs(deltaX) > abs(deltaY) -> {
                if (deltaX > 0) GestureType.SWIPE_RIGHT else GestureType.SWIPE_LEFT
            }
            deltaY > 0 -> GestureType.SWIPE_DOWN
            else -> GestureType.SWIPE_UP
        }
    }
    
    private suspend fun startSoftDrop() {
        if (isSoftDropping) return
        
        isSoftDropping = true
        softDropSpeed = SOFT_DROP_INITIAL_DELAY
        
        while (isSoftDropping) {
            viewModel.softDrop()
            delay(softDropSpeed)
            
            // Accelerate soft drop over time
            softDropSpeed = (softDropSpeed * SOFT_DROP_ACCELERATION).toLong()
                .coerceAtLeast(SOFT_DROP_REPEAT_DELAY)
        }
    }
    
    private fun stopSoftDrop() {
        isSoftDropping = false
        softDropSpeed = SOFT_DROP_INITIAL_DELAY
    }
    
    fun handleSwipeGesture(gesture: GestureType) {
        when (gesture) {
            GestureType.SWIPE_LEFT -> viewModel.moveLeft()
            GestureType.SWIPE_RIGHT -> viewModel.moveRight()
            GestureType.SWIPE_DOWN -> viewModel.softDrop()
            GestureType.SWIPE_UP -> viewModel.hardDrop()
            else -> {}
        }
    }
    
    fun reset() {
        lastTapTime = 0L
        tapCount = 0
        isLongPressing = false
        isSoftDropping = false
        softDropSpeed = SOFT_DROP_INITIAL_DELAY
        gestureHistory.clear()
    }
    
    // Gesture customization settings
    data class TouchSettings(
        val swipeSensitivity: Float = 1.0f,
        val tapToRotate: Boolean = true,
        val doubleTapAction: DoubleTapAction = DoubleTapAction.ROTATE_CCW,
        val longPressAction: LongPressAction = LongPressAction.HOLD,
        val vibrationEnabled: Boolean = true,
        val dragToMove: Boolean = true
    )
    
    enum class DoubleTapAction {
        ROTATE_CCW, ROTATE_180, HARD_DROP, HOLD
    }
    
    enum class LongPressAction {
        HOLD, HARD_DROP, PAUSE, NONE
    }
}

// Extension function to calculate distance between two points
fun Offset.getDistance(): Float {
    return sqrt(x * x + y * y)
}