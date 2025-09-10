package com.tetris.modern.rl.network

import com.google.gson.GsonBuilder
import com.tetris.modern.rl.BuildConfig
import android.content.Context
import com.tetris.modern.rl.data.preferences.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    private const val BASE_URL = "https://your-server.com/" // Replace with actual server URL
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L
    
    @Provides
    @Singleton
    fun provideGson() = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create()
    
    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }
    
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            
        // Add logging interceptor in debug mode
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }
        
        // Add network connectivity interceptor
        builder.addInterceptor(NetworkConnectivityInterceptor())
        
        // Add retry interceptor
        builder.addInterceptor(RetryInterceptor())
        
        return builder.build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: com.google.gson.Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

/**
 * Interceptor to add authentication token to requests
 */
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        
        // Skip auth for public endpoints
        if (isPublicEndpoint(originalRequest.url.toString())) {
            return chain.proceed(originalRequest)
        }
        
        val token = tokenManager.getToken()
        
        return if (token != null) {
            val authenticatedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build()
            chain.proceed(authenticatedRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
    
    private fun isPublicEndpoint(url: String): Boolean {
        val publicEndpoints = listOf(
            "auth.php?action=login",
            "auth.php?action=register",
            "auth.php?action=guest",
            "scores.php" // GET scores is public
        )
        
        return publicEndpoints.any { url.contains(it) }
    }
}

/**
 * Interceptor to check network connectivity
 */
class NetworkConnectivityInterceptor : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        
        // Add offline cache headers if needed
        val modifiedRequest = request.newBuilder()
            .header("Cache-Control", "public, max-age=60")
            .build()
            
        return try {
            chain.proceed(modifiedRequest)
        } catch (e: Exception) {
            throw NoConnectivityException()
        }
    }
}

/**
 * Interceptor to retry failed requests
 */
class RetryInterceptor : Interceptor {
    
    companion object {
        private const val MAX_RETRIES = 3
        private const val RETRY_DELAY = 1000L // milliseconds
    }
    
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        var response: okhttp3.Response? = null
        var retryCount = 0
        
        while (retryCount < MAX_RETRIES) {
            try {
                response = chain.proceed(request)
                
                if (response.isSuccessful) {
                    return response
                }
                
                // Retry on specific error codes
                if (response.code in listOf(500, 502, 503, 504)) {
                    response.close()
                    Thread.sleep(RETRY_DELAY * (retryCount + 1))
                    retryCount++
                } else {
                    return response
                }
            } catch (e: Exception) {
                if (retryCount >= MAX_RETRIES - 1) {
                    throw e
                }
                Thread.sleep(RETRY_DELAY * (retryCount + 1))
                retryCount++
            }
        }
        
        return response ?: throw Exception("Failed after $MAX_RETRIES retries")
    }
}

/**
 * Custom exception for no connectivity
 */
class NoConnectivityException : Exception("No network connection available")