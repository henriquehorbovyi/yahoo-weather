package com.henrik.yahooweather.data.network

import com.henrik.yahooweather.domain.WeatherEntity
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {

    @GET("weather")
    fun getCurrentWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"): Deferred<WeatherEntity>

    companion object {
        private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
        private const val API_KEY_VALUE = "65801d4a7291947056c5981048be9f63"
        private const val API_KEY_PARAMETER_NAME = "APPID"

        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor) : OpenWeatherApiService{

            val apiKeyInterceptor = Interceptor{chain ->
                val alteredUrl = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(API_KEY_PARAMETER_NAME, API_KEY_VALUE)
                    .build()
                val alteredRequest = chain.request()
                    .newBuilder()
                    .url(alteredUrl)
                    .build()
                return@Interceptor chain.proceed(alteredRequest)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(apiKeyInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApiService::class.java)
        }
    }

}