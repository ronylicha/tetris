package com.tetris.modern.rl.data.dao

import androidx.room.*
import com.tetris.modern.rl.data.entities.GameStateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameStateDao {
    @Query("SELECT * FROM game_states WHERE id = 1")
    fun getCurrentGameState(): Flow<GameStateEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGameState(gameState: GameStateEntity)
    
    @Query("DELETE FROM game_states")
    suspend fun clearGameState()
}