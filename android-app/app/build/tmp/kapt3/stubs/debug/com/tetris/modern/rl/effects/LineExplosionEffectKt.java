package com.tetris.modern.rl.effects;

import androidx.compose.animation.core.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.math.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000>\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00060\u000fH\u0007\u001a$\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u001a\u0010\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\bH\u0002\u001a$\u0010\u0017\u001a\u00020\u0006*\u00020\u00182\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0019"}, d2 = {"explosionColors", "", "Landroidx/compose/ui/graphics/Color;", "getExplosionColors", "()Ljava/util/List;", "LineExplosionEffect", "", "explosions", "Lcom/tetris/modern/rl/effects/LineExplosion;", "cellSize", "", "offsetY", "modifier", "Landroidx/compose/ui/Modifier;", "onExplosionComplete", "Lkotlin/Function1;", "createLineExplosion", "lineY", "", "gridWidth", "colors", "updateExplosion", "explosion", "drawExplosion", "Landroidx/compose/ui/graphics/drawscope/DrawScope;", "app_debug"})
public final class LineExplosionEffectKt {
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<androidx.compose.ui.graphics.Color> explosionColors = null;
    
    @androidx.compose.runtime.Composable
    public static final void LineExplosionEffect(@org.jetbrains.annotations.NotNull
    java.util.List<com.tetris.modern.rl.effects.LineExplosion> explosions, float cellSize, float offsetY, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.tetris.modern.rl.effects.LineExplosion, kotlin.Unit> onExplosionComplete) {
    }
    
    private static final void updateExplosion(com.tetris.modern.rl.effects.LineExplosion explosion) {
    }
    
    private static final void drawExplosion(androidx.compose.ui.graphics.drawscope.DrawScope $this$drawExplosion, com.tetris.modern.rl.effects.LineExplosion explosion, float cellSize, float offsetY) {
    }
    
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.effects.LineExplosion createLineExplosion(int lineY, int gridWidth, @org.jetbrains.annotations.NotNull
    java.util.List<androidx.compose.ui.graphics.Color> colors) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public static final java.util.List<androidx.compose.ui.graphics.Color> getExplosionColors() {
        return null;
    }
}