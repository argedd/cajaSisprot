package com.adminpay.caja.data.repository.socket

import android.util.Log
import com.adminpay.caja.data.remote.dto.paymentMethods.tarjeta.cardPaymentResultFromJson
import com.adminpay.caja.domain.model.paymentMethods.CardPaymentResultModel
import com.adminpay.caja.domain.model.socket.TcpSocketModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.InputStream
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.concurrent.thread

@Singleton
class TcpSocketImpl @Inject constructor() : TcpSocketModel {
    private var serverSocket: ServerSocket? = null
    private val connectedClients = ConcurrentHashMap<String, Socket>()
    private val resultCallbacks = mutableListOf<CompletableDeferred<CardPaymentResultModel>>()
    private var running = false

    override fun start(port: Int) {
        if (running) return
        running = true

        thread {
            try {
                serverSocket = ServerSocket(port)
                Log.d("TcpServer", "Servidor TCP escuchando en el puerto $port")

                while (running) {
                    val client = serverSocket!!.accept()
                    val clientAddress = client.inetAddress.hostAddress ?: continue

                    Log.d("TcpServer", "Cliente conectado: $clientAddress")

                    connectedClients[clientAddress]?.close()
                    connectedClients[clientAddress] = client

                    thread {
                        try {
                            val inputStream: InputStream = client.getInputStream()

                            while (running && !client.isClosed) {
                                val message = readMessage(inputStream)
                                if (message != null) {
                                    Log.d("TcpServer", "Mensaje recibido de $clientAddress: $message")

                                    try {
                                        val jsonMessage = JSONObject(message)
                                        val event = jsonMessage.getString("event")
                                        val data = jsonMessage.get("data")

                                        when (event) {
                                            "paymentToFront" -> {
                                                sendResults(data.toString())
                                            }

                                            "data", "connectedServer" -> {
                                                Log.d("TcpServer", "Evento '$event' recibido: $data")
                                            }

                                            else -> {
                                                Log.w("TcpServer", "Evento no reconocido: $event")
                                            }
                                        }

                                    } catch (e: Exception) {
                                        Log.e("TcpServer", "Error al parsear mensaje: ${e.message}")
                                    }
                                } else {
                                    break
                                }
                            }

                        } catch (e: Exception) {
                            Log.e("TcpServer", "Error con cliente $clientAddress: ${e.message}")
                        } finally {
                            Log.d("TcpServer", "Cliente $clientAddress desconectado.")
                            connectedClients.remove(clientAddress)
                            client.close()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("TcpServer", "Error al iniciar el servidor: ${e.message}")
            }
        }
    }

    private fun readMessage(inputStream: InputStream): String? {
        return try {
            val buffer = ByteArray(1024)
            val bytesRead = inputStream.read(buffer)
            if (bytesRead != -1) {
                String(buffer, 0, bytesRead)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("TcpServer", "Error al leer mensaje: ${e.message}")
            null
        }
    }

    override fun emitToClients(event: String, data: String) {
        thread {
            try {
                val jsonData = try {
                    JSONObject(data)
                } catch (e: Exception) {
                    data
                }

                val message = JSONObject().apply {
                    put("event", event)
                    put("data", jsonData)
                }.toString() + "\n"


                connectedClients.forEach { (ip, client) ->
                    try {
                        if (!client.isClosed && client.isConnected) {
                            val writer = BufferedWriter(OutputStreamWriter(client.getOutputStream()))
                            writer.write(message)
                            writer.flush()
                        } else {
                            connectedClients.remove(ip)
                            client.close()
                        }
                    } catch (e: Exception) {
                        connectedClients.remove(ip)
                        client.close()
                        Log.e("emitToClients", "Error al enviar a cliente $ip: ${e.message}", e)
                    }
                }

            } catch (e: Exception) {
                Log.e("emitToClients", "Error al emitir mensaje: ${e.message}", e)
            }
        }
    }

    override fun waitForResult(): Deferred<CardPaymentResultModel> {
        val deferred = CompletableDeferred<CardPaymentResultModel>()
        resultCallbacks.add(deferred)
        return deferred
    }

    private fun sendResults(data: String) {
        try {
            val json = JSONObject(data)
            val result = cardPaymentResultFromJson(json)
            resultCallbacks.forEach { it.complete(result) }
        } catch (e: Exception) {
            Log.e("sendResults", "Error al parsear resultado: ${e.message}", e)
            resultCallbacks.forEach { it.completeExceptionally(e) }
        } finally {
            resultCallbacks.clear()
        }
    }

    override fun closeConnections() {
        connectedClients.values.forEach { it.close() }
        connectedClients.clear()
        running = false
        serverSocket?.close()
        Log.d("TcpServer", "Conexiones cerradas.")
    }

    override fun hasConnectedClients(): Boolean {
        return connectedClients.isNotEmpty()
    }
}