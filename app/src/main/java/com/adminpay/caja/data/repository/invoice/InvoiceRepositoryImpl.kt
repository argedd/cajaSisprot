package com.adminpay.caja.data.repository.invoice

import com.adminpay.caja.data.remote.api.InvoiceApi
import com.adminpay.caja.data.remote.dto.invoice.toDomain
import com.adminpay.caja.domain.model.invoice.InvoiceModel
import com.adminpay.caja.domain.repository.invoice.InvoiceRepository
import javax.inject.Inject

class InvoiceRepositoryImpl @Inject constructor(
    private  val api: InvoiceApi
): InvoiceRepository {
    override suspend fun getInvoices(contract: String, status: String): List<InvoiceModel> {
        val response = api.getInvoicesByContractAndStatus(contract, status)
        return response.results.map { it.toDomain() }
    }
}