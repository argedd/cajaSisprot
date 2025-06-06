package com.adminpay.caja.data.remote.dto.paymentMethods.tarjeta

import com.adminpay.caja.domain.model.paymentMethods.CardPaymentResultModel

data class CardPaymentResultDto(
    val source: String,
    val autorizacion: String? = null,
    val terminalId: String? = null,
    val merchantId: String? = null,
    val pan: String? = null,
    val lote: String? = null,
    val recibo: String? = null,
    val message: String,
    val stan: String? = null,
    val code: String,
) {
    fun toDomain(): CardPaymentResultModel = CardPaymentResultModel(
        source = source,
        autorizacion = autorizacion,
        terminalId = terminalId,
        merchantId = merchantId,
        pan = pan,
        lote = lote,
        recibo = recibo,
        message = message,
        stan = stan,
        code = code
    )
}