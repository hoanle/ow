package com.whoisyari.myweather.config

/**
 * The configuration of the app
 */
class Settings {
    companion object {

        //Version of Open Weather API
        const val API_VERSION = 2.5

        //Version of DB, should be increased if there is any change in schemas
        const val DB_VERSION = 1

        const val APP_ID = "NjBjNmZiZWI0YjkzYWM2NTNjNDkyYmE4MDZmYzM0NmQ="

        //The default search days of Open Weather API
        const val DEFAULT_SEARCH_DAYS = 7

        //The default units of Open Weather API
        const val DEFAULT_SEARCH_UNIT = "Kelvin"

        //The maximum expiry time of caching
        const val CACHED_EXPIRY_TIME = 60 * 60 * 1000

        //Format date
        const val DATE_FORMAT = "EEE, dd MMM YYYY"
    }
}