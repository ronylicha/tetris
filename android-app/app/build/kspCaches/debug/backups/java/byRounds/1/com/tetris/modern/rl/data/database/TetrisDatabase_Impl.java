package com.tetris.modern.rl.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.tetris.modern.rl.data.dao.AchievementDao;
import com.tetris.modern.rl.data.dao.AchievementDao_Impl;
import com.tetris.modern.rl.data.dao.CustomizationDao;
import com.tetris.modern.rl.data.dao.CustomizationDao_Impl;
import com.tetris.modern.rl.data.dao.DailyChallengeDao;
import com.tetris.modern.rl.data.dao.DailyChallengeDao_Impl;
import com.tetris.modern.rl.data.dao.GameStateDao;
import com.tetris.modern.rl.data.dao.GameStateDao_Impl;
import com.tetris.modern.rl.data.dao.ProgressionDao;
import com.tetris.modern.rl.data.dao.ProgressionDao_Impl;
import com.tetris.modern.rl.data.dao.PuzzleProgressDao;
import com.tetris.modern.rl.data.dao.PuzzleProgressDao_Impl;
import com.tetris.modern.rl.data.dao.ScoreDao;
import com.tetris.modern.rl.data.dao.ScoreDao_Impl;
import com.tetris.modern.rl.data.dao.SettingsDao;
import com.tetris.modern.rl.data.dao.SettingsDao_Impl;
import com.tetris.modern.rl.data.dao.StatisticsDao;
import com.tetris.modern.rl.data.dao.StatisticsDao_Impl;
import com.tetris.modern.rl.data.dao.UnlockableDao;
import com.tetris.modern.rl.data.dao.UnlockableDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TetrisDatabase_Impl extends TetrisDatabase {
  private volatile ScoreDao _scoreDao;

  private volatile GameStateDao _gameStateDao;

  private volatile ProgressionDao _progressionDao;

  private volatile AchievementDao _achievementDao;

  private volatile PuzzleProgressDao _puzzleProgressDao;

  private volatile SettingsDao _settingsDao;

  private volatile DailyChallengeDao _dailyChallengeDao;

  private volatile CustomizationDao _customizationDao;

  private volatile UnlockableDao _unlockableDao;

  private volatile StatisticsDao _statisticsDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `scores` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `playerName` TEXT NOT NULL, `score` INTEGER NOT NULL, `level` INTEGER NOT NULL, `lines` INTEGER NOT NULL, `gameMode` TEXT NOT NULL, `date` INTEGER NOT NULL, `duration` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `game_states` (`id` INTEGER NOT NULL, `grid` TEXT NOT NULL, `currentPiece` TEXT NOT NULL, `nextPieces` TEXT NOT NULL, `heldPiece` TEXT, `score` INTEGER NOT NULL, `level` INTEGER NOT NULL, `lines` INTEGER NOT NULL, `gameMode` TEXT NOT NULL, `isPaused` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `progression` (`id` INTEGER NOT NULL, `currentLevel` INTEGER NOT NULL, `currentXp` INTEGER NOT NULL, `totalXp` INTEGER NOT NULL, `currentRank` TEXT NOT NULL, `unlockedThemes` TEXT NOT NULL, `unlockedMusic` TEXT NOT NULL, `unlockedPieceStyles` TEXT NOT NULL, `unlockedEffects` TEXT NOT NULL, `statistics` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `achievements` (`achievementId` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `category` TEXT NOT NULL, `xpReward` INTEGER NOT NULL, `isUnlocked` INTEGER NOT NULL, `unlockedDate` INTEGER, `progress` REAL NOT NULL, `maxProgress` REAL NOT NULL, PRIMARY KEY(`achievementId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `puzzle_progress` (`puzzleId` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `bestScore` INTEGER NOT NULL, `stars` INTEGER NOT NULL, `attempts` INTEGER NOT NULL, `completionTime` INTEGER, `hintsUsed` INTEGER NOT NULL, PRIMARY KEY(`puzzleId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `settings` (`id` INTEGER NOT NULL, `masterVolume` REAL NOT NULL, `musicVolume` REAL NOT NULL, `sfxVolume` REAL NOT NULL, `vibrationEnabled` INTEGER NOT NULL, `touchSensitivity` REAL NOT NULL, `selectedTheme` TEXT NOT NULL, `selectedMusic` TEXT NOT NULL, `selectedPieceStyle` TEXT NOT NULL, `selectedEffects` TEXT NOT NULL, `showGhostPiece` INTEGER NOT NULL, `showNextPieces` INTEGER NOT NULL, `autoHold` INTEGER NOT NULL, `das` INTEGER NOT NULL, `arr` INTEGER NOT NULL, `isDarkMode` INTEGER NOT NULL, `useDynamicColors` INTEGER NOT NULL, `isMuted` INTEGER NOT NULL, `controlType` TEXT NOT NULL, `isFirstLaunch` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `daily_challenges` (`date` TEXT NOT NULL, `seed` INTEGER NOT NULL, `modifiers` TEXT NOT NULL, `targetScore` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `playerScore` INTEGER NOT NULL, `completionTime` INTEGER, `attempts` INTEGER NOT NULL, `currentStreak` INTEGER NOT NULL, `bestStreak` INTEGER NOT NULL, PRIMARY KEY(`date`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `customizations` (`itemId` TEXT NOT NULL, `type` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `isUnlocked` INTEGER NOT NULL, `unlockLevel` INTEGER NOT NULL, `previewImage` TEXT, `data` TEXT, PRIMARY KEY(`itemId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `unlockables` (`id` TEXT NOT NULL, `type` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `requiredLevel` INTEGER NOT NULL, `requiredXP` INTEGER NOT NULL, `requiredAchievement` TEXT, `cost` INTEGER NOT NULL, `isUnlocked` INTEGER NOT NULL, `isSelected` INTEGER NOT NULL, `metadata` TEXT, `iconResource` TEXT, `previewResource` TEXT, `rarity` TEXT NOT NULL, `orderIndex` INTEGER NOT NULL, `unlockedAt` INTEGER, `usageCount` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `statistics` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `gameMode` TEXT NOT NULL, `totalGamesPlayed` INTEGER NOT NULL, `totalLinesCleared` INTEGER NOT NULL, `totalScore` INTEGER NOT NULL, `highScore` INTEGER NOT NULL, `bestCombo` INTEGER NOT NULL, `totalPiecesPlaced` INTEGER NOT NULL, `totalTSpins` INTEGER NOT NULL, `totalTetrisClears` INTEGER NOT NULL, `totalPerfectClears` INTEGER NOT NULL, `totalTimePlayed` INTEGER NOT NULL, `averageScore` REAL NOT NULL, `averageLinesPerGame` REAL NOT NULL, `winRate` REAL NOT NULL, `longestSurvivalTime` INTEGER NOT NULL, `fastestSprint40` INTEGER NOT NULL, `highestLevel` INTEGER NOT NULL, `totalXPEarned` INTEGER NOT NULL, `favoriteMode` TEXT, `lastPlayedTimestamp` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c8c3a47767820b5a36573011b623d68c')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `scores`");
        db.execSQL("DROP TABLE IF EXISTS `game_states`");
        db.execSQL("DROP TABLE IF EXISTS `progression`");
        db.execSQL("DROP TABLE IF EXISTS `achievements`");
        db.execSQL("DROP TABLE IF EXISTS `puzzle_progress`");
        db.execSQL("DROP TABLE IF EXISTS `settings`");
        db.execSQL("DROP TABLE IF EXISTS `daily_challenges`");
        db.execSQL("DROP TABLE IF EXISTS `customizations`");
        db.execSQL("DROP TABLE IF EXISTS `unlockables`");
        db.execSQL("DROP TABLE IF EXISTS `statistics`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsScores = new HashMap<String, TableInfo.Column>(10);
        _columnsScores.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScores.put("playerName", new TableInfo.Column("playerName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScores.put("score", new TableInfo.Column("score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScores.put("level", new TableInfo.Column("level", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScores.put("lines", new TableInfo.Column("lines", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScores.put("gameMode", new TableInfo.Column("gameMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScores.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScores.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScores.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScores.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysScores = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesScores = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoScores = new TableInfo("scores", _columnsScores, _foreignKeysScores, _indicesScores);
        final TableInfo _existingScores = TableInfo.read(db, "scores");
        if (!_infoScores.equals(_existingScores)) {
          return new RoomOpenHelper.ValidationResult(false, "scores(com.tetris.modern.rl.data.entities.ScoreEntity).\n"
                  + " Expected:\n" + _infoScores + "\n"
                  + " Found:\n" + _existingScores);
        }
        final HashMap<String, TableInfo.Column> _columnsGameStates = new HashMap<String, TableInfo.Column>(11);
        _columnsGameStates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("grid", new TableInfo.Column("grid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("currentPiece", new TableInfo.Column("currentPiece", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("nextPieces", new TableInfo.Column("nextPieces", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("heldPiece", new TableInfo.Column("heldPiece", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("score", new TableInfo.Column("score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("level", new TableInfo.Column("level", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("lines", new TableInfo.Column("lines", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("gameMode", new TableInfo.Column("gameMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("isPaused", new TableInfo.Column("isPaused", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGameStates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGameStates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGameStates = new TableInfo("game_states", _columnsGameStates, _foreignKeysGameStates, _indicesGameStates);
        final TableInfo _existingGameStates = TableInfo.read(db, "game_states");
        if (!_infoGameStates.equals(_existingGameStates)) {
          return new RoomOpenHelper.ValidationResult(false, "game_states(com.tetris.modern.rl.data.entities.GameStateEntity).\n"
                  + " Expected:\n" + _infoGameStates + "\n"
                  + " Found:\n" + _existingGameStates);
        }
        final HashMap<String, TableInfo.Column> _columnsProgression = new HashMap<String, TableInfo.Column>(10);
        _columnsProgression.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgression.put("currentLevel", new TableInfo.Column("currentLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgression.put("currentXp", new TableInfo.Column("currentXp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgression.put("totalXp", new TableInfo.Column("totalXp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgression.put("currentRank", new TableInfo.Column("currentRank", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgression.put("unlockedThemes", new TableInfo.Column("unlockedThemes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgression.put("unlockedMusic", new TableInfo.Column("unlockedMusic", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgression.put("unlockedPieceStyles", new TableInfo.Column("unlockedPieceStyles", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgression.put("unlockedEffects", new TableInfo.Column("unlockedEffects", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgression.put("statistics", new TableInfo.Column("statistics", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProgression = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProgression = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProgression = new TableInfo("progression", _columnsProgression, _foreignKeysProgression, _indicesProgression);
        final TableInfo _existingProgression = TableInfo.read(db, "progression");
        if (!_infoProgression.equals(_existingProgression)) {
          return new RoomOpenHelper.ValidationResult(false, "progression(com.tetris.modern.rl.data.entities.ProgressionEntity).\n"
                  + " Expected:\n" + _infoProgression + "\n"
                  + " Found:\n" + _existingProgression);
        }
        final HashMap<String, TableInfo.Column> _columnsAchievements = new HashMap<String, TableInfo.Column>(9);
        _columnsAchievements.put("achievementId", new TableInfo.Column("achievementId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("xpReward", new TableInfo.Column("xpReward", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("unlockedDate", new TableInfo.Column("unlockedDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("progress", new TableInfo.Column("progress", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("maxProgress", new TableInfo.Column("maxProgress", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAchievements = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAchievements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAchievements = new TableInfo("achievements", _columnsAchievements, _foreignKeysAchievements, _indicesAchievements);
        final TableInfo _existingAchievements = TableInfo.read(db, "achievements");
        if (!_infoAchievements.equals(_existingAchievements)) {
          return new RoomOpenHelper.ValidationResult(false, "achievements(com.tetris.modern.rl.data.entities.AchievementEntity).\n"
                  + " Expected:\n" + _infoAchievements + "\n"
                  + " Found:\n" + _existingAchievements);
        }
        final HashMap<String, TableInfo.Column> _columnsPuzzleProgress = new HashMap<String, TableInfo.Column>(7);
        _columnsPuzzleProgress.put("puzzleId", new TableInfo.Column("puzzleId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPuzzleProgress.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPuzzleProgress.put("bestScore", new TableInfo.Column("bestScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPuzzleProgress.put("stars", new TableInfo.Column("stars", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPuzzleProgress.put("attempts", new TableInfo.Column("attempts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPuzzleProgress.put("completionTime", new TableInfo.Column("completionTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPuzzleProgress.put("hintsUsed", new TableInfo.Column("hintsUsed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPuzzleProgress = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPuzzleProgress = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPuzzleProgress = new TableInfo("puzzle_progress", _columnsPuzzleProgress, _foreignKeysPuzzleProgress, _indicesPuzzleProgress);
        final TableInfo _existingPuzzleProgress = TableInfo.read(db, "puzzle_progress");
        if (!_infoPuzzleProgress.equals(_existingPuzzleProgress)) {
          return new RoomOpenHelper.ValidationResult(false, "puzzle_progress(com.tetris.modern.rl.data.entities.PuzzleProgressEntity).\n"
                  + " Expected:\n" + _infoPuzzleProgress + "\n"
                  + " Found:\n" + _existingPuzzleProgress);
        }
        final HashMap<String, TableInfo.Column> _columnsSettings = new HashMap<String, TableInfo.Column>(20);
        _columnsSettings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("masterVolume", new TableInfo.Column("masterVolume", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("musicVolume", new TableInfo.Column("musicVolume", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("sfxVolume", new TableInfo.Column("sfxVolume", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("vibrationEnabled", new TableInfo.Column("vibrationEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("touchSensitivity", new TableInfo.Column("touchSensitivity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("selectedTheme", new TableInfo.Column("selectedTheme", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("selectedMusic", new TableInfo.Column("selectedMusic", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("selectedPieceStyle", new TableInfo.Column("selectedPieceStyle", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("selectedEffects", new TableInfo.Column("selectedEffects", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("showGhostPiece", new TableInfo.Column("showGhostPiece", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("showNextPieces", new TableInfo.Column("showNextPieces", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("autoHold", new TableInfo.Column("autoHold", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("das", new TableInfo.Column("das", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("arr", new TableInfo.Column("arr", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("isDarkMode", new TableInfo.Column("isDarkMode", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("useDynamicColors", new TableInfo.Column("useDynamicColors", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("isMuted", new TableInfo.Column("isMuted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("controlType", new TableInfo.Column("controlType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSettings.put("isFirstLaunch", new TableInfo.Column("isFirstLaunch", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSettings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSettings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSettings = new TableInfo("settings", _columnsSettings, _foreignKeysSettings, _indicesSettings);
        final TableInfo _existingSettings = TableInfo.read(db, "settings");
        if (!_infoSettings.equals(_existingSettings)) {
          return new RoomOpenHelper.ValidationResult(false, "settings(com.tetris.modern.rl.data.entities.SettingsEntity).\n"
                  + " Expected:\n" + _infoSettings + "\n"
                  + " Found:\n" + _existingSettings);
        }
        final HashMap<String, TableInfo.Column> _columnsDailyChallenges = new HashMap<String, TableInfo.Column>(10);
        _columnsDailyChallenges.put("date", new TableInfo.Column("date", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("seed", new TableInfo.Column("seed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("modifiers", new TableInfo.Column("modifiers", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("targetScore", new TableInfo.Column("targetScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("playerScore", new TableInfo.Column("playerScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("completionTime", new TableInfo.Column("completionTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("attempts", new TableInfo.Column("attempts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("currentStreak", new TableInfo.Column("currentStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("bestStreak", new TableInfo.Column("bestStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDailyChallenges = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDailyChallenges = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDailyChallenges = new TableInfo("daily_challenges", _columnsDailyChallenges, _foreignKeysDailyChallenges, _indicesDailyChallenges);
        final TableInfo _existingDailyChallenges = TableInfo.read(db, "daily_challenges");
        if (!_infoDailyChallenges.equals(_existingDailyChallenges)) {
          return new RoomOpenHelper.ValidationResult(false, "daily_challenges(com.tetris.modern.rl.data.entities.DailyChallengeEntity).\n"
                  + " Expected:\n" + _infoDailyChallenges + "\n"
                  + " Found:\n" + _existingDailyChallenges);
        }
        final HashMap<String, TableInfo.Column> _columnsCustomizations = new HashMap<String, TableInfo.Column>(8);
        _columnsCustomizations.put("itemId", new TableInfo.Column("itemId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomizations.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomizations.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomizations.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomizations.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomizations.put("unlockLevel", new TableInfo.Column("unlockLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomizations.put("previewImage", new TableInfo.Column("previewImage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomizations.put("data", new TableInfo.Column("data", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCustomizations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCustomizations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCustomizations = new TableInfo("customizations", _columnsCustomizations, _foreignKeysCustomizations, _indicesCustomizations);
        final TableInfo _existingCustomizations = TableInfo.read(db, "customizations");
        if (!_infoCustomizations.equals(_existingCustomizations)) {
          return new RoomOpenHelper.ValidationResult(false, "customizations(com.tetris.modern.rl.data.entities.CustomizationEntity).\n"
                  + " Expected:\n" + _infoCustomizations + "\n"
                  + " Found:\n" + _existingCustomizations);
        }
        final HashMap<String, TableInfo.Column> _columnsUnlockables = new HashMap<String, TableInfo.Column>(17);
        _columnsUnlockables.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("requiredLevel", new TableInfo.Column("requiredLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("requiredXP", new TableInfo.Column("requiredXP", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("requiredAchievement", new TableInfo.Column("requiredAchievement", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("cost", new TableInfo.Column("cost", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("isSelected", new TableInfo.Column("isSelected", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("metadata", new TableInfo.Column("metadata", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("iconResource", new TableInfo.Column("iconResource", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("previewResource", new TableInfo.Column("previewResource", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("rarity", new TableInfo.Column("rarity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("orderIndex", new TableInfo.Column("orderIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("unlockedAt", new TableInfo.Column("unlockedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockables.put("usageCount", new TableInfo.Column("usageCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUnlockables = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUnlockables = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUnlockables = new TableInfo("unlockables", _columnsUnlockables, _foreignKeysUnlockables, _indicesUnlockables);
        final TableInfo _existingUnlockables = TableInfo.read(db, "unlockables");
        if (!_infoUnlockables.equals(_existingUnlockables)) {
          return new RoomOpenHelper.ValidationResult(false, "unlockables(com.tetris.modern.rl.data.entities.UnlockableEntity).\n"
                  + " Expected:\n" + _infoUnlockables + "\n"
                  + " Found:\n" + _existingUnlockables);
        }
        final HashMap<String, TableInfo.Column> _columnsStatistics = new HashMap<String, TableInfo.Column>(23);
        _columnsStatistics.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("gameMode", new TableInfo.Column("gameMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalGamesPlayed", new TableInfo.Column("totalGamesPlayed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalLinesCleared", new TableInfo.Column("totalLinesCleared", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalScore", new TableInfo.Column("totalScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("highScore", new TableInfo.Column("highScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("bestCombo", new TableInfo.Column("bestCombo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalPiecesPlaced", new TableInfo.Column("totalPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalTSpins", new TableInfo.Column("totalTSpins", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalTetrisClears", new TableInfo.Column("totalTetrisClears", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalPerfectClears", new TableInfo.Column("totalPerfectClears", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalTimePlayed", new TableInfo.Column("totalTimePlayed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("averageScore", new TableInfo.Column("averageScore", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("averageLinesPerGame", new TableInfo.Column("averageLinesPerGame", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("winRate", new TableInfo.Column("winRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("longestSurvivalTime", new TableInfo.Column("longestSurvivalTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("fastestSprint40", new TableInfo.Column("fastestSprint40", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("highestLevel", new TableInfo.Column("highestLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalXPEarned", new TableInfo.Column("totalXPEarned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("favoriteMode", new TableInfo.Column("favoriteMode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("lastPlayedTimestamp", new TableInfo.Column("lastPlayedTimestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStatistics = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStatistics = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStatistics = new TableInfo("statistics", _columnsStatistics, _foreignKeysStatistics, _indicesStatistics);
        final TableInfo _existingStatistics = TableInfo.read(db, "statistics");
        if (!_infoStatistics.equals(_existingStatistics)) {
          return new RoomOpenHelper.ValidationResult(false, "statistics(com.tetris.modern.rl.data.entities.StatisticsEntity).\n"
                  + " Expected:\n" + _infoStatistics + "\n"
                  + " Found:\n" + _existingStatistics);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "c8c3a47767820b5a36573011b623d68c", "699de8867df83fb923e9156c818902b0");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "scores","game_states","progression","achievements","puzzle_progress","settings","daily_challenges","customizations","unlockables","statistics");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `scores`");
      _db.execSQL("DELETE FROM `game_states`");
      _db.execSQL("DELETE FROM `progression`");
      _db.execSQL("DELETE FROM `achievements`");
      _db.execSQL("DELETE FROM `puzzle_progress`");
      _db.execSQL("DELETE FROM `settings`");
      _db.execSQL("DELETE FROM `daily_challenges`");
      _db.execSQL("DELETE FROM `customizations`");
      _db.execSQL("DELETE FROM `unlockables`");
      _db.execSQL("DELETE FROM `statistics`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ScoreDao.class, ScoreDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GameStateDao.class, GameStateDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProgressionDao.class, ProgressionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AchievementDao.class, AchievementDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PuzzleProgressDao.class, PuzzleProgressDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SettingsDao.class, SettingsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DailyChallengeDao.class, DailyChallengeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CustomizationDao.class, CustomizationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UnlockableDao.class, UnlockableDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StatisticsDao.class, StatisticsDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ScoreDao scoreDao() {
    if (_scoreDao != null) {
      return _scoreDao;
    } else {
      synchronized(this) {
        if(_scoreDao == null) {
          _scoreDao = new ScoreDao_Impl(this);
        }
        return _scoreDao;
      }
    }
  }

  @Override
  public GameStateDao gameStateDao() {
    if (_gameStateDao != null) {
      return _gameStateDao;
    } else {
      synchronized(this) {
        if(_gameStateDao == null) {
          _gameStateDao = new GameStateDao_Impl(this);
        }
        return _gameStateDao;
      }
    }
  }

  @Override
  public ProgressionDao progressionDao() {
    if (_progressionDao != null) {
      return _progressionDao;
    } else {
      synchronized(this) {
        if(_progressionDao == null) {
          _progressionDao = new ProgressionDao_Impl(this);
        }
        return _progressionDao;
      }
    }
  }

  @Override
  public AchievementDao achievementDao() {
    if (_achievementDao != null) {
      return _achievementDao;
    } else {
      synchronized(this) {
        if(_achievementDao == null) {
          _achievementDao = new AchievementDao_Impl(this);
        }
        return _achievementDao;
      }
    }
  }

  @Override
  public PuzzleProgressDao puzzleProgressDao() {
    if (_puzzleProgressDao != null) {
      return _puzzleProgressDao;
    } else {
      synchronized(this) {
        if(_puzzleProgressDao == null) {
          _puzzleProgressDao = new PuzzleProgressDao_Impl(this);
        }
        return _puzzleProgressDao;
      }
    }
  }

  @Override
  public SettingsDao settingsDao() {
    if (_settingsDao != null) {
      return _settingsDao;
    } else {
      synchronized(this) {
        if(_settingsDao == null) {
          _settingsDao = new SettingsDao_Impl(this);
        }
        return _settingsDao;
      }
    }
  }

  @Override
  public DailyChallengeDao dailyChallengeDao() {
    if (_dailyChallengeDao != null) {
      return _dailyChallengeDao;
    } else {
      synchronized(this) {
        if(_dailyChallengeDao == null) {
          _dailyChallengeDao = new DailyChallengeDao_Impl(this);
        }
        return _dailyChallengeDao;
      }
    }
  }

  @Override
  public CustomizationDao customizationDao() {
    if (_customizationDao != null) {
      return _customizationDao;
    } else {
      synchronized(this) {
        if(_customizationDao == null) {
          _customizationDao = new CustomizationDao_Impl(this);
        }
        return _customizationDao;
      }
    }
  }

  @Override
  public UnlockableDao unlockableDao() {
    if (_unlockableDao != null) {
      return _unlockableDao;
    } else {
      synchronized(this) {
        if(_unlockableDao == null) {
          _unlockableDao = new UnlockableDao_Impl(this);
        }
        return _unlockableDao;
      }
    }
  }

  @Override
  public StatisticsDao statisticsDao() {
    if (_statisticsDao != null) {
      return _statisticsDao;
    } else {
      synchronized(this) {
        if(_statisticsDao == null) {
          _statisticsDao = new StatisticsDao_Impl(this);
        }
        return _statisticsDao;
      }
    }
  }
}
