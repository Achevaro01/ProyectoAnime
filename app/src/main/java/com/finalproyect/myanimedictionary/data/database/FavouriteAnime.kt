package com.finalproyect.myanimedictionary.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_data")
data class FavouriteAnime (
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val title: String,
        val img: String
        )
