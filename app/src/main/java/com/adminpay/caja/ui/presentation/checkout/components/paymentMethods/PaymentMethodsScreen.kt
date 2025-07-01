package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.R
import com.adminpay.caja.domain.model.paymentMethods.PaymentMethodUIModel
import com.adminpay.caja.domain.model.tasa.ModelTasa
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.bancaNacional.BancaNacionalScreen
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.components.PaymentMethodCard
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.efectivo.EfectivoScreen
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.medioDigital.MedioDigitalScreen
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.pos.PosScreen
import com.adminpay.caja.ui.presentation.components.AppModalComponent
import com.adminpay.caja.utils.ScreenDimensions
import com.adminpay.caja.utils.adaptiveFontSize
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun PaymentMethodsScreen(
    screen: ScreenDimensions,
    sharedViewModel: CheckoutSharedViewModel,
    tasa: ModelTasa?
) {

    var selectedMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    val showModal = selectedMethod != null
    val fontSize = adaptiveFontSize(
        screen = screen,
        small = 12.sp,
        medium = 16.sp,
        large = 28.sp
    )

    val paymentMethods = listOf(
        PaymentMethodUIModel(
            title = "Banca Nacional",
            details = "PagoMóvil Transferencia ",
            icon = R.drawable.bankicon,
        ),
        PaymentMethodUIModel(
            title = "Efectivo",
            details = "Bolívares   Dólares",
            icon = R.drawable.pgefectivo,
        ),
        PaymentMethodUIModel(
            title = "Medios Digitales",
            details = "Binance Paypal Zelle",
            icon = R.drawable.iconbz,
            tag = "Solo Zelle",
            tagColor = Color(0xFFFFC107)
        ),
        PaymentMethodUIModel(
            title = "Punto de Venta",
            details = "Débito Crédito",
            icon = R.drawable.tarjeta_icono,

        )
    )





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
                fontSize = fontSize
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


                paymentMethods.forEach { method ->
                    val paymentType = when (method.title) {
                        "Banca Nacional" -> PaymentMethod.BANCA_NACIONAL
                        "Medios Digitales" -> PaymentMethod.MEDIOS_DIGITALES
                        "Efectivo" -> PaymentMethod.EFECTIVO
                        "Punto de Venta" -> PaymentMethod.PUNTO_VENTA
                        else -> null
                    }
                    PaymentMethodCard(
                        method = method,
                        onClick = {
                            paymentType?.let {
                                selectedMethod = it
                            }
                        },
                        screen = screen
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Aquí colocarías las pantallas asociadas
                if (showModal) {
                    AppModalComponent(onDismiss = { selectedMethod = null }) {
                        when (selectedMethod) {
                            PaymentMethod.BANCA_NACIONAL -> BancaNacionalScreen(sharedViewModel = sharedViewModel) {
                                selectedMethod = null
                            }

                            PaymentMethod.MEDIOS_DIGITALES -> MedioDigitalScreen(sharedViewModel = sharedViewModel){
                                selectedMethod = null
                            }
                            PaymentMethod.EFECTIVO -> EfectivoScreen(sharedViewModel = sharedViewModel, tasa = tasa){
                                selectedMethod = null
                            }
                            PaymentMethod.PUNTO_VENTA -> PosScreen(sharedViewModel = sharedViewModel){
                                selectedMethod = null
                            }
                            null -> {}
                        }
                    }
                }



            }
        }
    }
}


enum class PaymentMethod {
    BANCA_NACIONAL,
    MEDIOS_DIGITALES,
    EFECTIVO,
    PUNTO_VENTA
}
