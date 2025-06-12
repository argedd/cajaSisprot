package com.adminpay.caja.data.repository.payment

import com.adminpay.caja.data.remote.api.PaymentApi
import com.adminpay.caja.data.remote.dto.payment.validate.request.toDto
import com.adminpay.caja.data.remote.dto.payment.validate.response.toDomain
import com.adminpay.caja.domain.model.payment.validate.RequestPaymentValidateModel
import com.adminpay.caja.domain.model.payment.validate.ResponsePaymentValidateModel
import com.adminpay.caja.domain.repository.payment.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: PaymentApi
): PaymentRepository {
    override suspend fun validatePayment(request: RequestPaymentValidateModel): ResponsePaymentValidateModel {
        val response = api.validatePayment(request.toDto())
        return response.toDomain()
    }
}