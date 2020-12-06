package com.whoisyari.myweather.data.model

import com.google.gson.annotations.SerializedName

/**
 * FeelsLike model, a part of Weather Item
 */
data class FeelsLike(
    @SerializedName("day")
    val day: Float = 0.0f,

    @SerializedName("night")
    val night: Float = 0.0f,

    @SerializedName("eve")
    val eve: Float = 0.0f,

    @SerializedName("morn")
    val morn: Float = 0.0f
)