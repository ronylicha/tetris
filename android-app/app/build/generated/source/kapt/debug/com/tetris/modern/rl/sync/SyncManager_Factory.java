package com.tetris.modern.rl.sync;

import android.content.Context;
import com.tetris.modern.rl.auth.AuthManager;
import com.tetris.modern.rl.data.dao.ScoreDao;
import com.tetris.modern.rl.network.ApiService;
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
public final class SyncManager_Factory implements Factory<SyncManager> {
  private final Provider<Context> contextProvider;

  private final Provider<ApiService> apiServiceProvider;

  private final Provider<ScoreDao> scoreDaoProvider;

  private final Provider<AuthManager> authManagerProvider;

  public SyncManager_Factory(Provider<Context> contextProvider,
      Provider<ApiService> apiServiceProvider, Provider<ScoreDao> scoreDaoProvider,
      Provider<AuthManager> authManagerProvider) {
    this.contextProvider = contextProvider;
    this.apiServiceProvider = apiServiceProvider;
    this.scoreDaoProvider = scoreDaoProvider;
    this.authManagerProvider = authManagerProvider;
  }

  @Override
  public SyncManager get() {
    return newInstance(contextProvider.get(), apiServiceProvider.get(), scoreDaoProvider.get(), authManagerProvider.get());
  }

  public static SyncManager_Factory create(Provider<Context> contextProvider,
      Provider<ApiService> apiServiceProvider, Provider<ScoreDao> scoreDaoProvider,
      Provider<AuthManager> authManagerProvider) {
    return new SyncManager_Factory(contextProvider, apiServiceProvider, scoreDaoProvider, authManagerProvider);
  }

  public static SyncManager newInstance(Context context, ApiService apiService, ScoreDao scoreDao,
      AuthManager authManager) {
    return new SyncManager(context, apiService, scoreDao, authManager);
  }
}
