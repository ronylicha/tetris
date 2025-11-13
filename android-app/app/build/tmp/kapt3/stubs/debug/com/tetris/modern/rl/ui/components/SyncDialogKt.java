package com.tetris.modern.rl.ui.components;

import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.window.DialogProperties;
import com.tetris.modern.rl.auth.SyncStrategy;
import com.tetris.modern.rl.sync.SyncManager;
import com.tetris.modern.rl.ui.theme.NeonColors;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0003\u001aJ\u0010\u0007\u001a\u00020\u00012\b\b\u0002\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0012H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a\u0018\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0003H\u0003\u001a\u0018\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\fH\u0003\u001a:\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\f2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00010\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0012H\u0007\u001a\u0010\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0005H\u0002\u001a\u0010\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020\"H\u0002\u001a\u0010\u0010#\u001a\u00020\u00032\u0006\u0010$\u001a\u00020\u0005H\u0002\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006%"}, d2 = {"ComparisonRow", "", "label", "", "localValue", "", "remoteValue", "DataCard", "modifier", "Landroidx/compose/ui/Modifier;", "title", "data", "Lcom/tetris/modern/rl/sync/SyncManager$SyncData;", "isSelected", "", "color", "Landroidx/compose/ui/graphics/Color;", "onClick", "Lkotlin/Function0;", "DataCard-jzV_Hc0", "(Landroidx/compose/ui/Modifier;Ljava/lang/String;Lcom/tetris/modern/rl/sync/SyncManager$SyncData;ZJLkotlin/jvm/functions/Function0;)V", "DataStat", "value", "DetailedComparison", "localData", "remoteData", "SyncConflictDialog", "onStrategySelected", "Lkotlin/Function1;", "Lcom/tetris/modern/rl/auth/SyncStrategy;", "onDismiss", "formatNumber", "formatScore", "score", "", "formatTime", "timestamp", "app_debug"})
public final class SyncDialogKt {
    
    @androidx.compose.runtime.Composable
    public static final void SyncConflictDialog(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.sync.SyncManager.SyncData localData, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.sync.SyncManager.SyncData remoteData, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.tetris.modern.rl.auth.SyncStrategy, kotlin.Unit> onStrategySelected, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void DataStat(java.lang.String label, java.lang.String value) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void DetailedComparison(com.tetris.modern.rl.sync.SyncManager.SyncData localData, com.tetris.modern.rl.sync.SyncManager.SyncData remoteData) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ComparisonRow(java.lang.String label, long localValue, long remoteValue) {
    }
    
    private static final java.lang.String formatScore(int score) {
        return null;
    }
    
    private static final java.lang.String formatNumber(long value) {
        return null;
    }
    
    private static final java.lang.String formatTime(long timestamp) {
        return null;
    }
}