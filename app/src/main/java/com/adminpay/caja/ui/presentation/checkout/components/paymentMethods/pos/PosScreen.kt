package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.pos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adminpay.caja.R
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import com.adminpay.caja.ui.presentation.components.AppModalComponent
import com.adminpay.caja.ui.presentation.components.ErrorComponent
import com.adminpay.caja.ui.presentation.components.InputComponent
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun PosScreen(sharedViewModel: CheckoutSharedViewModel, viewModel: PosViewModel = hiltViewModel()) {
    var paymentType by remember { mutableStateOf("pos") }
    val remainingAmountBs by sharedViewModel.remainingAmountBs.collectAsState()
    val isButtonEnabled = remainingAmountBs > 0.0
    val screen = rememberScreenDimensions()

    var cedula by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("%.2f".format(remainingAmountBs)) }

    var cedulaError by remember { mutableStateOf<String?>(null) }
    var referenciaError by remember { mutableStateOf<String?>(null) }
    var montoError by remember { mutableStateOf<String?>(null) }

    val uiState by viewModel.uiState.collectAsState()
    val isConnected by viewModel.connectedClients.collectAsState()
    var showErrorModal by remember { mutableStateOf(false) }

    LaunchedEffect(remainingAmountBs) {
        monto = "%.2f".format(remainingAmountBs)
    }


    LaunchedEffect(uiState, isConnected) {
        if (uiState is PosUiState.Error) {
            showErrorModal = true
        }
    }

    if (showErrorModal && uiState is PosUiState.Error) {
        AppModalComponent(onDismiss = {
            showErrorModal = false
            viewModel.resetState()
        }) {
            ErrorComponent(
                message = (uiState as PosUiState.Error).message,
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Imagen
        Image(
            painter = painterResource(id = R.drawable.tarjetas),
            contentDescription = "Confirmación de tarjeta",
            modifier = Modifier
                .height(50.dp)
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones de pago
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = paymentType == "pos",
                    onClick = { paymentType = "pos" },
                    colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                )
                Text("POS")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.CreditCard, contentDescription = null, tint = MaterialTheme.colorScheme.primary )

            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = paymentType == "manual",
                    onClick = { paymentType = "manual" },
                    colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                )
                Text("Manual")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.EditNote, contentDescription = null, tint = MaterialTheme.colorScheme.primary)


            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Formulario dinámico según selección
        if (paymentType == "pos") {
            InputComponent(
                value = cedula,
                onValueChange = {
                    if (it.length <= 10 && it.all { c -> c.isDigit() }) cedula = it
                    cedulaError = null
                },
                placeholder = "Cédula de la Tarjeta",
                leadingIcon = Icons.Default.Badge,
                isError = cedulaError != null,
                error = cedulaError
            )
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            InputComponent(
                value = referencia,
                onValueChange = {
                    if (it.length <= 10 && it.all { c -> c.isDigit() }) referencia = it
                    referenciaError = null
                },
                placeholder = "Referencia",
                leadingIcon = Icons.Default.Numbers,
                isError = referenciaError != null,
                error = referenciaError
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        InputComponent(
            value = monto,
            onValueChange = {
                val regex = Regex("^\\d+(\\.\\d{0,2})?$")
                if (it.isEmpty() || regex.matches(it)) monto = it
                montoError = null
            },
            placeholder = "Monto",
            leadingIcon = Icons.Default.Payments,
            keyboardType = KeyboardType.Number,
            isError = montoError != null,
            error = montoError
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Cargar
        Button(
            onClick = {
                var valid = true
                if (paymentType == "pos") {
                    if (cedula.length !in 6..10) {
                        cedulaError = "Cédula inválida"
                        valid = false
                    }
                } else {
                    if (referencia.length !in 1..10) {
                        referenciaError = "Referencia inválida"
                        valid = false
                    }
                }

                val montoValue = monto.toFloatOrNull()
                if (monto.isBlank() || montoValue == null) {
                    montoError = "Monto inválido"
                    valid = false
                } else if (montoValue > remainingAmountBs.toFloat()) {
                    montoError = "El monto no puede ser mayor al restante"
                    valid = false
                }


                if (valid) {
                    // Aquí llamas a sharedViewModel o navegas
                    if (paymentType == "pos") {
                        viewModel.sendPayment(cedula = cedula, monto = monto.toDouble(), sharedViewModel)
                    }else{
                        viewModel.chargedManualPayment(amount = monto.toDouble(), reference = referencia, sharedViewModel)
                    }
                }
            },
            enabled = isButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Cargar Pago", color = Color.White)
        }
    }
}


