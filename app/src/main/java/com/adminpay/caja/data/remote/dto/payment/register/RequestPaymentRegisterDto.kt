package com.adminpay.caja.data.remote.dto.payment.register

import com.google.gson.annotations.SerializedName

data class RequestPaymentRegisterDto(
    @SerializedName("payments")
    val payments: List<Int>,

    @SerializedName("invoices")
    val invoices: List<Int>,

    @SerializedName("new_payments")
    val newPayments: List<NewPaymentDto>,

    @SerializedName("cash_dollar_bill")
    val cashDollarBill: List<CashDollarBillDto>
)

data class NewPaymentDto(
    @SerializedName("method_id")
    val methodId: Int,

    @SerializedName("amount")
    val amount: Double,

    @SerializedName("reference")
    val reference: String
)

data class CashDollarBillDto(
    @SerializedName("denomination")
    val denomination: Int,

    @SerializedName("serial_code")
    val serialCode: String
)