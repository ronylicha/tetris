package com.tetris.modern.rl.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.tetris.modern.rl.network.ApiService
import com.tetris.modern.rl.network.AuthInterceptor
import com.tetris.modern.rl.network.TokenManager
import com.tetris.modern.rl.network.models.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

@Singleton
class AuthManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
    private val authInterceptor: AuthInterceptor,
    private val tokenManager: TokenManager
) {
    
    private val sharedPreferences: SharedPreferences by lazy {
        try {
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            EncryptedSharedPreferences.create(
                "user_prefs", // Different name to avoid conflicts with TokenManager
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            // Fallback to regular SharedPreferences if encryption fails
            context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        }
    }
    
    sealed class AuthState {
        object Loading : AuthState()
        object NotAuthenticated : AuthState()
        data class Authenticated(
            val userId: String,
            val username: String,
            val displayName: String,
            val isGuest: Boolean = false
        ) : AuthState()
        data class Error(val message: String) : AuthState()
    }
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    private val _syncPreference = MutableStateFlow(SyncStrategy.MERGE_BOTH)
    val syncPreference: StateFlow<SyncStrategy> = _syncPreference.asStateFlow()
    
    private var googleSignInClient: GoogleSignInClient? = null
    
    init {
        checkAuthStatus()
        initializeGoogleSignIn()
    }
    
    private fun initializeGoogleSignIn() {
        // Note: Replace with actual OAuth 2.0 client ID from Google Cloud Console
        val serverClientId = "YOUR_ACTUAL_SERVER_CLIENT_ID" // TODO: Replace with actual client ID
        
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestEmail()
            .apply {
                // Only request ID token if we have a valid client ID
                if (serverClientId != "YOUR_ACTUAL_SERVER_CLIENT_ID") {
                    requestIdToken(serverClientId)
                }
            }
            .build()
        
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }
    
    private fun checkAuthStatus() {
        // Use TokenManager instead of AuthInterceptor for consistency
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tokenManager.authToken.collect { token ->
                    tokenManager.userId.collect { userId ->
                        if (token != null && userId != null) {
                            // Retrieve saved user information
                            val savedUsername = sharedPreferences.getString("username", null) ?: "user_$userId"
                            val savedDisplayName = sharedPreferences.getString("display_name", null) ?: savedUsername
                            val isGuest = sharedPreferences.getBoolean("is_guest", false)
                            
                            // For offline-first approach, consider user authenticated if token exists
                            // Server validation will happen asynchronously
                            _authState.value = AuthState.Authenticated(
                                userId = userId,
                                username = savedUsername,
                                displayName = savedDisplayName,
                                isGuest = isGuest
                            )
                            
                            // Validate token with server asynchronously (non-blocking)
                            validateTokenAsync(token)
                        } else {
                            _authState.value = AuthState.NotAuthenticated
                        }
                        return@collect
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.NotAuthenticated
            }
        }
    }
    
    private fun validateTokenAsync(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.refreshToken("Bearer $token")
                if (response.isSuccessful && response.body() != null) {
                    // Token is valid, update if new token provided
                    response.body()?.token?.let { newToken ->
                        tokenManager.saveAuthToken(newToken)
                    }
                } else {
                    // Token invalid, clear and set as not authenticated
                    tokenManager.clearTokens()
                    authInterceptor.clearTokens()
                    _authState.value = AuthState.NotAuthenticated
                }
            } catch (e: Exception) {
                // Network error - keep user authenticated with cached token
                // This ensures offline functionality
            }
        }
    }
    
    suspend fun login(email: String, password: String): Result<AuthState> {
        return try {
            _authState.value = AuthState.Loading
            
            val response = apiService.login(
                LoginRequest(
                    username = null,
                    email = email,
                    password = password,
                    googleToken = null
                )
            )
            
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                
                // Save tokens and user info
                authInterceptor.saveAuthToken(authResponse.token)
                authResponse.refreshToken?.let { authInterceptor.saveRefreshToken(it) }
                authInterceptor.saveUserId(authResponse.userId)
                
                // Save user information to SharedPreferences
                saveUserInfo(
                    username = authResponse.username,
                    displayName = authResponse.displayName,
                    isGuest = false
                )
                
                val authenticatedState = AuthState.Authenticated(
                    userId = authResponse.userId,
                    username = authResponse.username,
                    displayName = authResponse.displayName,
                    isGuest = false
                )
                
                _authState.value = authenticatedState
                Result.success(authenticatedState)
            } else {
                val error = "Login failed: ${response.message()}"
                _authState.value = AuthState.Error(error)
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            val error = "Login error: ${e.message}"
            _authState.value = AuthState.Error(error)
            Result.failure(e)
        }
    }
    
    suspend fun register(
        username: String,
        email: String,
        password: String,
        displayName: String
    ): Result<AuthState> {
        return try {
            _authState.value = AuthState.Loading
            
            val response = apiService.register(
                RegisterRequest(
                    username = username,
                    email = email,
                    password = password,
                    displayName = displayName
                )
            )
            
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                
                // Save tokens and user info
                authInterceptor.saveAuthToken(authResponse.token)
                authResponse.refreshToken?.let { authInterceptor.saveRefreshToken(it) }
                authInterceptor.saveUserId(authResponse.userId)
                
                // Save user information to SharedPreferences
                saveUserInfo(
                    username = authResponse.username,
                    displayName = authResponse.displayName,
                    isGuest = false
                )
                
                val authenticatedState = AuthState.Authenticated(
                    userId = authResponse.userId,
                    username = authResponse.username,
                    displayName = authResponse.displayName,
                    isGuest = false
                )
                
                _authState.value = authenticatedState
                Result.success(authenticatedState)
            } else {
                val error = "Registration failed: ${response.message()}"
                _authState.value = AuthState.Error(error)
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            val error = "Registration error: ${e.message}"
            _authState.value = AuthState.Error(error)
            Result.failure(e)
        }
    }
    
    suspend fun loginAsGuest(deviceId: String): Result<AuthState> {
        return try {
            _authState.value = AuthState.Loading
            
            val response = apiService.loginAsGuest(
                GuestLoginRequest(deviceId = deviceId)
            )
            
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                
                // Save tokens using both systems
                authInterceptor.saveAuthToken(authResponse.token)
                authInterceptor.saveUserId(authResponse.userId)
                
                tokenManager.saveAuthToken(authResponse.token)
                tokenManager.saveUserId(authResponse.userId)
                
                val authenticatedState = AuthState.Authenticated(
                    userId = authResponse.userId,
                    username = authResponse.username,
                    displayName = authResponse.displayName,
                    isGuest = true
                )
                
                _authState.value = authenticatedState
                Result.success(authenticatedState)
            } else {
                val error = "Guest login failed: ${response.message()}"
                _authState.value = AuthState.Error(error)
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            val error = "Guest login error: ${e.message}"
            _authState.value = AuthState.Error(error)
            Result.failure(e)
        }
    }
    
    suspend fun loginWithGoogle(account: GoogleSignInAccount): Result<AuthState> {
        return try {
            _authState.value = AuthState.Loading
            
            // Send Google token to server for validation
            val response = apiService.login(
                LoginRequest(
                    email = account.email ?: "",
                    password = "", // Server should handle Google auth differently
                    googleToken = account.idToken
                )
            )
            
if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                
                // Save tokens using both systems
                authInterceptor.saveAuthToken(authResponse.token)
                authResponse.refreshToken?.let { authInterceptor.saveRefreshToken(it) }
                authInterceptor.saveUserId(authResponse.userId)
                
                tokenManager.saveAuthToken(authResponse.token)
                authResponse.refreshToken?.let { tokenManager.saveRefreshToken(it) }
                tokenManager.saveUserId(authResponse.userId)
                
                // Also save in TokenManager for consistency
                tokenManager.saveAuthToken(authResponse.token)
                authResponse.refreshToken?.let { tokenManager.saveRefreshToken(it) }
                tokenManager.saveUserId(authResponse.userId)
                
                // Also save in TokenManager for consistency
                tokenManager.saveAuthToken(authResponse.token)
                authResponse.refreshToken?.let { tokenManager.saveRefreshToken(it) }
                tokenManager.saveUserId(authResponse.userId)
                
                val authenticatedState = AuthState.Authenticated(
                    userId = authResponse.userId,
                    username = authResponse.username,
                    displayName = account.displayName ?: authResponse.displayName,
                    isGuest = false
                )
                
                _authState.value = authenticatedState
                Result.success(authenticatedState)
            } else {
                val error = "Google login failed: ${response.message()}"
                _authState.value = AuthState.Error(error)
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            val error = "Google login error: ${e.message}"
            _authState.value = AuthState.Error(error)
            Result.failure(e)
        }
    }
    
    suspend fun convertGuestToFullAccount(
        username: String,
        email: String,
        password: String
    ): Result<AuthState> {
        val currentState = _authState.value
        if (currentState !is AuthState.Authenticated || !currentState.isGuest) {
            return Result.failure(Exception("Not a guest account"))
        }
        
        return try {
            _authState.value = AuthState.Loading
            
            // Convert guest account on server
            val response = apiService.register(
                RegisterRequest(
                    username = username,
                    email = email,
                    password = password,
                    displayName = currentState.displayName,
                    guestUserId = currentState.userId
                )
            )
            
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                
                // Update tokens using both systems
                authInterceptor.saveAuthToken(authResponse.token)
                authResponse.refreshToken?.let { authInterceptor.saveRefreshToken(it) }
                
                tokenManager.saveAuthToken(authResponse.token)
                authResponse.refreshToken?.let { tokenManager.saveRefreshToken(it) }
                
                val authenticatedState = AuthState.Authenticated(
                    userId = authResponse.userId,
                    username = authResponse.username,
                    displayName = authResponse.displayName,
                    isGuest = false
                )
                
                _authState.value = authenticatedState
                Result.success(authenticatedState)
            } else {
                val error = "Account conversion failed: ${response.message()}"
                _authState.value = currentState // Restore previous state
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            val error = "Account conversion error: ${e.message}"
            _authState.value = currentState // Restore previous state
            Result.failure(e)
        }
    }
    
    suspend fun refreshToken(): Result<Boolean> {
        // Try AuthInterceptor for refresh token
        val refreshToken = authInterceptor.getRefreshToken() 
            ?: return Result.failure(Exception("No refresh token"))
        
        return try {
            val response = apiService.refreshToken("Bearer $refreshToken")
            
            if (response.isSuccessful && response.body() != null) {
                val tokenResponse = response.body()!!
                // Update both systems
                authInterceptor.saveAuthToken(tokenResponse.token)
                tokenResponse.refreshToken?.let { authInterceptor.saveRefreshToken(it) }
                
                tokenManager.saveAuthToken(tokenResponse.token)
                tokenResponse.refreshToken?.let { tokenManager.saveRefreshToken(it) }
                Result.success(true)
            } else {
                // Token refresh failed, need to re-login
                logout()
                Result.failure(Exception("Token refresh failed"))
            }
        } catch (e: Exception) {
            logout()
            Result.failure(e)
        }
    }
    
    suspend fun checkAvailability(username: String?, email: String?): Result<AvailabilityCheckResponse> {
        return try {
            val response = apiService.checkAvailability(
                AvailabilityCheckRequest(
                    username = username,
                    email = email
                )
            )
            
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Availability check failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun logout() {
        // Clear tokens from both systems
        authInterceptor.clearTokens()
        CoroutineScope(Dispatchers.IO).launch {
            tokenManager.clearTokens()
        }
        clearUserInfo()
        _authState.value = AuthState.NotAuthenticated
        googleSignInClient?.signOut()
    }
    
    private fun saveUserInfo(username: String, displayName: String, isGuest: Boolean) {
        sharedPreferences.edit().apply {
            putString("username", username)
            putString("display_name", displayName)
            putBoolean("is_guest", isGuest)
            apply()
        }
    }
    
    private fun clearUserInfo() {
        sharedPreferences.edit().apply {
            remove("username")
            remove("display_name")
            remove("is_guest")
            apply()
        }
    }
    
    fun setSyncPreference(strategy: SyncStrategy) {
        _syncPreference.value = strategy
    }
    
    fun getGoogleSignInClient(): GoogleSignInClient? = googleSignInClient
    
    fun isAuthenticated(): Boolean = _authState.value is AuthState.Authenticated
    
    fun getCurrentUserId(): String? {
        // Try to get from auth state first, then from AuthInterceptor as fallback
        return when (val state = _authState.value) {
            is AuthState.Authenticated -> state.userId
            else -> authInterceptor.getUserId()
        }
    }
}

enum class SyncStrategy {
    KEEP_LOCAL,     // Overwrite cloud with local data
    KEEP_REMOTE,    // Overwrite local with cloud data
    MERGE_BOTH      // Intelligently merge both
}