package com.example.myapplication

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
//import kotlinx.coroutines.launch
import java.lang.Exception


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class BreweryViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<ArrayList<Brewery>>()

    // The external immutable LiveData for the response String
    val response: MutableLiveData<ArrayList<Brewery>>
        get() = _response

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Unconfined)

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        //getBreweries()
    }

    /**
     * Sets the value of the response LiveData to the Mars API status or the successful number of
     * Mars properties retrieved.
     */
    public fun getBreweries() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getPropertiesDeferred = ApiClient.getClient.getBreweries()
            try {
                // Await the completion of our Retrofit request
                var listResult : ArrayList<Brewery> = getPropertiesDeferred.await()
                _response.value = listResult
            } catch (e: Exception) {
                _response.value = ArrayList<Brewery>()
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}