package com.adminpay.caja.ui.presentation.contract

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.contract.Contract
import com.adminpay.caja.domain.useCase.GetContractsUseCase
import com.adminpay.caja.utils.parseHttpErrorMessage
import com.movilpay.autopago.utils.LoadingController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ContractViewModel @Inject constructor(
    private val getContractByIdentificationUseCase: GetContractsUseCase,
    private val loadingController: LoadingController

) : ViewModel() {

    private val _state = MutableStateFlow(ContractState())
    val state: StateFlow<ContractState> = _state.asStateFlow()

    fun onTipoChanged(tipo: String) {
        _state.value = _state.value.copy(tipo = tipo)
    }

    fun onCedulaChanged(cedula: String) {
        _state.value = _state.value.copy(cedula = cedula)
    }

    fun buscarContrato() {
        viewModelScope.launch {
            loadingController.show()
            try {
                val id = "${_state.value.tipo}${_state.value.cedula}"
                val contrato = getContractByIdentificationUseCase(id)
                _state.value = _state.value.copy(
                    contratos = contrato,
                    errorMessage = null // Reinicia error al buscar
                )
                if (contrato.isEmpty()) {
                    _state.value = _state.value.copy(
                        contratos = emptyList(),
                        errorMessage = "Cliente no encontrado"
                    )
                }

                Log.d("ContractViewModel", "Contrato encontrado: $contrato")
            } catch (e: Exception) {
                val mensaje = if (e is HttpException) {
                    parseHttpErrorMessage(e)
                } else {
                    e.message ?: "Error desconocido"
                }

                _state.value = _state.value.copy(
                    contratos = emptyList(),
                    errorMessage = mensaje
                )
            } finally {
                loadingController.hide()
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(errorMessage = null)
    }

}

data class ContractState(
    val tipo: String = "V",
    val cedula: String = "",
    val contratos: List<Contract> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null // Nuevo campo para el error
)

