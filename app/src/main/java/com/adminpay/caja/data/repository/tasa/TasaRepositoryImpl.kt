package com.adminpay.caja.data.repository.tasa

import android.util.Log
import com.adminpay.caja.data.remote.api.TasaApi
import com.adminpay.caja.data.remote.dto.tasa.TasaMapper
import com.adminpay.caja.domain.model.tasa.ModelTasa
import com.adminpay.caja.domain.repository.tasa.TasaRepository
import javax.inject.Inject

class TasaRepositoryImpl @Inject constructor(
    private val api: TasaApi,
) : TasaRepository {
    private val tasaMapper = TasaMapper() // Instancia directa

    override suspend fun getTasaBcv(): List<ModelTasa> {
        Log.d("TasaRepositoryImpl", "Fetching tasa from API")
        val response = api.getTasa()
        return response.map { dto ->
            Log.d("TasaRepositoryImpl", "Mapping DTO to Model: $dto")
            tasaMapper.mapToModel(dto)
        }
    }
}