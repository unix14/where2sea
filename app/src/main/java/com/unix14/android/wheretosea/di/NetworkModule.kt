package com.monkeytech.playform.di

import android.content.Context
import android.content.SharedPreferences
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.unix14.android.wheretosea.network.ApiService
import com.unix14.android.wheretosea.network.ApiSettings
import com.unix14.android.wheretosea.network.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single { provideSharedPreferences(get()) }
    factory { ApiSettings(get()) }

    single { provideDefaultOkhttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}

const val BASE_SERVER_URL = "https://api.stormglass.io/v1/weather/"

fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("User", Context.MODE_PRIVATE)
}

fun provideDefaultOkhttpClient(apiSettings: ApiSettings): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    val authInterceptor = AuthInterceptor(apiSettings)

    val httpClient = OkHttpClient.Builder().addInterceptor(logging).addInterceptor(authInterceptor)
    return httpClient.build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {

    return Retrofit.Builder()
        .baseUrl(BASE_SERVER_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
