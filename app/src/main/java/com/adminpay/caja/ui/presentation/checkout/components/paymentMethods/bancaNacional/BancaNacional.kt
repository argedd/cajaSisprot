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
import com.adminpay.caja.ui.presentation.components.InputComponent
import com.adminpay.caja.utils.getBankDrawableId
import com.adminpay.caja.utils.rememberDatePicker

@Composable
fun BancaNacionalScreen(
    sharedViewModel: CheckoutSharedViewModel,
    viewModel: BancaNacionalViewModel = hiltViewModel()
) {
    var fecha by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    val selectedInvoice = sharedViewModel.selectedInvoice
    val selectedBank = sharedViewModel.bankAssociated

    var paymentOption by remember { mutableStateOf("pagomovil") }

    val context = LocalContext.current
    val datePicker = rememberDatePicker(context) { selectedDate ->
        fecha = selectedDate
    }

    val uiState by viewModel.uiState.collectAsState()


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
            value = fecha,
            onValueChange = { fecha = it },
            placeholder = "Fecha de la operación",
            keyboardType = KeyboardType.Text,
            trailingIcon = Icons.Default.CalendarToday, // ← AGREGAR ESTO
            onTrailingIconClick = {
                datePicker.show()
            },
            readOnly = true
        )

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

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Validar Pago
        Button(
            onClick = {
                if (referencia.length != 8 || !referencia.all { it.isDigit() }) {
                    Toast.makeText(context, "Referencia inválida", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (fecha.isBlank()) {
                    Toast.makeText(context, "Seleccione la fecha", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val request = RequestPaymentValidateModel(
                    sender = selectedBank?.tlf ?: "",
                    reference = referencia,
                    date = fecha,
                    paymentMethod = paymentOption,
                    invoiceId = selectedInvoice?.id ?: 0
                )
                viewModel.validatePayment(request)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Validar Pago", color = Color.White)
        }

        if (uiState.error != null) {
            Text("Error: ${uiState.error}", color = Color.Red)
        }

        if (uiState.success != null) {
            Text("Validación exitosa", color = Color.Green)
        }

    }
}


