package com.finalproyect.myanimedictionary.data.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteAnimeViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<FavouriteAnime>>
    private val repository: FavouriteAnimeRepository

    init {
        val favouriteAnimeDao = FavouriteAnimeDB.getDatabase(application).favouriteAnimeDao()
        repository = FavouriteAnimeRepository(favouriteAnimeDao)
        readAllData = repository.getAllFavouriteAnime
    }

    fun insertFavouriteAnime(favouriteAnime: FavouriteAnime){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavouriteAnime(favouriteAnime)
        }
    }

    fun deleteFavouriteAnime(favouriteAnime: FavouriteAnime) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavouriteAnime(favouriteAnime)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}