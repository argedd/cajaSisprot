package com.adminpay.caja.data.remote.api

import com.adminpay.caja.data.remote.dto.office.OfficeClosingDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OfficeApi {
    @GET("api/gsoft/payments/office_closing/report/")
    suspend fun getOfficeClosingReport(
        @Query("notify") notify: String,
    ): OfficeClosingDto
}