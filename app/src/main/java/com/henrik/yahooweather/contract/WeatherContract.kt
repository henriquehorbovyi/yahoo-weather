package com.henrik.yahooweather.contract

import com.henrik.yahooweather.domain.WeatherEntity

interface WeatherContract {

    interface View{
        fun onCurrentWeatherResult(weatherEntity: WeatherEntity?)
        fun onNetworkFailure()
    }

    interface Presenter{
        suspend fun requestCurrentWeather(lat: Double, lng: Double)
    }
}