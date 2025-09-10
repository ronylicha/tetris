<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type, Authorization');

// Handle preflight requests
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    exit(0);
}

// Database configuration
$db_path = __DIR__ . '/../database/tetris_scores.db';

// Initialize database connection
try {
    $db = new PDO('sqlite:' . $db_path);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => 'Database connection failed']);
    exit;
}

// Start session for authentication
session_start();

// Get request data
$data = json_decode(file_get_contents('php://input'), true);
$action = $data['action'] ?? $_GET['action'] ?? '';

/**
 * Hash password securely
 */
function hashPassword($password) {
    return password_hash($password, PASSWORD_BCRYPT, ['cost' => 12]);
}

/**
 * Verify password
 */
function verifyPassword($password, $hash) {
    return password_verify($password, $hash);
}

/**
 * Generate secure token
 */
function generateToken() {
    return bin2hex(random_bytes(32));
}

/**
 * Validate email format
 */
function validateEmail($email) {
    return filter_var($email, FILTER_VALIDATE_EMAIL) !== false;
}

/**
 * Validate username
 */
function validateUsername($username) {
    return preg_match('/^[a-zA-Z0-9_]{3,20}$/', $username);
}

/**
 * Validate password strength
 */
function validatePassword($password) {
    return strlen($password) >= 6;
}

// Handle different actions
switch ($action) {
    case 'register':
        $username = trim($data['username'] ?? '');
        $email = trim($data['email'] ?? '');
        $password = $data['password'] ?? ''; // Don't trim password
        $displayName = trim($data['display_name'] ?? $data['displayName'] ?? $username); // Support both snake_case and camelCase
        
        // Validation
        if (!validateUsername($username)) {
            http_response_code(400);
            echo json_encode(['error' => 'Invalid username. Use 3-20 alphanumeric characters and underscores']);
            exit;
        }
        
        if (!validateEmail($email)) {
            http_response_code(400);
            echo json_encode(['error' => 'Invalid email address']);
            exit;
        }
        
        if (!validatePassword($password)) {
            http_response_code(400);
            echo json_encode(['error' => 'Password must be at least 6 characters']);
            exit;
        }
        
        try {
            // Check if username or email already exists
            $stmt = $db->prepare('SELECT id FROM players WHERE username = ? OR email = ?');
            $stmt->execute([$username, $email]);
            
            if ($stmt->fetch()) {
                http_response_code(400);
                echo json_encode(['error' => 'Username or email already exists']);
                exit;
            }
            
            // Hash password
            $hashedPassword = hashPassword($password);
            
            // Create authentication token
            $authToken = generateToken();
            
            // Insert new player
            $stmt = $db->prepare('
                INSERT INTO players (
                    username, display_name, email, password_hash, auth_token,
                    level, current_xp, total_xp, rank, created_at, last_login
                ) VALUES (?, ?, ?, ?, ?, 1, 0, 0, "Novice", datetime("now"), datetime("now"))
            ');
            
            $stmt->execute([$username, $displayName, $email, $hashedPassword, $authToken]);
            $playerId = $db->lastInsertId();
            
            // Set session
            $_SESSION['player_id'] = $playerId;
            $_SESSION['auth_token'] = $authToken;
            
            // Get player data
            $stmt = $db->prepare('SELECT * FROM players WHERE id = ?');
            $stmt->execute([$playerId]);
            $player = $stmt->fetch(PDO::FETCH_ASSOC);
            
            // Remove sensitive data
            unset($player['password_hash']);
            
            // Return response compatible with both web and Android app
            echo json_encode([
                'success' => true,
                // For web compatibility
                'player' => $player,
                'auth_token' => $authToken,
                // For Android compatibility
                'token' => $authToken,
                'refresh_token' => generateToken(), // Generate a refresh token
                'userId' => (string)$playerId,
                'username' => $username,
                'displayName' => $displayName,
                'email' => $email,
                'isGuest' => false,
                'message' => 'Account created successfully!'
            ]);
            
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Registration failed: ' . $e->getMessage()]);
        }
        break;
        
    case 'login':
        $email = trim($data['email'] ?? '');
        $password = $data['password'] ?? ''; // Don't trim password
        
        if (!$email || !$password) {
            http_response_code(400);
            echo json_encode(['error' => 'Email and password are required']);
            exit;
        }
        
        try {
            // Find player by email
            $stmt = $db->prepare('SELECT * FROM players WHERE email = ?');
            $stmt->execute([$email]);
            $player = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if (!$player || !verifyPassword($password, $player['password_hash'])) {
                http_response_code(401);
                echo json_encode(['error' => 'Invalid email or password']);
                exit;
            }
            
            // Generate new auth token
            $authToken = generateToken();
            
            // Update last login and auth token
            $stmt = $db->prepare('
                UPDATE players 
                SET last_login = datetime("now"), auth_token = ?
                WHERE id = ?
            ');
            $stmt->execute([$authToken, $player['id']]);
            
            // Set session
            $_SESSION['player_id'] = $player['id'];
            $_SESSION['auth_token'] = $authToken;
            
            // Remove sensitive data
            unset($player['password_hash']);
            $player['auth_token'] = $authToken;
            
            // Return response compatible with both web and Android app
            echo json_encode([
                'success' => true,
                // For web compatibility
                'player' => $player,
                'auth_token' => $authToken,
                // For Android compatibility
                'token' => $authToken,
                'refresh_token' => generateToken(),
                'userId' => (string)$player['id'],
                'username' => $player['username'],
                'displayName' => $player['display_name'],
                'email' => $player['email'],
                'isGuest' => false,
                'message' => 'Login successful!'
            ]);
            
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Login failed: ' . $e->getMessage()]);
        }
        break;
        
    case 'check':
        // Check if username or email is available
        $username = trim($data['username'] ?? '');
        $email = trim($data['email'] ?? '');
        
        $response = [
            'success' => true,
            'usernameAvailable' => true,
            'emailAvailable' => true
        ];
        
        try {
            if ($username) {
                $stmt = $db->prepare('SELECT id FROM players WHERE username = ?');
                $stmt->execute([$username]);
                if ($stmt->fetch()) {
                    $response['usernameAvailable'] = false;
                }
            }
            
            if ($email) {
                $stmt = $db->prepare('SELECT id FROM players WHERE email = ?');
                $stmt->execute([$email]);
                if ($stmt->fetch()) {
                    $response['emailAvailable'] = false;
                }
            }
            
            echo json_encode($response);
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Check failed: ' . $e->getMessage()]);
        }
        break;
        
    case 'guest':
        $deviceId = $data['deviceId'] ?? '';
        
        if (!$deviceId) {
            http_response_code(400);
            echo json_encode(['error' => 'Device ID is required']);
            exit;
        }
        
        try {
            // Check if guest already exists for this device
            $stmt = $db->prepare('SELECT * FROM players WHERE username LIKE ? AND is_guest = 1');
            $stmt->execute(['guest_' . substr($deviceId, 0, 8) . '%']);
            $existingGuest = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if ($existingGuest) {
                // Use existing guest account
                $playerId = $existingGuest['id'];
                $username = $existingGuest['username'];
                $authToken = $existingGuest['auth_token'];
                
                // Update last login
                $stmt = $db->prepare('UPDATE players SET last_login = datetime("now") WHERE id = ?');
                $stmt->execute([$playerId]);
            } else {
                // Create new guest account
                $guestNumber = rand(1000, 9999);
                $username = 'Guest_' . $guestNumber;
                $guestId = 'guest_' . substr($deviceId, 0, 8);
                $authToken = generateToken();
                
                // Insert guest player
                $stmt = $db->prepare('
                    INSERT INTO players (
                        username, display_name, auth_token, is_guest,
                        level, current_xp, total_xp, rank, created_at, last_login
                    ) VALUES (?, ?, ?, 1, 1, 0, 0, "Novice", datetime("now"), datetime("now"))
                ');
                
                $stmt->execute([$guestId, $username, $authToken]);
                $playerId = $db->lastInsertId();
            }
            
            // Set session
            $_SESSION['player_id'] = $playerId;
            $_SESSION['auth_token'] = $authToken;
            $_SESSION['is_guest'] = true;
            
            // Return guest data
            echo json_encode([
                'success' => true,
                'token' => $authToken,
                'userId' => (string)$playerId,
                'username' => $username,
                'displayName' => $username,
                'email' => null,
                'isGuest' => true,
                'message' => 'Guest login successful'
            ]);
            
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Guest login failed: ' . $e->getMessage()]);
        }
        break;
        
    case 'logout':
        // Clear session
        session_destroy();
        
        echo json_encode([
            'success' => true,
            'message' => 'Logged out successfully'
        ]);
        break;
        
    case 'verify':
        $authToken = $data['auth_token'] ?? $_SESSION['auth_token'] ?? '';
        
        if (!$authToken) {
            http_response_code(401);
            echo json_encode(['error' => 'No authentication token provided']);
            exit;
        }
        
        try {
            // Verify token
            $stmt = $db->prepare('SELECT * FROM players WHERE auth_token = ?');
            $stmt->execute([$authToken]);
            $player = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if (!$player) {
                http_response_code(401);
                echo json_encode(['error' => 'Invalid authentication token']);
                exit;
            }
            
            // Remove sensitive data
            unset($player['password_hash']);
            unset($player['auth_token']);
            
            echo json_encode([
                'success' => true,
                'player' => $player,
                'authenticated' => true
            ]);
            
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Verification failed']);
        }
        break;
        
    case 'link_guest':
        $guestId = $data['guest_id'] ?? '';
        $authToken = $data['auth_token'] ?? $_SESSION['auth_token'] ?? '';
        
        if (!$guestId || !$authToken) {
            http_response_code(400);
            echo json_encode(['error' => 'Guest ID and authentication required']);
            exit;
        }
        
        try {
            // Get authenticated player
            $stmt = $db->prepare('SELECT id FROM players WHERE auth_token = ?');
            $stmt->execute([$authToken]);
            $player = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if (!$player) {
                http_response_code(401);
                echo json_encode(['error' => 'Authentication required']);
                exit;
            }
            
            // Get guest data
            $stmt = $db->prepare('SELECT * FROM players WHERE id = ? AND email IS NULL');
            $stmt->execute([$guestId]);
            $guest = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if (!$guest) {
                http_response_code(404);
                echo json_encode(['error' => 'Guest account not found']);
                exit;
            }
            
            // Merge guest data into authenticated account
            $stmt = $db->prepare('
                UPDATE players 
                SET 
                    total_xp = total_xp + ?,
                    games_played = games_played + ?,
                    total_score = total_score + ?,
                    total_lines = total_lines + ?,
                    total_time = total_time + ?
                WHERE id = ?
            ');
            
            $stmt->execute([
                $guest['total_xp'],
                $guest['games_played'],
                $guest['total_score'],
                $guest['total_lines'],
                $guest['total_time'],
                $player['id']
            ]);
            
            // Transfer achievements
            $stmt = $db->prepare('
                INSERT OR IGNORE INTO player_achievements (player_id, achievement_id, progress, unlocked, unlocked_at)
                SELECT ?, achievement_id, progress, unlocked, unlocked_at
                FROM player_achievements
                WHERE player_id = ?
            ');
            $stmt->execute([$player['id'], $guestId]);
            
            // Transfer unlocks
            $stmt = $db->prepare('
                INSERT OR IGNORE INTO player_unlocks (player_id, unlockable_id, unlocked_at, is_new, equipped)
                SELECT ?, unlockable_id, unlocked_at, is_new, equipped
                FROM player_unlocks
                WHERE player_id = ?
            ');
            $stmt->execute([$player['id'], $guestId]);
            
            // Delete guest account
            $stmt = $db->prepare('DELETE FROM players WHERE id = ?');
            $stmt->execute([$guestId]);
            
            echo json_encode([
                'success' => true,
                'message' => 'Guest account linked successfully'
            ]);
            
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Failed to link guest account']);
        }
        break;
        
    case 'sync':
        $authToken = $data['auth_token'] ?? $_SESSION['auth_token'] ?? '';
        $syncData = $data['sync_data'] ?? [];
        
        if (!$authToken) {
            http_response_code(401);
            echo json_encode(['error' => 'Authentication required']);
            exit;
        }
        
        try {
            // Verify player
            $stmt = $db->prepare('SELECT id FROM players WHERE auth_token = ?');
            $stmt->execute([$authToken]);
            $player = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if (!$player) {
                http_response_code(401);
                echo json_encode(['error' => 'Invalid authentication']);
                exit;
            }
            
            // Update player data from sync
            if (isset($syncData['xp'])) {
                $stmt = $db->prepare('
                    UPDATE players 
                    SET current_xp = ?, total_xp = ?, level = ?
                    WHERE id = ?
                ');
                $stmt->execute([
                    $syncData['xp']['current'],
                    $syncData['xp']['total'],
                    $syncData['xp']['level'],
                    $player['id']
                ]);
            }
            
            // Sync achievements
            if (isset($syncData['achievements'])) {
                foreach ($syncData['achievements'] as $achievement) {
                    $stmt = $db->prepare('
                        INSERT OR REPLACE INTO player_achievements 
                        (player_id, achievement_id, progress, unlocked, unlocked_at)
                        VALUES (?, ?, ?, ?, ?)
                    ');
                    $stmt->execute([
                        $player['id'],
                        $achievement['id'],
                        $achievement['progress'],
                        $achievement['unlocked'],
                        $achievement['unlocked_at']
                    ]);
                }
            }
            
            // Get updated player data
            $stmt = $db->prepare('SELECT * FROM players WHERE id = ?');
            $stmt->execute([$player['id']]);
            $updatedPlayer = $stmt->fetch(PDO::FETCH_ASSOC);
            
            // Remove sensitive data
            unset($updatedPlayer['password_hash']);
            unset($updatedPlayer['auth_token']);
            
            echo json_encode([
                'success' => true,
                'player' => $updatedPlayer,
                'message' => 'Data synced successfully'
            ]);
            
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Sync failed']);
        }
        break;
        
    default:
        http_response_code(400);
        echo json_encode(['error' => 'Invalid action']);
}
?>