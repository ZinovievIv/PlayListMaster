package com.example.playlistmaster

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class TracksViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

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
            .transform(RoundedCorners(2))
            .placeholder(R.drawable.placeholder)
            .into(imageAlbumURL)
    }
    fun convertPixelsToDp(context: Context, pixels: Int) =
        (pixels / context.resources.displayMetrics.density).toInt()
}