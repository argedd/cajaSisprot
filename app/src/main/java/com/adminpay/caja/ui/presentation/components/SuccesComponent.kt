package com.adminpay.caja.ui.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.adminpay.caja.R
import com.adminpay.caja.utils.ScreenDimensions
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SuccessComponent(
    message: String,
    screen: ScreenDimensions,
    onClose: () -> Unit
) {
    val iconSize = screen.widthPercentage(0.08f)
    val spacing = screen.heightPercentage(0.02f)
    val fontSizeTitle = (screen.width.value * 0.018f).sp
    val fontSize = (screen.width.value * 0.014f).sp
    val buttonHeight = screen.heightPercentage(0.050f)
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success))


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing),
        modifier = Modifier
            .wrapContentHeight()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .size(iconSize)
        )

        Text(
            text = "Éxito",
            color = MaterialTheme.colorScheme.primary,
            fontSize = fontSizeTitle,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = fontSize,
            textAlign = TextAlign.Center,
        )

        Text(
            text = message,
            color = Color.Black,
            fontSize = fontSize,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = fontSize,
            textAlign = TextAlign.Center,
        )

        Button(
            onClick = onClose,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .width(screen.widthPercentage(0.25f))
                .height(buttonHeight),
            shape = MaterialTheme.shapes.large
        ) {
            Text("Cerrar", fontSize = fontSize)
        }
    }
}
