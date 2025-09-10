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
import com.tetris.modern.rl.data.entities.PuzzleProgressEntity;
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
public final class PuzzleProgressDao_Impl implements PuzzleProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PuzzleProgressEntity> __insertionAdapterOfPuzzleProgressEntity;

  public PuzzleProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPuzzleProgressEntity = new EntityInsertionAdapter<PuzzleProgressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `puzzle_progress` (`puzzleId`,`isCompleted`,`bestScore`,`stars`,`attempts`,`completionTime`,`hintsUsed`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PuzzleProgressEntity entity) {
        statement.bindLong(1, entity.getPuzzleId());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(2, _tmp);
        statement.bindLong(3, entity.getBestScore());
        statement.bindLong(4, entity.getStars());
        statement.bindLong(5, entity.getAttempts());
        if (entity.getCompletionTime() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getCompletionTime());
        }
        statement.bindLong(7, entity.getHintsUsed());
      }
    };
  }

  @Override
  public Object updatePuzzleProgress(final PuzzleProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPuzzleProgressEntity.insert(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PuzzleProgressEntity>> getAllPuzzleProgress() {
    final String _sql = "SELECT * FROM puzzle_progress";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"puzzle_progress"}, new Callable<List<PuzzleProgressEntity>>() {
      @Override
      @NonNull
      public List<PuzzleProgressEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPuzzleId = CursorUtil.getColumnIndexOrThrow(_cursor, "puzzleId");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfBestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "bestScore");
          final int _cursorIndexOfStars = CursorUtil.getColumnIndexOrThrow(_cursor, "stars");
          final int _cursorIndexOfAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "attempts");
          final int _cursorIndexOfCompletionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completionTime");
          final int _cursorIndexOfHintsUsed = CursorUtil.getColumnIndexOrThrow(_cursor, "hintsUsed");
          final List<PuzzleProgressEntity> _result = new ArrayList<PuzzleProgressEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PuzzleProgressEntity _item;
            final int _tmpPuzzleId;
            _tmpPuzzleId = _cursor.getInt(_cursorIndexOfPuzzleId);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final int _tmpBestScore;
            _tmpBestScore = _cursor.getInt(_cursorIndexOfBestScore);
            final int _tmpStars;
            _tmpStars = _cursor.getInt(_cursorIndexOfStars);
            final int _tmpAttempts;
            _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            final Long _tmpCompletionTime;
            if (_cursor.isNull(_cursorIndexOfCompletionTime)) {
              _tmpCompletionTime = null;
            } else {
              _tmpCompletionTime = _cursor.getLong(_cursorIndexOfCompletionTime);
            }
            final int _tmpHintsUsed;
            _tmpHintsUsed = _cursor.getInt(_cursorIndexOfHintsUsed);
            _item = new PuzzleProgressEntity(_tmpPuzzleId,_tmpIsCompleted,_tmpBestScore,_tmpStars,_tmpAttempts,_tmpCompletionTime,_tmpHintsUsed);
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
  public Flow<PuzzleProgressEntity> getPuzzleProgress(final int puzzleId) {
    final String _sql = "SELECT * FROM puzzle_progress WHERE puzzleId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, puzzleId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"puzzle_progress"}, new Callable<PuzzleProgressEntity>() {
      @Override
      @Nullable
      public PuzzleProgressEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPuzzleId = CursorUtil.getColumnIndexOrThrow(_cursor, "puzzleId");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfBestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "bestScore");
          final int _cursorIndexOfStars = CursorUtil.getColumnIndexOrThrow(_cursor, "stars");
          final int _cursorIndexOfAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "attempts");
          final int _cursorIndexOfCompletionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completionTime");
          final int _cursorIndexOfHintsUsed = CursorUtil.getColumnIndexOrThrow(_cursor, "hintsUsed");
          final PuzzleProgressEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpPuzzleId;
            _tmpPuzzleId = _cursor.getInt(_cursorIndexOfPuzzleId);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final int _tmpBestScore;
            _tmpBestScore = _cursor.getInt(_cursorIndexOfBestScore);
            final int _tmpStars;
            _tmpStars = _cursor.getInt(_cursorIndexOfStars);
            final int _tmpAttempts;
            _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            final Long _tmpCompletionTime;
            if (_cursor.isNull(_cursorIndexOfCompletionTime)) {
              _tmpCompletionTime = null;
            } else {
              _tmpCompletionTime = _cursor.getLong(_cursorIndexOfCompletionTime);
            }
            final int _tmpHintsUsed;
            _tmpHintsUsed = _cursor.getInt(_cursorIndexOfHintsUsed);
            _result = new PuzzleProgressEntity(_tmpPuzzleId,_tmpIsCompleted,_tmpBestScore,_tmpStars,_tmpAttempts,_tmpCompletionTime,_tmpHintsUsed);
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
  public Flow<List<PuzzleProgressEntity>> getCompletedPuzzles() {
    final String _sql = "SELECT * FROM puzzle_progress WHERE isCompleted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"puzzle_progress"}, new Callable<List<PuzzleProgressEntity>>() {
      @Override
      @NonNull
      public List<PuzzleProgressEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPuzzleId = CursorUtil.getColumnIndexOrThrow(_cursor, "puzzleId");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfBestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "bestScore");
          final int _cursorIndexOfStars = CursorUtil.getColumnIndexOrThrow(_cursor, "stars");
          final int _cursorIndexOfAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "attempts");
          final int _cursorIndexOfCompletionTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completionTime");
          final int _cursorIndexOfHintsUsed = CursorUtil.getColumnIndexOrThrow(_cursor, "hintsUsed");
          final List<PuzzleProgressEntity> _result = new ArrayList<PuzzleProgressEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PuzzleProgressEntity _item;
            final int _tmpPuzzleId;
            _tmpPuzzleId = _cursor.getInt(_cursorIndexOfPuzzleId);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final int _tmpBestScore;
            _tmpBestScore = _cursor.getInt(_cursorIndexOfBestScore);
            final int _tmpStars;
            _tmpStars = _cursor.getInt(_cursorIndexOfStars);
            final int _tmpAttempts;
            _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            final Long _tmpCompletionTime;
            if (_cursor.isNull(_cursorIndexOfCompletionTime)) {
              _tmpCompletionTime = null;
            } else {
              _tmpCompletionTime = _cursor.getLong(_cursorIndexOfCompletionTime);
            }
            final int _tmpHintsUsed;
            _tmpHintsUsed = _cursor.getInt(_cursorIndexOfHintsUsed);
            _item = new PuzzleProgressEntity(_tmpPuzzleId,_tmpIsCompleted,_tmpBestScore,_tmpStars,_tmpAttempts,_tmpCompletionTime,_tmpHintsUsed);
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
  public Object getCompletedCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM puzzle_progress WHERE isCompleted = 1";
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
  public Object getTotalStars(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(stars) FROM puzzle_progress";
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
