package com.adminpay.caja.domain.model.payment.get


data class PaymentSummary(
    val quantity: Int,
    val amount: Float,
    val quantityPercentage: Float,
    val amountPercentage: Float,
    val method: Int,
    val methodName: String
)

data class PaymentSummaryTotal(
    val totalQuantity: Int,
    val totalAmount: Float
)

data class PaymentSummaryResultDomain(
    val data: List<PaymentSummary>,
    val total: PaymentSummaryTotal
)
