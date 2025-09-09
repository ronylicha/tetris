package com.tetris.modern.rl.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tetris.modern.rl.data.entities.StatisticsEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StatisticsDao_Impl implements StatisticsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StatisticsEntity> __insertionAdapterOfStatisticsEntity;

  private final EntityDeletionOrUpdateAdapter<StatisticsEntity> __deletionAdapterOfStatisticsEntity;

  private final EntityDeletionOrUpdateAdapter<StatisticsEntity> __updateAdapterOfStatisticsEntity;

  private final SharedSQLiteStatement __preparedStmtOfIncrementGamesPlayed;

  private final SharedSQLiteStatement __preparedStmtOfAddLinesCleared;

  private final SharedSQLiteStatement __preparedStmtOfUpdateHighScore;

  private final SharedSQLiteStatement __preparedStmtOfAddTimePlayed;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByMode;

  public StatisticsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStatisticsEntity = new EntityInsertionAdapter<StatisticsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `statistics` (`id`,`gameMode`,`totalGamesPlayed`,`totalLinesCleared`,`totalScore`,`highScore`,`bestCombo`,`totalPiecesPlaced`,`totalTSpins`,`totalTetrisClears`,`totalPerfectClears`,`totalTimePlayed`,`averageScore`,`averageLinesPerGame`,`winRate`,`longestSurvivalTime`,`fastestSprint40`,`highestLevel`,`totalXPEarned`,`favoriteMode`,`lastPlayedTimestamp`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StatisticsEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getGameMode());
        statement.bindLong(3, entity.getTotalGamesPlayed());
        statement.bindLong(4, entity.getTotalLinesCleared());
        statement.bindLong(5, entity.getTotalScore());
        statement.bindLong(6, entity.getHighScore());
        statement.bindLong(7, entity.getBestCombo());
        statement.bindLong(8, entity.getTotalPiecesPlaced());
        statement.bindLong(9, entity.getTotalTSpins());
        statement.bindLong(10, entity.getTotalTetrisClears());
        statement.bindLong(11, entity.getTotalPerfectClears());
        statement.bindLong(12, entity.getTotalTimePlayed());
        statement.bindDouble(13, entity.getAverageScore());
        statement.bindDouble(14, entity.getAverageLinesPerGame());
        statement.bindDouble(15, entity.getWinRate());
        statement.bindLong(16, entity.getLongestSurvivalTime());
        statement.bindLong(17, entity.getFastestSprint40());
        statement.bindLong(18, entity.getHighestLevel());
        statement.bindLong(19, entity.getTotalXPEarned());
        if (entity.getFavoriteMode() == null) {
          statement.bindNull(20);
        } else {
          statement.bindString(20, entity.getFavoriteMode());
        }
        statement.bindLong(21, entity.getLastPlayedTimestamp());
        statement.bindLong(22, entity.getCreatedAt());
        statement.bindLong(23, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfStatisticsEntity = new EntityDeletionOrUpdateAdapter<StatisticsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `statistics` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StatisticsEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfStatisticsEntity = new EntityDeletionOrUpdateAdapter<StatisticsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `statistics` SET `id` = ?,`gameMode` = ?,`totalGamesPlayed` = ?,`totalLinesCleared` = ?,`totalScore` = ?,`highScore` = ?,`bestCombo` = ?,`totalPiecesPlaced` = ?,`totalTSpins` = ?,`totalTetrisClears` = ?,`totalPerfectClears` = ?,`totalTimePlayed` = ?,`averageScore` = ?,`averageLinesPerGame` = ?,`winRate` = ?,`longestSurvivalTime` = ?,`fastestSprint40` = ?,`highestLevel` = ?,`totalXPEarned` = ?,`favoriteMode` = ?,`lastPlayedTimestamp` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StatisticsEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getGameMode());
        statement.bindLong(3, entity.getTotalGamesPlayed());
        statement.bindLong(4, entity.getTotalLinesCleared());
        statement.bindLong(5, entity.getTotalScore());
        statement.bindLong(6, entity.getHighScore());
        statement.bindLong(7, entity.getBestCombo());
        statement.bindLong(8, entity.getTotalPiecesPlaced());
        statement.bindLong(9, entity.getTotalTSpins());
        statement.bindLong(10, entity.getTotalTetrisClears());
        statement.bindLong(11, entity.getTotalPerfectClears());
        statement.bindLong(12, entity.getTotalTimePlayed());
        statement.bindDouble(13, entity.getAverageScore());
        statement.bindDouble(14, entity.getAverageLinesPerGame());
        statement.bindDouble(15, entity.getWinRate());
        statement.bindLong(16, entity.getLongestSurvivalTime());
        statement.bindLong(17, entity.getFastestSprint40());
        statement.bindLong(18, entity.getHighestLevel());
        statement.bindLong(19, entity.getTotalXPEarned());
        if (entity.getFavoriteMode() == null) {
          statement.bindNull(20);
        } else {
          statement.bindString(20, entity.getFavoriteMode());
        }
        statement.bindLong(21, entity.getLastPlayedTimestamp());
        statement.bindLong(22, entity.getCreatedAt());
        statement.bindLong(23, entity.getUpdatedAt());
        statement.bindLong(24, entity.getId());
      }
    };
    this.__preparedStmtOfIncrementGamesPlayed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE statistics SET totalGamesPlayed = totalGamesPlayed + 1, lastPlayedTimestamp = ? WHERE gameMode = ?";
        return _query;
      }
    };
    this.__preparedStmtOfAddLinesCleared = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE statistics SET totalLinesCleared = totalLinesCleared + ? WHERE gameMode = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateHighScore = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE statistics SET highScore = ? WHERE gameMode = ? AND ? > highScore";
        return _query;
      }
    };
    this.__preparedStmtOfAddTimePlayed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE statistics SET totalTimePlayed = totalTimePlayed + ? WHERE gameMode = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM statistics";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByMode = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM statistics WHERE gameMode = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertStatistics(final StatisticsEntity statistics,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfStatisticsEntity.insert(statistics);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteStatistics(final StatisticsEntity statistics,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfStatisticsEntity.handle(statistics);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStatistics(final StatisticsEntity statistics,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStatisticsEntity.handle(statistics);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementGamesPlayed(final String mode, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementGamesPlayed.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, mode);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfIncrementGamesPlayed.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object addLinesCleared(final String mode, final int lines,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAddLinesCleared.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lines);
        _argIndex = 2;
        _stmt.bindString(_argIndex, mode);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfAddLinesCleared.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateHighScore(final String mode, final int score,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateHighScore.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, score);
        _argIndex = 2;
        _stmt.bindString(_argIndex, mode);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, score);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateHighScore.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object addTimePlayed(final String mode, final long time,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAddTimePlayed.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, time);
        _argIndex = 2;
        _stmt.bindString(_argIndex, mode);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfAddTimePlayed.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByMode(final String mode, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByMode.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, mode);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteByMode.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<StatisticsEntity>> getAllStatistics() {
    final String _sql = "SELECT * FROM statistics";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"statistics"}, new Callable<List<StatisticsEntity>>() {
      @Override
      @NonNull
      public List<StatisticsEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfTotalGamesPlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGamesPlayed");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highScore");
          final int _cursorIndexOfBestCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "bestCombo");
          final int _cursorIndexOfTotalPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPiecesPlaced");
          final int _cursorIndexOfTotalTSpins = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTSpins");
          final int _cursorIndexOfTotalTetrisClears = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTetrisClears");
          final int _cursorIndexOfTotalPerfectClears = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPerfectClears");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfAverageScore = CursorUtil.getColumnIndexOrThrow(_cursor, "averageScore");
          final int _cursorIndexOfAverageLinesPerGame = CursorUtil.getColumnIndexOrThrow(_cursor, "averageLinesPerGame");
          final int _cursorIndexOfWinRate = CursorUtil.getColumnIndexOrThrow(_cursor, "winRate");
          final int _cursorIndexOfLongestSurvivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "longestSurvivalTime");
          final int _cursorIndexOfFastestSprint40 = CursorUtil.getColumnIndexOrThrow(_cursor, "fastestSprint40");
          final int _cursorIndexOfHighestLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "highestLevel");
          final int _cursorIndexOfTotalXPEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "totalXPEarned");
          final int _cursorIndexOfFavoriteMode = CursorUtil.getColumnIndexOrThrow(_cursor, "favoriteMode");
          final int _cursorIndexOfLastPlayedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayedTimestamp");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<StatisticsEntity> _result = new ArrayList<StatisticsEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StatisticsEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final int _tmpTotalGamesPlayed;
            _tmpTotalGamesPlayed = _cursor.getInt(_cursorIndexOfTotalGamesPlayed);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final int _tmpHighScore;
            _tmpHighScore = _cursor.getInt(_cursorIndexOfHighScore);
            final int _tmpBestCombo;
            _tmpBestCombo = _cursor.getInt(_cursorIndexOfBestCombo);
            final int _tmpTotalPiecesPlaced;
            _tmpTotalPiecesPlaced = _cursor.getInt(_cursorIndexOfTotalPiecesPlaced);
            final int _tmpTotalTSpins;
            _tmpTotalTSpins = _cursor.getInt(_cursorIndexOfTotalTSpins);
            final int _tmpTotalTetrisClears;
            _tmpTotalTetrisClears = _cursor.getInt(_cursorIndexOfTotalTetrisClears);
            final int _tmpTotalPerfectClears;
            _tmpTotalPerfectClears = _cursor.getInt(_cursorIndexOfTotalPerfectClears);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final float _tmpAverageScore;
            _tmpAverageScore = _cursor.getFloat(_cursorIndexOfAverageScore);
            final float _tmpAverageLinesPerGame;
            _tmpAverageLinesPerGame = _cursor.getFloat(_cursorIndexOfAverageLinesPerGame);
            final float _tmpWinRate;
            _tmpWinRate = _cursor.getFloat(_cursorIndexOfWinRate);
            final long _tmpLongestSurvivalTime;
            _tmpLongestSurvivalTime = _cursor.getLong(_cursorIndexOfLongestSurvivalTime);
            final long _tmpFastestSprint40;
            _tmpFastestSprint40 = _cursor.getLong(_cursorIndexOfFastestSprint40);
            final int _tmpHighestLevel;
            _tmpHighestLevel = _cursor.getInt(_cursorIndexOfHighestLevel);
            final int _tmpTotalXPEarned;
            _tmpTotalXPEarned = _cursor.getInt(_cursorIndexOfTotalXPEarned);
            final String _tmpFavoriteMode;
            if (_cursor.isNull(_cursorIndexOfFavoriteMode)) {
              _tmpFavoriteMode = null;
            } else {
              _tmpFavoriteMode = _cursor.getString(_cursorIndexOfFavoriteMode);
            }
            final long _tmpLastPlayedTimestamp;
            _tmpLastPlayedTimestamp = _cursor.getLong(_cursorIndexOfLastPlayedTimestamp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new StatisticsEntity(_tmpId,_tmpGameMode,_tmpTotalGamesPlayed,_tmpTotalLinesCleared,_tmpTotalScore,_tmpHighScore,_tmpBestCombo,_tmpTotalPiecesPlaced,_tmpTotalTSpins,_tmpTotalTetrisClears,_tmpTotalPerfectClears,_tmpTotalTimePlayed,_tmpAverageScore,_tmpAverageLinesPerGame,_tmpWinRate,_tmpLongestSurvivalTime,_tmpFastestSprint40,_tmpHighestLevel,_tmpTotalXPEarned,_tmpFavoriteMode,_tmpLastPlayedTimestamp,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getStatisticsByMode(final String mode,
      final Continuation<? super StatisticsEntity> $completion) {
    final String _sql = "SELECT * FROM statistics WHERE gameMode = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, mode);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StatisticsEntity>() {
      @Override
      @Nullable
      public StatisticsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfTotalGamesPlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGamesPlayed");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highScore");
          final int _cursorIndexOfBestCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "bestCombo");
          final int _cursorIndexOfTotalPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPiecesPlaced");
          final int _cursorIndexOfTotalTSpins = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTSpins");
          final int _cursorIndexOfTotalTetrisClears = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTetrisClears");
          final int _cursorIndexOfTotalPerfectClears = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPerfectClears");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfAverageScore = CursorUtil.getColumnIndexOrThrow(_cursor, "averageScore");
          final int _cursorIndexOfAverageLinesPerGame = CursorUtil.getColumnIndexOrThrow(_cursor, "averageLinesPerGame");
          final int _cursorIndexOfWinRate = CursorUtil.getColumnIndexOrThrow(_cursor, "winRate");
          final int _cursorIndexOfLongestSurvivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "longestSurvivalTime");
          final int _cursorIndexOfFastestSprint40 = CursorUtil.getColumnIndexOrThrow(_cursor, "fastestSprint40");
          final int _cursorIndexOfHighestLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "highestLevel");
          final int _cursorIndexOfTotalXPEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "totalXPEarned");
          final int _cursorIndexOfFavoriteMode = CursorUtil.getColumnIndexOrThrow(_cursor, "favoriteMode");
          final int _cursorIndexOfLastPlayedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayedTimestamp");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final StatisticsEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final int _tmpTotalGamesPlayed;
            _tmpTotalGamesPlayed = _cursor.getInt(_cursorIndexOfTotalGamesPlayed);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final int _tmpHighScore;
            _tmpHighScore = _cursor.getInt(_cursorIndexOfHighScore);
            final int _tmpBestCombo;
            _tmpBestCombo = _cursor.getInt(_cursorIndexOfBestCombo);
            final int _tmpTotalPiecesPlaced;
            _tmpTotalPiecesPlaced = _cursor.getInt(_cursorIndexOfTotalPiecesPlaced);
            final int _tmpTotalTSpins;
            _tmpTotalTSpins = _cursor.getInt(_cursorIndexOfTotalTSpins);
            final int _tmpTotalTetrisClears;
            _tmpTotalTetrisClears = _cursor.getInt(_cursorIndexOfTotalTetrisClears);
            final int _tmpTotalPerfectClears;
            _tmpTotalPerfectClears = _cursor.getInt(_cursorIndexOfTotalPerfectClears);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final float _tmpAverageScore;
            _tmpAverageScore = _cursor.getFloat(_cursorIndexOfAverageScore);
            final float _tmpAverageLinesPerGame;
            _tmpAverageLinesPerGame = _cursor.getFloat(_cursorIndexOfAverageLinesPerGame);
            final float _tmpWinRate;
            _tmpWinRate = _cursor.getFloat(_cursorIndexOfWinRate);
            final long _tmpLongestSurvivalTime;
            _tmpLongestSurvivalTime = _cursor.getLong(_cursorIndexOfLongestSurvivalTime);
            final long _tmpFastestSprint40;
            _tmpFastestSprint40 = _cursor.getLong(_cursorIndexOfFastestSprint40);
            final int _tmpHighestLevel;
            _tmpHighestLevel = _cursor.getInt(_cursorIndexOfHighestLevel);
            final int _tmpTotalXPEarned;
            _tmpTotalXPEarned = _cursor.getInt(_cursorIndexOfTotalXPEarned);
            final String _tmpFavoriteMode;
            if (_cursor.isNull(_cursorIndexOfFavoriteMode)) {
              _tmpFavoriteMode = null;
            } else {
              _tmpFavoriteMode = _cursor.getString(_cursorIndexOfFavoriteMode);
            }
            final long _tmpLastPlayedTimestamp;
            _tmpLastPlayedTimestamp = _cursor.getLong(_cursorIndexOfLastPlayedTimestamp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new StatisticsEntity(_tmpId,_tmpGameMode,_tmpTotalGamesPlayed,_tmpTotalLinesCleared,_tmpTotalScore,_tmpHighScore,_tmpBestCombo,_tmpTotalPiecesPlaced,_tmpTotalTSpins,_tmpTotalTetrisClears,_tmpTotalPerfectClears,_tmpTotalTimePlayed,_tmpAverageScore,_tmpAverageLinesPerGame,_tmpWinRate,_tmpLongestSurvivalTime,_tmpFastestSprint40,_tmpHighestLevel,_tmpTotalXPEarned,_tmpFavoriteMode,_tmpLastPlayedTimestamp,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<StatisticsEntity> observeStatisticsByMode(final String mode) {
    final String _sql = "SELECT * FROM statistics WHERE gameMode = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, mode);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"statistics"}, new Callable<StatisticsEntity>() {
      @Override
      @Nullable
      public StatisticsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfTotalGamesPlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGamesPlayed");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highScore");
          final int _cursorIndexOfBestCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "bestCombo");
          final int _cursorIndexOfTotalPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPiecesPlaced");
          final int _cursorIndexOfTotalTSpins = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTSpins");
          final int _cursorIndexOfTotalTetrisClears = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTetrisClears");
          final int _cursorIndexOfTotalPerfectClears = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPerfectClears");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfAverageScore = CursorUtil.getColumnIndexOrThrow(_cursor, "averageScore");
          final int _cursorIndexOfAverageLinesPerGame = CursorUtil.getColumnIndexOrThrow(_cursor, "averageLinesPerGame");
          final int _cursorIndexOfWinRate = CursorUtil.getColumnIndexOrThrow(_cursor, "winRate");
          final int _cursorIndexOfLongestSurvivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "longestSurvivalTime");
          final int _cursorIndexOfFastestSprint40 = CursorUtil.getColumnIndexOrThrow(_cursor, "fastestSprint40");
          final int _cursorIndexOfHighestLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "highestLevel");
          final int _cursorIndexOfTotalXPEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "totalXPEarned");
          final int _cursorIndexOfFavoriteMode = CursorUtil.getColumnIndexOrThrow(_cursor, "favoriteMode");
          final int _cursorIndexOfLastPlayedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayedTimestamp");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final StatisticsEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final int _tmpTotalGamesPlayed;
            _tmpTotalGamesPlayed = _cursor.getInt(_cursorIndexOfTotalGamesPlayed);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final int _tmpHighScore;
            _tmpHighScore = _cursor.getInt(_cursorIndexOfHighScore);
            final int _tmpBestCombo;
            _tmpBestCombo = _cursor.getInt(_cursorIndexOfBestCombo);
            final int _tmpTotalPiecesPlaced;
            _tmpTotalPiecesPlaced = _cursor.getInt(_cursorIndexOfTotalPiecesPlaced);
            final int _tmpTotalTSpins;
            _tmpTotalTSpins = _cursor.getInt(_cursorIndexOfTotalTSpins);
            final int _tmpTotalTetrisClears;
            _tmpTotalTetrisClears = _cursor.getInt(_cursorIndexOfTotalTetrisClears);
            final int _tmpTotalPerfectClears;
            _tmpTotalPerfectClears = _cursor.getInt(_cursorIndexOfTotalPerfectClears);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final float _tmpAverageScore;
            _tmpAverageScore = _cursor.getFloat(_cursorIndexOfAverageScore);
            final float _tmpAverageLinesPerGame;
            _tmpAverageLinesPerGame = _cursor.getFloat(_cursorIndexOfAverageLinesPerGame);
            final float _tmpWinRate;
            _tmpWinRate = _cursor.getFloat(_cursorIndexOfWinRate);
            final long _tmpLongestSurvivalTime;
            _tmpLongestSurvivalTime = _cursor.getLong(_cursorIndexOfLongestSurvivalTime);
            final long _tmpFastestSprint40;
            _tmpFastestSprint40 = _cursor.getLong(_cursorIndexOfFastestSprint40);
            final int _tmpHighestLevel;
            _tmpHighestLevel = _cursor.getInt(_cursorIndexOfHighestLevel);
            final int _tmpTotalXPEarned;
            _tmpTotalXPEarned = _cursor.getInt(_cursorIndexOfTotalXPEarned);
            final String _tmpFavoriteMode;
            if (_cursor.isNull(_cursorIndexOfFavoriteMode)) {
              _tmpFavoriteMode = null;
            } else {
              _tmpFavoriteMode = _cursor.getString(_cursorIndexOfFavoriteMode);
            }
            final long _tmpLastPlayedTimestamp;
            _tmpLastPlayedTimestamp = _cursor.getLong(_cursorIndexOfLastPlayedTimestamp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new StatisticsEntity(_tmpId,_tmpGameMode,_tmpTotalGamesPlayed,_tmpTotalLinesCleared,_tmpTotalScore,_tmpHighScore,_tmpBestCombo,_tmpTotalPiecesPlaced,_tmpTotalTSpins,_tmpTotalTetrisClears,_tmpTotalPerfectClears,_tmpTotalTimePlayed,_tmpAverageScore,_tmpAverageLinesPerGame,_tmpWinRate,_tmpLongestSurvivalTime,_tmpFastestSprint40,_tmpHighestLevel,_tmpTotalXPEarned,_tmpFavoriteMode,_tmpLastPlayedTimestamp,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTotalGamesPlayed(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(totalGamesPlayed) FROM statistics";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalLinesCleared(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(totalLinesCleared) FROM statistics";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getOverallHighScore(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT MAX(highScore) FROM statistics";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalTimePlayed(final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(totalTimePlayed) FROM statistics";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getMostPlayedMode(final Continuation<? super String> $completion) {
    final String _sql = "SELECT gameMode FROM statistics ORDER BY totalGamesPlayed DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<String>() {
      @Override
      @Nullable
      public String call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final String _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getString(0);
            }
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
