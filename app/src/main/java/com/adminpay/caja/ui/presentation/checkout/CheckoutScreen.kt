package com.adminpay.caja.ui.presentation.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.PaymentMethodsScreen
import com.adminpay.caja.ui.presentation.checkout.components.sumary.CheckoutSummary
import com.adminpay.caja.utils.ScreenDimensions

@Composable
fun CheckoutScreen(
    screen: ScreenDimensions,
    serviceId: String,
    navController: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Contenido principal
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Parte izquierda: Métodos de pago
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                PaymentMethodsScreen(
                    screen = screen,
                    serviceId = serviceId
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Parte derecha: Resumen de compra
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                CheckoutSummary(screen = screen)
            }
        }

        // Botón de volver en la parte superior derecha
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver"
            )
        }
    }
}
