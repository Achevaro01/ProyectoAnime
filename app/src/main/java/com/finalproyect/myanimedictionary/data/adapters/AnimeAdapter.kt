package com.finalproyect.myanimedictionary.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.finalproyect.myanimedictionary.R
import com.finalproyect.myanimedictionary.data.database.FavouriteAnime
import com.finalproyect.myanimedictionary.data.database.FavouriteAnimeViewModel
import com.finalproyect.myanimedictionary.data.v4.Data
import com.finalproyect.myanimedictionary.presentation.activities.MainActivity
import com.finalproyect.myanimedictionary.presentation.fragments.AnimeDetailsBottomSheet
import com.squareup.picasso.Picasso

class AnimeAdapter(
    private val parentActivity: MainActivity, private val animes: List<Data>
) : RecyclerView.Adapter<AnimeAdapter.CustomViewHolder>() {

    private lateinit var favouriteAnimeViewModel: FavouriteAnimeViewModel

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var alreadyExists:Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.anime_item_layout, parent, false)
        parent.context
        favouriteAnimeViewModel = ViewModelProvider(parentActivity)[FavouriteAnimeViewModel::class.java]
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val anime = animes[position]
        val view = holder.itemView
        holder.itemView.context

        val favouriteList: MutableList<Int> = mutableListOf()
        val name = view.findViewById<TextView>(R.id.name)
        val image = view.findViewById<ImageView>(R.id.image)
        val favourite = view.findViewById<ImageButton>(R.id.favourite)

        name.text = anime.title
        val title = anime.title
        Picasso.get().load(anime.images.webp.imageUrl).into(image)
        val img = anime.images.webp.imageUrl

        view.setOnClickListener {
            AnimeDetailsBottomSheet(anime).apply {
                show(parentActivity.supportFragmentManager, "AnimeDetailsBottomSheet")
            }
        }

        favourite.setOnClickListener {
            insertDataToDatabase(title, img)

            if (favouriteList.isNullOrEmpty()) {
                favouriteList.add(anime.malId)
            } else {
                for (item in favouriteList) {
                    alreadyExists = item == anime.malId
                }
            }
            if (!alreadyExists) {
                favourite.setImageResource(R.drawable.ic_favourite3)
            } else {
                favourite.setImageResource(R.drawable.ic_favourite2)
                alreadyExists= false
                favouriteList.remove(anime.malId)
            }
            }
        }

    private fun insertDataToDatabase(title:String, img:String) {

        val favouriteAnime = FavouriteAnime(0, title, img)
        favouriteAnimeViewModel.insertFavouriteAnime(favouriteAnime)
        Toast.makeText(parentActivity, "Added to favourites", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
            return animes.size
        }
    }