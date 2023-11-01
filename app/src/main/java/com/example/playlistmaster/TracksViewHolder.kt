package com.example.playlistmaster

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text


class TracksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val trackNameView : TextView
    private val artistNameView : TextView
    private val trackTimeView : TextView
    private val imageAlbumURL : ImageView

    init {
        trackNameView = itemView.findViewById(R.id.trackName)
        artistNameView = itemView.findViewById(R.id.artistName)
        trackTimeView = itemView.findViewById(R.id.trackTime)
        imageAlbumURL = itemView.findViewById(R.id.imageAlbum)
    }

    fun bind(track: Track) {
        trackNameView.text = track.trackName
        artistNameView.text = track.artistName
        trackTimeView.text = track.trackTime
        Glide.with(itemView)
            .load(track.imageAlbumURL)
            .placeholder(R.drawable.loading_svgrepo_com)
            .into(imageAlbumURL)
    }
}