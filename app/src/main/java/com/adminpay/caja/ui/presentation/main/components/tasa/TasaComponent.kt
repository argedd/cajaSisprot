package com.adminpay.caja.ui.presentation.main.components.tasa

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun TasaBcv(viewModel: TasaViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val screen = rememberScreenDimensions()

    // Ejecuta una vez al montar para pedir las tasas
    LaunchedEffect(Unit) {
        viewModel.loadCurrentDayTasa()  // Asumo que tienes esta función en el ViewModel
    }

    // Log para verificar contenido de tasas
    Log.d("TasaBcv", "Tasas actuales: ${state.tasas}")

    state.tasas.firstOrNull()?.let { tasa ->
        Text(
            text = "${tasa.amount} BS",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF4CAF50),
                fontWeight = FontWeight.Bold,
                fontSize = screen.widthPercentage(0.035f).value.sp
            )
        )
    } ?: run {
        Text(
            text = "No disponible",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = screen.widthPercentage(0.035f).value.sp
        )
    }
}
