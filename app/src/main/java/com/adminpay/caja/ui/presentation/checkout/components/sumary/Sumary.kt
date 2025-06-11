package com.adminpay.caja.ui.presentation.checkout.components.sumary

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.domain.model.invoice.InvoiceModel
import com.adminpay.caja.utils.ScreenDimensions

@Composable
fun CheckoutSummary(screen: ScreenDimensions, selectedInvoice: InvoiceModel?) {
    if (selectedInvoice == null) {
        Text("No hay factura seleccionada.")
        return
    }

    val details = selectedInvoice.invoiceItems.firstOrNull()?.details ?: "Sin descripción"
    val totalAmountBs = selectedInvoice.amountBs.amount
    val chargedAmountBs = selectedInvoice.chargedBs
    val remainingAmountBs = totalAmountBs - chargedAmountBs

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
                Divider()
                Spacer(modifier = Modifier.height(12.dp))

                Text("Métodos de Pago", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(8.dp))

                data class MetodoPago(val nombre: String, val icon: ImageVector, val monto: String)

                val metodos = listOf(
                    MetodoPago("Transferencia", Icons.Default.AccountBalance, "100 Bs."),
                    MetodoPago("Pago Móvil", Icons.Default.PhoneIphone, "100 Bs."),
                    MetodoPago("Zelle", Icons.Default.AttachMoney, "100 Bs."),
                    MetodoPago("Punto de Venta", Icons.Default.CreditCard, "100 Bs.")
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in metodos.indices step 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOfNotNull(
                                metodos.getOrNull(i),
                                metodos.getOrNull(i + 1)
                            ).forEach { metodo ->
                                Card(
                                    modifier = Modifier.weight(1f),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = metodo.icon,
                                            contentDescription = metodo.nombre,
                                            tint = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier
                                                .size(28.dp)
                                                .padding(end = 8.dp)
                                        )
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(end = 8.dp)
                                        ) {
                                            Text(metodo.nombre, color = Color.White, fontSize = 14.sp)
                                            Text("Monto: ${metodo.monto}", color = Color.White, fontSize = 12.sp)
                                        }
                                        IconButton(onClick = { /* eliminar */ }) {
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
                Divider()
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
                    SummaryLine(
                        label = "Monto restante",
                        value = "%.2f Bs.".format(remainingAmountBs),
                        fontSize = 13,
                        color = Color(0xFFB71C1C)
                    )
                    AmountComponent(monto = totalAmountBs, screen = screen)
                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            // Acción de validación
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
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
