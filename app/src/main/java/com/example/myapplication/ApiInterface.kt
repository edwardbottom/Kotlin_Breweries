package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("breweries")
    fun getBreweries(): Call<List<Brewery>>

}