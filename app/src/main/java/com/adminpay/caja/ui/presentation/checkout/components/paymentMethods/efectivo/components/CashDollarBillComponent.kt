package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.efectivo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.adminpay.caja.domain.model.paymentMethods.CashDollarBill
import com.adminpay.caja.ui.presentation.components.InputComponent

@Composable
fun CashDollarBillComponent(onAdd: (CashDollarBill) -> Unit, onClose: () -> Unit) {
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
                modifier = Modifier.weight(1f),
                placeholder = "Denominación",
                keyboardType = KeyboardType.Number,
                readOnly = true
            )
            InputComponent(
                value = serial,
                onValueChange = { serial = it },
                modifier = Modifier.weight(1f),
                placeholder = "Serial",
                readOnly = true
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
                    onAdd(
                        CashDollarBill(
                            denomination = valor.toIntOrNull(),
                            serialCode = serial
                        )
                    )
                    valor = ""
                    serial = ""
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    }
}
