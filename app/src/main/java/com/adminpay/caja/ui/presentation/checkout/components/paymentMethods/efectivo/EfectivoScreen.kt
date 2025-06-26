package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.efectivo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adminpay.caja.domain.model.tasa.ModelTasa
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.efectivo.components.CashDollarBillComponent
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.efectivo.viewModels.EfectivoBsViewModel
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.efectivo.viewModels.EfectivoUsdViewModel
import com.adminpay.caja.ui.presentation.components.AppModalComponent
import com.adminpay.caja.ui.presentation.components.InputComponent
import com.adminpay.caja.utils.adaptiveFontSize
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun EfectivoScreen(
    sharedViewModel: CheckoutSharedViewModel,
    bsViewModel: EfectivoBsViewModel = hiltViewModel(),
    usdViewModel: EfectivoUsdViewModel = hiltViewModel(),
    tasa: ModelTasa?,
    onDismiss: () -> Unit
) {
    var selectedCurrency by remember { mutableStateOf("BS") }
    var showModal by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val remainingAmountBs by sharedViewModel.remainingAmountBs.collectAsState()
    val isButtonEnabled = remainingAmountBs > 0.0
    val screen = rememberScreenDimensions()
    val bodyFontSize = adaptiveFontSize(screen, small = 12.sp, medium = 14.sp, large = 24.sp)

    LaunchedEffect(selectedCurrency) {
        bsViewModel.clearForm()
        usdViewModel.clearForm()
        showError = false
    }

    val amount = if (selectedCurrency == "BS") bsViewModel.amount else usdViewModel.amount
    val cashBills = usdViewModel.cashBills
    val selectedInvoice = sharedViewModel.selectedInvoice
    println("selectedInvoice: $selectedInvoice")

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        // Contenido scrollable
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Selector de tipo
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedCurrency == "BS",
                    onClick = { selectedCurrency = "BS" }
                )
                Text("Efectivo BS", fontSize = bodyFontSize)
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(

                    selected = selectedCurrency == "USD",
                    onClick = { selectedCurrency = "USD" }
                )
                Text("Efectivo USD", fontSize = bodyFontSize)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de monto
            InputComponent(
                value = amount,
                onValueChange = {
                    if (selectedCurrency == "BS") bsViewModel.onAmountChange(it)
                    else usdViewModel.onAmountChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Monto",
                keyboardType = KeyboardType.Number,
            )

            if (selectedCurrency == "USD") {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { showModal = true }) {
                    Text("Cargar Billetes", fontSize = bodyFontSize)
                }

                if (cashBills.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Billetes Cargados:", style = MaterialTheme.typography.titleMedium)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 220.dp)
                            .padding(top = 8.dp)
                    ) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(cashBills) { bill ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Money,
                                                contentDescription = "Billete",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Column {
                                                Text("Valor: \$${bill.denomination}", style = MaterialTheme.typography.bodyMedium)
                                                Text("Serial: ${bill.serialCode}", style = MaterialTheme.typography.bodySmall)
                                            }
                                        }
                                        IconButton(onClick = { usdViewModel.removeCashBill(bill) }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (showError && cashBills.isEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Debe cargar al menos un billete.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Botón fijo abajo
        Button(
            onClick = {
                if (selectedCurrency == "BS") {
                    bsViewModel.addBsPayment(sharedViewModel,onDismiss) {
                        showError = false
                    }
                } else {
                    if (cashBills.isEmpty()) {
                        showError = true
                    } else {
                        usdViewModel.addUsdPayment(sharedViewModel, tasa, onDismiss) {
                            showError = false
                        }
                    }
                }
            },
            enabled = isButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Text("Cargar Pago", fontSize = bodyFontSize)
        }
    }

    if (showModal) {
        AppModalComponent(onDismiss = { showModal = false }) {
            val totalCurrent = cashBills.sumOf { it.denomination ?: 0 }

            CashDollarBillComponent(
                onAdd = { newBill ->
                    usdViewModel.addCashBill(newBill)
                },
                onClose = { showModal = false },
                maxAmount = usdViewModel.amount.toDoubleOrNull() ?: 0.0,
                currentTotal = totalCurrent.toDouble() // ✅ nuevo parámetro
            )
        }
    }

}

