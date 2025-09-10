package com.tetris.modern.rl.ui.screens;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.navigation.NavController;
import com.tetris.modern.rl.R;
import com.tetris.modern.rl.ui.theme.*;
import com.tetris.modern.rl.ui.viewmodels.MainViewModel;
import com.tetris.modern.rl.ui.viewmodels.SettingsViewModel;
import android.app.Activity;
import android.content.Intent;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0007\u001a$\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u001a.\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u00032\u001c\u0010\u000e\u001a\u0018\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u000f\u00a2\u0006\u0002\b\u0011\u00a2\u0006\u0002\b\u0012H\u0007\u001aF\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00142\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u000fH\u0007\u001a,\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00010\u000fH\u0007\u001a,\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00142\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u000fH\u0007\u00a8\u0006\u001f"}, d2 = {"InfoRow", "", "label", "", "value", "SettingsScreen", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/tetris/modern/rl/ui/viewmodels/MainViewModel;", "settingsViewModel", "Lcom/tetris/modern/rl/ui/viewmodels/SettingsViewModel;", "SettingsSection", "title", "content", "Lkotlin/Function1;", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "SliderSetting", "", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "steps", "", "onValueChange", "SwitchSetting", "checked", "", "onCheckedChange", "VolumeSlider", "app_debug"})
public final class SettingsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.viewmodels.SettingsViewModel settingsViewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SettingsSection(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void VolumeSlider(@org.jetbrains.annotations.NotNull
    java.lang.String label, float value, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onValueChange) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SliderSetting(@org.jetbrains.annotations.NotNull
    java.lang.String label, float value, @org.jetbrains.annotations.NotNull
    kotlin.ranges.ClosedFloatingPointRange<java.lang.Float> range, int steps, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onValueChange) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SwitchSetting(@org.jetbrains.annotations.NotNull
    java.lang.String label, boolean checked, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void InfoRow(@org.jetbrains.annotations.NotNull
    java.lang.String label, @org.jetbrains.annotations.NotNull
    java.lang.String value) {
    }
}