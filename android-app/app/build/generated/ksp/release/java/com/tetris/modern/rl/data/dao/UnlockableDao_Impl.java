package com.tetris.modern.rl.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tetris.modern.rl.data.entities.UnlockableEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class UnlockableDao_Impl implements UnlockableDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UnlockableEntity> __insertionAdapterOfUnlockableEntity;

  private final EntityDeletionOrUpdateAdapter<UnlockableEntity> __deletionAdapterOfUnlockableEntity;

  private final EntityDeletionOrUpdateAdapter<UnlockableEntity> __updateAdapterOfUnlockableEntity;

  private final SharedSQLiteStatement __preparedStmtOfUnlockItem;

  private final SharedSQLiteStatement __preparedStmtOfDeselectAllByType;

  private final SharedSQLiteStatement __preparedStmtOfSelectItem;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public UnlockableDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUnlockableEntity = new EntityInsertionAdapter<UnlockableEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `unlockables` (`id`,`type`,`name`,`description`,`requiredLevel`,`requiredXP`,`requiredAchievement`,`cost`,`isUnlocked`,`isSelected`,`metadata`,`iconResource`,`previewResource`,`rarity`,`orderIndex`,`unlockedAt`,`usageCount`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UnlockableEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getType());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getDescription());
        statement.bindLong(5, entity.getRequiredLevel());
        statement.bindLong(6, entity.getRequiredXP());
        if (entity.getRequiredAchievement() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRequiredAchievement());
        }
        statement.bindLong(8, entity.getCost());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final int _tmp_1 = entity.isSelected() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        if (entity.getMetadata() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getMetadata());
        }
        if (entity.getIconResource() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getIconResource());
        }
        if (entity.getPreviewResource() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getPreviewResource());
        }
        statement.bindString(14, entity.getRarity());
        statement.bindLong(15, entity.getOrderIndex());
        if (entity.getUnlockedAt() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getUnlockedAt());
        }
        statement.bindLong(17, entity.getUsageCount());
      }
    };
    this.__deletionAdapterOfUnlockableEntity = new EntityDeletionOrUpdateAdapter<UnlockableEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `unlockables` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UnlockableEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfUnlockableEntity = new EntityDeletionOrUpdateAdapter<UnlockableEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `unlockables` SET `id` = ?,`type` = ?,`name` = ?,`description` = ?,`requiredLevel` = ?,`requiredXP` = ?,`requiredAchievement` = ?,`cost` = ?,`isUnlocked` = ?,`isSelected` = ?,`metadata` = ?,`iconResource` = ?,`previewResource` = ?,`rarity` = ?,`orderIndex` = ?,`unlockedAt` = ?,`usageCount` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UnlockableEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getType());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getDescription());
        statement.bindLong(5, entity.getRequiredLevel());
        statement.bindLong(6, entity.getRequiredXP());
        if (entity.getRequiredAchievement() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRequiredAchievement());
        }
        statement.bindLong(8, entity.getCost());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final int _tmp_1 = entity.isSelected() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        if (entity.getMetadata() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getMetadata());
        }
        if (entity.getIconResource() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getIconResource());
        }
        if (entity.getPreviewResource() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getPreviewResource());
        }
        statement.bindString(14, entity.getRarity());
        statement.bindLong(15, entity.getOrderIndex());
        if (entity.getUnlockedAt() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getUnlockedAt());
        }
        statement.bindLong(17, entity.getUsageCount());
        statement.bindString(18, entity.getId());
      }
    };
    this.__preparedStmtOfUnlockItem = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE unlockables SET isUnlocked = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeselectAllByType = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE unlockables SET isSelected = 0 WHERE type = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSelectItem = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE unlockables SET isSelected = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM unlockables";
        return _query;
      }
    };
  }

  @Override
  public Object insertUnlockable(final UnlockableEntity unlockable,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUnlockableEntity.insert(unlockable);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<UnlockableEntity> unlockables,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUnlockableEntity.insert(unlockables);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteUnlockable(final UnlockableEntity unlockable,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfUnlockableEntity.handle(unlockable);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUnlockable(final UnlockableEntity unlockable,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUnlockableEntity.handle(unlockable);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object selectUnlockable(final String id, final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> UnlockableDao.DefaultImpls.selectUnlockable(UnlockableDao_Impl.this, id, __cont), $completion);
  }

  @Override
  public Object unlockItem(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUnlockItem.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfUnlockItem.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deselectAllByType(final String type, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeselectAllByType.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, type);
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
          __preparedStmtOfDeselectAllByType.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object selectItem(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSelectItem.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfSelectItem.release(_stmt);
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
  public Flow<List<UnlockableEntity>> getAllUnlockables() {
    final String _sql = "SELECT * FROM unlockables";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"unlockables"}, new Callable<List<UnlockableEntity>>() {
      @Override
      @NonNull
      public List<UnlockableEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfRequiredLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredLevel");
          final int _cursorIndexOfRequiredXP = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredXP");
          final int _cursorIndexOfRequiredAchievement = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredAchievement");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfIsSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "isSelected");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfIconResource = CursorUtil.getColumnIndexOrThrow(_cursor, "iconResource");
          final int _cursorIndexOfPreviewResource = CursorUtil.getColumnIndexOrThrow(_cursor, "previewResource");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final int _cursorIndexOfUsageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "usageCount");
          final List<UnlockableEntity> _result = new ArrayList<UnlockableEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UnlockableEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpRequiredLevel;
            _tmpRequiredLevel = _cursor.getInt(_cursorIndexOfRequiredLevel);
            final int _tmpRequiredXP;
            _tmpRequiredXP = _cursor.getInt(_cursorIndexOfRequiredXP);
            final String _tmpRequiredAchievement;
            if (_cursor.isNull(_cursorIndexOfRequiredAchievement)) {
              _tmpRequiredAchievement = null;
            } else {
              _tmpRequiredAchievement = _cursor.getString(_cursorIndexOfRequiredAchievement);
            }
            final int _tmpCost;
            _tmpCost = _cursor.getInt(_cursorIndexOfCost);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final boolean _tmpIsSelected;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSelected);
            _tmpIsSelected = _tmp_1 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpIconResource;
            if (_cursor.isNull(_cursorIndexOfIconResource)) {
              _tmpIconResource = null;
            } else {
              _tmpIconResource = _cursor.getString(_cursorIndexOfIconResource);
            }
            final String _tmpPreviewResource;
            if (_cursor.isNull(_cursorIndexOfPreviewResource)) {
              _tmpPreviewResource = null;
            } else {
              _tmpPreviewResource = _cursor.getString(_cursorIndexOfPreviewResource);
            }
            final String _tmpRarity;
            _tmpRarity = _cursor.getString(_cursorIndexOfRarity);
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            final Long _tmpUnlockedAt;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmpUnlockedAt = null;
            } else {
              _tmpUnlockedAt = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            final int _tmpUsageCount;
            _tmpUsageCount = _cursor.getInt(_cursorIndexOfUsageCount);
            _item = new UnlockableEntity(_tmpId,_tmpType,_tmpName,_tmpDescription,_tmpRequiredLevel,_tmpRequiredXP,_tmpRequiredAchievement,_tmpCost,_tmpIsUnlocked,_tmpIsSelected,_tmpMetadata,_tmpIconResource,_tmpPreviewResource,_tmpRarity,_tmpOrderIndex,_tmpUnlockedAt,_tmpUsageCount);
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
  public Object getUnlockableById(final String id,
      final Continuation<? super UnlockableEntity> $completion) {
    final String _sql = "SELECT * FROM unlockables WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UnlockableEntity>() {
      @Override
      @Nullable
      public UnlockableEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfRequiredLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredLevel");
          final int _cursorIndexOfRequiredXP = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredXP");
          final int _cursorIndexOfRequiredAchievement = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredAchievement");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfIsSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "isSelected");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfIconResource = CursorUtil.getColumnIndexOrThrow(_cursor, "iconResource");
          final int _cursorIndexOfPreviewResource = CursorUtil.getColumnIndexOrThrow(_cursor, "previewResource");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final int _cursorIndexOfUsageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "usageCount");
          final UnlockableEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpRequiredLevel;
            _tmpRequiredLevel = _cursor.getInt(_cursorIndexOfRequiredLevel);
            final int _tmpRequiredXP;
            _tmpRequiredXP = _cursor.getInt(_cursorIndexOfRequiredXP);
            final String _tmpRequiredAchievement;
            if (_cursor.isNull(_cursorIndexOfRequiredAchievement)) {
              _tmpRequiredAchievement = null;
            } else {
              _tmpRequiredAchievement = _cursor.getString(_cursorIndexOfRequiredAchievement);
            }
            final int _tmpCost;
            _tmpCost = _cursor.getInt(_cursorIndexOfCost);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final boolean _tmpIsSelected;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSelected);
            _tmpIsSelected = _tmp_1 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpIconResource;
            if (_cursor.isNull(_cursorIndexOfIconResource)) {
              _tmpIconResource = null;
            } else {
              _tmpIconResource = _cursor.getString(_cursorIndexOfIconResource);
            }
            final String _tmpPreviewResource;
            if (_cursor.isNull(_cursorIndexOfPreviewResource)) {
              _tmpPreviewResource = null;
            } else {
              _tmpPreviewResource = _cursor.getString(_cursorIndexOfPreviewResource);
            }
            final String _tmpRarity;
            _tmpRarity = _cursor.getString(_cursorIndexOfRarity);
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            final Long _tmpUnlockedAt;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmpUnlockedAt = null;
            } else {
              _tmpUnlockedAt = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            final int _tmpUsageCount;
            _tmpUsageCount = _cursor.getInt(_cursorIndexOfUsageCount);
            _result = new UnlockableEntity(_tmpId,_tmpType,_tmpName,_tmpDescription,_tmpRequiredLevel,_tmpRequiredXP,_tmpRequiredAchievement,_tmpCost,_tmpIsUnlocked,_tmpIsSelected,_tmpMetadata,_tmpIconResource,_tmpPreviewResource,_tmpRarity,_tmpOrderIndex,_tmpUnlockedAt,_tmpUsageCount);
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
  public Flow<List<UnlockableEntity>> getUnlockablesByType(final String type) {
    final String _sql = "SELECT * FROM unlockables WHERE type = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, type);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"unlockables"}, new Callable<List<UnlockableEntity>>() {
      @Override
      @NonNull
      public List<UnlockableEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfRequiredLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredLevel");
          final int _cursorIndexOfRequiredXP = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredXP");
          final int _cursorIndexOfRequiredAchievement = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredAchievement");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfIsSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "isSelected");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfIconResource = CursorUtil.getColumnIndexOrThrow(_cursor, "iconResource");
          final int _cursorIndexOfPreviewResource = CursorUtil.getColumnIndexOrThrow(_cursor, "previewResource");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final int _cursorIndexOfUsageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "usageCount");
          final List<UnlockableEntity> _result = new ArrayList<UnlockableEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UnlockableEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpRequiredLevel;
            _tmpRequiredLevel = _cursor.getInt(_cursorIndexOfRequiredLevel);
            final int _tmpRequiredXP;
            _tmpRequiredXP = _cursor.getInt(_cursorIndexOfRequiredXP);
            final String _tmpRequiredAchievement;
            if (_cursor.isNull(_cursorIndexOfRequiredAchievement)) {
              _tmpRequiredAchievement = null;
            } else {
              _tmpRequiredAchievement = _cursor.getString(_cursorIndexOfRequiredAchievement);
            }
            final int _tmpCost;
            _tmpCost = _cursor.getInt(_cursorIndexOfCost);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final boolean _tmpIsSelected;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSelected);
            _tmpIsSelected = _tmp_1 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpIconResource;
            if (_cursor.isNull(_cursorIndexOfIconResource)) {
              _tmpIconResource = null;
            } else {
              _tmpIconResource = _cursor.getString(_cursorIndexOfIconResource);
            }
            final String _tmpPreviewResource;
            if (_cursor.isNull(_cursorIndexOfPreviewResource)) {
              _tmpPreviewResource = null;
            } else {
              _tmpPreviewResource = _cursor.getString(_cursorIndexOfPreviewResource);
            }
            final String _tmpRarity;
            _tmpRarity = _cursor.getString(_cursorIndexOfRarity);
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            final Long _tmpUnlockedAt;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmpUnlockedAt = null;
            } else {
              _tmpUnlockedAt = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            final int _tmpUsageCount;
            _tmpUsageCount = _cursor.getInt(_cursorIndexOfUsageCount);
            _item = new UnlockableEntity(_tmpId,_tmpType,_tmpName,_tmpDescription,_tmpRequiredLevel,_tmpRequiredXP,_tmpRequiredAchievement,_tmpCost,_tmpIsUnlocked,_tmpIsSelected,_tmpMetadata,_tmpIconResource,_tmpPreviewResource,_tmpRarity,_tmpOrderIndex,_tmpUnlockedAt,_tmpUsageCount);
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
  public Flow<List<UnlockableEntity>> getUnlockedItems() {
    final String _sql = "SELECT * FROM unlockables WHERE isUnlocked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"unlockables"}, new Callable<List<UnlockableEntity>>() {
      @Override
      @NonNull
      public List<UnlockableEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfRequiredLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredLevel");
          final int _cursorIndexOfRequiredXP = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredXP");
          final int _cursorIndexOfRequiredAchievement = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredAchievement");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfIsSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "isSelected");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfIconResource = CursorUtil.getColumnIndexOrThrow(_cursor, "iconResource");
          final int _cursorIndexOfPreviewResource = CursorUtil.getColumnIndexOrThrow(_cursor, "previewResource");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final int _cursorIndexOfUsageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "usageCount");
          final List<UnlockableEntity> _result = new ArrayList<UnlockableEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UnlockableEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpRequiredLevel;
            _tmpRequiredLevel = _cursor.getInt(_cursorIndexOfRequiredLevel);
            final int _tmpRequiredXP;
            _tmpRequiredXP = _cursor.getInt(_cursorIndexOfRequiredXP);
            final String _tmpRequiredAchievement;
            if (_cursor.isNull(_cursorIndexOfRequiredAchievement)) {
              _tmpRequiredAchievement = null;
            } else {
              _tmpRequiredAchievement = _cursor.getString(_cursorIndexOfRequiredAchievement);
            }
            final int _tmpCost;
            _tmpCost = _cursor.getInt(_cursorIndexOfCost);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final boolean _tmpIsSelected;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSelected);
            _tmpIsSelected = _tmp_1 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpIconResource;
            if (_cursor.isNull(_cursorIndexOfIconResource)) {
              _tmpIconResource = null;
            } else {
              _tmpIconResource = _cursor.getString(_cursorIndexOfIconResource);
            }
            final String _tmpPreviewResource;
            if (_cursor.isNull(_cursorIndexOfPreviewResource)) {
              _tmpPreviewResource = null;
            } else {
              _tmpPreviewResource = _cursor.getString(_cursorIndexOfPreviewResource);
            }
            final String _tmpRarity;
            _tmpRarity = _cursor.getString(_cursorIndexOfRarity);
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            final Long _tmpUnlockedAt;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmpUnlockedAt = null;
            } else {
              _tmpUnlockedAt = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            final int _tmpUsageCount;
            _tmpUsageCount = _cursor.getInt(_cursorIndexOfUsageCount);
            _item = new UnlockableEntity(_tmpId,_tmpType,_tmpName,_tmpDescription,_tmpRequiredLevel,_tmpRequiredXP,_tmpRequiredAchievement,_tmpCost,_tmpIsUnlocked,_tmpIsSelected,_tmpMetadata,_tmpIconResource,_tmpPreviewResource,_tmpRarity,_tmpOrderIndex,_tmpUnlockedAt,_tmpUsageCount);
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
  public Flow<List<UnlockableEntity>> getLockedItems() {
    final String _sql = "SELECT * FROM unlockables WHERE isUnlocked = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"unlockables"}, new Callable<List<UnlockableEntity>>() {
      @Override
      @NonNull
      public List<UnlockableEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfRequiredLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredLevel");
          final int _cursorIndexOfRequiredXP = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredXP");
          final int _cursorIndexOfRequiredAchievement = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredAchievement");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfIsSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "isSelected");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfIconResource = CursorUtil.getColumnIndexOrThrow(_cursor, "iconResource");
          final int _cursorIndexOfPreviewResource = CursorUtil.getColumnIndexOrThrow(_cursor, "previewResource");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final int _cursorIndexOfUsageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "usageCount");
          final List<UnlockableEntity> _result = new ArrayList<UnlockableEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UnlockableEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpRequiredLevel;
            _tmpRequiredLevel = _cursor.getInt(_cursorIndexOfRequiredLevel);
            final int _tmpRequiredXP;
            _tmpRequiredXP = _cursor.getInt(_cursorIndexOfRequiredXP);
            final String _tmpRequiredAchievement;
            if (_cursor.isNull(_cursorIndexOfRequiredAchievement)) {
              _tmpRequiredAchievement = null;
            } else {
              _tmpRequiredAchievement = _cursor.getString(_cursorIndexOfRequiredAchievement);
            }
            final int _tmpCost;
            _tmpCost = _cursor.getInt(_cursorIndexOfCost);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final boolean _tmpIsSelected;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSelected);
            _tmpIsSelected = _tmp_1 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpIconResource;
            if (_cursor.isNull(_cursorIndexOfIconResource)) {
              _tmpIconResource = null;
            } else {
              _tmpIconResource = _cursor.getString(_cursorIndexOfIconResource);
            }
            final String _tmpPreviewResource;
            if (_cursor.isNull(_cursorIndexOfPreviewResource)) {
              _tmpPreviewResource = null;
            } else {
              _tmpPreviewResource = _cursor.getString(_cursorIndexOfPreviewResource);
            }
            final String _tmpRarity;
            _tmpRarity = _cursor.getString(_cursorIndexOfRarity);
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            final Long _tmpUnlockedAt;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmpUnlockedAt = null;
            } else {
              _tmpUnlockedAt = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            final int _tmpUsageCount;
            _tmpUsageCount = _cursor.getInt(_cursorIndexOfUsageCount);
            _item = new UnlockableEntity(_tmpId,_tmpType,_tmpName,_tmpDescription,_tmpRequiredLevel,_tmpRequiredXP,_tmpRequiredAchievement,_tmpCost,_tmpIsUnlocked,_tmpIsSelected,_tmpMetadata,_tmpIconResource,_tmpPreviewResource,_tmpRarity,_tmpOrderIndex,_tmpUnlockedAt,_tmpUsageCount);
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
  public Object getSelectedByType(final String type,
      final Continuation<? super UnlockableEntity> $completion) {
    final String _sql = "SELECT * FROM unlockables WHERE type = ? AND isSelected = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, type);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UnlockableEntity>() {
      @Override
      @Nullable
      public UnlockableEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfRequiredLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredLevel");
          final int _cursorIndexOfRequiredXP = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredXP");
          final int _cursorIndexOfRequiredAchievement = CursorUtil.getColumnIndexOrThrow(_cursor, "requiredAchievement");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfIsSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "isSelected");
          final int _cursorIndexOfMetadata = CursorUtil.getColumnIndexOrThrow(_cursor, "metadata");
          final int _cursorIndexOfIconResource = CursorUtil.getColumnIndexOrThrow(_cursor, "iconResource");
          final int _cursorIndexOfPreviewResource = CursorUtil.getColumnIndexOrThrow(_cursor, "previewResource");
          final int _cursorIndexOfRarity = CursorUtil.getColumnIndexOrThrow(_cursor, "rarity");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final int _cursorIndexOfUsageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "usageCount");
          final UnlockableEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpRequiredLevel;
            _tmpRequiredLevel = _cursor.getInt(_cursorIndexOfRequiredLevel);
            final int _tmpRequiredXP;
            _tmpRequiredXP = _cursor.getInt(_cursorIndexOfRequiredXP);
            final String _tmpRequiredAchievement;
            if (_cursor.isNull(_cursorIndexOfRequiredAchievement)) {
              _tmpRequiredAchievement = null;
            } else {
              _tmpRequiredAchievement = _cursor.getString(_cursorIndexOfRequiredAchievement);
            }
            final int _tmpCost;
            _tmpCost = _cursor.getInt(_cursorIndexOfCost);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final boolean _tmpIsSelected;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSelected);
            _tmpIsSelected = _tmp_1 != 0;
            final String _tmpMetadata;
            if (_cursor.isNull(_cursorIndexOfMetadata)) {
              _tmpMetadata = null;
            } else {
              _tmpMetadata = _cursor.getString(_cursorIndexOfMetadata);
            }
            final String _tmpIconResource;
            if (_cursor.isNull(_cursorIndexOfIconResource)) {
              _tmpIconResource = null;
            } else {
              _tmpIconResource = _cursor.getString(_cursorIndexOfIconResource);
            }
            final String _tmpPreviewResource;
            if (_cursor.isNull(_cursorIndexOfPreviewResource)) {
              _tmpPreviewResource = null;
            } else {
              _tmpPreviewResource = _cursor.getString(_cursorIndexOfPreviewResource);
            }
            final String _tmpRarity;
            _tmpRarity = _cursor.getString(_cursorIndexOfRarity);
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            final Long _tmpUnlockedAt;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmpUnlockedAt = null;
            } else {
              _tmpUnlockedAt = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            final int _tmpUsageCount;
            _tmpUsageCount = _cursor.getInt(_cursorIndexOfUsageCount);
            _result = new UnlockableEntity(_tmpId,_tmpType,_tmpName,_tmpDescription,_tmpRequiredLevel,_tmpRequiredXP,_tmpRequiredAchievement,_tmpCost,_tmpIsUnlocked,_tmpIsSelected,_tmpMetadata,_tmpIconResource,_tmpPreviewResource,_tmpRarity,_tmpOrderIndex,_tmpUnlockedAt,_tmpUsageCount);
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
