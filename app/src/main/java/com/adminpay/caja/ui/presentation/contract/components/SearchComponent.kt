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
import androidx.compose.ui.unit.sp
import com.adminpay.caja.utils.rememberScreenDimensions

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

    val paddingHorizontal = screen.widthPercentage(0.2f)
    val fieldHeight = screen.heightPercentage(0.07f)
    val dropdownWidth = screen.widthPercentage(0.08f)
    val spacing = screen.widthPercentage(0.01f)

    // Nuevas dimensiones para iconos y textos
    val iconSize = screen.widthPercentage(0.022f)


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            vertical = screen.heightPercentage(0.03f),
            horizontal = paddingHorizontal
        )
    ) {
        // 🟦 Menú desplegable de tipo
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
                        fontSize = (screen.width.value * 0.012).sp
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded,
                        modifier = Modifier.size(iconSize)
                    )
                },
                shape = RoundedCornerShape(screen.widthPercentage(0.03f)),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color(0xfffafafa),
                    unfocusedContainerColor = Color.White
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = (screen.width.value * 0.015).sp
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxHeight()
                    .border(
                        screen.widthPercentage(0.0005f),
                        Color.LightGray,
                        RoundedCornerShape(screen.widthPercentage(0.03f))
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
                                fontSize = (screen.width.value * 0.02).sp
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

        // 🧾 Campo de cédula o RIF
        TextField(
            value = cedula,
            onValueChange = onCedulaChange,
            placeholder = {
                Text(
                    "Cédula o RIF",
                    fontSize = (screen.width.value * 0.014).sp
                )
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = screen.widthPercentage(0.01f))
                        .size(iconSize)
                )
            },

            trailingIcon = {
                Box(
                    modifier = Modifier
                        .padding(end = screen.widthPercentage(0.004f))
                        .size(screen.widthPercentage(0.04f))
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
                            modifier = Modifier.padding(screen.widthPercentage(0.01f))
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(screen.widthPercentage(0.03f)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xfffafafa),
                unfocusedContainerColor = Color.White
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = (screen.width.value * 0.014).sp
            ),
            modifier = Modifier
                .height(fieldHeight)
                .weight(1f)
                .border(
                    screen.widthPercentage(0.0005f),
                    Color.LightGray,
                    RoundedCornerShape(screen.widthPercentage(0.03f))
                )
        )
    }
}



