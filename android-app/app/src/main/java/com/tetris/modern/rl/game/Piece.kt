package com.tetris.modern.rl.game

data class Piece(
    val type: Char,
    var x: Int = 0,
    var y: Int = 0,
    var rotation: Int = 0
) {
    companion object {
        val PIECES = mapOf(
            'I' to arrayOf(
                arrayOf(intArrayOf(0, 0, 0, 0), intArrayOf(1, 1, 1, 1), intArrayOf(0, 0, 0, 0), intArrayOf(0, 0, 0, 0)),
                arrayOf(intArrayOf(0, 0, 1, 0), intArrayOf(0, 0, 1, 0), intArrayOf(0, 0, 1, 0), intArrayOf(0, 0, 1, 0)),
                arrayOf(intArrayOf(0, 0, 0, 0), intArrayOf(0, 0, 0, 0), intArrayOf(1, 1, 1, 1), intArrayOf(0, 0, 0, 0)),
                arrayOf(intArrayOf(0, 1, 0, 0), intArrayOf(0, 1, 0, 0), intArrayOf(0, 1, 0, 0), intArrayOf(0, 1, 0, 0))
            ),
            'O' to arrayOf(
                arrayOf(intArrayOf(1, 1), intArrayOf(1, 1)),
                arrayOf(intArrayOf(1, 1), intArrayOf(1, 1)),
                arrayOf(intArrayOf(1, 1), intArrayOf(1, 1)),
                arrayOf(intArrayOf(1, 1), intArrayOf(1, 1))
            ),
            'T' to arrayOf(
                arrayOf(intArrayOf(0, 1, 0), intArrayOf(1, 1, 1), intArrayOf(0, 0, 0)),
                arrayOf(intArrayOf(0, 1, 0), intArrayOf(0, 1, 1), intArrayOf(0, 1, 0)),
                arrayOf(intArrayOf(0, 0, 0), intArrayOf(1, 1, 1), intArrayOf(0, 1, 0)),
                arrayOf(intArrayOf(0, 1, 0), intArrayOf(1, 1, 0), intArrayOf(0, 1, 0))
            ),
            'S' to arrayOf(
                arrayOf(intArrayOf(0, 1, 1), intArrayOf(1, 1, 0), intArrayOf(0, 0, 0)),
                arrayOf(intArrayOf(0, 1, 0), intArrayOf(0, 1, 1), intArrayOf(0, 0, 1)),
                arrayOf(intArrayOf(0, 0, 0), intArrayOf(0, 1, 1), intArrayOf(1, 1, 0)),
                arrayOf(intArrayOf(1, 0, 0), intArrayOf(1, 1, 0), intArrayOf(0, 1, 0))
            ),
            'Z' to arrayOf(
                arrayOf(intArrayOf(1, 1, 0), intArrayOf(0, 1, 1), intArrayOf(0, 0, 0)),
                arrayOf(intArrayOf(0, 0, 1), intArrayOf(0, 1, 1), intArrayOf(0, 1, 0)),
                arrayOf(intArrayOf(0, 0, 0), intArrayOf(1, 1, 0), intArrayOf(0, 1, 1)),
                arrayOf(intArrayOf(0, 1, 0), intArrayOf(1, 1, 0), intArrayOf(1, 0, 0))
            ),
            'J' to arrayOf(
                arrayOf(intArrayOf(1, 0, 0), intArrayOf(1, 1, 1), intArrayOf(0, 0, 0)),
                arrayOf(intArrayOf(0, 1, 1), intArrayOf(0, 1, 0), intArrayOf(0, 1, 0)),
                arrayOf(intArrayOf(0, 0, 0), intArrayOf(1, 1, 1), intArrayOf(0, 0, 1)),
                arrayOf(intArrayOf(0, 1, 0), intArrayOf(0, 1, 0), intArrayOf(1, 1, 0))
            ),
            'L' to arrayOf(
                arrayOf(intArrayOf(0, 0, 1), intArrayOf(1, 1, 1), intArrayOf(0, 0, 0)),
                arrayOf(intArrayOf(0, 1, 0), intArrayOf(0, 1, 0), intArrayOf(0, 1, 1)),
                arrayOf(intArrayOf(0, 0, 0), intArrayOf(1, 1, 1), intArrayOf(1, 0, 0)),
                arrayOf(intArrayOf(1, 1, 0), intArrayOf(0, 1, 0), intArrayOf(0, 1, 0))
            )
        )
        
        fun getRandomPiece(): Piece {
            val types = listOf('I', 'O', 'T', 'S', 'Z', 'J', 'L')
            return Piece(types.random())
        }
    }
    
    fun getCurrentShape(): Array<IntArray> {
        return PIECES[type]?.get(rotation) ?: arrayOf()
    }
    
    fun getNextRotation(): Array<IntArray> {
        val nextRotation = (rotation + 1) % 4
        return PIECES[type]?.get(nextRotation) ?: arrayOf()
    }
    
    fun rotate() {
        rotation = (rotation + 1) % 4
    }
    
    fun rotateBack() {
        rotation = if (rotation == 0) 3 else rotation - 1
    }
    
    fun copy(): Piece {
        return Piece(type, x, y, rotation)
    }
}