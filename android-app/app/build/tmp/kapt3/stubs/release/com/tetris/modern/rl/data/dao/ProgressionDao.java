package com.tetris.modern.rl.data.dao;

import androidx.room.*;
import com.tetris.modern.rl.data.entities.ProgressionEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003H\'J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001e\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0012\u00a8\u0006\u0013"}, d2 = {"Lcom/tetris/modern/rl/data/dao/ProgressionDao;", "", "getProgression", "Lkotlinx/coroutines/flow/Flow;", "Lcom/tetris/modern/rl/data/entities/ProgressionEntity;", "updateLevelAndRank", "", "level", "", "rank", "", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProgression", "progression", "(Lcom/tetris/modern/rl/data/entities/ProgressionEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateXp", "xp", "totalXp", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
@androidx.room.Dao
public abstract interface ProgressionDao {
    
    @androidx.room.Query(value = "SELECT * FROM progression WHERE id = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.ProgressionEntity> getProgression();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateProgression(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.ProgressionEntity progression, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE progression SET currentXp = :xp, totalXp = :totalXp WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateXp(int xp, int totalXp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE progression SET currentLevel = :level, currentRank = :rank WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateLevelAndRank(int level, @org.jetbrains.annotations.NotNull
    java.lang.String rank, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}