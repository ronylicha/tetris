-- Modern Tetris Progression System Database Schema
-- Complete database structure for player progression, achievements, and unlockables

-- ========================================
-- PLAYERS TABLE - User profiles and progression
-- ========================================
CREATE TABLE IF NOT EXISTS players (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    display_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE,
    avatar_id INTEGER DEFAULT 1,
    
    -- Progression
    level INTEGER DEFAULT 1,
    current_xp INTEGER DEFAULT 0,
    total_xp INTEGER DEFAULT 0,
    rank VARCHAR(20) DEFAULT 'Novice', -- Novice, Apprentice, Adept, Expert, Master, Grandmaster, Legend, Myth, Titan, Eternal
    
    -- Stats
    games_played INTEGER DEFAULT 0,
    total_score BIGINT DEFAULT 0,
    total_lines INTEGER DEFAULT 0,
    total_time INTEGER DEFAULT 0, -- in seconds
    favorite_mode VARCHAR(20),
    highest_score INTEGER DEFAULT 0,
    highest_combo INTEGER DEFAULT 0,
    total_tspins INTEGER DEFAULT 0,
    total_tetris INTEGER DEFAULT 0,
    perfect_clears INTEGER DEFAULT 0,
    
    -- Customization (current selections)
    current_theme VARCHAR(20) DEFAULT 'default',
    current_music VARCHAR(20) DEFAULT 'classic',
    current_piece_style VARCHAR(20) DEFAULT 'neon',
    current_background VARCHAR(20) DEFAULT 'space',
    
    -- Settings
    sfx_enabled BOOLEAN DEFAULT 1,
    particles_enabled BOOLEAN DEFAULT 1,
    effects_level INTEGER DEFAULT 2, -- 0: None, 1: Basic, 2: Full
    
    -- Metadata
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_login DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_game DATETIME,
    daily_streak INTEGER DEFAULT 0,
    last_daily_date DATE,
    
    CONSTRAINT check_level CHECK (level >= 1 AND level <= 100),
    CONSTRAINT check_xp CHECK (current_xp >= 0),
    CONSTRAINT check_username CHECK (length(username) >= 3 AND length(username) <= 50)
);

-- ========================================
-- ACHIEVEMENTS TABLE - All available achievements
-- ========================================
CREATE TABLE IF NOT EXISTS achievements (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    code VARCHAR(50) UNIQUE NOT NULL, -- Internal identifier
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(20) NOT NULL, -- beginner, lines, score, combo, special, modes, daily, secret
    tier VARCHAR(20) DEFAULT 'bronze', -- bronze, silver, gold, platinum
    
    -- Requirements (JSON format for flexibility)
    requirements TEXT NOT NULL, -- e.g., {"type": "score", "value": 100000}
    
    -- Rewards
    xp_reward INTEGER DEFAULT 100,
    unlock_reward VARCHAR(50), -- e.g., "theme:cyberpunk" or "title:Tetris Master"
    
    -- Display
    icon VARCHAR(50),
    hidden BOOLEAN DEFAULT 0, -- For secret achievements
    sort_order INTEGER DEFAULT 0,
    
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ========================================
-- PLAYER_ACHIEVEMENTS - Tracks unlocked achievements
-- ========================================
CREATE TABLE IF NOT EXISTS player_achievements (
    player_id INTEGER NOT NULL,
    achievement_id INTEGER NOT NULL,
    progress REAL DEFAULT 0, -- 0 to 1 (or beyond for cumulative achievements)
    unlocked BOOLEAN DEFAULT 0,
    unlocked_at DATETIME,
    
    PRIMARY KEY (player_id, achievement_id),
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    FOREIGN KEY (achievement_id) REFERENCES achievements(id)
);

-- ========================================
-- DAILY_CHALLENGES - Daily challenge definitions
-- ========================================
CREATE TABLE IF NOT EXISTS daily_challenges (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    date DATE UNIQUE NOT NULL,
    seed INTEGER NOT NULL, -- For procedural generation
    
    -- Challenge configuration
    mode VARCHAR(20) NOT NULL, -- classic, sprint, marathon, etc.
    goal_type VARCHAR(20) NOT NULL, -- score, lines, time, survival
    goal_value INTEGER NOT NULL,
    time_limit INTEGER, -- in seconds, NULL for unlimited
    
    -- Modifiers (JSON array)
    modifiers TEXT, -- e.g., ["invisible_pieces", "2x_speed", "no_hold", "earthquake"]
    
    -- Rewards
    xp_reward INTEGER DEFAULT 500,
    bonus_reward VARCHAR(50), -- Special unlock for first completion
    
    -- Stats
    total_attempts INTEGER DEFAULT 0,
    total_completions INTEGER DEFAULT 0,
    best_score INTEGER,
    best_player VARCHAR(50),
    
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ========================================
-- PLAYER_CHALLENGES - Daily challenge completions
-- ========================================
CREATE TABLE IF NOT EXISTS player_challenges (
    player_id INTEGER NOT NULL,
    challenge_id INTEGER NOT NULL,
    completed BOOLEAN DEFAULT 0,
    score INTEGER DEFAULT 0,
    completion_time INTEGER, -- in seconds
    attempts INTEGER DEFAULT 1,
    completed_at DATETIME,
    
    PRIMARY KEY (player_id, challenge_id),
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    FOREIGN KEY (challenge_id) REFERENCES daily_challenges(id)
);

-- ========================================
-- UNLOCKABLES - All unlockable content
-- ========================================
CREATE TABLE IF NOT EXISTS unlockables (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL, -- theme, music, piece_style, background, effect, title, avatar
    category VARCHAR(20), -- For grouping in UI
    
    -- Unlock requirements
    unlock_type VARCHAR(20) NOT NULL, -- level, achievement, purchase, special
    unlock_value VARCHAR(100), -- e.g., "level:20" or "achievement:tetris_master"
    
    -- Display
    preview_image VARCHAR(255),
    description TEXT,
    rarity VARCHAR(20) DEFAULT 'common', -- common, rare, epic, legendary
    sort_order INTEGER DEFAULT 0,
    
    -- Metadata
    is_default BOOLEAN DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ========================================
-- PLAYER_UNLOCKS - Tracks player's unlocked content
-- ========================================
CREATE TABLE IF NOT EXISTS player_unlocks (
    player_id INTEGER NOT NULL,
    unlockable_id INTEGER NOT NULL,
    unlocked_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_new BOOLEAN DEFAULT 1, -- For "new" badge in UI
    equipped BOOLEAN DEFAULT 0, -- Currently in use
    
    PRIMARY KEY (player_id, unlockable_id),
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    FOREIGN KEY (unlockable_id) REFERENCES unlockables(id)
);

-- ========================================
-- UPDATED SCORES TABLE - Link to players
-- ========================================
-- Note: These columns may already exist from previous updates
-- Using IF NOT EXISTS would be ideal but SQLite doesn't support it for ALTER TABLE
-- The migration script handles this more gracefully

-- ========================================
-- XP_TRANSACTIONS - Track XP gains for history
-- ========================================
CREATE TABLE IF NOT EXISTS xp_transactions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    player_id INTEGER NOT NULL,
    amount INTEGER NOT NULL,
    source VARCHAR(50) NOT NULL, -- gameplay, achievement, daily_challenge, bonus
    description TEXT,
    game_mode VARCHAR(20),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);

-- ========================================
-- PLAYER_BADGES - Special badges and titles
-- ========================================
CREATE TABLE IF NOT EXISTS player_badges (
    player_id INTEGER NOT NULL,
    badge_code VARCHAR(50) NOT NULL,
    earned_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    equipped BOOLEAN DEFAULT 0,
    
    PRIMARY KEY (player_id, badge_code),
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);

-- ========================================
-- GAME_SESSIONS - Track detailed game sessions
-- ========================================
CREATE TABLE IF NOT EXISTS game_sessions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    player_id INTEGER NOT NULL,
    game_mode VARCHAR(20) NOT NULL,
    score INTEGER NOT NULL,
    lines INTEGER NOT NULL,
    level INTEGER NOT NULL,
    duration INTEGER NOT NULL, -- in seconds
    
    -- Detailed stats
    pieces_placed INTEGER DEFAULT 0,
    hold_used INTEGER DEFAULT 0,
    tspins INTEGER DEFAULT 0,
    mini_tspins INTEGER DEFAULT 0,
    singles INTEGER DEFAULT 0,
    doubles INTEGER DEFAULT 0,
    triples INTEGER DEFAULT 0,
    tetris_count INTEGER DEFAULT 0,
    perfect_clears INTEGER DEFAULT 0,
    max_combo INTEGER DEFAULT 0,
    
    -- Mode-specific data (JSON)
    mode_data TEXT,
    
    -- Metadata
    started_at DATETIME NOT NULL,
    ended_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);

-- ========================================
-- INDEXES FOR PERFORMANCE
-- ========================================
CREATE INDEX idx_players_username ON players(username);
CREATE INDEX idx_players_level ON players(level DESC);
CREATE INDEX idx_players_total_xp ON players(total_xp DESC);
CREATE INDEX idx_player_achievements_player ON player_achievements(player_id);
CREATE INDEX idx_player_achievements_unlocked ON player_achievements(unlocked);
CREATE INDEX idx_daily_challenges_date ON daily_challenges(date);
CREATE INDEX idx_player_challenges_player ON player_challenges(player_id);
CREATE INDEX idx_player_unlocks_player ON player_unlocks(player_id);
CREATE INDEX idx_xp_transactions_player ON xp_transactions(player_id);
CREATE INDEX idx_game_sessions_player ON game_sessions(player_id);
CREATE INDEX idx_game_sessions_date ON game_sessions(ended_at DESC);

-- ========================================
-- VIEWS FOR COMMON QUERIES
-- ========================================

-- Player progression overview
CREATE VIEW IF NOT EXISTS player_progression_view AS
SELECT 
    p.id,
    p.username,
    p.display_name,
    p.level,
    p.current_xp,
    p.total_xp,
    p.rank,
    COUNT(DISTINCT pa.achievement_id) as achievements_unlocked,
    COUNT(DISTINCT pu.unlockable_id) as items_unlocked,
    p.daily_streak,
    p.games_played,
    p.highest_score
FROM players p
LEFT JOIN player_achievements pa ON p.id = pa.player_id AND pa.unlocked = 1
LEFT JOIN player_unlocks pu ON p.id = pu.player_id
GROUP BY p.id;

-- Global leaderboard with player info
CREATE VIEW IF NOT EXISTS global_leaderboard AS
SELECT 
    ROW_NUMBER() OVER (ORDER BY p.total_xp DESC) as rank,
    p.username,
    p.display_name,
    p.level,
    p.rank as player_rank,
    p.total_xp,
    p.highest_score,
    p.games_played,
    COUNT(DISTINCT pa.achievement_id) as achievements
FROM players p
LEFT JOIN player_achievements pa ON p.id = pa.player_id AND pa.unlocked = 1
GROUP BY p.id
ORDER BY p.total_xp DESC
LIMIT 100;

-- Daily challenge leaderboard
CREATE VIEW IF NOT EXISTS daily_leaderboard AS
SELECT 
    p.display_name,
    pc.score,
    pc.completion_time,
    pc.completed_at,
    dc.date as challenge_date
FROM player_challenges pc
JOIN players p ON pc.player_id = p.id
JOIN daily_challenges dc ON pc.challenge_id = dc.id
WHERE pc.completed = 1 AND dc.date = DATE('now')
ORDER BY pc.score DESC
LIMIT 50;

-- ========================================
-- INITIAL DATA - Populate default unlockables
-- ========================================

-- Default themes
INSERT OR IGNORE INTO unlockables (code, name, type, unlock_type, unlock_value, is_default) VALUES
('theme_default', 'Default', 'theme', 'level', 'level:1', 1),
('theme_cyberpunk', 'Cyberpunk', 'theme', 'level', 'level:10', 0),
('theme_retro', 'Retro', 'theme', 'level', 'level:20', 0),
('theme_nature', 'Nature', 'theme', 'level', 'level:30', 0),
('theme_minimal', 'Minimal', 'theme', 'level', 'level:40', 0),
('theme_galaxy', 'Galaxy', 'theme', 'level', 'level:50', 0),
('theme_matrix', 'Matrix', 'theme', 'achievement', 'achievement:hacker', 0),
('theme_rainbow', 'Rainbow', 'theme', 'special', 'daily_streak:30', 0);

-- Default music tracks
INSERT OR IGNORE INTO unlockables (code, name, type, unlock_type, unlock_value, is_default) VALUES
('music_classic', 'Classic', 'music', 'level', 'level:1', 1),
('music_chiptune', 'Chiptune', 'music', 'level', 'level:5', 0),
('music_synthwave', 'Synthwave', 'music', 'level', 'level:15', 0),
('music_orchestral', 'Orchestral', 'music', 'level', 'level:25', 0),
('music_jazz', 'Jazz', 'music', 'level', 'level:35', 0),
('music_metal', 'Metal', 'music', 'level', 'level:45', 0),
('music_lofi', 'Lo-Fi', 'music', 'achievement', 'achievement:zen_master', 0);

-- Default piece styles
INSERT OR IGNORE INTO unlockables (code, name, type, unlock_type, unlock_value, is_default) VALUES
('piece_neon', 'Neon', 'piece_style', 'level', 'level:1', 1),
('piece_glass', 'Glass', 'piece_style', 'level', 'level:8', 0),
('piece_pixel', 'Pixel', 'piece_style', 'level', 'level:18', 0),
('piece_hologram', 'Hologram', 'piece_style', 'level', 'level:28', 0),
('piece_crystal', 'Crystal', 'piece_style', 'level', 'level:38', 0),
('piece_animated', 'Animated', 'piece_style', 'level', 'level:48', 0);

-- Sample achievements
INSERT OR IGNORE INTO achievements (code, name, description, category, requirements, xp_reward) VALUES
('first_game', 'Welcome!', 'Complete your first game', 'beginner', '{"type": "games", "value": 1}', 50),
('score_10k', 'Getting Started', 'Score 10,000 points', 'score', '{"type": "score", "value": 10000}', 100),
('score_100k', 'High Scorer', 'Score 100,000 points', 'score', '{"type": "score", "value": 100000}', 500),
('lines_100', 'Line Clearer', 'Clear 100 lines total', 'lines', '{"type": "total_lines", "value": 100}', 200),
('tetris_master', 'Tetris Master', 'Perform 50 Tetris clears', 'special', '{"type": "tetris", "value": 50}', 1000),
('tspin_expert', 'T-Spin Expert', 'Perform 20 T-Spins', 'special', '{"type": "tspins", "value": 20}', 750),
('daily_warrior', 'Daily Warrior', 'Complete 7 daily challenges', 'daily', '{"type": "daily", "value": 7}', 500),
('streak_week', 'Week Streak', 'Maintain a 7-day streak', 'daily', '{"type": "streak", "value": 7}', 800);