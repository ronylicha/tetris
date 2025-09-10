package com.tetris.modern.rl.data.dao;

import androidx.room.*;
import com.tetris.modern.rl.data.entities.CustomizationEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0018\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u001c\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\n\u001a\u00020\bH\'J\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001c\u0010\u0010\u001a\u00020\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/tetris/modern/rl/data/dao/CustomizationDao;", "", "getAllCustomizations", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/tetris/modern/rl/data/entities/CustomizationEntity;", "getCustomization", "itemId", "", "getCustomizationsByType", "type", "getUnlockedCustomizations", "insertOrUpdateCustomization", "", "customization", "(Lcom/tetris/modern/rl/data/entities/CustomizationEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertOrUpdateCustomizations", "customizations", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unlockCustomization", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface CustomizationDao {
    
    @androidx.room.Query(value = "SELECT * FROM customizations")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.CustomizationEntity>> getAllCustomizations();
    
    @androidx.room.Query(value = "SELECT * FROM customizations WHERE type = :type")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.CustomizationEntity>> getCustomizationsByType(@org.jetbrains.annotations.NotNull
    java.lang.String type);
    
    @androidx.room.Query(value = "SELECT * FROM customizations WHERE isUnlocked = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.CustomizationEntity>> getUnlockedCustomizations();
    
    @androidx.room.Query(value = "SELECT * FROM customizations WHERE itemId = :itemId")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.CustomizationEntity> getCustomization(@org.jetbrains.annotations.NotNull
    java.lang.String itemId);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertOrUpdateCustomization(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.CustomizationEntity customization, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertOrUpdateCustomizations(@org.jetbrains.annotations.NotNull
    java.util.List<com.tetris.modern.rl.data.entities.CustomizationEntity> customizations, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE customizations SET isUnlocked = 1 WHERE itemId = :itemId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object unlockCustomization(@org.jetbrains.annotations.NotNull
    java.lang.String itemId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}