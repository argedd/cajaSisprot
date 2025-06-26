package com.adminpay.caja

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun rememberScreenDimensions(): DpSize {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    return DpSize(screenWidth, screenHeight)
}

@Composable
fun FullScreenModal90(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val screen = rememberScreenDimensions()
    val dialogWidth = screen.width * 0.9f
    val dialogHeight = screen.height * 0.9f

    Dialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(
            usePlatformDefaultWidth = false // ❗️ Clave para que el ancho personalizado funcione en tablets
        )
    ) {
        Box(
            modifier = Modifier
                .width(dialogWidth)
                .height(dialogHeight)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            content()
        }
    }
}



@Composable
fun DemoFullScreenModalUsage() {
    var showModal by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { showModal = true }) {
            Text("Mostrar Modal 90%")
        }

        if (showModal) {
            FullScreenModal90(onDismiss = { showModal = false }) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Este es un modal al 90%", fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = { showModal = false }) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }
}
