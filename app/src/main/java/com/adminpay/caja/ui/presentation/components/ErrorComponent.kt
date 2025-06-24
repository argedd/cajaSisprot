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
    val iconSize = screen.widthPercentage(0.08f)
    val spacing = screen.heightPercentage(0.025f)
    val fontSize = (screen.width.value * 0.02).sp
    val buttonHeight = screen.heightPercentage(0.07f)
    val horizontalPadding = screen.widthPercentage(0.1f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = horizontalPadding)
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
            color = Color.Black,
            fontSize = fontSize,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = fontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(spacing))

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


