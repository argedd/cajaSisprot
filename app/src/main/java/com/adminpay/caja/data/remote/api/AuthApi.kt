package com.adminpay.caja.data.remote.api

import com.adminpay.caja.data.remote.dto.auth.LoginRequestDto
import com.adminpay.caja.data.remote.dto.auth.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login/")
    suspend fun login(@Body body: LoginRequestDto): LoginResponseDto
}