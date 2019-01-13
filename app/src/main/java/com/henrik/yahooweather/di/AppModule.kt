package com.henrik.yahooweather.di

import com.henrik.yahooweather.contract.WeatherContract
import com.henrik.yahooweather.data.network.*
import com.henrik.yahooweather.data.repository.WeatherRepository
import com.henrik.yahooweather.data.repository.WeatherRepositoryImpl
import com.henrik.yahooweather.presenter.WeatherPresenter
import org.koin.dsl.module.module

object AppModule {
    val modules = module{
        factory <ConnectivityInterceptor> { ConnectivityInterceptorImpl(get()) }
        factory { OpenWeatherApiService(get()) }
        factory <WeatherNetworkDataSource> { WeatherNetworkDataSourceImpl(get()) }
        factory<WeatherRepository> { WeatherRepositoryImpl(get()) }
        single{ (view: WeatherContract.View) -> WeatherPresenter(get(), view) }
    }
}