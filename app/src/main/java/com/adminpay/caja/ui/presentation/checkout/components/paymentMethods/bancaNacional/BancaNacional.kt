package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.bancaNacional

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adminpay.caja.domain.model.payment.validate.RequestPaymentValidateModel
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import com.adminpay.caja.ui.presentation.components.AppModalComponent
import com.adminpay.caja.ui.presentation.components.ErrorComponent
import com.adminpay.caja.ui.presentation.components.InputComponent
import com.adminpay.caja.utils.getBankDrawableId
import com.adminpay.caja.utils.rememberDatePicker
import com.adminpay.caja.utils.rememberScreenDimensions
import com.movilpay.autopago.utils.formatFecha

@Composable
fun BancaNacionalScreen(
    sharedViewModel: CheckoutSharedViewModel,
    viewModel: BancaNacionalViewModel = hiltViewModel()
) {
    var fecha by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    var fechaError by remember { mutableStateOf<String?>(null) }
    var referenciaError by remember { mutableStateOf<String?>(null) }

    val selectedInvoice = sharedViewModel.selectedInvoice
    val selectedBank = sharedViewModel.bankAssociated

    var paymentOption by remember { mutableStateOf("pagomovil") }

    val context = LocalContext.current
    val datePicker = rememberDatePicker(context) { selectedDate ->
        fecha = selectedDate
    }

    val uiState by viewModel.uiState.collectAsState()
    var showErrorModal by remember { mutableStateOf(false) }
    val screen = rememberScreenDimensions()

    val remainingAmountBs by sharedViewModel.remainingAmountBs.collectAsState()
    val isButtonEnabled = remainingAmountBs > 0.0


    LaunchedEffect(uiState) {
        if (uiState is BancaNacionalUiState.Error) {
            showErrorModal = true
        }
    }
    if (showErrorModal && uiState is BancaNacionalUiState.Error) {
        AppModalComponent(onDismiss = {
            showErrorModal = false
            viewModel.resetState()
        }) {
            ErrorComponent(
                message = (uiState as BancaNacionalUiState.Error).message,
                screen = screen,
                onClose = {
                    showErrorModal = false
                    viewModel.resetState()
                }
            )
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        // Card con imagen y datos en grilla 2x2
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = getBankDrawableId(selectedBank?.bankCode)),
                    contentDescription = "Logo del banco ${selectedBank?.bankCode}",
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    val items = listOf(
                        Pair(Icons.Default.AccountBalance, "${selectedBank?.bankName}"),
                        Pair(Icons.Default.AccountBox, "${selectedBank?.identification}"),
                        Pair(Icons.Default.Smartphone, "${selectedBank?.tlf}"),
                        Pair(Icons.Default.Numbers, "${selectedBank?.nroCta}")
                    )

                    for (i in items.indices step 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            items.subList(i, i + 2).forEach { (icon, value) ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(value, fontSize = 13.sp)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = paymentOption == "pagomovil",
                    onClick = { paymentOption = "pagomovil" },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                )
                Text("Pago Móvil", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    Icons.Default.Smartphone,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = paymentOption == "transferencia",
                    onClick = { paymentOption = "transferencia" },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                )
                Text("Transferencia", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    Icons.Default.AccountBalance,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Formulario
        InputComponent(
            value = fecha.formatFecha(),
            onValueChange = { fecha = it },
            placeholder = "Fecha de la operación",
            keyboardType = KeyboardType.Text,
            trailingIcon = Icons.Default.CalendarToday, // ← AGREGAR ESTO
            onTrailingIconClick = {
                datePicker.show()
            },
            readOnly = true
        )
        fechaError?.let {
            Text(it, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        InputComponent(
            value = referencia,
            onValueChange = {
                if (it.length <= 8 && it.all { c -> c.isDigit() }) referencia = it
            },
            placeholder = "Referencia (8 dígitos)",
            keyboardType = KeyboardType.Number,
            leadingIcon = Icons.Default.Numbers,

            )
        referenciaError?.let {
            Text(it, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Validar Pago
        Button(
            onClick = {
                var hasError = false

                if (referencia.length != 8) {
                    referenciaError = "La referencia debe tener 8 dígitos"
                    hasError = true
                }

                if (fecha.isBlank()) {
                    fechaError = "Seleccione una fecha"
                    hasError = true
                }

                if (hasError) return@Button

                val request = RequestPaymentValidateModel(
                    sender = null,
                    reference = referencia,
                    date = fecha,
                    paymentMethod = paymentOption,
                    invoiceId = selectedInvoice?.id ?: 0
                )
                viewModel.validatePayment(
                    request, sharedViewModel,
                )
            },
            enabled = isButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Validar Pago", color = Color.White)
        }


    }
}


