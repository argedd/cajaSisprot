package com.adminpay.caja.data.remote.dto.invoice

import com.google.gson.annotations.SerializedName

data class InvoiceResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<InvoiceDto>
)

data class InvoiceDto(
    val id: Int,
    @SerializedName("dollar_date") val dollarDate: String,
    @SerializedName("dollar_rate") val dollarRate: String,
    @SerializedName("date_emission") val dateEmission: String,
    @SerializedName("date_expiration") val dateExpiration: String,
    @SerializedName("date_payment") val datePayment: String,
    @SerializedName("sub_total") val subTotal: String,
    @SerializedName("iva_amount") val ivaAmount: String,
    val amount: String,
    val charged: String,
    @SerializedName("charged_bs") val chargedBs: String,
    @SerializedName("amount_bs") val amountBs: String,
    val month: Int,
    val year: Int,
    val debt: String,
    @SerializedName("debt_bs") val debtBs: String,
    @SerializedName("client_name") val clientName: String,
    val contract: Int,
    val status: Int,
    @SerializedName("status_name") val statusName: String,
    @SerializedName("payment_available") val paymentAvailable: String,
    @SerializedName("invoice_items_gsoft") val invoiceItemsGsoft: List<InvoiceItemDto>
)

data class InvoiceItemDto(
    val id: Int,
    val details: String,
    val amount: String,
    @SerializedName("amount_bs") val amountBs: String,
    val sum: Int,
    val invoice: Int,
    val service: Int,
    @SerializedName("service_name") val serviceName: String
)
