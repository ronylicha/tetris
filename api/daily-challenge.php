<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type');

// Handle preflight requests
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    exit(0);
}

// Database connection
$db_path = __DIR__ . '/../database/tetris_scores.db';

try {
    $db = new PDO('sqlite:' . $db_path);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => 'Database connection failed']);
    exit;
}

// Get request data
$input = json_decode(file_get_contents('php://input'), true);
$action = $input['action'] ?? $_GET['action'] ?? '';

switch ($action) {
    case 'submit':
        submitScore($db, $input);
        break;
    
    case 'getRank':
        getPlayerRank($db, $input);
        break;
    
    case 'getStats':
        getChallengeStats($db, $input);
        break;
    
    case 'getLeaderboard':
        getDailyLeaderboard($db, $input);
        break;
    
    default:
        http_response_code(400);
        echo json_encode(['error' => 'Invalid action']);
        break;
}

function submitScore($db, $data) {
    $challengeId = $data['challengeId'] ?? '';
    $playerName = $data['playerName'] ?? 'Anonymous';
    $score = $data['score'] ?? 0;
    $time = $data['time'] ?? 0;
    $stats = json_encode($data['stats'] ?? []);
    $date = date('Y-m-d');
    
    try {
        // Check if player already submitted today
        $checkStmt = $db->prepare("
            SELECT id FROM daily_challenge_scores 
            WHERE challenge_id = :challenge_id 
            AND player_name = :player_name 
            AND date = :date
        ");
        $checkStmt->execute([
            ':challenge_id' => $challengeId,
            ':player_name' => $playerName,
            ':date' => $date
        ]);
        
        if ($checkStmt->fetch()) {
            echo json_encode(['error' => 'Already submitted today', 'success' => false]);
            return;
        }
        
        // Insert new score
        $stmt = $db->prepare("
            INSERT INTO daily_challenge_scores 
            (challenge_id, player_name, score, time, stats, date, created_at) 
            VALUES 
            (:challenge_id, :player_name, :score, :time, :stats, :date, :created_at)
        ");
        
        $stmt->execute([
            ':challenge_id' => $challengeId,
            ':player_name' => $playerName,
            ':score' => $score,
            ':time' => $time,
            ':stats' => $stats,
            ':date' => $date,
            ':created_at' => date('Y-m-d H:i:s')
        ]);
        
        // Get player's rank
        $rank = getPlayerRankInternal($db, $challengeId, $score, $date);
        
        echo json_encode([
            'success' => true,
            'rank' => $rank,
            'message' => 'Score submitted successfully'
        ]);
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Failed to submit score']);
    }
}

function getPlayerRank($db, $data) {
    $challengeId = $data['challengeId'] ?? '';
    $score = $data['score'] ?? 0;
    $date = date('Y-m-d');
    
    $rank = getPlayerRankInternal($db, $challengeId, $score, $date);
    echo json_encode(['rank' => $rank]);
}

function getPlayerRankInternal($db, $challengeId, $score, $date) {
    try {
        $stmt = $db->prepare("
            SELECT COUNT(*) + 1 as rank 
            FROM daily_challenge_scores 
            WHERE challenge_id = :challenge_id 
            AND date = :date 
            AND score > :score
        ");
        
        $stmt->execute([
            ':challenge_id' => $challengeId,
            ':date' => $date,
            ':score' => $score
        ]);
        
        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        return $result['rank'] ?? 1;
    } catch (PDOException $e) {
        return null;
    }
}

function getChallengeStats($db, $data) {
    $challengeId = $data['challengeId'] ?? '';
    $date = date('Y-m-d');
    
    try {
        // Get aggregate stats
        $stmt = $db->prepare("
            SELECT 
                COUNT(*) as attempts,
                COUNT(CASE WHEN score > 0 THEN 1 END) as completions,
                AVG(time) as average_time,
                MIN(time) as best_time,
                MAX(score) as best_score
            FROM daily_challenge_scores 
            WHERE challenge_id = :challenge_id 
            AND date = :date
        ");
        
        $stmt->execute([
            ':challenge_id' => $challengeId,
            ':date' => $date
        ]);
        
        $stats = $stmt->fetch(PDO::FETCH_ASSOC);
        
        // Get top 10 players
        $topStmt = $db->prepare("
            SELECT player_name, score, time 
            FROM daily_challenge_scores 
            WHERE challenge_id = :challenge_id 
            AND date = :date 
            ORDER BY score DESC, time ASC 
            LIMIT 10
        ");
        
        $topStmt->execute([
            ':challenge_id' => $challengeId,
            ':date' => $date
        ]);
        
        $topPlayers = $topStmt->fetchAll(PDO::FETCH_ASSOC);
        
        echo json_encode([
            'attempts' => $stats['attempts'] ?? 0,
            'completions' => $stats['completions'] ?? 0,
            'averageTime' => round($stats['average_time'] ?? 0),
            'bestTime' => $stats['best_time'],
            'bestScore' => $stats['best_score'],
            'topPlayers' => $topPlayers
        ]);
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Failed to get stats']);
    }
}

function getDailyLeaderboard($db, $data) {
    $date = $data['date'] ?? date('Y-m-d');
    $limit = $data['limit'] ?? 50;
    
    try {
        $stmt = $db->prepare("
            SELECT 
                player_name,
                score,
                time,
                created_at
            FROM daily_challenge_scores 
            WHERE date = :date 
            ORDER BY score DESC, time ASC 
            LIMIT :limit
        ");
        
        $stmt->bindValue(':date', $date, PDO::PARAM_STR);
        $stmt->bindValue(':limit', $limit, PDO::PARAM_INT);
        $stmt->execute();
        
        $leaderboard = $stmt->fetchAll(PDO::FETCH_ASSOC);
        
        echo json_encode([
            'success' => true,
            'date' => $date,
            'leaderboard' => $leaderboard
        ]);
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Failed to get leaderboard']);
    }
}
?>