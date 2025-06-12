package com.adminpay.caja.domain.useCase

import com.adminpay.caja.domain.model.payment.validate.RequestPaymentValidateModel
import com.adminpay.caja.domain.model.payment.validate.ResponsePaymentValidateModel
import com.adminpay.caja.domain.repository.payment.PaymentRepository
import javax.inject.Inject

class ValidatePaymentUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    suspend operator fun invoke(request: RequestPaymentValidateModel): ResponsePaymentValidateModel {
        return repository.validatePayment(request)
    }
}