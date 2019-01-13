package com.henrik.yahooweather.internal

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings

 class PhoneLocation {

     companion object {
        @SuppressLint("MissingPermission")
        fun requestLocation(context: Context): Location?{
            val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var gpsLocation: Location? = null

            val hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if(hasGPS){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0f, object : LocationListener {
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
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            return gpsLocation
        }
    }
}