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

}

@HiltViewModel
class BancaNacionalViewModel @Inject constructor(
    private val validatePaymentUseCase: ValidatePaymentUseCase,
    private val loadingController: LoadingController,

    ) : ViewModel() {

    private val _uiState: MutableStateFlow<BancaNacionalUiState> =
        MutableStateFlow(BancaNacionalUiState.Idle)
    val uiState: StateFlow<BancaNacionalUiState> = _uiState

    fun validatePayment(
        request: RequestPaymentValidateModel, sharedViewModel: CheckoutSharedViewModel,
    ) {
        Log.i("BancaNacionalViewModel", "Validando pago: $request")
        val exists = sharedViewModel.hasPaymentMethodWith(
            request.reference.toString(),
            if (request.paymentMethod == "pagomovil") 1 else 4
        )
        if (exists) {
            _uiState.value =
                BancaNacionalUiState.Error("Ya existe un método de pago con esta referencia")
        } else {
            loadingController.show()

            viewModelScope.launch {
                _uiState.value = BancaNacionalUiState.Idle
                try {
                    val response = validatePaymentUseCase(request)
                    _uiState.value = BancaNacionalUiState.Success(response)

                    sharedViewModel.addPaymentMethod(
                        ModelMethod(
                            id = 0,
                            idMethod = response.methodId,
                            type = 2,
                            methodName = response.methodName,
                            amount = response.amountData.amountUsd,
                            amountBs = response.amountData.amountBs,
                            idPayment = response.id,
                            reference = response.reference
                        )
                    )
                } catch (e: Exception) {
                    val errorMessage = if (e is HttpException) {
                        parseHttpErrorMessage(e)
                    } else {
                        e.message ?: "Error desconocido"
                    }
                    _uiState.value = BancaNacionalUiState.Error(errorMessage)
                } finally {
                    loadingController.hide()

                }
            }
        }
    }

    fun resetState() {
        _uiState.value = BancaNacionalUiState.Idle
    }
}
