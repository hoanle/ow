package com.whoisyari.myweather.data.network.deserializer

import com.google.gson.*
import com.whoisyari.myweather.data.network.response.WeatherListResponse
import com.whoisyari.myweather.util.*
import java.lang.reflect.Type

class WeatherListResponseDeserializer : JsonDeserializer<WeatherListResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): WeatherListResponse {
        return (json as? JsonObject)?.let {
            WeatherListResponse(
                it.getCity("city"),
                it.getString("cod"),
                it.getFloat("message"),
                it.getInt("cnt"),
                it.getWeatherItemList("list")
            )
        } ?: throw JsonParseException("Invalid json: $json")
    }
}