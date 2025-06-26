package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.bancaNacional

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.adminpay.caja.utils.*

@Composable
fun BancaNacionalScreen(
    sharedViewModel: CheckoutSharedViewModel,
    viewModel: BancaNacionalViewModel = hiltViewModel(),
    onDismiss: () -> Unit
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
    val isLoading = uiState is BancaNacionalUiState.Loading
    val screen = rememberScreenDimensions()
    val iconSize = screen.width.times(0.02f)
    val bodyFontSize = adaptiveFontSize(screen, small = 12.sp, medium = 14.sp, large = 16.sp)

    val remainingAmountBs by sharedViewModel.remainingAmountBs.collectAsState()
    val isButtonEnabled = remainingAmountBs > 0.0 && !isLoading

    var showErrorModal by remember { mutableStateOf(false) }

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
            .padding(screen.width.times(0.005f))
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(1.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(screen.width.times(0.02f)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = getBankDrawableId(selectedBank?.bankCode)),
                    contentDescription = "Logo del banco ${selectedBank?.bankCode}",
                    modifier = Modifier.width(screen.widthPercentage(0.02f))
                )
                Spacer(modifier = Modifier.width(screen.width.times(0.01f)))

                Column {
                    val items = listOf(
                        Icons.Default.AccountBalance to "${selectedBank?.bankName}",
                        Icons.Default.AccountBox to "${selectedBank?.identification}",
                        Icons.Default.Smartphone to "${selectedBank?.tlf}",
                        Icons.Default.Numbers to "${selectedBank?.nroCta}"
                    )
                    items.chunked(2).forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            rowItems.forEach { (icon, text) ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(iconSize)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text, fontSize = bodyFontSize)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(screen.height.times(0.01f)))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(screen.height.times(0.02f)))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("pagomovil" to Icons.Default.Smartphone, "transferencia" to Icons.Default.AccountBalance).forEach { (option, icon) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = paymentOption == option,
                        onClick = { paymentOption = option },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Text(option.replaceFirstChar { it.uppercase() }, fontSize = bodyFontSize)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(iconSize))
                }
            }
        }

        Spacer(modifier = Modifier.height(screen.height.times(0.03f)))

        InputComponent(
            value = fecha.formatFecha(),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { fecha = it },
            placeholder = "Fecha de la operación",
            keyboardType = KeyboardType.Text,
            trailingIcon = Icons.Default.CalendarToday,
            onTrailingIconClick = { datePicker.show() },
            readOnly = true
        )
        fechaError?.let {
            Text(it, color = Color.Red, fontSize = bodyFontSize)
        }

        Spacer(modifier = Modifier.height(screen.height.times(0.02f)))

        InputComponent(
            value = referencia,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                if (it.length <= 8 && it.all(Char::isDigit)) referencia = it
            },
            placeholder = "Referencia (8 dígitos)",
            keyboardType = KeyboardType.Number,
            leadingIcon = Icons.Default.Numbers
        )
        referenciaError?.let {
            Text(it, color = Color.Red, fontSize = bodyFontSize)
        }

        Spacer(modifier = Modifier.height(screen.height.times(0.03f)))

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
                viewModel.validatePayment(request, sharedViewModel, onDismiss)
            },
            enabled = isButtonEnabled,
            modifier = Modifier
                .fillMaxWidth().height(screen.heightPercentage(0.05f)),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isButtonEnabled) MaterialTheme.colorScheme.primary else Color.Gray
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .size(iconSize)
                        .padding(end = screen.width.times(0.01f)),
                    strokeWidth = 2.dp
                )
                Text("Validando...", color = Color.White, fontSize = bodyFontSize)
            } else {
                Text("Validar Pago", color = Color.White, fontSize = bodyFontSize)
            }
        }
    }
}
