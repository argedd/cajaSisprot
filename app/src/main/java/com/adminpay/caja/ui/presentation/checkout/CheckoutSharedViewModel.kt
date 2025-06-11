package com.adminpay.caja.ui.presentation.checkout


import androidx.lifecycle.ViewModel
import com.adminpay.caja.domain.model.invoice.InvoiceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckoutSharedViewModel @Inject constructor() : ViewModel() {
    var selectedInvoice: InvoiceModel? = null
}
