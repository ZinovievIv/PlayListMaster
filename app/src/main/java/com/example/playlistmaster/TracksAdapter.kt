package com.example.playlistmaster

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TracksAdapter(private val listTracks: ArrayList<Track>,
                    private val context: Context) :
    RecyclerView.Adapter<TracksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_for_recycle_view, parent,false)
        return TracksViewHolder(view, context)
    }
    override fun getItemCount() = listTracks.size
    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val track = listTracks[position]
        holder.bind(track)
        holder.itemView.setOnClickListener {
            SearchHistory.addTrack(track)
            Log.i("Track", "В адаптере ${SearchHistory.historyTracksList}")
        }
    }

    fun updateTracks(newTracks: List<Track>) {
        listTracks.clear()
        listTracks.addAll(newTracks)
        notifyDataSetChanged()
    }

    fun clearListTracks(listTracks: ArrayList<Track>) {
        listTracks.clear()
        notifyDataSetChanged()
    }


}