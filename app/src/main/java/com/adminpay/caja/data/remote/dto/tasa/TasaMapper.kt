package com.adminpay.caja.data.remote.dto.tasa


import com.adminpay.caja.domain.model.tasa.ModelTasa

class TasaMapper {

    fun mapToModel(dto: ResponseTasaDto): ModelTasa {
        return ModelTasa(
            id = dto.id,
            date = dto.date,
            amount = dto.amount,
            migrate = dto.migrate,
            status = dto.status,
            currency = dto.currency.toLong(), // Conversión de Int a Long
            currencyName = dto.currencyName
        )
    }

    // Opcional: Método inverso si necesitas convertir de ModelTasa a ResponseTasaDto
    fun mapToDto(model: ModelTasa): ResponseTasaDto {
        return ResponseTasaDto(
            id = model.id,
            amount = model.amount,
            currency = model.currency.toInt(), // Conversión de Long a Int
            currencyName = model.currencyName,
            date = model.date,
            migrate = model.migrate,
            status = model.status
        )
    }
}