package com.adminpay.caja.data.remote.dto.payment.validate.request

import com.adminpay.caja.domain.model.payment.validate.RequestPaymentValidateModel


fun RequestPaymentValidateModel.toDto(): RequestPaymentValidateDto {
    return RequestPaymentValidateDto(
        sender = sender,
        reference = reference,
        date = date,
        paymentMethod = paymentMethod,
        invoiceId = invoiceId,
        metadata = metadata?.let {
            MetadataDto(confirmationCode = it.confirmationCode)
        }
    )
}