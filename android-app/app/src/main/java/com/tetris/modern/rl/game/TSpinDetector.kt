package com.tetris.modern.rl.game

import com.tetris.modern.rl.game.models.Piece
import com.tetris.modern.rl.game.models.PieceType

/**
 * Detects T-spins and T-spin minis according to official Tetris guidelines
 */
class TSpinDetector {
    
    fun detectTSpin(piece: Piece, grid: Grid): Boolean {
        if (piece.type != PieceType.T) return false
        
        // Check the four corners of the T-piece's 3x3 bounding box
        val corners = getCornerPositions(piece)
        var filledCorners = 0
        
        for ((x, y) in corners) {
            if (isPositionFilled(x, y, grid)) {
                filledCorners++
            }
        }
        
        // T-spin requires at least 3 filled corners
        return filledCorners >= 3
    }
    
    fun detectTSpinMini(piece: Piece, grid: Grid): Boolean {
        if (piece.type != PieceType.T) return false
        
        // If it's a full T-spin, it's not a mini
        if (detectTSpin(piece, grid)) return false
        
        // Check for T-spin mini conditions
        val corners = getCornerPositions(piece)
        var filledCorners = 0
        var filledFrontCorners = 0
        
        val frontCorners = getFrontCorners(piece)
        
        for ((x, y) in corners) {
            if (isPositionFilled(x, y, grid)) {
                filledCorners++
                if (frontCorners.contains(Pair(x, y))) {
                    filledFrontCorners++
                }
            }
        }
        
        // T-spin mini: exactly 2 corners filled, including both front corners
        return filledCorners == 2 && filledFrontCorners == 2
    }
    
    private fun getCornerPositions(piece: Piece): List<Pair<Int, Int>> {
        // Get the four corners of the T-piece's 3x3 bounding box
        return when (piece.rotation) {
            0 -> listOf(
                Pair(piece.x, piece.y),
                Pair(piece.x + 2, piece.y),
                Pair(piece.x, piece.y + 2),
                Pair(piece.x + 2, piece.y + 2)
            )
            1 -> listOf(
                Pair(piece.x, piece.y),
                Pair(piece.x + 2, piece.y),
                Pair(piece.x, piece.y + 2),
                Pair(piece.x + 2, piece.y + 2)
            )
            2 -> listOf(
                Pair(piece.x, piece.y),
                Pair(piece.x + 2, piece.y),
                Pair(piece.x, piece.y + 2),
                Pair(piece.x + 2, piece.y + 2)
            )
            3 -> listOf(
                Pair(piece.x, piece.y),
                Pair(piece.x + 2, piece.y),
                Pair(piece.x, piece.y + 2),
                Pair(piece.x + 2, piece.y + 2)
            )
            else -> emptyList()
        }
    }
    
    private fun getFrontCorners(piece: Piece): List<Pair<Int, Int>> {
        // Front corners are the two corners facing the direction the T is pointing
        return when (piece.rotation) {
            0 -> listOf(Pair(piece.x, piece.y), Pair(piece.x + 2, piece.y))
            1 -> listOf(Pair(piece.x + 2, piece.y), Pair(piece.x + 2, piece.y + 2))
            2 -> listOf(Pair(piece.x, piece.y + 2), Pair(piece.x + 2, piece.y + 2))
            3 -> listOf(Pair(piece.x, piece.y), Pair(piece.x, piece.y + 2))
            else -> emptyList()
        }
    }
    
    private fun isPositionFilled(x: Int, y: Int, grid: Grid): Boolean {
        // Check if position is out of bounds (counts as filled)
        if (x < 0 || x >= TetrisEngine.GRID_WIDTH || y >= TetrisEngine.TOTAL_HEIGHT) {
            return true
        }
        
        // Check if position has a piece
        return grid.getCell(x, y) != null
    }
}