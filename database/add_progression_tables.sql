-- Add missing tables for complete progression synchronization
-- This script adds tables for player statistics, progression history, and sync tracking

-- Table for detailed player statistics by game mode
CREATE TABLE IF NOT EXISTS player_statistics (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    player_id INTEGER NOT NULL,
    game_mode VARCHAR(20) NOT NULL,
    
    -- Game statistics
    games_played INTEGER DEFAULT 0,
    total_score BIGINT DEFAULT 0,
    highest_score INTEGER DEFAULT 0,
    average_score INTEGER DEFAULT 0,
    total_lines INTEGER DEFAULT 0,
    total_time INTEGER DEFAULT 0, -- in seconds
    
    -- Performance metrics
    total_pieces INTEGER DEFAULT 0,
    total_tspins INTEGER DEFAULT 0,
    total_tetrises INTEGER DEFAULT 0,
    perfect_clears INTEGER DEFAULT 0,
    longest_combo INTEGER DEFAULT 0,
    highest_level INTEGER DEFAULT 0,
    
    -- Calculated metrics
    lines_per_minute REAL DEFAULT 0,
    score_per_line REAL DEFAULT 0,
    win_rate REAL DEFAULT 0, -- For battle modes
    completion_rate REAL DEFAULT 0, -- For puzzle/challenge modes
    
    -- Timestamps
    first_played DATETIME,
    last_played DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    UNIQUE(player_id, game_mode)
);

-- Table for progression history tracking
CREATE TABLE IF NOT EXISTS player_progression_history (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    player_id INTEGER NOT NULL,
    
    -- XP and Level changes
    xp_gained INTEGER NOT NULL,
    xp_source VARCHAR(50) NOT NULL, -- gameplay, achievement, daily_challenge, etc.
    xp_details TEXT, -- JSON with additional context
    
    -- State after change
    new_level INTEGER NOT NULL,
    new_total_xp INTEGER NOT NULL,
    new_rank VARCHAR(20),
    
    -- Unlocks triggered
    unlocks_triggered TEXT, -- JSON array of unlock IDs
    
    -- Metadata
    game_mode VARCHAR(20),
    game_session_id INTEGER,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);

-- Table for tracking synchronization status
CREATE TABLE IF NOT EXISTS player_sync_log (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    player_id INTEGER NOT NULL,
    
    -- Sync details
    sync_type VARCHAR(20) NOT NULL, -- full, partial, progression, scores, achievements
    sync_direction VARCHAR(10) NOT NULL, -- upload, download, merge
    sync_status VARCHAR(20) NOT NULL, -- pending, success, failed, conflict
    
    -- Data synced
    data_synced TEXT, -- JSON with details of what was synced
    conflicts_resolved TEXT, -- JSON with conflict resolution details
    
    -- Timestamps
    started_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    completed_at DATETIME,
    
    -- Error tracking
    error_message TEXT,
    retry_count INTEGER DEFAULT 0,
    
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);

-- Table for complete player progression state
CREATE TABLE IF NOT EXISTS player_progression (
    player_id INTEGER PRIMARY KEY,
    
    -- Current progression
    level INTEGER DEFAULT 1,
    current_xp INTEGER DEFAULT 0,
    total_xp INTEGER DEFAULT 0,
    xp_to_next_level INTEGER DEFAULT 100,
    rank VARCHAR(20) DEFAULT 'Novice',
    
    -- Unlocked content (JSON arrays)
    unlocked_themes TEXT DEFAULT '["default"]',
    unlocked_music TEXT DEFAULT '["classic"]',
    unlocked_piece_styles TEXT DEFAULT '["neon"]',
    unlocked_effects TEXT DEFAULT '["basic"]',
    unlocked_backgrounds TEXT DEFAULT '["space"]',
    
    -- Achievement progress (JSON)
    achievement_progress TEXT DEFAULT '{}',
    trophy_progress TEXT DEFAULT '{}',
    
    -- Daily challenges
    daily_streak INTEGER DEFAULT 0,
    last_daily_date DATE,
    daily_challenges_completed INTEGER DEFAULT 0,
    
    -- Overall statistics
    total_games_played INTEGER DEFAULT 0,
    total_play_time INTEGER DEFAULT 0,
    favorite_mode VARCHAR(20),
    
    -- Sync metadata
    last_sync DATETIME,
    sync_version INTEGER DEFAULT 1,
    
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);

-- Indexes for performance
CREATE INDEX IF NOT EXISTS idx_player_stats_mode ON player_statistics(player_id, game_mode);
CREATE INDEX IF NOT EXISTS idx_player_stats_score ON player_statistics(highest_score DESC);
CREATE INDEX IF NOT EXISTS idx_progression_history_player ON player_progression_history(player_id, timestamp DESC);
CREATE INDEX IF NOT EXISTS idx_sync_log_player ON player_sync_log(player_id, started_at DESC);
CREATE INDEX IF NOT EXISTS idx_sync_log_status ON player_sync_log(sync_status, started_at DESC);

-- Migrate existing data from players table to new tables
INSERT OR IGNORE INTO player_progression (
    player_id, level, current_xp, total_xp, rank,
    total_games_played, total_play_time, favorite_mode
)
SELECT 
    id, level, current_xp, total_xp, rank,
    games_played, total_time, favorite_mode
FROM players
WHERE NOT EXISTS (
    SELECT 1 FROM player_progression WHERE player_id = players.id
);

-- Create initial statistics records for existing players
INSERT OR IGNORE INTO player_statistics (
    player_id, game_mode, games_played, total_score, highest_score,
    total_lines, total_time, total_tspins, total_tetrises, perfect_clears
)
SELECT 
    id, 
    COALESCE(favorite_mode, 'Classic'),
    games_played,
    total_score,
    highest_score,
    total_lines,
    total_time,
    total_tspins,
    total_tetris,
    perfect_clears
FROM players
WHERE games_played > 0
AND NOT EXISTS (
    SELECT 1 FROM player_statistics 
    WHERE player_id = players.id 
    AND game_mode = COALESCE(players.favorite_mode, 'Classic')
);