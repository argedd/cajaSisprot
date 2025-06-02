package com.adminpay.caja.domain.repository.auth

import com.adminpay.caja.domain.model.auth.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
}