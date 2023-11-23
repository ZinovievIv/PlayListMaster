package com.example.playlistmaster

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson

const val SETTINGS = "settings"
const val THEME_SWITCH_POSITION = "theme_switch_position"

class App: Application() {
    private var darkTheme = false


    override fun onCreate() {
        super.onCreate()
        val sharePref = getSharedPreferences(SETTINGS, MODE_PRIVATE)
        val sharedPreferencesHistory = getSharedPreferences(HISTORY_TRACKS, MODE_PRIVATE)
        SearchHistory.readSharedPref(sharedPreferencesHistory)
        darkTheme = sharePref.getBoolean(THEME_SWITCH_POSITION, false)
        switchTheme(darkTheme)

    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    fun read(sharedPreferences: SharedPreferences) : Array<Track> {
        val json = sharedPreferences.getString(HISTORY_TRACKS_KEY, null) ?: return emptyArray()
        return  Gson().fromJson(json, Array<Track>::class.java)
    }

    fun write(sharedPreferences: SharedPreferences, array: Array<Track>) {
        val  json = Gson().toJson(array)
        sharedPreferences.edit()
            .putString(HISTORY_TRACKS_KEY, json)
            .apply()
    }
}