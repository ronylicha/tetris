package com.tetris.modern.rl.notifications;

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
public final class TetrisNotificationManager_Factory implements Factory<TetrisNotificationManager> {
  private final Provider<Context> contextProvider;

  public TetrisNotificationManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public TetrisNotificationManager get() {
    return newInstance(contextProvider.get());
  }

  public static TetrisNotificationManager_Factory create(Provider<Context> contextProvider) {
    return new TetrisNotificationManager_Factory(contextProvider);
  }

  public static TetrisNotificationManager newInstance(Context context) {
    return new TetrisNotificationManager(context);
  }
}
