package com.adminpay.caja.ui.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.adminpay.caja.R
import com.adminpay.caja.di.LoadingControllerEntryPoint
import com.adminpay.caja.utils.rememberScreenDimensions
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
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
    val screen = rememberScreenDimensions()
    val iconSize = screen.widthPercentage(0.2f)

    val isLoading by loadingController.isLoading.collectAsState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))




    AnimatedVisibility(visible = isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .then(modifier),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .size(iconSize)
            )
        }
    }
}
