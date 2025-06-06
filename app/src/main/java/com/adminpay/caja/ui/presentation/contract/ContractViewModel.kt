package com.adminpay.caja.ui.presentation.contract

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.contract.Contract
import com.adminpay.caja.domain.useCase.GetContractsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContractViewModel @Inject constructor(
    private val getContractByIdentificationUseCase: GetContractsUseCase
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
            try {
                val id = "${_state.value.tipo}${_state.value.cedula}"
                val contrato = getContractByIdentificationUseCase(id)
                _state.value = _state.value.copy(contratos = contrato)
            } catch (e: Exception) {
                _state.value = _state.value.copy(contratos = emptyList())
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
