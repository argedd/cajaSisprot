package com.adminpay.caja.ui.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.data.providers.TokenProvider
import com.adminpay.caja.domain.model.auth.User
import com.adminpay.caja.domain.useCase.LoginUseCase
import com.adminpay.caja.utils.parseHttpErrorMessage
import com.movilpay.autopago.utils.LoadingController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenProvider: TokenProvider,
    private val loadingController: LoadingController
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        loadingController.show()
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = loginUseCase(email, password)
                if (result.isSuccess) {
                    _authState.value = AuthState.Success(result)
                } else {
                    val errorMessage = result.exceptionOrNull()?.let { exception ->
                        if (exception is HttpException) {
                            parseHttpErrorMessage(exception)
                        } else {
                            exception.message ?: "Error desconocido"
                        }
                    } ?: "Error desconocido"
                    _authState.value = AuthState.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = if (e is HttpException) {
                    parseHttpErrorMessage(e)
                } else {
                    e.message ?: "Error desconocido"
                }
                _authState.value = AuthState.Error(errorMessage)
            } finally {
                loadingController.hide()
            }
        }
    }

    fun logout() {
        loadingController.show()
        tokenProvider.clearToken()
        _authState.value = AuthState.Idle
        loadingController.hide()
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: Result<User>) : AuthState()
    data class Error(val message: String) : AuthState()
}
