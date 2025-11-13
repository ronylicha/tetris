package com.tetris.modern.rl.auth;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.tetris.modern.rl.network.ApiService;
import com.tetris.modern.rl.network.AuthInterceptor;
import com.tetris.modern.rl.network.TokenManager;
import com.tetris.modern.rl.network.models.*;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import javax.inject.Singleton;
import kotlinx.coroutines.Dispatchers;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0007\u0018\u00002\u00020\u0001:\u0001KB)\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u001e\u001a\u00020\u001fH\u0002J0\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!2\b\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010$H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b&\u0010\'J\b\u0010(\u001a\u00020\u001fH\u0002J4\u0010)\u001a\b\u0012\u0004\u0012\u00020\r0!2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0006\u0010*\u001a\u00020$H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b+\u0010,J\b\u0010-\u001a\u0004\u0018\u00010$J\b\u0010.\u001a\u0004\u0018\u00010\u0015J\b\u0010/\u001a\u00020\u001fH\u0002J\u0006\u00100\u001a\u000201J,\u00102\u001a\b\u0012\u0004\u0012\u00020\r0!2\u0006\u0010%\u001a\u00020$2\u0006\u0010*\u001a\u00020$H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b3\u0010\'J$\u00104\u001a\b\u0012\u0004\u0012\u00020\r0!2\u0006\u00105\u001a\u00020$H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b6\u00107J$\u00108\u001a\b\u0012\u0004\u0012\u00020\r0!2\u0006\u00109\u001a\u00020:H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b;\u0010<J\u0006\u0010=\u001a\u00020\u001fJ\u001c\u0010>\u001a\b\u0012\u0004\u0012\u0002010!H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b?\u0010@J<\u0010A\u001a\b\u0012\u0004\u0012\u00020\r0!2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0006\u0010*\u001a\u00020$2\u0006\u0010B\u001a\u00020$H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bC\u0010DJ \u0010E\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020$2\u0006\u0010B\u001a\u00020$2\u0006\u0010F\u001a\u000201H\u0002J\u000e\u0010G\u001a\u00020\u001f2\u0006\u0010H\u001a\u00020\u000fJ\u0010\u0010I\u001a\u00020\u001f2\u0006\u0010J\u001a\u00020$H\u0002R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006L"}, d2 = {"Lcom/tetris/modern/rl/auth/AuthManager;", "", "context", "Landroid/content/Context;", "apiService", "Lcom/tetris/modern/rl/network/ApiService;", "authInterceptor", "Lcom/tetris/modern/rl/network/AuthInterceptor;", "tokenManager", "Lcom/tetris/modern/rl/network/TokenManager;", "(Landroid/content/Context;Lcom/tetris/modern/rl/network/ApiService;Lcom/tetris/modern/rl/network/AuthInterceptor;Lcom/tetris/modern/rl/network/TokenManager;)V", "_authState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/tetris/modern/rl/auth/AuthManager$AuthState;", "_syncPreference", "Lcom/tetris/modern/rl/auth/SyncStrategy;", "authState", "Lkotlinx/coroutines/flow/StateFlow;", "getAuthState", "()Lkotlinx/coroutines/flow/StateFlow;", "googleSignInClient", "Lcom/google/android/gms/auth/api/signin/GoogleSignInClient;", "sharedPreferences", "Landroid/content/SharedPreferences;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "sharedPreferences$delegate", "Lkotlin/Lazy;", "syncPreference", "getSyncPreference", "checkAuthStatus", "", "checkAvailability", "Lkotlin/Result;", "Lcom/tetris/modern/rl/network/models/AvailabilityCheckResponse;", "username", "", "email", "checkAvailability-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearUserInfo", "convertGuestToFullAccount", "password", "convertGuestToFullAccount-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentUserId", "getGoogleSignInClient", "initializeGoogleSignIn", "isAuthenticated", "", "login", "login-0E7RQCE", "loginAsGuest", "deviceId", "loginAsGuest-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loginWithGoogle", "account", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;", "loginWithGoogle-gIAlu-s", "(Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "refreshToken", "refreshToken-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "displayName", "register-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserInfo", "isGuest", "setSyncPreference", "strategy", "validateTokenAsync", "token", "AuthState", "app_debug"})
public final class AuthManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.network.ApiService apiService = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.network.AuthInterceptor authInterceptor = null;
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.network.TokenManager tokenManager = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy sharedPreferences$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.tetris.modern.rl.auth.AuthManager.AuthState> _authState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.auth.AuthManager.AuthState> authState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.tetris.modern.rl.auth.SyncStrategy> _syncPreference = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.auth.SyncStrategy> syncPreference = null;
    @org.jetbrains.annotations.Nullable
    private com.google.android.gms.auth.api.signin.GoogleSignInClient googleSignInClient;
    
    @javax.inject.Inject
    public AuthManager(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.ApiService apiService, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.AuthInterceptor authInterceptor, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.TokenManager tokenManager) {
        super();
    }
    
    private final android.content.SharedPreferences getSharedPreferences() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.auth.AuthManager.AuthState> getAuthState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.auth.SyncStrategy> getSyncPreference() {
        return null;
    }
    
    private final void initializeGoogleSignIn() {
    }
    
    private final void checkAuthStatus() {
    }
    
    private final void validateTokenAsync(java.lang.String token) {
    }
    
    public final void logout() {
    }
    
    private final void saveUserInfo(java.lang.String username, java.lang.String displayName, boolean isGuest) {
    }
    
    private final void clearUserInfo() {
    }
    
    public final void setSyncPreference(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.auth.SyncStrategy strategy) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.android.gms.auth.api.signin.GoogleSignInClient getGoogleSignInClient() {
        return null;
    }
    
    public final boolean isAuthenticated() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getCurrentUserId() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n\u00a8\u0006\u000b"}, d2 = {"Lcom/tetris/modern/rl/auth/AuthManager$AuthState;", "", "()V", "Authenticated", "Error", "Loading", "NotAuthenticated", "Lcom/tetris/modern/rl/auth/AuthManager$AuthState$Authenticated;", "Lcom/tetris/modern/rl/auth/AuthManager$AuthState$Error;", "Lcom/tetris/modern/rl/auth/AuthManager$AuthState$Loading;", "Lcom/tetris/modern/rl/auth/AuthManager$AuthState$NotAuthenticated;", "app_debug"})
    public static abstract class AuthState {
        
        private AuthState() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J1\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n\u00a8\u0006\u0019"}, d2 = {"Lcom/tetris/modern/rl/auth/AuthManager$AuthState$Authenticated;", "Lcom/tetris/modern/rl/auth/AuthManager$AuthState;", "userId", "", "username", "displayName", "isGuest", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", "getDisplayName", "()Ljava/lang/String;", "()Z", "getUserId", "getUsername", "component1", "component2", "component3", "component4", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class Authenticated extends com.tetris.modern.rl.auth.AuthManager.AuthState {
            @org.jetbrains.annotations.NotNull
            private final java.lang.String userId = null;
            @org.jetbrains.annotations.NotNull
            private final java.lang.String username = null;
            @org.jetbrains.annotations.NotNull
            private final java.lang.String displayName = null;
            private final boolean isGuest = false;
            
            public Authenticated(@org.jetbrains.annotations.NotNull
            java.lang.String userId, @org.jetbrains.annotations.NotNull
            java.lang.String username, @org.jetbrains.annotations.NotNull
            java.lang.String displayName, boolean isGuest) {
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String getUserId() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String getUsername() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String getDisplayName() {
                return null;
            }
            
            public final boolean isGuest() {
                return false;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String component3() {
                return null;
            }
            
            public final boolean component4() {
                return false;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.tetris.modern.rl.auth.AuthManager.AuthState.Authenticated copy(@org.jetbrains.annotations.NotNull
            java.lang.String userId, @org.jetbrains.annotations.NotNull
            java.lang.String username, @org.jetbrains.annotations.NotNull
            java.lang.String displayName, boolean isGuest) {
                return null;
            }
            
            @java.lang.Override
            public boolean equals(@org.jetbrains.annotations.Nullable
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override
            @org.jetbrains.annotations.NotNull
            public java.lang.String toString() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/tetris/modern/rl/auth/AuthManager$AuthState$Error;", "Lcom/tetris/modern/rl/auth/AuthManager$AuthState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class Error extends com.tetris.modern.rl.auth.AuthManager.AuthState {
            @org.jetbrains.annotations.NotNull
            private final java.lang.String message = null;
            
            public Error(@org.jetbrains.annotations.NotNull
            java.lang.String message) {
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String getMessage() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.tetris.modern.rl.auth.AuthManager.AuthState.Error copy(@org.jetbrains.annotations.NotNull
            java.lang.String message) {
                return null;
            }
            
            @java.lang.Override
            public boolean equals(@org.jetbrains.annotations.Nullable
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override
            @org.jetbrains.annotations.NotNull
            public java.lang.String toString() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/tetris/modern/rl/auth/AuthManager$AuthState$Loading;", "Lcom/tetris/modern/rl/auth/AuthManager$AuthState;", "()V", "app_debug"})
        public static final class Loading extends com.tetris.modern.rl.auth.AuthManager.AuthState {
            @org.jetbrains.annotations.NotNull
            public static final com.tetris.modern.rl.auth.AuthManager.AuthState.Loading INSTANCE = null;
            
            private Loading() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/tetris/modern/rl/auth/AuthManager$AuthState$NotAuthenticated;", "Lcom/tetris/modern/rl/auth/AuthManager$AuthState;", "()V", "app_debug"})
        public static final class NotAuthenticated extends com.tetris.modern.rl.auth.AuthManager.AuthState {
            @org.jetbrains.annotations.NotNull
            public static final com.tetris.modern.rl.auth.AuthManager.AuthState.NotAuthenticated INSTANCE = null;
            
            private NotAuthenticated() {
            }
        }
    }
}