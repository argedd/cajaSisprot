package com.adminpay.caja.domain.repository.contract

import com.adminpay.caja.domain.model.contract.Contract

interface ContractRepository {
    suspend fun getContracts(identification: String): List<Contract>
}
