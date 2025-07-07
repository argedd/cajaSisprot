package com.adminpay.caja.data.repository.office

import com.adminpay.caja.data.remote.api.OfficeApi
import com.adminpay.caja.data.remote.dto.office.toDomain
import com.adminpay.caja.domain.model.office.OfficeClosingResponse
import com.adminpay.caja.domain.repository.office.OfficeRepository
import javax.inject.Inject

class OfficeRepositoryImpl @Inject constructor(
    private val api: OfficeApi
):OfficeRepository  {
    override suspend fun getOfficeClosingReport(notify: String): OfficeClosingResponse {
        return api.getOfficeClosingReport(notify).toDomain()
    }
}