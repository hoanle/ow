package com.whoisyari.myweather.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.whoisyari.myweather.config.Settings
import com.whoisyari.myweather.util.WeatherConverter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Weather Item class
 */
@Entity
@TypeConverters(WeatherConverter::class)
class WeatherItem(
        @Transient
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") var id: Long = 0,

        @SerializedName("dt")
        @ColumnInfo(name = "dt")
        val dt: Long = 0,

        @SerializedName("sunrise")
        @ColumnInfo(name = "sunrise")
        val sunrise: Long = 0,

        @SerializedName("sunset")
        @ColumnInfo(name = "sunset")
        val sunset: Long = 0,

        @SerializedName("temp")
        @Embedded(prefix = "temp")
        val temp: Temp? = null,

        @SerializedName("feels_like")
        @Embedded(prefix = "feels_like")
        val feelsLike: FeelsLike? = null,

        @SerializedName("pressure")
        @ColumnInfo(name = "pressure")
        val pressure: Int = 0,

        @SerializedName("humidity")
        @ColumnInfo(name = "humidity")
        val humidity: Int = 0,

        @SerializedName("weather")
        @ColumnInfo(name = "weather")
        val weather: List<Weather> = arrayListOf(),

        @SerializedName("speed")
        @ColumnInfo(name = "speed")
        val speed: Float = 0.0f,

        @SerializedName("deg")
        @ColumnInfo(name = "deg")
        val deg: Int = 0,

        @SerializedName("clouds")
        @ColumnInfo(name = "clouds")
        val clouds: Int = 0,

        @SerializedName("pop")
        @ColumnInfo(name = "pop")
        val pop: Int = 0,

        @ColumnInfo(name = "created_at")
        var createAt: Long = 0,

        @ColumnInfo(name = "city_name")
        var cityName: String = "",

        @ColumnInfo(name = "forecast_days")
        var forecastDays: Int = Settings.DEFAULT_SEARCH_DAYS,

        @ColumnInfo(name = "units")
        var units: String = Settings.DEFAULT_SEARCH_UNIT
) {

    //Form the string of the date
    @delegate:Transient
    val dtStr: String by lazy { SimpleDateFormat(Settings.DATE_FORMAT, Locale("en")).format(Date(dt * 1000)) }

    //Get the average temp form Temp model
    @delegate:Transient
    val averageTemp: Float by lazy {
        temp?.average ?: 0.0f
    }

    //Get the first description from weather list
    @delegate:Transient
    val description: String by lazy {
        if (weather.isNullOrEmpty()) {
            ""
        } else {
            weather[0].description
        }
    }

    //Form the string for humidity
    @delegate:Transient
    val humidityStr: String by lazy { "$humidity%" }
}