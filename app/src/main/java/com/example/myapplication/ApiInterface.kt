package com.example.myapplication

//import kotlinx.coroutines.Deferred
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("breweries")
    fun getBreweries(): Deferred<ArrayList<Brewery>>

}