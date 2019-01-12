package com.henrik.yahooweather.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Main(val temp: Float) : Parcelable