package com.adminpay.caja.data.remote.api

import com.adminpay.caja.data.remote.dto.contract.ContractDto
import com.adminpay.caja.data.remote.dto.contract.ContractsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ContractApi {
    @GET("api/gsoft/contract/")
    suspend fun getContractsByClient(
        @Query("client_identification"
        ) identification: String): ContractsResponseDto
}
