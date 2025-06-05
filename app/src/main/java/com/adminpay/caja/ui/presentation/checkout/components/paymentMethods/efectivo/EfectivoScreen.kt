package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.efectivo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.adminpay.caja.ui.presentation.components.AppModalComponent
import com.adminpay.caja.ui.presentation.components.InputComponent

@Composable
fun EfectivoScreen() {
    var selectedCurrency by remember { mutableStateOf("BS") }
    var amount by remember { mutableStateOf("") }
    var showModal by remember { mutableStateOf(false) }
    var seriales by remember { mutableStateOf(listOf<SerialEntry>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedCurrency == "BS",
                onClick = { selectedCurrency = "BS" }
            )
            Text("Efectivo BS")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = selectedCurrency == "USD",
                onClick = { selectedCurrency = "USD" }
            )
            Text("Efectivo USD")
        }

        Spacer(modifier = Modifier.height(16.dp))

        InputComponent(
            value = amount,
            onValueChange = { amount = it },
            placeholder = "Monto",
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth()
        )

        if (selectedCurrency == "USD") {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showModal = true }) {
                Text("Cargar Seriales")
            }
        }

        if (seriales.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Billetes Cargados:", style = MaterialTheme.typography.labelMedium)
            LazyColumn {
                items(seriales) { entry ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Valor: ${entry.valor}, Serial: ${entry.serial}")
                        IconButton(onClick = {
                            seriales = seriales - entry
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
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
            Text("Cargar Pago")
        }
    }

    if (showModal) {
        AppModalComponent(onDismiss = { showModal = false }) {
            SerialEntryComponent(
                onAdd = {
                    seriales = seriales + it
                },
                onClose = { showModal = false }
            )
        }
    }
}

@Composable
fun SerialEntryComponent(onAdd: (SerialEntry) -> Unit, onClose: () -> Unit) {
    var valor by remember { mutableStateOf("") }
    var serial by remember { mutableStateOf("") }

    Column(modifier = Modifier.width(300.dp)) {
        Text("Agregar Billete", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InputComponent(
                value = valor,
                onValueChange = { valor = it },
                placeholder = "Valor",
                keyboardType = KeyboardType.Number,
                modifier = Modifier.weight(1f)
            )
            InputComponent(
                value = serial,
                onValueChange = { serial = it },
                placeholder = "Serial",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = onClose) {
                Text("Cancelar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                if (valor.isNotBlank() && serial.isNotBlank()) {
                    onAdd(SerialEntry(valor, serial))
                    valor = ""
                    serial = ""
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    }
}

data class SerialEntry(val valor: String, val serial: String)