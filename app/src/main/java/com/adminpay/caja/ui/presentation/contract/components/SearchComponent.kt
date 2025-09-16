package com.adminpay.caja.ui.presentation.contract.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.sp
import com.adminpay.caja.utils.*

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
    val screen = rememberScreenDimensions()
    val scale = screen.scaleFactor() // 🔑 Normalización por ancho

    val paddingHorizontal = screen.widthPercentage(0.2f)
    val fieldHeight = screen.heightPercentage(0.08f)
    val dropdownWidth = screen.widthPercentage(0.1f)
    val spacing = screen.widthPercentage(0.01f)
    val iconSize = (24 * scale).dp // 🔑 iconos adaptados por escala

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            vertical = screen.heightPercentage(0.03f),
            horizontal = paddingHorizontal
        )
    ) {
        // 🟦 Menú desplegable
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .width(dropdownWidth)
                .height(fieldHeight)
        ) {
            TextField(
                value = selectedTipo,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(
                        "Tipo",
                        fontSize = adaptiveFontSizeNew(screen, 10, 12, 14)
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded,
                        modifier = Modifier.size(iconSize)
                    )
                },
                shape = RoundedCornerShape((12 * scale).dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color(0xfffafafa),
                    unfocusedContainerColor = Color.White
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = adaptiveFontSizeNew(screen, 12, 14, 16)
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxHeight()
                    .border(
                        (1 * scale).dp,
                        Color.LightGray,
                        RoundedCornerShape((12 * scale).dp)
                    )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tipos.forEach { tipo ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = tipo,
                                fontSize = adaptiveFontSizeNew(screen, 12, 14, 16)
                            )
                        },
                        onClick = {
                            onTipoSelected(tipo)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(spacing))

        // 🧾 Campo cédula o RIF
        TextField(
            value = cedula,
            onValueChange = onCedulaChange,
            placeholder = {
                Text(
                    "Cédula o RIF",
                    fontSize = adaptiveFontSizeNew(screen, 12, 14, 16)
                )
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = (4 * scale).dp)
                        .size(iconSize)
                )
            },
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .padding(end = (4 * scale).dp)
                        .size((32 * scale).dp)
                        .clickable { onBuscarClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.White,
                            modifier = Modifier.padding((6 * scale).dp)
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape((12 * scale).dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xfffafafa),
                unfocusedContainerColor = Color.White
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = adaptiveFontSizeNew(screen, 12, 14, 16)
            ),
            modifier = Modifier
                .height(fieldHeight)
                .weight(1f)
                .border(
                    (1 * scale).dp,
                    Color.LightGray,
                    RoundedCornerShape((12 * scale).dp)
                )
        )
    }
}
