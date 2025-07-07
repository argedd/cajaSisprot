package com.adminpay.caja.data.remote.dto.office

import com.google.gson.annotations.SerializedName

data class OfficeClosingDto(
    @SerializedName("message")
    val message: String
)