package com.tetris.modern.rl.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.tetris.modern.rl.data.dao.*
import com.tetris.modern.rl.data.entities.*

@Database(
    entities = [
        ScoreEntity::class,
        GameStateEntity::class,
        ProgressionEntity::class,
        AchievementEntity::class,
        PuzzleProgressEntity::class,
        SettingsEntity::class,
        DailyChallengeEntity::class,
        CustomizationEntity::class,
        UnlockableEntity::class,
        StatisticsEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TetrisDatabase : RoomDatabase() {
    
    abstract fun scoreDao(): ScoreDao
    abstract fun gameStateDao(): GameStateDao
    abstract fun progressionDao(): ProgressionDao
    abstract fun achievementDao(): AchievementDao
    abstract fun puzzleProgressDao(): PuzzleProgressDao
    abstract fun settingsDao(): SettingsDao
    abstract fun dailyChallengeDao(): DailyChallengeDao
    abstract fun customizationDao(): CustomizationDao
    abstract fun unlockableDao(): UnlockableDao
    abstract fun statisticsDao(): StatisticsDao
    
    companion object {
        @Volatile
        private var INSTANCE: TetrisDatabase? = null
        
        fun getDatabase(context: Context): TetrisDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TetrisDatabase::class.java,
                    "tetris_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}