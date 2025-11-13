package com.tetris.modern.rl.network.models

import com.google.gson.annotations.SerializedName
import java.util.Date

// Authentication Models
data class LoginRequest(
    @SerializedName("username") val username: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("password") val password: String,
    @SerializedName("google_token") val googleToken: String? = null
)

data class RegisterRequest(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("guest_user_id") val guestUserId: String? = null
)

data class GuestLoginRequest(
    @SerializedName("deviceId") val deviceId: String
)

data class AuthResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("token") val token: String,
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("userId") val userId: String,
    @SerializedName("username") val username: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("email") val email: String? = null,
    @SerializedName("isGuest") val isGuest: Boolean = false,
    @SerializedName("message") val message: String? = null
)

data class TokenRefreshResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("token") val token: String,
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("message") val message: String? = null
)

data class AvailabilityCheckRequest(
    @SerializedName("username") val username: String? = null,
    @SerializedName("email") val email: String? = null
)

data class AvailabilityCheckResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("usernameAvailable") val usernameAvailable: Boolean = true,
    @SerializedName("emailAvailable") val emailAvailable: Boolean = true,
    @SerializedName("message") val message: String? = null
)

// Score Models
data class ScoreSubmitRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("player_name") val playerName: String,
    @SerializedName("score") val score: Int,
    @SerializedName("lines") val lines: Int,
    @SerializedName("level") val level: Int,
    @SerializedName("game_mode") val gameMode: String,
    @SerializedName("duration") val duration: Long,
    @SerializedName("date") val date: Long? = null,
    @SerializedName("mode_data") val modeData: String? = null,
    @SerializedName("special_achievements") val specialAchievements: String? = null
)

data class ScoreSubmitResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("score_id") val scoreId: Long? = null,
    @SerializedName("rank") val rank: Int? = null,
    @SerializedName("is_personal_best") val isPersonalBest: Boolean = false,
    @SerializedName("is_new_record") val isNewRecord: Boolean = false,
    @SerializedName("message") val message: String? = null
)

data class LeaderboardEntry(
    @SerializedName("rank") val rank: Int,
    @SerializedName("player_id") val playerId: String? = null,
    @SerializedName("player_name") val playerName: String,
    @SerializedName("score") val score: Int,
    @SerializedName("lines") val lines: Int,
    @SerializedName("level") val level: Int,
    @SerializedName("game_mode") val gameMode: String,
    @SerializedName("game_duration") val duration: Long,  // Changed from "duration" to "game_duration"
    @SerializedName("date_achieved") val date: String,  // Changed from Long to String and "date" to "date_achieved"
    @SerializedName("mode_data") val modeData: String? = null,
    @SerializedName("special_achievements") val specialAchievements: List<String>? = null
)

data class LeaderboardResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val leaderboard: List<LeaderboardEntry>,  // Changed from "leaderboard" to "data"
    @SerializedName("total_count") val totalCount: Int? = null,
    @SerializedName("page") val page: Int? = null,
    @SerializedName("message") val message: String? = null
)

data class PlayerScoresResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("scores") val scores: List<LeaderboardEntry>,
    @SerializedName("personal_best") val personalBest: LeaderboardEntry? = null,
    @SerializedName("total_games") val totalGames: Int? = null,
    @SerializedName("message") val message: String? = null
)

// Progression Models
data class ProgressionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("level") val level: Int,
    @SerializedName("current_xp") val currentXp: Int,
    @SerializedName("total_xp") val totalXp: Int,
    @SerializedName("rank") val rank: String,
    @SerializedName("statistics") val statistics: Map<String, Any>? = null,
    @SerializedName("unlocks") val unlocks: Map<String, List<String>>? = null,
    @SerializedName("achievements") val achievements: List<Map<String, Any>>? = null,
    @SerializedName("last_sync") val lastSync: String? = null,
    @SerializedName("message") val message: String? = null
)

data class ProgressionUpdateRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("xp_earned") val xpEarned: Int,
    @SerializedName("source") val source: String // gameplay, achievement, daily_challenge, etc.
)

data class ProgressionUpdateResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("new_level") val newLevel: Int,
    @SerializedName("new_xp") val newXp: Int,
    @SerializedName("level_up") val levelUp: Boolean = false,
    @SerializedName("new_unlocks") val newUnlocks: List<String>? = null,
    @SerializedName("message") val message: String? = null
)

data class ProgressionSyncRequest(
    @SerializedName("action") val action: String = "sync_progression",
    @SerializedName("player_id") val playerId: String,
    @SerializedName("level") val level: Int,
    @SerializedName("current_xp") val currentXp: Int,
    @SerializedName("total_xp") val totalXp: Int,
    @SerializedName("rank") val rank: String,
    @SerializedName("unlocked_themes") val unlockedThemes: List<String>,
    @SerializedName("unlocked_music") val unlockedMusic: List<String>,
    @SerializedName("unlocked_piece_styles") val unlockedPieceStyles: List<String>,
    @SerializedName("unlocked_effects") val unlockedEffects: List<String>,
    @SerializedName("statistics") val statistics: Map<String, Any>? = null,
    @SerializedName("total_games_played") val totalGamesPlayed: Int = 0,
    @SerializedName("total_play_time") val totalPlayTime: Long = 0,
    @SerializedName("favorite_mode") val favoriteMode: String = "Classic",
    @SerializedName("daily_streak") val dailyStreak: Int = 0,
    @SerializedName("daily_challenges_completed") val dailyChallengesCompleted: Int = 0
)

data class ProgressionSyncResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("synced_data") val syncedData: ProgressionData,
    @SerializedName("conflicts") val conflicts: List<String>? = null,
    @SerializedName("message") val message: String? = null
)

data class ProgressionMergeRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("local_data") val localData: ProgressionData,
    @SerializedName("merge_strategy") val mergeStrategy: String // keep_local, keep_remote, merge_both
)

data class ProgressionMergeResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("merged_data") val mergedData: ProgressionData,
    @SerializedName("message") val message: String? = null
)

data class ProgressionData(
    @SerializedName("level") val level: Int,
    @SerializedName("current_xp") val currentXp: Int,
    @SerializedName("total_xp") val totalXp: Int,
    @SerializedName("rank") val rank: String,
    @SerializedName("games_played") val gamesPlayed: Int,
    @SerializedName("total_score") val totalScore: Long,
    @SerializedName("total_lines") val totalLines: Int,
    @SerializedName("highest_score") val highestScore: Int,
    @SerializedName("achievements") val achievements: List<String>,
    @SerializedName("unlocks") val unlocks: List<String>,
    @SerializedName("last_sync") val lastSync: Long
)

// Achievement Models
data class AchievementsResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("achievements") val achievements: List<Achievement>,
    @SerializedName("unlocked_count") val unlockedCount: Int,
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("message") val message: String? = null
)

data class Achievement(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("tier") val tier: String,
    @SerializedName("xp_reward") val xpReward: Int,
    @SerializedName("icon") val icon: String? = null,
    @SerializedName("unlocked") val unlocked: Boolean = false,
    @SerializedName("unlocked_at") val unlockedAt: Long? = null,
    @SerializedName("progress") val progress: Float = 0f
)

data class AchievementUnlockRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("achievement_id") val achievementId: String
)

data class AchievementUnlockResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("xp_earned") val xpEarned: Int,
    @SerializedName("new_total_xp") val newTotalXp: Int,
    @SerializedName("level_up") val levelUp: Boolean = false,
    @SerializedName("message") val message: String? = null
)

// Daily Challenge Models
data class DailyChallengeResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("challenge") val challenge: DailyChallenge? = null,
    @SerializedName("message") val message: String? = null
)

data class DailyChallenge(
    @SerializedName("id") val id: Long,
    @SerializedName("date") val date: String,
    @SerializedName("seed") val seed: Int,
    @SerializedName("mode") val mode: String,
    @SerializedName("goal_type") val goalType: String,
    @SerializedName("goal_value") val goalValue: Int,
    @SerializedName("time_limit") val timeLimit: Int? = null,
    @SerializedName("modifiers") val modifiers: List<String>? = null,
    @SerializedName("xp_reward") val xpReward: Int,
    @SerializedName("bonus_reward") val bonusReward: String? = null,
    @SerializedName("completed") val completed: Boolean = false,
    @SerializedName("best_score") val bestScore: Int? = null
)

data class DailyChallengeResultRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("challenge_id") val challengeId: Long,
    @SerializedName("score") val score: Int,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("completion_time") val completionTime: Long? = null
)

data class DailyChallengeResultResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("rank") val rank: Int? = null,
    @SerializedName("xp_earned") val xpEarned: Int,
    @SerializedName("streak") val streak: Int,
    @SerializedName("new_record") val newRecord: Boolean = false,
    @SerializedName("message") val message: String? = null
)

data class DailyChallengeStreakResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("current_streak") val currentStreak: Int,
    @SerializedName("best_streak") val bestStreak: Int,
    @SerializedName("last_completed") val lastCompleted: String? = null,
    @SerializedName("message") val message: String? = null
)

// Puzzle Models
data class PuzzlesResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("puzzles") val puzzles: List<PuzzleInfo>,
    @SerializedName("completed_count") val completedCount: Int,
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("message") val message: String? = null
)

data class PuzzleInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("objective") val objective: String,
    @SerializedName("completed") val completed: Boolean = false,
    @SerializedName("stars") val stars: Int = 0,
    @SerializedName("best_moves") val bestMoves: Int? = null,
    @SerializedName("best_time") val bestTime: Long? = null
)

data class PuzzleResultRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("puzzle_id") val puzzleId: Int,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("moves") val moves: Int,
    @SerializedName("time") val time: Long,
    @SerializedName("stars") val stars: Int
)

data class PuzzleResultResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("new_record") val newRecord: Boolean = false,
    @SerializedName("xp_earned") val xpEarned: Int,
    @SerializedName("message") val message: String? = null
)

// Sync Models
data class SyncDataRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("last_sync_time") val lastSyncTime: Long
)

data class SyncDataResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("scores") val scores: List<LeaderboardEntry>,
    @SerializedName("total_score") val totalScore: Long,
    @SerializedName("games_played") val gamesPlayed: Int,
    @SerializedName("highest_score") val highestScore: Int,
    @SerializedName("total_lines") val totalLines: Int,
    @SerializedName("last_sync_time") val lastSyncTime: Long,
    @SerializedName("message") val message: String? = null
)