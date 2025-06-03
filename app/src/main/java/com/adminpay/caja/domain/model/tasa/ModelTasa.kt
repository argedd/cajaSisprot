package com.adminpay.caja.domain.model.tasa

data class ModelTasa (
    val id: Long,
    val date: String,
    val amount: String,
    val migrate: Boolean,
    val status: Boolean,
    val currency: Long,
    val currencyName: String
)