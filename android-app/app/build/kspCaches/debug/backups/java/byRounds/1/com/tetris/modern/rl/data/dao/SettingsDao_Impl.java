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
import com.tetris.modern.rl.data.entities.SettingsEntity;
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
public final class SettingsDao_Impl implements SettingsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SettingsEntity> __insertionAdapterOfSettingsEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateMasterVolume;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTheme;

  private final SharedSQLiteStatement __preparedStmtOfUpdateMusic;

  public SettingsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSettingsEntity = new EntityInsertionAdapter<SettingsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `settings` (`id`,`masterVolume`,`musicVolume`,`sfxVolume`,`vibrationEnabled`,`touchSensitivity`,`selectedTheme`,`selectedMusic`,`selectedPieceStyle`,`selectedEffects`,`showGhostPiece`,`showNextPieces`,`autoHold`,`das`,`arr`,`isDarkMode`,`useDynamicColors`,`isMuted`,`controlType`,`isFirstLaunch`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SettingsEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getMasterVolume());
        statement.bindDouble(3, entity.getMusicVolume());
        statement.bindDouble(4, entity.getSfxVolume());
        final int _tmp = entity.getVibrationEnabled() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindDouble(6, entity.getTouchSensitivity());
        statement.bindString(7, entity.getSelectedTheme());
        statement.bindString(8, entity.getSelectedMusic());
        statement.bindString(9, entity.getSelectedPieceStyle());
        statement.bindString(10, entity.getSelectedEffects());
        final int _tmp_1 = entity.getShowGhostPiece() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
        statement.bindLong(12, entity.getShowNextPieces());
        final int _tmp_2 = entity.getAutoHold() ? 1 : 0;
        statement.bindLong(13, _tmp_2);
        statement.bindLong(14, entity.getDas());
        statement.bindLong(15, entity.getArr());
        final int _tmp_3 = entity.isDarkMode() ? 1 : 0;
        statement.bindLong(16, _tmp_3);
        final int _tmp_4 = entity.getUseDynamicColors() ? 1 : 0;
        statement.bindLong(17, _tmp_4);
        final int _tmp_5 = entity.isMuted() ? 1 : 0;
        statement.bindLong(18, _tmp_5);
        statement.bindString(19, entity.getControlType());
        final int _tmp_6 = entity.isFirstLaunch() ? 1 : 0;
        statement.bindLong(20, _tmp_6);
      }
    };
    this.__preparedStmtOfUpdateMasterVolume = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE settings SET masterVolume = ? WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTheme = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE settings SET selectedTheme = ? WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateMusic = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE settings SET selectedMusic = ? WHERE id = 1";
        return _query;
      }
    };
  }

  @Override
  public Object updateSettings(final SettingsEntity settings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSettingsEntity.insert(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateMasterVolume(final float volume,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateMasterVolume.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, volume);
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
          __preparedStmtOfUpdateMasterVolume.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTheme(final String theme, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTheme.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, theme);
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
          __preparedStmtOfUpdateTheme.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateMusic(final String music, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateMusic.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, music);
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
          __preparedStmtOfUpdateMusic.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<SettingsEntity> getSettings() {
    final String _sql = "SELECT * FROM settings WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"settings"}, new Callable<SettingsEntity>() {
      @Override
      @Nullable
      public SettingsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMasterVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "masterVolume");
          final int _cursorIndexOfMusicVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "musicVolume");
          final int _cursorIndexOfSfxVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "sfxVolume");
          final int _cursorIndexOfVibrationEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "vibrationEnabled");
          final int _cursorIndexOfTouchSensitivity = CursorUtil.getColumnIndexOrThrow(_cursor, "touchSensitivity");
          final int _cursorIndexOfSelectedTheme = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedTheme");
          final int _cursorIndexOfSelectedMusic = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedMusic");
          final int _cursorIndexOfSelectedPieceStyle = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedPieceStyle");
          final int _cursorIndexOfSelectedEffects = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedEffects");
          final int _cursorIndexOfShowGhostPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "showGhostPiece");
          final int _cursorIndexOfShowNextPieces = CursorUtil.getColumnIndexOrThrow(_cursor, "showNextPieces");
          final int _cursorIndexOfAutoHold = CursorUtil.getColumnIndexOrThrow(_cursor, "autoHold");
          final int _cursorIndexOfDas = CursorUtil.getColumnIndexOrThrow(_cursor, "das");
          final int _cursorIndexOfArr = CursorUtil.getColumnIndexOrThrow(_cursor, "arr");
          final int _cursorIndexOfIsDarkMode = CursorUtil.getColumnIndexOrThrow(_cursor, "isDarkMode");
          final int _cursorIndexOfUseDynamicColors = CursorUtil.getColumnIndexOrThrow(_cursor, "useDynamicColors");
          final int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
          final int _cursorIndexOfControlType = CursorUtil.getColumnIndexOrThrow(_cursor, "controlType");
          final int _cursorIndexOfIsFirstLaunch = CursorUtil.getColumnIndexOrThrow(_cursor, "isFirstLaunch");
          final SettingsEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final float _tmpMasterVolume;
            _tmpMasterVolume = _cursor.getFloat(_cursorIndexOfMasterVolume);
            final float _tmpMusicVolume;
            _tmpMusicVolume = _cursor.getFloat(_cursorIndexOfMusicVolume);
            final float _tmpSfxVolume;
            _tmpSfxVolume = _cursor.getFloat(_cursorIndexOfSfxVolume);
            final boolean _tmpVibrationEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfVibrationEnabled);
            _tmpVibrationEnabled = _tmp != 0;
            final float _tmpTouchSensitivity;
            _tmpTouchSensitivity = _cursor.getFloat(_cursorIndexOfTouchSensitivity);
            final String _tmpSelectedTheme;
            _tmpSelectedTheme = _cursor.getString(_cursorIndexOfSelectedTheme);
            final String _tmpSelectedMusic;
            _tmpSelectedMusic = _cursor.getString(_cursorIndexOfSelectedMusic);
            final String _tmpSelectedPieceStyle;
            _tmpSelectedPieceStyle = _cursor.getString(_cursorIndexOfSelectedPieceStyle);
            final String _tmpSelectedEffects;
            _tmpSelectedEffects = _cursor.getString(_cursorIndexOfSelectedEffects);
            final boolean _tmpShowGhostPiece;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfShowGhostPiece);
            _tmpShowGhostPiece = _tmp_1 != 0;
            final int _tmpShowNextPieces;
            _tmpShowNextPieces = _cursor.getInt(_cursorIndexOfShowNextPieces);
            final boolean _tmpAutoHold;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfAutoHold);
            _tmpAutoHold = _tmp_2 != 0;
            final int _tmpDas;
            _tmpDas = _cursor.getInt(_cursorIndexOfDas);
            final int _tmpArr;
            _tmpArr = _cursor.getInt(_cursorIndexOfArr);
            final boolean _tmpIsDarkMode;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDarkMode);
            _tmpIsDarkMode = _tmp_3 != 0;
            final boolean _tmpUseDynamicColors;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfUseDynamicColors);
            _tmpUseDynamicColors = _tmp_4 != 0;
            final boolean _tmpIsMuted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsMuted);
            _tmpIsMuted = _tmp_5 != 0;
            final String _tmpControlType;
            _tmpControlType = _cursor.getString(_cursorIndexOfControlType);
            final boolean _tmpIsFirstLaunch;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsFirstLaunch);
            _tmpIsFirstLaunch = _tmp_6 != 0;
            _result = new SettingsEntity(_tmpId,_tmpMasterVolume,_tmpMusicVolume,_tmpSfxVolume,_tmpVibrationEnabled,_tmpTouchSensitivity,_tmpSelectedTheme,_tmpSelectedMusic,_tmpSelectedPieceStyle,_tmpSelectedEffects,_tmpShowGhostPiece,_tmpShowNextPieces,_tmpAutoHold,_tmpDas,_tmpArr,_tmpIsDarkMode,_tmpUseDynamicColors,_tmpIsMuted,_tmpControlType,_tmpIsFirstLaunch);
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
  public Object getSettingsSync(final Continuation<? super SettingsEntity> $completion) {
    final String _sql = "SELECT * FROM settings WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SettingsEntity>() {
      @Override
      @Nullable
      public SettingsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMasterVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "masterVolume");
          final int _cursorIndexOfMusicVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "musicVolume");
          final int _cursorIndexOfSfxVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "sfxVolume");
          final int _cursorIndexOfVibrationEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "vibrationEnabled");
          final int _cursorIndexOfTouchSensitivity = CursorUtil.getColumnIndexOrThrow(_cursor, "touchSensitivity");
          final int _cursorIndexOfSelectedTheme = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedTheme");
          final int _cursorIndexOfSelectedMusic = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedMusic");
          final int _cursorIndexOfSelectedPieceStyle = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedPieceStyle");
          final int _cursorIndexOfSelectedEffects = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedEffects");
          final int _cursorIndexOfShowGhostPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "showGhostPiece");
          final int _cursorIndexOfShowNextPieces = CursorUtil.getColumnIndexOrThrow(_cursor, "showNextPieces");
          final int _cursorIndexOfAutoHold = CursorUtil.getColumnIndexOrThrow(_cursor, "autoHold");
          final int _cursorIndexOfDas = CursorUtil.getColumnIndexOrThrow(_cursor, "das");
          final int _cursorIndexOfArr = CursorUtil.getColumnIndexOrThrow(_cursor, "arr");
          final int _cursorIndexOfIsDarkMode = CursorUtil.getColumnIndexOrThrow(_cursor, "isDarkMode");
          final int _cursorIndexOfUseDynamicColors = CursorUtil.getColumnIndexOrThrow(_cursor, "useDynamicColors");
          final int _cursorIndexOfIsMuted = CursorUtil.getColumnIndexOrThrow(_cursor, "isMuted");
          final int _cursorIndexOfControlType = CursorUtil.getColumnIndexOrThrow(_cursor, "controlType");
          final int _cursorIndexOfIsFirstLaunch = CursorUtil.getColumnIndexOrThrow(_cursor, "isFirstLaunch");
          final SettingsEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final float _tmpMasterVolume;
            _tmpMasterVolume = _cursor.getFloat(_cursorIndexOfMasterVolume);
            final float _tmpMusicVolume;
            _tmpMusicVolume = _cursor.getFloat(_cursorIndexOfMusicVolume);
            final float _tmpSfxVolume;
            _tmpSfxVolume = _cursor.getFloat(_cursorIndexOfSfxVolume);
            final boolean _tmpVibrationEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfVibrationEnabled);
            _tmpVibrationEnabled = _tmp != 0;
            final float _tmpTouchSensitivity;
            _tmpTouchSensitivity = _cursor.getFloat(_cursorIndexOfTouchSensitivity);
            final String _tmpSelectedTheme;
            _tmpSelectedTheme = _cursor.getString(_cursorIndexOfSelectedTheme);
            final String _tmpSelectedMusic;
            _tmpSelectedMusic = _cursor.getString(_cursorIndexOfSelectedMusic);
            final String _tmpSelectedPieceStyle;
            _tmpSelectedPieceStyle = _cursor.getString(_cursorIndexOfSelectedPieceStyle);
            final String _tmpSelectedEffects;
            _tmpSelectedEffects = _cursor.getString(_cursorIndexOfSelectedEffects);
            final boolean _tmpShowGhostPiece;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfShowGhostPiece);
            _tmpShowGhostPiece = _tmp_1 != 0;
            final int _tmpShowNextPieces;
            _tmpShowNextPieces = _cursor.getInt(_cursorIndexOfShowNextPieces);
            final boolean _tmpAutoHold;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfAutoHold);
            _tmpAutoHold = _tmp_2 != 0;
            final int _tmpDas;
            _tmpDas = _cursor.getInt(_cursorIndexOfDas);
            final int _tmpArr;
            _tmpArr = _cursor.getInt(_cursorIndexOfArr);
            final boolean _tmpIsDarkMode;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsDarkMode);
            _tmpIsDarkMode = _tmp_3 != 0;
            final boolean _tmpUseDynamicColors;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfUseDynamicColors);
            _tmpUseDynamicColors = _tmp_4 != 0;
            final boolean _tmpIsMuted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsMuted);
            _tmpIsMuted = _tmp_5 != 0;
            final String _tmpControlType;
            _tmpControlType = _cursor.getString(_cursorIndexOfControlType);
            final boolean _tmpIsFirstLaunch;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsFirstLaunch);
            _tmpIsFirstLaunch = _tmp_6 != 0;
            _result = new SettingsEntity(_tmpId,_tmpMasterVolume,_tmpMusicVolume,_tmpSfxVolume,_tmpVibrationEnabled,_tmpTouchSensitivity,_tmpSelectedTheme,_tmpSelectedMusic,_tmpSelectedPieceStyle,_tmpSelectedEffects,_tmpShowGhostPiece,_tmpShowNextPieces,_tmpAutoHold,_tmpDas,_tmpArr,_tmpIsDarkMode,_tmpUseDynamicColors,_tmpIsMuted,_tmpControlType,_tmpIsFirstLaunch);
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
