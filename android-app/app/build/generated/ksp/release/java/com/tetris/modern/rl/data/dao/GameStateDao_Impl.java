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
import com.tetris.modern.rl.data.entities.GameStateEntity;
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
public final class GameStateDao_Impl implements GameStateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GameStateEntity> __insertionAdapterOfGameStateEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearGameState;

  public GameStateDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGameStateEntity = new EntityInsertionAdapter<GameStateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `game_states` (`id`,`grid`,`currentPiece`,`nextPieces`,`heldPiece`,`score`,`level`,`lines`,`gameMode`,`isPaused`,`timestamp`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GameStateEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getGrid());
        statement.bindString(3, entity.getCurrentPiece());
        statement.bindString(4, entity.getNextPieces());
        if (entity.getHeldPiece() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getHeldPiece());
        }
        statement.bindLong(6, entity.getScore());
        statement.bindLong(7, entity.getLevel());
        statement.bindLong(8, entity.getLines());
        statement.bindString(9, entity.getGameMode());
        final int _tmp = entity.isPaused() ? 1 : 0;
        statement.bindLong(10, _tmp);
        statement.bindLong(11, entity.getTimestamp());
      }
    };
    this.__preparedStmtOfClearGameState = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM game_states";
        return _query;
      }
    };
  }

  @Override
  public Object saveGameState(final GameStateEntity gameState,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfGameStateEntity.insert(gameState);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearGameState(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearGameState.acquire();
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
          __preparedStmtOfClearGameState.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<GameStateEntity> getCurrentGameState() {
    final String _sql = "SELECT * FROM game_states WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"game_states"}, new Callable<GameStateEntity>() {
      @Override
      @Nullable
      public GameStateEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGrid = CursorUtil.getColumnIndexOrThrow(_cursor, "grid");
          final int _cursorIndexOfCurrentPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPiece");
          final int _cursorIndexOfNextPieces = CursorUtil.getColumnIndexOrThrow(_cursor, "nextPieces");
          final int _cursorIndexOfHeldPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "heldPiece");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfLines = CursorUtil.getColumnIndexOrThrow(_cursor, "lines");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfIsPaused = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaused");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final GameStateEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpGrid;
            _tmpGrid = _cursor.getString(_cursorIndexOfGrid);
            final String _tmpCurrentPiece;
            _tmpCurrentPiece = _cursor.getString(_cursorIndexOfCurrentPiece);
            final String _tmpNextPieces;
            _tmpNextPieces = _cursor.getString(_cursorIndexOfNextPieces);
            final String _tmpHeldPiece;
            if (_cursor.isNull(_cursorIndexOfHeldPiece)) {
              _tmpHeldPiece = null;
            } else {
              _tmpHeldPiece = _cursor.getString(_cursorIndexOfHeldPiece);
            }
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpLines;
            _tmpLines = _cursor.getInt(_cursorIndexOfLines);
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final boolean _tmpIsPaused;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaused);
            _tmpIsPaused = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _result = new GameStateEntity(_tmpId,_tmpGrid,_tmpCurrentPiece,_tmpNextPieces,_tmpHeldPiece,_tmpScore,_tmpLevel,_tmpLines,_tmpGameMode,_tmpIsPaused,_tmpTimestamp);
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
