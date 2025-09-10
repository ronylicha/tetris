package com.tetris.modern.rl.data.dao

import androidx.room.*
import com.tetris.modern.rl.data.entities.ScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores ORDER BY score DESC LIMIT :limit")
    fun getTopScores(limit: Int = 100): Flow<List<ScoreEntity>>
    
    @Query("SELECT * FROM scores WHERE gameMode = :gameMode ORDER BY score DESC LIMIT :limit")
    fun getTopScoresByMode(gameMode: String, limit: Int = 100): Flow<List<ScoreEntity>>
    
    @Query("SELECT * FROM scores WHERE playerName = :playerName ORDER BY score DESC")
    fun getPlayerScores(playerName: String): Flow<List<ScoreEntity>>
    
    @Insert
    suspend fun insertScore(score: ScoreEntity): Long
    
    @Update
    suspend fun updateScore(score: ScoreEntity)
    
    @Delete
    suspend fun deleteScore(score: ScoreEntity)
    
    @Query("DELETE FROM scores")
    suspend fun deleteAllScores()
    
    @Query("SELECT * FROM scores WHERE isSynced = 0")
    suspend fun getUnsyncedScores(): List<ScoreEntity>
    
    @Query("UPDATE scores SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<Long>)
    
    @Query("DELETE FROM scores WHERE timestamp < :timestamp")
    suspend fun deleteOldScores(timestamp: Long)
}