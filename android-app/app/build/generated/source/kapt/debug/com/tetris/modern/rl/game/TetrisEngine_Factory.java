package com.tetris.modern.rl.game;

import com.tetris.modern.rl.audio.AudioManager;
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
public final class TetrisEngine_Factory implements Factory<TetrisEngine> {
  private final Provider<AudioManager> audioManagerProvider;

  public TetrisEngine_Factory(Provider<AudioManager> audioManagerProvider) {
    this.audioManagerProvider = audioManagerProvider;
  }

  @Override
  public TetrisEngine get() {
    return newInstance(audioManagerProvider.get());
  }

  public static TetrisEngine_Factory create(Provider<AudioManager> audioManagerProvider) {
    return new TetrisEngine_Factory(audioManagerProvider);
  }

  public static TetrisEngine newInstance(AudioManager audioManager) {
    return new TetrisEngine(audioManager);
  }
}
