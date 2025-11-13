package com.tetris.modern.rl.notifications;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class TetrisFirebaseMessagingService_MembersInjector implements MembersInjector<TetrisFirebaseMessagingService> {
  private final Provider<TetrisNotificationManager> notificationManagerProvider;

  public TetrisFirebaseMessagingService_MembersInjector(
      Provider<TetrisNotificationManager> notificationManagerProvider) {
    this.notificationManagerProvider = notificationManagerProvider;
  }

  public static MembersInjector<TetrisFirebaseMessagingService> create(
      Provider<TetrisNotificationManager> notificationManagerProvider) {
    return new TetrisFirebaseMessagingService_MembersInjector(notificationManagerProvider);
  }

  @Override
  public void injectMembers(TetrisFirebaseMessagingService instance) {
    injectNotificationManager(instance, notificationManagerProvider.get());
  }

  @InjectedFieldSignature("com.tetris.modern.rl.notifications.TetrisFirebaseMessagingService.notificationManager")
  public static void injectNotificationManager(TetrisFirebaseMessagingService instance,
      TetrisNotificationManager notificationManager) {
    instance.notificationManager = notificationManager;
  }
}
