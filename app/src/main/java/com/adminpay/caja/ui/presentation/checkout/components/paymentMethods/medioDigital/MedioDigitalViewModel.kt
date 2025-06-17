package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.medioDigital

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

sealed class MedioDigitalUiState {
    data object Idle : MedioDigitalUiState()
    data class Success(val success: ResponsePaymentValidateModel? = null) : MedioDigitalUiState()
    data class Error(val message: String) : MedioDigitalUiState()

}

@HiltViewModel
class MedioDigitalViewModel @Inject constructor(
    private val validatePaymentUseCase: ValidatePaymentUseCase,
    private val loadingController: LoadingController,

    ) : ViewModel() {

    private val _uiState: MutableStateFlow<MedioDigitalUiState> =
        MutableStateFlow(MedioDigitalUiState.Idle)
    val uiState: StateFlow<MedioDigitalUiState> = _uiState

    fun validatePayment(
        request: RequestPaymentValidateModel, sharedViewModel: CheckoutSharedViewModel,
    ) {
        Log.i("MedioDigitalViewModel", "Validando pago: $request")
        val exists = sharedViewModel.hasPaymentMethodWith(
            request.reference.toString(),
            3
        )
        if (exists) {
            _uiState.value =
                MedioDigitalUiState.Error("Ya existe un método de pago con esta referencia")
        } else {
            loadingController.show()

            viewModelScope.launch {
                _uiState.value = MedioDigitalUiState.Idle
                try {
                    val response = validatePaymentUseCase(request)
                    _uiState.value = MedioDigitalUiState.Success(response)

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
                } catch (e: Exception) {
                    val errorMessage = if (e is HttpException) {
                        parseHttpErrorMessage(e)
                    } else {
                        e.message ?: "Error desconocido"
                    }
                    _uiState.value = MedioDigitalUiState.Error(errorMessage)
                } finally {
                    loadingController.hide()

                }
            }
        }
    }

    fun resetState() {
        _uiState.value = MedioDigitalUiState.Idle
    }
}
