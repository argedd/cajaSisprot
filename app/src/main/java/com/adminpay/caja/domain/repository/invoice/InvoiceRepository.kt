package com.adminpay.caja.domain.repository.invoice

import com.adminpay.caja.domain.model.invoice.InvoiceModel


interface InvoiceRepository {
        suspend fun getInvoices(contract: String, status: String): List<InvoiceModel>
}