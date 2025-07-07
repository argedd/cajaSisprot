package com.adminpay.caja.data.remote.dto.office

import com.adminpay.caja.domain.model.office.OfficeClosingResponse

fun OfficeClosingDto.toDomain(): OfficeClosingResponse {
    return OfficeClosingResponse(message = message)
}