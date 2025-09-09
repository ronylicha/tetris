package com.tetris.modern.rl.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.tetris.modern.rl.R
import com.tetris.modern.rl.ui.theme.NeonColors
import com.tetris.modern.rl.ui.viewmodels.MainViewModel

data class GameModeInfo(
    val id: String,
    val nameRes: Int,
    val descriptionRes: Int,
    val icon: ImageVector,
    val color: Color,
    val isLocked: Boolean = false,
    val requiredLevel: Int = 1,
    val isAvailableOnMobile: Boolean = true
)

@Composable
fun ModeSelectionScreen(
    viewModel: MainViewModel,
    onModeSelected: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val progressionState by viewModel.playerProgression.progressionState.collectAsState()
    val playerLevel = progressionState.level
    
    // Determine number of columns based on screen width
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val columns = when {
        screenWidthDp < 600 -> 1  // Phone portrait
        screenWidthDp < 840 -> 2  // Tablet or phone landscape
        else -> 3  // Large tablet
    }
    
    val gameModes = remember {
        listOf(
            GameModeInfo(
                "classic",
                R.string.mode_classic,
                R.string.mode_classic_desc,
                Icons.Default.AllInclusive,
                NeonColors.Cyan
            ),
            GameModeInfo(
                "sprint",
                R.string.mode_sprint,
                R.string.mode_sprint_desc,
                Icons.Default.Speed,
                NeonColors.Yellow
            ),
            GameModeInfo(
                "marathon",
                R.string.mode_marathon,
                R.string.mode_marathon_desc,
                Icons.AutoMirrored.Filled.DirectionsRun,
                NeonColors.Orange,
                requiredLevel = 3
            ),
            GameModeInfo(
                "zen",
                R.string.mode_zen,
                R.string.mode_zen_desc,
                Icons.Default.Spa,
                NeonColors.Purple,
                requiredLevel = 2
            ),
            GameModeInfo(
                "puzzle",
                R.string.mode_puzzle,
                R.string.mode_puzzle_desc,
                Icons.Default.Extension,
                NeonColors.Green,
                requiredLevel = 5
            ),
            GameModeInfo(
                "battle",
                R.string.mode_battle,
                R.string.mode_battle_desc,
                Icons.Default.SportsEsports,
                NeonColors.Red,
                requiredLevel = 8
            ),
            GameModeInfo(
                "powerup",
                R.string.mode_powerup,
                R.string.mode_powerup_desc,
                Icons.Default.FlashOn,
                NeonColors.Magenta,
                requiredLevel = 10
            ),
            GameModeInfo(
                "battle2p",
                R.string.mode_battle2p,
                R.string.mode_battle2p_desc,
                Icons.Default.People,
                Color(0xFFFF69B4), // Pink color
                requiredLevel = 6,
                isAvailableOnMobile = false
            ),
            GameModeInfo(
                "daily",
                R.string.mode_daily,
                R.string.mode_daily_desc,
                Icons.Default.CalendarToday,
                NeonColors.Cyan,
                requiredLevel = 1
            )
        )
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F0F1E),
                        Color(0xFF1A1A2E)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackPressed) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back), tint = Color.White)
                }
                Text(
                    stringResource(R.string.select_mode),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            // Game Modes Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(gameModes.filter { it.isAvailableOnMobile }) { mode ->
                    val isLocked = playerLevel < mode.requiredLevel
                    
                    GameModeCard(
                        mode = mode,
                        isLocked = isLocked,
                        playerLevel = playerLevel,
                        onClick = {
                            if (!isLocked) {
                                onModeSelected(mode.id)
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameModeCard(
    mode: GameModeInfo,
    isLocked: Boolean,
    playerLevel: Int,
    onClick: () -> Unit
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isLocked) 0.7f else 1f
    )
    
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val cardHeight = if (screenWidthDp < 600) 160.dp else 180.dp
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .clickable(enabled = !isLocked) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isLocked) 
                Color(0xFF2A2A3E).copy(alpha = 0.8f)
            else 
                Color(0xFF1A1A2E)
        ),
        border = BorderStroke(
            width = 2.dp,
            color = if (isLocked) 
                Color.Gray.copy(alpha = 0.5f) 
            else 
                mode.color.copy(alpha = 0.8f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(animatedAlpha)
        ) {
            // New badge
            if (mode.id == "powerup" || mode.id == "daily") {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            NeonColors.Yellow,
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        stringResource(R.string.new_mode),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    mode.icon,
                    contentDescription = null,
                    tint = if (isLocked) Color.Gray.copy(alpha = 0.8f) else mode.color,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.height(6.dp))
                
                Text(
                    text = stringResource(mode.nameRes),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isLocked) Color.White.copy(alpha = 0.6f) else Color.White,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(2.dp))
                
                Text(
                    text = stringResource(mode.descriptionRes),
                    fontSize = 11.sp,
                    color = if (isLocked) Color.White.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (isLocked) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = stringResource(R.string.locked),
                            tint = Color.Yellow.copy(alpha = 0.8f),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            stringResource(R.string.level_requirement, mode.requiredLevel),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Yellow.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }
    }
}