package com.tetris.modern.rl.di;

import com.tetris.modern.rl.data.dao.SettingsDao;
import com.tetris.modern.rl.data.database.TetrisDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideSettingsDaoFactory implements Factory<SettingsDao> {
  private final Provider<TetrisDatabase> databaseProvider;

  public DatabaseModule_ProvideSettingsDaoFactory(Provider<TetrisDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SettingsDao get() {
    return provideSettingsDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideSettingsDaoFactory create(
      Provider<TetrisDatabase> databaseProvider) {
    return new DatabaseModule_ProvideSettingsDaoFactory(databaseProvider);
  }

  public static SettingsDao provideSettingsDao(TetrisDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSettingsDao(database));
  }
}
