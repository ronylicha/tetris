package com.tetris.modern.rl.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tetris.modern.rl.playgames.PlayGamesManager
import com.tetris.modern.rl.ui.theme.NeonColors

@Composable
fun ProfileButton(
    playGamesState: PlayGamesManager.PlayGamesState,
    isAuthenticated: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(
                if (isAuthenticated || playGamesState.isSignedIn) {
                    Brush.horizontalGradient(
                        colors = listOf(
                            NeonColors.green.copy(alpha = 0.2f),
                            NeonColors.cyan.copy(alpha = 0.2f)
                        )
                    )
                } else {
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.1f),
                            Color.White.copy(alpha = 0.05f)
                        )
                    )
                }
            )
            .border(
                width = 1.dp,
                brush = if (isAuthenticated || playGamesState.isSignedIn) {
                    Brush.horizontalGradient(
                        colors = listOf(NeonColors.green, NeonColors.cyan)
                    )
                } else {
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f),
                            Color.White.copy(alpha = 0.2f)
                        )
                    )
                },
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Profile Icon or Avatar
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (playGamesState.isSignedIn) {
                            Color.Transparent
                        } else {
                            Color.White.copy(alpha = 0.1f)
                        }
                    )
            ) {
                when {
                    playGamesState.playerAvatarUrl != null -> {
                        AsyncImage(
                            model = playGamesState.playerAvatarUrl,
                            contentDescription = "Player Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    playGamesState.isSignedIn -> {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Signed In",
                            tint = NeonColors.green,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        )
                    }
                    isAuthenticated -> {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Authenticated",
                            tint = NeonColors.cyan,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        )
                    }
                    else -> {
                        Icon(
                            imageVector = Icons.Default.PersonOutline,
                            contentDescription = "Not Signed In",
                            tint = Color.White.copy(alpha = 0.5f),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        )
                    }
                }
            }
            
            // Status Text
            Column {
                Text(
                    text = when {
                        playGamesState.playerName != null -> playGamesState.playerName
                        playGamesState.isSignedIn -> "Play Games"
                        isAuthenticated -> "Connected"
                        else -> "Sign In"
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                
                // Connection Status Indicator
                if (playGamesState.isSignedIn || isAuthenticated) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(NeonColors.green)
                        )
                        Text(
                            text = if (playGamesState.isSignedIn) "Play Games" else "Online",
                            fontSize = 10.sp,
                            color = NeonColors.green
                        )
                    }
                }
            }
            
            // Google Play Games Icon if connected
            if (playGamesState.isSignedIn) {
                Icon(
                    imageVector = Icons.Default.Games,
                    contentDescription = "Google Play Games",
                    tint = NeonColors.green,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileButtonCompact(
    playGamesState: PlayGamesManager.PlayGamesState,
    isAuthenticated: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(
                brush = if (isAuthenticated || playGamesState.isSignedIn) {
                    Brush.radialGradient(
                        colors = listOf(
                            NeonColors.green.copy(alpha = 0.3f),
                            NeonColors.cyan.copy(alpha = 0.1f)
                        )
                    )
                } else {
                    Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.1f),
                            Color.White.copy(alpha = 0.05f)
                        )
                    )
                }
            )
            .border(
                width = 2.dp,
                color = if (isAuthenticated || playGamesState.isSignedIn) {
                    NeonColors.green
                } else {
                    Color.White.copy(alpha = 0.3f)
                },
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        when {
            playGamesState.playerAvatarUrl != null -> {
                AsyncImage(
                    model = playGamesState.playerAvatarUrl,
                    contentDescription = "Player Avatar",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            playGamesState.isSignedIn -> {
                Icon(
                    imageVector = Icons.Default.Games,
                    contentDescription = "Google Play Games",
                    tint = NeonColors.green,
                    modifier = Modifier.size(24.dp)
                )
            }
            isAuthenticated -> {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Authenticated",
                    tint = NeonColors.cyan,
                    modifier = Modifier.size(24.dp)
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.Default.PersonOutline,
                    contentDescription = "Sign In",
                    tint = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        
        // Online indicator dot
        if (playGamesState.isSignedIn || isAuthenticated) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(NeonColors.green)
                )
            }
        }
    }
}