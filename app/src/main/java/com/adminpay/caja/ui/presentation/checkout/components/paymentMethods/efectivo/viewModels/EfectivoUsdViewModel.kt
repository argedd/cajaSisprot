package com.adminpay.caja.ui.presentation.checkout.components.paymentMethods.efectivo.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.adminpay.caja.domain.model.paymentMethods.CashDollarBill
import com.adminpay.caja.domain.model.paymentMethods.ModelMethod
import com.adminpay.caja.ui.presentation.checkout.CheckoutSharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EfectivoUsdViewModel @Inject constructor(
) : ViewModel() {

    var amount by mutableStateOf("")
        private set

    var cashBills = mutableStateListOf<CashDollarBill>()
        private set

    fun onAmountChange(value: String) {
        amount = value
    }

    fun addCashBill(bill: CashDollarBill) {
        cashBills.add(bill)
    }

    fun removeCashBill(bill: CashDollarBill) {
        cashBills.remove(bill)
    }

    fun addUsdPayment(
        sharedViewModel: CheckoutSharedViewModel,
        onSuccess: () -> Unit
    ) {
        val parsedAmount = amount.toDoubleOrNull() ?: return
        if (cashBills.isEmpty()) return // obligatorio

        sharedViewModel.addPaymentMethod(
            ModelMethod(
                id = 2,
                idMethod = 8,
                type = 2,
                methodName = "Efectivo USD",
                amount = parsedAmount,
                amountBs = 100.00,
                cashDollarBill = cashBills.toList()
            )
        )
        clearForm()
        onSuccess()
    }

    fun clearForm() {
        amount = ""
        cashBills.clear()
    }
}
