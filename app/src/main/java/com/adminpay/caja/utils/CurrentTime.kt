package com.movilpay.autopago.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

 fun getCurrentDateTimeInVenezuela(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("America/Caracas")
    return dateFormat.format(Date())
}

@Composable
fun currentTimeVenezuela(): String {
    val venezuelaZone = remember { java.time.ZoneId.of("America/Caracas") }
    val formatter = remember { java.time.format.DateTimeFormatter.ofPattern("hh:mm:ss a") }
    val time = remember { mutableStateOf(formatter.format(java.time.ZonedDateTime.now(venezuelaZone))) }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000L)
            time.value = formatter.format(java.time.ZonedDateTime.now(venezuelaZone))
        }
    }

    return time.value
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

