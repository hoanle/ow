package com.whoisyari.myweather

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.whoisyari.myweather.ui.activity.MainActivity
import com.whoisyari.myweather.ui.adapter.WeatherItemHolder
import com.whoisyari.myweather.ui.fragment.WeatherSearchFragment
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun makeSureMainFragmentIsVisibleAndGetData() {
        val activity: MainActivity = activityRule.activity

        val fragment = activity.supportFragmentManager.findFragmentById(R.id.mainFrame)
        Assert.assertEquals(true, fragment != null)
        Assert.assertEquals(true, fragment is WeatherSearchFragment)
    }

    @Test
    fun checkConversionWorks() {
        Thread.sleep(2000)

        //Fill in the amount
        Espresso.onView(ViewMatchers.withId(R.id.etInput)).perform(ViewActions.typeText("saigon"))

        //Tap to select button
        Espresso.onView(ViewMatchers.withId(R.id.btnGetWeather)).perform(
                ViewActions.click()
        )

        //Can scroll and select 3st currency
        Espresso.onView(ViewMatchers.withId(R.id.rvWeathers))
                .perform(RecyclerViewActions.scrollToPosition<WeatherItemHolder>(3))
                .check(
                        ViewAssertions.matches(
                                ViewMatchers.isDisplayed()
                        )
                )
    }
}