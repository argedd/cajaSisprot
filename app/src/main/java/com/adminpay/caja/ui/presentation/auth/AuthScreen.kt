package com.adminpay.caja.ui.presentation.auth

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.adminpay.caja.R
import com.adminpay.caja.ui.presentation.components.InputComponent
import com.adminpay.caja.utils.rememberScreenDimensions
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(authViewModel: AuthViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.world))
    val screen = rememberScreenDimensions()

    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(username).matches()
    val isPasswordValid = password.length >= 6
    val isFormValid = isEmailValid && isPasswordValid

    val authState by authViewModel.authState.collectAsState()

    // Validación en tiempo real
    LaunchedEffect(username, password) {
        emailError = if (username.isNotBlank() && !isEmailValid) "Correo inválido" else null
        passwordError = if (password.isNotBlank() && !isPasswordValid) "Mínimo 6 caracteres" else null
    }

    // Mostrar Snackbar si hay error
    LaunchedEffect(authState) {
        if (authState is AuthState.Error) {
            scope.launch {
                snackbarHostState.showSnackbar((authState as AuthState.Error).message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {

                // Animación y Logo
                Box(
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .size(screen.widthPercentage(0.50f))
                            .padding(horizontal = 32.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.sgflogo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(screen.widthPercentage(0.5f))
                            .padding(top = 400.dp)
                    )
                }

                // Formulario
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        Text(
                            text = "Sistema de Gestión Financiera",
                            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFFFCA311),
                                    shape = MaterialTheme.shapes.extraLarge
                                )
                                .background(
                                    color = Color.White,
                                    shape = MaterialTheme.shapes.extraLarge
                                )
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                InputComponent(
                                    value = username,
                                    onValueChange = { username = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    placeholder = "Correo electrónico",
                                    keyboardType = KeyboardType.Email,
                                    error = emailError,
                                    leadingIcon = Icons.Default.Person,
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                InputComponent(
                                    value = password,
                                    onValueChange = { password = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    placeholder = "Contraseña",
                                    keyboardType = if (passwordVisible) KeyboardType.Text else KeyboardType.Password,
                                    error = passwordError,
                                    isPassword = !passwordVisible,
                                    leadingIcon = Icons.Default.Lock,
                                    trailingIcon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    onTrailingIconClick = {
                                        passwordVisible = !passwordVisible
                                    },
                                )

                                Spacer(modifier = Modifier.height(32.dp))

                                Button(
                                    onClick = {
                                        authViewModel.login(username, password)
                                    },
                                    enabled = isFormValid,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(46.dp),
                                    shape = MaterialTheme.shapes.medium,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isFormValid)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            Color.Gray,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                ) {
                                    Text("Iniciar sesión", style = MaterialTheme.typography.labelLarge)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
