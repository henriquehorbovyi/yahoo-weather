package com.henrik.yahooweather.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherEntity(
    val id: Int,
    @SerializedName("name")
    val city: String?,
    val main: Main,
    val weather: List<Weather>,
    val coord: Coord,
    val sys: Sys?
) : Parcelable {

    override fun toString(): String {
        return "About ${main.temp.toInt()}° C in $city, ${sys?.country} (${weather[0].description.capitalize()})"
    }

}