package com.tetris.modern.rl.data.repository;

import com.tetris.modern.rl.data.dao.ScoreDao;
import com.tetris.modern.rl.network.ApiService;
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
public final class ScoreRepository_Factory implements Factory<ScoreRepository> {
  private final Provider<ScoreDao> scoreDaoProvider;

  private final Provider<ApiService> apiServiceProvider;

  public ScoreRepository_Factory(Provider<ScoreDao> scoreDaoProvider,
      Provider<ApiService> apiServiceProvider) {
    this.scoreDaoProvider = scoreDaoProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public ScoreRepository get() {
    return newInstance(scoreDaoProvider.get(), apiServiceProvider.get());
  }

  public static ScoreRepository_Factory create(Provider<ScoreDao> scoreDaoProvider,
      Provider<ApiService> apiServiceProvider) {
    return new ScoreRepository_Factory(scoreDaoProvider, apiServiceProvider);
  }

  public static ScoreRepository newInstance(ScoreDao scoreDao, ApiService apiService) {
    return new ScoreRepository(scoreDao, apiService);
  }
}
