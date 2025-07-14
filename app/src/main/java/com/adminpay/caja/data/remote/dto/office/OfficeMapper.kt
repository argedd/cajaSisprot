package com.adminpay.caja.data.remote.dto.office

import com.adminpay.caja.domain.model.office.Filters
import com.adminpay.caja.domain.model.office.OfficeClosingResponse
import com.adminpay.caja.domain.model.office.RequestOfficeCloseModel

fun OfficeClosingDto.toDomain(): OfficeClosingResponse {
    return OfficeClosingResponse(message = message)
}

fun RequestOfficeCloseModel.toDto(): RequestOfficeCloseDto {
    return RequestOfficeCloseDto(
        fileType = fileType,
        description = description,
        fileLabel = fileLabel,
        filters = filters.toDto()
    )
}

fun Filters.toDto(): FiltersDto {
    return FiltersDto(
        resulType = resulType,
        since = since,
        until = until
    )
}
