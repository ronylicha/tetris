package com.tetris.modern.rl.game.models

import androidx.compose.ui.graphics.Color

enum class PieceType {
    I, O, T, S, Z, J, L
}

data class Piece(
    val type: PieceType,
    var x: Int,
    var y: Int,
    var rotation: Int = 0
) {
    companion object {
        // Tetromino shapes for each piece type and rotation
        private val SHAPES = mapOf(
            PieceType.I to arrayOf(
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(1, 1, 1, 1),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 0, 1, 0),
                    intArrayOf(0, 0, 1, 0),
                    intArrayOf(0, 0, 1, 0),
                    intArrayOf(0, 0, 1, 0)
                ),
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(1, 1, 1, 1),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 1, 0, 0)
                )
            ),
            PieceType.O to arrayOf(
                arrayOf(
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                )
            ),
            PieceType.T to arrayOf(
                arrayOf(
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(1, 1, 1, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(1, 1, 1, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(1, 1, 0, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                )
            ),
            PieceType.S to arrayOf(
                arrayOf(
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(1, 1, 0, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 0, 1, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(1, 1, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(1, 0, 0, 0),
                    intArrayOf(1, 1, 0, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                )
            ),
            PieceType.Z to arrayOf(
                arrayOf(
                    intArrayOf(1, 1, 0, 0),
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 0, 1, 0),
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(1, 1, 0, 0),
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(1, 1, 0, 0),
                    intArrayOf(1, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                )
            ),
            PieceType.J to arrayOf(
                arrayOf(
                    intArrayOf(1, 0, 0, 0),
                    intArrayOf(1, 1, 1, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(1, 1, 1, 0),
                    intArrayOf(0, 0, 1, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(1, 1, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                )
            ),
            PieceType.L to arrayOf(
                arrayOf(
                    intArrayOf(0, 0, 1, 0),
                    intArrayOf(1, 1, 1, 0),
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 1, 1, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(0, 0, 0, 0),
                    intArrayOf(1, 1, 1, 0),
                    intArrayOf(1, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                ),
                arrayOf(
                    intArrayOf(1, 1, 0, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 1, 0, 0),
                    intArrayOf(0, 0, 0, 0)
                )
            )
        )
        
        // Piece colors (neon theme)
        val COLORS = mapOf(
            PieceType.I to Color(0xFF00FFFF), // Cyan
            PieceType.O to Color(0xFFFFFF00), // Yellow
            PieceType.T to Color(0xFFFF00FF), // Magenta
            PieceType.S to Color(0xFF00FF00), // Green
            PieceType.Z to Color(0xFFFF0000), // Red
            PieceType.J to Color(0xFF0000FF), // Blue
            PieceType.L to Color(0xFFFF8800)  // Orange
        )
    }
    
    fun getCurrentShape(): Array<IntArray> {
        return SHAPES[type]?.get(rotation) ?: arrayOf()
    }
    
    fun getColor(): Color {
        return COLORS[type] ?: Color.White
    }
    
    fun copy(): Piece {
        return Piece(type, x, y, rotation)
    }
    
    fun getWidth(): Int {
        val shape = getCurrentShape()
        var maxX = 0
        for (row in shape) {
            for (col in row.indices) {
                if (row[col] != 0) {
                    maxX = maxOf(maxX, col + 1)
                }
            }
        }
        return maxX
    }
    
    fun getHeight(): Int {
        val shape = getCurrentShape()
        var maxY = 0
        for (row in shape.indices) {
            for (col in shape[row]) {
                if (col != 0) {
                    maxY = maxOf(maxY, row + 1)
                }
            }
        }
        return maxY
    }
}