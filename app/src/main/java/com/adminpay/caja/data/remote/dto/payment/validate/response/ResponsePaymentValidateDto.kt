package com.adminpay.caja.data.remote.dto.payment.validate.response

import com.google.gson.annotations.SerializedName

data class ResponsePaymentValidateDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("reference")
    val reference: String,

    @SerializedName("sender")
    val sender: String,

    @SerializedName("method_id")
    val methodId: Int,

    @SerializedName("method_name")
    val methodName: String,

    @SerializedName("amount")
    val amount: String,

    @SerializedName("amount_data")
    val amountData: AmountDataDto
)

data class AmountDataDto(
    @SerializedName("amount_usd")
    val amountUsd: Double,

    @SerializedName("amount_bs")
    val amountBs: Double,

    @SerializedName("rate")
    val rate: Double
)