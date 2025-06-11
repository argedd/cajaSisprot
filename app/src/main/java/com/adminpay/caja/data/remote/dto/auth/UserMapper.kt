package com.adminpay.caja.data.remote.dto.auth

import com.adminpay.caja.domain.model.auth.User

fun UserDto.toDomain(): User = User(
    uid = uid,
    name = name,
    email = email,
    role = role,
    phone = phone,
    referralCodeId = referralCodeId,
    office = office,
    idGsoft = idGsoft,
    officeName = officeName,
    lastName = lastName,
    isSuperuser = isSuperuser
)