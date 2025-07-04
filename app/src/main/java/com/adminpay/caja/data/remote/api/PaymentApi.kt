package com.adminpay.caja.data.remote.api

import com.adminpay.caja.data.remote.dto.payment.register.RequestPaymentRegisterDto
import com.adminpay.caja.data.remote.dto.payment.validate.request.RequestPaymentValidateDto
import com.adminpay.caja.data.remote.dto.payment.validate.response.ResponsePaymentValidateDto
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentApi {
    @POST("api/gsoft/payments/validate/")
    suspend fun validatePayment(
        @Body request: RequestPaymentValidateDto
    ): ResponsePaymentValidateDto

    @POST("api/gsoft/payments/register_payment/")
    suspend fun registerPayment(
        @Body request: RequestPaymentRegisterDto
    )
}