package com.example.playlistmaster

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TracksAdapter(private val listTracks: ArrayList<Track>, private val context: Context) :
    RecyclerView.Adapter<TracksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_for_recycle_view, parent,false)
        return TracksViewHolder(view, context)
    }
    override fun getItemCount() = listTracks.size
    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(listTracks[position])
    }

    fun updateTracks(newTracks: List<Track>) {
        listTracks.clear()
        listTracks.addAll(newTracks)
        notifyDataSetChanged()
    }
}