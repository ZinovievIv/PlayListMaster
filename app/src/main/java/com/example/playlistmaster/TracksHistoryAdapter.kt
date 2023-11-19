package com.example.playlistmaster

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
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
        holder.bind(historyTracksList[position])
        holder.itemView.setOnClickListener {
        }
    }

    fun updateTracks(newTracks: List<Track>) {
        historyTracksList.clear()
        historyTracksList.addAll(newTracks)
        notifyDataSetChanged()
    }
}