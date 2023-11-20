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

    private fun historyTracksToJson(): String {
        return Gson().toJson(historyTracksList)
    }

    private fun historyTracksFromJson(json: String): MutableList<Track> {
        return Gson().fromJson(json, Array<Track>::class.java).toMutableList()
    }

    fun readSharedPref(sharedPreferences: SharedPreferences) {
        val getInfo = sharedPreferences.getString(HISTORYTRACKSKEY, "")
        if (getInfo.isNullOrEmpty()) {
            historyTracksList = mutableListOf<Track>()
            Log.i("Track", "Восстанавливаем пустой список ${historyTracksList}")
        }  else {
            historyTracksList = getInfo?.let { historyTracksFromJson(it) }!!
            Log.i("Track", "Восстанавливаем список ${historyTracksList}")
        }
         //e historyTracksFromJson(getInfo)?.toMutableList()!!

                //}
    }

    fun writeSharedPref(sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putString(HISTORYTRACKSKEY, historyTracksToJson()).apply()
    }

    fun addTrack(newTrack: Track) {
        //if (historyTracksList.isNotEmpty()) {
        //    for (track in historyTracksList) {
        //        Log.i("Track", "${track.trackId}")
                    //                    Log.i("Track", "${newTrack.trackId}")
                    //                    if (newTrack.trackId == track.trackId) {
        //            Log.i("Track", "Не добавлено")
                            //                            } else {
        //            historyTracksList.add(newTrack)
                            //                                Log.i("Track", "Добавлен")
                            //                            }
                    //                }
                //        } else {
            historyTracksList.add(newTrack)
                //}
    }
}