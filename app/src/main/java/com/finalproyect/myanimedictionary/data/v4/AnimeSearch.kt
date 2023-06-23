package com.finalproyect.myanimedictionary.data.v4


import com.google.gson.annotations.SerializedName

data class AnimeSearch(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("pagination")
    val pagination: Pagination
)