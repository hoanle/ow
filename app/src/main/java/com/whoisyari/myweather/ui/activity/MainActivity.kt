package com.whoisyari.myweather.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.whoisyari.myweather.R
import com.whoisyari.myweather.ui.fragment.WeatherSearchFragment
import org.koin.android.ext.android.inject

/**
 * Main activity of app, has a empty container. Fragments will be put to this container
 * For simplicity, the app does not handle complicated case like reset configuration now
 */
class MainActivity : AppCompatActivity() {

    //Inject the WeatherSearchFragment and put it to the container
    private val weatherSearchFragment: WeatherSearchFragment by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrame, weatherSearchFragment)
            .commit()
    }
}