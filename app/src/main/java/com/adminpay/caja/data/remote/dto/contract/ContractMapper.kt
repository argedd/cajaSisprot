package com.adminpay.caja.data.remote.dto.contract

import com.adminpay.caja.domain.model.contract.BankAssociated
import com.adminpay.caja.domain.model.contract.Contract

fun ContractDto.toDomain(): Contract {
    return Contract(
        id = id,
        name = name,
        lastName = lastName,
        identification = identification,
        mobile = mobile,
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
        )
    )
}