package com.adminpay.caja.ui.presentation.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.adminpay.caja.domain.model.auth.User
import com.adminpay.caja.ui.presentation.auth.AuthState
import com.adminpay.caja.ui.presentation.auth.AuthViewModel
import com.adminpay.caja.ui.presentation.box.BoxScreen
import com.adminpay.caja.ui.presentation.checkout.CheckoutScreen
import com.adminpay.caja.ui.presentation.main.components.DrawerContent
import com.adminpay.caja.ui.presentation.main.components.HeaderContent
import com.adminpay.caja.utils.rememberScreenDimensions
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedGetBackStackEntry")
@Composable
fun MainScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val authState by authViewModel.authState.collectAsState()

    var selectedScreen by remember { mutableStateOf("facturacion") }

    // Obtenemos el User ya procesado desde el AuthState
    val user: User? = (authState as? AuthState.Success)?.user?.getOrNull()

    Log.d("MainScreen", "User: $user")

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                selectedRoute = selectedScreen,
                onDestinationClicked = { route ->
                    if (route == "logout") {
                        authViewModel.logout()
                    } else {
                        selectedScreen = route
                        scope.launch { drawerState.close() }
                    }
                },
                user = user
            )
        },
    ) {
        Scaffold(
            topBar = {
                HeaderContent(
                    user = user,
                    onMenuClick = { scope.launch { drawerState.open() } },
                )
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding).background(Color.White)) {
                    when (selectedScreen) {
                        "facturacion" -> CheckoutScreen(
                            screen = rememberScreenDimensions(),
                            serviceId = "1234"
                        )
                        "caja" -> BoxScreen()
                    }
                }
            }

        )
    }
}



