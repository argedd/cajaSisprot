package com.adminpay.caja.data.remote.dto.contract

import com.adminpay.caja.domain.model.contract.BankAssociated
import com.adminpay.caja.domain.model.contract.Contract

fun ContractDto.toDomain(): Contract {
    return Contract(
        id = id,
        clientId = clientId,
        name = name,
        lastName = lastName,
        identification = identification,
        installationOrder = installationOrder,
        mobile = mobile,
        status = status,
        statusName = statusName,
        email = email,
        addressTax = addressTax,
        debtBs = debtBs,
        bankAssociated = BankAssociated(
            nroCta = bankAssociated.nroCta,
            rlf = bankAssociated.rlf,
            bankName = bankAssociated.bankName,
            bankCode = bankAssociated.bankCode,
            identification = bankAssociated.identification
        ),
        sectorName = sectorName,
        parishName = parishName,
        retainingClient = retainingClient
    )
}
