package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.efectivo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import com.adminpay.caja.domain.model.paymentMethods.CashDollarBill
import com.adminpay.caja.ui.presentation.components.InputComponent
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.adminpay.caja.utils.adaptiveFontSize
import com.adminpay.caja.utils.validateDollarSerial
import com.adminpay.caja.utils.rememberScreenDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashDollarBillComponent(
    onAdd: (CashDollarBill) -> Unit,
    onClose: () -> Unit,
    maxAmount: Double,
    currentTotal: Double // ✅ nuevo parámetro
) {
    var valor by remember { mutableStateOf("") }
    var serial by remember { mutableStateOf("") }
    val validationResult = remember(serial) { validateDollarSerial(serial) }
    var expanded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val options = listOf("1", "5", "10", "20", "50", "100")
    val screen = rememberScreenDimensions()
    val bodyFontSize = adaptiveFontSize(screen, small = 12.sp, medium = 14.sp, large = 24.sp)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Agregar Billete", fontSize = bodyFontSize)
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .width(screen.widthPercentage(0.18f))
                    .height(screen.heightPercentage(0.09f))

            ) {
                TextField(
                    value = valor,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Denominación", fontSize = bodyFontSize ) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color(0xfffafafa),
                        unfocusedContainerColor = Color.White
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxHeight()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, fontSize = bodyFontSize) },
                            onClick = {
                                valor = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            InputComponent(
                value = serial,
                onValueChange = { serial = it },
                placeholder = "Serial",
                modifier = Modifier.weight(1f).height(50.dp),
                isError = !validationResult.isValid && serial.isNotEmpty(),
            )
        }

        if (!validationResult.isValid && serial.isNotEmpty()) {
            Text(
                text = validationResult.message,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = onClose) {
                Text("Cancelar", fontSize = bodyFontSize)
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                val amount = valor.toIntOrNull()
                when {
                    valor.isBlank() || serial.isBlank() -> {
                        errorMessage = "Todos los campos son obligatorios"
                    }
                    amount == null -> {
                        errorMessage = "Denominación inválida"
                    }
//                    currentTotal + amount > maxAmount -> {
//                        errorMessage = "No puedes exceder el monto máximo permitido ($maxAmount)"
//                    }
                    else -> {
                        onAdd(CashDollarBill(denomination = amount, serialCode = serial))
                        valor = ""
                        serial = ""
                        errorMessage = null
                    }
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar" )
            }
        }
    }
}



