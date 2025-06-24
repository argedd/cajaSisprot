package com.adminpay.caja

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adminpay.caja.utils.rememberScreenDimensions


@Composable
fun CCCardComponent(
    cliente: String = "Juan Pérez",
    contrato: String = "123456",
    estatus: String = "Activo",
    migrado: String = "Sí",
    sector: String = "Comercial",
    deuda: String = "$120.00",
    direccion: String = "Av. Principal #123, Caracas",
    onVerDetalles: () -> Unit
) {
    val screen = rememberScreenDimensions()
    val padding = screen.widthPercentage(0.04f)
    val iconSize = screen.widthPercentage(0.14f) // REDUCIDO
    val smallText = (screen.width.value * 0.032).sp
    val mediumText = (screen.width.value * 0.038).sp
    val titleText = (screen.width.value * 0.045).sp
    val iconLabelSize = screen.widthPercentage(0.045f)

    Card(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        shape = RoundedCornerShape(screen.widthPercentage(0.03f)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Imagen circular reducida
                Box(
                    modifier = Modifier
                        .size(iconSize)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Foto cliente",
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.width(screen.widthPercentage(0.03f)))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = cliente,
                        fontSize = titleText,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = "Contrato Nº $contrato",
                        fontSize = smallText,
                        color = Color.LightGray
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Estatus",
                            tint = Color.Green,
                            modifier = Modifier.size(iconLabelSize)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = estatus,
                            fontSize = smallText,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(screen.heightPercentage(0.015f)))

            // Fila de migrado, sector, deuda
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItemWithIcon(
                    icon = Icons.Default.Info,
                    label = "Migrado",
                    value = migrado,
                    textSize = smallText,
                    iconSize = iconLabelSize
                )
                InfoItemWithIcon(
                    icon = Icons.Default.Place,
                    label = "Sector",
                    value = sector,
                    textSize = smallText,
                    iconSize = iconLabelSize
                )
                InfoItemWithIcon(
                    icon = Icons.Default.AttachMoney,
                    label = "Deuda",
                    value = deuda,
                    textSize = smallText,
                    iconSize = iconLabelSize
                )
            }

            Spacer(modifier = Modifier.height(screen.heightPercentage(0.01f)))

            // Dirección
            Text(
                text = direccion,
                fontSize = smallText,
                color = Color.LightGray,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(screen.heightPercentage(0.015f)))

            // Botón centrado
            Button(
                onClick = onVerDetalles,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0FACD4)),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(screen.widthPercentage(0.02f))
            ) {
                Text("Ver detalles", fontSize = mediumText, color = Color.White)
            }
        }
    }
}

@Composable
fun InfoItemWithIcon(
    icon: ImageVector,
    label: String,
    value: String,
    textSize: TextUnit,
    iconSize: Dp
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = textSize,
                color = Color.LightGray
            )
        }
        Text(
            text = value,
            fontSize = textSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}




