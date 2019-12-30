package com.example.myapplication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
{
id: 3976,
name: "Anheuser-Busch InBev",
brewery_type: "large",
street: "1 Busch Pl",
city: "Saint Louis",
state: "Missouri",
postal_code: "63118-1849",
country: "United States",
longitude: "-90.2118998",
latitude: "38.5954536",
phone: "3145772000",
website_url: "http://www.anheuser-busch.com",
updated_at: "2018-08-24T00:46:11.169Z",
tag_list: [ ]
},
 */
data class Brewery(
            @SerializedName("id")
            val id: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("brewery_type")
            val type: String,
            @SerializedName("street")
            val street: String,
            @SerializedName("city")
            val city: String,
            @SerializedName("state")
            val state: String,
            @SerializedName("postal_code")
            val postal_code: String,
            @SerializedName("country")
            val country: String,
            @SerializedName("longitude")
            val longitude: String,
            @SerializedName("latitude")
            val latitude: String,
            @SerializedName("phone")
            val phone: String,
            @SerializedName("website_url")
            val website_url: String,
            @SerializedName("updated_at")
            val updated_at: String,
            @SerializedName("tag_list")
            val tag_list : Array<String>
)

// Data Model for the Response returned from the TMDB Api
data class BreweryResponse(
    val results: List<Brewery>?
)