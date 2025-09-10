package com.tetris.modern.rl.puzzles;

import com.tetris.modern.rl.game.TetrisEngine;
import com.tetris.modern.rl.game.models.PieceType;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b/\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\b\u0018\u00002\u00020\u0001:\u0004XYZ[B\u0093\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u0012\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0013\u0012\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0013\u00a2\u0006\u0002\u0010\u001bJ\u0006\u00106\u001a\u00020\u001fJ\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\u000f\u00108\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u00c6\u0003J\u000f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u00c6\u0003J\u000f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00050\u0013H\u00c6\u0003J\u0011\u0010;\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0013H\u00c6\u0003J\t\u0010<\u001a\u00020\u0005H\u00c6\u0003J\t\u0010=\u001a\u00020\u0005H\u00c6\u0003J\t\u0010>\u001a\u00020\bH\u00c6\u0003J\t\u0010?\u001a\u00020\u0005H\u00c6\u0003J\t\u0010@\u001a\u00020\u000bH\u00c6\u0003J\t\u0010A\u001a\u00020\u0003H\u00c6\u0003J\t\u0010B\u001a\u00020\u000eH\u00c6\u0003J\u0014\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u00c6\u0003\u00a2\u0006\u0002\u0010*J\u00b0\u0001\u0010D\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u00132\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0013H\u00c6\u0001\u00a2\u0006\u0002\u0010EJ\u0013\u0010F\u001a\u00020\u001f2\b\u0010G\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u000e\u0010H\u001a\u00020\u00052\u0006\u0010I\u001a\u00020\u0003J\b\u0010J\u001a\u00020\u0003H\u0002J\t\u0010K\u001a\u00020\u0003H\u00d6\u0001J\u0006\u0010L\u001a\u00020\u001fJ\b\u0010M\u001a\u00020\u001fH\u0002J\u0016\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020\u00032\u0006\u0010Q\u001a\u00020\u001fJ\u000e\u0010R\u001a\u00020O2\u0006\u0010S\u001a\u00020\u0014J\u000e\u0010T\u001a\u00020O2\u0006\u0010U\u001a\u00020VJ\t\u0010W\u001a\u00020\u0005H\u00d6\u0001R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001dR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0019\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\n\n\u0002\u0010+\u001a\u0004\b)\u0010*R\u000e\u0010,\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010(R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010#R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010#R\u000e\u00102\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010\u001dR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u00105\u00a8\u0006\\"}, d2 = {"Lcom/tetris/modern/rl/puzzles/Puzzle;", "", "id", "", "name", "", "description", "objective", "Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleObjective;", "objectiveShort", "difficulty", "Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleDifficulty;", "maxPieces", "targetTime", "", "initialGrid", "", "", "availablePieces", "", "Lcom/tetris/modern/rl/game/models/PieceType;", "constraints", "", "Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleConstraint;", "hints", "solution", "Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleMove;", "(ILjava/lang/String;Ljava/lang/String;Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleObjective;Ljava/lang/String;Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleDifficulty;IJ[[ILjava/util/List;Ljava/util/Set;Ljava/util/List;Ljava/util/List;)V", "getAvailablePieces", "()Ljava/util/List;", "completed", "", "getConstraints", "()Ljava/util/Set;", "getDescription", "()Ljava/lang/String;", "getDifficulty", "()Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleDifficulty;", "getHints", "getId", "()I", "getInitialGrid", "()[[I", "[[I", "linesCleared", "getMaxPieces", "getName", "getObjective", "()Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleObjective;", "getObjectiveShort", "piecesUsed", "getSolution", "getTargetTime", "()J", "checkCompletion", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(ILjava/lang/String;Ljava/lang/String;Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleObjective;Ljava/lang/String;Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleDifficulty;IJ[[ILjava/util/List;Ljava/util/Set;Ljava/util/List;Ljava/util/List;)Lcom/tetris/modern/rl/puzzles/Puzzle;", "equals", "other", "getHint", "level", "getTargetLines", "hashCode", "isCompleted", "isGridEmpty", "onLinesClear", "", "lines", "isTSpin", "onPiecePlaced", "piece", "setupGrid", "engine", "Lcom/tetris/modern/rl/game/TetrisEngine;", "toString", "PuzzleConstraint", "PuzzleDifficulty", "PuzzleMove", "PuzzleObjective", "app_debug"})
public final class Puzzle {
    private final int id = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.puzzles.Puzzle.PuzzleObjective objective = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String objectiveShort = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.puzzles.Puzzle.PuzzleDifficulty difficulty = null;
    private final int maxPieces = 0;
    private final long targetTime = 0L;
    @org.jetbrains.annotations.NotNull
    private final int[][] initialGrid = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.game.models.PieceType> availablePieces = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<com.tetris.modern.rl.puzzles.Puzzle.PuzzleConstraint> constraints = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> hints = null;
    @org.jetbrains.annotations.Nullable
    private final java.util.List<com.tetris.modern.rl.puzzles.Puzzle.PuzzleMove> solution = null;
    private int linesCleared = 0;
    private int piecesUsed = 0;
    private boolean completed = false;
    
    public Puzzle(int id, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.puzzles.Puzzle.PuzzleObjective objective, @org.jetbrains.annotations.NotNull
    java.lang.String objectiveShort, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.puzzles.Puzzle.PuzzleDifficulty difficulty, int maxPieces, long targetTime, @org.jetbrains.annotations.NotNull
    int[][] initialGrid, @org.jetbrains.annotations.NotNull
    java.util.List<? extends com.tetris.modern.rl.game.models.PieceType> availablePieces, @org.jetbrains.annotations.NotNull
    java.util.Set<? extends com.tetris.modern.rl.puzzles.Puzzle.PuzzleConstraint> constraints, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> hints, @org.jetbrains.annotations.Nullable
    java.util.List<com.tetris.modern.rl.puzzles.Puzzle.PuzzleMove> solution) {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.puzzles.Puzzle.PuzzleObjective getObjective() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getObjectiveShort() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.puzzles.Puzzle.PuzzleDifficulty getDifficulty() {
        return null;
    }
    
    public final int getMaxPieces() {
        return 0;
    }
    
    public final long getTargetTime() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final int[][] getInitialGrid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.game.models.PieceType> getAvailablePieces() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Set<com.tetris.modern.rl.puzzles.Puzzle.PuzzleConstraint> getConstraints() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getHints() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.tetris.modern.rl.puzzles.Puzzle.PuzzleMove> getSolution() {
        return null;
    }
    
    public final void setupGrid(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.TetrisEngine engine) {
    }
    
    public final void onLinesClear(int lines, boolean isTSpin) {
    }
    
    public final void onPiecePlaced(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.PieceType piece) {
    }
    
    public final boolean isCompleted() {
        return false;
    }
    
    public final boolean checkCompletion() {
        return false;
    }
    
    private final int getTargetLines() {
        return 0;
    }
    
    private final boolean isGridEmpty() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getHint(int level) {
        return null;
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.game.models.PieceType> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Set<com.tetris.modern.rl.puzzles.Puzzle.PuzzleConstraint> component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.tetris.modern.rl.puzzles.Puzzle.PuzzleMove> component13() {
        return null;
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
    public final com.tetris.modern.rl.puzzles.Puzzle.PuzzleObjective component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.puzzles.Puzzle.PuzzleDifficulty component6() {
        return null;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final long component8() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final int[][] component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.puzzles.Puzzle copy(int id, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.puzzles.Puzzle.PuzzleObjective objective, @org.jetbrains.annotations.NotNull
    java.lang.String objectiveShort, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.puzzles.Puzzle.PuzzleDifficulty difficulty, int maxPieces, long targetTime, @org.jetbrains.annotations.NotNull
    int[][] initialGrid, @org.jetbrains.annotations.NotNull
    java.util.List<? extends com.tetris.modern.rl.game.models.PieceType> availablePieces, @org.jetbrains.annotations.NotNull
    java.util.Set<? extends com.tetris.modern.rl.puzzles.Puzzle.PuzzleConstraint> constraints, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> hints, @org.jetbrains.annotations.Nullable
    java.util.List<com.tetris.modern.rl.puzzles.Puzzle.PuzzleMove> solution) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\f"}, d2 = {"Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleConstraint;", "", "(Ljava/lang/String;I)V", "NO_HOLD", "NO_SOFT_DROP", "NO_HARD_DROP", "NO_ROTATION", "MIRROR_MODE", "INVISIBLE_PIECES", "LIMITED_PIECES", "SPEED_UP", "ONE_CHANCE", "app_debug"})
    public static enum PuzzleConstraint {
        /*public static final*/ NO_HOLD /* = new NO_HOLD() */,
        /*public static final*/ NO_SOFT_DROP /* = new NO_SOFT_DROP() */,
        /*public static final*/ NO_HARD_DROP /* = new NO_HARD_DROP() */,
        /*public static final*/ NO_ROTATION /* = new NO_ROTATION() */,
        /*public static final*/ MIRROR_MODE /* = new MIRROR_MODE() */,
        /*public static final*/ INVISIBLE_PIECES /* = new INVISIBLE_PIECES() */,
        /*public static final*/ LIMITED_PIECES /* = new LIMITED_PIECES() */,
        /*public static final*/ SPEED_UP /* = new SPEED_UP() */,
        /*public static final*/ ONE_CHANCE /* = new ONE_CHANCE() */;
        
        PuzzleConstraint() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.puzzles.Puzzle.PuzzleConstraint> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleDifficulty;", "", "stars", "", "color", "", "(Ljava/lang/String;IILjava/lang/String;)V", "getColor", "()Ljava/lang/String;", "getStars", "()I", "EASY", "MEDIUM", "HARD", "EXPERT", "MASTER", "app_debug"})
    public static enum PuzzleDifficulty {
        /*public static final*/ EASY /* = new EASY(0, null) */,
        /*public static final*/ MEDIUM /* = new MEDIUM(0, null) */,
        /*public static final*/ HARD /* = new HARD(0, null) */,
        /*public static final*/ EXPERT /* = new EXPERT(0, null) */,
        /*public static final*/ MASTER /* = new MASTER(0, null) */;
        private final int stars = 0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String color = null;
        
        PuzzleDifficulty(int stars, java.lang.String color) {
        }
        
        public final int getStars() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getColor() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.puzzles.Puzzle.PuzzleDifficulty> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f\u00a8\u0006\u001a"}, d2 = {"Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleMove;", "", "pieceType", "Lcom/tetris/modern/rl/game/models/PieceType;", "x", "", "y", "rotation", "(Lcom/tetris/modern/rl/game/models/PieceType;III)V", "getPieceType", "()Lcom/tetris/modern/rl/game/models/PieceType;", "getRotation", "()I", "getX", "getY", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class PuzzleMove {
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.game.models.PieceType pieceType = null;
        private final int x = 0;
        private final int y = 0;
        private final int rotation = 0;
        
        public PuzzleMove(@org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.game.models.PieceType pieceType, int x, int y, int rotation) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.game.models.PieceType getPieceType() {
            return null;
        }
        
        public final int getX() {
            return 0;
        }
        
        public final int getY() {
            return 0;
        }
        
        public final int getRotation() {
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
        public final com.tetris.modern.rl.puzzles.Puzzle.PuzzleMove copy(@org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.game.models.PieceType pieceType, int x, int y, int rotation) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000e\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleObjective;", "", "(Ljava/lang/String;I)V", "CLEAR_ALL", "CLEAR_SPECIFIC", "CLEAR_TARGET", "CASCADE", "PATTERN", "SURVIVAL", "SPEED", "NO_ROTATION", "CHAIN", "PERFECT_CLEAR", "T_SPIN_ONLY", "TETRIS_ONLY", "app_debug"})
    public static enum PuzzleObjective {
        /*public static final*/ CLEAR_ALL /* = new CLEAR_ALL() */,
        /*public static final*/ CLEAR_SPECIFIC /* = new CLEAR_SPECIFIC() */,
        /*public static final*/ CLEAR_TARGET /* = new CLEAR_TARGET() */,
        /*public static final*/ CASCADE /* = new CASCADE() */,
        /*public static final*/ PATTERN /* = new PATTERN() */,
        /*public static final*/ SURVIVAL /* = new SURVIVAL() */,
        /*public static final*/ SPEED /* = new SPEED() */,
        /*public static final*/ NO_ROTATION /* = new NO_ROTATION() */,
        /*public static final*/ CHAIN /* = new CHAIN() */,
        /*public static final*/ PERFECT_CLEAR /* = new PERFECT_CLEAR() */,
        /*public static final*/ T_SPIN_ONLY /* = new T_SPIN_ONLY() */,
        /*public static final*/ TETRIS_ONLY /* = new TETRIS_ONLY() */;
        
        PuzzleObjective() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.puzzles.Puzzle.PuzzleObjective> getEntries() {
            return null;
        }
    }
}