package com.adminpay.caja.ui.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.adminpay.caja.R
import com.adminpay.caja.di.LoadingControllerEntryPoint
import dagger.hilt.android.EntryPointAccessors

@Composable
fun LoadingComponent(modifier: Modifier = Modifier) {
    val context = LocalContext.current.applicationContext
    val loadingController = remember {
        EntryPointAccessors.fromApplication(
            context,
            LoadingControllerEntryPoint::class.java
        ).loadingController()
    }

    val isLoading by loadingController.isLoading.collectAsState()

    // Animación de zoom
    val infiniteTransition = rememberInfiniteTransition(label = "zoom")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    AnimatedVisibility(visible = isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .then(modifier),
            contentAlignment = Alignment.Center
        ) {
            Image(

                painterResource(id = R.drawable.logocolor),
                contentDescription = "Loading...",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(3f / 1f)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
                contentScale = ContentScale.Fit
            )
        }
    }
}
