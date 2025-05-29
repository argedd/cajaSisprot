package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.medioDigital

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.adminpay.caja.R

@Composable
fun MedioDigitalScreen() {
    val options = listOf(
        MedioDigital("Binance", painterResource(id = R.drawable.binance)),
        MedioDigital("PayPal", painterResource(id = R.drawable.paypal)),
        MedioDigital("Zelle", painterResource(id = R.drawable.zelle)),
    )

    var selectedOption by remember { mutableStateOf(options[0].name) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val rows = options.chunked(3)

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { option ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .clickable { selectedOption = option.name },
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                RadioButton(
                                    selected = selectedOption == option.name,
                                    onClick = { selectedOption = option.name }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Image(
                                    painter = option.image,
                                    contentDescription = option.name,
                                    modifier = Modifier.size(100.dp)
                                )
                            }
                        }
                    }

                    // Rellenar espacios vacíos si la fila tiene menos de 3
                    repeat(3 - row.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

data class MedioDigital(val name: String, val image: Painter)
