package com.tetris.modern.rl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tetris.modern.rl.ui.theme.NeonColors

@Composable
fun ControlSelectionDialog(
    onControlTypeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedType by remember { mutableStateOf("touch") }
    
    Dialog(onDismissRequest = { }) { // Cannot dismiss without selecting
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1A2E)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Gamepad,
                    contentDescription = "Controls",
                    modifier = Modifier.size(48.dp),
                    tint = NeonColors.Cyan
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Choose Your Control Style",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Select how you prefer to play",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Touch Controls Option
                ControlOption(
                    title = "Touch Controls",
                    description = "Swipe and tap gestures\n• Tap: Rotate\n• Swipe Left/Right: Move\n• Swipe Down: Soft Drop\n• Swipe Up: Hard Drop\n• Long Press: Hold",
                    icon = Icons.Default.TouchApp,
                    isSelected = selectedType == "touch",
                    onClick = { selectedType = "touch" }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Pad Controls Option
                ControlOption(
                    title = "Virtual Pad",
                    description = "On-screen buttons\n• D-Pad for movement\n• Action buttons for rotate/drop\n• Hold button\n• Traditional controls",
                    icon = Icons.Default.Gamepad,
                    isSelected = selectedType == "pad",
                    onClick = { selectedType = "pad" }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Confirm Button
                Button(
                    onClick = {
                        onControlTypeSelected(selectedType)
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonColors.Cyan
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "CONFIRM",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "You can change this later in Settings",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ControlOption(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = NeonColors.Cyan,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                Color(0xFF2A2A3E)
            } else {
                Color(0xFF222234)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = if (isSelected) NeonColors.Cyan else Color.Gray
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.White else Color.Gray
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )
            }
            
            if (isSelected) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    modifier = Modifier.size(24.dp),
                    tint = NeonColors.Cyan
                )
            }
        }
    }
}