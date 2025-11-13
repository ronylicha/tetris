package com.tetris.modern.rl.viewmodel;

import com.tetris.modern.rl.auth.AuthManager;
import com.tetris.modern.rl.data.repository.ScoreRepository;
import com.tetris.modern.rl.sync.SyncManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class EnhancedLeaderboardViewModel_Factory implements Factory<EnhancedLeaderboardViewModel> {
  private final Provider<ScoreRepository> scoreRepositoryProvider;

  private final Provider<AuthManager> authManagerProvider;

  private final Provider<SyncManager> syncManagerProvider;

  public EnhancedLeaderboardViewModel_Factory(Provider<ScoreRepository> scoreRepositoryProvider,
      Provider<AuthManager> authManagerProvider, Provider<SyncManager> syncManagerProvider) {
    this.scoreRepositoryProvider = scoreRepositoryProvider;
    this.authManagerProvider = authManagerProvider;
    this.syncManagerProvider = syncManagerProvider;
  }

  @Override
  public EnhancedLeaderboardViewModel get() {
    return newInstance(scoreRepositoryProvider.get(), authManagerProvider.get(), syncManagerProvider.get());
  }

  public static EnhancedLeaderboardViewModel_Factory create(
      Provider<ScoreRepository> scoreRepositoryProvider, Provider<AuthManager> authManagerProvider,
      Provider<SyncManager> syncManagerProvider) {
    return new EnhancedLeaderboardViewModel_Factory(scoreRepositoryProvider, authManagerProvider, syncManagerProvider);
  }

  public static EnhancedLeaderboardViewModel newInstance(ScoreRepository scoreRepository,
      AuthManager authManager, SyncManager syncManager) {
    return new EnhancedLeaderboardViewModel(scoreRepository, authManager, syncManager);
  }
}
