package com.adminpay.caja.domain.repository.payment

import com.adminpay.caja.domain.model.payment.register.RequestPaymentRegisterModel
import com.adminpay.caja.domain.model.payment.validate.RequestPaymentValidateModel
import com.adminpay.caja.domain.model.payment.validate.ResponsePaymentValidateModel

interface PaymentRepository {
    suspend fun validatePayment(request: RequestPaymentValidateModel): ResponsePaymentValidateModel
    suspend fun registerPayment(request: RequestPaymentRegisterModel)

}