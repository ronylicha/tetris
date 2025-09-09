package com.tetris.modern.rl.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tetris.modern.rl.data.entities.DailyChallengeEntity;
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
public final class DailyChallengeDao_Impl implements DailyChallengeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DailyChallengeEntity> __insertionAdapterOfDailyChallengeEntity;

  public DailyChallengeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDailyChallengeEntity = new EntityInsertionAdapter<DailyChallengeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `daily_challenges` (`date`,`seed`,`modifiers`,`targetScore`,`isCompleted`,`playerScore`,`completionTime`,`attempts`,`currentStreak`,`bestStreak`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyChallengeEntity entity) {
        statement.bindString(1, entity.getDate());
        statement.bindLong(2, entity.getSeed());
        statement.bindString(3, entity.getModifiers());
        statement.bindLong(4, entity.getTargetScore());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getPlayerScore());
        if (entity.getCompletionTime() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCompletionTime());
        }
        statement.bindLong(8, entity.getAttempts());
        statement.bindLong(9, entity.getCurrentStreak());
        statement.bindLong(10, entity.getBestStreak());
      }
    };
  }

  @Override
  public Object insertOrUpdateChallenge(final DailyChallengeEntity challenge,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDailyChallengeEntity.insert(challenge);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<DailyChallengeEntity> getTodaysChallenge(final String date) {
    final String _sql = "SELECT * FROM daily_challenges WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_challenges"}, new Callable<DailyChallengeEntity>() {
      @Override
      @Nullable
      public DailyChallengeEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSeed = CursorUtil.getColumnIndexOrThrow(_cursor, "seed");
          final int _cursorIndexOfModifiers = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiers");
          final int _cursorIndexOfTargetScore = CursorUtil.getColumnIndexOrThrow(_cursor, "targetScore");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfPlayerScore = CursorUtil.getColumnIndexOrThrow(_cursor, "playerScore");
          final int _cursorIndexOfCompletionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completionTime");
          final int _cursorIndexOfAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "attempts");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfBestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "bestStreak");
          final DailyChallengeEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final long _tmpSeed;
            _tmpSeed = _cursor.getLong(_cursorIndexOfSeed);
            final String _tmpModifiers;
            _tmpModifiers = _cursor.getString(_cursorIndexOfModifiers);
            final int _tmpTargetScore;
            _tmpTargetScore = _cursor.getInt(_cursorIndexOfTargetScore);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final int _tmpPlayerScore;
            _tmpPlayerScore = _cursor.getInt(_cursorIndexOfPlayerScore);
            final Long _tmpCompletionTime;
            if (_cursor.isNull(_cursorIndexOfCompletionTime)) {
              _tmpCompletionTime = null;
            } else {
              _tmpCompletionTime = _cursor.getLong(_cursorIndexOfCompletionTime);
            }
            final int _tmpAttempts;
            _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpBestStreak;
            _tmpBestStreak = _cursor.getInt(_cursorIndexOfBestStreak);
            _result = new DailyChallengeEntity(_tmpDate,_tmpSeed,_tmpModifiers,_tmpTargetScore,_tmpIsCompleted,_tmpPlayerScore,_tmpCompletionTime,_tmpAttempts,_tmpCurrentStreak,_tmpBestStreak);
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
  public Flow<List<DailyChallengeEntity>> getAllChallenges() {
    final String _sql = "SELECT * FROM daily_challenges ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_challenges"}, new Callable<List<DailyChallengeEntity>>() {
      @Override
      @NonNull
      public List<DailyChallengeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSeed = CursorUtil.getColumnIndexOrThrow(_cursor, "seed");
          final int _cursorIndexOfModifiers = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiers");
          final int _cursorIndexOfTargetScore = CursorUtil.getColumnIndexOrThrow(_cursor, "targetScore");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfPlayerScore = CursorUtil.getColumnIndexOrThrow(_cursor, "playerScore");
          final int _cursorIndexOfCompletionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completionTime");
          final int _cursorIndexOfAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "attempts");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfBestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "bestStreak");
          final List<DailyChallengeEntity> _result = new ArrayList<DailyChallengeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyChallengeEntity _item;
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final long _tmpSeed;
            _tmpSeed = _cursor.getLong(_cursorIndexOfSeed);
            final String _tmpModifiers;
            _tmpModifiers = _cursor.getString(_cursorIndexOfModifiers);
            final int _tmpTargetScore;
            _tmpTargetScore = _cursor.getInt(_cursorIndexOfTargetScore);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final int _tmpPlayerScore;
            _tmpPlayerScore = _cursor.getInt(_cursorIndexOfPlayerScore);
            final Long _tmpCompletionTime;
            if (_cursor.isNull(_cursorIndexOfCompletionTime)) {
              _tmpCompletionTime = null;
            } else {
              _tmpCompletionTime = _cursor.getLong(_cursorIndexOfCompletionTime);
            }
            final int _tmpAttempts;
            _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpBestStreak;
            _tmpBestStreak = _cursor.getInt(_cursorIndexOfBestStreak);
            _item = new DailyChallengeEntity(_tmpDate,_tmpSeed,_tmpModifiers,_tmpTargetScore,_tmpIsCompleted,_tmpPlayerScore,_tmpCompletionTime,_tmpAttempts,_tmpCurrentStreak,_tmpBestStreak);
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
  public Flow<List<DailyChallengeEntity>> getCompletedChallenges() {
    final String _sql = "SELECT * FROM daily_challenges WHERE isCompleted = 1 ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_challenges"}, new Callable<List<DailyChallengeEntity>>() {
      @Override
      @NonNull
      public List<DailyChallengeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSeed = CursorUtil.getColumnIndexOrThrow(_cursor, "seed");
          final int _cursorIndexOfModifiers = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiers");
          final int _cursorIndexOfTargetScore = CursorUtil.getColumnIndexOrThrow(_cursor, "targetScore");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfPlayerScore = CursorUtil.getColumnIndexOrThrow(_cursor, "playerScore");
          final int _cursorIndexOfCompletionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completionTime");
          final int _cursorIndexOfAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "attempts");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfBestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "bestStreak");
          final List<DailyChallengeEntity> _result = new ArrayList<DailyChallengeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyChallengeEntity _item;
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final long _tmpSeed;
            _tmpSeed = _cursor.getLong(_cursorIndexOfSeed);
            final String _tmpModifiers;
            _tmpModifiers = _cursor.getString(_cursorIndexOfModifiers);
            final int _tmpTargetScore;
            _tmpTargetScore = _cursor.getInt(_cursorIndexOfTargetScore);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final int _tmpPlayerScore;
            _tmpPlayerScore = _cursor.getInt(_cursorIndexOfPlayerScore);
            final Long _tmpCompletionTime;
            if (_cursor.isNull(_cursorIndexOfCompletionTime)) {
              _tmpCompletionTime = null;
            } else {
              _tmpCompletionTime = _cursor.getLong(_cursorIndexOfCompletionTime);
            }
            final int _tmpAttempts;
            _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpBestStreak;
            _tmpBestStreak = _cursor.getInt(_cursorIndexOfBestStreak);
            _item = new DailyChallengeEntity(_tmpDate,_tmpSeed,_tmpModifiers,_tmpTargetScore,_tmpIsCompleted,_tmpPlayerScore,_tmpCompletionTime,_tmpAttempts,_tmpCurrentStreak,_tmpBestStreak);
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
  public Object getBestStreak(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT MAX(currentStreak) FROM daily_challenges";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Object getCurrentStreak(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT currentStreak FROM daily_challenges ORDER BY date DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _result;
          if (_cursor.moveToFirst()) {
            _result = _cursor.getInt(0);
          } else {
            _result = 0;
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
