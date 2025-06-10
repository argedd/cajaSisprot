package com.adminpay.caja.data.remote.api

import com.adminpay.caja.data.remote.dto.invoice.InvoiceResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface InvoiceApi {

    @GET("api/gsoft/invoices/")
    suspend fun getInvoicesByContractAndStatus(
        @Query("contract") contract: String,
        @Query("status") status: String
    ): InvoiceResponseDto
}
