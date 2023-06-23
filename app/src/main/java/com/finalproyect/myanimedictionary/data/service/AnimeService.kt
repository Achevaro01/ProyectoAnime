package com.finalproyect.myanimedictionary.data.service

import com.finalproyect.myanimedictionary.data.v4.AnimeSearch
import com.finalproyect.myanimedictionary.data.v4.v4Prove
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeService {

    @GET("top/anime")
    fun getTopAnimes(): Call<v4Prove>

    @GET("anime")
    fun getSearchedAnime(@Query("q")queryString: String): Call<AnimeSearch>

    @GET("anime/{id}/episodes/{episode}")
    fun getAnimeEpisodeById(@Path(value = "id", encoded = true) id:String, @Path(value = "episode", encoded = true) episode:String): String

    companion object {
        val BASE_URL = "https://api.jikan.moe/v4/"

        fun create(): AnimeService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(AnimeService::class.java)
        }
    }

}