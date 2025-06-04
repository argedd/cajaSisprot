package com.adminpay.caja.data.repository.tasa

import com.adminpay.caja.data.remote.api.TasaApi
import com.adminpay.caja.data.remote.dto.tasa.TasaMapper
import com.adminpay.caja.domain.model.tasa.ModelTasa
import com.adminpay.caja.domain.repository.tasa.TasaRepository
import javax.inject.Inject

class TasaRepositoryImpl @Inject constructor(
    private val api: TasaApi,
) : TasaRepository {
    private val tasaMapper = TasaMapper() // Instancia directa

    override suspend fun getTasaBcv(removed: Boolean): List<ModelTasa> {
        val response = api.getTasa(removed)
        return response.map { dto ->
            tasaMapper.mapToModel(dto)
        }
    }
}