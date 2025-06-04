package com.adminpay.caja.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.adminpay.caja.ui.presentation.box.BoxScreen
import com.adminpay.caja.ui.presentation.checkout.CheckoutScreen
import com.adminpay.caja.ui.presentation.contract.ContractScreen
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.ContractScreen.route
    ) {
        composable(Routes.ContractScreen.route) { ContractScreen(navController) }
        composable(Routes.BoxScreen.route) { BoxScreen() }
        composable(Routes.CheckoutScreen.route) { CheckoutScreen(
            navController=navController,
            screen = rememberScreenDimensions(),
            serviceId = "123456"
        ) }
        // Aquí agregas más pantallas internas si las necesitas
    }
}
