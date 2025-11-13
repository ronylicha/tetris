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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/tetris/modern/rl/auth/SyncStrategy;", "", "(Ljava/lang/String;I)V", "KEEP_LOCAL", "KEEP_REMOTE", "MERGE_BOTH", "app_debug"})
public enum SyncStrategy {
    /*public static final*/ KEEP_LOCAL /* = new KEEP_LOCAL() */,
    /*public static final*/ KEEP_REMOTE /* = new KEEP_REMOTE() */,
    /*public static final*/ MERGE_BOTH /* = new MERGE_BOTH() */;
    
    SyncStrategy() {
    }
    
    @org.jetbrains.annotations.NotNull
    public static kotlin.enums.EnumEntries<com.tetris.modern.rl.auth.SyncStrategy> getEntries() {
        return null;
    }
}