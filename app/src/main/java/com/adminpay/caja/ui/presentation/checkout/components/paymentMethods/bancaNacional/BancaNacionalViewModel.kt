package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.bancaNacional

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.payment.validate.RequestPaymentValidateModel
import com.adminpay.caja.domain.model.payment.validate.ResponsePaymentValidateModel
import com.adminpay.caja.domain.model.paymentMethods.ModelMethod
import com.adminpay.caja.domain.useCase.ValidatePaymentUseCase
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.HttpException
import com.adminpay.caja.utils.parseHttpErrorMessage
import com.movilpay.autopago.utils.LoadingController

sealed class BancaNacionalUiState {
    data object Idle : BancaNacionalUiState()
    data class Success(val success: ResponsePaymentValidateModel? = null) : BancaNacionalUiState()
    data class Error(val message: String) : BancaNacionalUiState()
    data object Loading : BancaNacionalUiState()


}

@HiltViewModel
class BancaNacionalViewModel @Inject constructor(
    private val validatePaymentUseCase: ValidatePaymentUseCase,

    ) : ViewModel() {

    private val _uiState: MutableStateFlow<BancaNacionalUiState> =
        MutableStateFlow(BancaNacionalUiState.Idle)
    val uiState: StateFlow<BancaNacionalUiState> = _uiState

    fun validatePayment(
        request: RequestPaymentValidateModel,
        sharedViewModel: CheckoutSharedViewModel,
        onDismiss: () -> Unit,
    ) {
        Log.i("BancaNacionalViewModel", "Validando pago: $request")
        val existsTrf = sharedViewModel.hasPaymentMethodWith(
            request.reference.toString(),
            4
        )
        val existsPm = sharedViewModel.hasPaymentMethodWith(
            request.reference.toString(),
            1
        )
        if (existsTrf || existsPm) {
            _uiState.value =
                BancaNacionalUiState.Error("Ya existe un método de pago con esta referencia")
        } else {
            _uiState.value = BancaNacionalUiState.Loading

            viewModelScope.launch {
                try {
                    val response = validatePaymentUseCase(request)
                    _uiState.value = BancaNacionalUiState.Success(response)

                    sharedViewModel.addPaymentMethod(
                        ModelMethod(
                            id = 0,
                            idMethod = response.methodId,
                            type = 1,
                            methodName = response.methodName,
                            amount = response.amountData.amountUsd,
                            amountBs = response.amountData.amountBs,
                            idPayment = response.id,
                            reference = response.reference
                        )
                    )
                    onDismiss()
                } catch (e: Exception) {
                    val errorMessage = if (e is HttpException) {
                        parseHttpErrorMessage(e)
                    } else {
                        e.message ?: "Error desconocido"
                    }
                    _uiState.value = BancaNacionalUiState.Error(errorMessage)
                } finally {

                }
            }
        }
    }

    fun resetState() {
        _uiState.value = BancaNacionalUiState.Idle
    }
}
