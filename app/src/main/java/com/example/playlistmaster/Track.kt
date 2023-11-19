package com.example.playlistmaster

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.time.Duration

data class Track(
    @SerializedName("trackName")
    val trackName: String,
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("trackTimeMillis")
    val trackTimeMillis: Long,
    @SerializedName("artworkUrl100")
    val imageAlbumURL: String,
    @SerializedName("trackId")
    val trackId: Long
) {
    fun getFormattedTrackTime(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
    }
}