package com.tetris.modern.rl.game;

import com.tetris.modern.rl.game.models.PieceType;

/**
 * Implements the 7-bag randomizer system for fair piece distribution
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u0006\u0010\n\u001a\u00020\u0005J\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/tetris/modern/rl/game/PieceBag;", "", "()V", "nextBag", "", "Lcom/tetris/modern/rl/game/models/PieceType;", "pieces", "fillBag", "", "bag", "getNext", "preview", "", "count", "", "reset", "app_debug"})
public final class PieceBag {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.game.models.PieceType> pieces = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.game.models.PieceType> nextBag = null;
    
    public PieceBag() {
        super();
    }
    
    public final void reset() {
    }
    
    private final void fillBag(java.util.List<com.tetris.modern.rl.game.models.PieceType> bag) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.game.models.PieceType getNext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.game.models.PieceType> preview(int count) {
        return null;
    }
}