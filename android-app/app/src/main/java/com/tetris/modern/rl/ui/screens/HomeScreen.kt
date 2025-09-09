package com.tetris.modern.rl.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.tetris.modern.rl.R
import com.tetris.modern.rl.ui.components.AchievementsDialog
import com.tetris.modern.rl.ui.components.ProgressionDialog
import com.tetris.modern.rl.ui.theme.NeonColors
import com.tetris.modern.rl.ui.viewmodels.MainViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateToModeSelection: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToLeaderboard: () -> Unit,
    onNavigateToStats: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progressionState by viewModel.playerProgression.progressionState.collectAsState()
    val achievementSystem by viewModel.achievementSystem.achievementProgress.collectAsState()
    
    var showProgressionDialog by remember { mutableStateOf(false) }
    var showAchievementsDialog by remember { mutableStateOf(false) }
    val isFirstLaunch by viewModel.isFirstLaunch
    var showTutorial by remember { mutableStateOf(isFirstLaunch) }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0A0A),
                        Color(0xFF1A1A2E)
                    )
                )
            )
    ) {
        // Animated background effect
        AnimatedBackground()
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Title Section
            TitleSection()
            
            // Player Info Card
            PlayerInfoCard(
                level = progressionState.level,
                rank = progressionState.rank.displayName,
                xp = progressionState.currentXP,
                xpToNext = progressionState.xpToNextLevel,
                onProgressionClick = { showProgressionDialog = true }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Main Menu Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MainMenuButton(
                    text = stringResource(R.string.play),
                    icon = Icons.Default.PlayArrow,
                    gradient = Brush.horizontalGradient(
                        colors = listOf(NeonColors.Cyan, NeonColors.Blue)
                    ),
                    onClick = onNavigateToModeSelection
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SecondaryMenuButton(
                        text = stringResource(R.string.leaderboard),
                        icon = Icons.Default.Leaderboard,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToLeaderboard
                    )
                    
                    SecondaryMenuButton(
                        text = stringResource(R.string.achievements),
                        icon = Icons.Default.EmojiEvents,
                        modifier = Modifier.weight(1f),
                        onClick = { showAchievementsDialog = true }
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SecondaryMenuButton(
                        text = stringResource(R.string.settings),
                        icon = Icons.Default.Settings,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToSettings
                    )
                    
                    SecondaryMenuButton(
                        text = stringResource(R.string.stats),
                        icon = Icons.Default.Analytics,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToStats
                    )
                }
            }
            
            // Daily Challenge Card
            DailyChallengeCard(
                onClick = { onNavigateToModeSelection() }
            )
            
            // Social Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Share */ }) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                }
                
                IconButton(onClick = { /* Rate */ }) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rate",
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                }
                
                IconButton(onClick = { /* Info */ }) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "About",
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
        
        // Dialogs
        if (showProgressionDialog) {
            ProgressionDialog(
                progression = viewModel.playerProgression,
                onDismiss = { showProgressionDialog = false }
            )
        }
        
        if (showAchievementsDialog) {
            AchievementsDialog(
                achievementSystem = viewModel.achievementSystem,
                onDismiss = { showAchievementsDialog = false }
            )
        }
        
        // Show tutorial on first launch
        if (showTutorial) {
            TutorialScreen(
                onComplete = {
                    viewModel.setFirstLaunchComplete()
                    showTutorial = false
                },
                onSkip = {
                    viewModel.setFirstLaunchComplete()
                    showTutorial = false
                }
            )
        }
    }
}

@Composable
fun TitleSection() {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TETRIS",
            fontSize = 72.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.scale(scale),
            style = MaterialTheme.typography.displayLarge.copy(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        NeonColors.Cyan,
                        NeonColors.Magenta,
                        NeonColors.Yellow
                    )
                )
            )
        )
    }
}

@Composable
fun PlayerInfoCard(
    level: Int,
    rank: String,
    xp: Int,
    xpToNext: Int,
    onProgressionClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onProgressionClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A2E).copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Level $level",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = rank,
                        fontSize = 16.sp,
                        color = NeonColors.Cyan
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$xp / $xpToNext XP",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = xp.toFloat() / xpToNext.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = NeonColors.Cyan,
                trackColor = Color(0xFF0A0A0A)
            )
        }
    }
}

@Composable
fun MainMenuButton(
    text: String,
    icon: ImageVector,
    gradient: Brush,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = text,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SecondaryMenuButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(60.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A3E).copy(alpha = 0.8f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NeonColors.Cyan,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}

@Composable
fun DailyChallengeCard(onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor by infiniteTransition.animateColor(
        initialValue = NeonColors.Cyan,
        targetValue = NeonColors.Magenta,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A3E).copy(alpha = 0.9f)
        ),
        border = BorderStroke(2.dp, animatedColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(R.string.daily_challenge),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = animatedColor
                )
                Text(
                    text = stringResource(R.string.new_challenge_available),
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
            
            Icon(
                Icons.Default.TrendingUp,
                contentDescription = null,
                tint = animatedColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun AnimatedBackground() {
    // Animated tetromino shapes falling in background
    val infiniteTransition = rememberInfiniteTransition()
    
    Box(modifier = Modifier.fillMaxSize()) {
        repeat(5) { index ->
            val offsetY by infiniteTransition.animateFloat(
                initialValue = -100f,
                targetValue = 2000f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 10000 + index * 2000,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
            
            TetrominoShape(
                modifier = Modifier
                    .offset(
                        x = (50 + index * 150).dp,
                        y = offsetY.dp
                    )
                    .scale(0.3f)
                    .alpha(0.1f),
                color = NeonColors.pieceColors[index % NeonColors.pieceColors.size]
            )
        }
    }
}

@Composable
fun TetrominoShape(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .background(color)
    )
}