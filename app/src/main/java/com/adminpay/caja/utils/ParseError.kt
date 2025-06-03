package com.movilpay.autopago.utils

import android.util.Log
import org.json.JSONObject
import retrofit2.HttpException

fun parseHttpErrorMessage(exception: HttpException): String {
    return try {
        val errorBody = exception.response()?.errorBody()
        val errorBodyString = errorBody?.string()
        Log.i("HttpError", "Cuerpo de error: $errorBodyString")

        if (!errorBodyString.isNullOrBlank()) {
            val json = JSONObject(errorBodyString)
            val messages = mutableListOf<String>()

            // Recorremos todas las claves del JSON
            val keys = json.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                // Si el valor es un arreglo (ej. lista de errores)
                when (val value = json.get(key)) {
                    is String -> messages.add("$value")
                    is org.json.JSONArray -> {
                        for (i in 0 until value.length()) {
                            messages.add(value.getString(i))
                        }
                    }
                    else -> messages.add("$value")
                }
            }

            if (messages.isNotEmpty()) {
                messages.joinToString("\n")
            } else {
                "Error desconocido del servidor"
            }
        } else {
            "Respuesta vacía del servidor"
        }
    } catch (e: Exception) {
        "Error al procesar el mensaje del servidor"
    }
}



