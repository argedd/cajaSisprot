package com.adminpay.caja.ui.navigation

sealed class Routes(val route: String) {
    object MainScreen : Routes("main_screen")
    object AuthScreen : Routes("auth_screen")


    // Rutas internas dentro del drawer
    object ContractScreen : Routes("contract_screen")
    object BoxScreen : Routes("box_screen")
    object CheckoutScreen : Routes("checkout_screen")


    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
