package com.adminpay.caja.ui.presentation.contract

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.contract.Contract
import com.adminpay.caja.domain.useCase.GetContractsUseCase
import com.movilpay.autopago.utils.LoadingController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
                _state.value = _state.value.copy(contratos = contrato)
                Log.d("ContractViewModel", "Contrato encontrado: $contrato")
            } catch (e: Exception) {
                _state.value = _state.value.copy(contratos = emptyList())
            }finally {
                loadingController.hide()

            }
        }
    }
}

data class ContractState(
    val tipo: String = "V",
    val cedula: String = "",
    val contratos: List<Contract> = emptyList(),
    val isLoading: Boolean = false
)
