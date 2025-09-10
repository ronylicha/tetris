package com.tetris.modern.rl.achievements;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AchievementSystem_Factory implements Factory<AchievementSystem> {
  private final Provider<Context> contextProvider;

  public AchievementSystem_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AchievementSystem get() {
    return newInstance(contextProvider.get());
  }

  public static AchievementSystem_Factory create(Provider<Context> contextProvider) {
    return new AchievementSystem_Factory(contextProvider);
  }

  public static AchievementSystem newInstance(Context context) {
    return new AchievementSystem(context);
  }
}
