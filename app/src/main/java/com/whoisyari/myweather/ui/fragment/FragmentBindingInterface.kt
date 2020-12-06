package com.whoisyari.myweather.ui.fragment

/**
 * Interface for loading and data binding fragment
 * T: Binding class
 */
interface FragmentBindingInterface<T> {
    fun performBinding(binding: T)
}