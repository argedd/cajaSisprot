package com.adminpay.caja.data.providers

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class TokenProvider @Inject constructor(
    private val prefs: SharedPreferences
) {
    fun saveToken(token: String) {
        prefs.edit { putString("auth_token", token) }
    }

    fun getToken(): String? = prefs.getString("auth_token", null)

    fun clearToken() {
        prefs.edit { remove("auth_token") }
    }
}