package com.adminpay.caja.ui.presentation.main.components.tasa

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adminpay.caja.utils.rememberScreenDimensions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TasaBcvWidgetCard(
    modifier: Modifier = Modifier,
    viewModel: TasaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val screen = rememberScreenDimensions()
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    val titleFontSize = (screen.width.value * 0.015).sp
    val amountFontSize = (screen.width.value * 0.02).sp
    val iconSize = screen.widthPercentage(0.03f)
    val smallPadding = screen.heightPercentage(0.005f)
    val mediumPadding = screen.heightPercentage(0.015f)
    val horizontalPadding = screen.widthPercentage(0.01f)

    LaunchedEffect(Unit) {
        viewModel.loadCurrentDayTasa()
    }

    Card(
        modifier = modifier
            .padding(horizontal = horizontalPadding, vertical = smallPadding)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = horizontalPadding, vertical = mediumPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AttachMoney,
                contentDescription = "Icono de tasa",
                tint = Color(0xFF00FFAA),
                modifier = Modifier.size(iconSize)
            )

            Spacer(modifier = Modifier.width(screen.widthPercentage(0.04f)))

            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = "Tasa BCV",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = titleFontSize
                    )
                )
                Spacer(modifier = Modifier.height(smallPadding))
                val tasaText = state.tasas.firstOrNull()?.let {
                    "${it.amount} BS"
                } ?: "No disponible"
                Text(
                    text = tasaText,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = Color(0xFF00FFAA),
                        fontSize = amountFontSize
                    )
                )
            }

            Box(
                modifier = Modifier.padding(start = screen.widthPercentage(0.02f))
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(screen.widthPercentage(0.06f))
                    )
                } else {
                    IconButton(onClick = {
                        isLoading = true
                        coroutineScope.launch {
                            viewModel.loadCurrentDayTasa()
                            delay(1200)
                            isLoading = false
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Actualizar tasa",
                            tint = Color.White,
                            modifier = Modifier.size(screen.widthPercentage(0.03f))
                        )
                    }
                }
            }
        }
    }
}

