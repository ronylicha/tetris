package com.tetris.modern.rl.network;

import com.google.gson.GsonBuilder;
import com.tetris.modern.rl.BuildConfig;
import android.content.Context;
import com.tetris.modern.rl.data.preferences.TokenManager;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Interceptor to add authentication token to requests
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/tetris/modern/rl/network/AuthInterceptor;", "Lokhttp3/Interceptor;", "tokenManager", "Lcom/tetris/modern/rl/data/preferences/TokenManager;", "(Lcom/tetris/modern/rl/data/preferences/TokenManager;)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "isPublicEndpoint", "", "url", "", "app_release"})
public final class AuthInterceptor implements okhttp3.Interceptor {
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.preferences.TokenManager tokenManager = null;
    
    @javax.inject.Inject
    public AuthInterceptor(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.preferences.TokenManager tokenManager) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull
    okhttp3.Interceptor.Chain chain) {
        return null;
    }
    
    private final boolean isPublicEndpoint(java.lang.String url) {
        return false;
    }
}