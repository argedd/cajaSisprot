package com.adminpay.caja.ui.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun AppModalComponent(
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val screen = rememberScreenDimensions()

    val isTablet = screen.width.value.dp >= 600.dp
    val isLandscape = screen.width.value > screen.height.value

    val maxWidth = when {
        isTablet && isLandscape -> screen.widthPercentage(0.85f)
        isTablet -> screen.widthPercentage(0.8f)
        else -> screen.widthPercentage(0.95f)
    }

    val maxHeight = when {
        isTablet && isLandscape -> screen.heightPercentage(0.85f)
        isTablet -> screen.heightPercentage(0.85f)
        else -> screen.heightPercentage(0.9f)
    }

    val shapeSize = screen.widthPercentage(0.015f)
    val padding = screen.widthPercentage(0.01f)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()     // Se adapta al contenido en ancho
                .heightIn(max = maxHeight)
                .widthIn(max = maxWidth),  // Límite máximo de ancho
            shape = RoundedCornerShape(shapeSize),
            tonalElevation = 8.dp,
            color = Color.White
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = content
                )
            }
        }
    }
}







