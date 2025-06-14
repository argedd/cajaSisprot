package com.adminpay.caja.ui.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.contract.BankAssociated
import com.adminpay.caja.domain.model.invoice.InvoiceModel
import com.adminpay.caja.domain.model.paymentMethods.ModelMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutSharedViewModel @Inject constructor() : ViewModel() {

    var selectedInvoice: InvoiceModel? = null



    var bankAssociated: BankAssociated? = null
    private var methodAutoId = 1

    private val _paymentMethods = MutableStateFlow<List<ModelMethod>>(emptyList())
    val paymentMethods: StateFlow<List<ModelMethod>> = _paymentMethods.asStateFlow()

    private val _chargedAmountBs = MutableStateFlow(0.0)
    val chargedAmountBs: StateFlow<Double> = _chargedAmountBs.asStateFlow()

    private val _remainingAmountBs = MutableStateFlow(0.0)
    val remainingAmountBs: StateFlow<Double> = _remainingAmountBs.asStateFlow()

    // ✅ Agrega método con ID autoincremental y actualiza montos
    fun addPaymentMethod(method: ModelMethod) {
        viewModelScope.launch {
            val methodWithId = method.copy(id = methodAutoId++)
            _paymentMethods.value += methodWithId
            updateAmounts()
        }
    }

    // ✅ Elimina método por ID y actualiza montos
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
}
