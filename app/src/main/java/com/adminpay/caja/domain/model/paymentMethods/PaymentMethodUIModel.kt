package com.adminpay.caja.domain.model.paymentMethods

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class PaymentMethodUIModel(
    val title: String,
    val details: String,
    val icon: Int,
    val tag: String? = null,
    val tagColor: Color = Color.Gray
)

data class PaymentMethodCard(
    val name: String,
    val totalToday: Float,
    val icon: @Composable () -> Unit,
    val barColor: Color
)

data class CardData(
    val name: String,
    val value: Float,
    val currentTotal: Int = 0
)

enum class CardStyle {
    Light, Dark
}
