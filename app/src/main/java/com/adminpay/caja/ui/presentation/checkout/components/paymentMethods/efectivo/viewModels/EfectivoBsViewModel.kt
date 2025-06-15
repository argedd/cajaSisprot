package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.efectivo.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.adminpay.caja.domain.model.paymentMethods.ModelMethod
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EfectivoBsViewModel @Inject constructor(
) : ViewModel() {

    var amount by mutableStateOf("")
        private set

    fun onAmountChange(value: String) {
        amount = value
    }

    fun addBsPayment(
        sharedViewModel: CheckoutSharedViewModel,
        onSuccess: () -> Unit
    ) {
        val parsedAmount = amount.toDoubleOrNull() ?: return

        sharedViewModel.addPaymentMethod(
            ModelMethod(
                id = 0,
                idMethod = 8,
                type = 2,
                methodName = "Efectivo BS",
                amountBs = parsedAmount,
                amount = 0.00,
                cashDollarBill = null // obligatorio
            )
        )
        clearForm()
        onSuccess()
    }

    fun clearForm() {
        amount = ""
    }
}

