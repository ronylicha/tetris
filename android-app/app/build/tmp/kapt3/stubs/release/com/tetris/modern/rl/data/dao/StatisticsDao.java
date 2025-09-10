package com.tetris.modern.rl.data.dao;

import androidx.room.*;
import com.tetris.modern.rl.data.entities.StatisticsEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u000e\bg\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001e\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u0014\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00170\u0016H\'J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ \u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u001f\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010 \u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u0018\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00162\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u001e\u0010\"\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010$\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006%"}, d2 = {"Lcom/tetris/modern/rl/data/dao/StatisticsDao;", "", "addLinesCleared", "", "mode", "", "lines", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addTimePlayed", "time", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteByMode", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteStatistics", "statistics", "Lcom/tetris/modern/rl/data/entities/StatisticsEntity;", "(Lcom/tetris/modern/rl/data/entities/StatisticsEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllStatistics", "Lkotlinx/coroutines/flow/Flow;", "", "getMostPlayedMode", "getOverallHighScore", "getStatisticsByMode", "getTotalGamesPlayed", "getTotalLinesCleared", "getTotalTimePlayed", "incrementGamesPlayed", "timestamp", "insertStatistics", "observeStatisticsByMode", "updateHighScore", "score", "updateStatistics", "app_release"})
@androidx.room.Dao
public abstract interface StatisticsDao {
    
    @androidx.room.Query(value = "SELECT * FROM statistics")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.StatisticsEntity>> getAllStatistics();
    
    @androidx.room.Query(value = "SELECT * FROM statistics WHERE gameMode = :mode")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getStatisticsByMode(@org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.tetris.modern.rl.data.entities.StatisticsEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM statistics WHERE gameMode = :mode")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.StatisticsEntity> observeStatisticsByMode(@org.jetbrains.annotations.NotNull
    java.lang.String mode);
    
    @androidx.room.Query(value = "SELECT SUM(totalGamesPlayed) FROM statistics")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getTotalGamesPlayed(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(totalLinesCleared) FROM statistics")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getTotalLinesCleared(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT MAX(highScore) FROM statistics")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getOverallHighScore(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(totalTimePlayed) FROM statistics")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getTotalTimePlayed(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT gameMode FROM statistics ORDER BY totalGamesPlayed DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMostPlayedMode(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.String> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertStatistics(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.StatisticsEntity statistics, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateStatistics(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.StatisticsEntity statistics, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE statistics SET totalGamesPlayed = totalGamesPlayed + 1, lastPlayedTimestamp = :timestamp WHERE gameMode = :mode")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object incrementGamesPlayed(@org.jetbrains.annotations.NotNull
    java.lang.String mode, long timestamp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE statistics SET totalLinesCleared = totalLinesCleared + :lines WHERE gameMode = :mode")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object addLinesCleared(@org.jetbrains.annotations.NotNull
    java.lang.String mode, int lines, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE statistics SET highScore = :score WHERE gameMode = :mode AND :score > highScore")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateHighScore(@org.jetbrains.annotations.NotNull
    java.lang.String mode, int score, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE statistics SET totalTimePlayed = totalTimePlayed + :time WHERE gameMode = :mode")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object addTimePlayed(@org.jetbrains.annotations.NotNull
    java.lang.String mode, long time, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteStatistics(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.StatisticsEntity statistics, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM statistics")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM statistics WHERE gameMode = :mode")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteByMode(@org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}