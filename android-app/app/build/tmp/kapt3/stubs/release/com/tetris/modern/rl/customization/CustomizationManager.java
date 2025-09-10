package com.tetris.modern.rl.customization;

import android.content.Context;
import com.tetris.modern.rl.progression.PlayerProgression;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0010\b\u0007\u0018\u00002\u00020\u0001:\u0007,-./012B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u000fJ\b\u0010\u0018\u001a\u0004\u0018\u00010\u0010J\b\u0010\u0019\u001a\u0004\u0018\u00010\u0012J\b\u0010\u001a\u001a\u0004\u0018\u00010\u0014J\u0018\u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000f0\u001cJ\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\u0006\u0010\u001f\u001a\u00020 J\u0018\u0010!\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000f0\u001cJ\u000e\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u001dJ\u000e\u0010%\u001a\u00020#2\u0006\u0010&\u001a\u00020\u001dJ\u000e\u0010\'\u001a\u00020#2\u0006\u0010(\u001a\u00020\u001dJ\b\u0010)\u001a\u00020#H\u0002J\u000e\u0010*\u001a\u00020#2\u0006\u0010+\u001a\u00020\u001dR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00063"}, d2 = {"Lcom/tetris/modern/rl/customization/CustomizationManager;", "", "context", "Landroid/content/Context;", "playerProgression", "Lcom/tetris/modern/rl/progression/PlayerProgression;", "(Landroid/content/Context;Lcom/tetris/modern/rl/progression/PlayerProgression;)V", "_customizationState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/tetris/modern/rl/customization/CustomizationManager$CustomizationState;", "customizationState", "Lkotlinx/coroutines/flow/StateFlow;", "getCustomizationState", "()Lkotlinx/coroutines/flow/StateFlow;", "musicTracks", "", "Lcom/tetris/modern/rl/customization/CustomizationManager$MusicTrack;", "pieceStyles", "Lcom/tetris/modern/rl/customization/CustomizationManager$PieceStyle;", "themes", "Lcom/tetris/modern/rl/customization/CustomizationManager$Theme;", "visualEffects", "Lcom/tetris/modern/rl/customization/CustomizationManager$VisualEffect;", "getCurrentEffects", "getCurrentMusic", "getCurrentPieceStyle", "getCurrentTheme", "getLockedItems", "", "", "getNextUnlocks", "currentLevel", "", "getUnlockedItems", "setMusic", "", "musicId", "setPieceStyle", "styleId", "setTheme", "themeId", "syncWithProgression", "toggleEffect", "effectId", "CustomizationState", "EffectType", "MusicTrack", "PieceStyle", "RenderStyle", "Theme", "VisualEffect", "app_release"})
public final class CustomizationManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.progression.PlayerProgression playerProgression = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.customization.CustomizationManager.Theme> themes = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.customization.CustomizationManager.MusicTrack> musicTracks = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.customization.CustomizationManager.PieceStyle> pieceStyles = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.customization.CustomizationManager.VisualEffect> visualEffects = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.tetris.modern.rl.customization.CustomizationManager.CustomizationState> _customizationState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.customization.CustomizationManager.CustomizationState> customizationState = null;
    
    @javax.inject.Inject
    public CustomizationManager(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.progression.PlayerProgression playerProgression) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.customization.CustomizationManager.CustomizationState> getCustomizationState() {
        return null;
    }
    
    private final void syncWithProgression() {
    }
    
    public final void setTheme(@org.jetbrains.annotations.NotNull
    java.lang.String themeId) {
    }
    
    public final void setMusic(@org.jetbrains.annotations.NotNull
    java.lang.String musicId) {
    }
    
    public final void setPieceStyle(@org.jetbrains.annotations.NotNull
    java.lang.String styleId) {
    }
    
    public final void toggleEffect(@org.jetbrains.annotations.NotNull
    java.lang.String effectId) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tetris.modern.rl.customization.CustomizationManager.Theme getCurrentTheme() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tetris.modern.rl.customization.CustomizationManager.MusicTrack getCurrentMusic() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.tetris.modern.rl.customization.CustomizationManager.PieceStyle getCurrentPieceStyle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.tetris.modern.rl.customization.CustomizationManager.VisualEffect> getCurrentEffects() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.util.List<java.lang.Object>> getUnlockedItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.util.List<java.lang.Object>> getLockedItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.Object> getNextUnlocks(int currentLevel) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bs\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H\u00c6\u0003Jw\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H\u00c6\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020$H\u00d6\u0001J\t\u0010%\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000e\u00a8\u0006&"}, d2 = {"Lcom/tetris/modern/rl/customization/CustomizationManager$CustomizationState;", "", "currentTheme", "", "currentMusic", "currentPieceStyle", "currentEffects", "", "unlockedThemes", "unlockedMusic", "unlockedPieceStyles", "unlockedEffects", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;)V", "getCurrentEffects", "()Ljava/util/Set;", "getCurrentMusic", "()Ljava/lang/String;", "getCurrentPieceStyle", "getCurrentTheme", "getUnlockedEffects", "getUnlockedMusic", "getUnlockedPieceStyles", "getUnlockedThemes", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"})
    public static final class CustomizationState {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String currentTheme = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String currentMusic = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String currentPieceStyle = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.Set<java.lang.String> currentEffects = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.Set<java.lang.String> unlockedThemes = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.Set<java.lang.String> unlockedMusic = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.Set<java.lang.String> unlockedPieceStyles = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.Set<java.lang.String> unlockedEffects = null;
        
        public CustomizationState(@org.jetbrains.annotations.NotNull
        java.lang.String currentTheme, @org.jetbrains.annotations.NotNull
        java.lang.String currentMusic, @org.jetbrains.annotations.NotNull
        java.lang.String currentPieceStyle, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> currentEffects, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedThemes, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedMusic, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedPieceStyles, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedEffects) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getCurrentTheme() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getCurrentMusic() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getCurrentPieceStyle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> getCurrentEffects() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> getUnlockedThemes() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> getUnlockedMusic() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> getUnlockedPieceStyles() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> getUnlockedEffects() {
            return null;
        }
        
        public CustomizationState() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Set<java.lang.String> component8() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.customization.CustomizationManager.CustomizationState copy(@org.jetbrains.annotations.NotNull
        java.lang.String currentTheme, @org.jetbrains.annotations.NotNull
        java.lang.String currentMusic, @org.jetbrains.annotations.NotNull
        java.lang.String currentPieceStyle, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> currentEffects, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedThemes, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedMusic, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedPieceStyles, @org.jetbrains.annotations.NotNull
        java.util.Set<java.lang.String> unlockedEffects) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2 = {"Lcom/tetris/modern/rl/customization/CustomizationManager$EffectType;", "", "(Ljava/lang/String;I)V", "PARTICLES", "TRAILS", "EXPLOSIONS", "LIGHTNING", "SHATTER", "QUANTUM", "RIPPLE", "GLOW", "app_release"})
    public static enum EffectType {
        /*public static final*/ PARTICLES /* = new PARTICLES() */,
        /*public static final*/ TRAILS /* = new TRAILS() */,
        /*public static final*/ EXPLOSIONS /* = new EXPLOSIONS() */,
        /*public static final*/ LIGHTNING /* = new LIGHTNING() */,
        /*public static final*/ SHATTER /* = new SHATTER() */,
        /*public static final*/ QUANTUM /* = new QUANTUM() */,
        /*public static final*/ RIPPLE /* = new RIPPLE() */,
        /*public static final*/ GLOW /* = new GLOW() */;
        
        EffectType() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.customization.CustomizationManager.EffectType> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00c6\u0003JO\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020\u0007H\u00d6\u0001J\t\u0010!\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012\u00a8\u0006\""}, d2 = {"Lcom/tetris/modern/rl/customization/CustomizationManager$MusicTrack;", "", "id", "", "name", "description", "tempo", "", "style", "composer", "requiredLevel", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;I)V", "getComposer", "()Ljava/lang/String;", "getDescription", "getId", "getName", "getRequiredLevel", "()I", "getStyle", "getTempo", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "app_release"})
    public static final class MusicTrack {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String id = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String description = null;
        private final int tempo = 0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String style = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String composer = null;
        private final int requiredLevel = 0;
        
        public MusicTrack(@org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String description, int tempo, @org.jetbrains.annotations.NotNull
        java.lang.String style, @org.jetbrains.annotations.NotNull
        java.lang.String composer, int requiredLevel) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDescription() {
            return null;
        }
        
        public final int getTempo() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getStyle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getComposer() {
            return null;
        }
        
        public final int getRequiredLevel() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        public final int component4() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component6() {
            return null;
        }
        
        public final int component7() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.customization.CustomizationManager.MusicTrack copy(@org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String description, int tempo, @org.jetbrains.annotations.NotNull
        java.lang.String style, @org.jetbrains.annotations.NotNull
        java.lang.String composer, int requiredLevel) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u001a\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\tH\u00c6\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u00c6\u0003J\t\u0010!\u001a\u00020\rH\u00c6\u0003JU\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010#\u001a\u00020\t2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020\rH\u00d6\u0001J\t\u0010&\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\'"}, d2 = {"Lcom/tetris/modern/rl/customization/CustomizationManager$PieceStyle;", "", "id", "", "name", "description", "renderStyle", "Lcom/tetris/modern/rl/customization/CustomizationManager$RenderStyle;", "hasAnimation", "", "specialEffects", "", "requiredLevel", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/tetris/modern/rl/customization/CustomizationManager$RenderStyle;ZLjava/util/List;I)V", "getDescription", "()Ljava/lang/String;", "getHasAnimation", "()Z", "getId", "getName", "getRenderStyle", "()Lcom/tetris/modern/rl/customization/CustomizationManager$RenderStyle;", "getRequiredLevel", "()I", "getSpecialEffects", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "app_release"})
    public static final class PieceStyle {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String id = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String description = null;
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.customization.CustomizationManager.RenderStyle renderStyle = null;
        private final boolean hasAnimation = false;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> specialEffects = null;
        private final int requiredLevel = 0;
        
        public PieceStyle(@org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String description, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.customization.CustomizationManager.RenderStyle renderStyle, boolean hasAnimation, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> specialEffects, int requiredLevel) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDescription() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.customization.CustomizationManager.RenderStyle getRenderStyle() {
            return null;
        }
        
        public final boolean getHasAnimation() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getSpecialEffects() {
            return null;
        }
        
        public final int getRequiredLevel() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.customization.CustomizationManager.RenderStyle component4() {
            return null;
        }
        
        public final boolean component5() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> component6() {
            return null;
        }
        
        public final int component7() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.customization.CustomizationManager.PieceStyle copy(@org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String description, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.customization.CustomizationManager.RenderStyle renderStyle, boolean hasAnimation, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> specialEffects, int requiredLevel) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2 = {"Lcom/tetris/modern/rl/customization/CustomizationManager$RenderStyle;", "", "(Ljava/lang/String;I)V", "STANDARD", "GLASS", "PIXEL", "HOLOGRAM", "CRYSTAL", "ANIMATED", "NEON", "RETRO", "app_release"})
    public static enum RenderStyle {
        /*public static final*/ STANDARD /* = new STANDARD() */,
        /*public static final*/ GLASS /* = new GLASS() */,
        /*public static final*/ PIXEL /* = new PIXEL() */,
        /*public static final*/ HOLOGRAM /* = new HOLOGRAM() */,
        /*public static final*/ CRYSTAL /* = new CRYSTAL() */,
        /*public static final*/ ANIMATED /* = new ANIMATED() */,
        /*public static final*/ NEON /* = new NEON() */,
        /*public static final*/ RETRO /* = new RETRO() */;
        
        RenderStyle() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.customization.CustomizationManager.RenderStyle> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\"\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001Bc\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\f\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0010J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u000fH\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u0016\u0010$\u001a\u00020\u0007H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b%\u0010\u0012J\u0016\u0010&\u001a\u00020\u0007H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\'\u0010\u0012J\u0016\u0010(\u001a\u00020\u0007H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b)\u0010\u0012J\u0016\u0010*\u001a\u00020\u0007H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b+\u0010\u0012J\u0015\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\fH\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\u0083\u0001\u0010.\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\f2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b/\u00100J\u0013\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00104\u001a\u00020\u000fH\u00d6\u0001J\t\u00105\u001a\u00020\u0003H\u00d6\u0001R\u0019\u0010\t\u001a\u00020\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0019\u0010\n\u001a\u00020\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0017\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u001d\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0019\u0010\u0006\u001a\u00020\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u001c\u0010\u0012R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0019\u0010\b\u001a\u00020\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u001f\u0010\u0012\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u00066"}, d2 = {"Lcom/tetris/modern/rl/customization/CustomizationManager$Theme;", "", "id", "", "name", "description", "primaryColor", "Landroidx/compose/ui/graphics/Color;", "secondaryColor", "backgroundColor", "gridColor", "pieceColors", "", "effectStyle", "requiredLevel", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJJJLjava/util/Map;Ljava/lang/String;ILkotlin/jvm/internal/DefaultConstructorMarker;)V", "getBackgroundColor-0d7_KjU", "()J", "J", "getDescription", "()Ljava/lang/String;", "getEffectStyle", "getGridColor-0d7_KjU", "getId", "getName", "getPieceColors", "()Ljava/util/Map;", "getPrimaryColor-0d7_KjU", "getRequiredLevel", "()I", "getSecondaryColor-0d7_KjU", "component1", "component10", "component2", "component3", "component4", "component4-0d7_KjU", "component5", "component5-0d7_KjU", "component6", "component6-0d7_KjU", "component7", "component7-0d7_KjU", "component8", "component9", "copy", "copy-vZAX7A0", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJJJLjava/util/Map;Ljava/lang/String;I)Lcom/tetris/modern/rl/customization/CustomizationManager$Theme;", "equals", "", "other", "hashCode", "toString", "app_release"})
    public static final class Theme {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String id = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String description = null;
        private final long primaryColor = 0L;
        private final long secondaryColor = 0L;
        private final long backgroundColor = 0L;
        private final long gridColor = 0L;
        @org.jetbrains.annotations.NotNull
        private final java.util.Map<java.lang.String, androidx.compose.ui.graphics.Color> pieceColors = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String effectStyle = null;
        private final int requiredLevel = 0;
        
        private Theme(java.lang.String id, java.lang.String name, java.lang.String description, long primaryColor, long secondaryColor, long backgroundColor, long gridColor, java.util.Map<java.lang.String, androidx.compose.ui.graphics.Color> pieceColors, java.lang.String effectStyle, int requiredLevel) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDescription() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Map<java.lang.String, androidx.compose.ui.graphics.Color> getPieceColors() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getEffectStyle() {
            return null;
        }
        
        public final int getRequiredLevel() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        public final int component10() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.Map<java.lang.String, androidx.compose.ui.graphics.Color> component8() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component9() {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u000bH\u00c6\u0003JE\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\u000bH\u00d6\u0001J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006#"}, d2 = {"Lcom/tetris/modern/rl/customization/CustomizationManager$VisualEffect;", "", "id", "", "name", "description", "effectType", "Lcom/tetris/modern/rl/customization/CustomizationManager$EffectType;", "intensity", "", "requiredLevel", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/tetris/modern/rl/customization/CustomizationManager$EffectType;FI)V", "getDescription", "()Ljava/lang/String;", "getEffectType", "()Lcom/tetris/modern/rl/customization/CustomizationManager$EffectType;", "getId", "getIntensity", "()F", "getName", "getRequiredLevel", "()I", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "app_release"})
    public static final class VisualEffect {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String id = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String description = null;
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.customization.CustomizationManager.EffectType effectType = null;
        private final float intensity = 0.0F;
        private final int requiredLevel = 0;
        
        public VisualEffect(@org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String description, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.customization.CustomizationManager.EffectType effectType, float intensity, int requiredLevel) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDescription() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.customization.CustomizationManager.EffectType getEffectType() {
            return null;
        }
        
        public final float getIntensity() {
            return 0.0F;
        }
        
        public final int getRequiredLevel() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.customization.CustomizationManager.EffectType component4() {
            return null;
        }
        
        public final float component5() {
            return 0.0F;
        }
        
        public final int component6() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.customization.CustomizationManager.VisualEffect copy(@org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.lang.String description, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.customization.CustomizationManager.EffectType effectType, float intensity, int requiredLevel) {
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