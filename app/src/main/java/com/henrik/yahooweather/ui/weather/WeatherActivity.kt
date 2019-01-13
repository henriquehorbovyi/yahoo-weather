
package com.henrik.yahooweather.ui.weather

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.henrik.yahooweather.R
import com.henrik.yahooweather.domain.WeatherEntity
import com.henrik.yahooweather.internal.statusBarColor
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    private var weatherEntity: WeatherEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusBarColor(Color.LTGRAY)
        setContentView(R.layout.activity_weather)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        weatherEntity = getExtras()
        setupView(weatherEntity)
    }

    @SuppressLint("SetTextI18n")
    private fun setupView(weatherEntity: WeatherEntity?){
        weatherEntity?.let {
            weather_city.text =
                    if(it.city?.trim() != "" && it.sys?.country != null) "${it.city}, ${it.sys.country}" else "-"
            weather_temp.text = "${it.main.temp.toInt()}° C"
            val imageIcon = it.weather[0].icon
            weather_description.text = it.weather[0].description.capitalize()
            val imageUrl = "http://openweathermap.org/img/w/$imageIcon.png"
            Glide.with(this@WeatherActivity)
                .load(imageUrl)
                .into(weather_icon)
        }
    }

    private fun getExtras(): WeatherEntity?{
        return intent.extras?.let {
            return@let it.get("weather") as WeatherEntity?
        }
    }

    private fun shareWeather(weather: WeatherEntity?){
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, weather.toString())
        startActivity(shareIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                shareWeather(weatherEntity)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}