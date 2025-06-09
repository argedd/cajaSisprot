package com.adminpay.caja.ui.presentation.box

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import kotlin.math.roundToInt

// ---- DATA CLASSES & STYLES ----

data class PaymentMethodCard(
    val name: String,
    val totalToday: Float,
    val icon: @Composable () -> Unit,
    val barColor: Color
)

data class CryptoCardData(
    val name: String,
    val value: Float,
    val currentTotal: Int = 0
)

enum class CryptoCardStyle {
    Light, Dark
}

// ---- CARD COMPONENT ----

@Composable
fun CryptoCard(
    style: CryptoCardStyle,
    data: CryptoCardData,
    modifier: Modifier = Modifier
) {
    val bgColor =
        if (style == CryptoCardStyle.Light) Color(0xFFF5F5F5) else MaterialTheme.colorScheme.primary
    val textColor = if (style == CryptoCardStyle.Light) Color.Black else Color.White

    Card(
        modifier = modifier
            .height(100.dp), // <- quité weight de aquí
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = data.name,
                fontSize = 16.sp,
                color = textColor,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = if (data.name.contains("Transacciones", ignoreCase = true))
                    data.currentTotal.toString()
                else
                    "$${data.value.roundToInt()}",
                fontSize = 20.sp,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// ---- MAIN UI ----

@Composable
fun BoxScreen() {
    val paymentMethods = listOf(
        PaymentMethodCard("Efectivo Bs", 100f, { Icon(Icons.Default.AttachMoney, null, tint = MaterialTheme.colorScheme.secondary) }, Color(0xFF4CAF50)),
        PaymentMethodCard("Efectivo $", 150f, { Icon(Icons.Default.AttachMoney, null, tint = MaterialTheme.colorScheme.secondary) }, Color(0xFF81C784)),
        PaymentMethodCard("Pago móvil", 1234.56f, { Icon(Icons.Default.QrCode, null, tint = MaterialTheme.colorScheme.secondary) }, Color(0xFF00BCD4)),
        PaymentMethodCard("Transferencia", 845.90f, { Icon(Icons.Default.SwapHoriz, null, tint = MaterialTheme.colorScheme.secondary) }, Color(0xFF3F51B5)),
        PaymentMethodCard("Binance", 320.00f, { Icon(Icons.Default.CurrencyBitcoin, null, tint = MaterialTheme.colorScheme.secondary) }, Color(0xFFFFC107)),
        PaymentMethodCard("PayPal", 1200.75f, { Icon(Icons.Default.AccountBalanceWallet, null, tint = MaterialTheme.colorScheme.secondary) }, Color(0xFF2962FF)),
        PaymentMethodCard("Zelle", 980.30f, { Icon(Icons.Default.AttachMoney, null, tint = MaterialTheme.colorScheme.secondary) }, Color(0xFF9C27B0)),
        PaymentMethodCard("Punto de venta", 1150.90f, { Icon(Icons.Default.CreditCard, null, tint = MaterialTheme.colorScheme.secondary) }, Color(0xFFFF9800)),
    )

    Scaffold(
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Acción cierre de caja */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Cierre", tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Cierre de caja", color = Color.White)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text("Resumen General", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    CryptoCard(CryptoCardStyle.Dark, CryptoCardData("Transacciones", 150f, 150), Modifier.weight(1f))
                    CryptoCard(CryptoCardStyle.Dark, CryptoCardData("Total Generado", 6350.00f), Modifier.weight(1f))
                }
            }

            item {
                Text("Métodos de Pago", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
            }
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 600.dp), // ajusta la altura según lo que necesites
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    userScrollEnabled = false // evita scroll dentro del grid
                ) {
                    items(paymentMethods.size) { index ->
                        val method = paymentMethods[index]
                        PaymentMethodCardView(method)
                    }
                }
            }


            item {
                PaymentMethodsBarChart(paymentMethods)
            }
        }
    }
}



@Composable
fun PaymentMethodsBarChart(paymentMethods: List<PaymentMethodCard>) {
    val barsData = paymentMethods.map { method ->
        Bars.Data(
            label = "", // Ocultamos leyenda
            value = method.totalToday.toDouble(),
            color = SolidColor(method.barColor)
        )
    }

    ColumnChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        data = listOf(Bars(label = "", values = barsData)),
        barProperties = BarProperties(
            thickness = 24.dp,
            spacing = 8.dp,
            cornerRadius = Bars.Data.Radius.Rectangle(8.dp, 8.dp)
        ),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
}


@SuppressLint("DefaultLocale")
@Composable
fun PaymentMethodCardView(method: PaymentMethodCard) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(method.barColor, CircleShape)
                )
                method.icon()
            }

            Column {
                Text(
                    method.name,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White,
                        fontSize = 12.sp
                    )
                )
                Text(
                    String.format("$%.2f", method.totalToday),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}



