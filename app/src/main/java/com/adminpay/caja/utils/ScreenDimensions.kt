package com.adminpay.caja.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ScreenDimensions(
    val width: Dp,
    val height: Dp
) {
    fun widthPercentage(percentage: Float): Dp = width * percentage
    fun heightPercentage(percentage: Float): Dp = height * percentage

    // Factor relativo a un ancho base (ej. 800dp ~ tablet 8")
    fun scaleFactor(baseWidth: Float = 800f): Float = width.value / baseWidth
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

@Composable
fun adaptiveFontSizeNew(
    screen: ScreenDimensions,
    small: Int,
    medium: Int,
    large: Int
): TextUnit {
    return when (screen.getCategory()) {
        ScreenCategory.SMALL -> small.sp
        ScreenCategory.MEDIUM -> medium.sp
        ScreenCategory.LARGE -> large.sp
    }
}

@Composable
fun adaptiveFontSizeScaled(
    screen: ScreenDimensions,
    base: Int = 14 // tamaño base
): TextUnit {
    val scale = screen.scaleFactor() // relativo a 800dp
    return (base * scale).sp
}
