package com.tetris.modern.rl.sync;

import android.content.Context;
import androidx.work.*;
import com.tetris.modern.rl.auth.AuthManager;
import com.tetris.modern.rl.auth.SyncStrategy;
import com.tetris.modern.rl.data.dao.ScoreDao;
import com.tetris.modern.rl.data.entities.ScoreEntity;
import com.tetris.modern.rl.network.ApiService;
import com.tetris.modern.rl.network.models.*;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001:\u000212B)\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0006\u0010\u0016\u001a\u00020\u0017J\u0016\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0082@\u00a2\u0006\u0002\u0010\u001bJ\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u001aH\u0002J\u001e\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\u001aH\u0082@\u00a2\u0006\u0002\u0010\"J&\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000f0$2\b\b\u0002\u0010%\u001a\u00020&H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\'\u0010(J\u0016\u0010)\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&H\u0086@\u00a2\u0006\u0002\u0010(J\b\u0010*\u001a\u00020\u0017H\u0002J*\u0010+\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\u001a2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00170-J\u000e\u0010.\u001a\u00020\u0017H\u0082@\u00a2\u0006\u0002\u0010/J\u0016\u00100\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u001aH\u0082@\u00a2\u0006\u0002\u0010\u001bR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00063"}, d2 = {"Lcom/tetris/modern/rl/sync/SyncManager;", "", "context", "Landroid/content/Context;", "apiService", "Lcom/tetris/modern/rl/network/ApiService;", "scoreDao", "Lcom/tetris/modern/rl/data/dao/ScoreDao;", "authManager", "Lcom/tetris/modern/rl/auth/AuthManager;", "(Landroid/content/Context;Lcom/tetris/modern/rl/network/ApiService;Lcom/tetris/modern/rl/data/dao/ScoreDao;Lcom/tetris/modern/rl/auth/AuthManager;)V", "_lastSyncTime", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_syncState", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState;", "lastSyncTime", "Lkotlinx/coroutines/flow/StateFlow;", "getLastSyncTime", "()Lkotlinx/coroutines/flow/StateFlow;", "syncState", "getSyncState", "clearSyncState", "", "downloadRemoteData", "remoteData", "Lcom/tetris/modern/rl/sync/SyncManager$SyncData;", "(Lcom/tetris/modern/rl/sync/SyncManager$SyncData;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasConflict", "", "local", "remote", "mergeData", "localData", "(Lcom/tetris/modern/rl/sync/SyncManager$SyncData;Lcom/tetris/modern/rl/sync/SyncManager$SyncData;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "performSync", "Lkotlin/Result;", "strategy", "Lcom/tetris/modern/rl/auth/SyncStrategy;", "performSync-gIAlu-s", "(Lcom/tetris/modern/rl/auth/SyncStrategy;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolveSyncConflict", "schedulePeriodicSync", "showSyncConflictDialog", "onStrategySelected", "Lkotlin/Function1;", "syncUnsyncedScores", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadLocalData", "SyncData", "SyncState", "app_debug"})
public final class SyncManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.network.ApiService apiService = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.dao.ScoreDao scoreDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.auth.AuthManager authManager = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.tetris.modern.rl.sync.SyncManager.SyncState> _syncState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.sync.SyncManager.SyncState> syncState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _lastSyncTime = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> lastSyncTime = null;
    
    @javax.inject.Inject
    public SyncManager(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.ApiService apiService, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.dao.ScoreDao scoreDao, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.auth.AuthManager authManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.sync.SyncManager.SyncState> getSyncState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getLastSyncTime() {
        return null;
    }
    
    private final void schedulePeriodicSync() {
    }
    
    private final boolean hasConflict(com.tetris.modern.rl.sync.SyncManager.SyncData local, com.tetris.modern.rl.sync.SyncManager.SyncData remote) {
        return false;
    }
    
    private final java.lang.Object uploadLocalData(com.tetris.modern.rl.sync.SyncManager.SyncData localData, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object downloadRemoteData(com.tetris.modern.rl.sync.SyncManager.SyncData remoteData, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object mergeData(com.tetris.modern.rl.sync.SyncManager.SyncData localData, com.tetris.modern.rl.sync.SyncManager.SyncData remoteData, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object syncUnsyncedScores(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void showSyncConflictDialog(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.sync.SyncManager.SyncData localData, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.sync.SyncManager.SyncData remoteData, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.tetris.modern.rl.auth.SyncStrategy, kotlin.Unit> onStrategySelected) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object resolveSyncConflict(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.auth.SyncStrategy strategy, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void clearSyncState() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\fJ\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001a\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0006H\u00c6\u0003JK\u0010\u001c\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020\bH\u00d6\u0001J\t\u0010!\u001a\u00020\"H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011\u00a8\u0006#"}, d2 = {"Lcom/tetris/modern/rl/sync/SyncManager$SyncData;", "", "scores", "", "Lcom/tetris/modern/rl/data/entities/ScoreEntity;", "totalScore", "", "gamesPlayed", "", "highestScore", "totalLines", "lastSyncTime", "(Ljava/util/List;JIIIJ)V", "getGamesPlayed", "()I", "getHighestScore", "getLastSyncTime", "()J", "getScores", "()Ljava/util/List;", "getTotalLines", "getTotalScore", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class SyncData {
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity> scores = null;
        private final long totalScore = 0L;
        private final int gamesPlayed = 0;
        private final int highestScore = 0;
        private final int totalLines = 0;
        private final long lastSyncTime = 0L;
        
        public SyncData(@org.jetbrains.annotations.NotNull
        java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity> scores, long totalScore, int gamesPlayed, int highestScore, int totalLines, long lastSyncTime) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity> getScores() {
            return null;
        }
        
        public final long getTotalScore() {
            return 0L;
        }
        
        public final int getGamesPlayed() {
            return 0;
        }
        
        public final int getHighestScore() {
            return 0;
        }
        
        public final int getTotalLines() {
            return 0;
        }
        
        public final long getLastSyncTime() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity> component1() {
            return null;
        }
        
        public final long component2() {
            return 0L;
        }
        
        public final int component3() {
            return 0;
        }
        
        public final int component4() {
            return 0;
        }
        
        public final int component5() {
            return 0;
        }
        
        public final long component6() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.sync.SyncManager.SyncData copy(@org.jetbrains.annotations.NotNull
        java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity> scores, long totalScore, int gamesPlayed, int highestScore, int totalLines, long lastSyncTime) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f\u00a8\u0006\r"}, d2 = {"Lcom/tetris/modern/rl/sync/SyncManager$SyncState;", "", "()V", "Conflict", "Error", "Idle", "Success", "Syncing", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState$Conflict;", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState$Error;", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState$Idle;", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState$Success;", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState$Syncing;", "app_debug"})
    public static abstract class SyncState {
        
        private SyncState() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"Lcom/tetris/modern/rl/sync/SyncManager$SyncState$Conflict;", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState;", "localData", "Lcom/tetris/modern/rl/sync/SyncManager$SyncData;", "remoteData", "(Lcom/tetris/modern/rl/sync/SyncManager$SyncData;Lcom/tetris/modern/rl/sync/SyncManager$SyncData;)V", "getLocalData", "()Lcom/tetris/modern/rl/sync/SyncManager$SyncData;", "getRemoteData", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class Conflict extends com.tetris.modern.rl.sync.SyncManager.SyncState {
            @org.jetbrains.annotations.NotNull
            private final com.tetris.modern.rl.sync.SyncManager.SyncData localData = null;
            @org.jetbrains.annotations.NotNull
            private final com.tetris.modern.rl.sync.SyncManager.SyncData remoteData = null;
            
            public Conflict(@org.jetbrains.annotations.NotNull
            com.tetris.modern.rl.sync.SyncManager.SyncData localData, @org.jetbrains.annotations.NotNull
            com.tetris.modern.rl.sync.SyncManager.SyncData remoteData) {
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.tetris.modern.rl.sync.SyncManager.SyncData getLocalData() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.tetris.modern.rl.sync.SyncManager.SyncData getRemoteData() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.tetris.modern.rl.sync.SyncManager.SyncData component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.tetris.modern.rl.sync.SyncManager.SyncData component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.tetris.modern.rl.sync.SyncManager.SyncState.Conflict copy(@org.jetbrains.annotations.NotNull
            com.tetris.modern.rl.sync.SyncManager.SyncData localData, @org.jetbrains.annotations.NotNull
            com.tetris.modern.rl.sync.SyncManager.SyncData remoteData) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/tetris/modern/rl/sync/SyncManager$SyncState$Error;", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class Error extends com.tetris.modern.rl.sync.SyncManager.SyncState {
            @org.jetbrains.annotations.NotNull
            private final java.lang.String message = null;
            
            public Error(@org.jetbrains.annotations.NotNull
            java.lang.String message) {
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String getMessage() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.tetris.modern.rl.sync.SyncManager.SyncState.Error copy(@org.jetbrains.annotations.NotNull
            java.lang.String message) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/tetris/modern/rl/sync/SyncManager$SyncState$Idle;", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState;", "()V", "app_debug"})
        public static final class Idle extends com.tetris.modern.rl.sync.SyncManager.SyncState {
            @org.jetbrains.annotations.NotNull
            public static final com.tetris.modern.rl.sync.SyncManager.SyncState.Idle INSTANCE = null;
            
            private Idle() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/tetris/modern/rl/sync/SyncManager$SyncState$Success;", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class Success extends com.tetris.modern.rl.sync.SyncManager.SyncState {
            @org.jetbrains.annotations.NotNull
            private final java.lang.String message = null;
            
            public Success(@org.jetbrains.annotations.NotNull
            java.lang.String message) {
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String getMessage() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.tetris.modern.rl.sync.SyncManager.SyncState.Success copy(@org.jetbrains.annotations.NotNull
            java.lang.String message) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/tetris/modern/rl/sync/SyncManager$SyncState$Syncing;", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState;", "()V", "app_debug"})
        public static final class Syncing extends com.tetris.modern.rl.sync.SyncManager.SyncState {
            @org.jetbrains.annotations.NotNull
            public static final com.tetris.modern.rl.sync.SyncManager.SyncState.Syncing INSTANCE = null;
            
            private Syncing() {
            }
        }
    }
}