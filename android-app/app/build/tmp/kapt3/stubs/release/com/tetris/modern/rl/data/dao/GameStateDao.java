package com.tetris.modern.rl.data.dao;

import androidx.room.*;
import com.tetris.modern.rl.data.entities.GameStateEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\'J\u0016\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/tetris/modern/rl/data/dao/GameStateDao;", "", "clearGameState", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentGameState", "Lkotlinx/coroutines/flow/Flow;", "Lcom/tetris/modern/rl/data/entities/GameStateEntity;", "saveGameState", "gameState", "(Lcom/tetris/modern/rl/data/entities/GameStateEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
@androidx.room.Dao
public abstract interface GameStateDao {
    
    @androidx.room.Query(value = "SELECT * FROM game_states WHERE id = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.GameStateEntity> getCurrentGameState();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveGameState(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.GameStateEntity gameState, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM game_states")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object clearGameState(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}