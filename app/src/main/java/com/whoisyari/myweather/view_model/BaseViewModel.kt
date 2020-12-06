package com.whoisyari.myweather.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException

/**
 * The base class of View Mode, contains some functions and variable to share accross different VM
 */
open class BaseViewModel : ViewModel(), ThreadProvider {

    val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    //To send out error message for the app to handle
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun clear() {
        compositeDisposable.clear()
    }

    /**
     * When a throwable happens, check some cases to get the message
     */
    fun consumeError(t: Throwable) = if (t is HttpException) {
        if (t.code() == 400 || t.code() == 404) {
            val responseBody = t.response()?.errorBody()?.string()
            val jsonObject = JSONObject(responseBody!!.trim())
            val message = jsonObject.getString("message")
            _errorLiveData.postValue(message)
        } else {
            _errorLiveData.postValue(t.localizedMessage)
        }
    } else {
        _errorLiveData.postValue(t.localizedMessage)
    }
}

interface ThreadProvider {
    fun getSchedulerIO(): Scheduler {
        return Schedulers.io()
    }

    fun getMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}