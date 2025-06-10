package com.adminpay.caja.data.remote.dto.invoice

import com.adminpay.caja.domain.model.invoice.InvoiceItemModel
import com.adminpay.caja.domain.model.invoice.InvoiceModel


fun InvoiceDto.toDomain(): InvoiceModel {
    return InvoiceModel(
        id = id,
        dollarDate = dollarDate,
        dollarRate = dollarRate,
        dateEmission = dateEmission,
        dateExpiration = dateExpiration,
        datePayment = datePayment,
        subTotal = subTotal,
        ivaAmount = ivaAmount,
        amount = amount,
        charged = charged,
        chargedBs = chargedBs,
        amountBs = amountBs,
        month = month,
        year = year,
        debt = debt,
        debtBs = debtBs,
        clientName = clientName,
        contract = contract,
        status = status,
        statusName = statusName,
        paymentAvailable = paymentAvailable,
        invoiceItems = invoiceItemsGsoft.map { it.toDomain() }
    )
}

fun InvoiceItemDto.toDomain(): InvoiceItemModel {
    return InvoiceItemModel(
        id = id,
        details = details,
        amount = amount,
        amountBs = amountBs,
        sum = sum,
        invoice = invoice,
        service = service,
        serviceName = serviceName
    )
}
