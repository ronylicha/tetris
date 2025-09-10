package com.tetris.modern.rl.data.dao;

import androidx.room.*;
import com.tetris.modern.rl.data.entities.UnlockableEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u000f\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000f0\u000eH\'J\u0014\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000f0\u000eH\'J\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00072\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0018\u0010\u0012\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0013\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001c\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000f0\u000e2\u0006\u0010\n\u001a\u00020\u000bH\'J\u0014\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000f0\u000eH\'J\u001c\u0010\u0016\u001a\u00020\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u000bH\u0097@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\u001e"}, d2 = {"Lcom/tetris/modern/rl/data/dao/UnlockableDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteUnlockable", "unlockable", "Lcom/tetris/modern/rl/data/entities/UnlockableEntity;", "(Lcom/tetris/modern/rl/data/entities/UnlockableEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deselectAllByType", "type", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllUnlockables", "Lkotlinx/coroutines/flow/Flow;", "", "getLockedItems", "getSelectedByType", "getUnlockableById", "id", "getUnlockablesByType", "getUnlockedItems", "insertAll", "unlockables", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertUnlockable", "selectItem", "selectUnlockable", "unlockItem", "updateUnlockable", "app_debug"})
@androidx.room.Dao
public abstract interface UnlockableDao {
    
    @androidx.room.Query(value = "SELECT * FROM unlockables")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.UnlockableEntity>> getAllUnlockables();
    
    @androidx.room.Query(value = "SELECT * FROM unlockables WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUnlockableById(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.tetris.modern.rl.data.entities.UnlockableEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM unlockables WHERE type = :type")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.UnlockableEntity>> getUnlockablesByType(@org.jetbrains.annotations.NotNull
    java.lang.String type);
    
    @androidx.room.Query(value = "SELECT * FROM unlockables WHERE isUnlocked = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.UnlockableEntity>> getUnlockedItems();
    
    @androidx.room.Query(value = "SELECT * FROM unlockables WHERE isUnlocked = 0")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.UnlockableEntity>> getLockedItems();
    
    @androidx.room.Query(value = "SELECT * FROM unlockables WHERE type = :type AND isSelected = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getSelectedByType(@org.jetbrains.annotations.NotNull
    java.lang.String type, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.tetris.modern.rl.data.entities.UnlockableEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertUnlockable(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.UnlockableEntity unlockable, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull
    java.util.List<com.tetris.modern.rl.data.entities.UnlockableEntity> unlockables, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateUnlockable(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.UnlockableEntity unlockable, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE unlockables SET isUnlocked = 1 WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object unlockItem(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE unlockables SET isSelected = 0 WHERE type = :type")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deselectAllByType(@org.jetbrains.annotations.NotNull
    java.lang.String type, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE unlockables SET isSelected = 1 WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object selectItem(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Transaction
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object selectUnlockable(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteUnlockable(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.UnlockableEntity unlockable, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM unlockables")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        
        @androidx.room.Transaction
        @org.jetbrains.annotations.Nullable
        public static java.lang.Object selectUnlockable(@org.jetbrains.annotations.NotNull
        com.tetris.modern.rl.data.dao.UnlockableDao $this, @org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
}