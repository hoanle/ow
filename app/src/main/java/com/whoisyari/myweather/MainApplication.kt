package com.whoisyari.myweather

import android.app.Application
import android.util.Base64
import android.util.Log
import com.whoisyari.myweather.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpDI()
    }

    /**
     * Start the entire app's modules
     */
    private fun setUpDI() {
        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}