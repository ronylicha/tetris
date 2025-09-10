package com.tetris.modern.rl.ui.controls;

import android.view.MotionEvent;
import androidx.compose.foundation.gestures.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.*;
import com.tetris.modern.rl.ui.viewmodels.MainViewModel;
import kotlinx.coroutines.Dispatchers;

/**
 * Advanced touch control system for Tetris gameplay
 * Supports swipes, taps, long press, and multi-touch gestures
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001d\u0018\u0000 02\u00020\u0001:\u0006012345B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J(\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\u0014\b\u0002\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00110\u0015H\u0007J,\u0010\u0017\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\fH\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001dJ6\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u00192\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00110\u0015H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b!\u0010\"J\u001a\u0010#\u001a\u00020\u00112\u0006\u0010$\u001a\u00020\u0019H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b%\u0010&J\u000e\u0010\'\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u0016J.\u0010)\u001a\u00020\u00112\u0006\u0010$\u001a\u00020\u00192\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00110\u0015H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b*\u0010+J\u0006\u0010,\u001a\u00020\u0011J\u000e\u0010-\u001a\u00020\u0011H\u0082@\u00a2\u0006\u0002\u0010.J\b\u0010/\u001a\u00020\u0011H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00066"}, d2 = {"Lcom/tetris/modern/rl/ui/controls/TouchControlsManager;", "", "viewModel", "Lcom/tetris/modern/rl/ui/viewmodels/MainViewModel;", "(Lcom/tetris/modern/rl/ui/viewmodels/MainViewModel;)V", "gestureHistory", "", "Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$GestureData;", "isLongPressing", "", "isSoftDropping", "lastTapTime", "", "softDropSpeed", "tapCount", "", "GameTouchControls", "", "modifier", "Landroidx/compose/ui/Modifier;", "onGesture", "Lkotlin/Function1;", "Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$GestureType;", "detectSwipe", "startPoint", "Landroidx/compose/ui/geometry/Offset;", "endPoint", "duration", "detectSwipe-Wko1d7g", "(JJJ)Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$GestureType;", "handleDrag", "startPosition", "endPosition", "handleDrag-Wko1d7g", "(JJLkotlin/jvm/functions/Function1;)V", "handleLongPress", "position", "handleLongPress-k-4lQ0M", "(J)V", "handleSwipeGesture", "gesture", "handleTap", "handleTap-3MmeM6k", "(JLkotlin/jvm/functions/Function1;)V", "reset", "startSoftDrop", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stopSoftDrop", "Companion", "DoubleTapAction", "GestureData", "GestureType", "LongPressAction", "TouchSettings", "app_release"})
public final class TouchControlsManager {
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel = null;
    public static final float SWIPE_THRESHOLD = 50.0F;
    public static final float SWIPE_VELOCITY_THRESHOLD = 300.0F;
    public static final long TAP_TIMEOUT = 200L;
    public static final long LONG_PRESS_TIMEOUT = 800L;
    public static final long DOUBLE_TAP_TIMEOUT = 300L;
    public static final long SOFT_DROP_INITIAL_DELAY = 100L;
    public static final long SOFT_DROP_REPEAT_DELAY = 50L;
    public static final float SOFT_DROP_ACCELERATION = 0.9F;
    public static final float LEFT_ZONE = 0.3F;
    public static final float RIGHT_ZONE = 0.7F;
    public static final float BOTTOM_ZONE = 0.8F;
    private long lastTapTime = 0L;
    private int tapCount = 0;
    private boolean isLongPressing = false;
    private boolean isSoftDropping = false;
    private long softDropSpeed = 100L;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.tetris.modern.rl.ui.controls.TouchControlsManager.GestureData> gestureHistory = null;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.ui.controls.TouchControlsManager.Companion Companion = null;
    
    public TouchControlsManager(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.viewmodels.MainViewModel viewModel) {
        super();
    }
    
    @androidx.compose.runtime.Composable
    public final void GameTouchControls(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.tetris.modern.rl.ui.controls.TouchControlsManager.GestureType, kotlin.Unit> onGesture) {
    }
    
    private final java.lang.Object startSoftDrop(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void stopSoftDrop() {
    }
    
    public final void handleSwipeGesture(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.ui.controls.TouchControlsManager.GestureType gesture) {
    }
    
    public final void reset() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$Companion;", "", "()V", "BOTTOM_ZONE", "", "DOUBLE_TAP_TIMEOUT", "", "LEFT_ZONE", "LONG_PRESS_TIMEOUT", "RIGHT_ZONE", "SOFT_DROP_ACCELERATION", "SOFT_DROP_INITIAL_DELAY", "SOFT_DROP_REPEAT_DELAY", "SWIPE_THRESHOLD", "SWIPE_VELOCITY_THRESHOLD", "TAP_TIMEOUT", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$DoubleTapAction;", "", "(Ljava/lang/String;I)V", "ROTATE_CCW", "ROTATE_180", "HARD_DROP", "HOLD", "app_release"})
    public static enum DoubleTapAction {
        /*public static final*/ ROTATE_CCW /* = new ROTATE_CCW() */,
        /*public static final*/ ROTATE_180 /* = new ROTATE_180() */,
        /*public static final*/ HARD_DROP /* = new HARD_DROP() */,
        /*public static final*/ HOLD /* = new HOLD() */;
        
        DoubleTapAction() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.ui.controls.TouchControlsManager.DoubleTapAction> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\u0016\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0017\u0010\rJ\u0016\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0019\u0010\rJ\t\u0010\u001a\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\nH\u00c6\u0003JE\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020#H\u00d6\u0001J\t\u0010$\u001a\u00020%H\u00d6\u0001R\u0019\u0010\u0006\u001a\u00020\u0005\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u0019\u0010\u0004\u001a\u00020\u0005\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006&"}, d2 = {"Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$GestureData;", "", "type", "Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$GestureType;", "startPoint", "Landroidx/compose/ui/geometry/Offset;", "endPoint", "timestamp", "", "velocity", "", "(Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$GestureType;JJJFLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getEndPoint-F1C5BW0", "()J", "J", "getStartPoint-F1C5BW0", "getTimestamp", "getType", "()Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$GestureType;", "getVelocity", "()F", "component1", "component2", "component2-F1C5BW0", "component3", "component3-F1C5BW0", "component4", "component5", "copy", "copy-5iVPX68", "(Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$GestureType;JJJF)Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$GestureData;", "equals", "", "other", "hashCode", "", "toString", "", "app_release"})
    public static final class GestureData {
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.ui.controls.TouchControlsManager.GestureType type = null;
        private final long startPoint = 0L;
        private final long endPoint = 0L;
        private final long timestamp = 0L;
        private final float velocity = 0.0F;
        
        private GestureData(com.tetris.modern.rl.ui.controls.TouchControlsManager.GestureType type, long startPoint, long endPoint, long timestamp, float velocity) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.ui.controls.TouchControlsManager.GestureType getType() {
            return null;
        }
        
        public final long getTimestamp() {
            return 0L;
        }
        
        public final float getVelocity() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.ui.controls.TouchControlsManager.GestureType component1() {
            return null;
        }
        
        public final long component4() {
            return 0L;
        }
        
        public final float component5() {
            return 0.0F;
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000e\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$GestureType;", "", "(Ljava/lang/String;I)V", "TAP", "DOUBLE_TAP", "LONG_PRESS", "SWIPE_LEFT", "SWIPE_RIGHT", "SWIPE_DOWN", "SWIPE_UP", "DRAG_LEFT", "DRAG_RIGHT", "DRAG_DOWN", "PINCH", "ROTATE", "app_release"})
    public static enum GestureType {
        /*public static final*/ TAP /* = new TAP() */,
        /*public static final*/ DOUBLE_TAP /* = new DOUBLE_TAP() */,
        /*public static final*/ LONG_PRESS /* = new LONG_PRESS() */,
        /*public static final*/ SWIPE_LEFT /* = new SWIPE_LEFT() */,
        /*public static final*/ SWIPE_RIGHT /* = new SWIPE_RIGHT() */,
        /*public static final*/ SWIPE_DOWN /* = new SWIPE_DOWN() */,
        /*public static final*/ SWIPE_UP /* = new SWIPE_UP() */,
        /*public static final*/ DRAG_LEFT /* = new DRAG_LEFT() */,
        /*public static final*/ DRAG_RIGHT /* = new DRAG_RIGHT() */,
        /*public static final*/ DRAG_DOWN /* = new DRAG_DOWN() */,
        /*public static final*/ PINCH /* = new PINCH() */,
        /*public static final*/ ROTATE /* = new ROTATE() */;
        
        GestureType() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.ui.controls.TouchControlsManager.GestureType> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$LongPressAction;", "", "(Ljava/lang/String;I)V", "HOLD", "HARD_DROP", "PAUSE", "NONE", "app_release"})
    public static enum LongPressAction {
        /*public static final*/ HOLD /* = new HOLD() */,
        /*public static final*/ HARD_DROP /* = new HARD_DROP() */,
        /*public static final*/ PAUSE /* = new PAUSE() */,
        /*public static final*/ NONE /* = new NONE() */;
        
        LongPressAction() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.tetris.modern.rl.ui.controls.TouchControlsManager.LongPressAction> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003JE\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u00052\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020#H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\n\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010\u00a8\u0006$"}, d2 = {"Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$TouchSettings;", "", "swipeSensitivity", "", "tapToRotate", "", "doubleTapAction", "Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$DoubleTapAction;", "longPressAction", "Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$LongPressAction;", "vibrationEnabled", "dragToMove", "(FZLcom/tetris/modern/rl/ui/controls/TouchControlsManager$DoubleTapAction;Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$LongPressAction;ZZ)V", "getDoubleTapAction", "()Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$DoubleTapAction;", "getDragToMove", "()Z", "getLongPressAction", "()Lcom/tetris/modern/rl/ui/controls/TouchControlsManager$LongPressAction;", "getSwipeSensitivity", "()F", "getTapToRotate", "getVibrationEnabled", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "", "app_release"})
    public static final class TouchSettings {
        private final float swipeSensitivity = 0.0F;
        private final boolean tapToRotate = false;
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.ui.controls.TouchControlsManager.DoubleTapAction doubleTapAction = null;
        @org.jetbrains.annotations.NotNull
        private final com.tetris.modern.rl.ui.controls.TouchControlsManager.LongPressAction longPressAction = null;
        private final boolean vibrationEnabled = false;
        private final boolean dragToMove = false;
        
        public TouchSettings(float swipeSensitivity, boolean tapToRotate, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.ui.controls.TouchControlsManager.DoubleTapAction doubleTapAction, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.ui.controls.TouchControlsManager.LongPressAction longPressAction, boolean vibrationEnabled, boolean dragToMove) {
            super();
        }
        
        public final float getSwipeSensitivity() {
            return 0.0F;
        }
        
        public final boolean getTapToRotate() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.ui.controls.TouchControlsManager.DoubleTapAction getDoubleTapAction() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.ui.controls.TouchControlsManager.LongPressAction getLongPressAction() {
            return null;
        }
        
        public final boolean getVibrationEnabled() {
            return false;
        }
        
        public final boolean getDragToMove() {
            return false;
        }
        
        public TouchSettings() {
            super();
        }
        
        public final float component1() {
            return 0.0F;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.ui.controls.TouchControlsManager.DoubleTapAction component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.ui.controls.TouchControlsManager.LongPressAction component4() {
            return null;
        }
        
        public final boolean component5() {
            return false;
        }
        
        public final boolean component6() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.ui.controls.TouchControlsManager.TouchSettings copy(float swipeSensitivity, boolean tapToRotate, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.ui.controls.TouchControlsManager.DoubleTapAction doubleTapAction, @org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.ui.controls.TouchControlsManager.LongPressAction longPressAction, boolean vibrationEnabled, boolean dragToMove) {
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