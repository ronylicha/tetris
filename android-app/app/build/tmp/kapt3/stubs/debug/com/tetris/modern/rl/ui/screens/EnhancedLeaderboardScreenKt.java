package com.tetris.modern.rl.ui.screens;

import androidx.compose.animation.*;
import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.paging.compose.LazyPagingItems;
import com.tetris.modern.rl.auth.AuthManager;
import com.tetris.modern.rl.auth.SyncStrategy;
import com.tetris.modern.rl.data.entities.ScoreEntity;
import com.tetris.modern.rl.data.repository.ScoreRepository;
import com.tetris.modern.rl.sync.SyncManager;
import com.tetris.modern.rl.ui.theme.NeonColors;
import com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000x\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u001e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a@\u0010\n\u001a\u00020\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\bH\u0003\u001a4\u0010\u0014\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a\b\u0010\u001a\u001a\u00020\u0001H\u0003\u001a \u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 H\u0003\u001a`\u0010!\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010#\u001a\u00020$2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010&2\u0014\u0010\'\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\u00010&2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u00010&H\u0003\u001a:\u0010)\u001a\u00020\u00012\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020+2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020\u00010&2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a \u00100\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 H\u0003\u001a\u0010\u00101\u001a\u00020\b2\u0006\u00102\u001a\u000203H\u0002\u00a8\u00064"}, d2 = {"EnhancedLeaderboardScreen", "", "onBack", "Lkotlin/Function0;", "viewModel", "Lcom/tetris/modern/rl/viewmodel/EnhancedLeaderboardViewModel;", "ErrorContent", "error", "", "onRetry", "LeaderboardContent", "topScores", "", "Lcom/tetris/modern/rl/data/entities/ScoreEntity;", "pagedScores", "Landroidx/paging/compose/LazyPagingItems;", "scrollState", "Landroidx/compose/foundation/lazy/LazyListState;", "currentPlayerName", "selectedMode", "LeaderboardHeader", "authState", "Lcom/tetris/modern/rl/auth/AuthManager$AuthState;", "syncState", "Lcom/tetris/modern/rl/sync/SyncManager$SyncState;", "onSync", "LoadingContent", "RegularScoreCard", "rank", "", "score", "isCurrentPlayer", "", "SearchAndFilters", "searchQuery", "selectedPeriod", "Lcom/tetris/modern/rl/data/repository/ScoreRepository$TimePeriod;", "onSearchChange", "Lkotlin/Function1;", "onModeChange", "onPeriodChange", "SyncConflictDialog", "localData", "Lcom/tetris/modern/rl/sync/SyncManager$SyncData;", "remoteData", "onStrategySelected", "Lcom/tetris/modern/rl/auth/SyncStrategy;", "onDismiss", "TopScoreCard", "formatDate", "date", "Ljava/util/Date;", "app_debug"})
public final class EnhancedLeaderboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void EnhancedLeaderboardScreen(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void LeaderboardHeader(kotlin.jvm.functions.Function0<kotlin.Unit> onBack, com.tetris.modern.rl.auth.AuthManager.AuthState authState, com.tetris.modern.rl.sync.SyncManager.SyncState syncState, kotlin.jvm.functions.Function0<kotlin.Unit> onSync) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void SearchAndFilters(java.lang.String searchQuery, java.lang.String selectedMode, com.tetris.modern.rl.data.repository.ScoreRepository.TimePeriod selectedPeriod, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSearchChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onModeChange, kotlin.jvm.functions.Function1<? super com.tetris.modern.rl.data.repository.ScoreRepository.TimePeriod, kotlin.Unit> onPeriodChange) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void LeaderboardContent(java.util.List<com.tetris.modern.rl.data.entities.ScoreEntity> topScores, androidx.paging.compose.LazyPagingItems<com.tetris.modern.rl.data.entities.ScoreEntity> pagedScores, androidx.compose.foundation.lazy.LazyListState scrollState, java.lang.String currentPlayerName, java.lang.String selectedMode) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void TopScoreCard(int rank, com.tetris.modern.rl.data.entities.ScoreEntity score, boolean isCurrentPlayer) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void RegularScoreCard(int rank, com.tetris.modern.rl.data.entities.ScoreEntity score, boolean isCurrentPlayer) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void LoadingContent() {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ErrorContent(java.lang.String error, kotlin.jvm.functions.Function0<kotlin.Unit> onRetry) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void SyncConflictDialog(com.tetris.modern.rl.sync.SyncManager.SyncData localData, com.tetris.modern.rl.sync.SyncManager.SyncData remoteData, kotlin.jvm.functions.Function1<? super com.tetris.modern.rl.auth.SyncStrategy, kotlin.Unit> onStrategySelected, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    private static final java.lang.String formatDate(java.util.Date date) {
        return null;
    }
}