package com.adminpay.caja.di

import com.adminpay.caja.data.repository.socket.TcpSocketImpl
import com.adminpay.caja.domain.model.socket.TcpSocketModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SocketModule {
    @Binds
    @Singleton
    abstract fun bindTcpServerManager(
        tcpSocketImpl: TcpSocketImpl
    ): TcpSocketModel
}