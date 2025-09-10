package com.tetris.modern.rl.di

import android.content.Context
import androidx.room.Room
import com.tetris.modern.rl.data.dao.*
import com.tetris.modern.rl.data.database.TetrisDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TetrisDatabase {
        return Room.databaseBuilder(
            context,
            TetrisDatabase::class.java,
            "tetris_database"
        ).fallbackToDestructiveMigration()
         .build()
    }
    
    @Provides
    fun provideScoreDao(database: TetrisDatabase): ScoreDao {
        return database.scoreDao()
    }
    
    @Provides
    fun provideSettingsDao(database: TetrisDatabase): SettingsDao {
        return database.settingsDao()
    }
    
    @Provides
    fun provideProgressionDao(database: TetrisDatabase): ProgressionDao {
        return database.progressionDao()
    }
    
    @Provides
    fun provideAchievementDao(database: TetrisDatabase): AchievementDao {
        return database.achievementDao()
    }
    
    @Provides
    fun provideDailyChallengeDao(database: TetrisDatabase): DailyChallengeDao {
        return database.dailyChallengeDao()
    }
    
    @Provides
    fun provideUnlockableDao(database: TetrisDatabase): UnlockableDao {
        return database.unlockableDao()
    }
    
    @Provides
    fun providePuzzleProgressDao(database: TetrisDatabase): PuzzleProgressDao {
        return database.puzzleProgressDao()
    }
    
    @Provides
    fun provideStatisticsDao(database: TetrisDatabase): StatisticsDao {
        return database.statisticsDao()
    }
}