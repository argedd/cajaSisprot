package com.adminpay.caja.data.remote.dto.payment.register

import com.adminpay.caja.domain.model.payment.register.CashDollarBillModel
import com.adminpay.caja.domain.model.payment.register.NewPaymentModel
import com.adminpay.caja.domain.model.payment.register.RequestPaymentRegisterModel

fun RequestPaymentRegisterModel.toDto(): RequestPaymentRegisterDto {
    return RequestPaymentRegisterDto(
        payments = payments,
        invoices = invoices,
        newPayments = newPayments.map { it.toDto() },
        cashDollarBill = cashDollarBill.map { it.toDto() }
    )
}

fun NewPaymentModel.toDto(): NewPaymentDto {
    return NewPaymentDto(
        methodId = methodId,
        amount = amount,
        reference = reference
    )
}

fun CashDollarBillModel.toDto(): CashDollarBillDto {
    return CashDollarBillDto(
        denomination = denomination,
        serialCode = serialCode
    )
}