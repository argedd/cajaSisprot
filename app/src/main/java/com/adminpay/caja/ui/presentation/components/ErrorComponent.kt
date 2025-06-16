package com.adminpay.caja.ui.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.utils.ScreenDimensions

@Composable
fun ErrorComponent(
    message: String,
    screen: ScreenDimensions,
    onClose: () -> Unit
) {
    val iconSize = (screen.width.value * 0.08).dp
    val spacing = (screen.height.value * 0.02).dp
    val fontSize = kotlin.comparisons.maxOf(screen.width.value * 0.020f, 12f).sp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = (screen.width.value * 0.08).dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = "Error",
            tint = Color(0xFF8D051B),
            modifier = Modifier.size(iconSize)
        )

        Spacer(modifier = Modifier.height(spacing))

        Text(
            text = message,
            color = Color.White,
            fontSize = fontSize,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = fontSize * 1.4, // importante para evitar montado
            textAlign = TextAlign.Center, // ✅ centra el texto
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(spacing))

        Button(
            onClick = onClose,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height((screen.height.value * 0.07).dp),
            shape = MaterialTheme.shapes.medium,

            ) {
            Text("Cerrar", fontSize = fontSize)
        }
    }
}

