package com.tetris.modern.rl.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b=\b\u0087\b\u0018\u00002\u00020\u0001B\u00cd\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u0012\b\b\u0002\u0010\u000e\u001a\u00020\f\u0012\b\b\u0002\u0010\u000f\u001a\u00020\f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\t\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\t\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\t\u0012\b\b\u0002\u0010\u0016\u001a\u00020\t\u0012\b\b\u0002\u0010\u0017\u001a\u00020\t\u0012\b\b\u0002\u0010\u0018\u001a\u00020\f\u0012\b\b\u0002\u0010\u0019\u001a\u00020\t\u00a2\u0006\u0002\u0010\u001aJ\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\fH\u00c6\u0003J\t\u00102\u001a\u00020\tH\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\tH\u00c6\u0003J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\tH\u00c6\u0003J\t\u00108\u001a\u00020\tH\u00c6\u0003J\t\u00109\u001a\u00020\tH\u00c6\u0003J\t\u0010:\u001a\u00020\fH\u00c6\u0003J\t\u0010;\u001a\u00020\u0005H\u00c6\u0003J\t\u0010<\u001a\u00020\tH\u00c6\u0003J\t\u0010=\u001a\u00020\u0005H\u00c6\u0003J\t\u0010>\u001a\u00020\u0005H\u00c6\u0003J\t\u0010?\u001a\u00020\tH\u00c6\u0003J\t\u0010@\u001a\u00020\u0005H\u00c6\u0003J\t\u0010A\u001a\u00020\fH\u00c6\u0003J\t\u0010B\u001a\u00020\fH\u00c6\u0003J\t\u0010C\u001a\u00020\fH\u00c6\u0003J\u00d1\u0001\u0010D\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\f2\b\b\u0002\u0010\u000f\u001a\u00020\f2\b\b\u0002\u0010\u0010\u001a\u00020\t2\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\t2\b\b\u0002\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u0017\u001a\u00020\t2\b\b\u0002\u0010\u0018\u001a\u00020\f2\b\b\u0002\u0010\u0019\u001a\u00020\tH\u00c6\u0001J\u0013\u0010E\u001a\u00020\t2\b\u0010F\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010G\u001a\u00020\u0003H\u00d6\u0001J\t\u0010H\u001a\u00020\fH\u00d6\u0001R\u0011\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0012\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0018\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001cR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001cR\u0011\u0010\u0015\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u001eR\u0011\u0010\u0019\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001eR\u0011\u0010\u0017\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u001eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010$R\u0011\u0010\u000f\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010 R\u0011\u0010\r\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010 R\u0011\u0010\u000e\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010 R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010 R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010$R\u0011\u0010\u0010\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001eR\u0011\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001cR\u0011\u0010\n\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010$R\u0011\u0010\u0016\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001e\u00a8\u0006I"}, d2 = {"Lcom/tetris/modern/rl/data/entities/SettingsEntity;", "", "id", "", "masterVolume", "", "musicVolume", "sfxVolume", "vibrationEnabled", "", "touchSensitivity", "selectedTheme", "", "selectedMusic", "selectedPieceStyle", "selectedEffects", "showGhostPiece", "showNextPieces", "autoHold", "das", "arr", "isDarkMode", "useDynamicColors", "isMuted", "controlType", "isFirstLaunch", "(IFFFZFLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZIZIIZZZLjava/lang/String;Z)V", "getArr", "()I", "getAutoHold", "()Z", "getControlType", "()Ljava/lang/String;", "getDas", "getId", "getMasterVolume", "()F", "getMusicVolume", "getSelectedEffects", "getSelectedMusic", "getSelectedPieceStyle", "getSelectedTheme", "getSfxVolume", "getShowGhostPiece", "getShowNextPieces", "getTouchSensitivity", "getUseDynamicColors", "getVibrationEnabled", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "settings")
public final class SettingsEntity {
    @androidx.room.PrimaryKey
    private final int id = 0;
    private final float masterVolume = 0.0F;
    private final float musicVolume = 0.0F;
    private final float sfxVolume = 0.0F;
    private final boolean vibrationEnabled = false;
    private final float touchSensitivity = 0.0F;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String selectedTheme = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String selectedMusic = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String selectedPieceStyle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String selectedEffects = null;
    private final boolean showGhostPiece = false;
    private final int showNextPieces = 0;
    private final boolean autoHold = false;
    private final int das = 0;
    private final int arr = 0;
    private final boolean isDarkMode = false;
    private final boolean useDynamicColors = false;
    private final boolean isMuted = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String controlType = null;
    private final boolean isFirstLaunch = false;
    
    public SettingsEntity(int id, float masterVolume, float musicVolume, float sfxVolume, boolean vibrationEnabled, float touchSensitivity, @org.jetbrains.annotations.NotNull
    java.lang.String selectedTheme, @org.jetbrains.annotations.NotNull
    java.lang.String selectedMusic, @org.jetbrains.annotations.NotNull
    java.lang.String selectedPieceStyle, @org.jetbrains.annotations.NotNull
    java.lang.String selectedEffects, boolean showGhostPiece, int showNextPieces, boolean autoHold, int das, int arr, boolean isDarkMode, boolean useDynamicColors, boolean isMuted, @org.jetbrains.annotations.NotNull
    java.lang.String controlType, boolean isFirstLaunch) {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    public final float getMasterVolume() {
        return 0.0F;
    }
    
    public final float getMusicVolume() {
        return 0.0F;
    }
    
    public final float getSfxVolume() {
        return 0.0F;
    }
    
    public final boolean getVibrationEnabled() {
        return false;
    }
    
    public final float getTouchSensitivity() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSelectedTheme() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSelectedMusic() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSelectedPieceStyle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSelectedEffects() {
        return null;
    }
    
    public final boolean getShowGhostPiece() {
        return false;
    }
    
    public final int getShowNextPieces() {
        return 0;
    }
    
    public final boolean getAutoHold() {
        return false;
    }
    
    public final int getDas() {
        return 0;
    }
    
    public final int getArr() {
        return 0;
    }
    
    public final boolean isDarkMode() {
        return false;
    }
    
    public final boolean getUseDynamicColors() {
        return false;
    }
    
    public final boolean isMuted() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getControlType() {
        return null;
    }
    
    public final boolean isFirstLaunch() {
        return false;
    }
    
    public SettingsEntity() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component10() {
        return null;
    }
    
    public final boolean component11() {
        return false;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final boolean component13() {
        return false;
    }
    
    public final int component14() {
        return 0;
    }
    
    public final int component15() {
        return 0;
    }
    
    public final boolean component16() {
        return false;
    }
    
    public final boolean component17() {
        return false;
    }
    
    public final boolean component18() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component19() {
        return null;
    }
    
    public final float component2() {
        return 0.0F;
    }
    
    public final boolean component20() {
        return false;
    }
    
    public final float component3() {
        return 0.0F;
    }
    
    public final float component4() {
        return 0.0F;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final float component6() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.entities.SettingsEntity copy(int id, float masterVolume, float musicVolume, float sfxVolume, boolean vibrationEnabled, float touchSensitivity, @org.jetbrains.annotations.NotNull
    java.lang.String selectedTheme, @org.jetbrains.annotations.NotNull
    java.lang.String selectedMusic, @org.jetbrains.annotations.NotNull
    java.lang.String selectedPieceStyle, @org.jetbrains.annotations.NotNull
    java.lang.String selectedEffects, boolean showGhostPiece, int showNextPieces, boolean autoHold, int das, int arr, boolean isDarkMode, boolean useDynamicColors, boolean isMuted, @org.jetbrains.annotations.NotNull
    java.lang.String controlType, boolean isFirstLaunch) {
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