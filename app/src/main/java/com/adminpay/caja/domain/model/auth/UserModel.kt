package com.adminpay.caja.domain.model.auth

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val role: String,
    val phone: String,
    val referralCodeId: String,
    val office: Int,
    val officeName: String,
    val lastName: String,
    val isSuperuser: Boolean
)