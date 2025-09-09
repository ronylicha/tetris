package com.tetris.modern.rl.data.dao

import androidx.room.*
import com.tetris.modern.rl.data.entities.CustomizationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomizationDao {
    @Query("SELECT * FROM customizations")
    fun getAllCustomizations(): Flow<List<CustomizationEntity>>
    
    @Query("SELECT * FROM customizations WHERE type = :type")
    fun getCustomizationsByType(type: String): Flow<List<CustomizationEntity>>
    
    @Query("SELECT * FROM customizations WHERE isUnlocked = 1")
    fun getUnlockedCustomizations(): Flow<List<CustomizationEntity>>
    
    @Query("SELECT * FROM customizations WHERE itemId = :itemId")
    fun getCustomization(itemId: String): Flow<CustomizationEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCustomization(customization: CustomizationEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCustomizations(customizations: List<CustomizationEntity>)
    
    @Query("UPDATE customizations SET isUnlocked = 1 WHERE itemId = :itemId")
    suspend fun unlockCustomization(itemId: String)
}