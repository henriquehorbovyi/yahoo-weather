
package com.henrik.yahooweather.ui.weather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.henrik.yahooweather.R
import com.henrik.yahooweather.domain.WeatherEntity
import com.henrik.yahooweather.internal.statusBarColor
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusBarColor(Color.LTGRAY)
        setContentView(R.layout.activity_weather)
        val weather = getExtras()
        setupView(weather)
    }

    private fun setupView(weatherEntity: WeatherEntity?){
        weatherEntity?.let {
            if(it.city?.trim() != "" && it.sys?.country != null){
                weather_city.text = "${it.city}, ${it.sys.country}"
            }else{
                weather_city.text = "-"
            }
            weather_temp.text = "${it.main.temp.toInt()}° C"
            weather_description.text = it.weather[0].description.capitalize()
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