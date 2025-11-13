package com.tetris.modern.rl.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;
import com.tetris.modern.rl.auth.AuthManager;
import com.tetris.modern.rl.auth.SyncStrategy;
import com.tetris.modern.rl.data.entities.ScoreEntity;
import com.tetris.modern.rl.data.repository.ScoreRepository;
import com.tetris.modern.rl.sync.SyncManager;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001:\u000201B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\"H\u0002J\b\u0010$\u001a\u00020\"H\u0002J\u0006\u0010%\u001a\u00020\"J\u000e\u0010&\u001a\u00020\"2\u0006\u0010\'\u001a\u00020(J\u0010\u0010)\u001a\u00020\"2\b\u0010*\u001a\u0004\u0018\u00010\u000bJ\u000e\u0010+\u001a\u00020\"2\u0006\u0010,\u001a\u00020\u000eJ\u0006\u0010-\u001a\u00020\"J\u000e\u0010.\u001a\u00020\"2\u0006\u0010/\u001a\u00020\u000bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00180\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0015\u00a8\u00062"}, d2 = {"Lcom/tetris/modern/rl/viewmodel/EnhancedLeaderboardViewModel;", "Landroidx/lifecycle/ViewModel;", "scoreRepository", "Lcom/tetris/modern/rl/data/repository/ScoreRepository;", "authManager", "Lcom/tetris/modern/rl/auth/AuthManager;", "syncManager", "Lcom/tetris/modern/rl/sync/SyncManager;", "(Lcom/tetris/modern/rl/data/repository/ScoreRepository;Lcom/tetris/modern/rl/auth/AuthManager;Lcom/tetris/modern/rl/sync/SyncManager;)V", "_searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_selectedMode", "_selectedPeriod", "Lcom/tetris/modern/rl/data/repository/ScoreRepository$TimePeriod;", "_uiState", "Lcom/tetris/modern/rl/viewmodel/EnhancedLeaderboardViewModel$LeaderboardUiState;", "authState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/tetris/modern/rl/auth/AuthManager$AuthState;", "getAuthState", "()Lkotlinx/coroutines/flow/StateFlow;", "pagedScores", "Lkotlinx/coroutines/flow/Flow;", "Landroidx/paging/PagingData;", "Lcom/tetris/modern/rl/data/entities/ScoreEntity;", "getPagedScores", "()Lkotlinx/coroutines/flow/Flow;", "syncState", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState;", "getSyncState", "uiState", "getUiState", "loadPlayerStats", "", "loadTop10", "observeFilters", "refresh", "resolveSyncConflict", "strategy", "Lcom/tetris/modern/rl/auth/SyncStrategy;", "selectMode", "mode", "selectPeriod", "period", "syncScores", "updateSearch", "query", "LeaderboardUiState", "PlayerStatsUi", "app_debug"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class, kotlinx.coroutines.FlowPreview.class})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class EnhancedLeaderboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.repository.ScoreRepository scoreRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.auth.AuthManager authManager = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.sync.SyncManager syncManager = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel.LeaderboardUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel.LeaderboardUiState> uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.auth.AuthManager.AuthState> authState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.sync.SyncManager.SyncState> syncState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedMode = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.tetris.modern.rl.data.repository.ScoreRepository.TimePeriod> _selectedPeriod = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.tetris.modern.rl.data.entities.ScoreEntity>> pagedScores = null;
    
    @javax.inject.Inject
    public EnhancedLeaderboardViewModel(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.repository.ScoreRepository scoreRepository, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.auth.AuthManager authManager, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.sync.SyncManager syncManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel.LeaderboardUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.auth.AuthManager.AuthState> getAuthState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.sync.SyncManager.SyncState> getSyncState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.tetris.modern.rl.data.entities.ScoreEntity>> getPagedScores() {
        return null;
    }
    
    private final void observeFilters() {
    }
    
    private final void loadTop10() {
    }
    
    private final void loadPlayerStats() {
    }
    
    public final void updateSearch(@org.jetbrains.annotations.NotNull
    java.lang.String query) {
    }
    
    public final void selectMode(@org.jetbrains.annotations.Nullable
    java.lang.String mode) {
    }
    
    public final void selectPeriod(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.repository.ScoreRepository.TimePeriod period) {
    }
    
    public final void refresh() {
    }
    
    public final void syncScores() {
    }
    
    public final void resolveSyncConflict(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.auth.SyncStrategy strategy) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BW\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J[\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00052\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eH\u00c6\u0001J\u0013\u0010#\u001a\u00020\u00032\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010\'\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0012R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006("}, d2 = {"Lcom/tetris/modern/rl/viewmodel/EnhancedLeaderboardViewModel$LeaderboardUiState;", "", "isLoading", "", "error", "", "selectedMode", "selectedPeriod", "Lcom/tetris/modern/rl/data/repository/ScoreRepository$TimePeriod;", "searchQuery", "top10Scores", "", "Lcom/tetris/modern/rl/data/entities/ScoreEntity;", "playerStats", "Lcom/tetris/modern/rl/viewmodel/EnhancedLeaderboardViewModel$PlayerStatsUi;", "(ZLjava/lang/String;Ljava/lang/String;Lcom/tetris/modern/rl/data/repository/ScoreRepository$TimePeriod;Ljava/lang/String;Ljava/util/List;Lcom/tetris/modern/rl/viewmodel/EnhancedLeaderboardViewModel$PlayerStatsUi;)V", "getError", "()Ljava/lang/String;", "()Z", "getPlayerStats", "()Lcom/tetris/modern/rl/viewmodel/EnhancedLeaderboardViewModel$PlayerStatsUi;", "getSearchQuery", "getSelectedMode", "getSelectedPeriod", "()Lcom/tetris/modern/rl/data/repository/ScoreRepository$TimePeriod;", "getTop10Scores", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class LeaderboardUiState {
        private final boolean isLoading = false;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String error = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String selectedMode = null;
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.data.repository.ScoreRepository.TimePeriod selectedPeriod = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String searchQuery = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity> top10Scores = null;
        @org.jetbrains.annotations.Nullable
        private final com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel.PlayerStatsUi playerStats = null;
        
        public LeaderboardUiState(boolean isLoading, @org.jetbrains.annotations.Nullable
        java.lang.String error, @org.jetbrains.annotations.Nullable
        java.lang.String selectedMode, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.data.repository.ScoreRepository.TimePeriod selectedPeriod, @org.jetbrains.annotations.NotNull
        java.lang.String searchQuery, @org.jetbrains.annotations.NotNull
        java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity> top10Scores, @org.jetbrains.annotations.Nullable
        com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel.PlayerStatsUi playerStats) {
            super();
        }
        
        public final boolean isLoading() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getError() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getSelectedMode() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.data.repository.ScoreRepository.TimePeriod getSelectedPeriod() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getSearchQuery() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity> getTop10Scores() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel.PlayerStatsUi getPlayerStats() {
            return null;
        }
        
        public LeaderboardUiState() {
            super();
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.data.repository.ScoreRepository.TimePeriod component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity> component6() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel.PlayerStatsUi component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel.LeaderboardUiState copy(boolean isLoading, @org.jetbrains.annotations.Nullable
        java.lang.String error, @org.jetbrains.annotations.Nullable
        java.lang.String selectedMode, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.data.repository.ScoreRepository.TimePeriod selectedPeriod, @org.jetbrains.annotations.NotNull
        java.lang.String searchQuery, @org.jetbrains.annotations.NotNull
        java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity> top10Scores, @org.jetbrains.annotations.Nullable
        com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel.PlayerStatsUi playerStats) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r\u00a8\u0006\u001d"}, d2 = {"Lcom/tetris/modern/rl/viewmodel/EnhancedLeaderboardViewModel$PlayerStatsUi;", "", "rank", "", "gamesPlayed", "averageScore", "", "bestScore", "totalLines", "(IIDII)V", "getAverageScore", "()D", "getBestScore", "()I", "getGamesPlayed", "getRank", "getTotalLines", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class PlayerStatsUi {
        private final int rank = 0;
        private final int gamesPlayed = 0;
        private final double averageScore = 0.0;
        private final int bestScore = 0;
        private final int totalLines = 0;
        
        public PlayerStatsUi(int rank, int gamesPlayed, double averageScore, int bestScore, int totalLines) {
            super();
        }
        
        public final int getRank() {
            return 0;
        }
        
        public final int getGamesPlayed() {
            return 0;
        }
        
        public final double getAverageScore() {
            return 0.0;
        }
        
        public final int getBestScore() {
            return 0;
        }
        
        public final int getTotalLines() {
            return 0;
        }
        
        public final int component1() {
            return 0;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        public final int component4() {
            return 0;
        }
        
        public final int component5() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel.PlayerStatsUi copy(int rank, int gamesPlayed, double averageScore, int bestScore, int totalLines) {
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
}