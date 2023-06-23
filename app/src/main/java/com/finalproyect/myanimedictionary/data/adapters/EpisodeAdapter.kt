package com.finalproyect.myanimedictionary.data.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.finalproyect.myanimedictionary.R
import com.finalproyect.myanimedictionary.data.v4.Data
import com.finalproyect.myanimedictionary.presentation.activities.MainActivity

class EpisodeAdapter(private val parentActivity: MainActivity, private val numEpisode: MutableList<Int>, private val data: Data) : RecyclerView.Adapter<EpisodeAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.episode_item_layout, parent, false)
        parent.context
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val episode = numEpisode[position]
        val view = holder.itemView
        holder.itemView.context

        val episodeName = view.findViewById<TextView>(R.id.button)

        episodeName.text = "Episode ${episode.toString()}"

        val url1 = data.title.replace(' ', '-')
        val url2 = url1.replace(":", "")
        val url3 = url2.replace(";", "")
        val url4 = url3.replace(";","")
        val url5 = url4.replace("º","")
        val url6 = url5.replace("'","")
        val url7 = url6.replace(".", "")
        val url8 = url7.replace("(", "")
        val url9 = url8.replace(")", "")
        val url10 = url9.replace("!", "")

        episodeName.setOnClickListener {
            openCustomTab(parentActivity, Uri.parse("https://www3.animeflv.net/ver/${url10}-${episode}"))
        }
    }

    override fun getItemCount(): Int {
        return numEpisode.size
    }

    private fun openCustomTab(activity: AppCompatActivity, url: Uri) {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)
        builder.build().launchUrl(activity, url)
    }
}