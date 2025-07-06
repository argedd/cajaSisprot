package com.adminpay.caja.data.remote.dto.payment.get

import com.google.gson.annotations.SerializedName



data class PaymentListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<List<PaymentDto>>
)

data class PaymentDto(
    val id: Int,
    val amount: String,
    val bank: Int,
    @SerializedName("bank_name") val bankName: String,
    val date: String,
    @SerializedName("dollar_date") val dollarDate: String?,
    @SerializedName("dollar_rate") val dollarRate: String,
    val method: Int,
    @SerializedName("method_name") val methodName: String,
    @SerializedName("payment_company") val paymentCompany: Int,
    val reference: String,
    val sender: Long,
    val status: Int
)