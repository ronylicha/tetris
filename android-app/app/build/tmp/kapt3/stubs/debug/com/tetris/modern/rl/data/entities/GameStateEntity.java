package com.tetris.modern.rl.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b \b\u0087\b\u0018\u00002\u00020\u0001Ba\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u000eH\u00c6\u0003J\t\u0010\"\u001a\u00020\u0010H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0005H\u00c6\u0003Jy\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u00c6\u0001J\u0013\u0010,\u001a\u00020\u000e2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020\u0003H\u00d6\u0001J\t\u0010/\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0019R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f\u00a8\u00060"}, d2 = {"Lcom/tetris/modern/rl/data/entities/GameStateEntity;", "", "id", "", "grid", "", "currentPiece", "nextPieces", "heldPiece", "score", "level", "lines", "gameMode", "isPaused", "", "timestamp", "", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIILjava/lang/String;ZJ)V", "getCurrentPiece", "()Ljava/lang/String;", "getGameMode", "getGrid", "getHeldPiece", "getId", "()I", "()Z", "getLevel", "getLines", "getNextPieces", "getScore", "getTimestamp", "()J", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "game_states")
public final class GameStateEntity {
    @androidx.room.PrimaryKey
    private final int id = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String grid = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String currentPiece = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String nextPieces = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String heldPiece = null;
    private final int score = 0;
    private final int level = 0;
    private final int lines = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String gameMode = null;
    private final boolean isPaused = false;
    private final long timestamp = 0L;
    
    public GameStateEntity(int id, @org.jetbrains.annotations.NotNull
    java.lang.String grid, @org.jetbrains.annotations.NotNull
    java.lang.String currentPiece, @org.jetbrains.annotations.NotNull
    java.lang.String nextPieces, @org.jetbrains.annotations.Nullable
    java.lang.String heldPiece, int score, int level, int lines, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, boolean isPaused, long timestamp) {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGrid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCurrentPiece() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getNextPieces() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getHeldPiece() {
        return null;
    }
    
    public final int getScore() {
        return 0;
    }
    
    public final int getLevel() {
        return 0;
    }
    
    public final int getLines() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGameMode() {
        return null;
    }
    
    public final boolean isPaused() {
        return false;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    public final int component1() {
        return 0;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final long component11() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.entities.GameStateEntity copy(int id, @org.jetbrains.annotations.NotNull
    java.lang.String grid, @org.jetbrains.annotations.NotNull
    java.lang.String currentPiece, @org.jetbrains.annotations.NotNull
    java.lang.String nextPieces, @org.jetbrains.annotations.Nullable
    java.lang.String heldPiece, int score, int level, int lines, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, boolean isPaused, long timestamp) {
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
}