package com.tetris.modern.rl.data.preferences

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("tetris_prefs", Context.MODE_PRIVATE)
    
    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }
    
    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }
    
    fun clearToken() {
        prefs.edit().remove("auth_token").apply()
    }
    
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
}