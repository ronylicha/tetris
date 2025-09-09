package com.tetris.modern.rl.game

import com.tetris.modern.rl.game.models.PieceType
import kotlin.random.Random

/**
 * Implements the 7-bag randomizer system for fair piece distribution
 */
class PieceBag {
    private val pieces = mutableListOf<PieceType>()
    private val nextBag = mutableListOf<PieceType>()
    
    init {
        reset()
    }
    
    fun reset() {
        pieces.clear()
        nextBag.clear()
        fillBag(pieces)
        fillBag(nextBag)
    }
    
    private fun fillBag(bag: MutableList<PieceType>) {
        bag.clear()
        bag.addAll(PieceType.values())
        bag.shuffle(Random.Default)
    }
    
    fun getNext(): PieceType {
        if (pieces.isEmpty()) {
            pieces.addAll(nextBag)
            nextBag.clear()
            fillBag(nextBag)
        }
        
        return pieces.removeAt(0)
    }
    
    fun preview(count: Int): List<PieceType> {
        val preview = mutableListOf<PieceType>()
        val combined = pieces + nextBag
        
        for (i in 0 until minOf(count, combined.size)) {
            preview.add(combined[i])
        }
        
        // If we don't have enough pieces, generate more bags
        while (preview.size < count) {
            val tempBag = PieceType.values().toMutableList()
            tempBag.shuffle(Random.Default)
            preview.addAll(tempBag.take(count - preview.size))
        }
        
        return preview
    }
}