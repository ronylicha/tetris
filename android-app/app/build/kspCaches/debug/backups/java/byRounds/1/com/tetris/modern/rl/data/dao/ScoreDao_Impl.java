package com.tetris.modern.rl.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tetris.modern.rl.data.database.Converters;
import com.tetris.modern.rl.data.entities.ScoreEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ScoreDao_Impl implements ScoreDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ScoreEntity> __insertionAdapterOfScoreEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<ScoreEntity> __deletionAdapterOfScoreEntity;

  private final EntityDeletionOrUpdateAdapter<ScoreEntity> __updateAdapterOfScoreEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllScores;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldScores;

  public ScoreDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfScoreEntity = new EntityInsertionAdapter<ScoreEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `scores` (`id`,`playerName`,`score`,`level`,`lines`,`gameMode`,`date`,`duration`,`isSynced`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScoreEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getPlayerName());
        statement.bindLong(3, entity.getScore());
        statement.bindLong(4, entity.getLevel());
        statement.bindLong(5, entity.getLines());
        statement.bindString(6, entity.getGameMode());
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp);
        }
        statement.bindLong(8, entity.getDuration());
        final int _tmp_1 = entity.isSynced() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getTimestamp());
      }
    };
    this.__deletionAdapterOfScoreEntity = new EntityDeletionOrUpdateAdapter<ScoreEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `scores` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScoreEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfScoreEntity = new EntityDeletionOrUpdateAdapter<ScoreEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `scores` SET `id` = ?,`playerName` = ?,`score` = ?,`level` = ?,`lines` = ?,`gameMode` = ?,`date` = ?,`duration` = ?,`isSynced` = ?,`timestamp` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScoreEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getPlayerName());
        statement.bindLong(3, entity.getScore());
        statement.bindLong(4, entity.getLevel());
        statement.bindLong(5, entity.getLines());
        statement.bindString(6, entity.getGameMode());
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp);
        }
        statement.bindLong(8, entity.getDuration());
        final int _tmp_1 = entity.isSynced() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getTimestamp());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllScores = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM scores";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldScores = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM scores WHERE timestamp < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertScore(final ScoreEntity score, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfScoreEntity.insertAndReturnId(score);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteScore(final ScoreEntity score, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfScoreEntity.handle(score);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateScore(final ScoreEntity score, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfScoreEntity.handle(score);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllScores(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllScores.acquire();
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
          __preparedStmtOfDeleteAllScores.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldScores(final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldScores.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
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
          __preparedStmtOfDeleteOldScores.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ScoreEntity>> getTopScores(final int limit) {
    final String _sql = "SELECT * FROM scores ORDER BY score DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"scores"}, new Callable<List<ScoreEntity>>() {
      @Override
      @NonNull
      public List<ScoreEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerName = CursorUtil.getColumnIndexOrThrow(_cursor, "playerName");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfLines = CursorUtil.getColumnIndexOrThrow(_cursor, "lines");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<ScoreEntity> _result = new ArrayList<ScoreEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScoreEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPlayerName;
            _tmpPlayerName = _cursor.getString(_cursorIndexOfPlayerName);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpLines;
            _tmpLines = _cursor.getInt(_cursorIndexOfLines);
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new ScoreEntity(_tmpId,_tmpPlayerName,_tmpScore,_tmpLevel,_tmpLines,_tmpGameMode,_tmpDate,_tmpDuration,_tmpIsSynced,_tmpTimestamp);
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
  public Flow<List<ScoreEntity>> getTopScoresByMode(final String gameMode, final int limit) {
    final String _sql = "SELECT * FROM scores WHERE gameMode = ? ORDER BY score DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, gameMode);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"scores"}, new Callable<List<ScoreEntity>>() {
      @Override
      @NonNull
      public List<ScoreEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerName = CursorUtil.getColumnIndexOrThrow(_cursor, "playerName");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfLines = CursorUtil.getColumnIndexOrThrow(_cursor, "lines");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<ScoreEntity> _result = new ArrayList<ScoreEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScoreEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPlayerName;
            _tmpPlayerName = _cursor.getString(_cursorIndexOfPlayerName);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpLines;
            _tmpLines = _cursor.getInt(_cursorIndexOfLines);
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new ScoreEntity(_tmpId,_tmpPlayerName,_tmpScore,_tmpLevel,_tmpLines,_tmpGameMode,_tmpDate,_tmpDuration,_tmpIsSynced,_tmpTimestamp);
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
  public Flow<List<ScoreEntity>> getPlayerScores(final String playerName) {
    final String _sql = "SELECT * FROM scores WHERE playerName = ? ORDER BY score DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, playerName);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"scores"}, new Callable<List<ScoreEntity>>() {
      @Override
      @NonNull
      public List<ScoreEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerName = CursorUtil.getColumnIndexOrThrow(_cursor, "playerName");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfLines = CursorUtil.getColumnIndexOrThrow(_cursor, "lines");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<ScoreEntity> _result = new ArrayList<ScoreEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScoreEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPlayerName;
            _tmpPlayerName = _cursor.getString(_cursorIndexOfPlayerName);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpLines;
            _tmpLines = _cursor.getInt(_cursorIndexOfLines);
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new ScoreEntity(_tmpId,_tmpPlayerName,_tmpScore,_tmpLevel,_tmpLines,_tmpGameMode,_tmpDate,_tmpDuration,_tmpIsSynced,_tmpTimestamp);
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
  public Object getUnsyncedScores(final Continuation<? super List<ScoreEntity>> $completion) {
    final String _sql = "SELECT * FROM scores WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ScoreEntity>>() {
      @Override
      @NonNull
      public List<ScoreEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerName = CursorUtil.getColumnIndexOrThrow(_cursor, "playerName");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfLines = CursorUtil.getColumnIndexOrThrow(_cursor, "lines");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<ScoreEntity> _result = new ArrayList<ScoreEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScoreEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPlayerName;
            _tmpPlayerName = _cursor.getString(_cursorIndexOfPlayerName);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpLines;
            _tmpLines = _cursor.getInt(_cursorIndexOfLines);
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new ScoreEntity(_tmpId,_tmpPlayerName,_tmpScore,_tmpLevel,_tmpLines,_tmpGameMode,_tmpDate,_tmpDuration,_tmpIsSynced,_tmpTimestamp);
            _result.add(_item);
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
  public Object markAsSynced(final List<Long> ids, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("UPDATE scores SET isSynced = 1 WHERE id IN (");
        final int _inputSize = ids.size();
        StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
        _stringBuilder.append(")");
        final String _sql = _stringBuilder.toString();
        final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
        int _argIndex = 1;
        for (long _item : ids) {
          _stmt.bindLong(_argIndex, _item);
          _argIndex++;
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
