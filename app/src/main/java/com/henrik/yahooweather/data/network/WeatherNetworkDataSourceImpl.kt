package com.henrik.yahooweather.data.network

import com.henrik.yahooweather.domain.WeatherEntity
import com.henrik.yahooweather.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(private val weatherApiService: OpenWeatherApiService) : WeatherNetworkDataSource {

    override suspend fun getCurrentWeatherByCoordinates(lat: Double, lng: Double): WeatherEntity? {
        return weatherApiService.getCurrentWeatherByCoordinates(lat, lng).await()
    }

}