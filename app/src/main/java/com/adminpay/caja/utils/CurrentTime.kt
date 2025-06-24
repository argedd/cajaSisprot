package com.adminpay.caja.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TimeVenezuelaWidgetCard(
    modifier: Modifier = Modifier
) {
    val venezuelaZone = remember { ZoneId.of("America/Caracas") }
    val formatter = remember { DateTimeFormatter.ofPattern("hh:mm:ss a") }
    val time = remember { mutableStateOf(formatter.format(ZonedDateTime.now(venezuelaZone))) }
    val screen = rememberScreenDimensions()

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            time.value = formatter.format(ZonedDateTime.now(venezuelaZone))
        }
    }

    val horizontalPadding = screen.widthPercentage(0.02f)
    val iconSize = screen.widthPercentage(0.03f)
    val titleFontSize = (screen.width.value * 0.015).sp
    val timeFontSize = (screen.width.value * 0.02).sp

    Card(
        modifier = modifier
            .padding(horizontal = horizontalPadding, vertical = screen.heightPercentage(0.01f))
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = horizontalPadding, vertical = screen.heightPercentage(0.015f))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = "Icono de hora",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(iconSize)
            )

            Spacer(modifier = Modifier.width(screen.widthPercentage(0.04f)))

            Column {
                Text(
                    text = "Hora Venezuela",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = titleFontSize
                    )
                )
                Spacer(modifier = Modifier.height(screen.heightPercentage(0.005f)))
                Text(
                    text = time.value,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = timeFontSize
                    )
                )
            }
        }
    }
}


fun String.formatFecha(fromFormat: String = "yyyy-MM-dd", toFormat: String = "dd-MM-yyyy"): String {
    return try {
        val parser = SimpleDateFormat(fromFormat, Locale.getDefault())
        val formatter = SimpleDateFormat(toFormat, Locale.getDefault())
        val date = parser.parse(this)
        formatter.format(date ?: return this)
    } catch (e: Exception) {
        this
    }
}
