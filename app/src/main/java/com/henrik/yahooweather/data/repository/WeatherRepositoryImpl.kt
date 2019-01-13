package com.henrik.yahooweather.data.repository

import com.henrik.yahooweather.data.network.WeatherNetworkDataSource
import com.henrik.yahooweather.domain.WeatherEntity

class WeatherRepositoryImpl(private val weatherNetworkDataSource: WeatherNetworkDataSource) : WeatherRepository {

    override suspend fun getCurrentWeatherByCoordinates(lat: Double, lng: Double): WeatherEntity?{
        return weatherNetworkDataSource.getCurrentWeatherByCoordinates(lat, lng)
    }
}