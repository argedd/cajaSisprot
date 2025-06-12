package com.adminpay.caja.data.remote.dto.payment.validate.response

import com.adminpay.caja.domain.model.payment.validate.AmountDataModel
import com.adminpay.caja.domain.model.payment.validate.ResponsePaymentValidateModel

fun ResponsePaymentValidateDto.toDomain(): ResponsePaymentValidateModel {
    return ResponsePaymentValidateModel(
        id = id,
        reference = reference,
        sender = sender,
        methodId = methodId,
        methodName = methodName,
        amount = amount,
        amountData = amountData.toDomain()
    )
}

fun AmountDataDto.toDomain(): AmountDataModel {
    return AmountDataModel(
        amountUsd = amountUsd,
        amountBs = amountBs,
        rate = rate
    )
}