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
import androidx.compose.ui.unit.sp
import com.adminpay.caja.utils.ScreenDimensions

@Composable
fun ErrorComponent(
    message: String,
    screen: ScreenDimensions,
    onClose: () -> Unit
) {
    val iconSize = screen.widthPercentage(0.05f)
    val spacing = screen.heightPercentage(0.02f)
    val fontSize = (screen.width.value * 0.018f).sp
    val buttonHeight = screen.heightPercentage(0.050f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = "Error",
            tint = Color(0xFF8D051B),
            modifier = Modifier.size(iconSize)
        )

        Text(
            text = message,
            color = Color.Black,
            fontSize = fontSize,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = fontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onClose,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Cerrar", fontSize = fontSize)
        }
    }
}
