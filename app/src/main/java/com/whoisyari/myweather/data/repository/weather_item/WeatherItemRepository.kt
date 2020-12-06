package com.whoisyari.myweather.data.repository.weather_item

import android.util.Base64
import androidx.lifecycle.LiveData
import com.whoisyari.myweather.config.Settings
import com.whoisyari.myweather.data.model.WeatherItem
import com.whoisyari.myweather.data.network.ApiInterface
import com.whoisyari.myweather.data.network.response.WeatherListResponse
import io.reactivex.Observable
import java.nio.charset.StandardCharsets

/**
 * Concrete class of WeatherItemRepository
 * @param apiInterface: Api service
 * @param weatherItemDao: WeatherItem DAO
 */
class WeatherItemRepositoryImpl(
    private val apiInterface: ApiInterface,
    private val weatherItemDao: WeatherItemDao
) : IWeatherRepository {

    override fun getRemoteWeatherList(
        cityName: String,
        forecastDays: Int,
        units: String
    ): Observable<WeatherListResponse> {
        return apiInterface.searchWeather(cityName, forecastDays, units, appId)
    }

    override fun getLocalWeatherList(
        cityName: String,
        forecastDays: Int,
        units: String
    ): LiveData<List<WeatherItem>> {
        return weatherItemDao.getWeatherItemList(cityName, forecastDays, units)
    }

    override fun purgeOldWeatherItem(time: Long) {
        return weatherItemDao.purgeExpiredRecord(time)
    }

    override fun insertWeatherItemsToLocalDb(weatherItems: List<WeatherItem>) {
        return weatherItemDao.insertAllWeatherItems(weatherItems)
    }

    private val appId: String by lazy { String(Base64.decode(Settings.APP_ID, Base64.NO_WRAP), StandardCharsets.UTF_8) }
}

/**
 * The interface to define actions: search weather from remote server store in locals
 */
interface IWeatherRepository {

    /**
     * To call Open Weather Service to get a list of WeatherItem
     * @cityName: the name of the place user wants to know the information
     * @forecastDays: Days to query, default value in Settings
     * units: Unit to query, default value in Settings
     */
    fun getRemoteWeatherList(
        cityName: String,
        forecastDays: Int = Settings.DEFAULT_SEARCH_DAYS,
        units: String = Settings.DEFAULT_SEARCH_UNIT
    ): Observable<WeatherListResponse>

    /**
     * To call the local db to get the list of WeatherItem
     * @cityName: the name of the place user wants to know the information
     * @forecastDays: Days to query, default value in Settings
     * units: Unit to query, default value in Settings
     */
    fun getLocalWeatherList(
        cityName: String,
        forecastDays: Int = Settings.DEFAULT_SEARCH_DAYS,
        units: String = Settings.DEFAULT_SEARCH_UNIT
    ): LiveData<List<WeatherItem>>

    /**
     * User to insert a list of weather item with search phrase to db and use for cache later
     * @param weatherItems: The list of item for searching
     */
    fun insertWeatherItemsToLocalDb(weatherItems: List<WeatherItem>)

    /**
     * To remove expired record from local db
     * time: The back time that record can stay
     */
    fun purgeOldWeatherItem(time: Long)
}