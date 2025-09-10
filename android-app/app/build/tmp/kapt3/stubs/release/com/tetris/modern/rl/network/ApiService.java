package com.tetris.modern.rl.network;

import com.tetris.modern.rl.network.models.*;
import retrofit2.Response;
import retrofit2.http.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00e4\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J \u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\n\b\u0003\u0010\n\u001a\u0004\u0018\u00010\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J4\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\n\b\u0003\u0010\u000f\u001a\u0004\u0018\u00010\u00062\b\b\u0003\u0010\u0010\u001a\u00020\u00112\b\b\u0003\u0010\u0012\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0013J*\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\n\b\u0003\u0010\u000f\u001a\u0004\u0018\u00010\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00032\b\b\u0001\u0010\u001d\u001a\u00020\u001eH\u00a7@\u00a2\u0006\u0002\u0010\u001fJ\u001e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001c0\u00032\b\b\u0001\u0010!\u001a\u00020\"H\u00a7@\u00a2\u0006\u0002\u0010#J\u001e\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u00032\b\b\u0001\u0010&\u001a\u00020\'H\u00a7@\u00a2\u0006\u0002\u0010(J\u001e\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u00032\b\b\u0001\u0010+\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00032\b\b\u0001\u0010-\u001a\u00020.H\u00a7@\u00a2\u0006\u0002\u0010/J\u001e\u00100\u001a\b\u0012\u0004\u0012\u0002010\u00032\b\b\u0001\u00102\u001a\u000203H\u00a7@\u00a2\u0006\u0002\u00104J\u001e\u00105\u001a\b\u0012\u0004\u0012\u0002060\u00032\b\b\u0001\u00107\u001a\u000208H\u00a7@\u00a2\u0006\u0002\u00109J\u001e\u0010:\u001a\b\u0012\u0004\u0012\u00020;0\u00032\b\b\u0001\u0010<\u001a\u00020=H\u00a7@\u00a2\u0006\u0002\u0010>J(\u0010?\u001a\b\u0012\u0004\u0012\u00020@0\u00032\b\b\u0001\u0010+\u001a\u00020\u00062\b\b\u0001\u0010A\u001a\u00020BH\u00a7@\u00a2\u0006\u0002\u0010CJ\u001e\u0010D\u001a\b\u0012\u0004\u0012\u00020E0\u00032\b\b\u0001\u0010F\u001a\u00020GH\u00a7@\u00a2\u0006\u0002\u0010HJ\u001e\u0010I\u001a\b\u0012\u0004\u0012\u00020J0\u00032\b\b\u0001\u0010K\u001a\u00020LH\u00a7@\u00a2\u0006\u0002\u0010MJ\u001e\u0010N\u001a\b\u0012\u0004\u0012\u00020O0\u00032\b\b\u0001\u0010P\u001a\u00020QH\u00a7@\u00a2\u0006\u0002\u0010R\u00a8\u0006S"}, d2 = {"Lcom/tetris/modern/rl/network/ApiService;", "", "getAchievements", "Lretrofit2/Response;", "Lcom/tetris/modern/rl/network/models/AchievementsResponse;", "playerId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDailyChallenge", "Lcom/tetris/modern/rl/network/models/DailyChallengeResponse;", "date", "getDailyChallengeStreak", "Lcom/tetris/modern/rl/network/models/DailyChallengeStreakResponse;", "getLeaderboard", "Lcom/tetris/modern/rl/network/models/LeaderboardResponse;", "mode", "limit", "", "offset", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlayerScores", "Lcom/tetris/modern/rl/network/models/PlayerScoresResponse;", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProgression", "Lcom/tetris/modern/rl/network/models/ProgressionResponse;", "getPuzzles", "Lcom/tetris/modern/rl/network/models/PuzzlesResponse;", "login", "Lcom/tetris/modern/rl/network/models/AuthResponse;", "loginRequest", "Lcom/tetris/modern/rl/network/models/LoginRequest;", "(Lcom/tetris/modern/rl/network/models/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loginAsGuest", "guestRequest", "Lcom/tetris/modern/rl/network/models/GuestLoginRequest;", "(Lcom/tetris/modern/rl/network/models/GuestLoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "mergeProgression", "Lcom/tetris/modern/rl/network/models/ProgressionMergeResponse;", "mergeRequest", "Lcom/tetris/modern/rl/network/models/ProgressionMergeRequest;", "(Lcom/tetris/modern/rl/network/models/ProgressionMergeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshToken", "Lcom/tetris/modern/rl/network/models/TokenRefreshResponse;", "token", "register", "registerRequest", "Lcom/tetris/modern/rl/network/models/RegisterRequest;", "(Lcom/tetris/modern/rl/network/models/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitDailyChallengeResult", "Lcom/tetris/modern/rl/network/models/DailyChallengeResultResponse;", "challengeResult", "Lcom/tetris/modern/rl/network/models/DailyChallengeResultRequest;", "(Lcom/tetris/modern/rl/network/models/DailyChallengeResultRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitPuzzleResult", "Lcom/tetris/modern/rl/network/models/PuzzleResultResponse;", "puzzleResult", "Lcom/tetris/modern/rl/network/models/PuzzleResultRequest;", "(Lcom/tetris/modern/rl/network/models/PuzzleResultRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitScore", "Lcom/tetris/modern/rl/network/models/ScoreSubmitResponse;", "scoreRequest", "Lcom/tetris/modern/rl/network/models/ScoreSubmitRequest;", "(Lcom/tetris/modern/rl/network/models/ScoreSubmitRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncData", "Lcom/tetris/modern/rl/network/models/SyncDataResponse;", "syncRequest", "Lcom/tetris/modern/rl/network/models/SyncDataRequest;", "(Ljava/lang/String;Lcom/tetris/modern/rl/network/models/SyncDataRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncProgression", "Lcom/tetris/modern/rl/network/models/ProgressionSyncResponse;", "request", "Lcom/tetris/modern/rl/network/models/ProgressionSyncRequest;", "(Lcom/tetris/modern/rl/network/models/ProgressionSyncRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unlockAchievement", "Lcom/tetris/modern/rl/network/models/AchievementUnlockResponse;", "achievementRequest", "Lcom/tetris/modern/rl/network/models/AchievementUnlockRequest;", "(Lcom/tetris/modern/rl/network/models/AchievementUnlockRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProgression", "Lcom/tetris/modern/rl/network/models/ProgressionUpdateResponse;", "progressionRequest", "Lcom/tetris/modern/rl/network/models/ProgressionUpdateRequest;", "(Lcom/tetris/modern/rl/network/models/ProgressionUpdateRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public abstract interface ApiService {
    
    @retrofit2.http.GET(value = "api/scores.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getLeaderboard(@retrofit2.http.Query(value = "mode")
    @org.jetbrains.annotations.Nullable
    java.lang.String mode, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "offset")
    int offset, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.LeaderboardResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/scores.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object submitScore(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.ScoreSubmitRequest scoreRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.ScoreSubmitResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/scores.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getPlayerScores(@retrofit2.http.Query(value = "player_id")
    @org.jetbrains.annotations.NotNull
    java.lang.String playerId, @retrofit2.http.Query(value = "mode")
    @org.jetbrains.annotations.Nullable
    java.lang.String mode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.PlayerScoresResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/player-progression.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getProgression(@retrofit2.http.Query(value = "player_id")
    @org.jetbrains.annotations.NotNull
    java.lang.String playerId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.ProgressionResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/player-progression.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateProgression(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.ProgressionUpdateRequest progressionRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.ProgressionUpdateResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/player-progression.php?action=sync")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object syncProgression(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.ProgressionSyncRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.ProgressionSyncResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/player-progression.php?action=merge")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object mergeProgression(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.ProgressionMergeRequest mergeRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.ProgressionMergeResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/player-progression.php?action=achievements")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getAchievements(@retrofit2.http.Query(value = "player_id")
    @org.jetbrains.annotations.NotNull
    java.lang.String playerId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.AchievementsResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/player-progression.php?action=unlock_achievement")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object unlockAchievement(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.AchievementUnlockRequest achievementRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.AchievementUnlockResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/daily-challenge.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getDailyChallenge(@retrofit2.http.Query(value = "date")
    @org.jetbrains.annotations.Nullable
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.DailyChallengeResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/daily-challenge.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object submitDailyChallengeResult(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.DailyChallengeResultRequest challengeResult, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.DailyChallengeResultResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/daily-challenge.php?action=streak")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getDailyChallengeStreak(@retrofit2.http.Query(value = "player_id")
    @org.jetbrains.annotations.NotNull
    java.lang.String playerId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.DailyChallengeStreakResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/auth.php?action=register")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object register(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.RegisterRequest registerRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.AuthResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/auth.php?action=login")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object login(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.LoginRequest loginRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.AuthResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/auth.php?action=guest")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object loginAsGuest(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.GuestLoginRequest guestRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.AuthResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/auth.php?action=refresh")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object refreshToken(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.TokenRefreshResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/puzzles.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getPuzzles(@retrofit2.http.Query(value = "player_id")
    @org.jetbrains.annotations.NotNull
    java.lang.String playerId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.PuzzlesResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/puzzles.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object submitPuzzleResult(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.PuzzleResultRequest puzzleResult, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.PuzzleResultResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/sync.php")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object syncData(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull
    java.lang.String token, @retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.SyncDataRequest syncRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tetris.modern.rl.network.models.SyncDataResponse>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}