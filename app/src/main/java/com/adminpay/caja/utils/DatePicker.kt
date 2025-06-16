package com.adminpay.caja.utils

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import java.util.*

@Composable
fun rememberDatePicker(context: Context, onDateSelected: (String) -> Unit): DatePickerDialog {
    val calendar = Calendar.getInstance()

    return DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formatted = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
            onDateSelected(formatted)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}
