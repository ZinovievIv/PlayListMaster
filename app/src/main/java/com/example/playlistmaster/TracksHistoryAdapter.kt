package com.example.playlistmaster

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TracksHistoryAdapter(private val historyTracksList: MutableList<Track>,
                           private val context: Context) : RecyclerView.Adapter<TracksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.track_for_recycle_view,parent,false)
        return TracksViewHolder(view, context)
    }

    override fun getItemCount(): Int = historyTracksList.size

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val track = historyTracksList[position]
        holder.bind(track)
        holder.itemView.setOnClickListener {
            val intent = Intent(this.context, PlayerActivity::class.java)
            intent.putExtra("track_info", track)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    fun updateAdapter() {
        historyTracksList.clear()
        historyTracksList.addAll(SearchHistory.historyTracksList)
        notifyDataSetChanged()
    }

    fun clearListTracks() {
        historyTracksList.clear()
        SearchHistory.historyTracksList.clear()
        notifyDataSetChanged()
    }

}