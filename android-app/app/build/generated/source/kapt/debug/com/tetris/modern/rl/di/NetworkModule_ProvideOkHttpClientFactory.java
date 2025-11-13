package com.tetris.modern.rl.di;

import android.content.Context;
import com.tetris.modern.rl.network.AuthInterceptor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

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
public final class NetworkModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<Context> contextProvider;

  private final Provider<Cache> cacheProvider;

  private final Provider<AuthInterceptor> authInterceptorProvider;

  public NetworkModule_ProvideOkHttpClientFactory(Provider<Context> contextProvider,
      Provider<Cache> cacheProvider, Provider<AuthInterceptor> authInterceptorProvider) {
    this.contextProvider = contextProvider;
    this.cacheProvider = cacheProvider;
    this.authInterceptorProvider = authInterceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(contextProvider.get(), cacheProvider.get(), authInterceptorProvider.get());
  }

  public static NetworkModule_ProvideOkHttpClientFactory create(Provider<Context> contextProvider,
      Provider<Cache> cacheProvider, Provider<AuthInterceptor> authInterceptorProvider) {
    return new NetworkModule_ProvideOkHttpClientFactory(contextProvider, cacheProvider, authInterceptorProvider);
  }

  public static OkHttpClient provideOkHttpClient(Context context, Cache cache,
      AuthInterceptor authInterceptor) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOkHttpClient(context, cache, authInterceptor));
  }
}
