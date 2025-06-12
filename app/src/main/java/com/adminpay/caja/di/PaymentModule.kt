package com.adminpay.caja.di

import com.adminpay.caja.data.remote.api.PaymentApi
import com.adminpay.caja.data.repository.payment.PaymentRepositoryImpl
import com.adminpay.caja.domain.repository.payment.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentModule {

    @Provides
    @Singleton
    fun providePaymentApi(@AuthRetrofit retrofit: Retrofit): PaymentApi =
        retrofit.create(PaymentApi::class.java)

    @Provides
    @Singleton
    fun providePaymentRepository(api: PaymentApi): PaymentRepository =
        PaymentRepositoryImpl(api)

}