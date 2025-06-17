package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.pos


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.payment.validate.RequestPaymentValidateModel
import com.adminpay.caja.domain.model.payment.validate.ResponsePaymentValidateModel
import com.adminpay.caja.domain.model.paymentMethods.ModelMethod
import com.adminpay.caja.domain.useCase.ValidatePaymentUseCase
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.medioDigital.MedioDigitalUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.HttpException
import com.adminpay.caja.utils.parseHttpErrorMessage
import com.movilpay.autopago.utils.LoadingController

sealed class PosUiState {
    data object Idle : PosUiState()
    data class Success(val success: ResponsePaymentValidateModel? = null) : PosUiState()
    data class Error(val message: String) : PosUiState()

}

@HiltViewModel
class PosViewModel @Inject constructor(
    private val loadingController: LoadingController,

    ) : ViewModel() {

    private val _uiState: MutableStateFlow<PosUiState> =
        MutableStateFlow(PosUiState.Idle)
    val uiState: StateFlow<PosUiState> = _uiState


    fun chargedManualPayment(amount: Double, reference:String, sharedViewModel: CheckoutSharedViewModel){
        val exists = findPaymentMethod(reference,sharedViewModel)
        if (exists) {
            _uiState.value =
                PosUiState.Error("Ya existe un método de pago con esta referencia")
        } else {
            sharedViewModel.addPaymentMethod(
                ModelMethod(
                    id = 0,
                    idMethod = 5,
                    type = 1,
                    methodName = "POS",
                    amountBs = amount,
                    reference = reference
                )
            )
        }
    }


    private fun findPaymentMethod(reference: String,sharedViewModel: CheckoutSharedViewModel): Boolean {
        val exists = sharedViewModel.hasPaymentMethodWith(
            reference.toString(),
            5
        )

        return exists
    }


    fun resetState() {
        _uiState.value = PosUiState.Idle
    }
}
