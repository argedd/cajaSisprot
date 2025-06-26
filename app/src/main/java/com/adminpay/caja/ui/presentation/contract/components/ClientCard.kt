package com.adminpay.caja.ui.presentation.contract.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.TransferWithinAStation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.adminpay.caja.R
import com.adminpay.caja.domain.model.contract.Contract
import com.adminpay.caja.ui.presentation.contract.components.cardComponents.ClienteFooter
import com.adminpay.caja.ui.presentation.contract.components.cardComponents.InfoItemWithIcon
import com.adminpay.caja.utils.rememberScreenDimensions

@Composable
fun ClienteCard(cliente: Contract, navController: NavHostController) {
    val screen = rememberScreenDimensions()
    val padding = screen.widthPercentage(0.013f)
    val iconSize = screen.widthPercentage(0.05f)
    val smallText = (screen.width.value * 0.012).sp
    val titleText = (screen.width.value * 0.015).sp
    val iconLabelSize = screen.widthPercentage(0.015f)

    Card(
        modifier = Modifier
            .padding(padding)
            .width(screen.widthPercentage(0.4f)),
        shape = RoundedCornerShape(screen.widthPercentage(0.03f)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(padding)) {

            // Header
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {

                // Imagen o icono circular
                Box(
                    modifier = Modifier
                        .size(iconSize)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logocolor),
                        contentDescription = "Foto cliente",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.width(screen.widthPercentage(0.03f)))

                // Nombre e identificación
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${cliente.name} ${cliente.lastName}",
                        fontSize = titleText,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = "Contrato: ${cliente.id}",
                        fontSize = smallText,
                        color = Color.LightGray
                    )
                }

                // Estatus
                val statusColor = when (cliente.statusName) {
                    "Activo" -> Color(0xFF4CAF50)
                    "Cancelado" -> Color(0xFFF44336)
                    else -> Color(0xFFFFC107)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Estatus",
                            tint = statusColor,
                            modifier = Modifier.size(iconLabelSize)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = cliente.statusName,
                            fontSize = smallText,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(screen.heightPercentage(0.015f)))

            // Fila con migrado, sector, deuda
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//
//                InfoItemWithIcon(
//                    icon = Icons.Default.AlternateEmail,
//                    label = "Email",
//                    value = cliente.email,
//                    textSize = smallText,
//                    iconSize = iconLabelSize
//                )
//                InfoItemWithIcon(
//                    icon = Icons.Default.PhoneAndroid,
//                    label = "Teléfono",
//                    value = cliente.mobile,
//                    textSize = smallText,
//                    iconSize = iconLabelSize
//                )
//            }

//Spacer(modifier = Modifier.height(screen.heightPercentage(0.015f)))

            // Fila con migrado, sector, deuda
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItemWithIcon(
                    icon = Icons.Default.Place,
                    label = "Sector",
                    value = cliente.sectorName,
                    textSize = smallText,
                    iconSize = iconLabelSize
                )
                InfoItemWithIcon(
                    icon = Icons.Default.CalendarMonth,
                    label = "Ciclo",
                    value = cliente.cycle.toString(),
                    textSize = smallText,
                    iconSize = iconLabelSize
                )
                InfoItemWithIcon(
                    icon = Icons.Default.TransferWithinAStation,
                    label = "Migrado",
                    value = if (cliente.migrated) "Sí" else "No",
                    textSize = smallText,
                    iconSize = iconLabelSize
                )

                InfoItemWithIcon(
                    icon = Icons.Default.Money,
                    label = "Deuda",
                    value = "${cliente.debtBs} Bs",
                    textSize = smallText,
                    iconSize = iconLabelSize
                )
            }

            Spacer(modifier = Modifier.height(screen.heightPercentage(0.01f)))

            // Dirección
            Text(
                text = cliente.addressTax,
                fontSize = smallText,
                color = Color.LightGray,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(screen.heightPercentage(0.015f)))

            ClienteFooter(
                cliente = cliente,
                navController = navController
            )
        }
    }
}

