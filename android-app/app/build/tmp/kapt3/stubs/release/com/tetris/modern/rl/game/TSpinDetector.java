package com.tetris.modern.rl.game;

import com.tetris.modern.rl.game.models.Piece;
import com.tetris.modern.rl.game.models.PieceType;

/**
 * Detects T-spins and T-spin minis according to official Tetris guidelines
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\"\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\"\u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\bH\u0002\u00a8\u0006\u0012"}, d2 = {"Lcom/tetris/modern/rl/game/TSpinDetector;", "", "()V", "detectTSpin", "", "piece", "Lcom/tetris/modern/rl/game/models/Piece;", "grid", "Lcom/tetris/modern/rl/game/Grid;", "detectTSpinMini", "getCornerPositions", "", "Lkotlin/Pair;", "", "getFrontCorners", "isPositionFilled", "x", "y", "app_release"})
public final class TSpinDetector {
    
    public TSpinDetector() {
        super();
    }
    
    public final boolean detectTSpin(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.Piece piece, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.Grid grid) {
        return false;
    }
    
    public final boolean detectTSpinMini(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.Piece piece, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.Grid grid) {
        return false;
    }
    
    private final java.util.List<kotlin.Pair<java.lang.Integer, java.lang.Integer>> getCornerPositions(com.tetris.modern.rl.game.models.Piece piece) {
        return null;
    }
    
    private final java.util.List<kotlin.Pair<java.lang.Integer, java.lang.Integer>> getFrontCorners(com.tetris.modern.rl.game.models.Piece piece) {
        return null;
    }
    
    private final boolean isPositionFilled(int x, int y, com.tetris.modern.rl.game.Grid grid) {
        return false;
    }
}