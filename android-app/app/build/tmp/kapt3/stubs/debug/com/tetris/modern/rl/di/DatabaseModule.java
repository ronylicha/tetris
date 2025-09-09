package com.tetris.modern.rl.di;

import android.content.Context;
import androidx.room.Room;
import com.tetris.modern.rl.data.dao.*;
import com.tetris.modern.rl.data.database.TetrisDatabase;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0018"}, d2 = {"Lcom/tetris/modern/rl/di/DatabaseModule;", "", "()V", "provideAchievementDao", "Lcom/tetris/modern/rl/data/dao/AchievementDao;", "database", "Lcom/tetris/modern/rl/data/database/TetrisDatabase;", "provideDailyChallengeDao", "Lcom/tetris/modern/rl/data/dao/DailyChallengeDao;", "provideDatabase", "context", "Landroid/content/Context;", "provideProgressionDao", "Lcom/tetris/modern/rl/data/dao/ProgressionDao;", "providePuzzleProgressDao", "Lcom/tetris/modern/rl/data/dao/PuzzleProgressDao;", "provideScoreDao", "Lcom/tetris/modern/rl/data/dao/ScoreDao;", "provideSettingsDao", "Lcom/tetris/modern/rl/data/dao/SettingsDao;", "provideStatisticsDao", "Lcom/tetris/modern/rl/data/dao/StatisticsDao;", "provideUnlockableDao", "Lcom/tetris/modern/rl/data/dao/UnlockableDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.di.DatabaseModule INSTANCE = null;
    
    private DatabaseModule() {
        super();
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.database.TetrisDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.dao.ScoreDao provideScoreDao(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.database.TetrisDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.dao.SettingsDao provideSettingsDao(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.database.TetrisDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.dao.ProgressionDao provideProgressionDao(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.database.TetrisDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.dao.AchievementDao provideAchievementDao(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.database.TetrisDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.dao.DailyChallengeDao provideDailyChallengeDao(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.database.TetrisDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.dao.UnlockableDao provideUnlockableDao(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.database.TetrisDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.dao.PuzzleProgressDao providePuzzleProgressDao(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.database.TetrisDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.data.dao.StatisticsDao provideStatisticsDao(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.database.TetrisDatabase database) {
        return null;
    }
}