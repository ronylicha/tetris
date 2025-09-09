package com.tetris.modern.rl.data.repository;

import com.tetris.modern.rl.data.dao.ScoreDao;
import com.tetris.modern.rl.data.entities.ScoreEntity;
import com.tetris.modern.rl.network.ApiService;
import com.tetris.modern.rl.network.NoConnectivityException;
import com.tetris.modern.rl.network.models.*;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ.\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001a\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f0\u00132\u0006\u0010\u0015\u001a\u00020\u000fJ(\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\f0\u00132\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u0018J$\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000b2\u0006\u0010\u001b\u001a\u00020\u0014H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u000e\u0010\u001e\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001f"}, d2 = {"Lcom/tetris/modern/rl/data/repository/ScoreRepository;", "", "scoreDao", "Lcom/tetris/modern/rl/data/dao/ScoreDao;", "apiService", "Lcom/tetris/modern/rl/network/ApiService;", "(Lcom/tetris/modern/rl/data/dao/ScoreDao;Lcom/tetris/modern/rl/network/ApiService;)V", "cleanOldScores", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchLeaderboard", "Lkotlin/Result;", "", "Lcom/tetris/modern/rl/network/models/LeaderboardEntry;", "gameMode", "", "fetchLeaderboard-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlayerScores", "Lkotlinx/coroutines/flow/Flow;", "Lcom/tetris/modern/rl/data/entities/ScoreEntity;", "playerName", "getTopScores", "limit", "", "saveScore", "", "score", "saveScore-gIAlu-s", "(Lcom/tetris/modern/rl/data/entities/ScoreEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncScores", "app_debug"})
public final class ScoreRepository {
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.dao.ScoreDao scoreDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.network.ApiService apiService = null;
    
    @javax.inject.Inject
    public ScoreRepository(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.dao.ScoreDao scoreDao, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.ApiService apiService) {
        super();
    }
    
    /**
     * Get top scores with offline fallback
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getTopScores(@org.jetbrains.annotations.Nullable
    java.lang.String gameMode, int limit) {
        return null;
    }
    
    /**
     * Sync unsynced scores with server
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object syncScores(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get player's personal scores
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity>> getPlayerScores(@org.jetbrains.annotations.NotNull
    java.lang.String playerName) {
        return null;
    }
    
    /**
     * Clean up old scores (older than 30 days)
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object cleanOldScores(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}