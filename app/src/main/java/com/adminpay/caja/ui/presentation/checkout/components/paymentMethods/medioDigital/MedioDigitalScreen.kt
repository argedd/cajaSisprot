package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.medioDigital

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adminpay.caja.R
import com.adminpay.caja.domain.model.payment.validate.PaymentMetadataModel
import com.adminpay.caja.domain.model.payment.validate.RequestPaymentValidateModel
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import com.adminpay.caja.ui.presentation.components.AppModalComponent
import com.adminpay.caja.ui.presentation.components.ErrorComponent
import com.adminpay.caja.ui.presentation.components.InputComponent
import com.adminpay.caja.utils.formatFecha
import com.adminpay.caja.utils.rememberDatePicker
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun MedioDigitalScreen(
    sharedViewModel: CheckoutSharedViewModel,
    viewModel: MedioDigitalViewModel = hiltViewModel()
) {
    val options = listOf(
        MedioDigital("Zelle", painterResource(id = R.drawable.zelle)),
    )

    var selectedOption by remember { mutableStateOf(options[0].name) }
    val selectedInvoice = sharedViewModel.selectedInvoice

    var titular by remember { mutableStateOf("") }
    var titularError by remember { mutableStateOf<String?>(null) }
    var fecha by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    var fechaError by remember { mutableStateOf<String?>(null) }
    var referenciaError by remember { mutableStateOf<String?>(null) }

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
        if (uiState is MedioDigitalUiState.Error) {
            showErrorModal = true
        }
    }
    if (showErrorModal && uiState is MedioDigitalUiState.Error) {
        AppModalComponent(onDismiss = {
            showErrorModal = false
            viewModel.resetState()
        }) {
            ErrorComponent(
                message = (uiState as MedioDigitalUiState.Error).message,
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
            .padding(horizontal = 16.dp)
    ) {
        val rows = options.chunked(3)

        Column {
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { option ->
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedOption = option.name },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            RadioButton(
                                selected = selectedOption == option.name,
                                onClick = { selectedOption = option.name }
                            )
                            Image(
                                painter = option.image,
                                contentDescription = option.name,
                                modifier = Modifier.size(120.dp)
                            )

                        }
                    }

                    // Rellenar espacios vacíos si la fila tiene menos de 3
                    repeat(3 - row.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

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
            value = titular,
            onValueChange = { titular = it },
            placeholder = "Titular de la cuenta",
            leadingIcon = Icons.Default.AccountCircle,

        )
        titularError?.let {
            Text(it, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        InputComponent(
            value = referencia,
            onValueChange = {
              referencia = it
            },
            placeholder = "Referencia",
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

                if (referencia.isBlank()) {
                    referenciaError = "La referencia es requerida"
                    hasError = true
                }

                if (fecha.isBlank()) {
                    fechaError = "Seleccione una fecha"
                    hasError = true
                }

                if (titular.isBlank()) {
                    titularError = "Ingrese el titular de la cuenta"
                    hasError = true
                }

                if (hasError) return@Button

                val request = RequestPaymentValidateModel(
                    sender = titular,
                    reference = null,
                    date = fecha,
                    paymentMethod = "zelle",
                    invoiceId = selectedInvoice?.id ?: 0,
                    metadata = PaymentMetadataModel(
                        confirmationCode = referencia
                    )

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

data class MedioDigital(val name: String, val image: Painter)
