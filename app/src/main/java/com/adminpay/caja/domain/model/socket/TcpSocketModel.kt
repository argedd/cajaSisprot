package com.adminpay.caja.domain.model.socket

import com.adminpay.caja.domain.model.paymentMethods.CardPaymentResultModel
import kotlinx.coroutines.Deferred

interface TcpSocketModel {
    fun start(port: Int = 5000)
    fun emitToClients(event: String, data: String)
    fun waitForResult(): Deferred<CardPaymentResultModel>
    fun closeConnections()
    fun hasConnectedClients(): Boolean
}