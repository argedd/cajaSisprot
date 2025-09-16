

package com.adminpay.caja.ui.presentation.checkout.components.sumary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.adminpay.caja.domain.model.auth.User
import com.adminpay.caja.ui.presentation.auth.AuthState
import com.adminpay.caja.ui.presentation.auth.AuthViewModel
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import com.adminpay.caja.ui.presentation.checkout.CheckoutUiState
import com.adminpay.caja.ui.presentation.components.AppModalNotificationComponent
import com.adminpay.caja.ui.presentation.components.ErrorComponent
import com.adminpay.caja.utils.ScreenDimensions
import com.adminpay.caja.utils.adaptiveFontSize
import com.adminpay.caja.utils.adaptiveFontSizeScaled

@Composable
fun CheckoutSummary(
    screen: ScreenDimensions,
    sharedViewModel: CheckoutSharedViewModel,
    finish: () -> Unit,
    authViewModel: AuthViewModel
) {
    val selectedInvoice = sharedViewModel.selectedInvoice
    val paymentMethods by sharedViewModel.paymentMethods.collectAsState()
    val chargedAmountBs by sharedViewModel.chargedAmountBs.collectAsState()
    val remainingAmountBs by sharedViewModel.remainingAmountBs.collectAsState()
    val authState by authViewModel.authState.collectAsState()

    val user: User? = (authState as? AuthState.Success)?.user?.getOrNull()

    val uiState by sharedViewModel.uiState.collectAsState()
    var showErrorModal by remember { mutableStateOf(false) }

    // 🔹 Escalas usando adaptiveFontSizeScaled
    val titleFontSize = adaptiveFontSize(screen, small = 14.sp, medium = 18.sp, large = 28.sp)
    val bodyFontSize = adaptiveFontSizeScaled(screen, base = 9)
    val smallFontSize = adaptiveFontSizeScaled(screen, base = 10)
    val buttonFontSize = adaptiveFontSizeScaled(screen, base = 10)

    LaunchedEffect(uiState) {
        if (uiState is CheckoutUiState.Error) {
            showErrorModal = true
        }
    }

    if (showErrorModal && uiState is CheckoutUiState.Error) {
        AppModalNotificationComponent(onDismiss = {
            showErrorModal = false
            sharedViewModel.resetState()
        }) {
            ErrorComponent(
                message = (uiState as CheckoutUiState.Error).message,
                screen = screen,
                onClose = {
                    showErrorModal = false
                    sharedViewModel.resetState()
                }
            )
        }
    }

    if (selectedInvoice == null) {
        Text("No hay factura seleccionada.", fontSize = bodyFontSize)
        return
    }

    val details = selectedInvoice.invoiceItems.firstOrNull()?.details ?: "Sin descripción"
    val totalAmountBs = selectedInvoice.amountBs.amount

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Resumen del Pedido",
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = titleFontSize)
        )

        Spacer(modifier = Modifier.height(screen.heightPercentage(0.015f)))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = screen.heightPercentage(0.005f))
        ) {
            Column(modifier = Modifier.padding(screen.widthPercentage(0.01f))) {
                val infoItems = listOf(
                    Icons.Default.Person to "Cliente: ${selectedInvoice.clientName}",
                    Icons.Default.Description to "Nota de cobro: #${selectedInvoice.id}",
                    Icons.AutoMirrored.Filled.Assignment to "Nro Contrato: #${selectedInvoice.contract}",
                    Icons.Default.Info to details
                )

                Column {
                    for (i in infoItems.indices step 2) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            listOfNotNull(
                                infoItems.getOrNull(i),
                                infoItems.getOrNull(i + 1)
                            ).forEach { (icon, text) ->
                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(screen.widthPercentage(0.001f)),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(screen.widthPercentage(0.020f))
                                    )
                                    Spacer(modifier = Modifier.width(screen.widthPercentage(0.015f)))
                                    Text(text, fontSize = bodyFontSize)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(screen.heightPercentage(0.01f)))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(screen.heightPercentage(0.01f)))

                Text("Métodos de Pago", fontSize = bodyFontSize)
                Spacer(modifier = Modifier.height(screen.heightPercentage(0.005f)))

                if (paymentMethods.isEmpty()) {
                    Text(
                        text = "Sin métodos de pago",
                        color = Color.Gray,
                        fontSize = bodyFontSize
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = screen.heightPercentage(0.18f)) // altura máxima del grid
                    ) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(screen.widthPercentage(0.01f)),
                        ) {
                            items(paymentMethods) { metodo ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                                    modifier = Modifier
                                        .width(screen.widthPercentage(0.18f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(screen.widthPercentage(0.004f)),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Wallet,
                                                contentDescription = metodo.methodName,
                                                tint = MaterialTheme.colorScheme.secondary,
                                                modifier = Modifier.size(screen.widthPercentage(0.015f))
                                            )
                                            Text(
                                                metodo.methodName,
                                                color = Color.White,
                                                fontSize = smallFontSize,
                                                maxLines = 1
                                            )
                                            Spacer(modifier = Modifier.weight(1f))

                                            if (!metodo.reference.isNullOrBlank()) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.End
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Numbers,
                                                        contentDescription = "Referencia",
                                                        tint = MaterialTheme.colorScheme.secondary,
                                                        modifier = Modifier.size(screen.widthPercentage(0.01f))
                                                    )
                                                    Text(
                                                        text = metodo.reference,
                                                        color = Color.White,
                                                        fontSize = smallFontSize,
                                                        maxLines = 1
                                                    )
                                                }
                                            }
                                        }

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "Monto: ${metodo.amountBs ?: 0.0} Bs.",
                                                color = Color.White,
                                                fontSize = smallFontSize
                                            )
                                            IconButton(
                                                onClick = {
                                                    sharedViewModel.removePaymentMethodById(metodo.id)
                                                },
                                                modifier = Modifier.size(screen.widthPercentage(0.015f))
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.DeleteOutline,
                                                    contentDescription = "Eliminar",
                                                    tint = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.height(screen.heightPercentage(0.01f)))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(screen.heightPercentage(0.01f)))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(screen.heightPercentage(0.005f))
                ) {
                    SummaryLine(
                        label = "Monto cargado",
                        value = "%.2f Bs.".format(chargedAmountBs),
                        screen = screen,
                        color = Color(0xFF388E3C)
                    )
                    if (remainingAmountBs > 0.0) {
                        SummaryLine(
                            label = "Monto restante",
                            value = "%.2f Bs.".format(remainingAmountBs),
                            screen = screen,
                            color = Color(0xFFB71C1C)
                        )
                        AmountComponent(monto = totalAmountBs, screen = screen)

                    }

                    if (user?.idGsoft == null) {
                        Text(
                            text = "Usuario no autorizado para pagar",
                            color = Color.Red,
                            fontSize = bodyFontSize,
                            modifier = Modifier.padding(horizontal = screen.widthPercentage(0.01f))
                        )
                    } else {
                        if (remainingAmountBs <= 0.0) {
                            Button(
                                onClick = {
                                    sharedViewModel.registerPayment(
                                        selectedInvoice,
                                        paymentMethods,
                                        finish
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = screen.widthPercentage(0.01f)),
                                enabled = remainingAmountBs <= 0.0,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (remainingAmountBs <= 0.0)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        Color.Gray
                                )
                            ) {
                                Text("Pagar", color = Color.White, fontSize = buttonFontSize)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryLine(label: String, value: String, screen: ScreenDimensions, color: Color = Color.Black) {
    val fontSize = adaptiveFontSizeScaled(screen, base = 10)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = fontSize, color = color)
        Text(text = value, fontSize = fontSize, color = color)
    }
}


