package com.adminpay.caja.data.repository.auth

import android.util.Log
import com.adminpay.caja.data.providers.TokenProvider
import com.adminpay.caja.data.remote.api.AuthApi
import com.adminpay.caja.data.remote.dto.auth.LoginRequestDto
import com.adminpay.caja.data.remote.dto.auth.toDomain
import com.adminpay.caja.domain.model.auth.User
import com.adminpay.caja.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenProvider: TokenProvider
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            Log.d("AuthRepo", "Enviando login: email=$email, password=$password")
            val response = api.login(LoginRequestDto(email, password))
            tokenProvider.saveToken(response.token)
            Log.d("AuthRepo", "Recibido token: ${response.token}")
            Log.d("AuthRepo", "Usuario: ${response.user}")
            Result.success(response.user.toDomain())
        } catch (e: Exception) {
            Log.d("Error", "Recibido token: ${e}")
            Result.failure(e)

        }
    }

}