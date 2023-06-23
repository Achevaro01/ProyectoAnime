package com.finalproyect.myanimedictionary.data.database

import androidx.lifecycle.LiveData

class FavouriteAnimeRepository(private val favouriteAnimeDao: FavouriteAnimeDao) {

    val getAllFavouriteAnime: LiveData<List<FavouriteAnime>> = favouriteAnimeDao.getAllFavouriteAnime()

    suspend fun insertFavouriteAnime(favouriteAnime: FavouriteAnime){
        favouriteAnimeDao.insertFavouriteAnime(favouriteAnime)
    }

    suspend fun deleteFavouriteAnime(favouriteAnime: FavouriteAnime) {
        favouriteAnimeDao.deleteFavouriteAnime(favouriteAnime)
    }

    suspend fun deleteAll(){
        favouriteAnimeDao.deleteAll()
    }
}