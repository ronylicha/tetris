-- Tetris High Scores Database Schema
-- SQLite database initialization script

CREATE TABLE IF NOT EXISTS high_scores (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    player_name VARCHAR(50) NOT NULL,
    score INTEGER NOT NULL,
    lines INTEGER NOT NULL,
    level INTEGER NOT NULL,
    game_duration INTEGER NOT NULL, -- in seconds
    date_achieved DATETIME DEFAULT CURRENT_TIMESTAMP,
    special_achievements TEXT, -- JSON string for T-spins, combos, etc.
    CONSTRAINT check_score_positive CHECK (score >= 0),
    CONSTRAINT check_lines_positive CHECK (lines >= 0),
    CONSTRAINT check_level_positive CHECK (level >= 1),
    CONSTRAINT check_name_length CHECK (length(player_name) > 0 AND length(player_name) <= 50)
);

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_score_desc ON high_scores(score DESC);
CREATE INDEX IF NOT EXISTS idx_date_desc ON high_scores(date_achieved DESC);
CREATE INDEX IF NOT EXISTS idx_player_name ON high_scores(player_name);

-- Insert some sample data for testing
INSERT OR IGNORE INTO high_scores (id, player_name, score, lines, level, game_duration, special_achievements) VALUES 
(1, 'Alex', 125000, 50, 6, 720, '{"tspins": 3, "combos": 5, "tetris": 8}'),
(2, 'Jordan', 98000, 42, 5, 680, '{"tspins": 1, "combos": 3, "tetris": 6}'),
(3, 'Sam', 87500, 38, 5, 650, '{"tspins": 2, "combos": 4, "tetris": 5}'),
(4, 'Casey', 76000, 35, 4, 600, '{"tspins": 0, "combos": 2, "tetris": 7}'),
(5, 'Taylor', 65000, 30, 4, 580, '{"tspins": 1, "combos": 1, "tetris": 4}');

-- View for leaderboard (top scores)
CREATE VIEW IF NOT EXISTS leaderboard AS
SELECT 
    ROW_NUMBER() OVER (ORDER BY score DESC) as rank,
    player_name,
    score,
    lines,
    level,
    game_duration,
    date_achieved,
    special_achievements
FROM high_scores
ORDER BY score DESC
LIMIT 50;

-- View for recent scores
CREATE VIEW IF NOT EXISTS recent_scores AS
SELECT 
    player_name,
    score,
    lines,
    level,
    game_duration,
    date_achieved,
    special_achievements
FROM high_scores
ORDER BY date_achieved DESC
LIMIT 20;