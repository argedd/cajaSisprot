package com.adminpay.caja.ui.presentation.contract.components.cardComponents

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.adminpay.caja.domain.model.contract.Contract
import com.adminpay.caja.ui.navigation.Routes
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import com.adminpay.caja.ui.presentation.components.AppModalComponent
import com.adminpay.caja.ui.presentation.invoices.FacturasModalContent
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import com.adminpay.caja.utils.rememberScreenDimensions

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun ClienteFooter(
    cliente: Contract,
    navController: NavHostController
) {
    var showModal by remember { mutableStateOf(false) }
    val tieneDeuda = cliente.debtBs > 0f && (cliente.status == 19 || cliente.status == 16)
    val parentEntry = remember {
        navController.getBackStackEntry(Routes.ContractScreen.route)
    }
    val sharedViewModel: CheckoutSharedViewModel = hiltViewModel(parentEntry)
    val screen = rememberScreenDimensions()


    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(screen.widthPercentage(0.02f))
    ) {
        Button(
            onClick = { showModal = true },
            enabled = tieneDeuda, // ✅ Solo habilitado si hay deuda
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFCA311),
                disabledContainerColor = Color.LightGray,
                contentColor = Color.White
            )
        ) {
            Text("Ver Detalles",fontSize=(screen.width.value * 0.012).sp, modifier = Modifier.padding(vertical = screen.heightPercentage(0.005f)))
        }
    }

    if (showModal) {
        AppModalComponent(onDismiss = { showModal = false }) {
            FacturasModalContent(
                contract = cliente.id.toString(),
                onPagarClick = { factura ->
                    showModal = false
                    sharedViewModel.selectedInvoice = factura // ✅ aquí se guarda
                    sharedViewModel.bankAssociated = cliente.bankAssociated
                    navController.navigate("checkout_screen")
                }
            )
        }
    }
}

