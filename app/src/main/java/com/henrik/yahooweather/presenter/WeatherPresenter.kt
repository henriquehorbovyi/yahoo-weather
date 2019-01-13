package com.henrik.yahooweather.presenter

import android.util.Log
import com.henrik.yahooweather.contract.WeatherContract
import com.henrik.yahooweather.data.repository.WeatherRepository
import com.henrik.yahooweather.internal.NoConnectivityException

class WeatherPresenter(
    private val weatherRepository: WeatherRepository,
    private val view: WeatherContract.View
) : WeatherContract.Presenter{

    override suspend fun requestCurrentWeather(lat: Double, lng: Double) {
        try{
            val currentWeather = weatherRepository.getCurrentWeatherByCoordinates(lat, lng)
            currentWeather?.let {
                view.onCurrentWeatherResult(currentWeather)
            }
        }catch (e: NoConnectivityException){
            view.onNetworkFailure()
        }
    }
}