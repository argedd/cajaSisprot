package com.adminpay.caja.ui.presentation.box

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adminpay.caja.domain.model.paymentMethods.CardData
import com.adminpay.caja.domain.model.paymentMethods.CardStyle
import com.adminpay.caja.ui.presentation.box.components.PaymentMethodCardComponent
import com.adminpay.caja.ui.presentation.box.components.PaymentMethodsBarChart
import com.adminpay.caja.ui.presentation.box.components.TotalCard
import com.adminpay.caja.ui.presentation.box.components.mapSummaryToCards

@Composable
fun BoxScreen(viewModel: BoxViewModel = hiltViewModel()) {
    val summary by viewModel.summary.collectAsState()



    LaunchedEffect(Unit) {
        viewModel.loadSummary()
    }

    if (summary == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val paymentMethods = mapSummaryToCards(summary!!.data)
    val totalAmount = summary!!.total.totalAmount
    val totalTransactions = summary!!.total.totalQuantity

    Scaffold(
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Acción de cierre */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Cierre", tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Cierre de caja", color = Color.White)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text("Resumen General", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    TotalCard(CardStyle.Dark, CardData("Transacciones", 0f, totalTransactions), Modifier.weight(1f))
                    TotalCard(CardStyle.Dark, CardData("Total Generado", totalAmount), Modifier.weight(1f))
                }
            }

            item {
                Text("Métodos de Pago", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
            }

            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 600.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    userScrollEnabled = false
                ) {
                    items(paymentMethods.size) { index ->
                        PaymentMethodCardComponent(paymentMethods[index])
                    }
                }
            }

            item {
                PaymentMethodsBarChart(paymentMethods)
            }
        }
    }
}

