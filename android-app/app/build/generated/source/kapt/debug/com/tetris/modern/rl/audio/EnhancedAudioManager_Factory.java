package com.tetris.modern.rl.audio;

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
public final class EnhancedAudioManager_Factory implements Factory<EnhancedAudioManager> {
  private final Provider<Context> contextProvider;

  public EnhancedAudioManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public EnhancedAudioManager get() {
    return newInstance(contextProvider.get());
  }

  public static EnhancedAudioManager_Factory create(Provider<Context> contextProvider) {
    return new EnhancedAudioManager_Factory(contextProvider);
  }

  public static EnhancedAudioManager newInstance(Context context) {
    return new EnhancedAudioManager(context);
  }
}
