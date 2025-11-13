package com.tetris.modern.rl.data.repository;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import com.tetris.modern.rl.auth.AuthManager;
import com.tetris.modern.rl.data.dao.PlayerStats;
import com.tetris.modern.rl.data.dao.ScoreDao;
import com.tetris.modern.rl.data.entities.ScoreEntity;
import com.tetris.modern.rl.network.ApiService;
import com.tetris.modern.rl.network.NoConnectivityException;
import com.tetris.modern.rl.network.models.*;
import com.tetris.modern.rl.sync.SyncManager;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import timber.log.Timber;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\t\u0010\u0016\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0017\u001a\u00020\bH\u00c6\u0003JB\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u0019J\u0013\u0010\u001a\u001a\u00020\b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001f"}, d2 = {"Lcom/tetris/modern/rl/data/repository/ScoreSubmitResult;", "", "localId", "", "localRank", "", "serverRank", "isPersonalBest", "", "isGlobalRecord", "(JILjava/lang/Integer;ZZ)V", "()Z", "getLocalId", "()J", "getLocalRank", "()I", "getServerRank", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "component1", "component2", "component3", "component4", "component5", "copy", "(JILjava/lang/Integer;ZZ)Lcom/tetris/modern/rl/data/repository/ScoreSubmitResult;", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class ScoreSubmitResult {
    private final long localId = 0L;
    private final int localRank = 0;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer serverRank = null;
    private final boolean isPersonalBest = false;
    private final boolean isGlobalRecord = false;
    
    public ScoreSubmitResult(long localId, int localRank, @org.jetbrains.annotations.Nullable
    java.lang.Integer serverRank, boolean isPersonalBest, boolean isGlobalRecord) {
        super();
    }
    
    public final long getLocalId() {
        return 0L;
    }
    
    public final int getLocalRank() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getServerRank() {
        return null;
    }
    
    public final boolean isPersonalBest() {
        return false;
    }
    
    public final boolean isGlobalRecord() {
        return false;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.repository.ScoreSubmitResult copy(long localId, int localRank, @org.jetbrains.annotations.Nullable
    java.lang.Integer serverRank, boolean isPersonalBest, boolean isGlobalRecord) {
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