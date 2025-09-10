package com.tetris.modern.rl.di;

import com.tetris.modern.rl.data.dao.ProgressionDao;
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
public final class DatabaseModule_ProvideProgressionDaoFactory implements Factory<ProgressionDao> {
  private final Provider<TetrisDatabase> databaseProvider;

  public DatabaseModule_ProvideProgressionDaoFactory(Provider<TetrisDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ProgressionDao get() {
    return provideProgressionDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideProgressionDaoFactory create(
      Provider<TetrisDatabase> databaseProvider) {
    return new DatabaseModule_ProvideProgressionDaoFactory(databaseProvider);
  }

  public static ProgressionDao provideProgressionDao(TetrisDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideProgressionDao(database));
  }
}
