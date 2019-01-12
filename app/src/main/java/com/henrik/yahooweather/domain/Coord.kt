package com.henrik.yahooweather.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coord(
    val lat: Double,
    val lng: Double
) : Parcelable