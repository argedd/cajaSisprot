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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.adminpay.caja.R
import com.adminpay.caja.ui.presentation.components.InputComponent

@Composable
fun MedioDigitalScreen() {
    val options = listOf(
        MedioDigital("Binance", painterResource(id = R.drawable.binance)),
        MedioDigital("PayPal", painterResource(id = R.drawable.paypal)),
        MedioDigital("Zelle", painterResource(id = R.drawable.zelle)),
    )

    var selectedOption by remember { mutableStateOf(options[0].name) }
    var titular by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal=16.dp)
    ) {
        val rows = options.chunked(3)

        Column() {
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

        // Formulario
        InputComponent(
            value = fecha,
            onValueChange = { fecha = it },
            placeholder = "Fecha de la operación",
            keyboardType = KeyboardType.Text,
            leadingIcon = Icons.Default.CalendarToday,
            onTrailingIconClick = {
                // Mostrar DatePickerDialog
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        InputComponent(
            value = titular,
            onValueChange = { titular = it },
            placeholder = "Titular de la cuenta",
            keyboardType = KeyboardType.Text,
            leadingIcon = Icons.Default.AccountCircle,
            onTrailingIconClick = {
                // Mostrar DatePickerDialog
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        InputComponent(
            value = monto,
            onValueChange = {
                if (it.length <= 8 && it.all { c -> c.isDigit() }) monto = it
            },
            placeholder = "Referencia (8 dígitos)",
            keyboardType = KeyboardType.Number,
            leadingIcon = Icons.Default.Numbers,

            )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Validar Pago
        Button(
            onClick = {
                // Acción de validación
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

data class MedioDigital(val name: String, val image: Painter)
