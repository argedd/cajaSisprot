package com.adminpay.caja.ui.presentation.finish

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.adminpay.caja.R
import com.adminpay.caja.ui.navigation.Routes
import com.adminpay.caja.utils.rememberScreenDimensions
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun FinishScreen(navController: NavHostController) {
    val screen = rememberScreenDimensions()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success))
    val iconSize = screen.widthPercentage(0.15f)
    val cardWidth = screen.widthPercentage(0.6f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🟦 Tarjeta que contiene toda la información visual
        Card(
            modifier = Modifier.width(cardWidth),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ✅ Animación
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(iconSize)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ Título de éxito en verde
                Text(
                    text = "¡Pago exitoso!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50), // Verde de éxito
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                // ✅ Mensaje descriptivo
                Text(
                    text = "El pago fue exitoso y la nota de cobro está totalmente pagada.",
                    fontSize = 20.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ✅ Botón Finalizar
                Button(
                    onClick = { navController.navigate(Routes.ContractScreen.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004C72))
                ) {
                    Text(
                        text = "Finalizar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
