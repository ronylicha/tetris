package com.tetris.modern.rl.data.dao;

import androidx.room.*;
import com.tetris.modern.rl.data.entities.PuzzleProgressEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u000e\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00032\u0006\u0010\u000b\u001a\u00020\u0007H\'J\u000e\u0010\f\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/tetris/modern/rl/data/dao/PuzzleProgressDao;", "", "getAllPuzzleProgress", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/tetris/modern/rl/data/entities/PuzzleProgressEntity;", "getCompletedCount", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCompletedPuzzles", "getPuzzleProgress", "puzzleId", "getTotalStars", "updatePuzzleProgress", "", "progress", "(Lcom/tetris/modern/rl/data/entities/PuzzleProgressEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface PuzzleProgressDao {
    
    @androidx.room.Query(value = "SELECT * FROM puzzle_progress")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.PuzzleProgressEntity>> getAllPuzzleProgress();
    
    @androidx.room.Query(value = "SELECT * FROM puzzle_progress WHERE puzzleId = :puzzleId")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.PuzzleProgressEntity> getPuzzleProgress(int puzzleId);
    
    @androidx.room.Query(value = "SELECT * FROM puzzle_progress WHERE isCompleted = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.PuzzleProgressEntity>> getCompletedPuzzles();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updatePuzzleProgress(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.PuzzleProgressEntity progress, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM puzzle_progress WHERE isCompleted = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getCompletedCount(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(stars) FROM puzzle_progress")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getTotalStars(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}