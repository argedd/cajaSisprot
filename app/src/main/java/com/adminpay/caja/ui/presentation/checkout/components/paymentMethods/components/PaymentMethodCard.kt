package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.R
import com.adminpay.caja.domain.model.paymentMethods.PaymentMethodUIModel
import com.adminpay.caja.utils.ScreenDimensions

@Composable
fun PaymentMethodCard(
    method: PaymentMethodUIModel,
    onClick: () -> Unit,
    screen: ScreenDimensions
) {
    val iconSize = screen.widthPercentage(0.05f)
    val textSize = (screen.width.value * 0.014).sp

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono
            Box(
                modifier = Modifier
                    .size(iconSize)
                    .background(Color(0xFFEFF1F3), shape = RoundedCornerShape(screen.widthPercentage(0.03f))),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = method.icon),
                    contentDescription = null,
                    modifier = Modifier.size(iconSize * 0.6f)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = method.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = textSize,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = method.details,
                    fontSize = textSize * 0.85f,
                    color = Color.LightGray
                )
            }

            if (method.tag != null) {
                Text(
                    text = method.tag,
                    fontSize = textSize * 0.75f,
                    color = method.tagColor,
                    modifier = Modifier
                        .background(method.tagColor.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.ArrowCircleRight,
                    contentDescription = "Select",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(
                        screen.widthPercentage(0.05f)
                    )
                )
            }
        }
    }
}
