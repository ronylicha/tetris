package com.tetris.modern.rl.playgames

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.games.PlayGames
import com.google.android.gms.games.LeaderboardsClient
import com.google.android.gms.games.AchievementsClient
import com.google.android.gms.games.PlayersClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class PlayGamesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    companion object {
        // Leaderboard IDs (these need to be configured in Play Console)
        const val LEADERBOARD_CLASSIC = "CgkI_YOUR_APP_ID_HERE_EAIQAQ"
        const val LEADERBOARD_SPRINT = "CgkI_YOUR_APP_ID_HERE_EAIQAQ"
        const val LEADERBOARD_MARATHON = "CgkI_YOUR_APP_ID_HERE_EAIQAQ"
        const val LEADERBOARD_ZEN = "CgkI_YOUR_APP_ID_HERE_EAIQAQ"
        const val LEADERBOARD_PUZZLE = "CgkI_YOUR_APP_ID_HERE_EAIQAQ"
        const val LEADERBOARD_BATTLE = "CgkI_YOUR_APP_ID_HERE_EAIQAQ"
        const val LEADERBOARD_GLOBAL_XP = "CgkI_YOUR_APP_ID_HERE_EAIQAQ"
        
        // Achievement IDs (these need to be configured in Play Console)
        const val ACHIEVEMENT_FIRST_GAME = "CgkI_YOUR_APP_ID_HERE_EAIQAg"
        const val ACHIEVEMENT_FIRST_TETRIS = "CgkI_YOUR_APP_ID_HERE_EAIQAw"
        const val ACHIEVEMENT_SCORE_10K = "CgkI_YOUR_APP_ID_HERE_EAIQBA"
        const val ACHIEVEMENT_SCORE_50K = "CgkI_YOUR_APP_ID_HERE_EAIQBQ"
        const val ACHIEVEMENT_SCORE_100K = "CgkI_YOUR_APP_ID_HERE_EAIQBg"
        const val ACHIEVEMENT_SCORE_500K = "CgkI_YOUR_APP_ID_HERE_EAIQBw"
        const val ACHIEVEMENT_SCORE_1M = "CgkI_YOUR_APP_ID_HERE_EAIQCA"
        const val ACHIEVEMENT_T_SPIN_MASTER = "CgkI_YOUR_APP_ID_HERE_EAIQCQ"
        const val ACHIEVEMENT_COMBO_10 = "CgkI_YOUR_APP_ID_HERE_EAIQCg"
        const val ACHIEVEMENT_PERFECT_CLEAR = "CgkI_YOUR_APP_ID_HERE_EAIQCw"
        const val ACHIEVEMENT_SPEED_DEMON = "CgkI_YOUR_APP_ID_HERE_EAIQDA"
        const val ACHIEVEMENT_MARATHON_COMPLETE = "CgkI_YOUR_APP_ID_HERE_EAIQDQ"
        const val ACHIEVEMENT_PUZZLE_MASTER = "CgkI_YOUR_APP_ID_HERE_EAIQDg"
        const val ACHIEVEMENT_DAILY_STREAK_7 = "CgkI_YOUR_APP_ID_HERE_EAIQDw"
        const val ACHIEVEMENT_DAILY_STREAK_30 = "CgkI_YOUR_APP_ID_HERE_EAIQEA"
        
        const val RC_SIGN_IN = 9001
        const val RC_LEADERBOARD_UI = 9002
        const val RC_ACHIEVEMENT_UI = 9003
    }
    
    data class PlayGamesState(
        val isSignedIn: Boolean = false,
        val playerName: String? = null,
        val playerId: String? = null,
        val playerAvatarUrl: String? = null,
        val isConnecting: Boolean = false,
        val error: String? = null
    )
    
    private val _state = MutableStateFlow(PlayGamesState())
    val state: StateFlow<PlayGamesState> = _state.asStateFlow()
    
    private var leaderboardsClient: LeaderboardsClient? = null
    private var achievementsClient: AchievementsClient? = null
    private var playersClient: PlayersClient? = null
    
    private var currentAccount: GoogleSignInAccount? = null
    
    init {
        initializePlayGames()
    }
    
    private fun initializePlayGames() {
        // Check if already signed in
        checkSignInStatus()
    }
    
    private fun checkSignInStatus() {
        _state.value = _state.value.copy(isConnecting = true)
        
        // Check for existing Google account
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            handleSignInSuccess(account)
        } else {
            _state.value = PlayGamesState(
                isSignedIn = false,
                isConnecting = false
            )
        }
    }
    
    suspend fun signIn(activity: Activity): Result<Boolean> {
        return suspendCancellableCoroutine { continuation ->
            _state.value = _state.value.copy(isConnecting = true, error = null)
            
            try {
                // Use Google Sign-In
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                    .requestEmail()
                    .build()
                    
                val googleSignInClient = GoogleSignIn.getClient(activity, gso)
                val signInIntent = googleSignInClient.signInIntent
                activity.startActivityForResult(signInIntent, RC_SIGN_IN)
                
                // Result will be handled in onActivityResult
                continuation.resume(Result.success(true))
            } catch (e: Exception) {
                Timber.e(e, "Play Games sign-in failed")
                _state.value = _state.value.copy(
                    isSignedIn = false,
                    isConnecting = false,
                    error = e.message
                )
                continuation.resume(Result.failure(e))
            }
        }
    }
    
    fun handleSignInResult(account: GoogleSignInAccount?) {
        if (account != null) {
            handleSignInSuccess(account)
        } else {
            _state.value = PlayGamesState(
                isSignedIn = false,
                isConnecting = false,
                error = "Sign in failed"
            )
        }
    }
    
    fun initializeClients(activity: Activity) {
        if (currentAccount == null) {
            val account = GoogleSignIn.getLastSignedInAccount(context)
            if (account != null) {
                currentAccount = account
            } else {
                return
            }
        }
        
        try {
            // Initialize clients with Activity context
            leaderboardsClient = PlayGames.getLeaderboardsClient(activity)
            achievementsClient = PlayGames.getAchievementsClient(activity)  
            playersClient = PlayGames.getPlayersClient(activity)
            
            // Get player info
            playersClient?.currentPlayer?.addOnSuccessListener { player ->
                _state.value = PlayGamesState(
                    isSignedIn = true,
                    playerName = player.displayName,
                    playerId = player.playerId,
                    playerAvatarUrl = player.iconImageUrl,
                    isConnecting = false
                )
                
                Timber.d("Play Games signed in: ${player.displayName}")
            }?.addOnFailureListener {
                // Even if we can't get player info, we're still signed in
                currentAccount?.let { account ->
                    _state.value = PlayGamesState(
                        isSignedIn = true,
                        playerName = account.displayName,
                        playerId = account.id,
                        playerAvatarUrl = account.photoUrl?.toString(),
                        isConnecting = false
                    )
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to initialize Play Games clients")
        }
    }
    
    private fun handleSignInSuccess(account: GoogleSignInAccount) {
        currentAccount = account
        
        // Set initial state
        _state.value = PlayGamesState(
            isSignedIn = true,
            playerName = account.displayName,
            playerId = account.id,
            playerAvatarUrl = account.photoUrl?.toString(),
            isConnecting = false
        )
        
        Timber.d("Play Games account ready: ${account.displayName}")
    }
    
    fun signOut() {
        // Sign out from Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        googleSignInClient.signOut().addOnCompleteListener {
            _state.value = PlayGamesState(isSignedIn = false)
            currentAccount = null
            leaderboardsClient = null
            achievementsClient = null
            playersClient = null
            
            Timber.d("Play Games signed out")
        }
    }
    
    // Score submission methods
    suspend fun submitScore(gameMode: String, score: Long): Result<Boolean> {
        if (!_state.value.isSignedIn) {
            return Result.failure(Exception("Not signed in to Play Games"))
        }
        
        val leaderboardId = getLeaderboardId(gameMode) ?: return Result.failure(
            Exception("Unknown game mode: $gameMode")
        )
        
        return suspendCancellableCoroutine { continuation ->
            try {
                leaderboardsClient?.submitScoreImmediate(leaderboardId, score)
                    ?.addOnSuccessListener {
                        Timber.d("Score submitted to Play Games: $score in $gameMode")
                        
                        // Check for score-based achievements
                        checkScoreAchievements(score)
                        
                        continuation.resume(Result.success(true))
                    }
                    ?.addOnFailureListener { exception ->
                        Timber.e(exception, "Failed to submit score to Play Games")
                        continuation.resume(Result.failure(exception))
                    }
            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }
        }
    }
    
    fun showLeaderboard(activity: Activity, gameMode: String? = null) {
        if (!_state.value.isSignedIn) {
            Timber.w("Cannot show leaderboard: not signed in")
            return
        }
        
        if (gameMode != null) {
            val leaderboardId = getLeaderboardId(gameMode)
            if (leaderboardId != null) {
                leaderboardsClient?.getLeaderboardIntent(leaderboardId)
                    ?.addOnSuccessListener { intent ->
                        activity.startActivityForResult(intent, RC_LEADERBOARD_UI)
                    }
            }
        } else {
            // Show all leaderboards
            leaderboardsClient?.allLeaderboardsIntent
                ?.addOnSuccessListener { intent ->
                    activity.startActivityForResult(intent, RC_LEADERBOARD_UI)
                }
        }
    }
    
    fun showAchievements(activity: Activity) {
        if (!_state.value.isSignedIn) {
            Timber.w("Cannot show achievements: not signed in")
            return
        }
        
        achievementsClient?.achievementsIntent
            ?.addOnSuccessListener { intent ->
                activity.startActivityForResult(intent, RC_ACHIEVEMENT_UI)
            }
    }
    
    // Achievement unlocking methods
    fun unlockAchievement(achievementId: String) {
        if (!_state.value.isSignedIn) return
        
        achievementsClient?.unlock(achievementId)
        Timber.d("Achievement unlocked: $achievementId")
    }
    
    fun incrementAchievement(achievementId: String, increment: Int) {
        if (!_state.value.isSignedIn) return
        
        achievementsClient?.increment(achievementId, increment)
        Timber.d("Achievement incremented: $achievementId by $increment")
    }
    
    fun setAchievementSteps(achievementId: String, steps: Int) {
        if (!_state.value.isSignedIn) return
        
        achievementsClient?.setSteps(achievementId, steps)
        Timber.d("Achievement progress set: $achievementId to $steps steps")
    }
    
    // Game-specific achievement checks
    fun checkScoreAchievements(score: Long) {
        when {
            score >= 1_000_000 -> unlockAchievement(ACHIEVEMENT_SCORE_1M)
            score >= 500_000 -> unlockAchievement(ACHIEVEMENT_SCORE_500K)
            score >= 100_000 -> unlockAchievement(ACHIEVEMENT_SCORE_100K)
            score >= 50_000 -> unlockAchievement(ACHIEVEMENT_SCORE_50K)
            score >= 10_000 -> unlockAchievement(ACHIEVEMENT_SCORE_10K)
        }
    }
    
    fun checkGameplayAchievements(
        tetrisCount: Int = 0,
        tSpinCount: Int = 0,
        maxCombo: Int = 0,
        perfectClears: Int = 0
    ) {
        if (tetrisCount > 0) {
            unlockAchievement(ACHIEVEMENT_FIRST_TETRIS)
        }
        
        if (tSpinCount >= 10) {
            unlockAchievement(ACHIEVEMENT_T_SPIN_MASTER)
        }
        
        if (maxCombo >= 10) {
            unlockAchievement(ACHIEVEMENT_COMBO_10)
        }
        
        if (perfectClears > 0) {
            unlockAchievement(ACHIEVEMENT_PERFECT_CLEAR)
        }
    }
    
    fun checkModeAchievements(gameMode: String, completed: Boolean = false) {
        when (gameMode) {
            "marathon" -> if (completed) unlockAchievement(ACHIEVEMENT_MARATHON_COMPLETE)
            "sprint" -> unlockAchievement(ACHIEVEMENT_SPEED_DEMON)
            "puzzle" -> if (completed) unlockAchievement(ACHIEVEMENT_PUZZLE_MASTER)
        }
    }
    
    fun checkDailyStreakAchievements(streakDays: Int) {
        when {
            streakDays >= 30 -> unlockAchievement(ACHIEVEMENT_DAILY_STREAK_30)
            streakDays >= 7 -> unlockAchievement(ACHIEVEMENT_DAILY_STREAK_7)
        }
    }
    
    // Helper methods
    private fun getLeaderboardId(gameMode: String): String? {
        return when (gameMode.lowercase()) {
            "classic" -> LEADERBOARD_CLASSIC
            "sprint" -> LEADERBOARD_SPRINT
            "marathon" -> LEADERBOARD_MARATHON
            "zen" -> LEADERBOARD_ZEN
            "puzzle" -> LEADERBOARD_PUZZLE
            "battle" -> LEADERBOARD_BATTLE
            else -> null
        }
    }
}

data class PlayGamesScore(
    val rank: Long,
    val displayRank: String,
    val displayScore: String,
    val rawScore: Long,
    val playerDisplayName: String,
    val playerIconUrl: String?
)