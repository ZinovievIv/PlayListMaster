package com.example.playlistmaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

class MediaLibraryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkTheme()
        setContentView(R.layout.activity_media_library)
    }

    private fun checkTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_PlaylistMasterNight)
        } else {
            setTheme(R.style.Theme_PlaylistMaster)
        }
    }

}