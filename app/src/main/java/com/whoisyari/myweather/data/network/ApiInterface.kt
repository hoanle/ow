package com.whoisyari.myweather.data.network

import com.whoisyari.myweather.config.Settings
import com.whoisyari.myweather.data.network.response.WeatherListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api Interface
 */
interface ApiInterface {

    @GET("data/${Settings.API_VERSION}/forecast/daily")
    fun searchWeather(
        @Query("q") cityName: String,
        @Query("cnt") forecastDays: Int = 7,
        @Query("units") units: String = "Kelvin",
        @Query("appid") appId: String
    ): Observable<WeatherListResponse>
}