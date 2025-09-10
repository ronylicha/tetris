package com.tetris.modern.rl.ui.viewmodels;

import com.tetris.modern.rl.achievements.AchievementSystem;
import com.tetris.modern.rl.audio.AudioManager;
import com.tetris.modern.rl.data.repository.ProgressionRepository;
import com.tetris.modern.rl.data.repository.ScoreRepository;
import com.tetris.modern.rl.data.repository.SettingsRepository;
import com.tetris.modern.rl.data.repository.StatisticsRepository;
import com.tetris.modern.rl.game.TetrisEngine;
import com.tetris.modern.rl.progression.PlayerProgression;
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
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<TetrisEngine> tetrisEngineProvider;

  private final Provider<AudioManager> audioManagerProvider;

  private final Provider<ScoreRepository> scoreRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<StatisticsRepository> statisticsRepositoryProvider;

  private final Provider<ProgressionRepository> progressionRepositoryProvider;

  private final Provider<PlayerProgression> playerProgressionProvider;

  private final Provider<AchievementSystem> achievementSystemProvider;

  public MainViewModel_Factory(Provider<TetrisEngine> tetrisEngineProvider,
      Provider<AudioManager> audioManagerProvider,
      Provider<ScoreRepository> scoreRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<StatisticsRepository> statisticsRepositoryProvider,
      Provider<ProgressionRepository> progressionRepositoryProvider,
      Provider<PlayerProgression> playerProgressionProvider,
      Provider<AchievementSystem> achievementSystemProvider) {
    this.tetrisEngineProvider = tetrisEngineProvider;
    this.audioManagerProvider = audioManagerProvider;
    this.scoreRepositoryProvider = scoreRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.statisticsRepositoryProvider = statisticsRepositoryProvider;
    this.progressionRepositoryProvider = progressionRepositoryProvider;
    this.playerProgressionProvider = playerProgressionProvider;
    this.achievementSystemProvider = achievementSystemProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(tetrisEngineProvider.get(), audioManagerProvider.get(), scoreRepositoryProvider.get(), settingsRepositoryProvider.get(), statisticsRepositoryProvider.get(), progressionRepositoryProvider.get(), playerProgressionProvider.get(), achievementSystemProvider.get());
  }

  public static MainViewModel_Factory create(Provider<TetrisEngine> tetrisEngineProvider,
      Provider<AudioManager> audioManagerProvider,
      Provider<ScoreRepository> scoreRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<StatisticsRepository> statisticsRepositoryProvider,
      Provider<ProgressionRepository> progressionRepositoryProvider,
      Provider<PlayerProgression> playerProgressionProvider,
      Provider<AchievementSystem> achievementSystemProvider) {
    return new MainViewModel_Factory(tetrisEngineProvider, audioManagerProvider, scoreRepositoryProvider, settingsRepositoryProvider, statisticsRepositoryProvider, progressionRepositoryProvider, playerProgressionProvider, achievementSystemProvider);
  }

  public static MainViewModel newInstance(TetrisEngine tetrisEngine, AudioManager audioManager,
      ScoreRepository scoreRepository, SettingsRepository settingsRepository,
      StatisticsRepository statisticsRepository, ProgressionRepository progressionRepository,
      PlayerProgression playerProgression, AchievementSystem achievementSystem) {
    return new MainViewModel(tetrisEngine, audioManager, scoreRepository, settingsRepository, statisticsRepository, progressionRepository, playerProgression, achievementSystem);
  }
}
