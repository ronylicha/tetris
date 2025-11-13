package com.tetris.modern.rl.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tetris.modern.rl.data.database.Converters;
import com.tetris.modern.rl.data.entities.DailyStreakEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
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
public final class DailyStreakDao_Impl implements DailyStreakDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DailyStreakEntity> __insertionAdapterOfDailyStreakEntity;

  private final Converters __converters = new Converters();

  private final SharedSQLiteStatement __preparedStmtOfUpdateCurrentStreak;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLongestStreak;

  private final SharedSQLiteStatement __preparedStmtOfIncrementDailyChallenges;

  private final SharedSQLiteStatement __preparedStmtOfIncrementTotalDaysPlayed;

  private final SharedSQLiteStatement __preparedStmtOfResetStreak;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public DailyStreakDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDailyStreakEntity = new EntityInsertionAdapter<DailyStreakEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `daily_streaks` (`id`,`currentStreak`,`longestStreak`,`lastLoginDate`,`totalDaysPlayed`,`lastCompletedChallengeDate`,`dailyChallengesCompleted`,`missedDays`,`streakStartDate`,`isStreakActive`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyStreakEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCurrentStreak());
        statement.bindLong(3, entity.getLongestStreak());
        final Long _tmp = __converters.dateToTimestamp(entity.getLastLoginDate());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp);
        }
        statement.bindLong(5, entity.getTotalDaysPlayed());
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getLastCompletedChallengeDate());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        statement.bindLong(7, entity.getDailyChallengesCompleted());
        statement.bindLong(8, entity.getMissedDays());
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getStreakStartDate());
        if (_tmp_2 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_2);
        }
        final int _tmp_3 = entity.isStreakActive() ? 1 : 0;
        statement.bindLong(10, _tmp_3);
      }
    };
    this.__preparedStmtOfUpdateCurrentStreak = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE daily_streaks SET currentStreak = ?, lastLoginDate = ?, isStreakActive = ? WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLongestStreak = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE daily_streaks SET longestStreak = ? WHERE id = 1 AND ? > longestStreak";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementDailyChallenges = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE daily_streaks SET dailyChallengesCompleted = dailyChallengesCompleted + 1, lastCompletedChallengeDate = ? WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementTotalDaysPlayed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE daily_streaks SET totalDaysPlayed = totalDaysPlayed + 1 WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfResetStreak = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE daily_streaks SET missedDays = missedDays + 1, isStreakActive = 0, currentStreak = 0 WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM daily_streaks";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrUpdateStreak(final DailyStreakEntity streak,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDailyStreakEntity.insert(streak);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCurrentStreak(final int streak, final Date date, final boolean active,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCurrentStreak.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, streak);
        _argIndex = 2;
        final Long _tmp = __converters.dateToTimestamp(date);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 3;
        final int _tmp_1 = active ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp_1);
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
          __preparedStmtOfUpdateCurrentStreak.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLongestStreak(final int streak,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLongestStreak.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, streak);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, streak);
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
          __preparedStmtOfUpdateLongestStreak.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementDailyChallenges(final Date date,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementDailyChallenges.acquire();
        int _argIndex = 1;
        final Long _tmp = __converters.dateToTimestamp(date);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
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
          __preparedStmtOfIncrementDailyChallenges.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementTotalDaysPlayed(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementTotalDaysPlayed.acquire();
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
          __preparedStmtOfIncrementTotalDaysPlayed.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object resetStreak(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfResetStreak.acquire();
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
          __preparedStmtOfResetStreak.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
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
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<DailyStreakEntity> getStreak() {
    final String _sql = "SELECT * FROM daily_streaks WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_streaks"}, new Callable<DailyStreakEntity>() {
      @Override
      @Nullable
      public DailyStreakEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLongestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "longestStreak");
          final int _cursorIndexOfLastLoginDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastLoginDate");
          final int _cursorIndexOfTotalDaysPlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDaysPlayed");
          final int _cursorIndexOfLastCompletedChallengeDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastCompletedChallengeDate");
          final int _cursorIndexOfDailyChallengesCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyChallengesCompleted");
          final int _cursorIndexOfMissedDays = CursorUtil.getColumnIndexOrThrow(_cursor, "missedDays");
          final int _cursorIndexOfStreakStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "streakStartDate");
          final int _cursorIndexOfIsStreakActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isStreakActive");
          final DailyStreakEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpLongestStreak;
            _tmpLongestStreak = _cursor.getInt(_cursorIndexOfLongestStreak);
            final Date _tmpLastLoginDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfLastLoginDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfLastLoginDate);
            }
            _tmpLastLoginDate = __converters.fromTimestamp(_tmp);
            final int _tmpTotalDaysPlayed;
            _tmpTotalDaysPlayed = _cursor.getInt(_cursorIndexOfTotalDaysPlayed);
            final Date _tmpLastCompletedChallengeDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfLastCompletedChallengeDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfLastCompletedChallengeDate);
            }
            _tmpLastCompletedChallengeDate = __converters.fromTimestamp(_tmp_1);
            final int _tmpDailyChallengesCompleted;
            _tmpDailyChallengesCompleted = _cursor.getInt(_cursorIndexOfDailyChallengesCompleted);
            final int _tmpMissedDays;
            _tmpMissedDays = _cursor.getInt(_cursorIndexOfMissedDays);
            final Date _tmpStreakStartDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStreakStartDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfStreakStartDate);
            }
            _tmpStreakStartDate = __converters.fromTimestamp(_tmp_2);
            final boolean _tmpIsStreakActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsStreakActive);
            _tmpIsStreakActive = _tmp_3 != 0;
            _result = new DailyStreakEntity(_tmpId,_tmpCurrentStreak,_tmpLongestStreak,_tmpLastLoginDate,_tmpTotalDaysPlayed,_tmpLastCompletedChallengeDate,_tmpDailyChallengesCompleted,_tmpMissedDays,_tmpStreakStartDate,_tmpIsStreakActive);
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
  public Object getStreakOnce(final Continuation<? super DailyStreakEntity> $completion) {
    final String _sql = "SELECT * FROM daily_streaks WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DailyStreakEntity>() {
      @Override
      @Nullable
      public DailyStreakEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLongestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "longestStreak");
          final int _cursorIndexOfLastLoginDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastLoginDate");
          final int _cursorIndexOfTotalDaysPlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDaysPlayed");
          final int _cursorIndexOfLastCompletedChallengeDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastCompletedChallengeDate");
          final int _cursorIndexOfDailyChallengesCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyChallengesCompleted");
          final int _cursorIndexOfMissedDays = CursorUtil.getColumnIndexOrThrow(_cursor, "missedDays");
          final int _cursorIndexOfStreakStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "streakStartDate");
          final int _cursorIndexOfIsStreakActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isStreakActive");
          final DailyStreakEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpLongestStreak;
            _tmpLongestStreak = _cursor.getInt(_cursorIndexOfLongestStreak);
            final Date _tmpLastLoginDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfLastLoginDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfLastLoginDate);
            }
            _tmpLastLoginDate = __converters.fromTimestamp(_tmp);
            final int _tmpTotalDaysPlayed;
            _tmpTotalDaysPlayed = _cursor.getInt(_cursorIndexOfTotalDaysPlayed);
            final Date _tmpLastCompletedChallengeDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfLastCompletedChallengeDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfLastCompletedChallengeDate);
            }
            _tmpLastCompletedChallengeDate = __converters.fromTimestamp(_tmp_1);
            final int _tmpDailyChallengesCompleted;
            _tmpDailyChallengesCompleted = _cursor.getInt(_cursorIndexOfDailyChallengesCompleted);
            final int _tmpMissedDays;
            _tmpMissedDays = _cursor.getInt(_cursorIndexOfMissedDays);
            final Date _tmpStreakStartDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStreakStartDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfStreakStartDate);
            }
            _tmpStreakStartDate = __converters.fromTimestamp(_tmp_2);
            final boolean _tmpIsStreakActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsStreakActive);
            _tmpIsStreakActive = _tmp_3 != 0;
            _result = new DailyStreakEntity(_tmpId,_tmpCurrentStreak,_tmpLongestStreak,_tmpLastLoginDate,_tmpTotalDaysPlayed,_tmpLastCompletedChallengeDate,_tmpDailyChallengesCompleted,_tmpMissedDays,_tmpStreakStartDate,_tmpIsStreakActive);
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
