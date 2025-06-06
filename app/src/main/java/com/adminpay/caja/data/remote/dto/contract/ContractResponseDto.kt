package com.adminpay.caja.data.remote.dto.contract

import com.google.gson.annotations.SerializedName

data class ContractDto(
    val id: Int,
    val name: String,

    @SerializedName("last_name")
    val lastName: String,

    val identification: String,
    val mobile: String,
    val status: Int,

    @SerializedName("status_name")
    val statusName: String,

    val email: String,

    @SerializedName("address_tax")
    val addressTax: String,

    val debt: Int,

    @SerializedName("debt_bs")
    val debtBs: Float,

    @SerializedName("bank_associated")
    val bankAssociated: BankAssociatedDto
)

data class BankAssociatedDto(
    @SerializedName("nro_cta")
    val nroCta: String,

    val rlf: String,

    @SerializedName("bank_name")
    val bankName: String,

    @SerializedName("bank_code")
    val bankCode: String,

    val identification: String
)
