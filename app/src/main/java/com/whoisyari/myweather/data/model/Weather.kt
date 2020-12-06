package com.whoisyari.myweather.data.model

import com.google.gson.annotations.SerializedName

/**
 * Weather model, a part of Weather Item
 */
data class Weather(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("main")
    val main: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("icon")
    val icon: String = ""
) {
    constructor() : this(0, ", ", "")
}