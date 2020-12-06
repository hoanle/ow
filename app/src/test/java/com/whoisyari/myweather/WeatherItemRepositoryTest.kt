package com.whoisyari.myweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.TestObserver
import com.whoisyari.myweather.config.Settings
import com.whoisyari.myweather.data.model.WeatherItem
import com.whoisyari.myweather.data.network.ApiInterface
import com.whoisyari.myweather.data.network.response.WeatherListResponse
import com.whoisyari.myweather.data.repository.weather_item.WeatherItemDao
import com.whoisyari.myweather.data.repository.weather_item.WeatherItemRepositoryImpl
import com.whoisyari.myweather.view_model.WeatherSearchViewModel

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test

class WeatherItemRepositoryTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val apiInterface: ApiInterface = mockk()
    private var weatherItemDao: WeatherItemDao = mockk()

    @Test
    fun emitsRemoteData() {
        val repository = WeatherItemRepositoryImpl(apiInterface, weatherItemDao)

        val response: WeatherListResponse = mockk()
        every {
            response.list
        } returns arrayListOf()

        every {
            apiInterface.searchWeather(
                "saigon",
                Settings.DEFAULT_SEARCH_DAYS,
                Settings.DEFAULT_SEARCH_UNIT,
                Settings.APP_ID
            )
        } returns Observable.just(response)

        val testObserver = repository.getRemoteWeatherList(
            "saigon",
            Settings.DEFAULT_SEARCH_DAYS,
            Settings.DEFAULT_SEARCH_UNIT
        ).test()

        testObserver.assertValue(response)
        testObserver.dispose()
    }

    @Test
    fun emitsLocalData() {
        val repository = WeatherItemRepositoryImpl(apiInterface, weatherItemDao)
        val weatherItem: WeatherItem = mockk()
        val localLiveData = MutableLiveData<List<WeatherItem>>(arrayListOf(weatherItem))

        every {
            repository.getLocalWeatherList(
                "saigon",
                Settings.DEFAULT_SEARCH_DAYS,
                Settings.DEFAULT_SEARCH_UNIT
            )
        } returns localLiveData

        val viewModel = spyk(WeatherSearchViewModel(repository))

        viewModel.searchWeather(
            "saigon",
            Settings.DEFAULT_SEARCH_DAYS,
            Settings.DEFAULT_SEARCH_UNIT
        )

        TestObserver.test(viewModel.weatherListLiveData).assertValue {
            it == localLiveData.value
        }
    }
}