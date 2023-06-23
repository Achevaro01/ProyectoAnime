package com.finalproyect.myanimedictionary.data.adapters

import android.annotation.SuppressLint
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
import com.finalproyect.myanimedictionary.presentation.activities.MainActivity
import com.squareup.picasso.Picasso

class FavouritesAdapter(private val parentActivity: MainActivity): RecyclerView.Adapter<FavouritesAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    private var favouriteList = emptyList<FavouriteAnime>()

    private lateinit var favouriteAnimeViewModel: FavouriteAnimeViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        favouriteAnimeViewModel = ViewModelProvider(parentActivity)[FavouriteAnimeViewModel::class.java]
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.anime_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = favouriteList[position]
        val view = holder.itemView
        holder.itemView.context

        val name = view.findViewById<TextView>(R.id.name)
        val image = view.findViewById<ImageView>(R.id.image)
        val favourite = view.findViewById<ImageButton>(R.id.favourite)

        name.text = currentItem.title
        Picasso.get().load(currentItem.img).into(image)
        favourite.setImageResource(R.drawable.ic_favourite3)

        favourite.setOnClickListener {
            favourite.setImageResource(R.drawable.ic_favourite2)
            deleteDataFromDatabase(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(favouriteAnime: List<FavouriteAnime>) {
        this.favouriteList = favouriteAnime
        notifyDataSetChanged()
    }

    private fun deleteDataFromDatabase(currentItem: FavouriteAnime) {
        favouriteAnimeViewModel.deleteFavouriteAnime(currentItem)
        Toast.makeText(parentActivity, "Delete from favourites", Toast.LENGTH_SHORT).show()
    }
}