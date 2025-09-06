<?php
/**
 * Tetris High Scores API
 * Handles score saving and leaderboard retrieval
 */

header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type');

// Handle preflight requests
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    exit(0);
}

class ScoreManager {
    private $db;
    
    public function __construct() {
        $dbPath = dirname(__DIR__) . '/database/tetris_scores.db';
        
        try {
            $this->db = new PDO('sqlite:' . $dbPath);
            $this->db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            $this->db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            $this->sendError('Database connection failed: ' . $e->getMessage());
        }
    }
    
    public function handleRequest() {
        $method = $_SERVER['REQUEST_METHOD'];
        $action = $_GET['action'] ?? '';
        
        switch ($method) {
            case 'GET':
                $this->handleGet($action);
                break;
            case 'POST':
                $this->handlePost($action);
                break;
            default:
                $this->sendError('Method not allowed', 405);
        }
    }
    
    private function handleGet($action) {
        switch ($action) {
            case 'leaderboard':
                $this->getLeaderboard();
                break;
            case 'recent':
                $this->getRecentScores();
                break;
            case 'player':
                $playerName = $_GET['name'] ?? '';
                $this->getPlayerScores($playerName);
                break;
            case 'stats':
                $this->getGlobalStats();
                break;
            default:
                $this->sendError('Invalid action');
        }
    }
    
    private function handlePost($action) {
        switch ($action) {
            case 'save':
                $this->saveScore();
                break;
            default:
                $this->sendError('Invalid action');
        }
    }
    
    private function saveScore() {
        $input = json_decode(file_get_contents('php://input'), true);
        
        // Validate input
        $requiredFields = ['playerName', 'score', 'lines', 'level', 'gameDuration'];
        foreach ($requiredFields as $field) {
            if (!isset($input[$field])) {
                $this->sendError("Missing required field: $field");
                return;
            }
        }
        
        // Sanitize and validate data
        $playerName = $this->sanitizePlayerName($input['playerName']);
        $score = (int) $input['score'];
        $lines = (int) $input['lines'];
        $level = (int) $input['level'];
        $gameDuration = (int) $input['gameDuration'];
        $specialAchievements = json_encode($input['specialAchievements'] ?? []);
        
        // Validate ranges
        if ($score < 0 || $lines < 0 || $level < 1 || $gameDuration < 0) {
            $this->sendError('Invalid score data');
            return;
        }
        
        if (empty($playerName)) {
            $this->sendError('Player name is required and must be 1-50 characters');
            return;
        }
        
        try {
            $stmt = $this->db->prepare("
                INSERT INTO high_scores 
                (player_name, score, lines, level, game_duration, special_achievements)
                VALUES (?, ?, ?, ?, ?, ?)
            ");
            
            $result = $stmt->execute([
                $playerName, $score, $lines, $level, $gameDuration, $specialAchievements
            ]);
            
            if ($result) {
                $scoreId = $this->db->lastInsertId();
                
                // Get the rank of this score
                $rankStmt = $this->db->prepare("
                    SELECT COUNT(*) + 1 as rank 
                    FROM high_scores 
                    WHERE score > ?
                ");
                $rankStmt->execute([$score]);
                $rank = $rankStmt->fetch()['rank'];
                
                $this->sendSuccess([
                    'id' => $scoreId,
                    'rank' => $rank,
                    'message' => 'Score saved successfully!'
                ]);
            } else {
                $this->sendError('Failed to save score');
            }
        } catch (PDOException $e) {
            $this->sendError('Database error: ' . $e->getMessage());
        }
    }
    
    private function getLeaderboard() {
        $limit = (int) ($_GET['limit'] ?? 50);
        $limit = min(max($limit, 1), 100); // Between 1 and 100
        
        try {
            $stmt = $this->db->prepare("
                SELECT 
                    ROW_NUMBER() OVER (ORDER BY score DESC) as rank,
                    player_name,
                    score,
                    lines,
                    level,
                    game_duration,
                    datetime(date_achieved, 'localtime') as date_achieved,
                    special_achievements
                FROM high_scores
                ORDER BY score DESC
                LIMIT ?
            ");
            
            $stmt->execute([$limit]);
            $leaderboard = $stmt->fetchAll();
            
            // Parse special achievements JSON
            foreach ($leaderboard as &$entry) {
                $entry['special_achievements'] = json_decode($entry['special_achievements'] ?? '{}', true);
                $entry['score'] = (int) $entry['score'];
                $entry['lines'] = (int) $entry['lines'];
                $entry['level'] = (int) $entry['level'];
                $entry['game_duration'] = (int) $entry['game_duration'];
            }
            
            $this->sendSuccess($leaderboard);
        } catch (PDOException $e) {
            $this->sendError('Database error: ' . $e->getMessage());
        }
    }
    
    private function getRecentScores() {
        $limit = (int) ($_GET['limit'] ?? 20);
        $limit = min(max($limit, 1), 50); // Between 1 and 50
        
        try {
            $stmt = $this->db->prepare("
                SELECT 
                    player_name,
                    score,
                    lines,
                    level,
                    game_duration,
                    datetime(date_achieved, 'localtime') as date_achieved,
                    special_achievements
                FROM high_scores
                ORDER BY date_achieved DESC
                LIMIT ?
            ");
            
            $stmt->execute([$limit]);
            $recentScores = $stmt->fetchAll();
            
            // Parse special achievements JSON
            foreach ($recentScores as &$entry) {
                $entry['special_achievements'] = json_decode($entry['special_achievements'] ?? '{}', true);
                $entry['score'] = (int) $entry['score'];
                $entry['lines'] = (int) $entry['lines'];
                $entry['level'] = (int) $entry['level'];
                $entry['game_duration'] = (int) $entry['game_duration'];
            }
            
            $this->sendSuccess($recentScores);
        } catch (PDOException $e) {
            $this->sendError('Database error: ' . $e->getMessage());
        }
    }
    
    private function getPlayerScores($playerName) {
        if (empty($playerName)) {
            $this->sendError('Player name is required');
            return;
        }
        
        $playerName = $this->sanitizePlayerName($playerName);
        
        try {
            $stmt = $this->db->prepare("
                SELECT 
                    score,
                    lines,
                    level,
                    game_duration,
                    datetime(date_achieved, 'localtime') as date_achieved,
                    special_achievements,
                    (SELECT COUNT(*) + 1 FROM high_scores hs2 WHERE hs2.score > hs1.score) as rank
                FROM high_scores hs1
                WHERE LOWER(player_name) = LOWER(?)
                ORDER BY score DESC
                LIMIT 10
            ");
            
            $stmt->execute([$playerName]);
            $playerScores = $stmt->fetchAll();
            
            // Parse special achievements JSON and convert to integers
            foreach ($playerScores as &$entry) {
                $entry['special_achievements'] = json_decode($entry['special_achievements'] ?? '{}', true);
                $entry['score'] = (int) $entry['score'];
                $entry['lines'] = (int) $entry['lines'];
                $entry['level'] = (int) $entry['level'];
                $entry['game_duration'] = (int) $entry['game_duration'];
                $entry['rank'] = (int) $entry['rank'];
            }
            
            $this->sendSuccess([
                'player_name' => $playerName,
                'scores' => $playerScores
            ]);
        } catch (PDOException $e) {
            $this->sendError('Database error: ' . $e->getMessage());
        }
    }
    
    private function getGlobalStats() {
        try {
            $stmt = $this->db->query("
                SELECT 
                    COUNT(*) as total_games,
                    MAX(score) as highest_score,
                    ROUND(AVG(score)) as average_score,
                    SUM(lines) as total_lines_cleared,
                    MAX(level) as highest_level,
                    COUNT(DISTINCT player_name) as unique_players
                FROM high_scores
            ");
            
            $stats = $stmt->fetch();
            
            // Convert to integers
            foreach ($stats as $key => $value) {
                $stats[$key] = (int) $value;
            }
            
            $this->sendSuccess($stats);
        } catch (PDOException $e) {
            $this->sendError('Database error: ' . $e->getMessage());
        }
    }
    
    private function sanitizePlayerName($name) {
        // Remove extra whitespace and limit length
        $name = trim($name);
        $name = substr($name, 0, 50);
        
        // Remove potentially dangerous characters but keep unicode letters
        $name = preg_replace('/[<>"\']/', '', $name);
        
        return $name;
    }
    
    private function sendSuccess($data) {
        echo json_encode([
            'success' => true,
            'data' => $data
        ]);
        exit;
    }
    
    private function sendError($message, $code = 400) {
        http_response_code($code);
        echo json_encode([
            'success' => false,
            'error' => $message
        ]);
        exit;
    }
}

// Initialize and handle the request
try {
    $scoreManager = new ScoreManager();
    $scoreManager->handleRequest();
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode([
        'success' => false,
        'error' => 'Internal server error'
    ]);
}
?>