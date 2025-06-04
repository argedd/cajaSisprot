package com.adminpay.caja.data.remote.api

import com.adminpay.caja.data.remote.dto.tasa.ResponseTasaDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TasaApi {
    @GET("api/base/currency_rate/")
    suspend fun getTasa(
        @Query("remove_pagination") removed: Boolean
    ): List<ResponseTasaDto>
}