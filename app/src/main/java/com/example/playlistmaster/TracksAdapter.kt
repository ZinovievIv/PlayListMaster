package com.example.playlistmaster

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
class TracksAdapter(private val listTracks: ArrayList<Track>, private val context: Context) :
    RecyclerView.Adapter<TracksViewHolder>() {

    val pref = TrackSharedPreferences()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_for_recycle_view, parent,false)
        return TracksViewHolder(view, context)
    }
    override fun getItemCount() = listTracks.size
    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(listTracks[position])
        holder.itemView.setOnClickListener {
            Log.i("TrackAdapter", "Click on track in search")
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