package com.adminpay.caja.ui.presentation.components

import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import kotlin.let

@Composable
fun HideNavigationBar() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    LaunchedEffect(Unit) {
        activity?.let {
            WindowCompat.setDecorFitsSystemWindows(it.window, false)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val controller = it.window.insetsController
                controller?.hide(WindowInsets.Type.navigationBars())
                controller?.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}
