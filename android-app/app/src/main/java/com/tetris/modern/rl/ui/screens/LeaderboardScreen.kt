package com.tetris.modern.rl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tetris.modern.rl.data.entities.ScoreEntity
import com.tetris.modern.rl.ui.theme.*
import com.tetris.modern.rl.ui.viewmodels.MainViewModel
import kotlinx.coroutines.flow.Flow
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sync
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.res.stringResource
import com.tetris.modern.rl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val scores by viewModel.topScores.collectAsState(initial = emptyList())
    val allTimeString = stringResource(R.string.all_time)
    var selectedMode by remember { mutableStateOf(allTimeString) }
    var isRefreshing by remember { mutableStateOf(false) }
    var isSyncing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    
    // Filter scores based on selected mode
    val filteredScores = when (selectedMode) {
        allTimeString -> scores
        stringResource(R.string.mode_classic) -> scores.filter { it.gameMode.equals("Classic", ignoreCase = true) }
        stringResource(R.string.mode_sprint) -> scores.filter { it.gameMode.equals("Sprint", ignoreCase = true) }
        stringResource(R.string.mode_marathon) -> scores.filter { it.gameMode.equals("Marathon", ignoreCase = true) }
        stringResource(R.string.mode_zen) -> scores.filter { it.gameMode.equals("Zen", ignoreCase = true) }
        stringResource(R.string.mode_puzzle) -> scores.filter { it.gameMode.equals("Puzzle", ignoreCase = true) }
        stringResource(R.string.mode_battle) -> scores.filter { it.gameMode.equals("Battle", ignoreCase = true) }
        else -> scores
    }
    
    // Sync on screen open
    LaunchedEffect(Unit) {
        isSyncing = true
        viewModel.syncLeaderboard()
        delay(1000) // Show sync indicator for at least 1 second
        isSyncing = false
    }
    
    // Pull to refresh function
    val onRefresh: () -> Unit = {
        coroutineScope.launch {
            isRefreshing = true
            viewModel.syncLeaderboard()
            delay(500) // Show refresh indicator briefly
            isRefreshing = false
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        stringResource(R.string.leaderboard),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundDark
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Back button and title with sync indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back), tint = Color.White)
                    }
                    Text(
                        stringResource(R.string.leaderboard),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                
                // Sync indicator
                if (isSyncing || isRefreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = NeonColors.cyan,
                        strokeWidth = 2.dp
                    )
                } else {
                    IconButton(
                        onClick = { 
                            coroutineScope.launch {
                                isRefreshing = true
                                viewModel.syncLeaderboard()
                                delay(500)
                                isRefreshing = false
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Sync,
                            contentDescription = stringResource(R.string.sync_data),
                            tint = NeonColors.cyan
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            // Mode selector
            ScrollableTabRow(
                selectedTabIndex = getTabIndex(selectedMode),
                containerColor = Surface,
                contentColor = TextPrimary
            ) {
                listOf(
                    stringResource(R.string.all_time),
                    stringResource(R.string.mode_classic),
                    stringResource(R.string.mode_sprint),
                    stringResource(R.string.mode_marathon),
                    stringResource(R.string.mode_zen),
                    stringResource(R.string.mode_puzzle),
                    stringResource(R.string.mode_battle)
                ).forEachIndexed { index, mode ->
                    Tab(
                        selected = selectedMode == mode,
                        onClick = { selectedMode = mode },
                        text = { Text(mode) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Scores list
            if (filteredScores.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Leaderboard,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = TextSecondary
                        )
                        Text(
                            text = "No scores yet",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondary
                        )
                        Text(
                            text = "Be the first to set a high score!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(filteredScores) { index, score ->
                        ScoreCard(
                            rank = index + 1,
                            score = score
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreCard(
    rank: Int,
    score: ScoreEntity
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when(rank) {
                1 -> Color(0xFFFFD700).copy(alpha = 0.2f)
                2 -> Color(0xFFC0C0C0).copy(alpha = 0.2f)
                3 -> Color(0xFFCD7F32).copy(alpha = 0.2f)
                else -> Surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank
            Text(
                text = "#$rank",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = when(rank) {
                    1 -> Color(0xFFFFD700)
                    2 -> Color(0xFFC0C0C0)
                    3 -> Color(0xFFCD7F32)
                    else -> Primary
                },
                modifier = Modifier.width(50.dp)
            )
            
            // Player info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = score.playerName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
                Text(
                    text = "${score.gameMode} • Level ${score.level}",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
            }
            
            // Score
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = score.score.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentCyan
                )
                Text(
                    text = "${score.lines} lines",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
fun getTabIndex(mode: String): Int {
    val modes = listOf(
        stringResource(R.string.all_time),
        stringResource(R.string.mode_classic),
        stringResource(R.string.mode_sprint),
        stringResource(R.string.mode_marathon),
        stringResource(R.string.mode_zen),
        stringResource(R.string.mode_puzzle),
        stringResource(R.string.mode_battle)
    )
    return modes.indexOf(mode).takeIf { it >= 0 } ?: 0
}