package com.adminpay.caja.data.remote.dto.payment.get

import com.google.gson.annotations.SerializedName



data class NewPaymentSummaryResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PaymentSummaryResult>?
)

data class PaymentSummaryResult(
    val data: List<PaymentSummaryDto>,
    val total: TotalSummaryDto
)

data class PaymentSummaryDto(
    val quantity: Int,
    val amount: String,
    @SerializedName("quantity_percentage") val quantityPercentage: String,
    @SerializedName("amount_percentage") val amountPercentage: String,
    val method: Int,
    @SerializedName("method_name") val methodName: String
)

data class TotalSummaryDto(
    @SerializedName("total_quantity") val totalQuantity: Int,
    @SerializedName("total_amount") val totalAmount: Float
)