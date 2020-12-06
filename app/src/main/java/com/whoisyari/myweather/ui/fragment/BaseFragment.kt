package com.whoisyari.myweather.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * The base fragment of the app, contains methods and variables that can be shared across different instances
 */

abstract class BaseFragment<T : ViewDataBinding> : Fragment(), FragmentBindingInterface<T> {

    protected lateinit var binding: T

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(
                inflater, layoutId(), null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performBinding(binding)
        registerLiveData()
    }

    /**
     * To display the error message
     * @param message: The content of the error
     */
    fun showSnackBar(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * To close the keyboard when the app finishes searching or has some error
     */
    fun closeKeyboard() {
        activity?.let {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.currentFocus?.windowToken, 0)
        }
    }

    /**
     * All LiveData should be registered in this functions
     */
    abstract fun registerLiveData()

    @LayoutRes
    abstract fun layoutId(): Int
}