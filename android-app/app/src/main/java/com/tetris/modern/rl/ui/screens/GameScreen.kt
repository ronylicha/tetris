package com.tetris.modern.rl.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetris.modern.rl.game.TetrisEngine
import com.tetris.modern.rl.game.models.Piece
import com.tetris.modern.rl.game.models.PieceType
import com.tetris.modern.rl.ui.components.GameControls
import com.tetris.modern.rl.ui.components.GameStats
import com.tetris.modern.rl.ui.components.NextPiecesDisplay
import com.tetris.modern.rl.ui.components.ScoreDisplay
import com.tetris.modern.rl.ui.components.EnhancedTouchControls
import com.tetris.modern.rl.ui.components.ModeSpecificDisplay
import com.tetris.modern.rl.ui.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.text.style.TextAlign

@Composable
fun GameScreen(
    viewModel: MainViewModel,
    gameMode: String,
    onBackToMenu: () -> Unit,
    onBackToModeSelection: () -> Unit = onBackToMenu
) {
    val gameState by viewModel.gameState.collectAsState()
    val density = LocalDensity.current
    
    DisposableEffect(gameMode) {
        viewModel.startGame(gameMode)
        
        onDispose {
            // Clean up when leaving the screen
            // Return to menu music when leaving game
            viewModel.returnToMenu()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Left Panel - Hold piece
            Column(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top
            ) {
                HoldPieceDisplay(
                    heldPiece = gameState.heldPiece,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            // Center - Game Canvas and Score
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Score at the top center
                ScoreDisplay(
                    score = gameState.score,
                    lines = gameState.lines,
                    level = gameState.level,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Mode-specific information under score (compact)
                ModeSpecificDisplay(
                    gameMode = gameState.gameMode,
                    modeInfo = gameState.modeInfo,
                    objective = gameState.modeObjective,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Game Canvas
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    TetrisGameCanvas(
                        viewModel = viewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    
                    // Pause overlay
                    if (gameState.isPaused) {
                        PauseOverlay(
                            onResume = { 
                                viewModel.resumeGame()
                            },
                            onQuit = onBackToMenu,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            
            // Right Panel - Next pieces
            Column(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxHeight()
                    .padding(8.dp),
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
        
        // Controls based on user preference
        if (viewModel.controlType.value == "touch") {
            // Enhanced touch controls overlay
            EnhancedTouchControls(
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Virtual pad controls
            if (!gameState.isGameOver) {
                GameControls(
                    viewModel = viewModel,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }
        }
        
        // Top bar with pause and back buttons
        TopGameBar(
            onPause = { 
                if (!gameState.isPaused && gameState.isPlaying) {
                    viewModel.pauseGame()
                }
            },
            onBack = onBackToMenu,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        
        // Game over overlay - placed at the top level to cover everything
        if (gameState.isGameOver) {
            GameOverOverlay(
                score = gameState.score,
                onRestart = { viewModel.startGame(gameMode) },
                onQuit = onBackToMenu,
                onChangeMode = onBackToModeSelection,
                modifier = Modifier.fillMaxSize(),
                isVictory = gameState.modeInfo["isVictory"] as? Boolean ?: false,
                gameMode = gameMode,
                lines = gameState.lines,
                level = gameState.level
            )
        }
    }
}

@Composable
fun TetrisGameCanvas(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var canvasSize by remember { mutableStateOf(Size.Zero) }
    val gameState by viewModel.gameState.collectAsState()
    var refreshTrigger by remember { mutableStateOf(0) }
    
    // Force canvas refresh at 60 FPS
    // Also force refresh when resuming from pause
    LaunchedEffect(gameState.isPlaying, gameState.isPaused) {
        if (!gameState.isPaused) {
            // Force immediate refresh when unpausing
            refreshTrigger++
        }
        while (gameState.isPlaying && !gameState.isPaused) {
            refreshTrigger++
            delay(16) // ~60 FPS refresh rate
        }
    }
    
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0A)),
        onDraw = {
        canvasSize = size
        
        val cellSize = size.width / TetrisEngine.GRID_WIDTH
        val gridHeight = cellSize * TetrisEngine.GRID_HEIGHT
        val offsetY = (size.height - gridHeight) / 2
        
        // Draw grid background
        drawGrid(cellSize, offsetY)
        
        // Draw placed pieces
        drawPlacedPieces(viewModel, cellSize, offsetY)
        
        // Draw ghost piece
        drawGhostPiece(viewModel, cellSize, offsetY)
        
        // Draw current piece
        drawCurrentPiece(viewModel, cellSize, offsetY)
        
        // Force recomposition based on refresh trigger
        refreshTrigger.toString()
    })
}

fun DrawScope.drawGrid(cellSize: Float, offsetY: Float) {
    val gridColor = Color(0xFF1A1A2E)
    val strokeWidth = 1.dp.toPx()
    
    // Draw vertical lines
    for (x in 0..TetrisEngine.GRID_WIDTH) {
        drawLine(
            color = gridColor,
            start = Offset(x * cellSize, offsetY),
            end = Offset(x * cellSize, offsetY + TetrisEngine.GRID_HEIGHT * cellSize),
            strokeWidth = strokeWidth
        )
    }
    
    // Draw horizontal lines
    for (y in 0..TetrisEngine.GRID_HEIGHT) {
        drawLine(
            color = gridColor,
            start = Offset(0f, offsetY + y * cellSize),
            end = Offset(TetrisEngine.GRID_WIDTH * cellSize, offsetY + y * cellSize),
            strokeWidth = strokeWidth
        )
    }
}

fun DrawScope.drawPlacedPieces(viewModel: MainViewModel, cellSize: Float, offsetY: Float) {
    val grid = viewModel.tetrisEngine.getGridState()
    
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            val pieceType = grid[y][x]
            if (pieceType >= 0) {
                val color = getPieceColor(PieceType.values()[pieceType])
                drawPieceCell(x, y, cellSize, offsetY, color)
            }
        }
    }
}

fun DrawScope.drawGhostPiece(viewModel: MainViewModel, cellSize: Float, offsetY: Float) {
    val ghostPiece = viewModel.tetrisEngine.getGhostPiecePosition()
    ghostPiece?.let { piece ->
        val shape = piece.getCurrentShape()
        val color = piece.getColor().copy(alpha = 0.3f)
        
        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] != 0) {
                    val x = piece.x + col
                    val y = piece.y + row - TetrisEngine.HIDDEN_ROWS
                    
                    if (y >= 0 && y < TetrisEngine.GRID_HEIGHT) {
                        drawPieceCell(x, y, cellSize, offsetY, color, isGhost = true)
                    }
                }
            }
        }
    }
}

fun DrawScope.drawCurrentPiece(viewModel: MainViewModel, cellSize: Float, offsetY: Float) {
    val currentPiece = viewModel.tetrisEngine.getCurrentPieceState()
    currentPiece?.let { piece ->
        val shape = piece.getCurrentShape()
        val color = piece.getColor()
        
        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] != 0) {
                    val x = piece.x + col
                    val y = piece.y + row - TetrisEngine.HIDDEN_ROWS
                    
                    if (y >= 0 && y < TetrisEngine.GRID_HEIGHT) {
                        drawPieceCell(x, y, cellSize, offsetY, color)
                    }
                }
            }
        }
    }
}

fun DrawScope.drawPieceCell(
    x: Int,
    y: Int,
    cellSize: Float,
    offsetY: Float,
    color: Color,
    isGhost: Boolean = false
) {
    val padding = cellSize * 0.05f
    val blockSize = cellSize - padding * 2
    
    if (isGhost) {
        // Draw ghost piece with dashed outline
        drawRect(
            color = color,
            topLeft = Offset(x * cellSize + padding, offsetY + y * cellSize + padding),
            size = Size(blockSize, blockSize),
            style = Stroke(width = 2.dp.toPx())
        )
    } else {
        // Draw solid block with gradient effect
        drawRect(
            color = color,
            topLeft = Offset(x * cellSize + padding, offsetY + y * cellSize + padding),
            size = Size(blockSize, blockSize),
            style = Fill
        )
        
        // Add highlight
        drawRect(
            color = color.copy(alpha = 0.5f),
            topLeft = Offset(x * cellSize + padding, offsetY + y * cellSize + padding),
            size = Size(blockSize * 0.3f, blockSize * 0.3f),
            style = Fill
        )
        
        // Add border
        drawRect(
            color = color.copy(alpha = 0.8f),
            topLeft = Offset(x * cellSize + padding, offsetY + y * cellSize + padding),
            size = Size(blockSize, blockSize),
            style = Stroke(width = 1.dp.toPx())
        )
    }
}

fun getPieceColor(type: PieceType): Color {
    return when (type) {
        PieceType.I -> Color(0xFF00FFFF) // Cyan
        PieceType.O -> Color(0xFFFFFF00) // Yellow
        PieceType.T -> Color(0xFFFF00FF) // Magenta
        PieceType.S -> Color(0xFF00FF00) // Green
        PieceType.Z -> Color(0xFFFF0000) // Red
        PieceType.J -> Color(0xFF0000FF) // Blue
        PieceType.L -> Color(0xFFFF8800) // Orange
    }
}

@Composable
fun HoldPieceDisplay(
    heldPiece: PieceType?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A2E)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "HOLD",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
            
            heldPiece?.let { pieceType ->
                // Draw the held piece preview
                Canvas(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(4.dp)
                ) {
                    val piece = Piece(pieceType, 0, 0, 0)
                    val shape = piece.getCurrentShape()
                    val color = getPieceColor(pieceType)
                    val cellSize = size.width / 4 // 4x4 grid for piece preview
                    
                    for (row in shape.indices) {
                        for (col in shape[row].indices) {
                            if (shape[row][col] != 0) {
                                val x = col * cellSize
                                val y = row * cellSize
                                
                                // Draw cell
                                drawRect(
                                    color = color,
                                    topLeft = Offset(x, y),
                                    size = Size(cellSize * 0.9f, cellSize * 0.9f)
                                )
                                
                                // Draw border
                                drawRect(
                                    color = color.copy(alpha = 0.8f),
                                    topLeft = Offset(x, y),
                                    size = Size(cellSize * 0.9f, cellSize * 0.9f),
                                    style = Stroke(width = 1.dp.toPx())
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopGameBar(
    onPause: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        
        IconButton(onClick = onPause) {
            Icon(
                imageVector = Icons.Default.Pause,
                contentDescription = "Pause",
                tint = Color.White
            )
        }
    }
}

@Composable
fun PauseOverlay(
    onResume: () -> Unit,
    onQuit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1A2E)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "PAUSED",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = onResume,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FFFF)
                    )
                ) {
                    Text(
                        "RESUME",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                // Help button for touch controls
                var showHelp by remember { mutableStateOf(false) }
                
                OutlinedButton(
                    onClick = { showHelp = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    border = BorderStroke(2.dp, Color(0xFF00FF00))
                ) {
                    Icon(
                        imageVector = Icons.Default.Help,
                        contentDescription = "Help",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF00FF00)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "TOUCH CONTROLS HELP",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FF00)
                    )
                }
                
                OutlinedButton(
                    onClick = onQuit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    border = BorderStroke(2.dp, Color(0xFFFF00FF))
                ) {
                    Text(
                        "QUIT TO MENU",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF00FF)
                    )
                }
                
                // Show help dialog if requested
                if (showHelp) {
                    TouchControlsHelpDialog(
                        onDismiss = { showHelp = false }
                    )
                }
            }
        }
    }
}

@Composable
fun GameOverOverlay(
    score: Int,
    onRestart: () -> Unit,
    onQuit: () -> Unit,
    onChangeMode: () -> Unit = onQuit,
    modifier: Modifier = Modifier,
    isVictory: Boolean = false,
    gameMode: String = "",
    lines: Int = 0,
    level: Int = 1
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val isMobile = screenHeight < 700
    
    Box(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(if (isMobile) 0.55f else 0.8f),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1A2E)
            ),
            border = BorderStroke(
                width = 2.dp,
                color = if (isVictory) Color(0xFF00FF00) else Color(0xFFFF0000)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(if (isMobile) 12.dp else 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(if (isMobile) 8.dp else 20.dp)
            ) {
                // Dynamic title based on success/failure
                val titleText = when {
                    isVictory -> "CHALLENGE COMPLETE!"
                    gameMode == "Sprint" && lines >= 40 -> "SPRINT COMPLETE!"
                    gameMode == "Marathon" && lines >= 150 -> "MARATHON COMPLETE!"
                    gameMode == "Puzzle" && isVictory -> "PUZZLE SOLVED!"
                    else -> "GAME OVER"
                }
                
                val titleColor = if (isVictory) Color(0xFF00FF00) else Color.Red
                
                Text(
                    text = titleText,
                    style = if (isMobile) MaterialTheme.typography.titleMedium else MaterialTheme.typography.headlineLarge,
                    color = titleColor,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                // Add success animation emoji if victory (smaller on mobile)
                if (isVictory && !isMobile) {
                    Text(
                        text = "🏆",
                        fontSize = if (isMobile) 32.sp else 48.sp,
                        modifier = Modifier.padding(vertical = if (isMobile) 4.dp else 8.dp)
                    )
                }
                
                Text(
                    text = "SCORE",
                    style = if (isMobile) MaterialTheme.typography.titleSmall else MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
                
                Text(
                    text = score.toString(),
                    style = if (isMobile) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.displayMedium,
                    color = Color(0xFF00FFFF),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                
                // Additional stats
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("LINES", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                        Text(lines.toString(), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("LEVEL", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                        Text(level.toString(), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(if (isMobile) 4.dp else 16.dp))
                
                Button(
                    onClick = onRestart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (isMobile) 48.dp else 56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FF00)
                    )
                ) {
                    Text(
                        "PLAY AGAIN",
                        fontSize = if (isMobile) 16.sp else 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                OutlinedButton(
                    onClick = onChangeMode,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (isMobile) 48.dp else 56.dp),
                    border = BorderStroke(2.dp, Color(0xFFFFFF00))
                ) {
                    Text(
                        "CHANGE MODE",
                        fontSize = if (isMobile) 16.sp else 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFF00)
                    )
                }
                
                TextButton(
                    onClick = onQuit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (isMobile) 40.dp else 48.dp)
                ) {
                    Text(
                        "MAIN MENU",
                        fontSize = if (isMobile) 14.sp else 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}