package com.adminpay.caja.ui.presentation.box

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.office.Filters
import com.adminpay.caja.domain.model.office.RequestOfficeCloseModel
import com.adminpay.caja.domain.model.payment.get.PaymentSummaryResultDomain
import com.adminpay.caja.domain.useCase.GetPaymentsOfDayUseCase
import com.adminpay.caja.domain.useCase.RequestOfficeCloseUseCase
import com.adminpay.caja.utils.parseHttpErrorMessage
import com.movilpay.autopago.utils.LoadingController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BoxViewModel @Inject constructor(
    private val getPaymentSummaryUseCase: GetPaymentsOfDayUseCase,
    private val closeOfficeUseCase: RequestOfficeCloseUseCase,
    private val loadingController: LoadingController,

    ) : ViewModel() {

    private val _summary = MutableStateFlow<PaymentSummaryResultDomain?>(null)
    val summary: StateFlow<PaymentSummaryResultDomain?> = _summary

    private val _uiState = MutableStateFlow<OfficeUiState>(OfficeUiState.Idle)
    val uiState: StateFlow<OfficeUiState> = _uiState
    val venezuelaZoneId = ZoneId.of("America/Caracas")
    val today =
        ZonedDateTime.now(venezuelaZoneId).toLocalDate()
            .format(DateTimeFormatter.ISO_DATE)
    val group = "method"
    fun loadSummary() {
        viewModelScope.launch {
            loadingController.show()
            try {

                val result = getPaymentSummaryUseCase(group, today)
                _summary.value = result
            } catch (e: Exception) {
                val errorMessage = if (e is HttpException) {
                    parseHttpErrorMessage(e)
                } else {
                    e.message ?: "Error desconocido"
                }

                _uiState.value = OfficeUiState.Error(errorMessage)
            } finally {
                loadingController.hide()
            }


        }
    }

    fun closeOffice() {
        viewModelScope.launch {
            loadingController.show()
            try {
                closeOfficeUseCase(
                    request = RequestOfficeCloseModel(
                        fileType = "excel",
                        description = "cierre de oficina",
                        fileLabel = "office_closure",
                        filters = Filters(
                            resulType = "SELF",
                            since = today,
                            until = today
                        )
                ))
                _uiState.value = OfficeUiState.Success("Cierre de caja realizado con éxito")
            } catch (e: Exception) {
                val errorMessage = if (e is HttpException) {
                    parseHttpErrorMessage(e)
                } else {
                    e.message ?: "Error desconocido"
                }

                _uiState.value = OfficeUiState.Error(errorMessage)
            } finally {
                loadingController.hide()
            }


        }
    }

    fun resetState() {
        _uiState.value = OfficeUiState.Idle
    }
}

sealed class OfficeUiState {
    data object Idle : OfficeUiState()
    data class Success(val message: String? = null) : OfficeUiState()
    data class Error(val message: String) : OfficeUiState()
}
