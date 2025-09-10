package com.tetris.modern.rl;

import androidx.hilt.work.HiltWorkerFactory;
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
public final class TetrisApplication_MembersInjector implements MembersInjector<TetrisApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public TetrisApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<TetrisApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new TetrisApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(TetrisApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.tetris.modern.rl.TetrisApplication.workerFactory")
  public static void injectWorkerFactory(TetrisApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
