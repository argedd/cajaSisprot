package com.adminpay.caja.ui.presentation.checkout.components.sumary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.utils.ScreenCategory
import com.adminpay.caja.utils.ScreenDimensions
import com.adminpay.caja.utils.adaptiveFontSize
import com.adminpay.caja.utils.getCategory

@Composable
fun AmountComponent(
    monto: Double,
    screen: ScreenDimensions
) {
    val horizontalPadding = 16.dp
    val verticalPadding =when (screen.getCategory()) {
        ScreenCategory.SMALL -> 4.dp
        ScreenCategory.MEDIUM -> 6.dp
        ScreenCategory.LARGE -> 12.dp
    }
    val iconSize = when (screen.getCategory()) {
        ScreenCategory.SMALL -> 20.dp
        ScreenCategory.MEDIUM -> 20.dp
        ScreenCategory.LARGE -> 36.dp
    }
    val textSize = adaptiveFontSize(screen, 10.sp, 12.sp, 20.sp)

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding, vertical = verticalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Payments,
                    contentDescription = "Monto",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(iconSize)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Monto a pagar",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontSize = textSize,
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = "$monto Bs.",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = textSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

