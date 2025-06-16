package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.bancaNacional

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.payment.validate.RequestPaymentValidateModel
import com.adminpay.caja.domain.model.payment.validate.ResponsePaymentValidateModel
import com.adminpay.caja.domain.useCase.ValidatePaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.HttpException
import com.adminpay.caja.utils.parseHttpErrorMessage

data class BancaNacionalUiState(
    val loading: Boolean = false,
    val success: ResponsePaymentValidateModel? = null,
    val error: String? = null
)

@HiltViewModel
class BancaNacionalViewModel @Inject constructor(
    private val validatePaymentUseCase: ValidatePaymentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BancaNacionalUiState())
    val uiState: StateFlow<BancaNacionalUiState> = _uiState

    fun validatePayment(request: RequestPaymentValidateModel) {
        viewModelScope.launch {
            _uiState.value = BancaNacionalUiState(loading = true)
            try {
                val response = validatePaymentUseCase(request)
                _uiState.value = BancaNacionalUiState(success = response)
            } catch (e: Exception) {
                val errorMessage = if (e is HttpException) {
                    parseHttpErrorMessage(e)
                } else {
                    e.message ?: "Error desconocido"
                }
                _uiState.value = BancaNacionalUiState(error = errorMessage)
            }
        }
    }
}
