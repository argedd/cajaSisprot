package com.adminpay.caja.data.remote.api

import com.adminpay.caja.data.remote.dto.payment.get.NewPaymentSummaryResponse
import com.adminpay.caja.data.remote.dto.payment.register.RequestPaymentRegisterDto
import com.adminpay.caja.data.remote.dto.payment.validate.request.RequestPaymentValidateDto
import com.adminpay.caja.data.remote.dto.payment.validate.response.ResponsePaymentValidateDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PaymentApi {
    @POST("api/gsoft/payments/validate/")
    suspend fun validatePayment(
        @Body request: RequestPaymentValidateDto
    ): ResponsePaymentValidateDto

    @POST("api/gsoft/payments/register_payment/")
    suspend fun registerPayment(
        @Body request: RequestPaymentRegisterDto
    )

        @GET("api/gsoft/payments/")
        suspend fun getPaymentsOfDay(
            @Query("group_by") group: String,
            @Query("since") date: String,
        ): NewPaymentSummaryResponse

}