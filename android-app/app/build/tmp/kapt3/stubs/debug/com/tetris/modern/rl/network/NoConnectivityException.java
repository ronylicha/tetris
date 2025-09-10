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
 * Custom exception for no connectivity
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2 = {"Lcom/tetris/modern/rl/network/NoConnectivityException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "()V", "app_debug"})
public final class NoConnectivityException extends java.lang.Exception {
    
    public NoConnectivityException() {
        super();
    }
}