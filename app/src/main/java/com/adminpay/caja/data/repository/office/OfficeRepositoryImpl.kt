package com.adminpay.caja.data.repository.office

import com.adminpay.caja.data.remote.api.OfficeApi
import com.adminpay.caja.data.remote.dto.office.OfficeClosingDto
import com.adminpay.caja.data.remote.dto.office.toDomain
import com.adminpay.caja.data.remote.dto.office.toDto
import com.adminpay.caja.domain.model.office.OfficeClosingResponse
import com.adminpay.caja.domain.model.office.RequestOfficeCloseModel
import com.adminpay.caja.domain.repository.office.OfficeRepository
import javax.inject.Inject

class OfficeRepositoryImpl @Inject constructor(
    private val api: OfficeApi
):OfficeRepository  {
    override suspend fun getOfficeClosingReport(notify: String): OfficeClosingResponse {
        return api.getOfficeClosingReport(notify).toDomain()
    }

    override suspend fun requestOfficeClose(request: RequestOfficeCloseModel) {
        return api.sendOfficeClose(request.toDto())

    }


}

