package com.tetris.modern.rl.effects

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

data class Particle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    var size: Float,
    var color: Color,
    var alpha: Float = 1f,
    var rotation: Float = 0f,
    var rotationSpeed: Float = Random.nextFloat() * 360f - 180f,
    var lifetime: Float = 1f
)

data class LineExplosion(
    val lineY: Int,
    val gridWidth: Int,
    val particles: MutableList<Particle>,
    var progress: Float = 0f,
    val startTime: Long = System.currentTimeMillis()
)

@Composable
fun LineExplosionEffect(
    explosions: List<LineExplosion>,
    cellSize: Float,
    offsetY: Float,
    modifier: Modifier = Modifier,
    onExplosionComplete: (LineExplosion) -> Unit = {}
) {
    var refreshTrigger by remember { mutableStateOf(0) }
    
    // Update explosions with proper lifecycle management
    LaunchedEffect(Unit) {
        while (true) {
            if (explosions.isNotEmpty()) {
                val completedExplosions = mutableListOf<LineExplosion>()
                explosions.forEach { explosion ->
                    updateExplosion(explosion)
                    // Mark explosions as completed after 1 second max
                    if (explosion.progress >= 1f || 
                        (System.currentTimeMillis() - explosion.startTime) > 1000) {
                        completedExplosions.add(explosion)
                    }
                }
                // Remove completed explosions
                completedExplosions.forEach { explosion ->
                    onExplosionComplete(explosion)
                }
                refreshTrigger++
            }
            delay(16) // 60 FPS
        }
    }
    
    Canvas(modifier = modifier) {
        explosions.forEach { explosion ->
            drawExplosion(explosion, cellSize, offsetY)
        }
        // Force recomposition
        refreshTrigger.toString()
    }
}

private fun updateExplosion(explosion: LineExplosion) {
    val elapsed = (System.currentTimeMillis() - explosion.startTime) / 1000f
    explosion.progress = minOf(elapsed / 0.5f, 1f) // 500ms duration
    
    explosion.particles.forEach { particle ->
        // Update particle physics
        particle.x += particle.vx * 0.016f
        particle.y += particle.vy * 0.016f
        particle.vy += 300f * 0.016f // Gravity
        
        // Update visual properties
        particle.alpha = 1f - explosion.progress
        particle.size *= 0.98f
        particle.rotation += particle.rotationSpeed * 0.016f
        particle.lifetime -= 0.016f
    }
    
    // Remove dead particles
    explosion.particles.removeAll { it.lifetime <= 0 || it.alpha <= 0 || it.size <= 0.1f }
}

private fun DrawScope.drawExplosion(
    explosion: LineExplosion,
    cellSize: Float,
    offsetY: Float
) {
    explosion.particles.forEach { particle ->
        val screenX = particle.x * cellSize
        val screenY = offsetY + particle.y * cellSize
        
        drawCircle(
            color = particle.color.copy(alpha = particle.alpha),
            radius = particle.size,
            center = Offset(screenX, screenY)
        )
        
        // Draw a glowing effect
        if (particle.size > 2f) {
            drawCircle(
                color = particle.color.copy(alpha = particle.alpha * 0.3f),
                radius = particle.size * 2f,
                center = Offset(screenX, screenY)
            )
        }
    }
}

fun createLineExplosion(
    lineY: Int,
    gridWidth: Int,
    colors: List<Color>
): LineExplosion {
    val particles = mutableListOf<Particle>()
    
    // Create particles for each cell in the line - reduced from 8 to 4 particles per cell
    for (x in 0 until gridWidth) {
        // Create fewer particles to reduce memory usage
        for (i in 0 until 4) {
            val baseX = x + 0.5f
            val baseY = lineY.toFloat() + 0.5f
            
            // Random velocity with bias towards upward and outward movement
            val angle = Random.nextFloat() * PI.toFloat() * 2
            val speed = Random.nextFloat() * 150f + 50f // Reduced speed range
            
            particles.add(
                Particle(
                    x = baseX + Random.nextFloat() * 0.5f - 0.25f,
                    y = baseY + Random.nextFloat() * 0.5f - 0.25f,
                    vx = cos(angle) * speed,
                    vy = sin(angle) * speed - 100f, // Reduced upward bias
                    size = Random.nextFloat() * 3f + 2f, // Slightly smaller particles
                    color = colors.random(),
                    alpha = 1f,
                    rotation = Random.nextFloat() * 360f,
                    rotationSpeed = Random.nextFloat() * 360f - 180f, // Reduced rotation speed
                    lifetime = Random.nextFloat() * 0.2f + 0.3f // Shorter lifetime
                )
            )
        }
    }
    
    return LineExplosion(
        lineY = lineY,
        gridWidth = gridWidth,
        particles = particles
    )
}

// Neon colors for explosions
val explosionColors = listOf(
    Color(0xFF00FFFF), // Cyan
    Color(0xFFFF00FF), // Magenta
    Color(0xFF00FF00), // Green
    Color(0xFFFFFF00), // Yellow
    Color(0xFFFF0080), // Pink
    Color(0xFF80FF00), // Lime
    Color(0xFF0080FF), // Blue
    Color(0xFFFF8000)  // Orange
)