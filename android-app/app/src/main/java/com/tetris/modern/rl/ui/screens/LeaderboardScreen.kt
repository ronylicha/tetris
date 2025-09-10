package com.tetris.modern.rl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val scores by viewModel.topScores.collectAsState(initial = emptyList())
    var selectedMode by remember { mutableStateOf("All") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Leaderboard",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            // Back button and title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    "Leaderboard",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            // Mode selector
            ScrollableTabRow(
                selectedTabIndex = getTabIndex(selectedMode),
                containerColor = Surface,
                contentColor = TextPrimary
            ) {
                listOf("All", "Classic", "Sprint", "Marathon", "Zen", "Puzzle", "Battle").forEachIndexed { index, mode ->
                    Tab(
                        selected = selectedMode == mode,
                        onClick = { selectedMode = mode },
                        text = { Text(mode) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Scores list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(scores) { index, score ->
                    ScoreCard(
                        rank = index + 1,
                        score = score
                    )
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

fun getTabIndex(mode: String): Int {
    return when(mode) {
        "All" -> 0
        "Classic" -> 1
        "Sprint" -> 2
        "Marathon" -> 3
        "Zen" -> 4
        "Puzzle" -> 5
        "Battle" -> 6
        else -> 0
    }
}