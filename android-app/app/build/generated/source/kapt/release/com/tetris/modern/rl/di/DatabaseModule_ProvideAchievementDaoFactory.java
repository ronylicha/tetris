package com.tetris.modern.rl.di;

import com.tetris.modern.rl.data.dao.AchievementDao;
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
public final class DatabaseModule_ProvideAchievementDaoFactory implements Factory<AchievementDao> {
  private final Provider<TetrisDatabase> databaseProvider;

  public DatabaseModule_ProvideAchievementDaoFactory(Provider<TetrisDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public AchievementDao get() {
    return provideAchievementDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideAchievementDaoFactory create(
      Provider<TetrisDatabase> databaseProvider) {
    return new DatabaseModule_ProvideAchievementDaoFactory(databaseProvider);
  }

  public static AchievementDao provideAchievementDao(TetrisDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAchievementDao(database));
  }
}
