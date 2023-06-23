package com.finalproyect.myanimedictionary.presentation.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.GridLayoutManager
import com.finalproyect.myanimedictionary.data.adapters.EpisodeAdapter
import com.finalproyect.myanimedictionary.data.v4.Data
import com.finalproyect.myanimedictionary.databinding.AnimeDetailsBottomSheetLayoutBinding
import com.finalproyect.myanimedictionary.presentation.activities.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class AnimeDetailsBottomSheet(
    private val anime: Data
) : BottomSheetDialogFragment() {

    lateinit var binding: AnimeDetailsBottomSheetLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val animeInflater = LayoutInflater.from(requireContext())
        binding = AnimeDetailsBottomSheetLayoutBinding.inflate(animeInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numEpisode: MutableList<Int> = mutableListOf()

        for (i in 1..anime.episodes) {
            numEpisode.add(i)
        }

        binding.apply {
            Picasso.get().load(anime.images.jpg.imageUrl).into(image)
            name.text = anime.title
            rating.text = anime.score.toString()
            pgRating.text = "PG: ${anime.rating}"
            episodes.text = (if (anime.episodes == 0) {
                "Episodes: Is on air"
            } else {
                "Episodes: ${anime.episodes} episodes"
            }).toString()
            synopsis.text = anime.synopsis

            dates.text = "Duration: ${anime.duration}"

            knowMoreText.setOnClickListener {
                openCustomTab(activity as AppCompatActivity, Uri.parse(anime.url))
            }
            binding.trailerButtom.setOnClickListener {
                if (!anime.trailer.youtubeId.isNullOrEmpty()) {
                    openYoutubeStandAlonePlayer(anime.trailer.youtubeId, true, true)
                } else {
                    Toast.makeText(context, "There is no trailer", Toast.LENGTH_SHORT).show()
                }
            }

            binding.episodesRecyclerView.adapter = EpisodeAdapter(activity as MainActivity, numEpisode, anime)
            binding.episodesRecyclerView.layoutManager = GridLayoutManager(context, 1)
        }
    }

    private fun openCustomTab(activity: AppCompatActivity, url: Uri) {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)
        builder.build().launchUrl(activity, url)
    }

    private fun openYoutubeStandAlonePlayer(VideoID: String, autoplay: Boolean = false, lightMode: Boolean = false) {
        val intent = YouTubeStandalonePlayer.createVideoIntent(
            requireActivity(),
            "AIzaSyBdvXk6LRlu0VuEebjs4A3a3eXqHXhX6cM", //Developer Api Key
            VideoID,
            0, //startIndex
            autoplay,
            lightMode
        )
        startActivity(intent)
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(date: String): String {
        return if (date.contains("-")) {
            val newDate = date.substring(0, date.lastIndexOf("-"))
            val _date = SimpleDateFormat("yyyy-MM").parse(newDate)
            SimpleDateFormat("MMM yyyy").format(_date)
        } else {
            date
        }
    }

}