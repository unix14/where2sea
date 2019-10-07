package com.unix14.android.wheretosea.models

import java.util.*
import kotlin.collections.ArrayList

data class Forecast(
    var hours: ArrayList<HourForecast>,
    var meta: Metadata
)

data class ForecastValue(
    var source: String,
    var value: Float
)

data class HourForecast(
    var time: Date,
    var airTemperature: ArrayList<ForecastValue>,
    var waveHeight: ArrayList<ForecastValue>,
    var seaLevel: ArrayList<ForecastValue>
)

data class Metadata(
    var dailyQuota: Int,
    var lat: Float,
    var lng: Float,
    var requestCount: Int
)
