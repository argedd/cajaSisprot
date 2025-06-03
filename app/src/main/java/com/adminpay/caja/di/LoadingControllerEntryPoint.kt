package com.adminpay.caja.di

import com.movilpay.autopago.utils.LoadingController
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LoadingControllerEntryPoint {
    fun loadingController(): LoadingController
}
