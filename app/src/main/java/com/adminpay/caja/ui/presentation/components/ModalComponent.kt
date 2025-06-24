package com.adminpay.caja.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.adminpay.caja.utils.rememberScreenDimensions


@Composable
fun AppModalComponent(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val screen = rememberScreenDimensions()
    val padding = screen.widthPercentage(0.04f)
    val shapeSize = screen.widthPercentage(0.02f)
    val maxModalWidth = screen.widthPercentage(0.9f) // máx 90% del ancho

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .widthIn(max = maxModalWidth) // importante
                .wrapContentHeight(),
            shape = RoundedCornerShape(shapeSize),
            tonalElevation = 8.dp,
            color = Color.LightGray
        ) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                content()
            }
        }
    }
}


