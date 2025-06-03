// === di/AuthModule.kt ===
package com.adminpay.caja.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.adminpay.caja.data.interceptors.AuthInterceptor
import com.adminpay.caja.data.providers.TokenProvider
import com.adminpay.caja.data.remote.api.AuthApi
import com.adminpay.caja.data.repository.auth.AuthRepositoryImpl
import com.adminpay.caja.domain.repository.auth.AuthRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {


    @Provides
    @Singleton
    fun provideSharedPrefs(app: Application): SharedPreferences =
        app.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideTokenProvider(prefs: SharedPreferences): TokenProvider =
        TokenProvider(prefs)

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenProvider: TokenProvider): AuthInterceptor =
        AuthInterceptor(tokenProvider)

    @Provides
    @Singleton
    @AuthOkHttp
    fun provideAuthOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(
        @AuthOkHttp okHttpClient: OkHttpClient,
        @ApiUrl baseUrl: String,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideAuthApi(@AuthRetrofit retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        tokenProvider: TokenProvider
    ): AuthRepository =
        AuthRepositoryImpl(api, tokenProvider)
}
