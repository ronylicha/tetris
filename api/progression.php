<?php
// Forward all requests to the working file
$_SERVER['SCRIPT_NAME'] = '/api/player-progression.php';
require __DIR__ . '/player-progression.php';
?>