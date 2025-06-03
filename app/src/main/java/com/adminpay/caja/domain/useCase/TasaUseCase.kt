package com.adminpay.caja.domain.useCase

import android.util.Log
import com.adminpay.caja.domain.model.tasa.ModelTasa
import com.adminpay.caja.domain.repository.tasa.TasaRepository
import javax.inject.Inject

class GetTasaUseCase @Inject constructor(    private val repository: TasaRepository
){
    suspend operator fun invoke(): List<ModelTasa> {
        Log.d("GetTasaUseCase", "Invoking use case")
        return repository.getTasaBcv()
    }

}