package com.tetris.modern.rl.playgames;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.PlayersClient;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import timber.log.Timber;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u0000 >2\u00020\u0001:\u0002>?B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J.\u0010\u0018\u001a\u00020\u00152\b\b\u0002\u0010\u0019\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u00172\b\b\u0002\u0010\u001b\u001a\u00020\u00172\b\b\u0002\u0010\u001c\u001a\u00020\u0017J\u0018\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020\u00152\u0006\u0010#\u001a\u00020$J\b\u0010%\u001a\u00020\u0015H\u0002J\u0012\u0010&\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010\'\u001a\u00020\u00152\b\u0010(\u001a\u0004\u0018\u00010\u000bJ\u0010\u0010)\u001a\u00020\u00152\u0006\u0010(\u001a\u00020\u000bH\u0002J\u0016\u0010*\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\u001f2\u0006\u0010,\u001a\u00020\u0017J\u000e\u0010-\u001a\u00020\u00152\u0006\u0010.\u001a\u00020/J\b\u00100\u001a\u00020\u0015H\u0002J\u0016\u00101\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\u001f2\u0006\u00102\u001a\u00020\u0017J\u000e\u00103\u001a\u00020\u00152\u0006\u0010.\u001a\u00020/J\u001a\u00104\u001a\u00020\u00152\u0006\u0010.\u001a\u00020/2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001fJ$\u00105\u001a\b\u0012\u0004\u0012\u00020!062\u0006\u0010.\u001a\u00020/H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b7\u00108J\u0006\u00109\u001a\u00020\u0015J,\u0010:\u001a\b\u0012\u0004\u0012\u00020!062\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020$H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b;\u0010<J\u000e\u0010=\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\u001fR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006@"}, d2 = {"Lcom/tetris/modern/rl/playgames/PlayGamesManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/tetris/modern/rl/playgames/PlayGamesManager$PlayGamesState;", "achievementsClient", "Lcom/google/android/gms/games/AchievementsClient;", "currentAccount", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;", "leaderboardsClient", "Lcom/google/android/gms/games/LeaderboardsClient;", "playersClient", "Lcom/google/android/gms/games/PlayersClient;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "checkDailyStreakAchievements", "", "streakDays", "", "checkGameplayAchievements", "tetrisCount", "tSpinCount", "maxCombo", "perfectClears", "checkModeAchievements", "gameMode", "", "completed", "", "checkScoreAchievements", "score", "", "checkSignInStatus", "getLeaderboardId", "handleSignInResult", "account", "handleSignInSuccess", "incrementAchievement", "achievementId", "increment", "initializeClients", "activity", "Landroid/app/Activity;", "initializePlayGames", "setAchievementSteps", "steps", "showAchievements", "showLeaderboard", "signIn", "Lkotlin/Result;", "signIn-gIAlu-s", "(Landroid/app/Activity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signOut", "submitScore", "submitScore-0E7RQCE", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unlockAchievement", "Companion", "PlayGamesState", "app_debug"})
public final class PlayGamesManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LEADERBOARD_CLASSIC = "CgkI_YOUR_APP_ID_HERE_EAIQAQ";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LEADERBOARD_SPRINT = "CgkI_YOUR_APP_ID_HERE_EAIQAQ";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LEADERBOARD_MARATHON = "CgkI_YOUR_APP_ID_HERE_EAIQAQ";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LEADERBOARD_ZEN = "CgkI_YOUR_APP_ID_HERE_EAIQAQ";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LEADERBOARD_PUZZLE = "CgkI_YOUR_APP_ID_HERE_EAIQAQ";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LEADERBOARD_BATTLE = "CgkI_YOUR_APP_ID_HERE_EAIQAQ";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LEADERBOARD_GLOBAL_XP = "CgkI_YOUR_APP_ID_HERE_EAIQAQ";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_FIRST_GAME = "CgkI_YOUR_APP_ID_HERE_EAIQAg";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_FIRST_TETRIS = "CgkI_YOUR_APP_ID_HERE_EAIQAw";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_SCORE_10K = "CgkI_YOUR_APP_ID_HERE_EAIQBA";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_SCORE_50K = "CgkI_YOUR_APP_ID_HERE_EAIQBQ";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_SCORE_100K = "CgkI_YOUR_APP_ID_HERE_EAIQBg";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_SCORE_500K = "CgkI_YOUR_APP_ID_HERE_EAIQBw";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_SCORE_1M = "CgkI_YOUR_APP_ID_HERE_EAIQCA";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_T_SPIN_MASTER = "CgkI_YOUR_APP_ID_HERE_EAIQCQ";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_COMBO_10 = "CgkI_YOUR_APP_ID_HERE_EAIQCg";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_PERFECT_CLEAR = "CgkI_YOUR_APP_ID_HERE_EAIQCw";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_SPEED_DEMON = "CgkI_YOUR_APP_ID_HERE_EAIQDA";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_MARATHON_COMPLETE = "CgkI_YOUR_APP_ID_HERE_EAIQDQ";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_PUZZLE_MASTER = "CgkI_YOUR_APP_ID_HERE_EAIQDg";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_DAILY_STREAK_7 = "CgkI_YOUR_APP_ID_HERE_EAIQDw";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACHIEVEMENT_DAILY_STREAK_30 = "CgkI_YOUR_APP_ID_HERE_EAIQEA";
    public static final int RC_SIGN_IN = 9001;
    public static final int RC_LEADERBOARD_UI = 9002;
    public static final int RC_ACHIEVEMENT_UI = 9003;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.tetris.modern.rl.playgames.PlayGamesManager.PlayGamesState> _state = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.playgames.PlayGamesManager.PlayGamesState> state = null;
    @org.jetbrains.annotations.Nullable
    private com.google.android.gms.games.LeaderboardsClient leaderboardsClient;
    @org.jetbrains.annotations.Nullable
    private com.google.android.gms.games.AchievementsClient achievementsClient;
    @org.jetbrains.annotations.Nullable
    private com.google.android.gms.games.PlayersClient playersClient;
    @org.jetbrains.annotations.Nullable
    private com.google.android.gms.auth.api.signin.GoogleSignInAccount currentAccount;
    @org.jetbrains.annotations.NotNull
    public static final com.tetris.modern.rl.playgames.PlayGamesManager.Companion Companion = null;
    
    @javax.inject.Inject
    public PlayGamesManager(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.tetris.modern.rl.playgames.PlayGamesManager.PlayGamesState> getState() {
        return null;
    }
    
    private final void initializePlayGames() {
    }
    
    private final void checkSignInStatus() {
    }
    
    public final void handleSignInResult(@org.jetbrains.annotations.Nullable
    com.google.android.gms.auth.api.signin.GoogleSignInAccount account) {
    }
    
    public final void initializeClients(@org.jetbrains.annotations.NotNull
    android.app.Activity activity) {
    }
    
    private final void handleSignInSuccess(com.google.android.gms.auth.api.signin.GoogleSignInAccount account) {
    }
    
    public final void signOut() {
    }
    
    public final void showLeaderboard(@org.jetbrains.annotations.NotNull
    android.app.Activity activity, @org.jetbrains.annotations.Nullable
    java.lang.String gameMode) {
    }
    
    public final void showAchievements(@org.jetbrains.annotations.NotNull
    android.app.Activity activity) {
    }
    
    public final void unlockAchievement(@org.jetbrains.annotations.NotNull
    java.lang.String achievementId) {
    }
    
    public final void incrementAchievement(@org.jetbrains.annotations.NotNull
    java.lang.String achievementId, int increment) {
    }
    
    public final void setAchievementSteps(@org.jetbrains.annotations.NotNull
    java.lang.String achievementId, int steps) {
    }
    
    public final void checkScoreAchievements(long score) {
    }
    
    public final void checkGameplayAchievements(int tetrisCount, int tSpinCount, int maxCombo, int perfectClears) {
    }
    
    public final void checkModeAchievements(@org.jetbrains.annotations.NotNull
    java.lang.String gameMode, boolean completed) {
    }
    
    public final void checkDailyStreakAchievements(int streakDays) {
    }
    
    private final java.lang.String getLeaderboardId(java.lang.String gameMode) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/tetris/modern/rl/playgames/PlayGamesManager$Companion;", "", "()V", "ACHIEVEMENT_COMBO_10", "", "ACHIEVEMENT_DAILY_STREAK_30", "ACHIEVEMENT_DAILY_STREAK_7", "ACHIEVEMENT_FIRST_GAME", "ACHIEVEMENT_FIRST_TETRIS", "ACHIEVEMENT_MARATHON_COMPLETE", "ACHIEVEMENT_PERFECT_CLEAR", "ACHIEVEMENT_PUZZLE_MASTER", "ACHIEVEMENT_SCORE_100K", "ACHIEVEMENT_SCORE_10K", "ACHIEVEMENT_SCORE_1M", "ACHIEVEMENT_SCORE_500K", "ACHIEVEMENT_SCORE_50K", "ACHIEVEMENT_SPEED_DEMON", "ACHIEVEMENT_T_SPIN_MASTER", "LEADERBOARD_BATTLE", "LEADERBOARD_CLASSIC", "LEADERBOARD_GLOBAL_XP", "LEADERBOARD_MARATHON", "LEADERBOARD_PUZZLE", "LEADERBOARD_SPRINT", "LEADERBOARD_ZEN", "RC_ACHIEVEMENT_UI", "", "RC_LEADERBOARD_UI", "RC_SIGN_IN", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BI\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003JM\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\rR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\f\u00a8\u0006\u001d"}, d2 = {"Lcom/tetris/modern/rl/playgames/PlayGamesManager$PlayGamesState;", "", "isSignedIn", "", "playerName", "", "playerId", "playerAvatarUrl", "isConnecting", "error", "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;)V", "getError", "()Ljava/lang/String;", "()Z", "getPlayerAvatarUrl", "getPlayerId", "getPlayerName", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class PlayGamesState {
        private final boolean isSignedIn = false;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String playerName = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String playerId = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String playerAvatarUrl = null;
        private final boolean isConnecting = false;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String error = null;
        
        public PlayGamesState(boolean isSignedIn, @org.jetbrains.annotations.Nullable
        java.lang.String playerName, @org.jetbrains.annotations.Nullable
        java.lang.String playerId, @org.jetbrains.annotations.Nullable
        java.lang.String playerAvatarUrl, boolean isConnecting, @org.jetbrains.annotations.Nullable
        java.lang.String error) {
            super();
        }
        
        public final boolean isSignedIn() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getPlayerName() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getPlayerId() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getPlayerAvatarUrl() {
            return null;
        }
        
        public final boolean isConnecting() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getError() {
            return null;
        }
        
        public PlayGamesState() {
            super();
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component4() {
            return null;
        }
        
        public final boolean component5() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.tetris.modern.rl.playgames.PlayGamesManager.PlayGamesState copy(boolean isSignedIn, @org.jetbrains.annotations.Nullable
        java.lang.String playerName, @org.jetbrains.annotations.Nullable
        java.lang.String playerId, @org.jetbrains.annotations.Nullable
        java.lang.String playerAvatarUrl, boolean isConnecting, @org.jetbrains.annotations.Nullable
        java.lang.String error) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}