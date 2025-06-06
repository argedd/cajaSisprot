package com.adminpay.caja.domain.useCase

import com.adminpay.caja.domain.model.contract.Contract
import com.adminpay.caja.domain.repository.contract.ContractRepository
import javax.inject.Inject

class GetContractsUseCase @Inject constructor(
    private val repository: ContractRepository
) {
    suspend operator fun invoke(identification: String): List<Contract> {
        return repository.getContracts(identification)
    }
}