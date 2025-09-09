package com.tetris.modern.rl.game.models;

/**
 * Represents the current state of the game
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\bA\b\u0086\b\u0018\u00002\u00020\u0001B\u009f\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\u0014\b\u0002\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00030\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0016\u001a\u00020\b\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0014\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\f\u0012\u0014\b\u0002\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u0012\u0012\b\b\u0002\u0010 \u001a\u00020\f\u00a2\u0006\u0002\u0010!J\t\u0010@\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010A\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010H\u00c6\u0003J\u0015\u0010B\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00030\u0012H\u00c6\u0003J\t\u0010C\u001a\u00020\u0014H\u00c6\u0003J\t\u0010D\u001a\u00020\u0003H\u00c6\u0003J\t\u0010E\u001a\u00020\bH\u00c6\u0003J\u0010\u0010F\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u0010\u0010G\u001a\u0004\u0018\u00010\u0014H\u00c6\u0003\u00a2\u0006\u0002\u0010;J\u0010\u0010H\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u0010\u0010I\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u0011\u0010J\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u0010H\u00c6\u0003J\t\u0010K\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010L\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u000b\u0010M\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u0015\u0010N\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u0012H\u00c6\u0003J\t\u0010O\u001a\u00020\fH\u00c6\u0003J\t\u0010P\u001a\u00020\u0003H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0003H\u00c6\u0003J\t\u0010R\u001a\u00020\bH\u00c6\u0003J\t\u0010S\u001a\u00020\bH\u00c6\u0003J\t\u0010T\u001a\u00020\bH\u00c6\u0003J\t\u0010U\u001a\u00020\fH\u00c6\u0003J\u000b\u0010V\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\u00a8\u0002\u0010W\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00102\u0014\b\u0002\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00030\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\b2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u00102\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\f2\u0014\b\u0002\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u00122\b\b\u0002\u0010 \u001a\u00020\fH\u00c6\u0001\u00a2\u0006\u0002\u0010XJ\u0013\u0010Y\u001a\u00020\b2\b\u0010Z\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010[\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\\\u001a\u00020\fH\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0015\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b$\u0010%R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010-R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010-R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010-R\u0011\u0010\u0016\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010-R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010#R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010#R\u001d\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0011\u0010 \u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010*R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0015\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b5\u0010%R\u0011\u0010\u0015\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010#R\u0019\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u00104R\u0015\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b8\u0010%R\u0013\u0010\u001e\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010*R\u0015\u0010\u0018\u001a\u0004\u0018\u00010\u0014\u00a2\u0006\n\n\u0002\u0010<\u001a\u0004\b:\u0010;R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010#R\u001d\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00030\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u00101R\u0015\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b?\u0010%\u00a8\u0006]"}, d2 = {"Lcom/tetris/modern/rl/game/models/GameState;", "", "score", "", "lines", "level", "combo", "isPlaying", "", "isPaused", "isGameOver", "gameMode", "", "heldPiece", "Lcom/tetris/modern/rl/game/models/PieceType;", "nextPieces", "", "statistics", "", "gameDuration", "", "piecesPlaced", "isVictory", "targetLines", "remainingTime", "currentRound", "opponentLines", "powerUps", "Lcom/tetris/modern/rl/game/models/PowerUpType;", "puzzleId", "puzzleObjective", "modeInfo", "modeObjective", "(IIIIZZZLjava/lang/String;Lcom/tetris/modern/rl/game/models/PieceType;Ljava/util/List;Ljava/util/Map;JIZLjava/lang/Integer;Ljava/lang/Long;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/util/List;Ljava/lang/Integer;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)V", "getCombo", "()I", "getCurrentRound", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getGameDuration", "()J", "getGameMode", "()Ljava/lang/String;", "getHeldPiece", "()Lcom/tetris/modern/rl/game/models/PieceType;", "()Z", "getLevel", "getLines", "getModeInfo", "()Ljava/util/Map;", "getModeObjective", "getNextPieces", "()Ljava/util/List;", "getOpponentLines", "getPiecesPlaced", "getPowerUps", "getPuzzleId", "getPuzzleObjective", "getRemainingTime", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getScore", "getStatistics", "getTargetLines", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(IIIIZZZLjava/lang/String;Lcom/tetris/modern/rl/game/models/PieceType;Ljava/util/List;Ljava/util/Map;JIZLjava/lang/Integer;Ljava/lang/Long;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/util/List;Ljava/lang/Integer;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Lcom/tetris/modern/rl/game/models/GameState;", "equals", "other", "hashCode", "toString", "app_release"})
public final class GameState {
    private final int score = 0;
    private final int lines = 0;
    private final int level = 0;
    private final int combo = 0;
    private final boolean isPlaying = false;
    private final boolean isPaused = false;
    private final boolean isGameOver = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String gameMode = null;
    @org.jetbrains.annotations.Nullable
    private final com.tetris.modern.rl.game.models.PieceType heldPiece = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.game.models.PieceType> nextPieces = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Map<java.lang.String, java.lang.Integer> statistics = null;
    private final long gameDuration = 0L;
    private final int piecesPlaced = 0;
    private final boolean isVictory = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer targetLines = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long remainingTime = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer currentRound = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer opponentLines = null;
    @org.jetbrains.annotations.Nullable
    private final java.util.List<com.tetris.modern.rl.game.models.PowerUpType> powerUps = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer puzzleId = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String puzzleObjective = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Map<java.lang.String, java.lang.String> modeInfo = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String modeObjective = null;
    
    public GameState(int score, int lines, int level, int combo, boolean isPlaying, boolean isPaused, boolean isGameOver, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, @org.jetbrains.annotations.Nullable
    com.tetris.modern.rl.game.models.PieceType heldPiece, @org.jetbrains.annotations.NotNull
    java.util.List<? extends com.tetris.modern.rl.game.models.PieceType> nextPieces, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.Integer> statistics, long gameDuration, int piecesPlaced, boolean isVictory, @org.jetbrains.annotations.Nullable
    java.lang.Integer targetLines, @org.jetbrains.annotations.Nullable
    java.lang.Long remainingTime, @org.jetbrains.annotations.Nullable
    java.lang.Integer currentRound, @org.jetbrains.annotations.Nullable
    java.lang.Integer opponentLines, @org.jetbrains.annotations.Nullable
    java.util.List<? extends com.tetris.modern.rl.game.models.PowerUpType> powerUps, @org.jetbrains.annotations.Nullable
    java.lang.Integer puzzleId, @org.jetbrains.annotations.Nullable
    java.lang.String puzzleObjective, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.String> modeInfo, @org.jetbrains.annotations.NotNull
    java.lang.String modeObjective) {
        super();
    }
    
    public final int getScore() {
        return 0;
    }
    
    public final int getLines() {
        return 0;
    }
    
    public final int getLevel() {
        return 0;
    }
    
    public final int getCombo() {
        return 0;
    }
    
    public final boolean isPlaying() {
        return false;
    }
    
    public final boolean isPaused() {
        return false;
    }
    
    public final boolean isGameOver() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGameMode() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tetris.modern.rl.game.models.PieceType getHeldPiece() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.game.models.PieceType> getNextPieces() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.Integer> getStatistics() {
        return null;
    }
    
    public final long getGameDuration() {
        return 0L;
    }
    
    public final int getPiecesPlaced() {
        return 0;
    }
    
    public final boolean isVictory() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getTargetLines() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getRemainingTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getCurrentRound() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getOpponentLines() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.tetris.modern.rl.game.models.PowerUpType> getPowerUps() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getPuzzleId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getPuzzleObjective() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.String> getModeInfo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getModeObjective() {
        return null;
    }
    
    public GameState() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.game.models.PieceType> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.Integer> component11() {
        return null;
    }
    
    public final long component12() {
        return 0L;
    }
    
    public final int component13() {
        return 0;
    }
    
    public final boolean component14() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component17() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component18() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.tetris.modern.rl.game.models.PowerUpType> component19() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component20() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component21() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.String> component22() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component23() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tetris.modern.rl.game.models.PieceType component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.game.models.GameState copy(int score, int lines, int level, int combo, boolean isPlaying, boolean isPaused, boolean isGameOver, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, @org.jetbrains.annotations.Nullable
    com.tetris.modern.rl.game.models.PieceType heldPiece, @org.jetbrains.annotations.NotNull
    java.util.List<? extends com.tetris.modern.rl.game.models.PieceType> nextPieces, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.Integer> statistics, long gameDuration, int piecesPlaced, boolean isVictory, @org.jetbrains.annotations.Nullable
    java.lang.Integer targetLines, @org.jetbrains.annotations.Nullable
    java.lang.Long remainingTime, @org.jetbrains.annotations.Nullable
    java.lang.Integer currentRound, @org.jetbrains.annotations.Nullable
    java.lang.Integer opponentLines, @org.jetbrains.annotations.Nullable
    java.util.List<? extends com.tetris.modern.rl.game.models.PowerUpType> powerUps, @org.jetbrains.annotations.Nullable
    java.lang.Integer puzzleId, @org.jetbrains.annotations.Nullable
    java.lang.String puzzleObjective, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.String> modeInfo, @org.jetbrains.annotations.NotNull
    java.lang.String modeObjective) {
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