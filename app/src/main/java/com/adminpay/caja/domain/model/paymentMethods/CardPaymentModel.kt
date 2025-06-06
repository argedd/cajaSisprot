package com.adminpay.caja.domain.model.paymentMethods

data class CardPaymentResultModel(
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
)

