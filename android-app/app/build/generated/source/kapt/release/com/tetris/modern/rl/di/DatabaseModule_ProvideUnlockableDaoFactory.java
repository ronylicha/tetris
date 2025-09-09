package com.tetris.modern.rl.di;

import com.tetris.modern.rl.data.dao.UnlockableDao;
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
public final class DatabaseModule_ProvideUnlockableDaoFactory implements Factory<UnlockableDao> {
  private final Provider<TetrisDatabase> databaseProvider;

  public DatabaseModule_ProvideUnlockableDaoFactory(Provider<TetrisDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public UnlockableDao get() {
    return provideUnlockableDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideUnlockableDaoFactory create(
      Provider<TetrisDatabase> databaseProvider) {
    return new DatabaseModule_ProvideUnlockableDaoFactory(databaseProvider);
  }

  public static UnlockableDao provideUnlockableDao(TetrisDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideUnlockableDao(database));
  }
}
