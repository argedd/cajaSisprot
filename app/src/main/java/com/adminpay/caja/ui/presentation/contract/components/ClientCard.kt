package com.adminpay.caja.ui.presentation.contract.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adminpay.caja.domain.model.invoice.AmountBs
import com.adminpay.caja.domain.model.invoice.FacturaModel
import com.adminpay.caja.ui.presentation.contract.Cliente
import com.adminpay.caja.ui.presentation.contract.components.cardComponents.ClienteBody
import com.adminpay.caja.ui.presentation.contract.components.cardComponents.ClienteFooter
import com.adminpay.caja.ui.presentation.contract.components.cardComponents.ClienteHeader

@Composable
fun ClienteCard(cliente: Cliente, navController: NavHostController) {
    val facturas = listOf(
        FacturaModel(
            id = 40973,
            date_emission = "2025-04-27",
            date_expiration = "2025-04-29",
            amount_bs = AmountBs(876.94, 755.98, 120.96),
            debt_bs = 876.94
        ),
        FacturaModel(
            id = 40974,
            date_emission = "2025-05-01",
            date_expiration = "2025-05-05",
            amount_bs = AmountBs(950.00, 803.50, 146.50),
            debt_bs = 950.00
        ),
        FacturaModel(
            id = 40975,
            date_emission = "2025-05-10",
            date_expiration = "2025-05-15",
            amount_bs = AmountBs(1020.20, 865.00, 155.20),
            debt_bs = 1020.20
        )
    )
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            ClienteHeader(cliente)
            ClienteBody(cliente)
            HorizontalDivider()
            ClienteFooter(
                cliente = cliente,
                facturas = facturas,
                navController = navController
            )

        }
    }
}
