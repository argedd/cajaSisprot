package com.adminpay.caja.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.adminpay.caja.R

fun Context.getDynamicDrawableId(resourceName: String): Int {
    return resources.getIdentifier(
        resourceName,  // Nombre del recurso (ej: "banco_bbva")
        "drawable",     // Tipo de recurso (drawable)
        packageName
    ).takeIf { it != 0 } ?: R.drawable.b0191 // Fallback si no existe
}

@Composable
fun getBankDrawableId(bankCode: String?): Int {
    val context = LocalContext.current
    return context.getDynamicDrawableId("b${bankCode}") // Ej: "bbva" -> "bbbva"
}