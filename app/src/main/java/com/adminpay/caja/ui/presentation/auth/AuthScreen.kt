package com.adminpay.caja.ui.presentation.auth

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val screen = rememberScreenDimensions()

    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(username).matches()
    val isPasswordValid = password.length >= 6
    val isFormValid = isEmailValid && isPasswordValid

    val authState by authViewModel.authState.collectAsState()

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.world))

    LaunchedEffect(username, password) {
        emailError = if (username.isNotBlank() && !isEmailValid) "Correo inválido" else null
        passwordError =
            if (password.isNotBlank() && !isPasswordValid) "Mínimo 6 caracteres" else null
    }

    LaunchedEffect(authState) {
        if (authState is AuthState.Error) {
            scope.launch {
                snackbarHostState.showSnackbar((authState as AuthState.Error).message)
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = screen.widthPercentage(0.09f)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screen.heightPercentage(0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .size(screen.widthPercentage(0.5f))
                    )

                    Image(
                        painter = painterResource(id = R.drawable.sgflogo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(top = screen.heightPercentage(0.2f))
                            .size(screen.widthPercentage(0.3f))

                    )
                }


                Text(
                    text = "Sistema de Gestión de Caja",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontSize = (screen.width.value * 0.02).sp
                    ),
                    modifier = Modifier.padding(bottom = screen.heightPercentage(0.03f))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = screen.widthPercentage(0.1f)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputComponent(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = "Correo electrónico",
                        keyboardType = KeyboardType.Email,
                        error = emailError,
                        leadingIcon = Icons.Default.Person,
                    )

                    Spacer(modifier = Modifier.height(screen.heightPercentage(0.02f)))

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
                        onTrailingIconClick = { passwordVisible = !passwordVisible }
                    )

                    Spacer(modifier = Modifier.height(screen.heightPercentage(0.03f)))

                    Button(
                        onClick = {
                            authViewModel.login(username, password)
                        },
                        enabled = isFormValid,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screen.heightPercentage(0.055f)), // Altura proporcional (~46.dp)
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White,
                            disabledContainerColor = Color.LightGray,
                            disabledContentColor = Color.White


                        )
                    ) {
                        Text(
                            "Iniciar sesión",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = (screen.width.value * 0.016).sp // Tamaño adaptado
                            )
                        )

                    }
                }
            }
        }
    }
}
