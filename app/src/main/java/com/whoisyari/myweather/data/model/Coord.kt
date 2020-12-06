package com.whoisyari.myweather.data.model

import com.google.gson.annotations.SerializedName

/**
 * Coord model, a part of Weather Item
 */
data class Coord(
    @SerializedName("lon")
    val lon: Float,

    @SerializedName("lat")
    val lat: Float
)