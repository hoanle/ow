package com.whoisyari.myweather.data.network.response

import com.google.gson.annotations.SerializedName
import com.whoisyari.myweather.data.model.City
import com.whoisyari.myweather.data.model.WeatherItem

/**
 * The response class from API get weather
 */
data class WeatherListResponse(
    @SerializedName("city")
    val city: City?,

    @SerializedName("cod")
    val cod: String,

    @SerializedName("message")
    val message: Float,

    @SerializedName("cnt")
    val cnt: Int,

    @SerializedName("list")
    val list: List<WeatherItem>
)