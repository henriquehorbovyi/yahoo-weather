
package com.henrik.yahooweather.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.henrik.yahooweather.R
import com.henrik.yahooweather.domain.WeatherEntity
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val weather = getExtras()
        setupView(weather)
    }

    private fun setupView(weatherEntity: WeatherEntity?){
        weatherEntity?.let {
            weather_temp.text = "${it.main.temp} C°"

            val imageIcon = it.weather[0].icon
            val imageUrl = "http://openweathermap.org/img/w/$imageIcon.png"
            Glide.with(this).load(imageUrl).into(weather_icon)
        }
    }

    private fun getExtras(): WeatherEntity?{
        return intent.extras?.let {
            return@let it.get("weather") as WeatherEntity
        }
    }

}