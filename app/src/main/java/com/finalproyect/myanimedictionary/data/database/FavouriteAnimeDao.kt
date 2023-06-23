package com.finalproyect.myanimedictionary.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteAnimeDao {

    @Query("SELECT * FROM anime_data ORDER BY id ASC")
    fun getAllFavouriteAnime(): LiveData<List<FavouriteAnime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteAnime(insert: FavouriteAnime)

    @Delete
    suspend fun deleteFavouriteAnime(delete: FavouriteAnime)

    @Query("DELETE FROM anime_data")
    suspend fun deleteAll()
}
