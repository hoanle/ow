package com.whoisyari.myweather.ui.fragment

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.whoisyari.myweather.R
import com.whoisyari.myweather.config.Settings
import com.whoisyari.myweather.databinding.WeatherSearchFragmentBinding
import com.whoisyari.myweather.ui.adapter.WeatherItemAdapter
import com.whoisyari.myweather.view_model.WeatherSearchViewModel

/**
 * Main fragment where user can search and see the list of weather item now.
 */
class WeatherSearchFragment(private val weatherSearchViewModel: WeatherSearchViewModel) :
        BaseFragment<WeatherSearchFragmentBinding>() {

    //Register the LiveData
    override fun registerLiveData() {
        weatherSearchViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            closeKeyboard()
            showSnackBar(it)
        })

        weatherSearchViewModel.weatherListLiveData.observe(viewLifecycleOwner, Observer {
            closeKeyboard()
            binding.adapter?.setData(it)
        })
    }

    override fun layoutId(): Int {
        return R.layout.weather_search_fragment
    }

    //Bind
    override fun performBinding(binding: WeatherSearchFragmentBinding) {
        binding.forecastDays = Settings.DEFAULT_SEARCH_DAYS
        binding.units = Settings.DEFAULT_SEARCH_UNIT
        binding.viewModel = weatherSearchViewModel
        binding.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        binding.adapter = WeatherItemAdapter()
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    //Check cached expiry here
    override fun onResume() {
        super.onResume()
        weatherSearchViewModel.purgeOldWeatherItem()
    }

    //Release the disposals
    override fun onStop() {
        weatherSearchViewModel.clear()
        super.onStop()
    }
}