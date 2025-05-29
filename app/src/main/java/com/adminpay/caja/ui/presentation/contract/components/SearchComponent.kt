package com.adminpay.caja.ui.presentation.contract.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun Buscador(
    selectedTipo: String,
    cedula: String,
    onCedulaChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        TextField(
            value = selectedTipo,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo") },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xfffafafa),
                unfocusedContainerColor = Color(0xffffffff),
            ),
            modifier = Modifier.width(80.dp).border(1.dp, Color(0xFF004C72), RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        TextField(
            value = cedula,
            onValueChange = onCedulaChange,
            placeholder = { Text("Cédula o RIF") },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            },
            trailingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF004C72))
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xfffafafa),
                unfocusedContainerColor = Color(0xffffffff)

            ),
            modifier = Modifier
                .height(56.dp)
                .weight(1f)
                .border(1.dp, Color(0xFF004C72), RoundedCornerShape(8.dp))

        )
    }
}
