package com.adminpay.caja.ui.presentation.contract.components.cardComponents

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adminpay.caja.domain.model.invoice.FacturaModel
import com.adminpay.caja.ui.presentation.components.AppModalComponent
import com.adminpay.caja.ui.presentation.contract.Cliente
import com.adminpay.caja.ui.presentation.invoices.FacturasModalContent

@Composable
fun ClienteFooter(
    cliente: Cliente,
    facturas: List<FacturaModel>,
    navController: NavHostController
) {
    var showModal by remember { mutableStateOf(false) }

    if (cliente.debt_bs > 0f) {
        Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Button(
                onClick = { showModal = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFCA311),
                    contentColor = Color.White
                )
            ) {
                Text("Ver Facturas")
            }
        }
    }

    if (showModal) {
        AppModalComponent(onDismiss = { showModal = false }) {
            FacturasModalContent(
                facturas = facturas,
                onPagarClick = {
                    showModal = false
                    navController.navigate("checkout_screen") // Puedes pasar datos si deseas
                }
            )
        }
    }
}
