package com.whoisyari.myweather.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
* Temp model, a part of Weather Item
*/

@Entity
class Temp(
        @SerializedName("day")
        val day: Float = 0.0f,

        @SerializedName("min")
        val min: Float = 0.0f,

        @SerializedName("max")
        val max: Float = 0.0f,

        @SerializedName("night")
        val night: Float = 0.0f,

        @SerializedName("eve")
        val eve: Float = 0.0f,

        @SerializedName("morn")
        val morn: Float = 0.0f
) {
    @delegate:Transient
    val average: Float by lazy { (min + max) / 2 }
}