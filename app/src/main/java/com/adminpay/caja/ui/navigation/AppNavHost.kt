package com.adminpay.caja.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adminpay.caja.ui.presentation.auth.AuthScreen
import com.adminpay.caja.ui.presentation.auth.AuthState
import com.adminpay.caja.ui.presentation.auth.AuthViewModel
import com.adminpay.caja.ui.presentation.main.MainScreen


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.AuthScreen.route,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val state = authViewModel.authState.collectAsState().value

    LaunchedEffect(state) {

        when (state) {
            is AuthState.Success -> {
                navController.navigate(Routes.MainScreen.route) {
                    popUpTo(Routes.AuthScreen.route) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                // No navegar a ningún lado. El AuthScreen ya está visible.
                // Puedes mostrar un Snackbar o mensaje de error desde el AuthScreen.
            }
            AuthState.Idle -> {
                // Al iniciar la app, asegúrate de estar en AuthScreen
                navController.navigate(Routes.AuthScreen.route) {
                    popUpTo(0) // limpiar todo si es necesario
                }
            }
            else -> {}
        }
    }



    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.MainScreen.route) {
            MainScreen(navController= navController, authViewModel = authViewModel)
        }

        composable(Routes.AuthScreen.route) {
            AuthScreen(authViewModel = authViewModel )
        }
    }
}

