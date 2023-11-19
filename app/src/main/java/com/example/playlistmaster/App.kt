package com.example.playlistmaster

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate

const val SETTINGS = "settings"
const val THEME_SWITCH_POSITION = "theme_switch_position"
const val HISTORY_SEARCH = "search_history"

class App: Application() {
    private var darkTheme = false


    override fun onCreate() {
        super.onCreate()
        val sharePref = getSharedPreferences(SETTINGS, MODE_PRIVATE)
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