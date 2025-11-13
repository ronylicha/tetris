package com.tetris.modern.rl.utils;

import androidx.compose.runtime.Composable;

/**
 * Device type detection and responsive utilities
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0005\f\r\u000e\u000f\u0010B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J\u001a\u0010\b\u001a\u00020\t2\u0006\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\u0011"}, d2 = {"Lcom/tetris/modern/rl/utils/DeviceUtils;", "", "()V", "getDeviceInfo", "Lcom/tetris/modern/rl/utils/DeviceUtils$DeviceInfo;", "getGameLayoutConfig", "Lcom/tetris/modern/rl/utils/DeviceUtils$GameLayoutConfig;", "deviceInfo", "getGridColumns", "", "forModeSelection", "", "DeviceInfo", "DeviceType", "GameLayoutConfig", "Orientation", "PanelArrangement", "app_debug"})
public final class DeviceUtils {
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.utils.DeviceUtils INSTANCE = null;
    
    private DeviceUtils() {
        super();
    }
    
    @androidx.compose.runtime.Composable
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.utils.DeviceUtils.DeviceInfo getDeviceInfo() {
        return null;
    }
    
    /**
     * Get responsive game layout configuration
     */
    @androidx.compose.runtime.Composable
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.utils.DeviceUtils.GameLayoutConfig getGameLayoutConfig(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.utils.DeviceUtils.DeviceInfo deviceInfo) {
        return null;
    }
    
    /**
     * Get number of columns for grid layouts
     */
    @androidx.compose.runtime.Composable
    public final int getGridColumns(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.utils.DeviceUtils.DeviceInfo deviceInfo, boolean forModeSelection) {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001a\u001a\u00020\nH\u00c6\u0003JE\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\n2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\rR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006!"}, d2 = {"Lcom/tetris/modern/rl/utils/DeviceUtils$DeviceInfo;", "", "type", "Lcom/tetris/modern/rl/utils/DeviceUtils$DeviceType;", "orientation", "Lcom/tetris/modern/rl/utils/DeviceUtils$Orientation;", "screenWidthDp", "", "screenHeightDp", "isTablet", "", "isLandscape", "(Lcom/tetris/modern/rl/utils/DeviceUtils$DeviceType;Lcom/tetris/modern/rl/utils/DeviceUtils$Orientation;IIZZ)V", "()Z", "getOrientation", "()Lcom/tetris/modern/rl/utils/DeviceUtils$Orientation;", "getScreenHeightDp", "()I", "getScreenWidthDp", "getType", "()Lcom/tetris/modern/rl/utils/DeviceUtils$DeviceType;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
    public static final class DeviceInfo {
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.utils.DeviceUtils.DeviceType type = null;
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.utils.DeviceUtils.Orientation orientation = null;
        private final int screenWidthDp = 0;
        private final int screenHeightDp = 0;
        private final boolean isTablet = false;
        private final boolean isLandscape = false;
        
        public DeviceInfo(@org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.utils.DeviceUtils.DeviceType type, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.utils.DeviceUtils.Orientation orientation, int screenWidthDp, int screenHeightDp, boolean isTablet, boolean isLandscape) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.utils.DeviceUtils.DeviceType getType() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.utils.DeviceUtils.Orientation getOrientation() {
            return null;
        }
        
        public final int getScreenWidthDp() {
            return 0;
        }
        
        public final int getScreenHeightDp() {
            return 0;
        }
        
        public final boolean isTablet() {
            return false;
        }
        
        public final boolean isLandscape() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.utils.DeviceUtils.DeviceType component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.utils.DeviceUtils.Orientation component2() {
            return null;
        }
        
        public final int component3() {
            return 0;
        }
        
        public final int component4() {
            return 0;
        }
        
        public final boolean component5() {
            return false;
        }
        
        public final boolean component6() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.utils.DeviceUtils.DeviceInfo copy(@org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.utils.DeviceUtils.DeviceType type, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.utils.DeviceUtils.Orientation orientation, int screenWidthDp, int screenHeightDp, boolean isTablet, boolean isLandscape) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/tetris/modern/rl/utils/DeviceUtils$DeviceType;", "", "(Ljava/lang/String;I)V", "PHONE", "TABLET_7", "TABLET_10", "app_debug"})
    public static enum DeviceType {
        /*public static final*/ PHONE /* = new PHONE() */,
        /*public static final*/ TABLET_7 /* = new TABLET_7() */,
        /*public static final*/ TABLET_10 /* = new TABLET_10() */;
        
        DeviceType() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.utils.DeviceUtils.DeviceType> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\tH\u00c6\u0003J\u0016\u0010\u001e\u001a\u00020\u000bH\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001f\u0010\u000fJ\u0016\u0010 \u001a\u00020\u000bH\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b!\u0010\u000fJY\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bH\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b#\u0010$J\u0013\u0010%\u001a\u00020\u00032\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020(H\u00d6\u0001J\t\u0010)\u001a\u00020*H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0019\u0010\f\u001a\u00020\u000b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u0010\u0010\u000fR\u0019\u0010\n\u001a\u00020\u000b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006+"}, d2 = {"Lcom/tetris/modern/rl/utils/DeviceUtils$GameLayoutConfig;", "", "showSidePanels", "", "showBottomControls", "showFloatingControls", "gridScale", "", "panelArrangement", "Lcom/tetris/modern/rl/utils/DeviceUtils$PanelArrangement;", "maxGridWidth", "Landroidx/compose/ui/unit/Dp;", "maxGridHeight", "(ZZZFLcom/tetris/modern/rl/utils/DeviceUtils$PanelArrangement;FFLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getGridScale", "()F", "getMaxGridHeight-D9Ej5fM", "F", "getMaxGridWidth-D9Ej5fM", "getPanelArrangement", "()Lcom/tetris/modern/rl/utils/DeviceUtils$PanelArrangement;", "getShowBottomControls", "()Z", "getShowFloatingControls", "getShowSidePanels", "component1", "component2", "component3", "component4", "component5", "component6", "component6-D9Ej5fM", "component7", "component7-D9Ej5fM", "copy", "copy-gv0btCI", "(ZZZFLcom/tetris/modern/rl/utils/DeviceUtils$PanelArrangement;FF)Lcom/tetris/modern/rl/utils/DeviceUtils$GameLayoutConfig;", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class GameLayoutConfig {
        private final boolean showSidePanels = false;
        private final boolean showBottomControls = false;
        private final boolean showFloatingControls = false;
        private final float gridScale = 0.0F;
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.utils.DeviceUtils.PanelArrangement panelArrangement = null;
        private final float maxGridWidth = 0.0F;
        private final float maxGridHeight = 0.0F;
        
        private GameLayoutConfig(boolean showSidePanels, boolean showBottomControls, boolean showFloatingControls, float gridScale, com.tetris.modern.rl.utils.DeviceUtils.PanelArrangement panelArrangement, float maxGridWidth, float maxGridHeight) {
            super();
        }
        
        public final boolean getShowSidePanels() {
            return false;
        }
        
        public final boolean getShowBottomControls() {
            return false;
        }
        
        public final boolean getShowFloatingControls() {
            return false;
        }
        
        public final float getGridScale() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.utils.DeviceUtils.PanelArrangement getPanelArrangement() {
            return null;
        }
        
        public final boolean component1() {
            return false;
        }
        
        public final boolean component2() {
            return false;
        }
        
        public final boolean component3() {
            return false;
        }
        
        public final float component4() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.utils.DeviceUtils.PanelArrangement component5() {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/tetris/modern/rl/utils/DeviceUtils$Orientation;", "", "(Ljava/lang/String;I)V", "PORTRAIT", "LANDSCAPE", "app_debug"})
    public static enum Orientation {
        /*public static final*/ PORTRAIT /* = new PORTRAIT() */,
        /*public static final*/ LANDSCAPE /* = new LANDSCAPE() */;
        
        Orientation() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.utils.DeviceUtils.Orientation> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/tetris/modern/rl/utils/DeviceUtils$PanelArrangement;", "", "(Ljava/lang/String;I)V", "VERTICAL", "HORIZONTAL_COMPACT", "HORIZONTAL_EXTENDED", "app_debug"})
    public static enum PanelArrangement {
        /*public static final*/ VERTICAL /* = new VERTICAL() */,
        /*public static final*/ HORIZONTAL_COMPACT /* = new HORIZONTAL_COMPACT() */,
        /*public static final*/ HORIZONTAL_EXTENDED /* = new HORIZONTAL_EXTENDED() */;
        
        PanelArrangement() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.utils.DeviceUtils.PanelArrangement> getEntries() {
            return null;
        }
    }
}