package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.pos


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.paymentMethods.CardPaymentResultModel
import com.adminpay.caja.domain.model.paymentMethods.ModelMethod
import com.adminpay.caja.domain.model.socket.TcpSocketModel
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.HttpException
import com.adminpay.caja.utils.parseHttpErrorMessage
import com.movilpay.autopago.utils.LoadingController
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withTimeout
import org.json.JSONObject

sealed class PosUiState {
    data object Idle : PosUiState()
    data class Success(val success: CardPaymentResultModel? = null) : PosUiState()
    data class Error(val message: String) : PosUiState()

}

@HiltViewModel
class PosViewModel @Inject constructor(
    private val loadingController: LoadingController,
    private val tcpServerManager: TcpSocketModel

    ) : ViewModel() {

    private val _uiState: MutableStateFlow<PosUiState> =
        MutableStateFlow(PosUiState.Idle)
    val uiState: StateFlow<PosUiState> = _uiState

    private val _connectedClients = MutableStateFlow(false)
    val connectedClients: StateFlow<Boolean> = _connectedClients.asStateFlow()

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
                    type = 2,
                    methodName = "POS-Manual",
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

    fun sendPayment(
        cedula: String,
        monto: Double,
        sharedViewModel: CheckoutSharedViewModel,
    ) {
        viewModelScope.launch {
            loadingController.show()
            _uiState.value = PosUiState.Idle

            try {
                if (!tcpServerManager.hasConnectedClients()) {
                    _uiState.value =
                        PosUiState.Error("Verificar conexión del punto de venta")
                    _connectedClients.value = false
                    return@launch
                }

                _connectedClients.value = true

                val payload = JSONObject().apply {
                    put("cedula", cedula)
                    put("monto", monto)
                }.toString()

                tcpServerManager.emitToClients("paymentClient", payload)

                val result = withTimeout(60_000) {
                    tcpServerManager.waitForResult().await()
                }

                Log.d("CardPaymentViewModel", "Respuesta recibida: $result")

                if (result.code == "00") {
                    _uiState.value = PosUiState.Success(result)
                    sharedViewModel.addPaymentMethod(
                        ModelMethod(
                            id = 0,
                            idMethod = 5,
                            type = 2,
                            methodName = "POS",
                            reference = result.recibo,
                            amountBs = monto
                        )
                    )
                } else {
                    _uiState.value = PosUiState.Error("Pago rechazado: ${result.message}")
                }

            } catch (e: HttpException) {
                val errorMsg = parseHttpErrorMessage(e)
                _uiState.value = PosUiState.Error(errorMsg)
            } catch (e: Exception) {
                _uiState.value = PosUiState.Error("Error inesperado: ${e.localizedMessage}")
            } finally {
                loadingController.hide()
            }
        }
    }



    fun resetState() {
        _uiState.value = PosUiState.Idle
    }
}
