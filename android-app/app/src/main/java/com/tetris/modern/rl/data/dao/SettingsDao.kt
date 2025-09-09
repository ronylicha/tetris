package com.tetris.modern.rl.data.dao

import androidx.room.*
import com.tetris.modern.rl.data.entities.SettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE id = 1")
    fun getSettings(): Flow<SettingsEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(settings: SettingsEntity)
    
    @Query("UPDATE settings SET masterVolume = :volume WHERE id = 1")
    suspend fun updateMasterVolume(volume: Float)
    
    @Query("UPDATE settings SET selectedTheme = :theme WHERE id = 1")
    suspend fun updateTheme(theme: String)
    
    @Query("UPDATE settings SET selectedMusic = :music WHERE id = 1")
    suspend fun updateMusic(music: String)
    
    @Query("SELECT * FROM settings WHERE id = 1")
    suspend fun getSettingsSync(): SettingsEntity?
}