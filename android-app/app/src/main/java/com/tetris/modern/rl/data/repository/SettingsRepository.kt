package com.tetris.modern.rl.data.repository

import com.tetris.modern.rl.data.dao.SettingsDao
import com.tetris.modern.rl.data.entities.SettingsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val settingsDao: SettingsDao
) {
    fun getSettings(): Flow<SettingsEntity?> = settingsDao.getSettings()
    
    suspend fun updateSettings(settings: SettingsEntity) {
        settingsDao.updateSettings(settings)
    }
    
    suspend fun updateMasterVolume(volume: Float) {
        settingsDao.updateMasterVolume(volume)
    }
    
    suspend fun updateTheme(theme: String) {
        settingsDao.updateTheme(theme)
    }
    
    suspend fun updateMusic(music: String) {
        settingsDao.updateMusic(music)
    }
    
    suspend fun updateControlType(type: String) {
        val current = settingsDao.getSettingsSync() ?: SettingsEntity()
        settingsDao.updateSettings(current.copy(controlType = type))
    }
    
    suspend fun updateFirstLaunch(isFirstLaunch: Boolean) {
        val current = settingsDao.getSettingsSync() ?: SettingsEntity()
        settingsDao.updateSettings(current.copy(isFirstLaunch = isFirstLaunch))
    }
    
    suspend fun initializeDefaultSettings() {
        val settings = SettingsEntity()
        settingsDao.updateSettings(settings)
    }
}