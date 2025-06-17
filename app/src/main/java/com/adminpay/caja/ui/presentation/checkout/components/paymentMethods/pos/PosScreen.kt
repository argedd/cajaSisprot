package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.pos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adminpay.caja.R
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import com.adminpay.caja.ui.presentation.components.InputComponent

@Composable
fun PosScreen(sharedViewModel: CheckoutSharedViewModel, viewModel: PosViewModel = hiltViewModel()) {
    var paymentType by remember { mutableStateOf("pos") }
    val remainingAmountBs by sharedViewModel.remainingAmountBs.collectAsState()

    var cedula by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("%.2f".format(remainingAmountBs)) }

    var cedulaError by remember { mutableStateOf<String?>(null) }
    var referenciaError by remember { mutableStateOf<String?>(null) }
    var montoError by remember { mutableStateOf<String?>(null) }

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

                if (monto.isBlank() || monto.toFloatOrNull() == null) {
                    montoError = "Monto inválido"
                    valid = false
                }

                if (valid) {
                    // Aquí llamas a sharedViewModel o navegas
                    if (paymentType == "pos") {

                    }else{
                        viewModel.chargedManualPayment(amount = monto.toDouble(), reference = referencia, sharedViewModel)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Cargar Pago", color = Color.White)
        }
    }
}


