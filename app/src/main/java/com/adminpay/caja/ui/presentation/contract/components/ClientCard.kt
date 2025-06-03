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
import com.adminpay.caja.ui.presentation.contract.Cliente
import com.adminpay.caja.ui.presentation.contract.components.cardComponents.ClienteBody
import com.adminpay.caja.ui.presentation.contract.components.cardComponents.ClienteFooter
import com.adminpay.caja.ui.presentation.contract.components.cardComponents.ClienteHeader

@Composable
fun ClienteCard(cliente: Cliente, navController: NavHostController) {
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
            ClienteFooter(cliente,  navController)
        }
    }
}
