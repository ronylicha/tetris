package com.tetris.modern.rl.ui.screens;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.navigation.NavController;
import com.tetris.modern.rl.ui.theme.NeonColors;
import com.tetris.modern.rl.ui.viewmodels.MainViewModel;
import java.text.NumberFormat;
import java.util.Locale;
import kotlin.time.DurationUnit;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0007\u001aH\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\f\u00a2\u0006\u0002\b\u000e\u00a2\u0006\u0002\b\u000fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001a\u001a\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0007\u001a\u000e\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0019\u001a\u000e\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0019\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001b"}, d2 = {"StatItem", "", "label", "", "value", "StatsCard", "title", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "color", "Landroidx/compose/ui/graphics/Color;", "content", "Lkotlin/Function1;", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "StatsCard-9LQNqLg", "(Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;JLkotlin/jvm/functions/Function1;)V", "StatsScreen", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/tetris/modern/rl/ui/viewmodels/MainViewModel;", "formatPlayTime", "millis", "", "formatTime", "app_debug"})
public final class StatsScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void StatsScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void StatItem(@org.jetbrains.annotations.NotNull
    java.lang.String label, @org.jetbrains.annotations.NotNull
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String formatTime(long millis) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String formatPlayTime(long millis) {
        return null;
    }
}