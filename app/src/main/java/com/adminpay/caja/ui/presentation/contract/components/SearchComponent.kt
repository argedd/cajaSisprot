package com.adminpay.caja.ui.presentation.contract.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Buscador(
    selectedTipo: String,
    cedula: String,
    onTipoSelected: (String) -> Unit,
    onCedulaChange: (String) -> Unit,
    onBuscarClick: () -> Unit
) {
    val tipos = listOf("V", "E", "J", "G", "P")
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
    ) {
        // 🟦 Tipo con menú desplegable (más ancho y misma altura que el TextField)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .width(100.dp)
                .height(56.dp)
        ) {
            TextField(
                value = selectedTipo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo") },
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
                    .fillMaxHeight() // 🔄 igual altura que el padre
                    .border(1.dp, Color(0xFF004C72), RoundedCornerShape(8.dp))
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tipos.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo) },
                        onClick = {
                            onTipoSelected(tipo)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // 🧾 Campo de cédula o RIF
        TextField(
            value = cedula,
            onValueChange = onCedulaChange,
            placeholder = { Text("Cédula o RIF") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            trailingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = Color(0xFF004C72),
                    modifier = Modifier.clickable { onBuscarClick() }
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xfffafafa),
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .height(56.dp)
                .weight(1f)
                .border(1.dp, Color(0xFF004C72), RoundedCornerShape(8.dp))
        )
    }
}


