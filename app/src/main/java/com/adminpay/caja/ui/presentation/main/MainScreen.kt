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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.adminpay.caja.domain.model.auth.User
import com.adminpay.caja.ui.navigation.MainNavHost
import com.adminpay.caja.ui.presentation.auth.AuthState
import com.adminpay.caja.ui.presentation.auth.AuthViewModel
import com.adminpay.caja.ui.presentation.main.components.DrawerContent
import com.adminpay.caja.ui.presentation.main.components.HeaderContent
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val authState by authViewModel.authState.collectAsState()

    val user: User? = (authState as? AuthState.Success)?.user?.getOrNull()

    // NavController interno para navegación dentro del layout principal
    val internalNavController = rememberNavController()

    // Obtener la ruta actual
    val currentRoute = internalNavController.currentBackStackEntryAsState().value?.destination?.route.orEmpty()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                selectedRoute = currentRoute,
                onDestinationClicked = { route ->
                    if (route == "logout") {
                        authViewModel.logout()
                    } else {
                        internalNavController.navigate(route) {
                            launchSingleTop = true
                            popUpTo(internalNavController.graph.startDestinationId) {
                                saveState = true
                            }
                            restoreState = true
                        }
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
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .background(Color.White)
            ) {
                MainNavHost(navController = internalNavController, authViewModel = authViewModel)
            }
        }
    }
}
