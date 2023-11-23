package com.example.playlistmaster
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

const val HISTORY_TRACKS = "history_tracks_list"
const val HISTORY_TRACKS_KEY = "history_tracks_key"
private const val MAX_HISTORY_SIZE = 10

object SearchHistory {

    var historyTracksList = mutableListOf<Track>()

    private fun historyTracksToJson(): String {
        return Gson().toJson(historyTracksList)
    }

    private fun historyTracksFromJson(json: String): MutableList<Track> {
        return Gson().fromJson(json, Array<Track>::class.java).toMutableList()
    }

    fun readSharedPref(sharedPreferences: SharedPreferences) {
        val getInfo = sharedPreferences.getString(HISTORY_TRACKS_KEY, "")
        historyTracksList = if (getInfo.isNullOrEmpty()) {
            mutableListOf<Track>()
        } else {
            getInfo?.let { historyTracksFromJson(it) }!!
        }
    }

    fun writeSharedPref(sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putString(HISTORY_TRACKS_KEY, historyTracksToJson()).apply()
    }

    fun addTrack(newTrack: Track) {
        if (historyTracksList.contains(newTrack)){
            historyTracksList.remove(newTrack)
            historyTracksList.add(newTrack)
        } else if (historyTracksList.size == MAX_HISTORY_SIZE) {
            historyTracksList.remove(historyTracksList.first())
            historyTracksList.add(newTrack)
        } else {
            historyTracksList.add(newTrack)
        }
    }

    fun clearList() {
        historyTracksList.clear()
    }
}