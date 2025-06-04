package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.bancaNacional.BancaNacionalScreen
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.efectivo.EfectivoScreen
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.medioDigital.MedioDigitalScreen
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.pos.PosScreen
import com.adminpay.caja.utils.ScreenDimensions

@Composable
fun PaymentMethodsScreen(
    screen: ScreenDimensions,
    serviceId: String
) {
    var selectedMethod by remember { mutableStateOf(PaymentMethod.BANCA_NACIONAL) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título fuera del Card
        Text(
            "Seleccione método de pago",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = minOf(screen.widthPercentage(0.025f).value, 20f).sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contenido dentro del Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        PaymentMethodIconButton(
                            icon = Icons.Default.AccountBalance,
                            label = "Banca Nacional",
                            isSelected = selectedMethod == PaymentMethod.BANCA_NACIONAL,
                            onClick = { selectedMethod = PaymentMethod.BANCA_NACIONAL },
                            iconSize = 40.dp,
                            boxSize = 64.dp,
                            fontSize = 14.sp
                        )
                        PaymentMethodIconButton(
                            icon = Icons.Default.PhoneAndroid,
                            label = "Medios Digitales",
                            isSelected = selectedMethod == PaymentMethod.MEDIOS_DIGITALES,
                            onClick = { selectedMethod = PaymentMethod.MEDIOS_DIGITALES },
                            iconSize = 40.dp,
                            boxSize = 64.dp,
                            fontSize = 14.sp
                        )
                        PaymentMethodIconButton(
                            icon = Icons.Default.AttachMoney,
                            label = "Efectivo",
                            isSelected = selectedMethod == PaymentMethod.EFECTIVO,
                            onClick = { selectedMethod = PaymentMethod.EFECTIVO },
                            iconSize = 40.dp,
                            boxSize = 64.dp,
                            fontSize = 14.sp
                        )
                        PaymentMethodIconButton(
                            icon = Icons.Default.CreditCard,
                            label = "Punto de Venta",
                            isSelected = selectedMethod == PaymentMethod.PUNTO_VENTA,
                            onClick = { selectedMethod = PaymentMethod.PUNTO_VENTA },
                            iconSize = 40.dp,
                            boxSize = 64.dp,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Aquí colocarías las pantallas asociadas
                when (selectedMethod) {
                    PaymentMethod.BANCA_NACIONAL -> BancaNacionalScreen()
                    PaymentMethod.MEDIOS_DIGITALES -> MedioDigitalScreen()
                    PaymentMethod.EFECTIVO -> EfectivoScreen()
                    PaymentMethod.PUNTO_VENTA -> PosScreen()
                }


            }
        }
    }
}


@Composable
fun PaymentMethodIconButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    iconSize: Dp,
    boxSize: Dp,
    fontSize: TextUnit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f)
    val contentColor = Color.White

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(boxSize)
                .background(backgroundColor, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = contentColor,
                modifier = Modifier.size(iconSize)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


enum class PaymentMethod {
    BANCA_NACIONAL,
    MEDIOS_DIGITALES,
    EFECTIVO,
    PUNTO_VENTA
}
