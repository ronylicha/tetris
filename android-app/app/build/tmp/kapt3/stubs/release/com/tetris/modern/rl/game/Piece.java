package com.tetris.modern.rl.game;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 &2\u00020\u0001:\u0001&B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\u0006\u0010\u0017\u001a\u00020\u0000J1\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0011\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c\u00a2\u0006\u0002\u0010\u001eJ\u0011\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c\u00a2\u0006\u0002\u0010\u001eJ\t\u0010 \u001a\u00020\u0005H\u00d6\u0001J\u0006\u0010!\u001a\u00020\"J\u0006\u0010#\u001a\u00020\"J\t\u0010$\u001a\u00020%H\u00d6\u0001R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\n\"\u0004\b\u0010\u0010\fR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\f\u00a8\u0006\'"}, d2 = {"Lcom/tetris/modern/rl/game/Piece;", "", "type", "", "x", "", "y", "rotation", "(CIII)V", "getRotation", "()I", "setRotation", "(I)V", "getType", "()C", "getX", "setX", "getY", "setY", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "getCurrentShape", "", "", "()[[I", "getNextRotation", "hashCode", "rotate", "", "rotateBack", "toString", "", "Companion", "app_release"})
public final class Piece {
    private final char type = '\u0000';
    private int x;
    private int y;
    private int rotation;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Map<java.lang.Character, int[][][]> PIECES = null;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.game.Piece.Companion Companion = null;
    
    public Piece(char type, int x, int y, int rotation) {
        super();
    }
    
    public final char getType() {
        return '\u0000';
    }
    
    public final int getX() {
        return 0;
    }
    
    public final void setX(int p0) {
    }
    
    public final int getY() {
        return 0;
    }
    
    public final void setY(int p0) {
    }
    
    public final int getRotation() {
        return 0;
    }
    
    public final void setRotation(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final int[][] getCurrentShape() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final int[][] getNextRotation() {
        return null;
    }
    
    public final void rotate() {
    }
    
    public final void rotateBack() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.game.Piece copy() {
        return null;
    }
    
    public final char component1() {
        return '\u0000';
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.game.Piece copy(char type, int x, int y, int rotation) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\f\n\u0002\u0010\u0011\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bR)\u0010\u0003\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00060\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2 = {"Lcom/tetris/modern/rl/game/Piece$Companion;", "", "()V", "PIECES", "", "", "", "", "getPIECES", "()Ljava/util/Map;", "getRandomPiece", "Lcom/tetris/modern/rl/game/Piece;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Map<java.lang.Character, int[][][]> getPIECES() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.game.Piece getRandomPiece() {
            return null;
        }
    }
}