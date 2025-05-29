package com.adminpay.caja.ui.presentation.contract.components.cardComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adminpay.caja.ui.presentation.contract.Cliente

@Composable
fun ClienteFooter(cliente: Cliente) {
    if (cliente.debt_bs > 0f) {
        Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Button(
                onClick = { /* Acción de pago */ },
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
}
