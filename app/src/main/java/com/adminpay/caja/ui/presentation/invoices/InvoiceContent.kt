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

@Composable
fun FacturasModalContent(
    contract: String,
    onPagarClick: (InvoiceModel) -> Unit,
    viewModel: InvoicesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(contract) {
        contract.toIntOrNull()?.let { contractId ->
            viewModel.loadInvoices(contract = contractId.toString())
        }
    }

    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Text("Error: ${state.error}")
        }

        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = if(state.invoices.size == 1) 1 else 2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.heightIn(max = 600.dp)
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
