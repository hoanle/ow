package com.whoisyari.myweather.util

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.whoisyari.myweather.data.model.*

fun JsonObject.getString(key: String): String {
    return (get(key) as? JsonPrimitive)?.asString ?: ""
}

fun JsonObject.getLong(key: String): Long {
    return (get(key) as? JsonPrimitive)?.asLong ?: 0L
}

fun JsonObject.getFloat(key: String): Float {
    return (get(key) as? JsonPrimitive)?.asFloat ?: 0.0f
}

fun JsonObject.getInt(key: String): Int {
    return (get(key) as? JsonPrimitive)?.asInt ?: 0
}

fun JsonObject.getCity(key: String): City? {
    return if (get(key) == null || get(key).isJsonNull) {
        null
    } else {
        Gson().fromJson(get(key), City::class.java)
    }
}

fun JsonObject.getWeatherList(key: String): List<Weather> {
    val list = arrayListOf<Weather>()
    val a = get(key)
    (a as? JsonArray)?.forEach {
        val jsonObject = it.asJsonObject
        val item = Weather(
            jsonObject.getInt("id"),
            jsonObject.getString("main"),
            jsonObject.getString("description"),
            jsonObject.getString("icon")
        )
        list.add(item)
    }
    return list
}

fun JsonObject.getWeatherItemList(key: String): List<WeatherItem> {
    val list = arrayListOf<WeatherItem>()
    val a = get(key)
    (a as? JsonArray)?.forEach {
        val jsonObject = it.asJsonObject
        val item = WeatherItem(
            0,
            jsonObject.getLong("dt"),
            jsonObject.getLong("sunrise"),
            jsonObject.getLong("sunset"),
            if (jsonObject.get("temp") == null || jsonObject.get("temp").isJsonNull) {
                null
            } else {
                Gson().fromJson(jsonObject.get("temp"), Temp::class.java)
            },
            if (jsonObject.get("feels_like") == null || jsonObject.get("feels_like").isJsonNull) {
                null
            } else {
                Gson().fromJson(jsonObject.get("feels_like"), FeelsLike::class.java)
            },
            jsonObject.getInt("pressure"),
            jsonObject.getInt("humidity"),
            if (jsonObject.get("weather") == null || jsonObject.get("weather").isJsonNull) {
                arrayListOf()
            } else {
                jsonObject.getWeatherList("weather")
            },
            jsonObject.getFloat("speed"),
            jsonObject.getInt("deg"),
            jsonObject.getInt("clouds"),
            jsonObject.getInt("pop"),
            System.currentTimeMillis() / 1000
        )
        list.add(item)
    }
    return list
}