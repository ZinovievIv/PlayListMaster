package com.example.playlistmaster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter(private val listTracks: List<Track> ) :
    RecyclerView.Adapter<TracksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_for_recycle_view, parent,false)
        return TracksViewHolder(view)
    }

    override fun getItemCount() = listTracks.size

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(listTracks[position])
    }


}