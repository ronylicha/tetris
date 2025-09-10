package com.tetris.modern.rl.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Neon color palette
object NeonColors {
    val Cyan = Color(0xFF00FFFF)
    val Magenta = Color(0xFFFF00FF)
    val Yellow = Color(0xFFFFFF00)
    val Green = Color(0xFF00FF00)
    val Red = Color(0xFFFF0000)
    val Blue = Color(0xFF0000FF)
    val Orange = Color(0xFFFF8800)
    val Purple = Color(0xFF9C27B0)
    val Gold = Color(0xFFFFD700)
    
    val pieceColors = listOf(Cyan, Yellow, Magenta, Green, Red, Blue, Orange)
}

private val DarkColorScheme = darkColorScheme(
    primary = NeonColors.Cyan,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF004A4A),
    onPrimaryContainer = NeonColors.Cyan,
    
    secondary = NeonColors.Magenta,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF4A004A),
    onSecondaryContainer = NeonColors.Magenta,
    
    tertiary = NeonColors.Yellow,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF4A4A00),
    onTertiaryContainer = NeonColors.Yellow,
    
    background = Color(0xFF0A0A0A),
    onBackground = Color.White,
    
    surface = Color(0xFF1A1A2E),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2A2A3E),
    onSurfaceVariant = Color(0xFFB0B0B0),
    
    error = Color(0xFFFF0000),
    onError = Color.White,
    errorContainer = Color(0xFF4A0000),
    onErrorContainer = Color(0xFFFF8888),
    
    outline = Color(0xFF606060),
    outlineVariant = Color(0xFF404040),
    
    scrim = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006666),
    onPrimary = Color.White,
    primaryContainer = NeonColors.Cyan.copy(alpha = 0.2f),
    onPrimaryContainer = Color(0xFF003333),
    
    secondary = Color(0xFF660066),
    onSecondary = Color.White,
    secondaryContainer = NeonColors.Magenta.copy(alpha = 0.2f),
    onSecondaryContainer = Color(0xFF330033),
    
    tertiary = Color(0xFF666600),
    onTertiary = Color.White,
    tertiaryContainer = NeonColors.Yellow.copy(alpha = 0.2f),
    onTertiaryContainer = Color(0xFF333300),
    
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF1A1A1A),
    
    surface = Color.White,
    onSurface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF404040),
    
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    
    outline = Color(0xFF767676),
    outlineVariant = Color(0xFFC6C6C6),
    
    scrim = Color.Black
)

@Composable
fun ModernTetrisTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}