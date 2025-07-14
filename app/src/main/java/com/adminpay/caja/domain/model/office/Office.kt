package com.adminpay.caja.domain.model.office

data class OfficeClosingResponse(
    val message: String
)

data class RequestOfficeCloseModel(
    val fileType: String,
    val description: String,
    val fileLabel: String,
    val filters: Filters
)

data class Filters(
    val resulType: String,
    val since: String,
    val until: String
)
