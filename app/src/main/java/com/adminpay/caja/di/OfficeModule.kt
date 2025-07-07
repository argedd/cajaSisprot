package com.adminpay.caja.di

import com.adminpay.caja.data.remote.api.OfficeApi
import com.adminpay.caja.data.repository.office.OfficeRepositoryImpl
import com.adminpay.caja.domain.repository.office.OfficeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OfficeModule {

    @Provides
    @Singleton
    fun provideOfficeApi(@AuthRetrofit retrofit: Retrofit): OfficeApi =
        retrofit.create(OfficeApi::class.java)

    @Provides
    @Singleton
    fun provideOfficeRepository(api: OfficeApi): OfficeRepository =
        OfficeRepositoryImpl(api)

}