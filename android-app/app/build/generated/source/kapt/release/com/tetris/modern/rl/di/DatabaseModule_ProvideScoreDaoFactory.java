package com.tetris.modern.rl.di;

import com.tetris.modern.rl.data.dao.ScoreDao;
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
public final class DatabaseModule_ProvideScoreDaoFactory implements Factory<ScoreDao> {
  private final Provider<TetrisDatabase> databaseProvider;

  public DatabaseModule_ProvideScoreDaoFactory(Provider<TetrisDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ScoreDao get() {
    return provideScoreDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideScoreDaoFactory create(
      Provider<TetrisDatabase> databaseProvider) {
    return new DatabaseModule_ProvideScoreDaoFactory(databaseProvider);
  }

  public static ScoreDao provideScoreDao(TetrisDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideScoreDao(database));
  }
}
