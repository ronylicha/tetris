package com.tetris.modern.rl.network.models

import com.google.gson.annotations.SerializedName
import java.util.Date

// Base response
data class BaseResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String? = null,
    @SerializedName("error") val error: String? = null
)

// Score models
data class LeaderboardResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val leaderboard: List<LeaderboardEntry>,
    @SerializedName("total") val total: Int
)

data class LeaderboardEntry(
    @SerializedName("rank") val rank: Int,
    @SerializedName("player_name") val playerName: String,
    @SerializedName("score") val score: Int,
    @SerializedName("lines") val lines: Int,
    @SerializedName("level") val level: Int,
    @SerializedName("game_mode") val gameMode: String,
    @SerializedName("date") val date: String,
    @SerializedName("country") val country: String? = null
)

data class ScoreSubmitRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("player_name") val playerName: String,
    @SerializedName("score") val score: Int,
    @SerializedName("lines") val lines: Int,
    @SerializedName("level") val level: Int,
    @SerializedName("game_mode") val gameMode: String,
    @SerializedName("duration") val duration: Long,
    @SerializedName("statistics") val statistics: Map<String, Any>? = null
)

data class ScoreSubmitResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("score_id") val scoreId: String?,
    @SerializedName("rank") val rank: Int?,
    @SerializedName("is_personal_best") val isPersonalBest: Boolean
)

data class PlayerScoresResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("scores") val scores: List<PlayerScore>
)

data class PlayerScore(
    @SerializedName("score_id") val scoreId: String,
    @SerializedName("score") val score: Int,
    @SerializedName("lines") val lines: Int,
    @SerializedName("level") val level: Int,
    @SerializedName("game_mode") val gameMode: String,
    @SerializedName("date") val date: String
)

// Progression models
data class ProgressionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val progression: ProgressionData
)

data class ProgressionData(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("level") val level: Int,
    @SerializedName("current_xp") val currentXP: Int,
    @SerializedName("total_xp") val totalXP: Int,
    @SerializedName("rank") val rank: String,
    @SerializedName("unlocked_items") val unlockedItems: UnlockedItems,
    @SerializedName("statistics") val statistics: PlayerStatistics
)

data class UnlockedItems(
    @SerializedName("themes") val themes: List<String>,
    @SerializedName("music") val music: List<String>,
    @SerializedName("piece_styles") val pieceStyles: List<String>,
    @SerializedName("effects") val effects: List<String>
)

data class PlayerStatistics(
    @SerializedName("total_games") val totalGames: Int,
    @SerializedName("total_lines") val totalLines: Int,
    @SerializedName("total_score") val totalScore: Long,
    @SerializedName("highest_score") val highestScore: Int,
    @SerializedName("total_play_time") val totalPlayTime: Long,
    @SerializedName("favorite_mode") val favoriteMode: String
)

data class ProgressionUpdateRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("xp_gained") val xpGained: Int,
    @SerializedName("source") val source: String,
    @SerializedName("game_data") val gameData: Map<String, Any>? = null
)

data class ProgressionUpdateResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("new_level") val newLevel: Int,
    @SerializedName("new_xp") val newXP: Int,
    @SerializedName("level_up") val levelUp: Boolean,
    @SerializedName("unlocks") val unlocks: List<String>? = null,
    @SerializedName("message") val message: String? = null
)

data class ProgressionSyncRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("total_xp") val totalXP: Int,
    @SerializedName("level") val level: Int,
    @SerializedName("rank") val rank: String,
    @SerializedName("unlocked_items") val unlockedItems: List<String>
)

data class ProgressionSyncResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("progression") val progression: ProgressionServerData? = null
)

data class ProgressionServerData(
    @SerializedName("level") val level: Int,
    @SerializedName("current_xp") val currentXP: Int,
    @SerializedName("total_xp") val totalXP: Int,
    @SerializedName("rank") val rank: String,
    @SerializedName("unlocked_themes") val unlockedThemes: List<String>,
    @SerializedName("unlocked_music") val unlockedMusic: List<String>,
    @SerializedName("unlocked_piece_styles") val unlockedPieceStyles: List<String>,
    @SerializedName("unlocked_effects") val unlockedEffects: List<String>,
    @SerializedName("statistics") val statistics: Map<String, Any>
)

data class ProgressionMergeRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("guest_data") val guestData: ProgressionData
)

data class ProgressionMergeResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("merged_data") val mergedData: ProgressionData
)

// Achievement models
data class AchievementsResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("achievements") val achievements: List<AchievementData>
)

data class AchievementData(
    @SerializedName("achievement_id") val achievementId: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("unlocked") val unlocked: Boolean,
    @SerializedName("unlocked_date") val unlockedDate: String?,
    @SerializedName("progress") val progress: Int,
    @SerializedName("max_progress") val maxProgress: Int,
    @SerializedName("xp_reward") val xpReward: Int,
    @SerializedName("trophy") val trophy: String?
)

data class AchievementUnlockRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("achievement_id") val achievementId: String,
    @SerializedName("game_stats") val gameStats: Map<String, Any>
)

data class AchievementUnlockResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("xp_earned") val xpEarned: Int,
    @SerializedName("new_achievements") val newAchievements: List<String>
)

// Daily Challenge models
data class DailyChallengeResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("challenge") val challenge: DailyChallengeData
)

data class DailyChallengeData(
    @SerializedName("date") val date: String,
    @SerializedName("seed") val seed: Long,
    @SerializedName("modifiers") val modifiers: List<String>,
    @SerializedName("target_score") val targetScore: Int,
    @SerializedName("target_lines") val targetLines: Int,
    @SerializedName("time_limit") val timeLimit: Long,
    @SerializedName("leaderboard") val leaderboard: List<DailyChallengeScore>? = null
)

data class DailyChallengeScore(
    @SerializedName("rank") val rank: Int,
    @SerializedName("player_name") val playerName: String,
    @SerializedName("score") val score: Int,
    @SerializedName("completion_time") val completionTime: Long
)

data class DailyChallengeResultRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("date") val date: String,
    @SerializedName("score") val score: Int,
    @SerializedName("lines") val lines: Int,
    @SerializedName("completion_time") val completionTime: Long,
    @SerializedName("completed") val completed: Boolean
)

data class DailyChallengeResultResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("rank") val rank: Int,
    @SerializedName("streak") val streak: Int,
    @SerializedName("rewards") val rewards: List<String>? = null
)

data class DailyChallengeStreakResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("current_streak") val currentStreak: Int,
    @SerializedName("best_streak") val bestStreak: Int,
    @SerializedName("last_completed") val lastCompleted: String?
)

// Authentication models
data class RegisterRequest(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class GuestLoginRequest(
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("guest_name") val guestName: String? = null
)

data class AuthResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("token") val token: String?,
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("player_id") val playerId: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("is_guest") val isGuest: Boolean = false
)

data class TokenRefreshResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("token") val token: String?,
    @SerializedName("refresh_token") val refreshToken: String?
)

// Puzzle models
data class PuzzlesResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("puzzles") val puzzles: List<PuzzleProgressData>
)

data class PuzzleProgressData(
    @SerializedName("puzzle_id") val puzzleId: Int,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("stars") val stars: Int,
    @SerializedName("best_time") val bestTime: Long?,
    @SerializedName("best_moves") val bestMoves: Int?
)

data class PuzzleResultRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("puzzle_id") val puzzleId: Int,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("stars") val stars: Int,
    @SerializedName("time") val time: Long,
    @SerializedName("moves") val moves: Int,
    @SerializedName("hints_used") val hintsUsed: Int
)

data class PuzzleResultResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("is_new_best") val isNewBest: Boolean,
    @SerializedName("xp_earned") val xpEarned: Int
)

// Sync models
data class SyncDataRequest(
    @SerializedName("player_id") val playerId: String,
    @SerializedName("last_sync") val lastSync: String,
    @SerializedName("local_data") val localData: LocalSyncData
)

data class LocalSyncData(
    @SerializedName("scores") val scores: List<ScoreSubmitRequest>? = null,
    @SerializedName("progression") val progression: ProgressionData? = null,
    @SerializedName("achievements") val achievements: List<String>? = null,
    @SerializedName("puzzles") val puzzles: List<PuzzleProgressData>? = null,
    @SerializedName("settings") val settings: Map<String, Any>? = null
)

data class SyncDataResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("server_data") val serverData: LocalSyncData,
    @SerializedName("conflicts") val conflicts: List<String>? = null,
    @SerializedName("last_sync") val lastSync: String
)