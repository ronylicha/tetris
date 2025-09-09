package com.tetris.modern.rl.data.dao;

import androidx.room.*;
import com.tetris.modern.rl.data.entities.AchievementEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u0014\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\u00020\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u001e\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u0019\u00a8\u0006\u001a"}, d2 = {"Lcom/tetris/modern/rl/data/dao/AchievementDao;", "", "getAchievementsByCategory", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/tetris/modern/rl/data/entities/AchievementEntity;", "category", "", "getAllAchievements", "getUnlockedAchievements", "insertOrUpdateAchievement", "", "achievement", "(Lcom/tetris/modern/rl/data/entities/AchievementEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertOrUpdateAchievements", "achievements", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unlockAchievement", "id", "date", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProgress", "progress", "", "(Ljava/lang/String;FLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
@androidx.room.Dao
public abstract interface AchievementDao {
    
    @androidx.room.Query(value = "SELECT * FROM achievements")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.AchievementEntity>> getAllAchievements();
    
    @androidx.room.Query(value = "SELECT * FROM achievements WHERE isUnlocked = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.AchievementEntity>> getUnlockedAchievements();
    
    @androidx.room.Query(value = "SELECT * FROM achievements WHERE category = :category")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.AchievementEntity>> getAchievementsByCategory(@org.jetbrains.annotations.NotNull
    java.lang.String category);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertOrUpdateAchievement(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.AchievementEntity achievement, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertOrUpdateAchievements(@org.jetbrains.annotations.NotNull
    java.util.List<com.tetris.modern.rl.data.entities.AchievementEntity> achievements, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE achievements SET isUnlocked = 1, unlockedDate = :date WHERE achievementId = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object unlockAchievement(@org.jetbrains.annotations.NotNull
    java.lang.String id, long date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE achievements SET progress = :progress WHERE achievementId = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateProgress(@org.jetbrains.annotations.NotNull
    java.lang.String id, float progress, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}