package com.adminpay.caja.ui.presentation.invoices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.adminpay.caja.domain.model.invoice.InvoiceModel
import com.adminpay.caja.utils.ScreenDimensions
import com.adminpay.caja.utils.adaptiveFontSize
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun FacturaCard(
    factura: InvoiceModel,
    enabled: Boolean,
    onPagarClick: () -> Unit
) {
    val screen = rememberScreenDimensions()
    val fontSize = adaptiveFontSize(
        screen = screen,
        small = 12.sp,
        medium = 18.sp,
        large = 22.sp
    )



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(screen.widthPercentage(0.01f)),
        shape = RoundedCornerShape(screen.widthPercentage(0.02f)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = screen.heightPercentage(0.01f))
    ) {
        Column(
            modifier = Modifier
                .padding(screen.widthPercentage(0.025f))
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(screen.widthPercentage(0.04f))
                        .background(MaterialTheme.colorScheme.secondary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.RequestPage,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(screen.widthPercentage(0.02f))
                    )
                }
                Spacer(modifier = Modifier.width(screen.widthPercentage(0.02f)))
                Text(
                    text = "Nota de Cobro #${factura.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize
                )
            }

            Spacer(modifier = Modifier.height(screen.heightPercentage(0.015f)))

            RowItem(Icons.Default.DateRange, "Emisión", factura.dateEmission, screen)
            RowItem(Icons.Default.CalendarMonth, "Corte", factura.dateExpiration, screen)
            RowItem(Icons.Default.AttachMoney, "Monto", "Bs ${"%.2f".format(factura.amountBs.amount)}", screen)

            Spacer(modifier = Modifier.height(screen.heightPercentage(0.015f)))

            Button(
                onClick = onPagarClick,
                enabled = enabled,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.LightGray
                )
            ) {
                Text("Pagar", fontSize = (screen.width.value * 0.015f).sp)
            }
        }
    }
}



@Composable
private fun RowItem(icon: ImageVector, label: String, value: String, screen: ScreenDimensions) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = screen.heightPercentage(0.005f))
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(screen.widthPercentage(0.02f))
        )
        Spacer(modifier = Modifier.width(screen.widthPercentage(0.015f)))
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = (screen.width.value * 0.014f).sp,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = (screen.width.value * 0.014f).sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

