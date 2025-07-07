package com.adminpay.caja.data.repository.payment

import com.adminpay.caja.data.remote.api.PaymentApi
import com.adminpay.caja.data.remote.dto.payment.register.toDto
import com.adminpay.caja.data.remote.dto.payment.validate.request.toDto
import com.adminpay.caja.data.remote.dto.payment.validate.response.toDomain
import com.adminpay.caja.domain.model.payment.register.RequestPaymentRegisterModel
import com.adminpay.caja.domain.model.payment.validate.RequestPaymentValidateModel
import com.adminpay.caja.domain.model.payment.validate.ResponsePaymentValidateModel
import com.adminpay.caja.domain.repository.payment.PaymentRepository
import javax.inject.Inject
import com.adminpay.caja.data.remote.dto.payment.get.toDomain
import com.adminpay.caja.domain.model.payment.get.PaymentSummaryResultDomain
import com.adminpay.caja.domain.model.payment.get.PaymentSummaryTotal


class PaymentRepositoryImpl @Inject constructor(
    private val api: PaymentApi
): PaymentRepository {
    override suspend fun validatePayment(request: RequestPaymentValidateModel): ResponsePaymentValidateModel {
        val response = api.validatePayment(request.toDto())
        return response.toDomain()
    }
    override suspend fun registerPayment(request: RequestPaymentRegisterModel) {
         api.registerPayment(request.toDto())
    }

    override suspend fun getPaymentSummary(group: String, date: String): PaymentSummaryResultDomain {
        val response = api.getPaymentsOfDay(group, date)
        val summaryDto = response.results?.firstOrNull()

        return summaryDto?.toDomain() ?: PaymentSummaryResultDomain(
            data = emptyList(),
            total = PaymentSummaryTotal(
                totalQuantity = 0,
                totalAmount = 0f
            )
        )

    }


}