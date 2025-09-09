package com.tetris.modern.rl.data.dao;

import androidx.room.*;
import com.tetris.modern.rl.data.entities.SettingsEntity;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003H\'J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u0015"}, d2 = {"Lcom/tetris/modern/rl/data/dao/SettingsDao;", "", "getSettings", "Lkotlinx/coroutines/flow/Flow;", "Lcom/tetris/modern/rl/data/entities/SettingsEntity;", "getSettingsSync", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMasterVolume", "", "volume", "", "(FLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMusic", "music", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSettings", "settings", "(Lcom/tetris/modern/rl/data/entities/SettingsEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateTheme", "theme", "app_release"})
@androidx.room.Dao
public abstract interface SettingsDao {
    
    @androidx.room.Query(value = "SELECT * FROM settings WHERE id = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.tetris.modern.rl.data.entities.SettingsEntity> getSettings();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateSettings(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.data.entities.SettingsEntity settings, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE settings SET masterVolume = :volume WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateMasterVolume(float volume, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE settings SET selectedTheme = :theme WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateTheme(@org.jetbrains.annotations.NotNull
    java.lang.String theme, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE settings SET selectedMusic = :music WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateMusic(@org.jetbrains.annotations.NotNull
    java.lang.String music, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM settings WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getSettingsSync(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.tetris.modern.rl.data.entities.SettingsEntity> $completion);
}