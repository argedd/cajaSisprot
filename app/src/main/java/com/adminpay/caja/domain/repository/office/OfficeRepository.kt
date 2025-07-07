package com.adminpay.caja.domain.repository.office

import com.adminpay.caja.domain.model.office.OfficeClosingResponse

interface OfficeRepository {
    suspend fun getOfficeClosingReport(notify: String): OfficeClosingResponse

}