package com.adminpay.caja.domain.model.contract

data class Contract(
    val id: Int,
    val clientId: Int,
    val name: String,
    val lastName: String,
    val identification: String,
    val installationOrder: String,
    val mobile: String,
    val email: String,
    val status: Int,
    val statusName: String,
    val addressTax: String,
    val debtBs: Float,
    val bankAssociated: BankAssociated,
    val sectorName: String,
    val parishName: String,
    val retainingClient: Boolean
)

data class BankAssociated(
    val nroCta: String,
    val rlf: String,
    val bankName: String,
    val bankCode: String,
    val identification: String
)
