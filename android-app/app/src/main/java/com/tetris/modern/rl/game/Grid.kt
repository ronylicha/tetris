package com.tetris.modern.rl.game

import com.tetris.modern.rl.game.models.Piece
import com.tetris.modern.rl.game.models.PieceType

class Grid(
    private val width: Int,
    private val height: Int
) {
    private val cells: Array<Array<PieceType?>> = Array(height) { Array(width) { null } }
    
    fun clear() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                cells[y][x] = null
            }
        }
    }
    
    fun getCell(x: Int, y: Int): PieceType? {
        return if (x in 0 until width && y in 0 until height) {
            cells[y][x]
        } else {
            null
        }
    }
    
    fun setCell(x: Int, y: Int, type: PieceType?) {
        if (x in 0 until width && y in 0 until height) {
            cells[y][x] = type
        }
    }
    
    fun placePiece(piece: Piece) {
        val shape = piece.getCurrentShape()
        
        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] != 0) {
                    val gridX = piece.x + col
                    val gridY = piece.y + row
                    
                    if (gridX in 0 until width && gridY in 0 until height) {
                        cells[gridY][gridX] = piece.type
                    }
                }
            }
        }
    }
    
    fun clearLines(): List<Int> {
        val linesToClear = mutableListOf<Int>()
        
        // Find completed lines
        for (y in 0 until height) {
            if (isLineFull(y)) {
                linesToClear.add(y)
            }
        }
        
        // If there are lines to clear, remove them all at once
        if (linesToClear.isNotEmpty()) {
            // Create a new grid without the cleared lines
            val newCells = Array(height) { arrayOfNulls<PieceType>(width) }
            var newY = height - 1
            
            // Copy non-cleared lines from bottom to top
            for (y in height - 1 downTo 0) {
                if (!linesToClear.contains(y)) {
                    for (x in 0 until width) {
                        newCells[newY][x] = cells[y][x]
                    }
                    newY--
                }
            }
            
            // Update the grid
            for (y in 0 until height) {
                for (x in 0 until width) {
                    cells[y][x] = newCells[y][x]
                }
            }
        }
        
        return linesToClear
    }
    
    private fun isLineFull(y: Int): Boolean {
        for (x in 0 until width) {
            if (cells[y][x] == null) {
                return false
            }
        }
        return true
    }
    
    private fun clearLine(y: Int) {
        for (x in 0 until width) {
            cells[y][x] = null
        }
    }
    
    private fun moveDownAbove(clearedLine: Int) {
        for (y in clearedLine - 1 downTo 0) {
            for (x in 0 until width) {
                cells[y + 1][x] = cells[y][x]
            }
        }
        
        // Clear top line
        for (x in 0 until width) {
            cells[0][x] = null
        }
    }
    
    fun isPerfectClear(): Boolean {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (cells[y][x] != null) {
                    return false
                }
            }
        }
        return true
    }
    
    fun getHeight(x: Int): Int {
        for (y in 0 until height) {
            if (cells[y][x] != null) {
                return height - y
            }
        }
        return 0
    }
    
    fun getAggregateHeight(): Int {
        var totalHeight = 0
        for (x in 0 until width) {
            totalHeight += getHeight(x)
        }
        return totalHeight
    }
    
    fun getHoles(): Int {
        var holes = 0
        
        for (x in 0 until width) {
            var blockFound = false
            
            for (y in 0 until height) {
                if (cells[y][x] != null) {
                    blockFound = true
                } else if (blockFound) {
                    holes++
                }
            }
        }
        
        return holes
    }
    
    fun getBumpiness(): Int {
        var bumpiness = 0
        var lastHeight = getHeight(0)
        
        for (x in 1 until width) {
            val currentHeight = getHeight(x)
            bumpiness += kotlin.math.abs(currentHeight - lastHeight)
            lastHeight = currentHeight
        }
        
        return bumpiness
    }
    
    fun getWells(): Int {
        var wells = 0
        
        for (x in 0 until width) {
            val currentHeight = getHeight(x)
            val leftHeight = if (x > 0) getHeight(x - 1) else Int.MAX_VALUE
            val rightHeight = if (x < width - 1) getHeight(x + 1) else Int.MAX_VALUE
            
            if (currentHeight < leftHeight && currentHeight < rightHeight) {
                val wellDepth = kotlin.math.min(leftHeight, rightHeight) - currentHeight
                wells += wellDepth * wellDepth // Square the depth for emphasis
            }
        }
        
        return wells
    }
    
    fun getVisibleGrid(): Array<IntArray> {
        // Return only the visible portion of the grid (excluding hidden rows)
        val visibleHeight = height - TetrisEngine.HIDDEN_ROWS
        val visibleGrid = Array(visibleHeight) { IntArray(width) }
        
        for (y in 0 until visibleHeight) {
            for (x in 0 until width) {
                val actualY = y + TetrisEngine.HIDDEN_ROWS
                visibleGrid[y][x] = cells[actualY][x]?.ordinal ?: -1
            }
        }
        
        return visibleGrid
    }
    
    fun getFullGrid(): Array<Array<PieceType?>> {
        return cells.map { it.clone() }.toTypedArray()
    }
    
    fun copy(): Grid {
        val newGrid = Grid(width, height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                newGrid.cells[y][x] = cells[y][x]
            }
        }
        return newGrid
    }
}