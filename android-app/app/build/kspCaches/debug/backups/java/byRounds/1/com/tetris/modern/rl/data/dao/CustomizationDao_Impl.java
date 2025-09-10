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
import com.tetris.modern.rl.data.entities.CustomizationEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class CustomizationDao_Impl implements CustomizationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CustomizationEntity> __insertionAdapterOfCustomizationEntity;

  private final SharedSQLiteStatement __preparedStmtOfUnlockCustomization;

  public CustomizationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCustomizationEntity = new EntityInsertionAdapter<CustomizationEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `customizations` (`itemId`,`type`,`name`,`description`,`isUnlocked`,`unlockLevel`,`previewImage`,`data`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CustomizationEntity entity) {
        statement.bindString(1, entity.getItemId());
        statement.bindString(2, entity.getType());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getDescription());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getUnlockLevel());
        if (entity.getPreviewImage() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPreviewImage());
        }
        if (entity.getData() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getData());
        }
      }
    };
    this.__preparedStmtOfUnlockCustomization = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE customizations SET isUnlocked = 1 WHERE itemId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrUpdateCustomization(final CustomizationEntity customization,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCustomizationEntity.insert(customization);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertOrUpdateCustomizations(final List<CustomizationEntity> customizations,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCustomizationEntity.insert(customizations);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object unlockCustomization(final String itemId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUnlockCustomization.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, itemId);
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
          __preparedStmtOfUnlockCustomization.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CustomizationEntity>> getAllCustomizations() {
    final String _sql = "SELECT * FROM customizations";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"customizations"}, new Callable<List<CustomizationEntity>>() {
      @Override
      @NonNull
      public List<CustomizationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "itemId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockLevel");
          final int _cursorIndexOfPreviewImage = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImage");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final List<CustomizationEntity> _result = new ArrayList<CustomizationEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CustomizationEntity _item;
            final String _tmpItemId;
            _tmpItemId = _cursor.getString(_cursorIndexOfItemId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final int _tmpUnlockLevel;
            _tmpUnlockLevel = _cursor.getInt(_cursorIndexOfUnlockLevel);
            final String _tmpPreviewImage;
            if (_cursor.isNull(_cursorIndexOfPreviewImage)) {
              _tmpPreviewImage = null;
            } else {
              _tmpPreviewImage = _cursor.getString(_cursorIndexOfPreviewImage);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            _item = new CustomizationEntity(_tmpItemId,_tmpType,_tmpName,_tmpDescription,_tmpIsUnlocked,_tmpUnlockLevel,_tmpPreviewImage,_tmpData);
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
  public Flow<List<CustomizationEntity>> getCustomizationsByType(final String type) {
    final String _sql = "SELECT * FROM customizations WHERE type = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, type);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"customizations"}, new Callable<List<CustomizationEntity>>() {
      @Override
      @NonNull
      public List<CustomizationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "itemId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockLevel");
          final int _cursorIndexOfPreviewImage = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImage");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final List<CustomizationEntity> _result = new ArrayList<CustomizationEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CustomizationEntity _item;
            final String _tmpItemId;
            _tmpItemId = _cursor.getString(_cursorIndexOfItemId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final int _tmpUnlockLevel;
            _tmpUnlockLevel = _cursor.getInt(_cursorIndexOfUnlockLevel);
            final String _tmpPreviewImage;
            if (_cursor.isNull(_cursorIndexOfPreviewImage)) {
              _tmpPreviewImage = null;
            } else {
              _tmpPreviewImage = _cursor.getString(_cursorIndexOfPreviewImage);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            _item = new CustomizationEntity(_tmpItemId,_tmpType,_tmpName,_tmpDescription,_tmpIsUnlocked,_tmpUnlockLevel,_tmpPreviewImage,_tmpData);
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
  public Flow<List<CustomizationEntity>> getUnlockedCustomizations() {
    final String _sql = "SELECT * FROM customizations WHERE isUnlocked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"customizations"}, new Callable<List<CustomizationEntity>>() {
      @Override
      @NonNull
      public List<CustomizationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "itemId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockLevel");
          final int _cursorIndexOfPreviewImage = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImage");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final List<CustomizationEntity> _result = new ArrayList<CustomizationEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CustomizationEntity _item;
            final String _tmpItemId;
            _tmpItemId = _cursor.getString(_cursorIndexOfItemId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final int _tmpUnlockLevel;
            _tmpUnlockLevel = _cursor.getInt(_cursorIndexOfUnlockLevel);
            final String _tmpPreviewImage;
            if (_cursor.isNull(_cursorIndexOfPreviewImage)) {
              _tmpPreviewImage = null;
            } else {
              _tmpPreviewImage = _cursor.getString(_cursorIndexOfPreviewImage);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            _item = new CustomizationEntity(_tmpItemId,_tmpType,_tmpName,_tmpDescription,_tmpIsUnlocked,_tmpUnlockLevel,_tmpPreviewImage,_tmpData);
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
  public Flow<CustomizationEntity> getCustomization(final String itemId) {
    final String _sql = "SELECT * FROM customizations WHERE itemId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, itemId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"customizations"}, new Callable<CustomizationEntity>() {
      @Override
      @Nullable
      public CustomizationEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "itemId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockLevel");
          final int _cursorIndexOfPreviewImage = CursorUtil.getColumnIndexOrThrow(_cursor, "previewImage");
          final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
          final CustomizationEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpItemId;
            _tmpItemId = _cursor.getString(_cursorIndexOfItemId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final int _tmpUnlockLevel;
            _tmpUnlockLevel = _cursor.getInt(_cursorIndexOfUnlockLevel);
            final String _tmpPreviewImage;
            if (_cursor.isNull(_cursorIndexOfPreviewImage)) {
              _tmpPreviewImage = null;
            } else {
              _tmpPreviewImage = _cursor.getString(_cursorIndexOfPreviewImage);
            }
            final String _tmpData;
            if (_cursor.isNull(_cursorIndexOfData)) {
              _tmpData = null;
            } else {
              _tmpData = _cursor.getString(_cursorIndexOfData);
            }
            _result = new CustomizationEntity(_tmpItemId,_tmpType,_tmpName,_tmpDescription,_tmpIsUnlocked,_tmpUnlockLevel,_tmpPreviewImage,_tmpData);
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
