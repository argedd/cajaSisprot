package com.adminpay.caja.ui.presentation.box

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.payment.get.PaymentSummaryResultDomain
import com.adminpay.caja.domain.useCase.GetPaymentsOfDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BoxViewModel @Inject constructor(
    private val getPaymentSummaryUseCase: GetPaymentsOfDayUseCase
) : ViewModel() {

    private val _summary = MutableStateFlow<PaymentSummaryResultDomain?>(null)
    val summary: StateFlow<PaymentSummaryResultDomain?> = _summary

    fun loadSummary() {
        viewModelScope.launch {
            val venezuelaZoneId = ZoneId.of("America/Caracas")
            val today = ZonedDateTime.now(venezuelaZoneId).toLocalDate().format(DateTimeFormatter.ISO_DATE)
            val group = "method"
            val result = getPaymentSummaryUseCase(group, today)
            Log.d("BoxViewModel", "Summary: $result")
            _summary.value = result
        }
    }
}
