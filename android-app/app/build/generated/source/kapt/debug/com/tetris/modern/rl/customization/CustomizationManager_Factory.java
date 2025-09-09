package com.tetris.modern.rl.customization;

import android.content.Context;
import com.tetris.modern.rl.progression.PlayerProgression;
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
public final class CustomizationManager_Factory implements Factory<CustomizationManager> {
  private final Provider<Context> contextProvider;

  private final Provider<PlayerProgression> playerProgressionProvider;

  public CustomizationManager_Factory(Provider<Context> contextProvider,
      Provider<PlayerProgression> playerProgressionProvider) {
    this.contextProvider = contextProvider;
    this.playerProgressionProvider = playerProgressionProvider;
  }

  @Override
  public CustomizationManager get() {
    return newInstance(contextProvider.get(), playerProgressionProvider.get());
  }

  public static CustomizationManager_Factory create(Provider<Context> contextProvider,
      Provider<PlayerProgression> playerProgressionProvider) {
    return new CustomizationManager_Factory(contextProvider, playerProgressionProvider);
  }

  public static CustomizationManager newInstance(Context context,
      PlayerProgression playerProgression) {
    return new CustomizationManager(context, playerProgression);
  }
}
