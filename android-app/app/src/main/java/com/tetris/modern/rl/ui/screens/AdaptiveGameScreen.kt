package com.tetris.modern.rl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tetris.modern.rl.ui.components.*
import com.tetris.modern.rl.ui.viewmodels.MainViewModel
import com.tetris.modern.rl.utils.DeviceUtils
import com.tetris.modern.rl.utils.DeviceUtils.PanelArrangement

@Composable
fun AdaptiveGameScreen(
    viewModel: MainViewModel,
    gameMode: String,
    onBackToMenu: () -> Unit,
    onBackToModeSelection: () -> Unit = onBackToMenu
) {
    val gameState by viewModel.gameState.collectAsState()
    val deviceInfo = DeviceUtils.getDeviceInfo()
    val layoutConfig = DeviceUtils.getGameLayoutConfig(deviceInfo)
    
    DisposableEffect(gameMode) {
        viewModel.startGame(gameMode)
        onDispose {
            viewModel.returnToMenu()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when (layoutConfig.panelArrangement) {
            PanelArrangement.VERTICAL -> {
                VerticalGameLayout(
                    viewModel = viewModel,
                    gameState = gameState,
                    layoutConfig = layoutConfig,
                    onBackToMenu = onBackToMenu
                )
            }
            PanelArrangement.HORIZONTAL_COMPACT -> {
                CompactHorizontalGameLayout(
                    viewModel = viewModel,
                    gameState = gameState,
                    layoutConfig = layoutConfig,
                    onBackToMenu = onBackToMenu
                )
            }
            PanelArrangement.HORIZONTAL_EXTENDED -> {
                ExtendedHorizontalGameLayout(
                    viewModel = viewModel,
                    gameState = gameState,
                    layoutConfig = layoutConfig,
                    onBackToMenu = onBackToMenu
                )
            }
        }
        
        // Floating controls for landscape modes (only if pad mode is selected)
        if (layoutConfig.showFloatingControls && viewModel.controlType.value == "pad") {
            FloatingGameControls(
                viewModel = viewModel,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
        
        // Touch gestures overlay (only if touch mode is selected)
        if (viewModel.controlType.value == "touch") {
            EnhancedTouchControls(
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun VerticalGameLayout(
    viewModel: MainViewModel,
    gameState: com.tetris.modern.rl.game.models.GameState,
    layoutConfig: DeviceUtils.GameLayoutConfig,
    onBackToMenu: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top bar with score and menu button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(onClick = onBackToMenu)
            ScoreDisplay(
                score = gameState.score,
                lines = gameState.lines,
                level = gameState.level,
                modifier = Modifier.weight(1f)
            )
            MuteButton(viewModel = viewModel)
        }
        
        // Main game area with side panels
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Hold piece
            Box(
                modifier = Modifier
                    .weight(0.15f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                HoldPieceDisplay(
                    heldPiece = gameState.heldPiece,
                    modifier = Modifier.padding(4.dp)
                )
            }
            
            // Game grid
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                TetrisGameCanvas(
                    viewModel = viewModel,
                    modifier = Modifier
                        .widthIn(max = layoutConfig.maxGridWidth)
                        .heightIn(max = layoutConfig.maxGridHeight)
                        .aspectRatio(0.5f)
                )
            }
            
            // Next pieces
            Box(
                modifier = Modifier
                    .weight(0.15f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                NextPiecesDisplay(
                    nextPieces = gameState.nextPieces.map { pieceType ->
                        com.tetris.modern.rl.game.Piece(pieceType.name.first())
                    },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        
        // Bottom controls (if enabled and pad mode is selected)
        if (layoutConfig.showBottomControls && viewModel.controlType.value == "pad") {
            GameControls(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun CompactHorizontalGameLayout(
    viewModel: MainViewModel,
    gameState: com.tetris.modern.rl.game.models.GameState,
    layoutConfig: DeviceUtils.GameLayoutConfig,
    onBackToMenu: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Left panel - Stats and Hold
        Column(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton(onClick = onBackToMenu)
            
            HoldPieceDisplay(
                heldPiece = gameState.heldPiece,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            CompactScoreDisplay(
                score = gameState.score,
                lines = gameState.lines,
                level = gameState.level
            )
            
            MuteButton(viewModel = viewModel)
        }
        
        // Center - Game grid
        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            TetrisGameCanvas(
                viewModel = viewModel,
                modifier = Modifier
                    .widthIn(max = layoutConfig.maxGridWidth)
                    .heightIn(max = layoutConfig.maxGridHeight)
                    .aspectRatio(0.5f)
            )
        }
        
        // Right panel - Next pieces
        Column(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            NextPiecesDisplay(
                nextPieces = gameState.nextPieces.map { pieceType ->
                    com.tetris.modern.rl.game.Piece(pieceType.name.first())
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ExtendedHorizontalGameLayout(
    viewModel: MainViewModel,
    gameState: com.tetris.modern.rl.game.models.GameState,
    layoutConfig: DeviceUtils.GameLayoutConfig,
    onBackToMenu: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Left panel - Extended stats
        Column(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            BackButton(onClick = onBackToMenu)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            ExtendedStatsPanel(
                score = gameState.score,
                lines = gameState.lines,
                level = gameState.level,
                gameMode = gameState.gameMode,
                modeInfo = gameState.modeInfo
            )
        }
        
        // Hold piece
        Column(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            Text("HOLD", style = MaterialTheme.typography.titleMedium)
            HoldPieceDisplay(
                heldPiece = gameState.heldPiece,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        // Center - Game grid (larger on tablets)
        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            TetrisGameCanvas(
                viewModel = viewModel,
                modifier = Modifier
                    .widthIn(max = layoutConfig.maxGridWidth)
                    .heightIn(max = layoutConfig.maxGridHeight)
                    .aspectRatio(0.5f)
            )
        }
        
        // Next pieces
        Column(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            Text("NEXT", style = MaterialTheme.typography.titleMedium)
            NextPiecesDisplay(
                nextPieces = gameState.nextPieces.map { pieceType ->
                    com.tetris.modern.rl.game.Piece(pieceType.name.first())
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        // Right panel - Live leaderboard preview (tablets only)
        Column(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            MiniLeaderboard(
                currentScore = gameState.score,
                gameMode = gameState.gameMode
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            MuteButton(viewModel = viewModel)
        }
    }
}

@Composable
private fun FloatingGameControls(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.7f)
        )
    ) {
        GameControls(
            viewModel = viewModel,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun BackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
        )
    }
}

@Composable
private fun MuteButton(viewModel: MainViewModel) {
    IconButton(
        onClick = { viewModel.toggleMute() }
    ) {
        Icon(
            if (viewModel.isMuted.value) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
            contentDescription = "Toggle Sound",
            tint = Color.White
        )
    }
}

@Composable
private fun CompactScoreDisplay(
    score: Int,
    lines: Int,
    level: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = score.toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Text(
            text = "L:$lines Lv:$level",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
private fun ExtendedStatsPanel(
    score: Int,
    lines: Int,
    level: Int,
    gameMode: String,
    modeInfo: Map<String, Any>?
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A2E)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("STATISTICS", style = MaterialTheme.typography.titleMedium, color = Color.White)
            Divider(color = Color.Gray)
            
            StatRow("Score", score.toString())
            StatRow("Lines", lines.toString())
            StatRow("Level", level.toString())
            StatRow("Mode", gameMode)
            
            modeInfo?.forEach { (key, value) ->
                StatRow(key, value.toString())
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
        Text(value, color = Color.White, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun MiniLeaderboard(
    currentScore: Int,
    gameMode: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A2E)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("TOP SCORES", style = MaterialTheme.typography.titleMedium, color = Color.White)
            Divider(color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
            
            // Mock leaderboard data - would be real data in production
            repeat(5) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "#${index + 1}",
                        color = if (index == 0) Color.Yellow else Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        "${100000 - index * 10000}",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            
            Divider(color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
            
            // Current score position
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("YOU", color = Color.Cyan, style = MaterialTheme.typography.bodySmall)
                Text(
                    currentScore.toString(),
                    color = Color.Cyan,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}