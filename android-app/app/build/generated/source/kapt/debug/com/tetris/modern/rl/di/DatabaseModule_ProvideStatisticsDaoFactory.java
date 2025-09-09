package com.tetris.modern.rl.di;

import com.tetris.modern.rl.data.dao.StatisticsDao;
import com.tetris.modern.rl.data.database.TetrisDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideStatisticsDaoFactory implements Factory<StatisticsDao> {
  private final Provider<TetrisDatabase> databaseProvider;

  public DatabaseModule_ProvideStatisticsDaoFactory(Provider<TetrisDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public StatisticsDao get() {
    return provideStatisticsDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideStatisticsDaoFactory create(
      Provider<TetrisDatabase> databaseProvider) {
    return new DatabaseModule_ProvideStatisticsDaoFactory(databaseProvider);
  }

  public static StatisticsDao provideStatisticsDao(TetrisDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStatisticsDao(database));
  }
}
