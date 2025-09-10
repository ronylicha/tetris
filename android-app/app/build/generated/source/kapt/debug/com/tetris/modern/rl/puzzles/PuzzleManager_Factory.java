package com.tetris.modern.rl.puzzles;

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
public final class PuzzleManager_Factory implements Factory<PuzzleManager> {
  @Override
  public PuzzleManager get() {
    return newInstance();
  }

  public static PuzzleManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PuzzleManager newInstance() {
    return new PuzzleManager();
  }

  private static final class InstanceHolder {
    private static final PuzzleManager_Factory INSTANCE = new PuzzleManager_Factory();
  }
}
