package com.tetris.modern.rl.ui.screens;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.navigation.NavController;
import com.tetris.modern.rl.data.entities.ScoreEntity;
import com.tetris.modern.rl.ui.theme.*;
import com.tetris.modern.rl.ui.viewmodels.MainViewModel;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0018\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007\u001a\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r\u00a8\u0006\u000e"}, d2 = {"LeaderboardScreen", "", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/tetris/modern/rl/ui/viewmodels/MainViewModel;", "ScoreCard", "rank", "", "score", "Lcom/tetris/modern/rl/data/entities/ScoreEntity;", "getTabIndex", "mode", "", "app_debug"})
public final class LeaderboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void LeaderboardScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ScoreCard(int rank, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.ScoreEntity score) {
    }
    
    public static final int getTabIndex(@org.jetbrains.annotations.NotNull
    java.lang.String mode) {
        return 0;
    }
}