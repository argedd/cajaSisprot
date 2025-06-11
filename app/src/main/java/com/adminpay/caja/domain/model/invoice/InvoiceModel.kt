package com.adminpay.caja.domain.model.invoice

data class InvoiceModel(
    val id: Int,
    val dollarDate: String?,                      // null en el JSON
    val dollarRate: String?,                      // null en el JSON
    val dateEmission: String,
    val dateExpiration: String,
    val datePayment: String?,                     // null en el JSON
    val subTotal: String,
    val ivaAmount: String,
    val amount: String,
    val charged: String,
    val chargedBs: Double,                        // En el JSON es número (Double)
    val amountBs: AmountBsModel,                  // Es un objeto anidado
    val month: Int,
    val year: Int,
    val debt: Double,                             // En el JSON es número (Double)
    val debtBs: Double,
    val clientName: String,
    val contract: Int,
    val status: Int,
    val statusName: String,
    val paymentAvailable: Boolean,                // En el JSON es booleano
    val invoiceItems: List<InvoiceItemModel>      // Renombrado correctamente
)

data class AmountBsModel(
    val amount: Double,
    val subTotal: Double,
    val ivaAmount: Double
)

data class InvoiceItemModel(
    val id: Int,
    val details: String,
    val amount: String,
    val amountBs: Double,                         // En el JSON es número
    val sum: Int,
    val invoice: Int,
    val service: Int,
    val serviceName: String
)
