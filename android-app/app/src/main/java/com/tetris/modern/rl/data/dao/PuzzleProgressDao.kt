package com.tetris.modern.rl.data.dao

import androidx.room.*
import com.tetris.modern.rl.data.entities.PuzzleProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PuzzleProgressDao {
    @Query("SELECT * FROM puzzle_progress")
    fun getAllPuzzleProgress(): Flow<List<PuzzleProgressEntity>>
    
    @Query("SELECT * FROM puzzle_progress WHERE puzzleId = :puzzleId")
    fun getPuzzleProgress(puzzleId: Int): Flow<PuzzleProgressEntity?>
    
    @Query("SELECT * FROM puzzle_progress WHERE isCompleted = 1")
    fun getCompletedPuzzles(): Flow<List<PuzzleProgressEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePuzzleProgress(progress: PuzzleProgressEntity)
    
    @Query("SELECT COUNT(*) FROM puzzle_progress WHERE isCompleted = 1")
    suspend fun getCompletedCount(): Int
    
    @Query("SELECT SUM(stars) FROM puzzle_progress")
    suspend fun getTotalStars(): Int
}