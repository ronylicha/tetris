package com.tetris.modern.rl.data.repository;

import android.content.Context;
import com.google.gson.Gson;
import com.tetris.modern.rl.auth.AuthManager;
import com.tetris.modern.rl.data.dao.ProgressionDao;
import com.tetris.modern.rl.data.dao.ScoreDao;
import com.tetris.modern.rl.data.entities.ProgressionEntity;
import com.tetris.modern.rl.network.ApiService;
import com.tetris.modern.rl.network.models.*;
import com.tetris.modern.rl.progression.PlayerProgression;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B9\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u001bH\u0082@\u00a2\u0006\u0002\u0010\u0013J\u000e\u0010\u001c\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u000e\u0010\u001d\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u000e\u0010\u001e\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010!JD\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u0010%\u001a\u00020\u00182\u0006\u0010&\u001a\u00020\u00162\u0016\b\u0002\u0010\'\u001a\u0010\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0001\u0018\u00010(H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b)\u0010*R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006+"}, d2 = {"Lcom/tetris/modern/rl/data/repository/ProgressionRepository;", "", "context", "Landroid/content/Context;", "progressionDao", "Lcom/tetris/modern/rl/data/dao/ProgressionDao;", "scoreDao", "Lcom/tetris/modern/rl/data/dao/ScoreDao;", "apiService", "Lcom/tetris/modern/rl/network/ApiService;", "authManager", "Lcom/tetris/modern/rl/auth/AuthManager;", "playerProgression", "Lcom/tetris/modern/rl/progression/PlayerProgression;", "(Landroid/content/Context;Lcom/tetris/modern/rl/data/dao/ProgressionDao;Lcom/tetris/modern/rl/data/dao/ScoreDao;Lcom/tetris/modern/rl/network/ApiService;Lcom/tetris/modern/rl/auth/AuthManager;Lcom/tetris/modern/rl/progression/PlayerProgression;)V", "gson", "Lcom/google/gson/Gson;", "getProgression", "Lcom/tetris/modern/rl/data/entities/ProgressionEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUnlocksForLevel", "", "", "level", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pushProgressionToServer", "", "resetProgression", "saveProgression", "syncProgression", "syncWithAPI", "userId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProgression", "Lkotlin/Result;", "Lcom/tetris/modern/rl/network/models/ProgressionUpdateResponse;", "xpGained", "source", "gameData", "", "updateProgression-BWLJW6A", "(ILjava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ProgressionRepository {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.dao.ProgressionDao progressionDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.dao.ScoreDao scoreDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.network.ApiService apiService = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.auth.AuthManager authManager = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.progression.PlayerProgression playerProgression = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    
    @javax.inject.Inject
    public ProgressionRepository(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.dao.ProgressionDao progressionDao, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.dao.ScoreDao scoreDao, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.ApiService apiService, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.auth.AuthManager authManager, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.progression.PlayerProgression playerProgression) {
        super();
    }
    
    /**
     * Get current player progression
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getProgression(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.tetris.modern.rl.data.entities.ProgressionEntity> $completion) {
        return null;
    }
    
    /**
     * Save current progression state to database (without adding XP)
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object saveProgression(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Sync progression with server
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object syncProgression(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Push local progression to server
     */
    private final java.lang.Object pushProgressionToServer(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Sync progression with API - call this after each game
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object syncWithAPI(@org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get unlocks for a specific level
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getUnlocksForLevel(int level, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    /**
     * Reset progression
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object resetProgression(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}