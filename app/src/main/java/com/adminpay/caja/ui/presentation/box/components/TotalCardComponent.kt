package com.adminpay.caja.ui.presentation.box.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.domain.model.paymentMethods.CardData
import com.adminpay.caja.domain.model.paymentMethods.CardStyle
@Composable
fun TotalCard(
    style: CardStyle,
    data: CardData,
    modifier: Modifier = Modifier
) {
    val bgColor = if (style == CardStyle.Light) Color(0xFFF5F5F5) else MaterialTheme.colorScheme.primary
    val textColor = if (style == CardStyle.Light) Color.Black else Color.White

    Card(
        modifier = modifier.height(100.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = data.name, fontSize = 16.sp, color = textColor)
            Text(
                text = if (data.name.contains("Transacciones", true)) data.currentTotal.toString() else "$${data.value.toInt()}",
                fontSize = 20.sp,
                color = textColor
            )
        }
    }
}