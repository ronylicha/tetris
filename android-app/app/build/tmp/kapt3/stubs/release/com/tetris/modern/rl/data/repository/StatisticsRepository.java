package com.tetris.modern.rl.data.repository;

import com.tetris.modern.rl.data.dao.StatisticsDao;
import com.tetris.modern.rl.data.entities.StatisticsEntity;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u000e\u0010\u0010\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u00132\u0006\u0010\u0007\u001a\u00020\bJ\u0018\u0010\u0015\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0016J\u000e\u0010\u0017\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u000e\u0010\u0018\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0016J^\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010$J\u001e\u0010%\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010&\u001a\u00020\u00062\u0006\u0010\'\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010(R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/tetris/modern/rl/data/repository/StatisticsRepository;", "", "statisticsDao", "Lcom/tetris/modern/rl/data/dao/StatisticsDao;", "(Lcom/tetris/modern/rl/data/dao/StatisticsDao;)V", "addLinesCleared", "", "mode", "", "lines", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addTimePlayed", "time", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOverallHighScore", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStatisticsByMode", "Lkotlinx/coroutines/flow/Flow;", "Lcom/tetris/modern/rl/data/entities/StatisticsEntity;", "getStatisticsForMode", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTotalGamesPlayed", "getTotalLinesCleared", "incrementGamesPlayed", "recordGameStats", "gameMode", "score", "playTime", "pieces", "tetrises", "tSpins", "perfectClears", "maxCombo", "level", "(Ljava/lang/String;IIJIIIIIILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateHighScore", "updateStatistics", "statistics", "(Lcom/tetris/modern/rl/data/entities/StatisticsEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public final class StatisticsRepository {
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.dao.StatisticsDao statisticsDao = null;
    
    @javax.inject.Inject
    public StatisticsRepository(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.dao.StatisticsDao statisticsDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.StatisticsEntity> getStatisticsByMode(@org.jetbrains.annotations.NotNull
    java.lang.String mode) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getStatisticsForMode(@org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.tetris.modern.rl.data.entities.StatisticsEntity> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateStatistics(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.StatisticsEntity statistics, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object recordGameStats(@org.jetbrains.annotations.NotNull
    java.lang.String gameMode, int lines, int score, long playTime, int pieces, int tetrises, int tSpins, int perfectClears, int maxCombo, int level, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object incrementGamesPlayed(@org.jetbrains.annotations.NotNull
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateHighScore(@org.jetbrains.annotations.NotNull
    java.lang.String mode, int score, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object addLinesCleared(@org.jetbrains.annotations.NotNull
    java.lang.String mode, int lines, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object addTimePlayed(@org.jetbrains.annotations.NotNull
    java.lang.String mode, long time, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getTotalGamesPlayed(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getTotalLinesCleared(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getOverallHighScore(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
}