package com.tetris.modern.rl.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.tetris.modern.rl.data.entities.ScoreEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ScoreDao {
    // Basic queries with filtering and pagination
    @Query("""
        SELECT * FROM scores 
        WHERE (:gameMode IS NULL OR gameMode = :gameMode)
        ORDER BY score DESC 
        LIMIT :limit OFFSET :offset
    """)
    fun getTopScores(
        gameMode: String? = null,
        limit: Int = 100,
        offset: Int = 0
    ): Flow<List<ScoreEntity>>
    
    // Paging support for infinite scroll
    @Query("""
        SELECT * FROM scores 
        WHERE (:gameMode IS NULL OR gameMode = :gameMode)
        AND (:searchQuery IS NULL OR playerName LIKE '%' || :searchQuery || '%')
        ORDER BY score DESC
    """)
    fun getScoresPaged(
        gameMode: String? = null,
        searchQuery: String? = null
    ): PagingSource<Int, ScoreEntity>
    
    // Get scores by time period
    @Query("""
        SELECT * FROM scores 
        WHERE (:gameMode IS NULL OR gameMode = :gameMode)
        AND date >= :startDate
        ORDER BY score DESC 
        LIMIT :limit
    """)
    fun getScoresByPeriod(
        gameMode: String? = null,
        startDate: Date,
        limit: Int = 100
    ): Flow<List<ScoreEntity>>
    
    // Get all scores for a player
    @Query("""
        SELECT * FROM scores 
        WHERE playerName = :playerName
        ORDER BY score DESC
    """)
    fun getPlayerScores(playerName: String): Flow<List<ScoreEntity>>
    
    // Get player's best score per mode
    @Query("""
        SELECT * FROM scores s1
        WHERE s1.playerName = :playerName
        AND s1.score = (
            SELECT MAX(s2.score) 
            FROM scores s2 
            WHERE s2.playerName = s1.playerName 
            AND s2.gameMode = s1.gameMode
        )
        ORDER BY s1.gameMode
    """)
    fun getPlayerBestScores(playerName: String): Flow<List<ScoreEntity>>
    
    // Get all scores for sync
    @Query("SELECT * FROM scores")
    fun getAllScores(): Flow<List<ScoreEntity>>
    
    // Get player rank
    @Query("""
        SELECT COUNT(*) + 1 
        FROM scores 
        WHERE score > :score 
        AND (:gameMode IS NULL OR gameMode = :gameMode)
    """)
    suspend fun getPlayerRank(score: Int, gameMode: String? = null): Int
    
    // Search players
    @Query("""
        SELECT DISTINCT playerName 
        FROM scores 
        WHERE playerName LIKE '%' || :query || '%'
        LIMIT 10
    """)
    suspend fun searchPlayers(query: String): List<String>
    
    @Insert
    suspend fun insertScore(score: ScoreEntity): Long
    
    @Update
    suspend fun updateScore(score: ScoreEntity)
    
    @Delete
    suspend fun deleteScore(score: ScoreEntity)
    
    @Query("DELETE FROM scores")
    suspend fun deleteAllScores()
    
    @Query("DELETE FROM scores WHERE isSynced = 1")
    suspend fun deleteAllSyncedScores()
    
    // Sync related queries
    @Query("SELECT * FROM scores WHERE isSynced = 0")
    fun getUnsyncedScores(): Flow<List<ScoreEntity>>
    
    @Query("UPDATE scores SET isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long)
    
    @Query("UPDATE scores SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<Long>)
    
    @Query("DELETE FROM scores WHERE timestamp < :timestamp")
    suspend fun deleteOldScores(timestamp: Long)
    
    // Statistics queries
    @Query("""
        SELECT 
            COUNT(*) as gamesPlayed,
            SUM(score) as totalScore,
            MAX(score) as highestScore,
            SUM(lines) as totalLines,
            AVG(score) as averageScore
        FROM scores 
        WHERE playerName = :playerName
        AND (:gameMode IS NULL OR gameMode = :gameMode)
    """)
    suspend fun getPlayerStats(playerName: String, gameMode: String? = null): PlayerStats
    
    // Top 10 for quick display
    @Query("""
        SELECT * FROM scores 
        WHERE (:gameMode IS NULL OR gameMode = :gameMode)
        ORDER BY score DESC 
        LIMIT 10
    """)
    fun getTop10(gameMode: String? = null): Flow<List<ScoreEntity>>
    
    // Check if score is a personal best
    @Query("""
        SELECT COUNT(*) = 0 
        FROM scores 
        WHERE playerName = :playerName 
        AND gameMode = :gameMode 
        AND score > :score
    """)
    suspend fun isPersonalBest(playerName: String, gameMode: String, score: Int): Boolean
}

data class PlayerStats(
    val gamesPlayed: Int,
    val totalScore: Long,
    val highestScore: Int,
    val totalLines: Int,
    val averageScore: Double
)