package com.adminpay.caja.domain.model.payment.register

data class RequestPaymentRegisterModel(
    val payments: List<Int>,
    val invoices: List<Int>,
    val newPayments: List<NewPaymentModel>,
    val cashDollarBill: List<CashDollarBillModel>
)

data class NewPaymentModel(
    val methodId: Int,
    val amount: Double,
    val reference: String
)

data class CashDollarBillModel(
    val denomination: Int,
    val serialCode: String
)