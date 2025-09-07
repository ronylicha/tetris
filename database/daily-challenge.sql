-- Daily Challenge Tables for Tetris
-- Add these tables to the existing database

-- Daily Challenge Scores Table
CREATE TABLE IF NOT EXISTS daily_challenge_scores (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    challenge_id VARCHAR(50) NOT NULL,
    player_name VARCHAR(50) NOT NULL,
    score INTEGER NOT NULL DEFAULT 0,
    time INTEGER NOT NULL DEFAULT 0, -- completion time in seconds
    stats TEXT, -- JSON string with detailed stats
    date DATE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints
    CONSTRAINT check_dc_score_positive CHECK (score >= 0),
    CONSTRAINT check_dc_time_positive CHECK (time >= 0),
    CONSTRAINT check_dc_name_length CHECK (length(player_name) > 0 AND length(player_name) <= 50),
    
    -- Ensure one submission per player per day
    UNIQUE(challenge_id, player_name, date)
);

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_dc_challenge_date ON daily_challenge_scores(challenge_id, date);
CREATE INDEX IF NOT EXISTS idx_dc_score_desc ON daily_challenge_scores(score DESC);
CREATE INDEX IF NOT EXISTS idx_dc_date_desc ON daily_challenge_scores(date DESC);
CREATE INDEX IF NOT EXISTS idx_dc_player ON daily_challenge_scores(player_name);

-- Daily Challenge Metadata Table
CREATE TABLE IF NOT EXISTS daily_challenges (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    challenge_id VARCHAR(50) UNIQUE NOT NULL,
    date DATE UNIQUE NOT NULL,
    name VARCHAR(100),
    seed INTEGER NOT NULL,
    objective_type VARCHAR(50),
    objective_target TEXT,
    modifiers TEXT, -- JSON array of modifiers
    difficulty INTEGER DEFAULT 1,
    max_time INTEGER DEFAULT 0, -- time limit in seconds, 0 = no limit
    max_pieces INTEGER DEFAULT 0, -- piece limit, 0 = no limit
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT check_dc_difficulty CHECK (difficulty >= 1 AND difficulty <= 5)
);

-- Create index for date lookups
CREATE INDEX IF NOT EXISTS idx_dc_meta_date ON daily_challenges(date);

-- Player Streaks Table
CREATE TABLE IF NOT EXISTS daily_challenge_streaks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    player_name VARCHAR(50) UNIQUE NOT NULL,
    current_streak INTEGER DEFAULT 0,
    longest_streak INTEGER DEFAULT 0,
    last_played_date DATE,
    total_challenges_completed INTEGER DEFAULT 0,
    total_xp_earned INTEGER DEFAULT 0,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT check_dc_streak_positive CHECK (current_streak >= 0),
    CONSTRAINT check_dc_longest_positive CHECK (longest_streak >= 0)
);

-- Create index for player lookups
CREATE INDEX IF NOT EXISTS idx_dc_streak_player ON daily_challenge_streaks(player_name);

-- View for today's leaderboard
CREATE VIEW IF NOT EXISTS daily_leaderboard_today AS
SELECT 
    ROW_NUMBER() OVER (ORDER BY score DESC, time ASC) as rank,
    player_name,
    score,
    time,
    stats,
    created_at
FROM daily_challenge_scores
WHERE date = DATE('now', 'localtime')
ORDER BY score DESC, time ASC
LIMIT 100;

-- View for all-time daily challenge stats
CREATE VIEW IF NOT EXISTS daily_challenge_stats AS
SELECT 
    player_name,
    COUNT(*) as challenges_completed,
    MAX(score) as best_score,
    MIN(time) as best_time,
    AVG(score) as avg_score,
    SUM(CASE WHEN date = DATE('now', 'localtime') THEN 1 ELSE 0 END) as completed_today
FROM daily_challenge_scores
GROUP BY player_name
ORDER BY challenges_completed DESC;

-- Sample data for testing (optional)
INSERT OR IGNORE INTO daily_challenges (challenge_id, date, name, seed, objective_type, objective_target, modifiers, difficulty)
VALUES 
    ('daily_' || strftime('%s', 'now'), DATE('now', 'localtime'), 'Epic Challenge', 
     ABS(RANDOM()), 'lines', '40', '[{"id":"speed_increase","name":"Accelerating Speed"}]', 3);