package com.adminpay.caja.domain.model.invoice

data class FacturaModel(
    val id: Int,
    val date_emission: String,
    val date_expiration: String,
    val amount_bs: AmountBs,
    val debt_bs: Double
)

data class AmountBs(
    val amount: Double,
    val sub_total: Double,
    val iva_amount: Double
)

data class InvoiceModel(
    val id: Int,
    val dollarDate: String,
    val dollarRate: String,
    val dateEmission: String,
    val dateExpiration: String,
    val datePayment: String,
    val subTotal: String,
    val ivaAmount: String,
    val amount: String,
    val charged: String,
    val chargedBs: String,
    val amountBs: String,
    val month: Int,
    val year: Int,
    val debt: String,
    val debtBs: String,
    val clientName: String,
    val contract: Int,
    val status: Int,
    val statusName: String,
    val paymentAvailable: String,
    val invoiceItems: List<InvoiceItemModel>
)

data class InvoiceItemModel(
    val id: Int,
    val details: String,
    val amount: String,
    val amountBs: String,
    val sum: Int,
    val invoice: Int,
    val service: Int,
    val serviceName: String
)

