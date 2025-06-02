package com.adminpay.caja.domain.useCase

import com.adminpay.caja.domain.model.auth.User
import com.adminpay.caja.domain.repository.auth.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return repository.login(email, password)
    }
}