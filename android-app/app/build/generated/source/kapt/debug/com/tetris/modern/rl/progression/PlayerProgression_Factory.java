package com.tetris.modern.rl.progression;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class PlayerProgression_Factory implements Factory<PlayerProgression> {
  @Override
  public PlayerProgression get() {
    return newInstance();
  }

  public static PlayerProgression_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PlayerProgression newInstance() {
    return new PlayerProgression();
  }

  private static final class InstanceHolder {
    private static final PlayerProgression_Factory INSTANCE = new PlayerProgression_Factory();
  }
}
