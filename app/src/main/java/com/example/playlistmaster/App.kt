package com.example.playlistmaster

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson

const val SETTINGS = "settings"
const val THEME_SWITCH_POSITION = "theme_switch_position"
const val HISTORY_SEARCH = "search_history"

class App: Application() {
    private var darkTheme = false


    override fun onCreate() {
        super.onCreate()
        val sharePref = getSharedPreferences(SETTINGS, MODE_PRIVATE)
        val sharedPreferencesHistory = getSharedPreferences(HISTORY_SEARCH, MODE_PRIVATE)
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
}