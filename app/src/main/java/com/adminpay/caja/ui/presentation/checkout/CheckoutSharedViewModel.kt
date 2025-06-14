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

    // Métodos de pago
    private val _paymentMethods = MutableStateFlow<List<ModelMethod>>(emptyList())
    val paymentMethods: StateFlow<List<ModelMethod>> = _paymentMethods.asStateFlow()

    // Agregar método de pago
    fun addPaymentMethod(method: ModelMethod) {
        viewModelScope.launch {
            _paymentMethods.value += method
        }
    }

    // Eliminar método de pago por ID
    fun removePaymentMethodById(id: Int) {
        viewModelScope.launch {
            _paymentMethods.value = _paymentMethods.value.filterNot { it.id == id }
        }
    }

    // Opcional: Limpiar todos
    fun clearPaymentMethods() {
        viewModelScope.launch {
            _paymentMethods.value = emptyList()
        }
    }
}
