package com.tetris.modern.rl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TestAuthScreen(
    onAuthSuccess: () -> Unit,
    onGuestMode: () -> Unit
) {
    android.util.Log.d("TestAuthScreen", "TestAuthScreen is rendering")
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "TEST AUTH SCREEN",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            
            Button(
                onClick = onGuestMode,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Back to Home", color = Color.White)
            }
            
            Button(
                onClick = onAuthSuccess,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text("Auth Success", color = Color.White)
            }
        }
    }
}