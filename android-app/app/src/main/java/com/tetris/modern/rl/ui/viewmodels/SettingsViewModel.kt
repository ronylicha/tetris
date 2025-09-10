package com.tetris.modern.rl.ui.viewmodels

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    
    private val prefs = application.getSharedPreferences("tetris_settings", Context.MODE_PRIVATE)
    
    val currentLanguage = mutableStateOf(getCurrentLanguage())
    
    private fun getCurrentLanguage(): String {
        return prefs.getString("app_language", Locale.getDefault().language) ?: "en"
    }
    
    fun setLanguage(languageCode: String) {
        viewModelScope.launch {
            // Save preference
            prefs.edit().putString("app_language", languageCode).apply()
            
            // Update locale
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            
            val config = Configuration(getApplication<Application>().resources.configuration)
            config.setLocale(locale)
            
            @Suppress("DEPRECATION")
            getApplication<Application>().resources.updateConfiguration(
                config,
                getApplication<Application>().resources.displayMetrics
            )
            
            currentLanguage.value = languageCode
        }
    }
    
    fun applyLanguageOnStartup() {
        val savedLanguage = getCurrentLanguage()
        if (savedLanguage != Locale.getDefault().language) {
            // Apply language without restarting
            val locale = Locale(savedLanguage)
            Locale.setDefault(locale)
            
            val config = Configuration(getApplication<Application>().resources.configuration)
            config.setLocale(locale)
            
            @Suppress("DEPRECATION")
            getApplication<Application>().resources.updateConfiguration(
                config,
                getApplication<Application>().resources.displayMetrics
            )
            
            currentLanguage.value = savedLanguage
        }
    }
}