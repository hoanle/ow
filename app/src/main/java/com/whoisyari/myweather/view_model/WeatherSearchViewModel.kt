package com.whoisyari.myweather.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.whoisyari.myweather.config.Settings
import com.whoisyari.myweather.data.model.WeatherItem
import com.whoisyari.myweather.data.repository.weather_item.IWeatherRepository
import java.util.*

/**
 * SearchViewModel interface, contain action that view model can perform
 */
interface IWeatherSearchViewModel {

    /**
     * To call Open Weather Service to get a list of WeatherItem
     * @cityName: the name of the place user wants to know the information
     * @forecastDays: Days to query, default value in Settings
     * units: Unit to query, default value in Settings
     */
    fun searchWeatherFromRemoteSource(
            cityName: String,
            forecastDays: Int = Settings.DEFAULT_SEARCH_DAYS,
            units: String = Settings.DEFAULT_SEARCH_UNIT
    )

    /**
     * To call the local db to get the list of WeatherItem
     * @cityName: the name of the place user wants to know the information
     * @forecastDays: Days to query, default value in Settings
     * units: Unit to query, default value in Settings
     */
    fun searchWeatherInLocalDB(
            cityName: String,
            forecastDays: Int = Settings.DEFAULT_SEARCH_DAYS,
            units: String = Settings.DEFAULT_SEARCH_UNIT): LiveData<List<WeatherItem>>

    /**
     * To perform a search of weather items
     */
    fun searchWeather(
            cityName: String,
            forecastDays: Int = Settings.DEFAULT_SEARCH_DAYS,
            units: String = Settings.DEFAULT_SEARCH_UNIT)

    /**
     * To remove expired items
     */
    fun purgeOldWeatherItem()
}

/**
 * Implementation of the SearchViewModel
 */
class WeatherSearchViewModel(private val weatherItemRepository: IWeatherRepository) :
        BaseViewModel(), IWeatherSearchViewModel {

    //Two-way binding the city that user searches
    val cityLiveData = MutableLiveData<String>()

    private var _localRecordLiveData: LiveData<List<WeatherItem>>? = null

    //The data that will be share to fragment
    private val _weatherListLiveData = MutableLiveData<List<WeatherItem>>()
    val weatherListLiveData = _weatherListLiveData

    override fun searchWeatherFromRemoteSource(cityName: String, forecastDays: Int, units: String) {
        compositeDisposable.add(
                weatherItemRepository.getRemoteWeatherList(cityName, forecastDays, units)
                        .subscribeOn(getSchedulerIO())
                        .observeOn(getSchedulerIO())
                        .subscribe({
                            val transformed = it.list.map { item ->
                                item.cityName = cityName.toLowerCase(Locale.getDefault())
                                item.createAt = System.currentTimeMillis()
                                item.forecastDays = forecastDays
                                item.units = units
                                item
                            }
                            weatherItemRepository.insertWeatherItemsToLocalDb(transformed)
                        }, {
                            consumeError(it)
                        })
        )
    }

    override fun searchWeatherInLocalDB(cityName: String, forecastDays: Int, units: String): LiveData<List<WeatherItem>> {
        return weatherItemRepository.getLocalWeatherList(cityName, forecastDays, units)
    }

    override fun searchWeather(cityName: String, forecastDays: Int, units: String) {
        weatherListLiveData.postValue(arrayListOf())
        _localRecordLiveData?.apply {
            this.removeObserver(observer)
        }
        _localRecordLiveData = searchWeatherInLocalDB(cityName, forecastDays, units)
        _localRecordLiveData?.observeForever(observer)
    }

    override fun purgeOldWeatherItem() {
        Thread {
            weatherItemRepository.purgeOldWeatherItem(System.currentTimeMillis() - Settings.CACHED_EXPIRY_TIME)
        }.start()
    }

    //The observer that will use to perform search.
    private val observer: Observer<List<WeatherItem>> = Observer {
        if (it.isNullOrEmpty()) {
            searchWeatherFromRemoteSource(cityLiveData.value!!, Settings.DEFAULT_SEARCH_DAYS, Settings.DEFAULT_SEARCH_UNIT)
        } else {
            weatherListLiveData.postValue(it)
        }
    }
}

