package com.tetris.modern.rl.auth;

import android.content.Context;
import com.tetris.modern.rl.network.ApiService;
import com.tetris.modern.rl.network.AuthInterceptor;
import com.tetris.modern.rl.network.TokenManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AuthManager_Factory implements Factory<AuthManager> {
  private final Provider<Context> contextProvider;

  private final Provider<ApiService> apiServiceProvider;

  private final Provider<AuthInterceptor> authInterceptorProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public AuthManager_Factory(Provider<Context> contextProvider,
      Provider<ApiService> apiServiceProvider, Provider<AuthInterceptor> authInterceptorProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.contextProvider = contextProvider;
    this.apiServiceProvider = apiServiceProvider;
    this.authInterceptorProvider = authInterceptorProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public AuthManager get() {
    return newInstance(contextProvider.get(), apiServiceProvider.get(), authInterceptorProvider.get(), tokenManagerProvider.get());
  }

  public static AuthManager_Factory create(Provider<Context> contextProvider,
      Provider<ApiService> apiServiceProvider, Provider<AuthInterceptor> authInterceptorProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new AuthManager_Factory(contextProvider, apiServiceProvider, authInterceptorProvider, tokenManagerProvider);
  }

  public static AuthManager newInstance(Context context, ApiService apiService,
      AuthInterceptor authInterceptor, TokenManager tokenManager) {
    return new AuthManager(context, apiService, authInterceptor, tokenManager);
  }
}
