package com.adminpay.caja.data.remote.dto.paymentMethods.tarjeta

import com.adminpay.caja.domain.model.paymentMethods.CardPaymentResultModel
import org.json.JSONObject

fun cardPaymentResultFromJson(json: JSONObject): CardPaymentResultModel {
    val main = if (json.has("userInfo")) json.getJSONObject("userInfo") else json

    val dto = CardPaymentResultDto(
        source = main.optString("source"),
        autorizacion = main.optString("autorizacion", null.toString()),
        terminalId = main.optString("terminalId", null.toString()),
        merchantId = main.optString("merchantId", null.toString()),
        pan = main.optString("pan", null.toString()),
        lote = main.optString("lote", null.toString()),
        recibo = main.optString("recibo", null.toString()),
        message = main.optString("message"),
        stan = main.optString("stan", null.toString()),
        code = main.optString("code")
    )

    return dto.toDomain()
}