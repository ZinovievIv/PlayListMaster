package com.example.playlistmaster
import android.content.SharedPreferences
import com.google.gson.Gson

const val HISTORYTRACKSKEY = "history_tracks_list"

class SearchHistory {
    class TrackSharedPreferences {
        fun read(sharedPreferences: SharedPreferences) : Array<Track> {
            val json = sharedPreferences.getString(HISTORYTRACKSKEY, null) ?: return emptyArray()
            return  Gson().fromJson(json, Array<Track>::class.java)
        }

        fun write(sharedPreferences: SharedPreferences, array: Array<Track>) {
            val  json = Gson().toJson(array)
            sharedPreferences.edit()
                .putString(HISTORYTRACKSKEY, json)
                .apply()
        }
    }


}