package com.adminpay.caja.data.remote.dto.tasa

import com.google.gson.annotations.SerializedName

class ResponseTasaDto(
    val id: Long,
    val amount: String,
    val currency: Int,
    @SerializedName("currency_name") val currencyName: String,
    val date: String,
    val migrate: Boolean,
    val status: Boolean
)
