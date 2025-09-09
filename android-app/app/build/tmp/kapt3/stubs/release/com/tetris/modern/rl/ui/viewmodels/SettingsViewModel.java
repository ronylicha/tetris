package com.tetris.modern.rl.ui.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import androidx.lifecycle.AndroidViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.util.Locale;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\r\u001a\u00020\u000eJ\b\u0010\b\u001a\u00020\u0007H\u0002J\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0007R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/tetris/modern/rl/ui/viewmodels/SettingsViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "currentLanguage", "Landroidx/compose/runtime/MutableState;", "", "getCurrentLanguage", "()Landroidx/compose/runtime/MutableState;", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "applyLanguageOnStartup", "", "setLanguage", "languageCode", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class SettingsViewModel extends androidx.lifecycle.AndroidViewModel {
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.runtime.MutableState<java.lang.String> currentLanguage = null;
    
    @javax.inject.Inject
    public SettingsViewModel(@org.jetbrains.annotations.NotNull
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.runtime.MutableState<java.lang.String> getCurrentLanguage() {
        return null;
    }
    
    private final java.lang.String getCurrentLanguage() {
        return null;
    }
    
    public final void setLanguage(@org.jetbrains.annotations.NotNull
    java.lang.String languageCode) {
    }
    
    public final void applyLanguageOnStartup() {
    }
}