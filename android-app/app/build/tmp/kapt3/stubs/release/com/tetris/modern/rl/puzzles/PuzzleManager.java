package com.tetris.modern.rl.puzzles;

import com.tetris.modern.rl.game.models.PieceType;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001:\u00014B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002Jb\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\b\b\u0002\u0010\u0015\u001a\u00020\u00162\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00042\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0002J\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J#\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u00a2\u0006\u0002\u0010 J\u001c\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010%\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010&\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\fJ\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020)0(J\u0014\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0012\u001a\u00020\u0013J\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010,\u001a\u00020\fJ.\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\f2\u0006\u00101\u001a\u00020\u00162\u0006\u00102\u001a\u00020\f2\u0006\u00103\u001a\u00020\fR!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007\u00a8\u00065"}, d2 = {"Lcom/tetris/modern/rl/puzzles/PuzzleManager;", "", "()V", "puzzles", "", "Lcom/tetris/modern/rl/puzzles/Puzzle;", "getPuzzles", "()Ljava/util/List;", "puzzles$delegate", "Lkotlin/Lazy;", "createPuzzle", "id", "", "name", "", "description", "objective", "Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleObjective;", "difficulty", "Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleDifficulty;", "maxPieces", "timeLimit", "", "availablePieces", "Lcom/tetris/modern/rl/game/models/PieceType;", "constraints", "", "Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleConstraint;", "generateHints", "generateInitialGrid", "", "", "(ILcom/tetris/modern/rl/puzzles/Puzzle$PuzzleDifficulty;)[[I", "generateRemainingPuzzles", "existingPuzzles", "generateSolution", "Lcom/tetris/modern/rl/puzzles/Puzzle$PuzzleMove;", "getObjectiveShort", "getPuzzle", "getPuzzleProgress", "", "Lcom/tetris/modern/rl/puzzles/PuzzleManager$PuzzleProgress;", "getPuzzlesByDifficulty", "getUnlockedPuzzles", "playerLevel", "markPuzzleCompleted", "", "puzzleId", "stars", "completionTime", "movesUsed", "hintsUsed", "PuzzleProgress", "app_release"})
public final class PuzzleManager {
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy puzzles$delegate = null;
    
    @javax.inject.Inject
    public PuzzleManager() {
        super();
    }
    
    private final java.util.List<com.tetris.modern.rl.puzzles.Puzzle> getPuzzles() {
        return null;
    }
    
    private final com.tetris.modern.rl.puzzles.Puzzle createPuzzle(int id, java.lang.String name, java.lang.String description, com.tetris.modern.rl.puzzles.Puzzle.PuzzleObjective objective, com.tetris.modern.rl.puzzles.Puzzle.PuzzleDifficulty difficulty, int maxPieces, long timeLimit, java.util.List<? extends com.tetris.modern.rl.game.models.PieceType> availablePieces, java.util.Set<? extends com.tetris.modern.rl.puzzles.Puzzle.PuzzleConstraint> constraints) {
        return null;
    }
    
    private final java.util.List<com.tetris.modern.rl.puzzles.Puzzle> generateRemainingPuzzles(java.util.List<com.tetris.modern.rl.puzzles.Puzzle> existingPuzzles) {
        return null;
    }
    
    private final int[][] generateInitialGrid(int id, com.tetris.modern.rl.puzzles.Puzzle.PuzzleDifficulty difficulty) {
        return null;
    }
    
    private final java.util.List<java.lang.String> generateHints(com.tetris.modern.rl.puzzles.Puzzle.PuzzleObjective objective, com.tetris.modern.rl.puzzles.Puzzle.PuzzleDifficulty difficulty) {
        return null;
    }
    
    private final java.util.List<com.tetris.modern.rl.puzzles.Puzzle.PuzzleMove> generateSolution(int id, com.tetris.modern.rl.puzzles.Puzzle.PuzzleObjective objective) {
        return null;
    }
    
    private final java.lang.String getObjectiveShort(com.tetris.modern.rl.puzzles.Puzzle.PuzzleObjective objective) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tetris.modern.rl.puzzles.Puzzle getPuzzle(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.puzzles.Puzzle> getPuzzlesByDifficulty(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.puzzles.Puzzle.PuzzleDifficulty difficulty) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.puzzles.Puzzle> getUnlockedPuzzles(int playerLevel) {
        return null;
    }
    
    public final void markPuzzleCompleted(int puzzleId, int stars, long completionTime, int movesUsed, int hintsUsed) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.Integer, com.tetris.modern.rl.puzzles.PuzzleManager.PuzzleProgress> getPuzzleProgress() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0018\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u000fJ\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\fJD\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u00052\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00d6\u0001J\t\u0010 \u001a\u00020!H\u00d6\u0001R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014\u00a8\u0006\""}, d2 = {"Lcom/tetris/modern/rl/puzzles/PuzzleManager$PuzzleProgress;", "", "puzzleId", "", "completed", "", "stars", "bestTime", "", "bestMoves", "(IZILjava/lang/Long;Ljava/lang/Integer;)V", "getBestMoves", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBestTime", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getCompleted", "()Z", "getPuzzleId", "()I", "getStars", "component1", "component2", "component3", "component4", "component5", "copy", "(IZILjava/lang/Long;Ljava/lang/Integer;)Lcom/tetris/modern/rl/puzzles/PuzzleManager$PuzzleProgress;", "equals", "other", "hashCode", "toString", "", "app_release"})
    public static final class PuzzleProgress {
        private final int puzzleId = 0;
        private final boolean completed = false;
        private final int stars = 0;
        @org.jetbrains.annotations.Nullable
        private final java.lang.Long bestTime = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.Integer bestMoves = null;
        
        public PuzzleProgress(int puzzleId, boolean completed, int stars, @org.jetbrains.annotations.Nullable
        java.lang.Long bestTime, @org.jetbrains.annotations.Nullable
        java.lang.Integer bestMoves) {
            super();
        }
        
        public final int getPuzzleId() {
            return 0;
        }
        
        public final boolean getCompleted() {
            return false;
        }
        
        public final int getStars() {
            return 0;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Long getBestTime() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Integer getBestMoves() {
            return null;
        }
        
        public final int component1() {
            return 0;
        }
        
        public final boolean component2() {
            return false;
        }
        
        public final int component3() {
            return 0;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Long component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Integer component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.puzzles.PuzzleManager.PuzzleProgress copy(int puzzleId, boolean completed, int stars, @org.jetbrains.annotations.Nullable
        java.lang.Long bestTime, @org.jetbrains.annotations.Nullable
        java.lang.Integer bestMoves) {
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
}