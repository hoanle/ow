package com.whoisyari.myweather.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.whoisyari.myweather.data.model.Weather
import java.util.*

class WeatherConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToWeatherList(data: String?): List<Weather> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Weather>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun weatherListToString(someObjects: List<Weather>): String {
        return gson.toJson(someObjects)
    }


}