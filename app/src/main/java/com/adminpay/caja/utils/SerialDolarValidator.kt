package com.adminpay.caja.utils

fun validateDollarSerial(serial: String): ValidationResult {
    val cleanedSerial = serial.trim().uppercase()

    if (cleanedSerial.isEmpty()) {
        return ValidationResult(false, "")
    }

    // Patrones válidos
    val modernPattern1 = "^[A-Z]{2}\\d{8}\$".toRegex()  // 2 letras + 8 dígitos
    val modernPattern2 = "^[A-Z]{2}\\d{8}[A-Z]\$".toRegex() // 2 letras + 8 dígitos + 1 letra
    val oldPattern = "^[A-Z]\\d{7,8}[A-Z]\$".toRegex() // 1 letra + 7/8 dígitos + 1 letra

    return when {
        cleanedSerial.matches(modernPattern1) ||
                cleanedSerial.matches(modernPattern2) ||
                cleanedSerial.matches(oldPattern) ->
            ValidationResult(true, "")

        else ->
            ValidationResult(
                false,
                "Formato inválido. Los seriales pueden ser:\n" +
                        "• Moderno: AA12345678 o AA12345678B (2 letras, 8 números, letra opcional)\n" +
                        "• Antiguo: A1234567B o A12345678B (1 letra, 7-8 números, 1 letra)"
            )
    }
}

data class ValidationResult(val isValid: Boolean, val message: String)