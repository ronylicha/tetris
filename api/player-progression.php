<?php
// API endpoints for player progression system
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, PUT, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type, Authorization');

// Handle preflight requests
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    exit(0);
}

// Database connection
$dbPath = __DIR__ . '/../database/tetris_scores.db';

if (!file_exists($dbPath)) {
    http_response_code(500);
    echo json_encode(['error' => 'Database not found']);
    exit;
}

try {
    $db = new PDO('sqlite:' . $dbPath);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => 'Database connection failed: ' . $e->getMessage()]);
    exit;
}

// Get action from URL or request
$inputData = [];
if ($_SERVER['REQUEST_METHOD'] === 'POST' && strpos($_SERVER['CONTENT_TYPE'] ?? '', 'application/json') !== false) {
    $rawInput = file_get_contents('php://input');
    $inputData = json_decode($rawInput, true) ?? [];
}

// Get action from multiple sources
$action = $_GET['action'] ?? $_POST['action'] ?? $inputData['action'] ?? '';

// For testing, output what we received if debug is requested
if (isset($_GET['debug'])) {
    echo json_encode([
        'action' => $action,
        'method' => $_SERVER['REQUEST_METHOD'],
        'content_type' => $_SERVER['CONTENT_TYPE'] ?? '',
        'inputData' => $inputData
    ]);
    exit;
}

switch ($action) {
    // ========================================
    // ANDROID COMPATIBILITY ENDPOINTS
    // ========================================
    
    case 'get_progression':
        // Android app uses this endpoint to get player progression
        $playerId = $_GET['player_id'] ?? $inputData['player_id'] ?? null;
        
        if (!$playerId) {
            http_response_code(400);
            echo json_encode(['error' => 'Player ID required']);
            exit;
        }
        
        try {
            // Get player data
            $stmt = $db->prepare("SELECT * FROM players WHERE id = ? OR username = ?");
            $stmt->execute([$playerId, $playerId]);
            $player = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if (!$player) {
                http_response_code(404);
                echo json_encode(['error' => 'Player not found']);
                exit;
            }
            
            // Get progression data
            $stmt = $db->prepare("SELECT * FROM player_progression WHERE player_id = ?");
            $stmt->execute([$player['id']]);
            $progression = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if (!$progression) {
                // Create default progression if not exists
                $stmt = $db->prepare("
                    INSERT INTO player_progression (player_id) VALUES (?)
                ");
                $stmt->execute([$player['id']]);
                
                $progression = [
                    'player_id' => $player['id'],
                    'level' => 1,
                    'current_xp' => 0,
                    'total_xp' => 0,
                    'rank' => 'Novice',
                    'unlocked_themes' => '["default"]',
                    'unlocked_music' => '["classic"]',
                    'unlocked_piece_styles' => '["neon"]',
                    'unlocked_effects' => '["basic"]'
                ];
            }
            
            // Get statistics
            $stmt = $db->prepare("
                SELECT game_mode, games_played, total_score, highest_score, total_lines, 
                       total_tspins, total_tetrises, perfect_clears, longest_combo
                FROM player_statistics 
                WHERE player_id = ?
            ");
            $stmt->execute([$player['id']]);
            $stats = $stmt->fetchAll(PDO::FETCH_ASSOC);
            
            // Get achievements
            $stmt = $db->prepare("
                SELECT a.code, a.name, pa.unlocked, pa.progress
                FROM achievements a
                LEFT JOIN player_achievements pa ON a.id = pa.achievement_id AND pa.player_id = ?
                WHERE pa.unlocked = 1
            ");
            $stmt->execute([$player['id']]);
            $achievements = $stmt->fetchAll(PDO::FETCH_ASSOC);
            
            // Build response
            $response = [
                'success' => true,
                'level' => (int)($progression['level'] ?? $player['level']),
                'current_xp' => (int)($progression['current_xp'] ?? $player['current_xp']),
                'total_xp' => (int)($progression['total_xp'] ?? $player['total_xp']),
                'rank' => $progression['rank'] ?? $player['rank'],
                'statistics' => [
                    'total_games_played' => (int)$player['games_played'],
                    'total_score' => (int)$player['total_score'],
                    'total_lines' => (int)$player['total_lines'],
                    'highest_score' => (int)$player['highest_score'],
                    'total_tspins' => (int)$player['total_tspins'],
                    'total_tetrises' => (int)$player['total_tetris'],
                    'perfect_clears' => (int)$player['perfect_clears'],
                    'longest_combo' => (int)$player['highest_combo'],
                    'total_play_time' => (int)$player['total_time'],
                    'favorite_mode' => $player['favorite_mode'] ?? 'Classic',
                    'mode_stats' => $stats
                ],
                'unlocks' => [
                    'themes' => json_decode($progression['unlocked_themes'] ?? '["default"]'),
                    'music' => json_decode($progression['unlocked_music'] ?? '["classic"]'),
                    'piece_styles' => json_decode($progression['unlocked_piece_styles'] ?? '["neon"]'),
                    'effects' => json_decode($progression['unlocked_effects'] ?? '["basic"]')
                ],
                'achievements' => $achievements,
                'last_sync' => $progression['last_sync'] ?? null
            ];
            
            echo json_encode($response);
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
        }
        break;
        
    case 'sync_progression':
        // Android app uses this to sync progression data
        $data = $inputData ?? [];
        $playerId = $data['player_id'] ?? null;
        
        if (!$playerId) {
            http_response_code(400);
            echo json_encode(['error' => 'Player ID required']);
            exit;
        }
        
        try {
            $db->beginTransaction();
            
            // Get player
            $stmt = $db->prepare("SELECT id FROM players WHERE id = ? OR username = ?");
            $stmt->execute([$playerId, $playerId]);
            $player = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if (!$player) {
                $db->rollBack();
                http_response_code(404);
                echo json_encode(['error' => 'Player not found']);
                exit;
            }
            
            $playerIdNum = $player['id'];
            
            // Update player table
            if (isset($data['level']) || isset($data['current_xp']) || isset($data['total_xp'])) {
                $updates = [];
                $params = [];
                
                if (isset($data['level'])) {
                    $updates[] = "level = ?";
                    $params[] = $data['level'];
                }
                if (isset($data['current_xp'])) {
                    $updates[] = "current_xp = ?";
                    $params[] = $data['current_xp'];
                }
                if (isset($data['total_xp'])) {
                    $updates[] = "total_xp = ?";
                    $params[] = $data['total_xp'];
                }
                if (isset($data['rank'])) {
                    $updates[] = "rank = ?";
                    $params[] = $data['rank'];
                }
                
                if (!empty($updates)) {
                    $params[] = $playerIdNum;
                    $stmt = $db->prepare("UPDATE players SET " . implode(', ', $updates) . " WHERE id = ?");
                    $stmt->execute($params);
                }
            }
            
            // Update or create progression record
            $stmt = $db->prepare("SELECT player_id FROM player_progression WHERE player_id = ?");
            $stmt->execute([$playerIdNum]);
            $exists = $stmt->fetch();
            
            if ($exists) {
                // Update existing
                $stmt = $db->prepare("
                    UPDATE player_progression SET
                        level = ?, current_xp = ?, total_xp = ?, rank = ?,
                        unlocked_themes = ?, unlocked_music = ?, 
                        unlocked_piece_styles = ?, unlocked_effects = ?,
                        achievement_progress = ?, total_games_played = ?,
                        total_play_time = ?, favorite_mode = ?,
                        daily_streak = ?, daily_challenges_completed = ?,
                        last_sync = CURRENT_TIMESTAMP
                    WHERE player_id = ?
                ");
            } else {
                // Insert new
                $stmt = $db->prepare("
                    INSERT INTO player_progression (
                        level, current_xp, total_xp, rank,
                        unlocked_themes, unlocked_music, 
                        unlocked_piece_styles, unlocked_effects,
                        achievement_progress, total_games_played,
                        total_play_time, favorite_mode,
                        daily_streak, daily_challenges_completed,
                        last_sync, player_id
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)
                ");
            }
            
            $stmt->execute([
                $data['level'] ?? 1,
                $data['current_xp'] ?? 0,
                $data['total_xp'] ?? 0,
                $data['rank'] ?? 'Novice',
                json_encode($data['unlocked_themes'] ?? ['default']),
                json_encode($data['unlocked_music'] ?? ['classic']),
                json_encode($data['unlocked_piece_styles'] ?? ['neon']),
                json_encode($data['unlocked_effects'] ?? ['basic']),
                json_encode($data['achievement_progress'] ?? []),
                $data['total_games_played'] ?? 0,
                $data['total_play_time'] ?? 0,
                $data['favorite_mode'] ?? 'Classic',
                $data['daily_streak'] ?? 0,
                $data['daily_challenges_completed'] ?? 0,
                $playerIdNum
            ]);
            
            // Update statistics if provided
            if (isset($data['statistics']) && is_array($data['statistics'])) {
                $stats = $data['statistics'];
                
                // Update overall stats in players table
                $stmt = $db->prepare("
                    UPDATE players SET
                        games_played = ?, total_score = ?, total_lines = ?,
                        total_time = ?, highest_score = ?, highest_combo = ?,
                        total_tspins = ?, total_tetris = ?, perfect_clears = ?
                    WHERE id = ?
                ");
                $stmt->execute([
                    $stats['total_games_played'] ?? 0,
                    $stats['total_score'] ?? 0,
                    $stats['total_lines'] ?? 0,
                    $stats['total_play_time'] ?? 0,
                    $stats['highest_score'] ?? 0,
                    $stats['longest_combo'] ?? 0,
                    $stats['total_tspins'] ?? 0,
                    $stats['total_tetrises'] ?? 0,
                    $stats['perfect_clears'] ?? 0,
                    $playerIdNum
                ]);
            }
            
            // Log the sync
            $stmt = $db->prepare("
                INSERT INTO player_sync_log 
                (player_id, sync_type, sync_direction, sync_status, data_synced, completed_at)
                VALUES (?, 'progression', 'upload', 'success', ?, CURRENT_TIMESTAMP)
            ");
            $stmt->execute([$playerIdNum, json_encode($data)]);
            
            $db->commit();
            
            echo json_encode([
                'success' => true,
                'message' => 'Progression synced successfully',
                'player_id' => $playerIdNum
            ]);
            
        } catch (PDOException $e) {
            $db->rollBack();
            http_response_code(500);
            echo json_encode(['error' => 'Sync failed: ' . $e->getMessage()]);
        }
        break;
        
    // ========================================
    // PLAYER ENDPOINTS
    // ========================================
    
    case 'get_player':
        $playerId = $_GET['id'] ?? $_GET['player_id'] ?? null;
        $username = $_GET['username'] ?? null;
        
        if (!$playerId && !$username) {
            http_response_code(400);
            echo json_encode(['error' => 'Player ID or username required']);
            exit;
        }
        
        $sql = $playerId 
            ? "SELECT * FROM players WHERE id = :param"
            : "SELECT * FROM players WHERE username = :param";
            
        $stmt = $db->prepare($sql);
        $stmt->execute(['param' => $playerId ?? $username]);
        $player = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$player) {
            http_response_code(404);
            echo json_encode(['error' => 'Player not found']);
            exit;
        }
        
        // Get additional stats
        $player['achievements_count'] = $db->query(
            "SELECT COUNT(*) FROM player_achievements 
             WHERE player_id = {$player['id']} AND unlocked = 1"
        )->fetchColumn();
        
        $player['unlocks_count'] = $db->query(
            "SELECT COUNT(*) FROM player_unlocks WHERE player_id = {$player['id']}"
        )->fetchColumn();
        
        echo json_encode($player);
        break;
        
    case 'create_player':
        $data = $inputData ?? [];
        $username = $data['username'] ?? '';
        $displayName = $data['display_name'] ?? $username;
        $email = $data['email'] ?? null;
        
        if (strlen($username) < 3 || strlen($username) > 50) {
            http_response_code(400);
            echo json_encode(['error' => 'Username must be 3-50 characters']);
            exit;
        }
        
        try {
            $stmt = $db->prepare(
                "INSERT INTO players (username, display_name, email) 
                 VALUES (:username, :display_name, :email)"
            );
            $stmt->execute([
                'username' => strtolower(preg_replace('/[^a-zA-Z0-9_]/', '', $username)),
                'display_name' => $displayName,
                'email' => $email
            ]);
            
            $playerId = $db->lastInsertId();
            
            // Grant default unlockables
            $db->exec(
                "INSERT INTO player_unlocks (player_id, unlockable_id, equipped)
                 SELECT $playerId, id, is_default FROM unlockables WHERE is_default = 1"
            );
            
            echo json_encode(['success' => true, 'player_id' => $playerId]);
        } catch (PDOException $e) {
            http_response_code(400);
            echo json_encode(['error' => 'Username already exists']);
        }
        break;
        
    case 'update_player_stats':
        $data = $inputData ?? [];
        $playerId = $data['player_id'] ?? null;
        
        if (!$playerId) {
            http_response_code(400);
            echo json_encode(['error' => 'Player ID required']);
            exit;
        }
        
        // Build dynamic update query based on provided fields
        $updates = [];
        $params = ['id' => $playerId];
        
        $allowedFields = [
            'games_played', 'total_score', 'total_lines', 'total_time',
            'highest_score', 'highest_combo', 'total_tspins', 'total_tetris',
            'perfect_clears', 'favorite_mode'
        ];
        
        foreach ($allowedFields as $field) {
            if (isset($data[$field])) {
                $updates[] = "$field = :$field";
                $params[$field] = $data[$field];
            }
        }
        
        if (empty($updates)) {
            echo json_encode(['success' => true, 'message' => 'No updates provided']);
            exit;
        }
        
        $sql = "UPDATE players SET " . implode(', ', $updates) . 
               ", last_game = CURRENT_TIMESTAMP WHERE id = :id";
        $stmt = $db->prepare($sql);
        $stmt->execute($params);
        
        echo json_encode(['success' => true]);
        break;
        
    // ========================================
    // XP & PROGRESSION ENDPOINTS
    // ========================================
    
    case 'add_xp':
        $data = $inputData ?? [];
        $playerId = $data['player_id'] ?? null;
        $amount = $data['amount'] ?? 0;
        $source = $data['source'] ?? 'gameplay';
        $description = $data['description'] ?? '';
        
        if (!$playerId || $amount <= 0) {
            http_response_code(400);
            echo json_encode(['error' => 'Valid player ID and XP amount required']);
            exit;
        }
        
        // Get current player data
        $stmt = $db->prepare("SELECT level, current_xp, total_xp FROM players WHERE id = ?");
        $stmt->execute([$playerId]);
        $player = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$player) {
            http_response_code(404);
            echo json_encode(['error' => 'Player not found']);
            exit;
        }
        
        // Calculate new XP and level
        $newTotalXP = $player['total_xp'] + $amount;
        $newCurrentXP = $player['current_xp'] + $amount;
        $newLevel = $player['level'];
        $leveledUp = false;
        
        // Check for level up (1000 XP per level)
        while ($newCurrentXP >= 1000 && $newLevel < 100) {
            $newCurrentXP -= 1000;
            $newLevel++;
            $leveledUp = true;
        }
        
        // Update player
        $stmt = $db->prepare(
            "UPDATE players SET 
             current_xp = ?, total_xp = ?, level = ?,
             rank = CASE 
                WHEN ? >= 90 THEN 'Eternal'
                WHEN ? >= 80 THEN 'Titan'
                WHEN ? >= 70 THEN 'Myth'
                WHEN ? >= 60 THEN 'Legend'
                WHEN ? >= 50 THEN 'Grandmaster'
                WHEN ? >= 40 THEN 'Master'
                WHEN ? >= 30 THEN 'Expert'
                WHEN ? >= 20 THEN 'Adept'
                WHEN ? >= 10 THEN 'Apprentice'
                ELSE 'Novice'
             END
             WHERE id = ?"
        );
        $stmt->execute([
            $newCurrentXP, $newTotalXP, $newLevel,
            $newLevel, $newLevel, $newLevel, $newLevel, $newLevel,
            $newLevel, $newLevel, $newLevel, $newLevel,
            $playerId
        ]);
        
        // Log XP transaction
        $stmt = $db->prepare(
            "INSERT INTO xp_transactions (player_id, amount, source, description)
             VALUES (?, ?, ?, ?)"
        );
        $stmt->execute([$playerId, $amount, $source, $description]);
        
        echo json_encode([
            'success' => true,
            'new_level' => $newLevel,
            'new_xp' => $newCurrentXP,
            'total_xp' => $newTotalXP,
            'leveled_up' => $leveledUp
        ]);
        break;
        
    // ========================================
    // ACHIEVEMENTS ENDPOINTS
    // ========================================
    
    case 'get_achievements':
        $playerId = $_GET['player_id'] ?? null;
        
        $sql = "SELECT a.*, 
                COALESCE(pa.progress, 0) as progress,
                COALESCE(pa.unlocked, 0) as unlocked,
                pa.unlocked_at
                FROM achievements a
                LEFT JOIN player_achievements pa 
                ON a.id = pa.achievement_id AND pa.player_id = :player_id
                ORDER BY a.category, a.sort_order";
                
        $stmt = $db->prepare($sql);
        $stmt->execute(['player_id' => $playerId]);
        $achievements = $stmt->fetchAll(PDO::FETCH_ASSOC);
        
        echo json_encode($achievements);
        break;
        
    case 'unlock_achievement':
        $data = $inputData ?? [];
        $playerId = $data['player_id'] ?? null;
        $achievementCode = $data['achievement_code'] ?? null;
        $progress = $data['progress'] ?? 1.0;
        
        if (!$playerId || !$achievementCode) {
            http_response_code(400);
            echo json_encode(['error' => 'Player ID and achievement code required']);
            exit;
        }
        
        // Get achievement
        $stmt = $db->prepare("SELECT * FROM achievements WHERE code = ?");
        $stmt->execute([$achievementCode]);
        $achievement = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$achievement) {
            http_response_code(404);
            echo json_encode(['error' => 'Achievement not found']);
            exit;
        }
        
        // Check if already unlocked
        $stmt = $db->prepare(
            "SELECT unlocked FROM player_achievements 
             WHERE player_id = ? AND achievement_id = ?"
        );
        $stmt->execute([$playerId, $achievement['id']]);
        $existing = $stmt->fetch(PDO::FETCH_ASSOC);
        
        $unlocked = $progress >= 1.0;
        $isNew = false;
        
        if ($existing) {
            if (!$existing['unlocked'] && $unlocked) {
                // Newly unlocked
                $stmt = $db->prepare(
                    "UPDATE player_achievements 
                     SET progress = ?, unlocked = ?, unlocked_at = CURRENT_TIMESTAMP
                     WHERE player_id = ? AND achievement_id = ?"
                );
                $stmt->execute([$progress, $unlocked, $playerId, $achievement['id']]);
                $isNew = true;
            } else {
                // Update progress only
                $stmt = $db->prepare(
                    "UPDATE player_achievements SET progress = ?
                     WHERE player_id = ? AND achievement_id = ?"
                );
                $stmt->execute([$progress, $playerId, $achievement['id']]);
            }
        } else {
            // Insert new
            $stmt = $db->prepare(
                "INSERT INTO player_achievements 
                 (player_id, achievement_id, progress, unlocked, unlocked_at)
                 VALUES (?, ?, ?, ?, ?)"
            );
            $stmt->execute([
                $playerId, $achievement['id'], $progress, $unlocked,
                $unlocked ? date('Y-m-d H:i:s') : null
            ]);
            $isNew = $unlocked;
        }
        
        // Award XP if newly unlocked
        if ($isNew && $achievement['xp_reward']) {
            $stmt = $db->prepare(
                "UPDATE players SET 
                 current_xp = current_xp + ?,
                 total_xp = total_xp + ?
                 WHERE id = ?"
            );
            $stmt->execute([$achievement['xp_reward'], $achievement['xp_reward'], $playerId]);
        }
        
        echo json_encode([
            'success' => true,
            'unlocked' => $unlocked,
            'is_new' => $isNew,
            'xp_reward' => $isNew ? $achievement['xp_reward'] : 0
        ]);
        break;
        
    // ========================================
    // DAILY CHALLENGE ENDPOINTS
    // ========================================
    
    case 'get_daily_challenge':
        $date = $_GET['date'] ?? date('Y-m-d');
        
        $stmt = $db->prepare("SELECT * FROM daily_challenges WHERE date = ?");
        $stmt->execute([$date]);
        $challenge = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$challenge) {
            // Generate new daily challenge
            $seed = crc32($date);
            $modes = ['classic', 'sprint', 'marathon'];
            $goalTypes = ['score', 'lines', 'time', 'survival'];
            $modifiers = [
                'invisible_pieces', '2x_speed', 'no_hold', 'earthquake',
                'mirror_mode', 'random_garbage', 'mini_pieces', 'fog_of_war'
            ];
            
            // Random but deterministic based on date
            srand($seed);
            $selectedMode = $modes[rand(0, count($modes) - 1)];
            $selectedGoal = $goalTypes[rand(0, count($goalTypes) - 1)];
            
            // Select 1-3 random modifiers
            $numModifiers = rand(1, 3);
            shuffle($modifiers);
            $selectedModifiers = array_slice($modifiers, 0, $numModifiers);
            
            $goalValue = $selectedGoal === 'score' ? rand(20000, 100000) :
                        ($selectedGoal === 'lines' ? rand(20, 50) :
                        ($selectedGoal === 'time' ? rand(60, 300) : rand(120, 300)));
            
            $stmt = $db->prepare(
                "INSERT INTO daily_challenges 
                 (date, seed, mode, goal_type, goal_value, modifiers, xp_reward)
                 VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            $stmt->execute([
                $date, $seed, $selectedMode, $selectedGoal, $goalValue,
                json_encode($selectedModifiers), 500
            ]);
            
            $challenge = [
                'id' => $db->lastInsertId(),
                'date' => $date,
                'seed' => $seed,
                'mode' => $selectedMode,
                'goal_type' => $selectedGoal,
                'goal_value' => $goalValue,
                'modifiers' => json_encode($selectedModifiers),
                'xp_reward' => 500
            ];
        }
        
        // Get player's attempt if logged in
        $playerId = $_GET['player_id'] ?? null;
        if ($playerId) {
            $stmt = $db->prepare(
                "SELECT * FROM player_challenges 
                 WHERE player_id = ? AND challenge_id = ?"
            );
            $stmt->execute([$playerId, $challenge['id']]);
            $challenge['player_attempt'] = $stmt->fetch(PDO::FETCH_ASSOC);
        }
        
        echo json_encode($challenge);
        break;
        
    case 'submit_challenge':
        $data = $inputData ?? [];
        $playerId = $data['player_id'] ?? null;
        $challengeId = $data['challenge_id'] ?? null;
        $score = $data['score'] ?? 0;
        $completed = $data['completed'] ?? false;
        $completionTime = $data['completion_time'] ?? null;
        
        if (!$playerId || !$challengeId) {
            http_response_code(400);
            echo json_encode(['error' => 'Player ID and challenge ID required']);
            exit;
        }
        
        // Check if already attempted
        $stmt = $db->prepare(
            "SELECT * FROM player_challenges 
             WHERE player_id = ? AND challenge_id = ?"
        );
        $stmt->execute([$playerId, $challengeId]);
        $existing = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if ($existing) {
            // Update if better score
            if ($score > $existing['score']) {
                $stmt = $db->prepare(
                    "UPDATE player_challenges 
                     SET score = ?, completed = ?, completion_time = ?, 
                         attempts = attempts + 1, completed_at = CURRENT_TIMESTAMP
                     WHERE player_id = ? AND challenge_id = ?"
                );
                $stmt->execute([$score, $completed, $completionTime, $playerId, $challengeId]);
            }
        } else {
            // Insert new attempt
            $stmt = $db->prepare(
                "INSERT INTO player_challenges 
                 (player_id, challenge_id, completed, score, completion_time, completed_at)
                 VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)"
            );
            $stmt->execute([$playerId, $challengeId, $completed, $score, $completionTime]);
            
            // Award XP if completed
            if ($completed) {
                $stmt = $db->prepare("SELECT xp_reward FROM daily_challenges WHERE id = ?");
                $stmt->execute([$challengeId]);
                $xpReward = $stmt->fetchColumn();
                
                if ($xpReward) {
                    $stmt = $db->prepare(
                        "UPDATE players SET 
                         current_xp = current_xp + ?,
                         total_xp = total_xp + ?,
                         daily_streak = daily_streak + 1,
                         last_daily_date = DATE('now')
                         WHERE id = ?"
                    );
                    $stmt->execute([$xpReward, $xpReward, $playerId]);
                }
            }
        }
        
        echo json_encode(['success' => true]);
        break;
        
    // ========================================
    // UNLOCKABLES ENDPOINTS
    // ========================================
    
    case 'get_unlockables':
        $playerId = $_GET['player_id'] ?? null;
        $type = $_GET['type'] ?? null;
        
        $sql = "SELECT u.*, 
                COALESCE(pu.unlocked_at IS NOT NULL, 0) as unlocked,
                COALESCE(pu.equipped, 0) as equipped,
                COALESCE(pu.is_new, 0) as is_new
                FROM unlockables u
                LEFT JOIN player_unlocks pu 
                ON u.id = pu.unlockable_id AND pu.player_id = :player_id";
                
        if ($type) {
            $sql .= " WHERE u.type = :type";
        }
        
        $sql .= " ORDER BY u.type, u.sort_order";
        
        $stmt = $db->prepare($sql);
        $params = ['player_id' => $playerId];
        if ($type) {
            $params['type'] = $type;
        }
        $stmt->execute($params);
        $unlockables = $stmt->fetchAll(PDO::FETCH_ASSOC);
        
        echo json_encode($unlockables);
        break;
        
    case 'unlock_item':
        $data = $inputData ?? [];
        $playerId = $data['player_id'] ?? null;
        $unlockableCode = $data['unlockable_code'] ?? null;
        
        if (!$playerId || !$unlockableCode) {
            http_response_code(400);
            echo json_encode(['error' => 'Player ID and unlockable code required']);
            exit;
        }
        
        // Get unlockable
        $stmt = $db->prepare("SELECT * FROM unlockables WHERE code = ?");
        $stmt->execute([$unlockableCode]);
        $unlockable = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$unlockable) {
            http_response_code(404);
            echo json_encode(['error' => 'Unlockable not found']);
            exit;
        }
        
        // Check if already unlocked
        $stmt = $db->prepare(
            "SELECT * FROM player_unlocks 
             WHERE player_id = ? AND unlockable_id = ?"
        );
        $stmt->execute([$playerId, $unlockable['id']]);
        
        if (!$stmt->fetch()) {
            // Unlock item
            $stmt = $db->prepare(
                "INSERT INTO player_unlocks (player_id, unlockable_id)
                 VALUES (?, ?)"
            );
            $stmt->execute([$playerId, $unlockable['id']]);
        }
        
        echo json_encode(['success' => true]);
        break;
        
    case 'equip_item':
        $data = $inputData ?? [];
        $playerId = $data['player_id'] ?? null;
        $unlockableCode = $data['unlockable_code'] ?? null;
        
        if (!$playerId || !$unlockableCode) {
            http_response_code(400);
            echo json_encode(['error' => 'Player ID and unlockable code required']);
            exit;
        }
        
        // Get unlockable
        $stmt = $db->prepare("SELECT * FROM unlockables WHERE code = ?");
        $stmt->execute([$unlockableCode]);
        $unlockable = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$unlockable) {
            http_response_code(404);
            echo json_encode(['error' => 'Unlockable not found']);
            exit;
        }
        
        // Unequip other items of same type
        $stmt = $db->prepare(
            "UPDATE player_unlocks 
             SET equipped = 0 
             WHERE player_id = ? AND unlockable_id IN 
             (SELECT id FROM unlockables WHERE type = ?)"
        );
        $stmt->execute([$playerId, $unlockable['type']]);
        
        // Equip this item
        $stmt = $db->prepare(
            "UPDATE player_unlocks 
             SET equipped = 1, is_new = 0
             WHERE player_id = ? AND unlockable_id = ?"
        );
        $stmt->execute([$playerId, $unlockable['id']]);
        
        // Update player's current selection
        $columnMap = [
            'theme' => 'current_theme',
            'music' => 'current_music',
            'piece_style' => 'current_piece_style',
            'background' => 'current_background'
        ];
        
        if (isset($columnMap[$unlockable['type']])) {
            $column = $columnMap[$unlockable['type']];
            $stmt = $db->prepare("UPDATE players SET $column = ? WHERE id = ?");
            $stmt->execute([$unlockable['code'], $playerId]);
        }
        
        echo json_encode(['success' => true]);
        break;
        
    // ========================================
    // LEADERBOARDS
    // ========================================
    
    case 'get_leaderboard':
        $type = $_GET['type'] ?? 'xp'; // xp, score, daily
        $limit = min(100, $_GET['limit'] ?? 50);
        
        switch ($type) {
            case 'xp':
                $sql = "SELECT p.username, p.display_name, p.level, p.rank, p.total_xp,
                        COUNT(DISTINCT pa.achievement_id) as achievements
                        FROM players p
                        LEFT JOIN player_achievements pa ON p.id = pa.player_id AND pa.unlocked = 1
                        GROUP BY p.id
                        ORDER BY p.total_xp DESC
                        LIMIT :limit";
                break;
                
            case 'score':
                $sql = "SELECT p.display_name, hs.score, hs.lines, hs.level, hs.date_achieved
                        FROM high_scores hs
                        LEFT JOIN players p ON hs.player_id = p.id
                        ORDER BY hs.score DESC
                        LIMIT :limit";
                break;
                
            case 'daily':
                $sql = "SELECT p.display_name, pc.score, pc.completion_time
                        FROM player_challenges pc
                        JOIN players p ON pc.player_id = p.id
                        JOIN daily_challenges dc ON pc.challenge_id = dc.id
                        WHERE dc.date = DATE('now') AND pc.completed = 1
                        ORDER BY pc.score DESC
                        LIMIT :limit";
                break;
                
            default:
                http_response_code(400);
                echo json_encode(['error' => 'Invalid leaderboard type']);
                exit;
        }
        
        $stmt = $db->prepare($sql);
        $stmt->execute(['limit' => $limit]);
        $leaderboard = $stmt->fetchAll(PDO::FETCH_ASSOC);
        
        echo json_encode($leaderboard);
        break;
        
    // ========================================
    // GUEST DATA MIGRATION
    // ========================================
    
    case 'migrate_guest_data':
        // Get authorization header
        $headers = getallheaders();
        $authHeader = $headers['Authorization'] ?? '';
        
        if (!$authHeader || !preg_match('/Bearer\s+(.*)$/i', $authHeader, $matches)) {
            http_response_code(401);
            echo json_encode(['error' => 'Authentication required']);
            exit;
        }
        
        $authToken = $matches[1];
        
        // Verify authentication token
        $stmt = $db->prepare("SELECT id, username FROM players WHERE auth_token = ?");
        $stmt->execute([$authToken]);
        $player = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$player) {
            http_response_code(401);
            echo json_encode(['error' => 'Invalid authentication token']);
            exit;
        }
        
        // Get guest data from request
        $input = $inputData ?? [];
        $guestData = $input['guest_data'] ?? null;
        
        if (!$guestData) {
            http_response_code(400);
            echo json_encode(['error' => 'No guest data provided']);
            exit;
        }
        
        try {
            // Extract player data from guest
            $playerData = $guestData['playerData'] ?? [];
            
            // Update player stats if guest had progress
            if ($playerData) {
                $stmt = $db->prepare("
                    UPDATE players 
                    SET 
                        total_xp = total_xp + :xp,
                        games_played = games_played + :games,
                        total_score = total_score + :score,
                        total_lines = total_lines + :lines,
                        total_time = total_time + :time,
                        total_tspins = total_tspins + :tspins,
                        total_tetrises = total_tetrises + :tetris
                    WHERE id = :id
                ");
                
                $stmt->execute([
                    ':xp' => $playerData['total_xp'] ?? 0,
                    ':games' => $playerData['games_played'] ?? 0,
                    ':score' => $playerData['total_score'] ?? 0,
                    ':lines' => $playerData['total_lines'] ?? 0,
                    ':time' => $playerData['total_time'] ?? 0,
                    ':tspins' => $playerData['total_tspins'] ?? 0,
                    ':tetris' => $playerData['total_tetrises'] ?? $playerData['total_tetris'] ?? 0,
                    ':id' => $player['id']
                ]);
                
                // Recalculate level based on new total XP
                $stmt = $db->prepare("SELECT total_xp FROM players WHERE id = ?");
                $stmt->execute([$player['id']]);
                $result = $stmt->fetch(PDO::FETCH_ASSOC);
                $totalXP = $result['total_xp'];
                
                // Calculate level (same formula as frontend)
                $level = 1;
                $xpNeeded = 0;
                while ($xpNeeded <= $totalXP && $level < 100) {
                    $xpNeeded += floor(100 * pow(1.5, $level - 1));
                    if ($xpNeeded <= $totalXP) {
                        $level++;
                    }
                }
                
                // Calculate current XP for this level
                $xpForCurrentLevel = 0;
                for ($i = 1; $i < $level; $i++) {
                    $xpForCurrentLevel += floor(100 * pow(1.5, $i - 1));
                }
                $currentXP = $totalXP - $xpForCurrentLevel;
                
                // Update level and current XP
                $stmt = $db->prepare("
                    UPDATE players 
                    SET level = :level, current_xp = :current_xp
                    WHERE id = :id
                ");
                $stmt->execute([
                    ':level' => $level,
                    ':current_xp' => $currentXP,
                    ':id' => $player['id']
                ]);
            }
            
            // Handle achievements if present
            $achievements = $guestData['achievements'] ?? [];
            if (!empty($achievements)) {
                foreach ($achievements as $achievement) {
                    // Check if achievement exists
                    $stmt = $db->prepare("SELECT id FROM achievements WHERE code = ?");
                    $stmt->execute([$achievement['code'] ?? '']);
                    $achId = $stmt->fetchColumn();
                    
                    if ($achId) {
                        // Insert or update player achievement
                        $stmt = $db->prepare("
                            INSERT OR REPLACE INTO player_achievements 
                            (player_id, achievement_id, progress, unlocked, unlocked_at)
                            VALUES (?, ?, ?, ?, ?)
                        ");
                        $stmt->execute([
                            $player['id'],
                            $achId,
                            $achievement['progress'] ?? 0,
                            $achievement['unlocked'] ?? 0,
                            $achievement['unlocked_at'] ?? date('Y-m-d H:i:s')
                        ]);
                    }
                }
            }
            
            // Handle unlockables if present
            $unlockables = $guestData['unlockables'] ?? [];
            if (!empty($unlockables)) {
                foreach ($unlockables as $unlock) {
                    if ($unlock['unlocked'] ?? false) {
                        // Check if unlockable exists
                        $stmt = $db->prepare("SELECT id FROM unlockables WHERE code = ?");
                        $stmt->execute([$unlock['code'] ?? '']);
                        $unlockId = $stmt->fetchColumn();
                        
                        if ($unlockId) {
                            // Insert or update player unlock
                            $stmt = $db->prepare("
                                INSERT OR IGNORE INTO player_unlocks 
                                (player_id, unlockable_id, unlocked_at, equipped)
                                VALUES (?, ?, ?, ?)
                            ");
                            $stmt->execute([
                                $player['id'],
                                $unlockId,
                                date('Y-m-d H:i:s'),
                                $unlock['equipped'] ?? 0
                            ]);
                        }
                    }
                }
            }
            
            echo json_encode([
                'success' => true,
                'message' => 'Guest data migrated successfully',
                'player_id' => $player['id']
            ]);
            
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Migration failed: ' . $e->getMessage()]);
        }
        break;
        
    default:
        http_response_code(400);
        echo json_encode(['error' => 'Invalid action']);
        break;
}
?>