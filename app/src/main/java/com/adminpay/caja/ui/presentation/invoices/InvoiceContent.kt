package com.adminpay.caja.ui.presentation.invoices

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adminpay.caja.domain.model.invoice.InvoiceModel
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun FacturasModalContent(
    contract: String,
    onPagarClick: (InvoiceModel) -> Unit,
    viewModel: InvoicesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val screen = rememberScreenDimensions()

    LaunchedEffect(contract) {
        contract.toIntOrNull()?.let {
            viewModel.loadInvoices(it.toString())
        }
    }

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screen.heightPercentage(0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(screen.widthPercentage(0.02f)),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: ${state.error}")
            }
        }

        else -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(if(state.invoices.size > 1) 2 else 1),
                    verticalArrangement = Arrangement.spacedBy(screen.heightPercentage(0.015f)),
                    horizontalArrangement = Arrangement.spacedBy(screen.widthPercentage(0.015f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = screen.heightPercentage(0.6f)) // ✅ esto salva el crash
                ) {
                    itemsIndexed(state.invoices.sortedBy { it.dateEmission }) { index, factura ->
                        FacturaCard(
                            factura = factura,
                            enabled = index == 0,
                            onPagarClick = { onPagarClick(factura) }
                        )
                    }
                }
            }

        }
    }
}


