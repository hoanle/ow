package com.whoisyari.myweather.ui.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.whoisyari.myweather.data.model.WeatherItem

/**
 * The class is to differentiate the new data and new data
 * @param oldList: Current data shown in the RecyclerView
 * @param newList: New data that should be considered to display on the RecyclerView
 */
class WeatherItemDiffUtilCallback(
        private val oldList: List<WeatherItem>,
        private val newList: List<WeatherItem>

) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    //Consider only 4 factors: dt, average temp, pressure and humidity.
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].dt == newList[newItemPosition].dt
                && oldList[oldItemPosition].temp?.average == newList[newItemPosition].temp?.average
                && oldList[oldItemPosition].pressure == newList[newItemPosition].pressure
                && oldList[oldItemPosition].humidity == newList[newItemPosition].humidity
    }

    //Consider only 4 factors: dt, average temp, pressure and humidity.
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].dt == newList[newItemPosition].dt
                && oldList[oldItemPosition].temp?.average == newList[newItemPosition].temp?.average
                && oldList[oldItemPosition].pressure == newList[newItemPosition].pressure
                && oldList[oldItemPosition].humidity == newList[newItemPosition].humidity
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}