package com.tetris.modern.rl.data.repository;

import android.content.Context;
import com.tetris.modern.rl.auth.AuthManager;
import com.tetris.modern.rl.data.dao.ProgressionDao;
import com.tetris.modern.rl.data.dao.ScoreDao;
import com.tetris.modern.rl.network.ApiService;
import com.tetris.modern.rl.progression.PlayerProgression;
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
public final class ProgressionRepository_Factory implements Factory<ProgressionRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<ProgressionDao> progressionDaoProvider;

  private final Provider<ScoreDao> scoreDaoProvider;

  private final Provider<ApiService> apiServiceProvider;

  private final Provider<AuthManager> authManagerProvider;

  private final Provider<PlayerProgression> playerProgressionProvider;

  public ProgressionRepository_Factory(Provider<Context> contextProvider,
      Provider<ProgressionDao> progressionDaoProvider, Provider<ScoreDao> scoreDaoProvider,
      Provider<ApiService> apiServiceProvider, Provider<AuthManager> authManagerProvider,
      Provider<PlayerProgression> playerProgressionProvider) {
    this.contextProvider = contextProvider;
    this.progressionDaoProvider = progressionDaoProvider;
    this.scoreDaoProvider = scoreDaoProvider;
    this.apiServiceProvider = apiServiceProvider;
    this.authManagerProvider = authManagerProvider;
    this.playerProgressionProvider = playerProgressionProvider;
  }

  @Override
  public ProgressionRepository get() {
    return newInstance(contextProvider.get(), progressionDaoProvider.get(), scoreDaoProvider.get(), apiServiceProvider.get(), authManagerProvider.get(), playerProgressionProvider.get());
  }

  public static ProgressionRepository_Factory create(Provider<Context> contextProvider,
      Provider<ProgressionDao> progressionDaoProvider, Provider<ScoreDao> scoreDaoProvider,
      Provider<ApiService> apiServiceProvider, Provider<AuthManager> authManagerProvider,
      Provider<PlayerProgression> playerProgressionProvider) {
    return new ProgressionRepository_Factory(contextProvider, progressionDaoProvider, scoreDaoProvider, apiServiceProvider, authManagerProvider, playerProgressionProvider);
  }

  public static ProgressionRepository newInstance(Context context, ProgressionDao progressionDao,
      ScoreDao scoreDao, ApiService apiService, AuthManager authManager,
      PlayerProgression playerProgression) {
    return new ProgressionRepository(context, progressionDao, scoreDao, apiService, authManager, playerProgression);
  }
}
