package com.tetris.modern.rl

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.content.SharedPreferences
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.tetris.modern.rl.ui.viewmodels.SettingsViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.games.GamesSignInClient
import com.google.android.gms.games.PlayGames
import com.tetris.modern.rl.ui.screens.GameScreen
import com.tetris.modern.rl.ui.screens.HomeScreen
import com.tetris.modern.rl.ui.screens.LeaderboardScreen
import com.tetris.modern.rl.ui.screens.ModeSelectionScreen
import com.tetris.modern.rl.ui.screens.SettingsScreen
import com.tetris.modern.rl.ui.screens.StatsScreen
import com.tetris.modern.rl.ui.screens.TutorialScreen
import com.tetris.modern.rl.ui.theme.ModernTetrisTheme
import com.tetris.modern.rl.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val viewModel: MainViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private lateinit var gamesSignInClient: GamesSignInClient
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen before super.onCreate()
        val splashScreen = installSplashScreen()
        
        // Apply saved language BEFORE super.onCreate()
        val prefs = getSharedPreferences("tetris_settings", Context.MODE_PRIVATE)
        val savedLanguage = prefs.getString("app_language", java.util.Locale.getDefault().language) ?: "en"
        val locale = java.util.Locale(savedLanguage)
        java.util.Locale.setDefault(locale)
        
        val config = resources.configuration
        config.setLocale(locale)
        
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
        
        super.onCreate(savedInstanceState)
        
        // Apply saved language preference again after onCreate
        settingsViewModel.applyLanguageOnStartup()
        
        // Keep splash screen visible while loading
        splashScreen.setKeepOnScreenCondition { false }
        
        // Initialize Google Play Games
        gamesSignInClient = PlayGames.getGamesSignInClient(this)
        
        // Full immersive mode - hide all system UI
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        // Make completely fullscreen
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                it.hide(android.view.WindowInsets.Type.statusBars())
                it.hide(android.view.WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
            )
        }
        
        // Sign in to Google Play Games silently
        signInSilently()
        
        setContent {
            ModernTetrisTheme(
                darkTheme = viewModel.isDarkMode.value,
                dynamicColor = viewModel.useDynamicColors.value
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TetrisNavigation(viewModel)
                }
            }
        }
    }
    
    private fun signInSilently() {
        lifecycleScope.launch {
            gamesSignInClient.isAuthenticated().addOnCompleteListener { task ->
                val isAuthenticated = task.isSuccessful && task.result.isAuthenticated
                if (isAuthenticated) {
                    Timber.d("Already signed in to Google Play Games")
                    viewModel.setGooglePlaySignedIn(true)
                } else {
                    gamesSignInClient.signIn().addOnCompleteListener { signInTask ->
                        val success = signInTask.isSuccessful && signInTask.result.isAuthenticated
                        Timber.d("Google Play Games sign-in: $success")
                        viewModel.setGooglePlaySignedIn(success)
                    }
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
    
    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
}

@Composable
fun TetrisNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    var currentGameMode by remember { mutableStateOf("") }
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tetris_prefs", Context.MODE_PRIVATE)
    val hasSeenTutorial = remember { mutableStateOf(prefs.getBoolean("has_seen_tutorial", false)) }
    
    // Show tutorial on first launch
    val startDestination = if (hasSeenTutorial.value) "home" else "tutorial"
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("tutorial") {
            TutorialScreen(
                onComplete = {
                    prefs.edit().putBoolean("has_seen_tutorial", true).apply()
                    navController.navigate("home") {
                        popUpTo("tutorial") { inclusive = true }
                    }
                },
                onSkip = {
                    prefs.edit().putBoolean("has_seen_tutorial", true).apply()
                    navController.navigate("home") {
                        popUpTo("tutorial") { inclusive = true }
                    }
                }
            )
        }
        
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToModeSelection = {
                    navController.navigate("mode_selection")
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                },
                onNavigateToLeaderboard = {
                    navController.navigate("leaderboard")
                },
                onNavigateToStats = {
                    navController.navigate("stats")
                }
            )
        }
        
        composable("mode_selection") {
            ModeSelectionScreen(
                viewModel = viewModel,
                onModeSelected = { mode ->
                    currentGameMode = mode
                    navController.navigate("game")
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("game") {
            GameScreen(
                viewModel = viewModel,
                gameMode = currentGameMode,
                onBackToMenu = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onBackToModeSelection = {
                    navController.navigate("mode_selection") {
                        popUpTo("home")
                    }
                }
            )
        }
        
        composable("settings") {
            SettingsScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        
        composable("leaderboard") {
            LeaderboardScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        
        composable("stats") {
            StatsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}