package com.adminpay.caja.ui.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.adminpay.caja.utils.rememberScreenDimensions
import kotlinx.coroutines.delay

@Composable
fun AppModalComponent(
    title: String? = null,
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
    val padding = screen.widthPercentage(0.015f)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            // Delay para activar animación justo después de aparecer
            delay(50)
            visible = true
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(200)) + scaleIn(initialScale = 0.95f),
            exit = fadeOut(animationSpec = tween(150)) + scaleOut(targetScale = 0.95f)
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .sizeIn(maxWidth = maxWidth, maxHeight = maxHeight)
                    .shadow(12.dp, RoundedCornerShape(shapeSize)), // Sombra suave
                shape = RoundedCornerShape(shapeSize),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (title != null) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    content()
                }
            }
        }
    }
}
