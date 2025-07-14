package com.adminpay.caja.data.remote.api

import com.adminpay.caja.data.remote.dto.office.OfficeClosingDto
import com.adminpay.caja.data.remote.dto.office.RequestOfficeCloseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OfficeApi {
    @GET("api/gsoft/payments/office_closing/report/")
    suspend fun getOfficeClosingReport(
        @Query("notify") notify: String,
    ): OfficeClosingDto

    @POST("/api/base/proccess_file/")
    suspend fun sendOfficeClose(
        @Body request: RequestOfficeCloseDto
    )
}