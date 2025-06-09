package com.adminpay.caja.data.repository.contract

import com.adminpay.caja.data.remote.api.ContractApi
import com.adminpay.caja.data.remote.dto.contract.toDomain
import com.adminpay.caja.domain.model.contract.Contract
import com.adminpay.caja.domain.repository.contract.ContractRepository
import javax.inject.Inject

class ContractRepositoryImpl @Inject constructor(
    private val api: ContractApi
) : ContractRepository {
    override suspend fun getContracts(identification: String): List<Contract> {
        val response = api.getContractsByClient(identification)
        return response.results.map { it.toDomain() }
    }
}
