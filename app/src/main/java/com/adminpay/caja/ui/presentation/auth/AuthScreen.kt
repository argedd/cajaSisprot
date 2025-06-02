package com.adminpay.caja.ui.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun AuthScreen(authViewModel: AuthViewModel ) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.world))
    val screen = rememberScreenDimensions()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo negro completo
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Parte izquierda con animación
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
                    contentDescription = "Imagen del logo",
                    modifier = Modifier
                        .size(screen.widthPercentage(0.5f))
                        .padding(top = 400.dp)

                )
            }

            // Parte derecha con título + Card
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
                    // Texto fuera del Card
                    Text(
                        text = "Sistema de Gestión Financiera",
                        style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Card con borde amarillo directamente
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = Color(0xFFFCA311),
                                shape = MaterialTheme.shapes.extraLarge
                            )
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.shapes.extraLarge
                            )
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            InputComponent(
                                value = username,
                                onValueChange = { username = it },
                                placeholder = "Usuario",
                                keyboardType = KeyboardType.Text,
                                leadingIcon = Icons.Default.Person,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            InputComponent(
                                value = password,
                                onValueChange = { password = it },
                                placeholder = "Contraseña",
                                keyboardType = if (passwordVisible) KeyboardType.Text else KeyboardType.Password,
                                leadingIcon = Icons.Default.Lock,
                                trailingIcon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                onTrailingIconClick = { passwordVisible = !passwordVisible },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            Button(
                                onClick = {
                                    authViewModel.login(email = username, password = password)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = MaterialTheme.shapes.medium,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Text(
                                    text = "Iniciar sesión",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}
