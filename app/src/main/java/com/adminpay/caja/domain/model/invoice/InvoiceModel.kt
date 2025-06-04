package com.adminpay.caja.domain.model.invoice

data class FacturaModel(
    val id: Int,
    val date_emission: String,
    val date_expiration: String,
    val amount_bs: AmountBs,
    val debt_bs: Double
)

data class AmountBs(
    val amount: Double,
    val sub_total: Double,
    val iva_amount: Double
)
