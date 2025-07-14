package com.adminpay.caja.domain.useCase

import com.adminpay.caja.domain.model.office.OfficeClosingResponse
import com.adminpay.caja.domain.model.office.RequestOfficeCloseModel
import com.adminpay.caja.domain.repository.office.OfficeRepository
import javax.inject.Inject

class GetOfficeClosingReportUseCase @Inject constructor(
    private val repository: OfficeRepository
) {
    suspend operator fun invoke(notify: String): OfficeClosingResponse {
        return repository.getOfficeClosingReport(notify)
    }
}

class RequestOfficeCloseUseCase @Inject constructor(
    private val repository: OfficeRepository
) {
    suspend operator fun invoke(request: RequestOfficeCloseModel) {
        return repository.requestOfficeClose(request)
    }
}
