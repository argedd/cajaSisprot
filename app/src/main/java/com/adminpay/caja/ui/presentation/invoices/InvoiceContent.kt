package com.adminpay.caja.ui.presentation.invoices

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adminpay.caja.domain.model.invoice.FacturaModel

@Composable
fun FacturasModalContent(
    facturas: List<FacturaModel>,
    onPagarClick: (FacturaModel) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.heightIn(max = 600.dp)
    ) {
        itemsIndexed(facturas) { index, factura ->
            FacturaCard(
                factura = factura,
                enabled = index == 0,
                onPagarClick = { onPagarClick(factura) }
            )
        }

    }
}