package com.whoisyari.myweather.di

import androidx.room.Room
import com.whoisyari.myweather.data.network.ApiClient
import com.whoisyari.myweather.data.repository.AppDatabase
import com.whoisyari.myweather.data.repository.weather_item.IWeatherRepository
import com.whoisyari.myweather.data.repository.weather_item.WeatherItemDao
import com.whoisyari.myweather.data.repository.weather_item.WeatherItemDaoImp
import com.whoisyari.myweather.data.repository.weather_item.WeatherItemRepositoryImpl
import com.whoisyari.myweather.ui.fragment.WeatherSearchFragment
import com.whoisyari.myweather.view_model.WeatherSearchViewModel
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.dsl.module


/**
 * Network module, should be one for whole app life
 */
val networkModule = module {
    single {
        ApiClient().getService()
    }
}

/**
 * Database module, should be one for whole app life
 */
val daoModules = module {
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(charArrayOf('a', 'b', 'c'))
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(get(), AppDatabase::class.java, "weather_db")
            .openHelperFactory(factory)
            .build()
    }

    single<WeatherItemDao> { WeatherItemDaoImp(db = get()) }
}

/**
 * Repository module
 */
val repositoryModules = module {
    factory<IWeatherRepository> {
        WeatherItemRepositoryImpl(apiInterface = get(), weatherItemDao = get())
    }
}

/**
 * Fragment module. newInstance could be used but recently, there are lots of dev inject directly into constructor
 */
val fragmentModules = module {
    factory {
        WeatherSearchFragment(weatherSearchViewModel = get())
    }
}

/**
 * Factory module
 */
val viewModelModules = module {
    factory {
        WeatherSearchViewModel(weatherItemRepository = get())
    }
}

// Merge all modules to app modules list
val appModules = listOf(
    networkModule,
    daoModules,
    repositoryModules,
    fragmentModules,
    viewModelModules
)