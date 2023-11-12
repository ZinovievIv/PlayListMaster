package com.example.playlistmaster

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import retrofit2.Retrofit
import kotlin.math.roundToInt
import kotlin.time.Duration


class TracksViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

    private val trackNameView: TextView
    private val artistNameView: TextView
    private val trackTimeView: TextView
    private val imageAlbumURL: ImageView

    private val roundCornenRadius = 2.0f

    init {
        trackNameView = itemView.findViewById(R.id.trackName)
        artistNameView = itemView.findViewById(R.id.artistName)
        trackTimeView = itemView.findViewById(R.id.trackTime)
        imageAlbumURL = itemView.findViewById(R.id.imageAlbum)
    }

    fun bind(track: Track) {
        trackNameView.text = track.trackName
        artistNameView.text = track.artistName
        trackTimeView.text = track.getFormattedTrackTime()
        Glide.with(itemView)
            .load(track.imageAlbumURL)
            .transform(RoundedCorners(dpToPx(roundCornenRadius, context)))
            .placeholder(R.drawable.placeholder_track)
            .into(imageAlbumURL)
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics
        ).toInt()
    }
}