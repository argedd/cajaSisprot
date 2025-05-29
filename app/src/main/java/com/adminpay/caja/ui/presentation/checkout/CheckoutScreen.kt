package com.adminpay.caja.ui.presentation.checkout


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.PaymentMethodsScreen
import com.adminpay.caja.ui.presentation.checkout.components.sumary.CheckoutSummary
import com.adminpay.caja.utils.ScreenDimensions

@Composable
fun CheckoutScreen(
    screen: ScreenDimensions,
    serviceId: String
) {
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
}
