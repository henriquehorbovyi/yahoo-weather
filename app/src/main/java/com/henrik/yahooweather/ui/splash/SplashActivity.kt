package com.henrik.yahooweather.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.henrik.yahooweather.R
import kotlinx.android.synthetic.main.activity_splash.*
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.henrik.yahooweather.contract.WeatherContract
import com.henrik.yahooweather.domain.WeatherEntity
import com.henrik.yahooweather.internal.PhoneLocation
import com.henrik.yahooweather.internal.isPermissionGranted
import com.henrik.yahooweather.presenter.WeatherPresenter
import com.henrik.yahooweather.ui.base.ScopedActivity
import com.henrik.yahooweather.ui.weather.WeatherActivity
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

private const val LOCATION_PERMISSION_CODE = 100
private const val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION

class SplashActivity : ScopedActivity(), WeatherContract.View{

    private val weatherPresenter: WeatherPresenter by inject{ parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        try_again_button.setOnClickListener {
            hideNoConnectivityAnimation()
            showSearchLocationAnimation()
            initApp()
        }
    }

    private fun initApp(){
        showSearchLocationAnimation()
        val location = PhoneLocation.requestLocation(this)
        location?.let {
            launch {
                weatherPresenter.requestCurrentWeather(location.latitude, location.longitude)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        isPermissionGranted(LOCATION_PERMISSION, permissionNotGranted = {
            ActivityCompat.requestPermissions(this, arrayOf(LOCATION_PERMISSION), LOCATION_PERMISSION_CODE)
        }, permissionGranted = {
            initApp()
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            LOCATION_PERMISSION_CODE -> {
                if(grantResults.count() > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initApp()
                }else{
                    AlertDialog.Builder(this)
                        .setMessage("You denied permission. Go in settings and give location permission for this app for a normal usage.")
                        .setPositiveButton("Go to settings") { _, _ ->
                            val settingsIntent = Intent()
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            settingsIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", packageName, null)
                            settingsIntent.data = uri
                            startActivity(settingsIntent)
                        }
                        .setNegativeButton("Close") { _, _ ->
                            finish()
                        }
                        .show()
                }
            }
        }
    }

    override fun onCurrentWeatherResult(weatherEntity: WeatherEntity?) {
        val weatherActivityIntent = Intent(this@SplashActivity, WeatherActivity::class.java)
        weatherActivityIntent.putExtra("weather", weatherEntity)
        startActivity(weatherActivityIntent)
        finish()
    }

    override fun onNetworkFailure() {
        hideSearchLocationAnimation()
        showNoConnectivityAnimation()
    }

    private fun hideSearchLocationAnimation(){
        animation_search_location.pauseAnimation()
        animation_search_location.visibility = View.GONE
        loading.visibility = View.GONE
    }
    private fun showSearchLocationAnimation(){
        animation_search_location.playAnimation()
        animation_search_location.visibility = View.VISIBLE
        loading.visibility = View.VISIBLE
    }

    private fun hideNoConnectivityAnimation(){
        animation_no_connection.pauseAnimation()
        animation_no_connection.visibility = View.GONE
        try_again_button.visibility = View.GONE
    }
    private fun showNoConnectivityAnimation(){
        animation_no_connection.playAnimation()
        animation_no_connection.visibility = View.VISIBLE
        try_again_button.visibility = View.VISIBLE
    }

}
