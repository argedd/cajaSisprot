package com.adminpay.caja.di

import com.adminpay.caja.data.remote.api.TasaApi
import com.adminpay.caja.data.repository.tasa.TasaRepositoryImpl
import com.adminpay.caja.domain.repository.tasa.TasaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TasaModule {

    @Provides
    @Singleton
    fun provideTasaApi(@AuthRetrofit retrofit: Retrofit): TasaApi =
        retrofit.create(TasaApi::class.java)

    @Provides
    @Singleton
    fun provideTasaRepository(api: TasaApi): TasaRepository =
        TasaRepositoryImpl(api)
}