package com.tetris.modern.rl.data.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;
import com.tetris.modern.rl.data.dao.*;
import com.tetris.modern.rl.data.entities.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0016H&\u00a8\u0006\u0018"}, d2 = {"Lcom/tetris/modern/rl/data/database/TetrisDatabase;", "Landroidx/room/RoomDatabase;", "()V", "achievementDao", "Lcom/tetris/modern/rl/data/dao/AchievementDao;", "customizationDao", "Lcom/tetris/modern/rl/data/dao/CustomizationDao;", "dailyChallengeDao", "Lcom/tetris/modern/rl/data/dao/DailyChallengeDao;", "gameStateDao", "Lcom/tetris/modern/rl/data/dao/GameStateDao;", "progressionDao", "Lcom/tetris/modern/rl/data/dao/ProgressionDao;", "puzzleProgressDao", "Lcom/tetris/modern/rl/data/dao/PuzzleProgressDao;", "scoreDao", "Lcom/tetris/modern/rl/data/dao/ScoreDao;", "settingsDao", "Lcom/tetris/modern/rl/data/dao/SettingsDao;", "statisticsDao", "Lcom/tetris/modern/rl/data/dao/StatisticsDao;", "unlockableDao", "Lcom/tetris/modern/rl/data/dao/UnlockableDao;", "Companion", "app_release"})
@androidx.room.Database(entities = {com.tetris.modern.rl.data.entities.ScoreEntity.class, com.tetris.modern.rl.data.entities.GameStateEntity.class, com.tetris.modern.rl.data.entities.ProgressionEntity.class, com.tetris.modern.rl.data.entities.AchievementEntity.class, com.tetris.modern.rl.data.entities.PuzzleProgressEntity.class, com.tetris.modern.rl.data.entities.SettingsEntity.class, com.tetris.modern.rl.data.entities.DailyChallengeEntity.class, com.tetris.modern.rl.data.entities.CustomizationEntity.class, com.tetris.modern.rl.data.entities.UnlockableEntity.class, com.tetris.modern.rl.data.entities.StatisticsEntity.class}, version = 2, exportSchema = false)
@androidx.room.TypeConverters(value = {com.tetris.modern.rl.data.database.Converters.class})
public abstract class TetrisDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile
    @org.jetbrains.annotations.Nullable
    private static volatile com.tetris.modern.rl.data.database.TetrisDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.data.database.TetrisDatabase.Companion Companion = null;
    
    public TetrisDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.tetris.modern.rl.data.dao.ScoreDao scoreDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.tetris.modern.rl.data.dao.GameStateDao gameStateDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.tetris.modern.rl.data.dao.ProgressionDao progressionDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.tetris.modern.rl.data.dao.AchievementDao achievementDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.tetris.modern.rl.data.dao.PuzzleProgressDao puzzleProgressDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.tetris.modern.rl.data.dao.SettingsDao settingsDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.tetris.modern.rl.data.dao.DailyChallengeDao dailyChallengeDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.tetris.modern.rl.data.dao.CustomizationDao customizationDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.tetris.modern.rl.data.dao.UnlockableDao unlockableDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.tetris.modern.rl.data.dao.StatisticsDao statisticsDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/tetris/modern/rl/data/database/TetrisDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/tetris/modern/rl/data/database/TetrisDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.data.database.TetrisDatabase getDatabase(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
    }
}