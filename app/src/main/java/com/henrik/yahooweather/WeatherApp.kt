package com.henrik.yahooweather

import android.app.Application
import com.henrik.yahooweather.di.AppModule
import org.koin.android.ext.android.startKoin

class WeatherApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this, arrayListOf(AppModule.modules))
    }
}
