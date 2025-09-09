package com.tetris.modern.rl.ui.screens;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.text.font.FontWeight;
import com.tetris.modern.rl.game.TetrisEngine;
import com.tetris.modern.rl.game.models.Piece;
import com.tetris.modern.rl.game.models.PieceType;
import com.tetris.modern.rl.ui.viewmodels.MainViewModel;
import androidx.compose.ui.text.style.TextAlign;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\u001an\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u0003H\u0007\u001a6\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u001c\u0010\u0015\u001a\u00020\u00012\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a.\u0010\u0018\u001a\u00020\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a\u001a\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a.\u0010\u001b\u001a\u00020\u00012\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a\u0013\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0017\u00a2\u0006\u0002\u0010!\u001a\"\u0010\"\u001a\u00020\u0001*\u00020#2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%\u001a\"\u0010\'\u001a\u00020\u0001*\u00020#2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%\u001a\u001a\u0010(\u001a\u00020\u0001*\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%\u001aF\u0010)\u001a\u00020\u0001*\u00020#2\u0006\u0010*\u001a\u00020\u00032\u0006\u0010+\u001a\u00020\u00032\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%2\u0006\u0010,\u001a\u00020\u001f2\b\b\u0002\u0010-\u001a\u00020\u000b\u00f8\u0001\u0000\u00a2\u0006\u0004\b.\u0010/\u001a\"\u00100\u001a\u00020\u0001*\u00020#2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00061"}, d2 = {"GameOverOverlay", "", "score", "", "onRestart", "Lkotlin/Function0;", "onQuit", "onChangeMode", "modifier", "Landroidx/compose/ui/Modifier;", "isVictory", "", "gameMode", "", "lines", "level", "GameScreen", "viewModel", "Lcom/tetris/modern/rl/ui/viewmodels/MainViewModel;", "onBackToMenu", "onBackToModeSelection", "HoldPieceDisplay", "heldPiece", "Lcom/tetris/modern/rl/game/models/PieceType;", "PauseOverlay", "onResume", "TetrisGameCanvas", "TopGameBar", "onPause", "onBack", "getPieceColor", "Landroidx/compose/ui/graphics/Color;", "type", "(Lcom/tetris/modern/rl/game/models/PieceType;)J", "drawCurrentPiece", "Landroidx/compose/ui/graphics/drawscope/DrawScope;", "cellSize", "", "offsetY", "drawGhostPiece", "drawGrid", "drawPieceCell", "x", "y", "color", "isGhost", "drawPieceCell-hftG7rw", "(Landroidx/compose/ui/graphics/drawscope/DrawScope;IIFFJZ)V", "drawPlacedPieces", "app_debug"})
public final class GameScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void GameScreen(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackToMenu, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackToModeSelection) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void TetrisGameCanvas(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    public static final void drawGrid(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.graphics.drawscope.DrawScope $this$drawGrid, float cellSize, float offsetY) {
    }
    
    public static final void drawPlacedPieces(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.graphics.drawscope.DrawScope $this$drawPlacedPieces, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel, float cellSize, float offsetY) {
    }
    
    public static final void drawGhostPiece(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.graphics.drawscope.DrawScope $this$drawGhostPiece, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel, float cellSize, float offsetY) {
    }
    
    public static final void drawCurrentPiece(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.graphics.drawscope.DrawScope $this$drawCurrentPiece, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel, float cellSize, float offsetY) {
    }
    
    public static final long getPieceColor(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.game.models.PieceType type) {
        return 0L;
    }
    
    @androidx.compose.runtime.Composable
    public static final void HoldPieceDisplay(@org.jetbrains.annotations.Nullable
    com.tetris.modern.rl.game.models.PieceType heldPiece, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void TopGameBar(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onPause, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void PauseOverlay(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onResume, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onQuit, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void GameOverOverlay(int score, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onRestart, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onQuit, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onChangeMode, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier, boolean isVictory, @org.jetbrains.annotations.NotNull
    java.lang.String gameMode, int lines, int level) {
    }
}