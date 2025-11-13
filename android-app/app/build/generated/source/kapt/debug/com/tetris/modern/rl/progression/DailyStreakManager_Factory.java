package com.tetris.modern.rl.progression;

import com.tetris.modern.rl.data.dao.DailyStreakDao;
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
public final class DailyStreakManager_Factory implements Factory<DailyStreakManager> {
  private final Provider<DailyStreakDao> dailyStreakDaoProvider;

  public DailyStreakManager_Factory(Provider<DailyStreakDao> dailyStreakDaoProvider) {
    this.dailyStreakDaoProvider = dailyStreakDaoProvider;
  }

  @Override
  public DailyStreakManager get() {
    return newInstance(dailyStreakDaoProvider.get());
  }

  public static DailyStreakManager_Factory create(Provider<DailyStreakDao> dailyStreakDaoProvider) {
    return new DailyStreakManager_Factory(dailyStreakDaoProvider);
  }

  public static DailyStreakManager newInstance(DailyStreakDao dailyStreakDao) {
    return new DailyStreakManager(dailyStreakDao);
  }
}
