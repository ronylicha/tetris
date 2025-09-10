package com.tetris.modern.rl.data.dao;

import androidx.room.*;
import com.tetris.modern.rl.data.entities.DailyChallengeEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u000e\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u000e\u0010\n\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00032\u0006\u0010\f\u001a\u00020\rH\'J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0011\u00a8\u0006\u0012"}, d2 = {"Lcom/tetris/modern/rl/data/dao/DailyChallengeDao;", "", "getAllChallenges", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/tetris/modern/rl/data/entities/DailyChallengeEntity;", "getBestStreak", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCompletedChallenges", "getCurrentStreak", "getTodaysChallenge", "date", "", "insertOrUpdateChallenge", "", "challenge", "(Lcom/tetris/modern/rl/data/entities/DailyChallengeEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface DailyChallengeDao {
    
    @androidx.room.Query(value = "SELECT * FROM daily_challenges WHERE date = :date")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.DailyChallengeEntity> getTodaysChallenge(@org.jetbrains.annotations.NotNull
    java.lang.String date);
    
    @androidx.room.Query(value = "SELECT * FROM daily_challenges ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.DailyChallengeEntity>> getAllChallenges();
    
    @androidx.room.Query(value = "SELECT * FROM daily_challenges WHERE isCompleted = 1 ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.tetris.modern.rl.data.entities.DailyChallengeEntity>> getCompletedChallenges();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertOrUpdateChallenge(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.DailyChallengeEntity challenge, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT MAX(currentStreak) FROM daily_challenges")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getBestStreak(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT currentStreak FROM daily_challenges ORDER BY date DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getCurrentStreak(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}