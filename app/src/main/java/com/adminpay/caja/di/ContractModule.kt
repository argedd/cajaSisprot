package com.adminpay.caja.di

import com.adminpay.caja.data.remote.api.ContractApi
import com.adminpay.caja.data.repository.contract.ContractRepositoryImpl
import com.adminpay.caja.domain.repository.contract.ContractRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContractModule {

    @Provides
    @Singleton
    fun provideContractApi(@AuthRetrofit retrofit: Retrofit): ContractApi =
        retrofit.create(ContractApi::class.java)

    @Provides
    @Singleton
    fun provideContractRepository(api: ContractApi): ContractRepository =
        ContractRepositoryImpl(api)
}