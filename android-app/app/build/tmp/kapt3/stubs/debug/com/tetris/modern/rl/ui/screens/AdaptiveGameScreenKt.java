package com.tetris.modern.rl.ui.screens;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import com.tetris.modern.rl.ui.components.*;
import com.tetris.modern.rl.ui.viewmodels.MainViewModel;
import com.tetris.modern.rl.utils.DeviceUtils;
import com.tetris.modern.rl.utils.DeviceUtils.PanelArrangement;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001a6\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\u0016\u0010\t\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a.\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a \u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0012H\u0003\u001a.\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a>\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0004\u001a\u00020\u00052\u0014\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u0018H\u0003\u001a\u001a\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u001b\u001a\u00020\u001cH\u0003\u001a\u0018\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u0004\u001a\u00020\u0005H\u0003\u001a\u0010\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0018\u0010 \u001a\u00020\u00012\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\u0005H\u0003\u001a.\u0010#\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u00a8\u0006$"}, d2 = {"AdaptiveGameScreen", "", "viewModel", "Lcom/tetris/modern/rl/ui/viewmodels/MainViewModel;", "gameMode", "", "onBackToMenu", "Lkotlin/Function0;", "onBackToModeSelection", "BackButton", "onClick", "CompactHorizontalGameLayout", "gameState", "Lcom/tetris/modern/rl/game/models/GameState;", "layoutConfig", "Lcom/tetris/modern/rl/utils/DeviceUtils$GameLayoutConfig;", "CompactScoreDisplay", "score", "", "lines", "level", "ExtendedHorizontalGameLayout", "ExtendedStatsPanel", "modeInfo", "", "", "FloatingGameControls", "modifier", "Landroidx/compose/ui/Modifier;", "MiniLeaderboard", "currentScore", "MuteButton", "StatRow", "label", "value", "VerticalGameLayout", "app_debug"})
public final class AdaptiveGameScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void AdaptiveGameScreen(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackToMenu, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackToModeSelection) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void VerticalGameLayout(com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel, com.tetris.modern.rl.game.models.GameState gameState, com.tetris.modern.rl.utils.DeviceUtils.GameLayoutConfig layoutConfig, kotlin.jvm.functions.Function0<kotlin.Unit> onBackToMenu) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void CompactHorizontalGameLayout(com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel, com.tetris.modern.rl.game.models.GameState gameState, com.tetris.modern.rl.utils.DeviceUtils.GameLayoutConfig layoutConfig, kotlin.jvm.functions.Function0<kotlin.Unit> onBackToMenu) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ExtendedHorizontalGameLayout(com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel, com.tetris.modern.rl.game.models.GameState gameState, com.tetris.modern.rl.utils.DeviceUtils.GameLayoutConfig layoutConfig, kotlin.jvm.functions.Function0<kotlin.Unit> onBackToMenu) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void FloatingGameControls(com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void BackButton(kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void MuteButton(com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void CompactScoreDisplay(int score, int lines, int level) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ExtendedStatsPanel(int score, int lines, int level, java.lang.String gameMode, java.util.Map<java.lang.String, ? extends java.lang.Object> modeInfo) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void StatRow(java.lang.String label, java.lang.String value) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void MiniLeaderboard(int currentScore, java.lang.String gameMode) {
    }
}