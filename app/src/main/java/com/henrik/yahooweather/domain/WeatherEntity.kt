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
) : Parcelable