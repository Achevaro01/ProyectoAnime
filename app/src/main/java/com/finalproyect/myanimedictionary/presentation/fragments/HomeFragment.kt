package com.finalproyect.myanimedictionary.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.finalproyect.myanimedictionary.data.adapters.AnimeAdapter
import com.finalproyect.myanimedictionary.data.service.AnimeService
import com.finalproyect.myanimedictionary.data.v4.AnimeSearch
import com.finalproyect.myanimedictionary.data.v4.v4Prove
import com.finalproyect.myanimedictionary.databinding.FragmentHomeBinding
import com.finalproyect.myanimedictionary.presentation.activities.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding

    private var animeService = AnimeService.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {

            val call = animeService.getTopAnimes()

            call.enqueue(object : Callback<v4Prove> {

                override fun onResponse(call: Call<v4Prove>, response: Response<v4Prove>) {
                    val top = response.body()?.data
                    if ((activity == null) || (top.isNullOrEmpty())) {
                    } else {
                        binding.animeRecyclerView.adapter = AnimeAdapter(activity as MainActivity, top)
                        binding.animeRecyclerView.layoutManager = GridLayoutManager(context, 3)
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                }

                override fun onFailure(call: Call<v4Prove>, t: Throwable) {
                    showError()
                }
            })

            binding.animeSearch.setOnQueryTextListener(this@HomeFragment)
        }
    }

    private fun showError() {
        Toast.makeText(context, "No results", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        if (!p0.isNullOrEmpty()) {
            val callSearchedAnime = animeService.getSearchedAnime(p0)

            callSearchedAnime.enqueue(object : Callback<AnimeSearch> {

                override fun onResponse(
                    call: Call<AnimeSearch>,
                    response: Response<AnimeSearch>
                ) {
                    if (response.body() != null) {
                        val searchedAnimes = response.body()!!.data
                        binding.animeRecyclerView.adapter = AnimeAdapter(activity as MainActivity, searchedAnimes)
                        binding.animeRecyclerView.layoutManager = GridLayoutManager(context, 3)
                    }
                }

                override fun onFailure(call: Call<AnimeSearch>, t: Throwable) {
                    showError()
                }

            })
        } else {
            Toast.makeText(context, "The text is null or empty", Toast.LENGTH_SHORT).show()
        }
        binding.animeSearch.clearFocus()
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }
}