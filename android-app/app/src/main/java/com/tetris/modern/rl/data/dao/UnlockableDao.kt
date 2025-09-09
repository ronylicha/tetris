package com.tetris.modern.rl.data.dao

import androidx.room.*
import com.tetris.modern.rl.data.entities.UnlockableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UnlockableDao {
    @Query("SELECT * FROM unlockables")
    fun getAllUnlockables(): Flow<List<UnlockableEntity>>
    
    @Query("SELECT * FROM unlockables WHERE id = :id")
    suspend fun getUnlockableById(id: String): UnlockableEntity?
    
    @Query("SELECT * FROM unlockables WHERE type = :type")
    fun getUnlockablesByType(type: String): Flow<List<UnlockableEntity>>
    
    @Query("SELECT * FROM unlockables WHERE isUnlocked = 1")
    fun getUnlockedItems(): Flow<List<UnlockableEntity>>
    
    @Query("SELECT * FROM unlockables WHERE isUnlocked = 0")
    fun getLockedItems(): Flow<List<UnlockableEntity>>
    
    @Query("SELECT * FROM unlockables WHERE type = :type AND isSelected = 1")
    suspend fun getSelectedByType(type: String): UnlockableEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnlockable(unlockable: UnlockableEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(unlockables: List<UnlockableEntity>)
    
    @Update
    suspend fun updateUnlockable(unlockable: UnlockableEntity)
    
    @Query("UPDATE unlockables SET isUnlocked = 1 WHERE id = :id")
    suspend fun unlockItem(id: String)
    
    @Query("UPDATE unlockables SET isSelected = 0 WHERE type = :type")
    suspend fun deselectAllByType(type: String)
    
    @Query("UPDATE unlockables SET isSelected = 1 WHERE id = :id")
    suspend fun selectItem(id: String)
    
    @Transaction
    suspend fun selectUnlockable(id: String) {
        getUnlockableById(id)?.let { unlockable ->
            deselectAllByType(unlockable.type)
            selectItem(id)
        }
    }
    
    @Delete
    suspend fun deleteUnlockable(unlockable: UnlockableEntity)
    
    @Query("DELETE FROM unlockables")
    suspend fun deleteAll()
}