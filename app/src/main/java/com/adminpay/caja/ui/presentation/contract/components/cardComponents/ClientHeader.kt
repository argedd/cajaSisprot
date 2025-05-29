package com.adminpay.caja.ui.presentation.contract.components.cardComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.ui.presentation.contract.Cliente

@Composable
fun ClienteHeader(cliente: Cliente) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF004C72))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = cliente.name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

    }
}
