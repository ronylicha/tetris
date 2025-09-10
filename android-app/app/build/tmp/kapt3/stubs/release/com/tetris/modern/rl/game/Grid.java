package com.tetris.modern.rl.game;

import com.tetris.modern.rl.game.models.Piece;
import com.tetris.modern.rl.game.models.PieceType;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u0003H\u0002J\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000fJ\u0006\u0010\u0010\u001a\u00020\u0000J\u0006\u0010\u0011\u001a\u00020\u0003J\u0006\u0010\u0012\u001a\u00020\u0003J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003J\u0019\u0010\u0015\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00070\u0007\u00a2\u0006\u0002\u0010\u0016J\u000e\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0003J\u0006\u0010\u0018\u001a\u00020\u0003J\u0011\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0007\u00a2\u0006\u0002\u0010\u001bJ\u0006\u0010\u001c\u001a\u00020\u0003J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\r\u001a\u00020\u0003H\u0002J\u0006\u0010\u001f\u001a\u00020\u001eJ\u0010\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u0003H\u0002J\u000e\u0010\"\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020$J \u0010%\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00032\b\u0010&\u001a\u0004\u0018\u00010\bR\u001e\u0010\u0006\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00070\u0007X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/tetris/modern/rl/game/Grid;", "", "width", "", "height", "(II)V", "cells", "", "Lcom/tetris/modern/rl/game/models/PieceType;", "[[Lcom/tetris/modern/rl/game/models/PieceType;", "clear", "", "clearLine", "y", "clearLines", "", "copy", "getAggregateHeight", "getBumpiness", "getCell", "x", "getFullGrid", "()[[Lcom/tetris/modern/rl/game/models/PieceType;", "getHeight", "getHoles", "getVisibleGrid", "", "()[[I", "getWells", "isLineFull", "", "isPerfectClear", "moveDownAbove", "clearedLine", "placePiece", "piece", "Lcom/tetris/modern/rl/game/models/Piece;", "setCell", "type", "app_release"})
public final class Grid {
    private final int width = 0;
    private final int height = 0;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.game.models.PieceType[][] cells = null;
    
    public Grid(int width, int height) {
        super();
    }
    
    public final void clear() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tetris.modern.rl.game.models.PieceType getCell(int x, int y) {
        return null;
    }
    
    public final void setCell(int x, int y, @org.jetbrains.annotations.Nullable
    com.tetris.modern.rl.game.models.PieceType type) {
    }
    
    public final void placePiece(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.Piece piece) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.Integer> clearLines() {
        return null;
    }
    
    private final boolean isLineFull(int y) {
        return false;
    }
    
    private final void clearLine(int y) {
    }
    
    private final void moveDownAbove(int clearedLine) {
    }
    
    public final boolean isPerfectClear() {
        return false;
    }
    
    public final int getHeight(int x) {
        return 0;
    }
    
    public final int getAggregateHeight() {
        return 0;
    }
    
    public final int getHoles() {
        return 0;
    }
    
    public final int getBumpiness() {
        return 0;
    }
    
    public final int getWells() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final int[][] getVisibleGrid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.game.models.PieceType[][] getFullGrid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.game.Grid copy() {
        return null;
    }
}