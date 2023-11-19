package com.example.playlistmaster
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

const val HISTORYTRACKS = "history_tracks_list"
const val HISTORYTRACKSKEY = "history_tracks_list"

object SearchHistory {

    val historyTracksList = ArrayList<Track>()

    private fun historyTracksToJson() : String {
        return Gson().toJson(historyTracksList)
    }

    fun historyTracksFromJson(json : String) : Array<Track> {
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    fun writeSharedPref(sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putString(HISTORYTRACKS, historyTracksToJson()).apply()
        Log.i("SharedPreference", "Записан список истории треков")
    }

    fun addTrack(newTrack: Track) {
        historyTracksList.add(newTrack)
    }

}