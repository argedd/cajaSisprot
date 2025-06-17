package com.adminpay.caja.ui.presentation.checkout.components.sumary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import com.adminpay.caja.utils.ScreenDimensions

@Composable
fun CheckoutSummary(screen: ScreenDimensions, sharedViewModel: CheckoutSharedViewModel) {
    val selectedInvoice = sharedViewModel.selectedInvoice
    val paymentMethods by sharedViewModel.paymentMethods.collectAsState()
    val chargedAmountBs by sharedViewModel.chargedAmountBs.collectAsState()
    val remainingAmountBs by sharedViewModel.remainingAmountBs.collectAsState()

    if (selectedInvoice == null) {
        Text("No hay factura seleccionada.")
        return
    }

    val details = selectedInvoice.invoiceItems.firstOrNull()?.details ?: "Sin descripción"
    val totalAmountBs = selectedInvoice.amountBs.amount

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Resumen del Pedido",
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
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
                                        .padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))

                Text("Métodos de Pago", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(8.dp))

                if (paymentMethods.isEmpty()) {
                    Text(
                        text = "Sin métodos de pago",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(paymentMethods) { metodo ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
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
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(end = 4.dp)
                                        )
                                        Text(
                                            metodo.methodName,
                                            color = Color.White,
                                            fontSize = 13.sp,
                                            maxLines = 1
                                        )
                                        Spacer(modifier = Modifier.weight(1f)) // Empuja a la derecha si hay referencia

                                        if (!metodo.reference.isNullOrBlank()) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.End
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Numbers,
                                                    contentDescription = "Referencia",
                                                    tint = MaterialTheme.colorScheme.secondary,
                                                    modifier = Modifier
                                                        .size(20.dp)
                                                        .padding(end = 4.dp)
                                                )
                                                Text(
                                                    text = "ref: ${metodo.reference}",
                                                    color = Color.White,
                                                    fontSize = 13.sp,
                                                    maxLines = 1
                                                )
                                            }
                                        }

                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            "Monto: ${metodo.amountBs ?: 0.0} Bs.",
                                            color = Color.White,
                                            fontSize = 12.sp
                                        )
                                        IconButton(
                                            onClick = {
                                                sharedViewModel.removePaymentMethodById(
                                                    metodo.id
                                                )
                                            },
                                            modifier = Modifier.size(24.dp)
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

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SummaryLine(
                        label = "Monto cargado",
                        value = "%.2f Bs.".format(chargedAmountBs),
                        fontSize = 13,
                        color = Color(0xFF388E3C)
                    )
                    if (remainingAmountBs > 0.0) {
                        SummaryLine(
                            label = "Monto restante",
                            value = "%.2f Bs.".format(remainingAmountBs),
                            fontSize = 13,
                            color = Color(0xFFB71C1C)
                        )
                    }
                    AmountComponent(monto = totalAmountBs, screen = screen)
                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            // Acción de validación
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        enabled = remainingAmountBs <= 0.0,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (remainingAmountBs <= 0.0)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Gray
                        )
                    ) {
                        Text("Pagar", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryLine(label: String, value: String, fontSize: Int = 14, color: Color = Color.Black) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = fontSize.sp, color = color)
        Text(text = value, fontSize = fontSize.sp, color = color)
    }
}
