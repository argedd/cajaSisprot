package com.adminpay.caja.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * Clase que contiene las dimensiones de la pantalla y métodos de ayuda
 */
data class ScreenDimensions(
    val width: Dp,
    val height: Dp
) {
    /**
     * Calcula un porcentaje del ancho de pantalla
     * @param percentage Porcentaje (0.0 a 1.0)
     */
    fun widthPercentage(percentage: Float): Dp = width * percentage

    /**
     * Calcula un porcentaje del alto de pantalla
     * @param percentage Porcentaje (0.0 a 1.0)
     */
    fun heightPercentage(percentage: Float): Dp = height * percentage
}

/**
 * Función composable que provee las dimensiones actuales de la pantalla
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun rememberScreenDimensions(): ScreenDimensions {
    val configuration = LocalConfiguration.current
    return ScreenDimensions(
        width = configuration.screenWidthDp.dp,
        height = configuration.screenHeightDp.dp
    )
}

enum class ScreenCategory {
    SMALL, MEDIUM, LARGE
}

fun ScreenDimensions.getCategory(): ScreenCategory = when {
    width.value < 800f -> ScreenCategory.SMALL
    width.value < 1400f -> ScreenCategory.MEDIUM
    else -> ScreenCategory.LARGE
}

@Composable
fun adaptiveFontSize(
    screen: ScreenDimensions,
    small: TextUnit,
    medium: TextUnit,
    large: TextUnit
): TextUnit {
    return when (screen.getCategory()) {
        ScreenCategory.SMALL -> small
        ScreenCategory.MEDIUM -> medium
        ScreenCategory.LARGE -> large
    }
}