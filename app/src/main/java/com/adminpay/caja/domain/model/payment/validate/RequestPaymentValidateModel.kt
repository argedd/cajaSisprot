package com.adminpay.caja.domain.model.payment.validate

data class RequestPaymentValidateModel(
    val sender: String? = null,
    val reference: String? = null,
    val date: String,
    val paymentMethod: String,
    val invoiceId: Int,
    val metadata: PaymentMetadataModel? = null
)

data class PaymentMetadataModel(
    val confirmationCode: String? = null
)
