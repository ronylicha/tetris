package com.tetris.modern.rl.data.repository;

import com.tetris.modern.rl.data.dao.ProgressionDao;
import com.tetris.modern.rl.network.ApiService;
import com.tetris.modern.rl.progression.PlayerProgression;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
  private final Provider<ProgressionDao> progressionDaoProvider;

  private final Provider<ApiService> apiServiceProvider;

  private final Provider<PlayerProgression> playerProgressionProvider;

  public ProgressionRepository_Factory(Provider<ProgressionDao> progressionDaoProvider,
      Provider<ApiService> apiServiceProvider,
      Provider<PlayerProgression> playerProgressionProvider) {
    this.progressionDaoProvider = progressionDaoProvider;
    this.apiServiceProvider = apiServiceProvider;
    this.playerProgressionProvider = playerProgressionProvider;
  }

  @Override
  public ProgressionRepository get() {
    return newInstance(progressionDaoProvider.get(), apiServiceProvider.get(), playerProgressionProvider.get());
  }

  public static ProgressionRepository_Factory create(
      Provider<ProgressionDao> progressionDaoProvider, Provider<ApiService> apiServiceProvider,
      Provider<PlayerProgression> playerProgressionProvider) {
    return new ProgressionRepository_Factory(progressionDaoProvider, apiServiceProvider, playerProgressionProvider);
  }

  public static ProgressionRepository newInstance(ProgressionDao progressionDao,
      ApiService apiService, PlayerProgression playerProgression) {
    return new ProgressionRepository(progressionDao, apiService, playerProgression);
  }
}
