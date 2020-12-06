package com.whoisyari.myweather

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.whoisyari.myweather.data.network.ApiClient
import com.whoisyari.myweather.data.repository.AppDatabase
import com.whoisyari.myweather.data.repository.weather_item.WeatherItemRepositoryImpl
import com.whoisyari.myweather.ui.adapter.WeatherItemHolder
import com.whoisyari.myweather.ui.fragment.WeatherSearchFragment
import com.whoisyari.myweather.view_model.WeatherSearchViewModel
import org.junit.Before
import org.junit.Test

class WeatherSearchFragmentTest {

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        val apiService = ApiClient().getService()

        val weatherItemRepository =
            WeatherItemRepositoryImpl(apiService, appDatabase.weatherItemDao())
        val currencyViewModel = WeatherSearchViewModel(weatherItemRepository)

        val factory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                if (className == WeatherSearchFragment::class.java.name) {
                    return WeatherSearchFragment(currencyViewModel)
                }
                return super.instantiate(classLoader, className)
            }
        }
        launchFragmentInContainer<WeatherSearchFragment>(null, factory = factory)
    }

    @Test
    fun launchFragmentAndVerifyUI() {
        Espresso.onView(ViewMatchers.withId(R.id.etInput)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.btnGetWeather)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun testScrollToListItem() {

        Espresso.onView(ViewMatchers.withId(R.id.etInput)).perform(ViewActions.typeText("saigon"))

        Espresso.onView(ViewMatchers.withId(R.id.btnGetWeather)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.rvWeathers)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.rvWeathers)).check(
            ViewAssertions.matches(
                ViewMatchers.isCompletelyDisplayed()
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.rvWeathers))
            .perform(RecyclerViewActions.scrollToPosition<WeatherItemHolder>(3))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            )
    }
}