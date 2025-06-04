package com.adminpay.caja.ui.presentation.main.components.tasa

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adminpay.caja.domain.model.tasa.ModelTasa
import com.adminpay.caja.domain.useCase.GetTasaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasaViewModel @Inject constructor(
    private val getTasaUseCase: GetTasaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TasaViewState())
    val state: StateFlow<TasaViewState> = _state.asStateFlow()

    init {
        loadCurrentDayTasa()
    }

    fun loadCurrentDayTasa() {
        getTasa()
    }

    private fun getTasa() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val result = getTasaUseCase(removed = true)
                _state.update {
                    it.copy(
                        isLoading = false,
                        tasas = result,
                        error = null,
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Error: ${e.localizedMessage}"
                    )
                }
            }
        }
    }
}

data class TasaViewState(
    val isLoading: Boolean = false,
    val tasas: List<ModelTasa> = emptyList(),
    val error: String? = null,
)