package com.adminpay.caja.ui.presentation.contract.components.cardComponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.domain.model.contract.Contract

@Composable
fun ClienteBody(cliente: Contract) {
    val labelColor = Color(0xFFFCA311)
    val textSizeLabel = 12.sp
    val textSizeValue = 14.sp

    Column(modifier = Modifier.padding(12.dp)) {

        // Fila para Contrato y Estado
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GridItem(
                icon = Icons.Default.FormatListNumbered,
                label = "Contrato",
                value = cliente.id.toString(),
                labelColor = labelColor,
                textSizeLabel = textSizeLabel,
                textSizeValue = textSizeValue,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            GridItem(
                icon = Icons.Default.SignalWifiStatusbar4Bar,
                label = "Estado",
                value = cliente.statusName,
                labelColor = labelColor,
                textSizeLabel = textSizeLabel,
                textSizeValue = textSizeValue,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            GridItem(
                icon = Icons.Default.Money,
                label = "Deuda",
                value = "${cliente.debtBs} Bs",
                labelColor = labelColor,
                textSizeLabel = textSizeLabel,
                textSizeValue = textSizeValue,
                modifier = Modifier.weight(1f)
            )
        }


        Spacer(modifier = Modifier.height(4.dp))

        // Dirección fuera del grid
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Dirección",
                tint = labelColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text(
                    text = "Dirección",
                    color = labelColor,
                    fontSize = textSizeLabel,
                    fontWeight = FontWeight.Bold
                )
                Text(text = cliente.addressTax, fontSize = textSizeValue)
            }
        }
    }
}

@Composable
private fun GridItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    labelColor: Color,
    textSizeLabel: androidx.compose.ui.unit.TextUnit,
    textSizeValue: androidx.compose.ui.unit.TextUnit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier.padding(4.dp)
    ) {
        Icon(icon, contentDescription = label, tint = labelColor, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(label, color = labelColor, fontSize = textSizeLabel, fontWeight = FontWeight.Bold)
            Text(value, fontSize = textSizeValue)
        }
    }
}
