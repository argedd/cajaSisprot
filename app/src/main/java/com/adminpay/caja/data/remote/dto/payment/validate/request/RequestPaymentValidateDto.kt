package com.adminpay.caja.data.remote.dto.payment.validate.request


import com.google.gson.annotations.SerializedName

data class RequestPaymentValidateDto(
    @SerializedName("sender")
    val sender: String? = null,

    @SerializedName("reference")
    val reference: String? = null,

    @SerializedName("date")
    val date: String,

    @SerializedName("payment_method")
    val paymentMethod: String,

    @SerializedName("invoice_id")
    val invoiceId: Int,

    @SerializedName("metadata")
    val metadata: MetadataDto? = null
)

data class MetadataDto(
    @SerializedName("confirmation_code")
    val confirmationCode: String? = null
)

