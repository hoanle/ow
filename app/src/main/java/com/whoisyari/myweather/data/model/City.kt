package com.whoisyari.myweather.data.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

/**
 * City model, a part of Weather Item
 */
data class City(

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("coord")
    @Embedded(prefix = "coord")
    val coord: Coord,

    @SerializedName("country")
    val country: String = "",

    @SerializedName("population")
    val population: Int = 0,

    @SerializedName("timezone")
    val timezone: Int = 0
)