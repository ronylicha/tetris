package com.tetris.modern.rl.di;

import com.tetris.modern.rl.data.dao.DailyChallengeDao;
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
public final class DatabaseModule_ProvideDailyChallengeDaoFactory implements Factory<DailyChallengeDao> {
  private final Provider<TetrisDatabase> databaseProvider;

  public DatabaseModule_ProvideDailyChallengeDaoFactory(Provider<TetrisDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public DailyChallengeDao get() {
    return provideDailyChallengeDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideDailyChallengeDaoFactory create(
      Provider<TetrisDatabase> databaseProvider) {
    return new DatabaseModule_ProvideDailyChallengeDaoFactory(databaseProvider);
  }

  public static DailyChallengeDao provideDailyChallengeDao(TetrisDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDailyChallengeDao(database));
  }
}
