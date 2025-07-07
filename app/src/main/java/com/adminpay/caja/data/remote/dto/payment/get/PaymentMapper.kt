package com.adminpay.caja.data.remote.dto.payment.get

import com.adminpay.caja.domain.model.payment.get.PaymentSummary
import com.adminpay.caja.domain.model.payment.get.PaymentSummaryResultDomain
import com.adminpay.caja.domain.model.payment.get.PaymentSummaryTotal


fun PaymentSummaryDto.toDomain(): PaymentSummary = PaymentSummary(
    quantity = quantity,
    amount = amount.toFloatOrNull() ?: 0f,
    quantityPercentage = quantityPercentage.toFloatOrNull() ?: 0f,
    amountPercentage = amountPercentage.toFloatOrNull() ?: 0f,
    method = method,
    methodName = methodName
)

fun TotalSummaryDto.toDomain(): PaymentSummaryTotal = PaymentSummaryTotal(
    totalQuantity = totalQuantity,
    totalAmount = totalAmount
)

fun PaymentSummaryResult.toDomain(): PaymentSummaryResultDomain = PaymentSummaryResultDomain(
    data = data.map { it.toDomain() },
    total = total.toDomain()
)
