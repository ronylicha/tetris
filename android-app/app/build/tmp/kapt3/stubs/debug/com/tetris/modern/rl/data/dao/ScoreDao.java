package com.tetris.modern.rl.data.dao;

import androidx.paging.PagingSource;
import androidx.room.*;
import com.tetris.modern.rl.data.entities.ScoreEntity;
import kotlinx.coroutines.flow.Flow;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\n\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00100\u000fH\'J\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00100\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\'J\"\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u001c\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00100\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\'J\"\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u001bJ2\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00100\u000f2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u0015H\'J,\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\f0!2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u0013H\'J \u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00100\u000f2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0013H\'J4\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00100\u000f2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00132\b\b\u0002\u0010\u001f\u001a\u00020\u00152\b\b\u0002\u0010%\u001a\u00020\u0015H\'J\u0014\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00100\u000fH\'J\u0016\u0010\'\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ&\u0010(\u001a\u00020)2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010*J\u0016\u0010+\u001a\u00020\u00032\u0006\u0010,\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010+\u001a\u00020\u00032\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\b0\u0010H\u00a7@\u00a2\u0006\u0002\u0010.J\u001c\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00130\u00102\u0006\u00100\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u00101J\u0016\u00102\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\r\u00a8\u00063"}, d2 = {"Lcom/tetris/modern/rl/data/dao/ScoreDao;", "", "deleteAllScores", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllSyncedScores", "deleteOldScores", "timestamp", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteScore", "score", "Lcom/tetris/modern/rl/data/entities/ScoreEntity;", "(Lcom/tetris/modern/rl/data/entities/ScoreEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllScores", "Lkotlinx/coroutines/flow/Flow;", "", "getPlayerBestScores", "playerName", "", "getPlayerRank", "", "gameMode", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlayerScores", "getPlayerStats", "Lcom/tetris/modern/rl/data/dao/PlayerStats;", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getScoresByPeriod", "startDate", "Ljava/util/Date;", "limit", "getScoresPaged", "Landroidx/paging/PagingSource;", "searchQuery", "getTop10", "getTopScores", "offset", "getUnsyncedScores", "insertScore", "isPersonalBest", "", "(Ljava/lang/String;Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markAsSynced", "id", "ids", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchPlayers", "query", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateScore", "app_debug"})
@androidx.room.Dao
public abstract interface ScoreDao {
    
    @androidx.room.Query(value = "\n        SELECT * FROM scores \n        WHERE (:gameMode IS NULL OR gameMode = :gameMode)\n        ORDER BY score DESC \n        LIMIT :limit OFFSET :offset\n    ")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getTopScores(@org.jetbrains.annotations.Nullable
    java.lang.String gameMode, int limit, int offset);
    
    @androidx.room.Query(value = "\n        SELECT * FROM scores \n        WHERE (:gameMode IS NULL OR gameMode = :gameMode)\n        AND (:searchQuery IS NULL OR playerName LIKE \'%\' || :searchQuery || \'%\')\n        ORDER BY score DESC\n    ")
    @org.jetbrains.annotations.NotNull
    public abstract androidx.paging.PagingSource<java.lang.Integer, com.tetris.modern.rl.data.entities.ScoreEntity> getScoresPaged(@org.jetbrains.annotations.Nullable
    java.lang.String gameMode, @org.jetbrains.annotations.Nullable
    java.lang.String searchQuery);
    
    @androidx.room.Query(value = "\n        SELECT * FROM scores \n        WHERE (:gameMode IS NULL OR gameMode = :gameMode)\n        AND date >= :startDate\n        ORDER BY score DESC \n        LIMIT :limit\n    ")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getScoresByPeriod(@org.jetbrains.annotations.Nullable
    java.lang.String gameMode, @org.jetbrains.annotations.NotNull
    java.util.Date startDate, int limit);
    
    @androidx.room.Query(value = "\n        SELECT * FROM scores \n        WHERE playerName = :playerName\n        ORDER BY score DESC\n    ")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getPlayerScores(@org.jetbrains.annotations.NotNull
    java.lang.String playerName);
    
    @androidx.room.Query(value = "\n        SELECT * FROM scores s1\n        WHERE s1.playerName = :playerName\n        AND s1.score = (\n            SELECT MAX(s2.score) \n            FROM scores s2 \n            WHERE s2.playerName = s1.playerName \n            AND s2.gameMode = s1.gameMode\n        )\n        ORDER BY s1.gameMode\n    ")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getPlayerBestScores(@org.jetbrains.annotations.NotNull
    java.lang.String playerName);
    
    @androidx.room.Query(value = "SELECT * FROM scores")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getAllScores();
    
    @androidx.room.Query(value = "\n        SELECT COUNT(*) + 1 \n        FROM scores \n        WHERE score > :score \n        AND (:gameMode IS NULL OR gameMode = :gameMode)\n    ")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getPlayerRank(int score, @org.jetbrains.annotations.Nullable
    java.lang.String gameMode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "\n        SELECT DISTINCT playerName \n        FROM scores \n        WHERE playerName LIKE \'%\' || :query || \'%\'\n        LIMIT 10\n    ")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object searchPlayers(@org.jetbrains.annotations.NotNull
    java.lang.String query, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
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
    
    @androidx.room.Query(value = "DELETE FROM scores WHERE isSynced = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteAllSyncedScores(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM scores WHERE isSynced = 0")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getUnsyncedScores();
    
    @androidx.room.Query(value = "UPDATE scores SET isSynced = 1 WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsSynced(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE scores SET isSynced = 1 WHERE id IN (:ids)")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsSynced(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Long> ids, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM scores WHERE timestamp < :timestamp")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteOldScores(long timestamp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        SELECT \n            COUNT(*) as gamesPlayed,\n            SUM(score) as totalScore,\n            MAX(score) as highestScore,\n            SUM(lines) as totalLines,\n            AVG(score) as averageScore\n        FROM scores \n        WHERE playerName = :playerName\n        AND (:gameMode IS NULL OR gameMode = :gameMode)\n    ")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getPlayerStats(@org.jetbrains.annotations.NotNull
    java.lang.String playerName, @org.jetbrains.annotations.Nullable
    java.lang.String gameMode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.tetris.modern.rl.data.dao.PlayerStats> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM scores \n        WHERE (:gameMode IS NULL OR gameMode = :gameMode)\n        ORDER BY score DESC \n        LIMIT 10\n    ")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getTop10(@org.jetbrains.annotations.Nullable
    java.lang.String gameMode);
    
    @androidx.room.Query(value = "\n        SELECT COUNT(*) = 0 \n        FROM scores \n        WHERE playerName = :playerName \n        AND gameMode = :gameMode \n        AND score > :score\n    ")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object isPersonalBest(@org.jetbrains.annotations.NotNull
    java.lang.String playerName, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, int score, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}