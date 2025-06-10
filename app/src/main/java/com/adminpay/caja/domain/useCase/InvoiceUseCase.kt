package com.adminpay.caja.domain.useCase

import com.adminpay.caja.domain.model.invoice.InvoiceModel
import com.adminpay.caja.domain.repository.invoice.InvoiceRepository
import javax.inject.Inject

class GetInvoicesUseCase @Inject constructor(
    private val repository: InvoiceRepository
) {
    suspend operator fun invoke(contract: String, status: String): List<InvoiceModel> {
        return repository.getInvoices(contract, status)
    }
}