package com.adminpay.caja.domain.repository.office

import com.adminpay.caja.data.remote.dto.office.OfficeClosingDto
import com.adminpay.caja.domain.model.office.OfficeClosingResponse
import com.adminpay.caja.domain.model.office.RequestOfficeCloseModel

interface OfficeRepository {
    suspend fun getOfficeClosingReport(notify: String): OfficeClosingResponse
    suspend fun requestOfficeClose(request: RequestOfficeCloseModel)



}