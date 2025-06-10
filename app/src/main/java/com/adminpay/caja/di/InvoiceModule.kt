package com.adminpay.caja.di

import com.adminpay.caja.data.remote.api.InvoiceApi
import com.adminpay.caja.data.repository.invoice.InvoiceRepositoryImpl
import com.adminpay.caja.domain.repository.invoice.InvoiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InvoiceModule {

    @Provides
    @Singleton
    fun provideInvoiceApi(@AuthRetrofit retrofit: Retrofit): InvoiceApi =
        retrofit.create(InvoiceApi::class.java)

    @Provides
    @Singleton
    fun provideInvoiceRepository(api: InvoiceApi): InvoiceRepository =
        InvoiceRepositoryImpl(api)
}