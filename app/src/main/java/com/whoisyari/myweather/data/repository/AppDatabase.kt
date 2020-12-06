package com.whoisyari.myweather.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whoisyari.myweather.config.Settings
import com.whoisyari.myweather.data.model.WeatherItem
import com.whoisyari.myweather.data.repository.weather_item.WeatherItemDao

/**
 * Define the app database
 */
@Database(
        entities = [
            WeatherItem::class
        ],
        version = Settings.DB_VERSION,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherItemDao(): WeatherItemDao
}