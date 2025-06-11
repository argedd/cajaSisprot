package com.adminpay.caja.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    val token: String,
    val user: UserDto,
    val message: String
)

data class UserDto(
    val uid: String,
    val name: String,
    val email: String,
    val role: String,
    val phone: String,
    @SerializedName("referral_code_id") val referralCodeId: String,
    val office: Int,
    @SerializedName("id_gsoft") val idGsoft: Int,
    @SerializedName("office_name") val officeName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("is_superuser") val isSuperuser: Boolean
)