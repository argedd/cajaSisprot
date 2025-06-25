package com.adminpay.caja.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AdaptiveBoxModal(
    onDismiss: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // Fondo semitransparente
            .clickable(onClick = onDismiss),            // Cerrar al click fuera
        contentAlignment = Alignment.Center
    ) {
        // Este Box es el modal en sí
        Box(
            modifier = Modifier
                .wrapContentSize()                     // Tamaño dinámico según contenido
                .background(Color.White, RoundedCornerShape(16.dp))
                .clickable(enabled = false) {}         // Evitar cierre al click dentro
                .padding(16.dp),
            content = content
        )
    }
}
