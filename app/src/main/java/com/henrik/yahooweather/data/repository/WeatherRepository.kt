package com.henrik.yahooweather.data.repository

import com.henrik.yahooweather.domain.WeatherEntity

interface WeatherRepository {
    suspend fun getCurrentWeatherByCoordinates(lat: Double, lng: Double): WeatherEntity?
}