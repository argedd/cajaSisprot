package com.adminpay.caja.ui.presentation.box.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.domain.model.paymentMethods.CardData
import com.adminpay.caja.domain.model.paymentMethods.CardStyle
import com.adminpay.caja.utils.rememberScreenDimensions
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TotalCard(
    style: CardStyle,
    data: CardData,
    modifier: Modifier = Modifier
) {
    val bgColor = MaterialTheme.colorScheme.primary
    val textColor = if (style == CardStyle.Light) Color.Black else Color.White
    val accentColor = MaterialTheme.colorScheme.secondary

    val screen = rememberScreenDimensions()
    val titleFontSize = (screen.width.value * 0.015).sp
    val amountFontSize = (screen.width.value * 0.018).sp
    val iconSize = screen.widthPercentage(0.04f)
    val horizontalPadding = screen.widthPercentage(0.02f)
    val verticalPadding = screen.heightPercentage(0.012f)

    val icon: ImageVector = when {
        data.name.contains("Transacciones", ignoreCase = true) -> Icons.Default.SwapHoriz
        data.name.contains("Total Generado", ignoreCase = true) -> Icons.Default.AttachMoney
        else -> Icons.Default.AttachMoney
    }

    Card(
        modifier = modifier
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
            .fillMaxWidth()
            .height(90.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalPadding, vertical = verticalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier
                    .size(iconSize)
                    .padding(end = 12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = data.name,
                    fontSize = titleFontSize,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (data.name.contains("Transacciones", true))
                        data.currentTotal.toString()
                    else
                        "USD ${data.value.toInt()}",
                    fontSize = amountFontSize,
                    color = Color.White
                )
            }
        }
    }
}

