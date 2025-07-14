package com.adminpay.caja.data.remote.dto.office

import com.google.gson.annotations.SerializedName

data class OfficeClosingDto(
    @SerializedName("message")
    val message: String
)

data class RequestOfficeCloseDto(
    @SerializedName("file_type")
    val fileType: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("file_label")
    val fileLabel: String,

    @SerializedName("filters")
    val filters: FiltersDto
)

data class FiltersDto(
    @SerializedName("result_type")
    val resulType: String,

    @SerializedName("since")
    val since: String,

    @SerializedName("until")
    val until: String
)
