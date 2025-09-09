package com.tetris.modern.rl.data.dao;

import androidx.room.*;
import com.tetris.modern.rl.data.entities.ScoreEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001c\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\'J\u001e\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000f0\u000e2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\'J&\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000f0\u000e2\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\'J\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0018\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001c\u0010\u0019\u001a\u00020\u00032\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00070\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\u001d"}, d2 = {"Lcom/tetris/modern/rl/data/dao/ScoreDao;", "", "deleteAllScores", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldScores", "timestamp", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteScore", "score", "Lcom/tetris/modern/rl/data/entities/ScoreEntity;", "(Lcom/tetris/modern/rl/data/entities/ScoreEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlayerScores", "Lkotlinx/coroutines/flow/Flow;", "", "playerName", "", "getTopScores", "limit", "", "getTopScoresByMode", "gameMode", "getUnsyncedScores", "insertScore", "markAsSynced", "ids", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateScore", "app_debug"})
@androidx.room.Dao
public abstract interface ScoreDao {
    
    @androidx.room.Query(value = "SELECT * FROM scores ORDER BY score DESC LIMIT :limit")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getTopScores(int limit);
    
    @androidx.room.Query(value = "SELECT * FROM scores WHERE gameMode = :gameMode ORDER BY score DESC LIMIT :limit")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getTopScoresByMode(@org.jetbrains.annotations.NotNull
    java.lang.String gameMode, int limit);
    
    @androidx.room.Query(value = "SELECT * FROM scores WHERE playerName = :playerName ORDER BY score DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getPlayerScores(@org.jetbrains.annotations.NotNull
    java.lang.String playerName);
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertScore(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.ScoreEntity score, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateScore(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.ScoreEntity score, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteScore(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.ScoreEntity score, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM scores")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteAllScores(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM scores WHERE isSynced = 0")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUnsyncedScores(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> $completion);
    
    @androidx.room.Query(value = "UPDATE scores SET isSynced = 1 WHERE id IN (:ids)")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsSynced(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Long> ids, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM scores WHERE timestamp < :timestamp")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteOldScores(long timestamp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}