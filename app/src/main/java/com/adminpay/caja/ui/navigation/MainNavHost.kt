package com.adminpay.caja.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.adminpay.caja.ui.presentation.auth.AuthViewModel
import com.adminpay.caja.ui.presentation.box.BoxScreen
import com.adminpay.caja.ui.presentation.checkout.CheckoutScreen
import com.adminpay.caja.ui.presentation.contract.ContractScreen
import com.adminpay.caja.ui.presentation.finish.FinishScreen
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun MainNavHost(navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(
        navController = navController,
        startDestination = Routes.ContractScreen.route
    ) {
        composable(Routes.ContractScreen.route) { ContractScreen(navController) }
        composable(Routes.BoxScreen.route) { BoxScreen() }
        composable(Routes.CheckoutScreen.route) { CheckoutScreen(
            navController=navController,
            screen = rememberScreenDimensions(),
            authViewModel = authViewModel
        ) }
        composable(Routes.FinishScreen.route) {
            FinishScreen(navController= navController)
        }
    }
}
