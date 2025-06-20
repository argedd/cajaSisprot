package com.adminpay.caja.ui.presentation.checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.contract.BankAssociated
import com.adminpay.caja.domain.model.invoice.InvoiceModel
import com.adminpay.caja.domain.model.payment.register.CashDollarBillModel
import com.adminpay.caja.domain.model.payment.register.NewPaymentModel
import com.adminpay.caja.domain.model.payment.register.RequestPaymentRegisterModel
import com.adminpay.caja.domain.model.paymentMethods.ModelMethod
import com.adminpay.caja.domain.useCase.RegisterPaymentUseCase
import com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.bancaNacional.BancaNacionalUiState
import com.adminpay.caja.utils.parseHttpErrorMessage
import com.movilpay.autopago.utils.LoadingController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CheckoutSharedViewModel @Inject constructor(
    private val loadingController: LoadingController,
    private val registerPaymentUseCase: RegisterPaymentUseCase
) : ViewModel() {

    var selectedInvoice: InvoiceModel? = null



    var bankAssociated: BankAssociated? = null

    private var methodAutoId = 1

    private val _paymentMethods = MutableStateFlow<List<ModelMethod>>(emptyList())
    val paymentMethods: StateFlow<List<ModelMethod>> = _paymentMethods.asStateFlow()

    private val _chargedAmountBs = MutableStateFlow(0.0)
    val chargedAmountBs: StateFlow<Double> = _chargedAmountBs.asStateFlow()

    private val _remainingAmountBs = MutableStateFlow(0.0)
    val remainingAmountBs: StateFlow<Double> = _remainingAmountBs.asStateFlow()

    private val _uiState = MutableStateFlow<CheckoutUiState>(CheckoutUiState.Idle)
    val uiState: StateFlow<CheckoutUiState> = _uiState


    fun addPaymentMethod(method: ModelMethod) {
        viewModelScope.launch {
            val methodWithId = method.copy(id = methodAutoId++)
            _paymentMethods.value += methodWithId
            updateAmounts()
        }
    }

    fun hasPaymentMethodWith(reference: String, idMethod: Int): Boolean {
        return _paymentMethods.value.any {
            it.reference == reference && it.idMethod == idMethod
        }
    }


    fun removePaymentMethodById(id: Int) {
        viewModelScope.launch {
            _paymentMethods.value = _paymentMethods.value.filterNot { it.id == id }
            updateAmounts()
        }
    }

    // ✅ Limpia todos los métodos de pago
    fun clearPaymentMethods() {
        viewModelScope.launch {
            _paymentMethods.value = emptyList()
            updateAmounts()
        }
    }

    fun registerPayment(
        selectedInvoice: InvoiceModel,
        paymentMethods: List<ModelMethod>,
        finish: () -> Unit
    ) {
        val payments = paymentMethods
            .filter { it.type == 1 }
            .mapNotNull { it.idPayment }

        val newPayments = paymentMethods
            .filter { it.type == 2 }
            .mapNotNull {
                val amount = if (it.idMethod == 2) it.amount else it.amountBs
                val reference = it.reference
                val methodId = it.idMethod

                if (amount != null ) {
                    NewPaymentModel(
                        methodId = methodId,
                        amount = amount,
                        reference = reference.orEmpty()
                    )
                } else null
            }


        val cashDollarBill = paymentMethods
            .flatMap { it.cashDollarBill.orEmpty() }
            .mapNotNull { bill ->
                val denom = bill.denomination
                val serial = bill.serialCode
                if (denom != null && serial != null) {
                    CashDollarBillModel(
                        denomination = denom,
                        serialCode = serial
                    )
                } else null
            }

        val payload = RequestPaymentRegisterModel(
            payments = payments,
            invoices = listOf(selectedInvoice.id),
            newPayments = newPayments,
            cashDollarBill = cashDollarBill
        )

        Log.d("payload", payload.toString())

        viewModelScope.launch {
            loadingController.show()
            try {
                registerPaymentUseCase(payload)
                withContext(Dispatchers.Main) {
                    finish()
                }
                _uiState.value = CheckoutUiState.Success("Pago registrado con éxito")
            } catch (e: Exception) {
                val errorMessage = if (e is HttpException) {
                    parseHttpErrorMessage(e)
                } else {
                    e.message ?: "Error desconocido"
                }

                _uiState.value = CheckoutUiState.Error(errorMessage)
            } finally {
                loadingController.hide()
            }
        }

    }


    // ✅ Calcula y actualiza los montos cargados y restantes
    fun updateAmounts() {
        val invoice = selectedInvoice ?: return
        val total = invoice.amountBs.amount
        val initialCharged = invoice.chargedBs
        val additionalPayments = _paymentMethods.value.sumOf { it.amountBs ?: 0.0 }
        val updatedCharged = initialCharged + additionalPayments
        val updatedRemaining = total - updatedCharged

        _chargedAmountBs.value = updatedCharged
        _remainingAmountBs.value = updatedRemaining
    }

   fun clearAmounts() {
        _chargedAmountBs.value = 0.0
        _remainingAmountBs.value = 0.0
    }
    fun resetState() {
        _uiState.value = CheckoutUiState.Idle
    }
}

sealed class CheckoutUiState {
    data object Idle : CheckoutUiState()
    data class Success(val message: String? = null) : CheckoutUiState()
    data class Error(val message: String) : CheckoutUiState()
}

