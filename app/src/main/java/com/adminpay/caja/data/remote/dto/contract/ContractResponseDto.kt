package com.adminpay.caja.data.remote.dto.contract

import com.google.gson.annotations.SerializedName



data class ContractsResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,

    @SerializedName("results")
    val results: List<ContractDto>
)


data class ContractDto(
    val id: Int,

    @SerializedName("client_id")
    val clientId: Int,

    val name: String,

    @SerializedName("last_name")
    val lastName: String,

    val identification: String,

    @SerializedName("installation_order")
    val installationOrder: String,

    val mobile: String,
    val status: Int,

    @SerializedName("status_name")
    val statusName: String,

    val email: String,

    @SerializedName("address_tax")
    val addressTax: String,

    val debt: Double,

    @SerializedName("debt_bs")
    val debtBs: Float,

    @SerializedName("bank_associated")
    val bankAssociated: BankAssociatedDto,

    @SerializedName("sector_name")
    val sectorName: String,

    @SerializedName("parish_name")
    val parishName: String,

    @SerializedName("retaining_client")
    val retainingClient: Boolean,

    val cycle: Int,
    val migrated: Boolean
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
