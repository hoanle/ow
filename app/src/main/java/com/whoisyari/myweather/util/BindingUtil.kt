package com.whoisyari.myweather.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Some custom biding adapter
 */
object BindingUtil {

    /**
     * Bind an adapter to RecyclerView
     * @rv: The recyclerview that will receive the adapter
     * @adapter: item to bind
     */
    @BindingAdapter("adapterToRv")
    @JvmStatic
    fun <T : RecyclerView.ViewHolder> setAdapterToRecyclerView(rv: RecyclerView, adapter: RecyclerView.Adapter<T>) {
        rv.adapter = adapter
    }

    /**
     * Bind an layoutManager to RecyclerView
     * @rv: The recyclerview that will receive the adapter
     * @layoutManager: item to bind
     */
    @BindingAdapter("layoutManagerToRv")
    @JvmStatic
    fun setLayoutManagerToRecyclerView(rv: RecyclerView, layoutManager: GridLayoutManager) {
        rv.layoutManager = layoutManager
    }
}