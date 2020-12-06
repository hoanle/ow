package com.whoisyari.myweather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.whoisyari.myweather.R
import com.whoisyari.myweather.data.model.WeatherItem
import com.whoisyari.myweather.databinding.WeatherItemBinding

/**
 * WeatherItem adapter class. It uses RecyclerViewAdapter with DiffUtil to optimise the speed
 */
class WeatherItemAdapter() : RecyclerView.Adapter<WeatherItemHolder>() {

    var list: ArrayList<WeatherItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): WeatherItemHolder {
        val binding = DataBindingUtil
                .inflate<WeatherItemBinding>(
                        LayoutInflater.from(parent.context), R.layout.weather_item,
                        parent, false
                )

        return WeatherItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<WeatherItem>) {
        val diffCallback = WeatherItemDiffUtilCallback(list, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: WeatherItemHolder, position: Int) {
        holder.binding.weatherItem = list[position]
        holder.binding.executePendingBindings()
    }
}

class WeatherItemHolder(
        val binding: WeatherItemBinding
) : RecyclerView.ViewHolder(binding.root) {

}