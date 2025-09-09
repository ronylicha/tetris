package com.tetris.modern.rl.data.repository;

import com.tetris.modern.rl.data.dao.SettingsDao;
import com.tetris.modern.rl.data.entities.SettingsEntity;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006J\u000e\u0010\b\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/tetris/modern/rl/data/repository/SettingsRepository;", "", "settingsDao", "Lcom/tetris/modern/rl/data/dao/SettingsDao;", "(Lcom/tetris/modern/rl/data/dao/SettingsDao;)V", "getSettings", "Lkotlinx/coroutines/flow/Flow;", "Lcom/tetris/modern/rl/data/entities/SettingsEntity;", "initializeDefaultSettings", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateControlType", "type", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateFirstLaunch", "isFirstLaunch", "", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMasterVolume", "volume", "", "(FLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMusic", "music", "updateSettings", "settings", "(Lcom/tetris/modern/rl/data/entities/SettingsEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateTheme", "theme", "app_debug"})
public final class SettingsRepository {
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.data.dao.SettingsDao settingsDao = null;
    
    @javax.inject.Inject
    public SettingsRepository(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.dao.SettingsDao settingsDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.SettingsEntity> getSettings() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateSettings(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.SettingsEntity settings, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateMasterVolume(float volume, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateTheme(@org.jetbrains.annotations.NotNull
    java.lang.String theme, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateMusic(@org.jetbrains.annotations.NotNull
    java.lang.String music, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateControlType(@org.jetbrains.annotations.NotNull
    java.lang.String type, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateFirstLaunch(boolean isFirstLaunch, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object initializeDefaultSettings(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}