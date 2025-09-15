package com.adminpay.caja.domain.model.payment.get

data class Payment(
    val id: Int,
    val amount: String,
    val bank: Int,
    val bankName: String,
    val date: String,
    val dollarDate: String?,
    val dollarRate: String,
    val method: Int,
    val methodName: String,
    val paymentCompany: Int,
    val reference: String,
    val sender: Long,
    val status: Int
)