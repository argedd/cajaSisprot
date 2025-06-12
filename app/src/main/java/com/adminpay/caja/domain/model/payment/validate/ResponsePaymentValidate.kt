package com.adminpay.caja.domain.model.payment.validate

data class ResponsePaymentValidateModel(
    val id: Int,
    val reference: String,
    val sender: String,
    val methodId: Int,
    val methodName: String,
    val amount: String,
    val amountData: AmountDataModel
)

data class AmountDataModel(
    val amountUsd: Double,
    val amountBs: Double,
    val rate: Double
)