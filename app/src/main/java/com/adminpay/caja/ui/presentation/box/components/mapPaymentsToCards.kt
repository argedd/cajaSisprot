package com.adminpay.caja.ui.presentation.box.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.adminpay.caja.domain.model.payment.get.PaymentSummary
import com.adminpay.caja.domain.model.paymentMethods.PaymentMethodCard

/**
 * Genera el ícono correspondiente al método de pago.
 */
fun iconForMethod(methodName: String, tint: Color): @Composable () -> Unit {
    val icon: ImageVector = when (methodName.uppercase()) {
        "EFECTIVO BS", "EFECTIVO $" -> Icons.Default.AttachMoney
        "PAGO MOVIL" -> Icons.Default.QrCode
        "TRANSFERENCIA" -> Icons.Default.SwapHoriz
        "BINANCE" -> Icons.Default.CurrencyBitcoin
        "PAYPAL" -> Icons.Default.AccountBalanceWallet
        "ZELLE" -> Icons.Default.AttachMoney
        "PUNTO DE VENTA" -> Icons.Default.CreditCard
        else -> Icons.Default.Payment
    }

    return {
        Icon(imageVector = icon, contentDescription = methodName, tint = tint)
    }
}

/**
 * Devuelve el color principal del método de pago.
 */
fun colorForMethod(methodName: String): Color {
    return when (methodName.uppercase()) {
        "EFECTIVO BS" -> Color(0xFF4CAF50)
        "EFECTIVO $" -> Color(0xFF81C784)
        "PAGO MOVIL" -> Color(0xFF00BCD4)
        "TRANSFERENCIA" -> Color(0xFF3F51B5)
        "BINANCE" -> Color(0xFFFFC107)
        "PAYPAL" -> Color(0xFF2962FF)
        "ZELLE" -> Color(0xFF9C27B0)
        "PUNTO DE VENTA" -> Color(0xFFFF9800)
        else -> Color.Gray
    }
}

/**
 * Mapea una lista de resumen de pagos en tarjetas de UI.
 */
fun mapSummaryToCards(data: List<PaymentSummary>): List<PaymentMethodCard> {
    return data.map { item ->
        val methodName = item.methodName.uppercase()
        val color = colorForMethod(methodName)
        val icon = iconForMethod(methodName, Color.White)

        PaymentMethodCard(
            name = item.methodName,
            totalToday = item.amount, // Asegúrate de que esto sea Float
            icon = icon,
            barColor = color
        )
    }
}
