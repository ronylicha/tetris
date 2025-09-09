package com.tetris.modern.rl.network

import com.tetris.modern.rl.network.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Score endpoints
    @GET("api/scores.php")
    suspend fun getLeaderboard(
        @Query("mode") mode: String? = null,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): Response<LeaderboardResponse>
    
    @POST("api/scores.php")
    suspend fun submitScore(
        @Body scoreRequest: ScoreSubmitRequest
    ): Response<ScoreSubmitResponse>
    
    @GET("api/scores.php")
    suspend fun getPlayerScores(
        @Query("player_id") playerId: String,
        @Query("mode") mode: String? = null
    ): Response<PlayerScoresResponse>
    
    // Progression endpoints
    @GET("api/player-progression.php")
    suspend fun getProgression(
        @Query("player_id") playerId: String
    ): Response<ProgressionResponse>
    
    @POST("api/player-progression.php")
    suspend fun updateProgression(
        @Body progressionRequest: ProgressionUpdateRequest
    ): Response<ProgressionUpdateResponse>
    
    @POST("api/player-progression.php?action=sync")
    suspend fun syncProgression(
        @Body request: ProgressionSyncRequest
    ): Response<ProgressionSyncResponse>
    
    @POST("api/player-progression.php?action=merge")
    suspend fun mergeProgression(
        @Body mergeRequest: ProgressionMergeRequest
    ): Response<ProgressionMergeResponse>
    
    // Achievement endpoints
    @GET("api/player-progression.php?action=achievements")
    suspend fun getAchievements(
        @Query("player_id") playerId: String
    ): Response<AchievementsResponse>
    
    @POST("api/player-progression.php?action=unlock_achievement")
    suspend fun unlockAchievement(
        @Body achievementRequest: AchievementUnlockRequest
    ): Response<AchievementUnlockResponse>
    
    // Daily Challenge endpoints
    @GET("api/daily-challenge.php")
    suspend fun getDailyChallenge(
        @Query("date") date: String? = null
    ): Response<DailyChallengeResponse>
    
    @POST("api/daily-challenge.php")
    suspend fun submitDailyChallengeResult(
        @Body challengeResult: DailyChallengeResultRequest
    ): Response<DailyChallengeResultResponse>
    
    @GET("api/daily-challenge.php?action=streak")
    suspend fun getDailyChallengeStreak(
        @Query("player_id") playerId: String
    ): Response<DailyChallengeStreakResponse>
    
    // Authentication endpoints
    @POST("api/auth.php?action=register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<AuthResponse>
    
    @POST("api/auth.php?action=login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<AuthResponse>
    
    @POST("api/auth.php?action=guest")
    suspend fun loginAsGuest(
        @Body guestRequest: GuestLoginRequest
    ): Response<AuthResponse>
    
    @POST("api/auth.php?action=refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String
    ): Response<TokenRefreshResponse>
    
    // Puzzle endpoints
    @GET("api/puzzles.php")
    suspend fun getPuzzles(
        @Query("player_id") playerId: String
    ): Response<PuzzlesResponse>
    
    @POST("api/puzzles.php")
    suspend fun submitPuzzleResult(
        @Body puzzleResult: PuzzleResultRequest
    ): Response<PuzzleResultResponse>
    
    // Sync endpoint
    @POST("api/sync.php")
    suspend fun syncData(
        @Header("Authorization") token: String,
        @Body syncRequest: SyncDataRequest
    ): Response<SyncDataResponse>
}