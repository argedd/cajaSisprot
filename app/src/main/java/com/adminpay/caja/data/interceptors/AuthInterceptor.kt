package com.adminpay.caja.data.interceptors

import android.util.Log
import com.adminpay.caja.data.providers.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenProvider.getToken()

        // Logging URL y método
        Log.d("AuthInterceptor", "➡️ Request: ${originalRequest.method} ${originalRequest.url}")

        val requestBuilder = originalRequest.newBuilder()
        if (!token.isNullOrEmpty()) {
            Log.d("AuthInterceptor", "🔐 Token: Bearer $token")
            requestBuilder.addHeader("Authorization", "Bearer $token")
        } else {
            Log.d("AuthInterceptor", "⚠️ Token no encontrado")
        }

        val request = requestBuilder.build()
        val response = chain.proceed(request)

        val responseBody = response.body
        val content = responseBody?.string()

        Log.d("AuthInterceptor", "✅ Status Code: ${response.code}")
        Log.d("AuthInterceptor", "📦 Body: $content")

        // Re-construir el body para que Retrofit pueda leerlo
        val newBody = content?.toResponseBody(responseBody?.contentType())

        return response.newBuilder()
            .body(newBody)
            .build()
    }
}
