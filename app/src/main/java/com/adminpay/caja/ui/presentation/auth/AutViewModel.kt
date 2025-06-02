package com.adminpay.caja.ui.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.data.providers.TokenProvider
import com.adminpay.caja.domain.model.auth.User
import com.adminpay.caja.domain.useCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenProvider: TokenProvider
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = loginUseCase(email, password)
                _authState.value = AuthState.Success(result)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    fun logout() {
        tokenProvider.clearToken()
        _authState.value = AuthState.Idle
    }
}




sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: Result<User>) : AuthState()
    data class Error(val message: String) : AuthState()
}
