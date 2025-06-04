package com.adminpay.caja.domain.repository.tasa

import com.adminpay.caja.domain.model.tasa.ModelTasa

interface TasaRepository {
    suspend fun getTasaBcv(removed: Boolean): List<ModelTasa>
}