package com.finalproyect.myanimedictionary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteAnime::class],
    version = 1,
    exportSchema = false
)
abstract class FavouriteAnimeDB : RoomDatabase(){

    abstract fun favouriteAnimeDao(): FavouriteAnimeDao

    companion object{
        @Volatile
        private var INSTANCE: FavouriteAnimeDB? = null

        fun getDatabase(context: Context): FavouriteAnimeDB{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteAnimeDB::class.java,
                    "anime_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
