package com.tetris.modern.rl.data.dao;

import android.database.Cursor;
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
import com.tetris.modern.rl.data.entities.ProgressionEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProgressionDao_Impl implements ProgressionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProgressionEntity> __insertionAdapterOfProgressionEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateXp;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLevelAndRank;

  public ProgressionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProgressionEntity = new EntityInsertionAdapter<ProgressionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `progression` (`id`,`currentLevel`,`currentXp`,`totalXp`,`currentRank`,`unlockedThemes`,`unlockedMusic`,`unlockedPieceStyles`,`unlockedEffects`,`statistics`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProgressionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCurrentLevel());
        statement.bindLong(3, entity.getCurrentXp());
        statement.bindLong(4, entity.getTotalXp());
        statement.bindString(5, entity.getCurrentRank());
        statement.bindString(6, entity.getUnlockedThemes());
        statement.bindString(7, entity.getUnlockedMusic());
        statement.bindString(8, entity.getUnlockedPieceStyles());
        statement.bindString(9, entity.getUnlockedEffects());
        statement.bindString(10, entity.getStatistics());
      }
    };
    this.__preparedStmtOfUpdateXp = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE progression SET currentXp = ?, totalXp = ? WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLevelAndRank = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE progression SET currentLevel = ?, currentRank = ? WHERE id = 1";
        return _query;
      }
    };
  }

  @Override
  public Object updateProgression(final ProgressionEntity progression,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProgressionEntity.insert(progression);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateXp(final int xp, final int totalXp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateXp.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, xp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, totalXp);
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
          __preparedStmtOfUpdateXp.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLevelAndRank(final int level, final String rank,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLevelAndRank.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, level);
        _argIndex = 2;
        _stmt.bindString(_argIndex, rank);
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
          __preparedStmtOfUpdateLevelAndRank.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<ProgressionEntity> getProgression() {
    final String _sql = "SELECT * FROM progression WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"progression"}, new Callable<ProgressionEntity>() {
      @Override
      @Nullable
      public ProgressionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCurrentLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "currentLevel");
          final int _cursorIndexOfCurrentXp = CursorUtil.getColumnIndexOrThrow(_cursor, "currentXp");
          final int _cursorIndexOfTotalXp = CursorUtil.getColumnIndexOrThrow(_cursor, "totalXp");
          final int _cursorIndexOfCurrentRank = CursorUtil.getColumnIndexOrThrow(_cursor, "currentRank");
          final int _cursorIndexOfUnlockedThemes = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedThemes");
          final int _cursorIndexOfUnlockedMusic = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedMusic");
          final int _cursorIndexOfUnlockedPieceStyles = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedPieceStyles");
          final int _cursorIndexOfUnlockedEffects = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedEffects");
          final int _cursorIndexOfStatistics = CursorUtil.getColumnIndexOrThrow(_cursor, "statistics");
          final ProgressionEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpCurrentLevel;
            _tmpCurrentLevel = _cursor.getInt(_cursorIndexOfCurrentLevel);
            final int _tmpCurrentXp;
            _tmpCurrentXp = _cursor.getInt(_cursorIndexOfCurrentXp);
            final int _tmpTotalXp;
            _tmpTotalXp = _cursor.getInt(_cursorIndexOfTotalXp);
            final String _tmpCurrentRank;
            _tmpCurrentRank = _cursor.getString(_cursorIndexOfCurrentRank);
            final String _tmpUnlockedThemes;
            _tmpUnlockedThemes = _cursor.getString(_cursorIndexOfUnlockedThemes);
            final String _tmpUnlockedMusic;
            _tmpUnlockedMusic = _cursor.getString(_cursorIndexOfUnlockedMusic);
            final String _tmpUnlockedPieceStyles;
            _tmpUnlockedPieceStyles = _cursor.getString(_cursorIndexOfUnlockedPieceStyles);
            final String _tmpUnlockedEffects;
            _tmpUnlockedEffects = _cursor.getString(_cursorIndexOfUnlockedEffects);
            final String _tmpStatistics;
            _tmpStatistics = _cursor.getString(_cursorIndexOfStatistics);
            _result = new ProgressionEntity(_tmpId,_tmpCurrentLevel,_tmpCurrentXp,_tmpTotalXp,_tmpCurrentRank,_tmpUnlockedThemes,_tmpUnlockedMusic,_tmpUnlockedPieceStyles,_tmpUnlockedEffects,_tmpStatistics);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
