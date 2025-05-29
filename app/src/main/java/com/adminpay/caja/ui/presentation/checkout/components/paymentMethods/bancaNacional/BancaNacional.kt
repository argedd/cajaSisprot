package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.bancaNacional

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.R
import com.adminpay.caja.ui.presentation.components.InputComponent

@Composable
fun BancaNacionalScreen() {
    var fecha by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        // Card con imagen y datos en grilla 2x2
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.b0191),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    val items = listOf(
                        Pair(Icons.Default.AccountBalance, "Banco XYZ"),
                        Pair(Icons.Default.AccountBalance, "V-12345678"),
                        Pair(Icons.Default.Smartphone, "0414-1234567"),
                        Pair(Icons.Default.Money, "0102-0123-4567-8912")
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
                                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(value, fontSize = 14.sp)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título centrado
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.AccountBalance, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Transferencia", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.Smartphone, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Pago Móvil", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

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
                // Acción de validación
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Validar Pago", color = Color.White)
        }
    }
}


