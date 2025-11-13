package com.tetris.modern.rl.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.tetris.modern.rl.auth.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {
    
    val authState: StateFlow<AuthManager.AuthState> = authManager.authState
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    private val _usernameAvailable = MutableStateFlow<Boolean?>(null)
    val usernameAvailable: StateFlow<Boolean?> = _usernameAvailable.asStateFlow()
    
    private val _emailAvailable = MutableStateFlow<Boolean?>(null)
    val emailAvailable: StateFlow<Boolean?> = _emailAvailable.asStateFlow()
    
    suspend fun checkUsernameAvailability(username: String) {
        if (username.length < 3) {
            _usernameAvailable.value = null
            return
        }
        
        authManager.checkAvailability(username = username, email = null)
            .onSuccess { result ->
                _usernameAvailable.value = result.usernameAvailable
            }
            .onFailure {
                _usernameAvailable.value = null
            }
    }
    
    suspend fun checkEmailAvailability(email: String) {
        if (!email.contains("@")) {
            _emailAvailable.value = null
            return
        }
        
        authManager.checkAvailability(username = null, email = email)
            .onSuccess { result ->
                _emailAvailable.value = result.emailAvailable
            }
            .onFailure {
                _emailAvailable.value = null
            }
    }
    
    suspend fun login(email: String, password: String) {
        _isLoading.value = true
        _errorMessage.value = null
        
        authManager.login(email, password)
            .onSuccess {
                _errorMessage.value = null
            }
            .onFailure { exception ->
                _errorMessage.value = exception.message
            }
        
        _isLoading.value = false
    }
    
    suspend fun register(
        username: String,
        email: String,
        password: String,
        displayName: String
    ) {
        _isLoading.value = true
        _errorMessage.value = null
        
        authManager.register(username, email, password, displayName)
            .onSuccess {
                _errorMessage.value = null
            }
            .onFailure { exception ->
                _errorMessage.value = exception.message
            }
        
        _isLoading.value = false
    }
    
    suspend fun loginAsGuest(deviceId: String) {
        _isLoading.value = true
        _errorMessage.value = null
        
        authManager.loginAsGuest(deviceId)
            .onSuccess {
                _errorMessage.value = null
            }
            .onFailure { exception ->
                _errorMessage.value = exception.message
            }
        
        _isLoading.value = false
    }
    
    suspend fun loginWithGoogle(account: GoogleSignInAccount) {
        _isLoading.value = true
        _errorMessage.value = null
        
        authManager.loginWithGoogle(account)
            .onSuccess {
                _errorMessage.value = null
            }
            .onFailure { exception ->
                _errorMessage.value = exception.message
            }
        
        _isLoading.value = false
    }
    
    fun getGoogleSignInIntent(): Intent? {
        return authManager.getGoogleSignInClient()?.signInIntent
    }
    
    fun logout() {
        authManager.logout()
    }
    
    fun setError(message: String) {
        _errorMessage.value = message
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}