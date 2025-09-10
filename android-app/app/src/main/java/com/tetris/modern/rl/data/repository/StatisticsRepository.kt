package com.tetris.modern.rl.data.repository

import com.tetris.modern.rl.data.dao.StatisticsDao
import com.tetris.modern.rl.data.entities.StatisticsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatisticsRepository @Inject constructor(
    private val statisticsDao: StatisticsDao
) {
    fun getStatisticsByMode(mode: String): Flow<StatisticsEntity?> = 
        statisticsDao.observeStatisticsByMode(mode)
    
    suspend fun getStatisticsForMode(mode: String): StatisticsEntity? {
        return statisticsDao.getStatisticsByMode(mode)
    }
    
    suspend fun updateStatistics(statistics: StatisticsEntity) {
        statisticsDao.updateStatistics(statistics)
    }
    
    suspend fun recordGameStats(
        gameMode: String,
        lines: Int,
        score: Int,
        playTime: Long,
        pieces: Int,
        tetrises: Int,
        tSpins: Int,
        perfectClears: Int,
        maxCombo: Int,
        level: Int
    ) {
        val current = statisticsDao.getStatisticsByMode(gameMode)
        
        if (current == null) {
            // Create new statistics for this mode
            val newStats = StatisticsEntity(
                gameMode = gameMode,
                totalGamesPlayed = 1,
                totalLinesCleared = lines,
                totalScore = score.toLong(),
                highScore = score,
                bestCombo = maxCombo,
                totalPiecesPlaced = pieces,
                totalTSpins = tSpins,
                totalTetrisClears = tetrises,
                totalPerfectClears = perfectClears,
                totalTimePlayed = playTime,
                averageScore = score.toFloat(),
                averageLinesPerGame = lines.toFloat(),
                highestLevel = level,
                lastPlayedTimestamp = System.currentTimeMillis()
            )
            statisticsDao.insertStatistics(newStats)
        } else {
            // Update existing statistics
            val updatedStats = current.copy(
                totalGamesPlayed = current.totalGamesPlayed + 1,
                totalLinesCleared = current.totalLinesCleared + lines,
                totalScore = current.totalScore + score,
                highScore = maxOf(current.highScore, score),
                bestCombo = maxOf(current.bestCombo, maxCombo),
                totalPiecesPlaced = current.totalPiecesPlaced + pieces,
                totalTSpins = current.totalTSpins + tSpins,
                totalTetrisClears = current.totalTetrisClears + tetrises,
                totalPerfectClears = current.totalPerfectClears + perfectClears,
                totalTimePlayed = current.totalTimePlayed + playTime,
                averageScore = (current.totalScore + score).toFloat() / (current.totalGamesPlayed + 1),
                averageLinesPerGame = (current.totalLinesCleared + lines).toFloat() / (current.totalGamesPlayed + 1),
                highestLevel = maxOf(current.highestLevel, level),
                lastPlayedTimestamp = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            statisticsDao.updateStatistics(updatedStats)
        }
        
        // Also update overall statistics (if not already updating overall)
        if (gameMode != "overall") {
            val overallStats = statisticsDao.getStatisticsByMode("overall")
            if (overallStats == null) {
                // Create new overall statistics
                val newOverallStats = StatisticsEntity(
                    gameMode = "overall",
                    totalGamesPlayed = 1,
                    totalLinesCleared = lines,
                    totalScore = score.toLong(),
                    highScore = score,
                    bestCombo = maxCombo,
                    totalPiecesPlaced = pieces,
                    totalTSpins = tSpins,
                    totalTetrisClears = tetrises,
                    totalPerfectClears = perfectClears,
                    totalTimePlayed = playTime,
                    averageScore = score.toFloat(),
                    averageLinesPerGame = lines.toFloat(),
                    highestLevel = level,
                    lastPlayedTimestamp = System.currentTimeMillis()
                )
                statisticsDao.insertStatistics(newOverallStats)
            } else {
                // Update existing overall statistics
                val updatedOverallStats = overallStats.copy(
                    totalGamesPlayed = overallStats.totalGamesPlayed + 1,
                    totalLinesCleared = overallStats.totalLinesCleared + lines,
                    totalScore = overallStats.totalScore + score,
                    highScore = maxOf(overallStats.highScore, score),
                    bestCombo = maxOf(overallStats.bestCombo, maxCombo),
                    totalPiecesPlaced = overallStats.totalPiecesPlaced + pieces,
                    totalTSpins = overallStats.totalTSpins + tSpins,
                    totalTetrisClears = overallStats.totalTetrisClears + tetrises,
                    totalPerfectClears = overallStats.totalPerfectClears + perfectClears,
                    totalTimePlayed = overallStats.totalTimePlayed + playTime,
                    averageScore = (overallStats.totalScore + score).toFloat() / (overallStats.totalGamesPlayed + 1),
                    averageLinesPerGame = (overallStats.totalLinesCleared + lines).toFloat() / (overallStats.totalGamesPlayed + 1),
                    highestLevel = maxOf(overallStats.highestLevel, level),
                    lastPlayedTimestamp = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                statisticsDao.updateStatistics(updatedOverallStats)
            }
        }
    }
    
    suspend fun incrementGamesPlayed(mode: String) {
        statisticsDao.incrementGamesPlayed(mode)
    }
    
    suspend fun updateHighScore(mode: String, score: Int) {
        statisticsDao.updateHighScore(mode, score)
    }
    
    suspend fun addLinesCleared(mode: String, lines: Int) {
        statisticsDao.addLinesCleared(mode, lines)
    }
    
    suspend fun addTimePlayed(mode: String, time: Long) {
        statisticsDao.addTimePlayed(mode, time)
    }
    
    suspend fun getTotalGamesPlayed(): Int {
        return statisticsDao.getTotalGamesPlayed() ?: 0
    }
    
    suspend fun getTotalLinesCleared(): Int {
        return statisticsDao.getTotalLinesCleared() ?: 0
    }
    
    suspend fun getOverallHighScore(): Int {
        return statisticsDao.getOverallHighScore() ?: 0
    }
}