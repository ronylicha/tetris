package com.tetris.modern.rl.data.dao

import androidx.room.*
import com.tetris.modern.rl.data.entities.StatisticsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticsDao {
    @Query("SELECT * FROM statistics")
    fun getAllStatistics(): Flow<List<StatisticsEntity>>
    
    @Query("SELECT * FROM statistics WHERE gameMode = :mode")
    suspend fun getStatisticsByMode(mode: String): StatisticsEntity?
    
    @Query("SELECT * FROM statistics WHERE gameMode = :mode")
    fun observeStatisticsByMode(mode: String): Flow<StatisticsEntity?>
    
    @Query("SELECT SUM(totalGamesPlayed) FROM statistics")
    suspend fun getTotalGamesPlayed(): Int?
    
    @Query("SELECT SUM(totalLinesCleared) FROM statistics")
    suspend fun getTotalLinesCleared(): Int?
    
    @Query("SELECT MAX(highScore) FROM statistics")
    suspend fun getOverallHighScore(): Int?
    
    @Query("SELECT SUM(totalTimePlayed) FROM statistics")
    suspend fun getTotalTimePlayed(): Long?
    
    @Query("SELECT gameMode FROM statistics ORDER BY totalGamesPlayed DESC LIMIT 1")
    suspend fun getMostPlayedMode(): String?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatistics(statistics: StatisticsEntity)
    
    @Update
    suspend fun updateStatistics(statistics: StatisticsEntity)
    
    @Query("UPDATE statistics SET totalGamesPlayed = totalGamesPlayed + 1, lastPlayedTimestamp = :timestamp WHERE gameMode = :mode")
    suspend fun incrementGamesPlayed(mode: String, timestamp: Long = System.currentTimeMillis())
    
    @Query("UPDATE statistics SET totalLinesCleared = totalLinesCleared + :lines WHERE gameMode = :mode")
    suspend fun addLinesCleared(mode: String, lines: Int)
    
    @Query("UPDATE statistics SET highScore = :score WHERE gameMode = :mode AND :score > highScore")
    suspend fun updateHighScore(mode: String, score: Int)
    
    @Query("UPDATE statistics SET totalTimePlayed = totalTimePlayed + :time WHERE gameMode = :mode")
    suspend fun addTimePlayed(mode: String, time: Long)
    
    @Delete
    suspend fun deleteStatistics(statistics: StatisticsEntity)
    
    @Query("DELETE FROM statistics")
    suspend fun deleteAll()
    
    @Query("DELETE FROM statistics WHERE gameMode = :mode")
    suspend fun deleteByMode(mode: String)
}