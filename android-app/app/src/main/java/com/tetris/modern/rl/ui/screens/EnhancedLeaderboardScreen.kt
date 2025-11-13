package com.tetris.modern.rl.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.tetris.modern.rl.auth.AuthManager
import com.tetris.modern.rl.auth.SyncStrategy
import com.tetris.modern.rl.data.entities.ScoreEntity
import com.tetris.modern.rl.data.repository.ScoreRepository
import com.tetris.modern.rl.sync.SyncManager
import com.tetris.modern.rl.ui.theme.NeonColors
import com.tetris.modern.rl.viewmodel.EnhancedLeaderboardViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedLeaderboardScreen(
    onBack: () -> Unit,
    viewModel: EnhancedLeaderboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val syncState by viewModel.syncState.collectAsStateWithLifecycle()
    val pagedScores = viewModel.pagedScores.collectAsLazyPagingItems()
    
    val scope = rememberCoroutineScope()
    val pullRefreshState = rememberPullToRefreshState()
    val scrollState = rememberLazyListState()
    
    // Handle sync conflict dialog
    var showSyncDialog by remember { mutableStateOf(false) }
    
    LaunchedEffect(syncState) {
        when (syncState) {
            is SyncManager.SyncState.Conflict -> {
                showSyncDialog = true
            }
            else -> {}
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0E27),
                        Color(0xFF1A1E3A)
                    )
                )
            )
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            LeaderboardHeader(
                onBack = onBack,
                authState = authState,
                syncState = syncState,
                onSync = { viewModel.syncScores() }
            )
            
            // Search and Filters
            SearchAndFilters(
                searchQuery = uiState.searchQuery,
                selectedMode = uiState.selectedMode,
                selectedPeriod = uiState.selectedPeriod,
                onSearchChange = viewModel::updateSearch,
                onModeChange = viewModel::selectMode,
                onPeriodChange = viewModel::selectPeriod
            )
            
            // Leaderboard Content
            when {
                uiState.isLoading -> {
                    LoadingContent()
                }
                uiState.error != null -> {
                    ErrorContent(
                        error = uiState.error ?: "Unknown error",
                        onRetry = { viewModel.refresh() }
                    )
                }
                else -> {
                    LeaderboardContent(
                        topScores = uiState.top10Scores,
                        pagedScores = pagedScores,
                        scrollState = scrollState,
                        currentPlayerName = (authState as? AuthManager.AuthState.Authenticated)?.displayName,
                        selectedMode = uiState.selectedMode
                    )
                }
            }
        }
        
        // Pull to refresh indicator
        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        
        // Sync status overlay
        AnimatedVisibility(
            visible = syncState is SyncManager.SyncState.Syncing,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = NeonColors.purple.copy(alpha = 0.9f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Synchronizing scores...",
                        color = Color.White
                    )
                }
            }
        }
    }
    
    // Pull refresh logic
    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.refresh()
            delay(1000)
            pullRefreshState.endRefresh()
        }
    }
    
    // Sync conflict dialog
    val conflictState = syncState as? SyncManager.SyncState.Conflict
    if (showSyncDialog && conflictState != null) {
        SyncConflictDialog(
            localData = conflictState.localData,
            remoteData = conflictState.remoteData,
            onStrategySelected = { strategy ->
                viewModel.resolveSyncConflict(strategy)
                showSyncDialog = false
            },
            onDismiss = { showSyncDialog = false }
        )
    }
}

@Composable
private fun LeaderboardHeader(
    onBack: () -> Unit,
    authState: AuthManager.AuthState,
    syncState: SyncManager.SyncState,
    onSync: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF1E2340).copy(alpha = 0.95f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            
            Text(
                text = "LEADERBOARD",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = NeonColors.cyan,
                modifier = Modifier.weight(1f)
            )
            
            // Auth indicator
            when (authState) {
                is AuthManager.AuthState.Authenticated -> {
                    if (authState.isGuest) {
                        AssistChip(
                            onClick = { },
                            label = { Text("Guest", color = NeonColors.orange) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.PersonOutline,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = NeonColors.orange
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = NeonColors.orange.copy(alpha = 0.2f)
                            )
                        )
                    } else {
                        AssistChip(
                            onClick = { },
                            label = { Text(authState.displayName, color = NeonColors.green) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = NeonColors.green
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = NeonColors.green.copy(alpha = 0.2f)
                            )
                        )
                    }
                }
                else -> {
                    AssistChip(
                        onClick = { },
                        label = { Text("Offline", color = Color.Red) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.CloudOff,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.Red
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.Red.copy(alpha = 0.2f)
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Sync button
            IconButton(
                onClick = onSync,
                enabled = syncState !is SyncManager.SyncState.Syncing
            ) {
                Icon(
                    Icons.Default.Sync,
                    contentDescription = "Sync",
                    tint = if (syncState is SyncManager.SyncState.Syncing) 
                        Color.Gray else Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchAndFilters(
    searchQuery: String,
    selectedMode: String?,
    selectedPeriod: ScoreRepository.TimePeriod,
    onSearchChange: (String) -> Unit,
    onModeChange: (String?) -> Unit,
    onPeriodChange: (ScoreRepository.TimePeriod) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search player...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonColors.cyan,
                unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White.copy(alpha = 0.8f)
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Mode filter chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val modes = listOf(
                null to "All",
                "classic" to "Classic",
                "sprint" to "Sprint",
                "marathon" to "Marathon",
                "zen" to "Zen",
                "puzzle" to "Puzzle",
                "battle" to "Battle"
            )
            
            modes.forEach { (mode, label) ->
                FilterChip(
                    selected = selectedMode == mode,
                    onClick = { onModeChange(mode) },
                    label = { Text(label) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonColors.cyan.copy(alpha = 0.3f),
                        selectedLabelColor = NeonColors.cyan
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Time period filter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ScoreRepository.TimePeriod.values().forEach { period ->
                FilterChip(
                    selected = selectedPeriod == period,
                    onClick = { onPeriodChange(period) },
                    label = { 
                        Text(
                            when (period) {
                                ScoreRepository.TimePeriod.DAY -> "Today"
                                ScoreRepository.TimePeriod.WEEK -> "Week"
                                ScoreRepository.TimePeriod.MONTH -> "Month"
                                ScoreRepository.TimePeriod.ALL_TIME -> "All Time"
                            }
                        )
                    },
                    modifier = Modifier.weight(1f),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonColors.purple.copy(alpha = 0.3f),
                        selectedLabelColor = NeonColors.purple
                    )
                )
            }
        }
    }
}

@Composable
private fun LeaderboardContent(
    topScores: List<ScoreEntity>,
    pagedScores: LazyPagingItems<ScoreEntity>,
    scrollState: LazyListState,
    currentPlayerName: String?,
    selectedMode: String?
) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Top 10 with special styling
        if (topScores.isNotEmpty()) {
            item {
                Text(
                    text = "TOP 10",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonColors.cyan,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            itemsIndexed(topScores) { index, score ->
                TopScoreCard(
                    rank = index + 1,
                    score = score,
                    isCurrentPlayer = score.playerName == currentPlayerName
                )
            }
            
            if (pagedScores.itemCount > 0) {
                item {
                    Divider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = Color.White.copy(alpha = 0.2f)
                    )
                    Text(
                        text = "ALL SCORES",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonColors.purple,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
        
        // Paginated scores
        items(
            count = pagedScores.itemCount,
            key = pagedScores.itemKey { it.id }
        ) { index ->
            val score = pagedScores[index]
            score?.let {
                RegularScoreCard(
                    rank = topScores.size + index + 1,
                    score = it,
                    isCurrentPlayer = it.playerName == currentPlayerName
                )
            }
        }
        
        // Loading indicator for pagination
        if (pagedScores.loadState.append is androidx.paging.LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = NeonColors.cyan,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun TopScoreCard(
    rank: Int,
    score: ScoreEntity,
    isCurrentPlayer: Boolean
) {
    val rankColor = when (rank) {
        1 -> Color(0xFFFFD700) // Gold
        2 -> Color(0xFFC0C0C0) // Silver
        3 -> Color(0xFFCD7F32) // Bronze
        else -> Color.White
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrentPlayer) 
                NeonColors.cyan.copy(alpha = 0.1f) 
            else Color(0xFF1E2340).copy(alpha = 0.7f)
        ),
        border = if (isCurrentPlayer) BorderStroke(2.dp, NeonColors.cyan) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank badge
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                rankColor.copy(alpha = 0.3f),
                                rankColor.copy(alpha = 0.1f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = rank.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = rankColor
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Player info
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = score.playerName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCurrentPlayer) NeonColors.cyan else Color.White
                    )
                    if (isCurrentPlayer) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Badge(
                            containerColor = NeonColors.cyan.copy(alpha = 0.2f)
                        ) {
                            Text("YOU", color = NeonColors.cyan)
                        }
                    }
                }
                
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Lines: ${score.lines}",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "Level: ${score.level}",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                    Text(
                        text = formatDate(score.date),
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
            
            // Score
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = NumberFormat.getInstance().format(score.score),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = rankColor
                )
                Text(
                    text = score.gameMode.uppercase(),
                    fontSize = 10.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun RegularScoreCard(
    rank: Int,
    score: ScoreEntity,
    isCurrentPlayer: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrentPlayer) 
                NeonColors.cyan.copy(alpha = 0.05f) 
            else Color(0xFF1E2340).copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#$rank",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.width(40.dp)
            )
            
            Text(
                text = score.playerName,
                fontSize = 14.sp,
                color = if (isCurrentPlayer) NeonColors.cyan else Color.White,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = NumberFormat.getInstance().format(score.score),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = NeonColors.cyan,
            strokeWidth = 3.dp
        )
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Error,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color.Red
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = error,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = NeonColors.cyan
            )
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Retry")
        }
    }
}

@Composable
private fun SyncConflictDialog(
    localData: SyncManager.SyncData,
    remoteData: SyncManager.SyncData,
    onStrategySelected: (SyncStrategy) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Sync Conflict Detected", color = NeonColors.cyan)
        },
        text = {
            Column {
                Text("Your local data differs from the cloud data.")
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Local data summary
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = NeonColors.purple.copy(alpha = 0.1f)
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Local Data", fontWeight = FontWeight.Bold)
                        Text("Games: ${localData.gamesPlayed}")
                        Text("Highest: ${NumberFormat.getInstance().format(localData.highestScore)}")
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Remote data summary
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = NeonColors.cyan.copy(alpha = 0.1f)
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Cloud Data", fontWeight = FontWeight.Bold)
                        Text("Games: ${remoteData.gamesPlayed}")
                        Text("Highest: ${NumberFormat.getInstance().format(remoteData.highestScore)}")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onStrategySelected(SyncStrategy.MERGE_BOTH) }) {
                Text("Merge Both")
            }
        },
        dismissButton = {
            Row {
                TextButton(onClick = { onStrategySelected(SyncStrategy.KEEP_LOCAL) }) {
                    Text("Keep Local")
                }
                TextButton(onClick = { onStrategySelected(SyncStrategy.KEEP_REMOTE) }) {
                    Text("Keep Cloud")
                }
            }
        }
    )
}

private fun formatDate(date: Date): String {
    val now = Date()
    val diff = now.time - date.time
    val days = diff / (1000 * 60 * 60 * 24)
    
    return when {
        days == 0L -> "Today"
        days == 1L -> "Yesterday"
        days < 7 -> "$days days ago"
        else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(date)
    }
}