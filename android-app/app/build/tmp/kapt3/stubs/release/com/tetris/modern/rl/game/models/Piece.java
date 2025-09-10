package com.tetris.modern.rl.game.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 (2\u00020\u0001:\u0001(B\'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\u0006\u0010\u0017\u001a\u00020\u0000J1\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0013\u0010\u001b\u001a\u00020\u001c\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0011\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 \u00a2\u0006\u0002\u0010\"J\u0006\u0010#\u001a\u00020\u0005J\u0006\u0010$\u001a\u00020\u0005J\t\u0010%\u001a\u00020\u0005H\u00d6\u0001J\t\u0010&\u001a\u00020\'H\u00d6\u0001R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\n\"\u0004\b\u0010\u0010\fR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\f\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006)"}, d2 = {"Lcom/tetris/modern/rl/game/models/Piece;", "", "type", "Lcom/tetris/modern/rl/game/models/PieceType;", "x", "", "y", "rotation", "(Lcom/tetris/modern/rl/game/models/PieceType;III)V", "getRotation", "()I", "setRotation", "(I)V", "getType", "()Lcom/tetris/modern/rl/game/models/PieceType;", "getX", "setX", "getY", "setY", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "getColor", "Landroidx/compose/ui/graphics/Color;", "getColor-0d7_KjU", "()J", "getCurrentShape", "", "", "()[[I", "getHeight", "getWidth", "hashCode", "toString", "", "Companion", "app_release"})
public final class Piece {
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.game.models.PieceType type = null;
    private int x;
    private int y;
    private int rotation;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Map<com.tetris.modern.rl.game.models.PieceType, int[][][]> SHAPES = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Map<com.tetris.modern.rl.game.models.PieceType, androidx.compose.ui.graphics.Color> COLORS = null;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.game.models.Piece.Companion Companion = null;
    
    public Piece(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.PieceType type, int x, int y, int rotation) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.game.models.PieceType getType() {
        return null;
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
    public final com.tetris.modern.rl.game.models.Piece copy() {
        return null;
    }
    
    public final int getWidth() {
        return 0;
    }
    
    public final int getHeight() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.game.models.PieceType component1() {
        return null;
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
    public final com.tetris.modern.rl.game.models.Piece copy(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.PieceType type, int x, int y, int rotation) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0015\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR&\u0010\t\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/tetris/modern/rl/game/models/Piece$Companion;", "", "()V", "COLORS", "", "Lcom/tetris/modern/rl/game/models/PieceType;", "Landroidx/compose/ui/graphics/Color;", "getCOLORS", "()Ljava/util/Map;", "SHAPES", "", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Map<com.tetris.modern.rl.game.models.PieceType, androidx.compose.ui.graphics.Color> getCOLORS() {
            return null;
        }
    }
}