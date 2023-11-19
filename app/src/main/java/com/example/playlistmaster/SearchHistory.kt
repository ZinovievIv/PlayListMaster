package com.example.playlistmaster
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

const val HISTORYTRACKS = "history_tracks_list"
const val HISTORYTRACKSKEY = "history_tracks_key"

object SearchHistory {

    var historyTracksList = mutableListOf<Track>()

    private fun historyTracksToJson() : String {
        return Gson().toJson(historyTracksList)
    }

    private fun historyTracksFromJson(json : String) : Array<Track>? {
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    fun readSharedPref(sharedPreferences: SharedPreferences) {
        var getInfo = sharedPreferences.getString(HISTORYTRACKSKEY, "")
        Log.i("Gettt", "Get + ${getInfo.toString()}")
        if (getInfo.isNullOrEmpty()){
            historyTracksList = mutableListOf<Track>()
        } else {
            historyTracksList = historyTracksFromJson(getInfo)?.toMutableList()!!
        }
    }

    fun writeSharedPref(sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putString(HISTORYTRACKSKEY, historyTracksToJson()).apply()
        Log.i("SharedPreference", "Записан список истории треков ${historyTracksList}")
    }

    fun addTrack(newTrack: Track) {
        historyTracksList.add(newTrack)
    }

}