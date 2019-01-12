package com.henrik.yahooweather.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.henrik.yahooweather.R
import kotlinx.android.synthetic.main.activity_splash.*
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.henrik.yahooweather.data.network.OpenWeatherApiService
import com.henrik.yahooweather.ui.base.ScopedActivity
import com.henrik.yahooweather.ui.weather.WeatherActivity
import kotlinx.coroutines.*

private const val LOCATION_PERMISSION_CODE = 100

class SplashActivity : ScopedActivity(){

    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        askPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE)
    }

    private fun askPermission(permission: String, requestCode: Int){
        if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }else{
            initApp()
        }
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

    private fun initApp(){
        showSearchLocationAnimation()
        val location = getPhoneLocation()
        location?.let {
            launch {
                val weather = withContext(Dispatchers.IO){
                    OpenWeatherApiService.invoke().getCurrentWeatherByCoordenates(location.latitude, location.longitude).await()
                }
                val weatherActivityIntent = Intent(this@SplashActivity, WeatherActivity::class.java)
                weatherActivityIntent.putExtra("weather", weather)
                startActivity(weatherActivityIntent)
                finish()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getPhoneLocation(): Location?{
        var gpsLocation: Location? = null
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(hasGPS){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f, object : LocationListener{
                override fun onLocationChanged(location: Location?) {
                    location?.let { gpsLocation = it }
                }
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                override fun onProviderEnabled(provider: String?) {}
                override fun onProviderDisabled(provider: String?) {}
            })
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lastKnownLocation?.let { gpsLocation = it }
        }else{
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        return gpsLocation
    }

    private fun showSearchLocationAnimation(){
        animation_view.visibility = View.VISIBLE
    }
}
