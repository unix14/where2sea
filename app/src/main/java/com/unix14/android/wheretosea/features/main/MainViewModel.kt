package com.unix14.android.wheretosea.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unix14.android.wheretosea.common.ProgressData
import com.unix14.android.wheretosea.common.SingleLiveEvent
import com.unix14.android.wheretosea.models.Forecast
import com.unix14.android.wheretosea.network.ApiService
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainViewModel(private val api: ApiService) : ViewModel() {

    sealed class ErrorEvent {
        data class Error(val errorMsg: String) : ErrorEvent()
    }

    sealed class NavigationEvent {
        data class ForecastRecievedEvent(val imagePath: String) : NavigationEvent()
    }

    val progressData: ProgressData = ProgressData()
    val navigationEvent = SingleLiveEvent<NavigationEvent>()
    val errorEvent = SingleLiveEvent<ErrorEvent>()
    val forecastResponse = MutableLiveData<Forecast>()

    fun fetchForecast(lat: Float, lng: Float) {
        progressData.startProgress()
        api.getForecast(lat, lng, "airTemperature,waveHeight,seaLevel").enqueue(object : Callback, retrofit2.Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if(response.isSuccessful){
                    val forecast = response.body()
                    if (forecast != null) {
                        errorEvent.postValue(ErrorEvent.Error("No Error"))
                        forecastResponse.postValue(forecast)
                    } else {
                        errorEvent.postValue(ErrorEvent.Error(response.errorBody().toString() + "**420**" + response.message()))
                    }
                }
                errorEvent.postValue(ErrorEvent.Error("Unsuccseessfull " + response.errorBody().toString() + "**420**" + response.message()))

                progressData.endProgress()
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                errorEvent.postValue(ErrorEvent.Error("Unsuccseessfull " + t.message))

                progressData.endProgress()
            }
        })

    }


}