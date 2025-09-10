package com.tetris.modern.rl.data.repository;

import com.tetris.modern.rl.data.dao.StatisticsDao;
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
public final class StatisticsRepository_Factory implements Factory<StatisticsRepository> {
  private final Provider<StatisticsDao> statisticsDaoProvider;

  public StatisticsRepository_Factory(Provider<StatisticsDao> statisticsDaoProvider) {
    this.statisticsDaoProvider = statisticsDaoProvider;
  }

  @Override
  public StatisticsRepository get() {
    return newInstance(statisticsDaoProvider.get());
  }

  public static StatisticsRepository_Factory create(Provider<StatisticsDao> statisticsDaoProvider) {
    return new StatisticsRepository_Factory(statisticsDaoProvider);
  }

  public static StatisticsRepository newInstance(StatisticsDao statisticsDao) {
    return new StatisticsRepository(statisticsDao);
  }
}
