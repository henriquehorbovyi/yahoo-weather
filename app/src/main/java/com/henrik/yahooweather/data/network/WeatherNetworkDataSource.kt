package com.henrik.yahooweather.data.network

import com.henrik.yahooweather.domain.WeatherEntity

interface WeatherNetworkDataSource {
    suspend fun getCurrentWeatherByCoordinates(lat: Double, lng: Double): WeatherEntity?
}