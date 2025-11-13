package com.tetris.modern.rl.data.dao;

import androidx.room.*;
import com.tetris.modern.rl.data.entities.DailyStreakEntity;
import kotlinx.coroutines.flow.Flow;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\'J\u0010\u0010\b\u001a\u0004\u0018\u00010\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J&\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0018\u00a8\u0006\u0019"}, d2 = {"Lcom/tetris/modern/rl/data/dao/DailyStreakDao;", "", "clearAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStreak", "Lkotlinx/coroutines/flow/Flow;", "Lcom/tetris/modern/rl/data/entities/DailyStreakEntity;", "getStreakOnce", "incrementDailyChallenges", "date", "Ljava/util/Date;", "(Ljava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "incrementTotalDaysPlayed", "insertOrUpdateStreak", "streak", "(Lcom/tetris/modern/rl/data/entities/DailyStreakEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resetStreak", "updateCurrentStreak", "", "active", "", "(ILjava/util/Date;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLongestStreak", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface DailyStreakDao {
    
    @androidx.room.Query(value = "SELECT * FROM daily_streaks WHERE id = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.DailyStreakEntity> getStreak();
    
    @androidx.room.Query(value = "SELECT * FROM daily_streaks WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getStreakOnce(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.tetris.modern.rl.data.entities.DailyStreakEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertOrUpdateStreak(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.DailyStreakEntity streak, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE daily_streaks SET currentStreak = :streak, lastLoginDate = :date, isStreakActive = :active WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateCurrentStreak(int streak, @org.jetbrains.annotations.NotNull
    java.util.Date date, boolean active, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE daily_streaks SET longestStreak = :streak WHERE id = 1 AND :streak > longestStreak")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateLongestStreak(int streak, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE daily_streaks SET dailyChallengesCompleted = dailyChallengesCompleted + 1, lastCompletedChallengeDate = :date WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object incrementDailyChallenges(@org.jetbrains.annotations.NotNull
    java.util.Date date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE daily_streaks SET totalDaysPlayed = totalDaysPlayed + 1 WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object incrementTotalDaysPlayed(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE daily_streaks SET missedDays = missedDays + 1, isStreakActive = 0, currentStreak = 0 WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object resetStreak(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM daily_streaks")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object clearAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}