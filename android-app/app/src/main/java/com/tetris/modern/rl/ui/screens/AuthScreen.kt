package com.tetris.modern.rl.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.tetris.modern.rl.auth.AuthManager
import com.tetris.modern.rl.ui.theme.NeonColors
import com.tetris.modern.rl.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import android.provider.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit,
    onGuestMode: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    android.util.Log.d("AuthScreen", "AuthScreen composable is rendering")
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    val usernameAvailable by viewModel.usernameAvailable.collectAsStateWithLifecycle()
    val emailAvailable by viewModel.emailAvailable.collectAsStateWithLifecycle()
    
    // Check user status
    val authenticatedState = authState as? AuthManager.AuthState.Authenticated
    val isGuestUser = authenticatedState?.isGuest == true
    val isAuthenticatedUser = authenticatedState != null && !authenticatedState.isGuest
    val currentUsername = authenticatedState?.username
    val currentDisplayName = authenticatedState?.displayName
    
    var isLoginMode by remember { mutableStateOf(true) }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    
    // Google Sign-In launcher
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.let {
                scope.launch {
                    viewModel.loginWithGoogle(it)
                }
            }
        } catch (e: ApiException) {
            viewModel.setError("Google sign-in failed: ${e.message}")
        }
    }
    
    // Handle auth state changes
    // Don't auto-navigate if user is already authenticated as guest
    // This allows guest users to see the upgrade screen
    LaunchedEffect(authState) {
        when (authState) {
            is AuthManager.AuthState.Authenticated -> {
                // Comment out auto-navigation to let users see their status
                // onAuthSuccess()
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
                .padding(top = 48.dp), // Add extra padding to avoid overlapping with back button
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo/Title
            Text(
                text = "TETRIS",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = NeonColors.cyan,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = when {
                    isAuthenticatedUser -> "Your Account"
                    isGuestUser -> "Upgrade Your Account"
                    isLoginMode -> "Welcome Back!"
                    else -> "Create Account"
                },
                fontSize = 20.sp,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Show authenticated user status
            if (isAuthenticatedUser) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = NeonColors.cyan.copy(alpha = 0.1f)
                    ),
                    border = BorderStroke(1.dp, NeonColors.cyan.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Authenticated",
                            tint = NeonColors.cyan,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(bottom = 8.dp)
                        )
                        Text(
                            text = "You're signed in as",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = currentDisplayName ?: currentUsername ?: "User",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonColors.cyan,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Sign Out Button
                        Button(
                            onClick = {
                                viewModel.logout()
                                onGuestMode() // Return to home or login screen
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red.copy(alpha = 0.2f)
                            ),
                            border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = "Sign Out",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Sign Out",
                                color = Color.Red
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Continue Playing Button
                        OutlinedButton(
                            onClick = onAuthSuccess,
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(1.dp, NeonColors.cyan)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Games,
                                contentDescription = "Continue",
                                modifier = Modifier.size(20.dp),
                                tint = NeonColors.cyan
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Continue Playing",
                                color = NeonColors.cyan
                            )
                        }
                    }
                }
            }
            
            // Show guest status if applicable
            else if (isGuestUser) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = NeonColors.green.copy(alpha = 0.1f)
                    ),
                    border = BorderStroke(1.dp, NeonColors.green.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Currently playing as:",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Text(
                                text = currentUsername ?: "Guest",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonColors.green
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Guest",
                            tint = NeonColors.green,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                
                Text(
                    text = "Sign in or create an account to save your progress!",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            // Auth Form Card - Only show if not authenticated (or guest wanting to upgrade)
            if (!isAuthenticatedUser) {
                Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E2340).copy(alpha = 0.95f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Form Fields
                    AnimatedVisibility(
                        visible = !isLoginMode,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column {
                            OutlinedTextField(
                                value = username,
                                onValueChange = { 
                                    username = it.trim()
                                    // Check availability after 3+ characters
                                    if (username.length >= 3) {
                                        scope.launch {
                                            viewModel.checkUsernameAvailability(username)
                                        }
                                    }
                                },
                                label = { Text("Username") },
                                leadingIcon = {
                                    Icon(Icons.Default.Person, contentDescription = null)
                                },
                                trailingIcon = {
                                    when {
                                        username.length < 3 -> null
                                        usernameAvailable == true -> Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = "Available",
                                            tint = Color.Green
                                        )
                                        usernameAvailable == false -> Icon(
                                            Icons.Default.Cancel,
                                            contentDescription = "Not available",
                                            tint = Color.Red
                                        )
                                        else -> CircularProgressIndicator(
                                            modifier = Modifier.size(20.dp),
                                            strokeWidth = 2.dp
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NeonColors.cyan,
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White.copy(alpha = 0.8f)
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                singleLine = true
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            OutlinedTextField(
                                value = displayName,
                                onValueChange = { displayName = it.trim() },
                                label = { Text("Display Name") },
                                leadingIcon = {
                                    Icon(Icons.Default.Badge, contentDescription = null)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NeonColors.cyan,
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White.copy(alpha = 0.8f)
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                singleLine = true
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                    
                    OutlinedTextField(
                        value = email,
                        onValueChange = { 
                            email = it.trim()
                            // Check availability if valid email format and in register mode
                            if (!isLoginMode && email.contains("@") && email.contains(".")) {
                                scope.launch {
                                    viewModel.checkEmailAvailability(email)
                                }
                            }
                        },
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null)
                        },
                        trailingIcon = {
                            if (!isLoginMode) {
                                when {
                                    !email.contains("@") || !email.contains(".") -> null
                                    emailAvailable == true -> Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = "Available",
                                        tint = Color.Green
                                    )
                                    emailAvailable == false -> Icon(
                                        Icons.Default.Cancel,
                                        contentDescription = "Already in use",
                                        tint = Color.Red
                                    )
                                    else -> null
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonColors.cyan,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White.copy(alpha = 0.8f)
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonColors.cyan,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White.copy(alpha = 0.8f)
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = if (isLoginMode) ImeAction.Done else ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { 
                                if (!isLoginMode) focusManager.moveFocus(FocusDirection.Down)
                            },
                            onDone = {
                                if (isLoginMode) {
                                    scope.launch {
                                        viewModel.login(email.trim(), password)
                                    }
                                }
                            }
                        ),
                        singleLine = true
                    )
                    
                    AnimatedVisibility(
                        visible = !isLoginMode,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            OutlinedTextField(
                                value = confirmPassword,
                                onValueChange = { confirmPassword = it },
                                label = { Text("Confirm Password") },
                                leadingIcon = {
                                    Icon(Icons.Default.Lock, contentDescription = null)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NeonColors.cyan,
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White.copy(alpha = 0.8f)
                                ),
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        scope.launch {
                                            if (password == confirmPassword) {
                                                viewModel.register(username.trim(), email.trim(), password, displayName.trim().ifEmpty { username.trim() })
                                            } else {
                                                viewModel.setError("Passwords don't match")
                                            }
                                        }
                                    }
                                ),
                                singleLine = true,
                                isError = confirmPassword.isNotEmpty() && password != confirmPassword
                            )
                        }
                    }
                    
                    // Error Message
                    AnimatedVisibility(
                        visible = errorMessage != null,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                            )
                        ) {
                            Text(
                                text = errorMessage ?: "",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Submit Button
                    Button(
                        onClick = {
                            scope.launch {
                                if (isLoginMode) {
                                    viewModel.login(email.trim(), password)
                                } else {
                                    if (password == confirmPassword) {
                                        viewModel.register(username.trim(), email.trim(), password, displayName.trim().ifEmpty { username.trim() })
                                    } else {
                                        viewModel.setError("Passwords don't match")
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = !isLoading && email.isNotEmpty() && password.isNotEmpty() &&
                                (isLoginMode || (username.isNotEmpty() && password == confirmPassword)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonColors.cyan,
                            disabledContainerColor = NeonColors.cyan.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text(
                                text = if (isLoginMode) "Sign In" else "Create Account",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    // Divider
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Divider(
                            modifier = Modifier.weight(1f),
                            color = Color.White.copy(alpha = 0.2f)
                        )
                        Text(
                            text = "OR",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.White.copy(alpha = 0.5f)
                        )
                        Divider(
                            modifier = Modifier.weight(1f),
                            color = Color.White.copy(alpha = 0.2f)
                        )
                    }
                    
                    // Google Sign In Button
                    OutlinedButton(
                        onClick = {
                            viewModel.getGoogleSignInIntent()?.let { intent ->
                                googleSignInLauncher.launch(intent)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = Brush.horizontalGradient(
                                colors = listOf(NeonColors.cyan, NeonColors.purple)
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Android,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Sign in with Google")
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Guest Mode Button - if already guest, just go back
                    TextButton(
                        onClick = {
                            if (isGuestUser) {
                                // Already logged in as guest, just go back
                                onGuestMode()
                            } else {
                                // Not logged in, create new guest account
                                val deviceId = Settings.Secure.getString(
                                    context.contentResolver,
                                    Settings.Secure.ANDROID_ID
                                )
                                scope.launch {
                                    viewModel.loginAsGuest(deviceId)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (isGuestUser) "Continue Playing" else "Continue as Guest",
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Toggle Mode
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (isLoginMode) "Don't have an account? " else "Already have an account? ",
                            color = Color.White.copy(alpha = 0.6f)
                        )
                        Text(
                            text = if (isLoginMode) "Sign Up" else "Sign In",
                            color = NeonColors.cyan,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                isLoginMode = !isLoginMode
                                // Clear form
                                username = ""
                                displayName = ""
                                confirmPassword = ""
                                viewModel.clearError()
                            }
                        )
                    }
                }
            }
            } // End of Card for auth form
        }
        
        // Back button in top left corner - placed last to be on top
        IconButton(
            onClick = {
                android.util.Log.d("AuthScreen", "Back button clicked")
                onGuestMode()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(48.dp) // Make button bigger for easier clicking
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = NeonColors.cyan,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}