package com.adminpay.caja.data.remote.dto.payment.get

import com.adminpay.caja.domain.model.payment.get.Payment

fun PaymentDto.toDomain(): Payment {
    return Payment(
        id = id,
        amount = amount,
        bank = bank,
        bankName = bankName,
        date = date,
        dollarDate = dollarDate,
        dollarRate = dollarRate,
        method = method,
        methodName = methodName,
        paymentCompany = paymentCompany,
        reference = reference,
        sender = sender,
        status = status
    )
}
