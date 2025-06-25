package com.adminpay.caja.domain.model.paymentMethods

import androidx.compose.ui.graphics.Color

data class PaymentMethodUIModel(
    val title: String,
    val details: String,
    val icon: Int,
    val tag: String? = null,
    val tagColor: Color = Color.Gray
)
