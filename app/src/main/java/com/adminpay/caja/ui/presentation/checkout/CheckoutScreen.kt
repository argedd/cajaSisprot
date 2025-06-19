package com.adminpay.caja.ui.presentation.checkout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.adminpay.caja.ui.navigation.Routes
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.PaymentMethodsScreen
import com.adminpay.caja.ui.presentation.checkout.components.sumary.CheckoutSummary
import com.adminpay.caja.ui.presentation.main.components.tasa.TasaViewModel
import com.adminpay.caja.utils.ScreenDimensions

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun CheckoutScreen(
    screen: ScreenDimensions,
    navController: NavHostController
) {

    val parentEntry = remember {
        navController.getBackStackEntry(Routes.ContractScreen.route)
    }
    val sharedViewModel: CheckoutSharedViewModel = hiltViewModel(parentEntry)


    val tasaViewModel: TasaViewModel = hiltViewModel()
    val tasaState by tasaViewModel.state.collectAsState()
    val tasa = tasaState.tasas.firstOrNull()

    LaunchedEffect(Unit) {
        sharedViewModel.clearAmounts()
        sharedViewModel.clearPaymentMethods()
    }


    Box(modifier = Modifier.fillMaxSize()) {
        // Contenido principal
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Parte izquierda: Métodos de pago
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                PaymentMethodsScreen(
                    screen = screen,
                    sharedViewModel = sharedViewModel,
                    tasa = tasa
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Parte derecha: Resumen de compra
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ) {
                CheckoutSummary(
                    screen = screen,
                    sharedViewModel = sharedViewModel,
                    finish = { navController.navigate("finish_screen") } // Usa tu route real aquí si es diferente
                )
            }
        }

        // Botón de volver en la parte superior derecha
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver"
            )
        }
    }
}
