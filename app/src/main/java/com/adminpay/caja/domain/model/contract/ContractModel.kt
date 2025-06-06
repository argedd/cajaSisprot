package com.adminpay.caja.domain.model.contract

data class Contract(
    val id: Int,
    val name: String,
    val lastName: String,
    val identification: String,
    val mobile: String,
    val statusName: String,
    val email: String,
    val addressTax: String,
    val debtBs: Float,
    val bankAssociated: BankAssociated
)

data class BankAssociated(
    val nroCta: String,
    val rlf: String,
    val bankName: String,
    val bankCode: String,
    val identification: String
)