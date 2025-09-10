package com.tetris.modern.rl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tetris.modern.rl.R
import com.tetris.modern.rl.ui.theme.*
import com.tetris.modern.rl.ui.viewmodels.MainViewModel
import com.tetris.modern.rl.ui.viewmodels.SettingsViewModel
import android.app.Activity
import android.content.Intent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState(initial = null)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Back button and title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back), tint = Color.White)
                }
                Text(
                    stringResource(R.string.settings),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            // General Settings
            SettingsSection(title = stringResource(R.string.general)) {
                // Language Selection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.language), color = TextPrimary)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = settingsViewModel.currentLanguage.value == "en",
                            onClick = { 
                                coroutineScope.launch {
                                    settingsViewModel.setLanguage("en")
                                    delay(100) // Small delay to ensure preference is saved
                                    
                                    // Restart the entire application
                                    val activity = context as? Activity
                                    activity?.let {
                                        val intent = it.packageManager.getLaunchIntentForPackage(it.packageName)
                                        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        it.startActivity(intent)
                                        it.finishAffinity()
                                        android.os.Process.killProcess(android.os.Process.myPid())
                                    }
                                }
                            },
                            label = { Text(stringResource(R.string.language_english)) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Primary,
                                selectedLabelColor = Color.Black
                            )
                        )
                        FilterChip(
                            selected = settingsViewModel.currentLanguage.value == "fr",
                            onClick = { 
                                coroutineScope.launch {
                                    settingsViewModel.setLanguage("fr")
                                    delay(100) // Small delay to ensure preference is saved
                                    
                                    // Restart the entire application
                                    val activity = context as? Activity
                                    activity?.let {
                                        val intent = it.packageManager.getLaunchIntentForPackage(it.packageName)
                                        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        it.startActivity(intent)
                                        it.finishAffinity()
                                        android.os.Process.killProcess(android.os.Process.myPid())
                                    }
                                }
                            },
                            label = { Text(stringResource(R.string.language_french)) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Primary,
                                selectedLabelColor = Color.Black
                            )
                        )
                    }
                }
            }
            
            // Audio Settings
            SettingsSection(title = stringResource(R.string.audio)) {
                VolumeSlider(
                    label = stringResource(R.string.master_volume),
                    value = settings?.masterVolume ?: 1f,
                    onValueChange = { viewModel.updateMasterVolume(it) }
                )
                VolumeSlider(
                    label = stringResource(R.string.music_volume),
                    value = settings?.musicVolume ?: 0.8f,
                    onValueChange = { viewModel.updateMusicVolume(it) }
                )
                VolumeSlider(
                    label = stringResource(R.string.sfx_volume),
                    value = settings?.sfxVolume ?: 1f,
                    onValueChange = { viewModel.updateSfxVolume(it) }
                )
            }
            
            // Controls Settings
            SettingsSection(title = stringResource(R.string.controls)) {
                SwitchSetting(
                    label = stringResource(R.string.vibration),
                    checked = settings?.vibrationEnabled ?: true,
                    onCheckedChange = { viewModel.updateVibration(it) }
                )
                SliderSetting(
                    label = stringResource(R.string.touch_sensitivity),
                    value = settings?.touchSensitivity ?: 1f,
                    onValueChange = { viewModel.updateTouchSensitivity(it) }
                )
                SliderSetting(
                    label = stringResource(R.string.das_delay),
                    value = (settings?.das ?: 100).toFloat(),
                    range = 50f..200f,
                    onValueChange = { viewModel.updateDAS(it.toInt()) }
                )
                SliderSetting(
                    label = stringResource(R.string.arr_rate),
                    value = (settings?.arr ?: 20).toFloat(),
                    range = 0f..50f,
                    onValueChange = { viewModel.updateARR(it.toInt()) }
                )
            }
            
            // Gameplay Settings
            SettingsSection(title = stringResource(R.string.gameplay)) {
                SwitchSetting(
                    label = "Show Ghost Piece",
                    checked = settings?.showGhostPiece ?: true,
                    onCheckedChange = { viewModel.updateGhostPiece(it) }
                )
                SliderSetting(
                    label = "Next Pieces",
                    value = (settings?.showNextPieces ?: 4).toFloat(),
                    range = 1f..6f,
                    steps = 5,
                    onValueChange = { viewModel.updateNextPieces(it.toInt()) }
                )
                SwitchSetting(
                    label = "Auto Hold",
                    checked = settings?.autoHold ?: false,
                    onCheckedChange = { viewModel.updateAutoHold(it) }
                )
            }
            
            // Account & Sync
            SettingsSection(title = stringResource(R.string.account_sync)) {
                val isGooglePlaySignedIn by viewModel.isGooglePlaySignedIn.collectAsState()
                
                if (isGooglePlaySignedIn) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Google Play Games", color = TextPrimary)
                            Text("Connected", fontSize = 12.sp, color = NeonColors.Green)
                        }
                        OutlinedButton(
                            onClick = { /* Sign out */ },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Error
                            )
                        ) {
                            Text("Sign Out")
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Google Play Games", color = TextPrimary)
                            Text("Not connected", fontSize = 12.sp, color = TextSecondary)
                        }
                        Button(
                            onClick = { 
                                // Trigger Google Play sign in
                                // This should be handled by MainActivity
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonColors.Cyan,
                                contentColor = Color.Black
                            )
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Google Play")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Connect")
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    "Connect to save your progress and compete on leaderboards",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
            
            // About
            SettingsSection(title = stringResource(R.string.about)) {
                InfoRow(label = stringResource(R.string.version), value = "1.1.0")
                InfoRow(label = stringResource(R.string.developer), value = stringResource(R.string.developer_name))
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = Surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun VolumeSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = TextPrimary)
            Text("${(value * 100).toInt()}%", color = TextSecondary)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            colors = SliderDefaults.colors(
                thumbColor = Primary,
                activeTrackColor = Primary
            )
        )
    }
}

@Composable
fun SliderSetting(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChange: (Float) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = TextPrimary)
            Text(value.toInt().toString(), color = TextSecondary)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = range,
            steps = steps,
            colors = SliderDefaults.colors(
                thumbColor = Primary,
                activeTrackColor = Primary
            )
        )
    }
}

@Composable
fun SwitchSetting(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextPrimary)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Primary,
                checkedTrackColor = Primary.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextPrimary)
        Text(value, color = TextSecondary)
    }
}