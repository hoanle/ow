package com.whoisyari.myweather.data.repository.weather_item

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.whoisyari.myweather.data.model.WeatherItem
import com.whoisyari.myweather.data.repository.AppDatabase

/**
 * Interface to define action on local WeatherItems collection
 */
@Dao
interface WeatherItemDao {

    @Query("SELECT * FROM WeatherItem WHERE city_name LIKE :cityName AND forecast_days = :forecastDays AND units = :units LIMIT 5")
    fun getWeatherItemList(cityName: String, forecastDays: Int, units: String): LiveData<List<WeatherItem>>

    @Insert(entity = WeatherItem::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWeatherItems(weathers: List<WeatherItem>)

    @Query("DELETE FROM WeatherItem WHERE created_at < :time")
    fun purgeExpiredRecord(time: Long)
}

/**
 * Implementation of the WeatherItemDao
 */
class WeatherItemDaoImp(private val db: AppDatabase) : WeatherItemDao {

    /**
     * Get weather from local db
     * @cityName: the query input by user, then create a 'like' pattern to search in db
     * @forecastDays: days of forecast, to match in db
     * @units: units of searching, to match in db
     */
    override fun getWeatherItemList(
            cityName: String,
            forecastDays: Int,
            units: String): LiveData<List<WeatherItem>> {
        return db.weatherItemDao().getWeatherItemList("%$cityName%", forecastDays, units)
    }

    /**
     * Insert a list of weather items into local db
     * @weathers: the list item
     */
    override fun insertAllWeatherItems(weathers: List<WeatherItem>) {
        return db.weatherItemDao().insertAllWeatherItems(weathers)
    }

    /**
     * Remove expired item
     * @time: the back time that items can stay
     */
    override fun purgeExpiredRecord(time: Long) {
        db.weatherItemDao().purgeExpiredRecord(time)
    }
}