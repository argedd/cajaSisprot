package com.adminpay.caja.data.remote.api

import com.adminpay.caja.data.remote.dto.contract.ContractDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ContractApi {
    @GET("api/public/contracts/")
    suspend fun getContractsByClient(
        @Query("client_identification"
        ) identification: String): List<ContractDto>
}
