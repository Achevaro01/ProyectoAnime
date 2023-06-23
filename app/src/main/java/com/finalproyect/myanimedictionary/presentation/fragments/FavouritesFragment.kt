package com.finalproyect.myanimedictionary.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.finalproyect.myanimedictionary.data.adapters.FavouritesAdapter
import com.finalproyect.myanimedictionary.data.database.FavouriteAnimeViewModel
import com.finalproyect.myanimedictionary.databinding.FragmentFavouritesBinding
import com.finalproyect.myanimedictionary.presentation.activities.MainActivity

class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding

    private lateinit var favouriteAnimeViewModel: FavouriteAnimeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(layoutInflater)

        // Recyclerview
        val adapter = FavouritesAdapter(activity as MainActivity)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        // UserViewModel
        favouriteAnimeViewModel = ViewModelProvider(this)[FavouriteAnimeViewModel::class.java]
        favouriteAnimeViewModel.readAllData.observe(viewLifecycleOwner, Observer { favouriteAnime ->
            adapter.setData(favouriteAnime)
        })
        return binding.root
    }
}