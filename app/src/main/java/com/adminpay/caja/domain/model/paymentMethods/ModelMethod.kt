package com.adminpay.caja.domain.model.paymentMethods

data class ModelMethod(
    val id: Int,
    val idMethod: Int,
    val type: Int,
    val methodName: String,
    val amount: Double? = null,
    val amountBs: Double? = null,
    val idPayment: Int? = null,
    val reference: String? = null,
    val cashDollarBill: List<CashDollarBill>? = null
)

data class CashDollarBill(
    val denomination: Int? = null,
    val serialCode: String? = null
)