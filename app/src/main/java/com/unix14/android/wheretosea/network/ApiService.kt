package com.unix14.android.wheretosea.network

import com.unix14.android.wheretosea.models.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("point")
    fun getForecast(@Query("lat") lat: Float, @Query("lng") lng: Float, @Query("params") params: String): Call<Forecast>
}