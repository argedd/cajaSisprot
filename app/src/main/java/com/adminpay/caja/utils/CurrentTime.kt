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

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            time.value = formatter.format(ZonedDateTime.now(venezuelaZone))
        }
    }

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = "Icono de hora",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "Hora Venezuela",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = time.value,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 32.sp
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
