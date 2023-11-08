package com.example.playlistmaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        checkTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        val buttonMedia = findViewById<Button>(R.id.buttonMedia)
        val buttonSettings = findViewById<Button>(R.id.buttonSettings)

        buttonMedia.setOnClickListener{
            val buttonMediaIntent =Intent(this@MainActivity, MediaLibraryActivity::class.java)
            startActivity(buttonMediaIntent)
        }

        buttonSearch.setOnClickListener{
                val buttonSearchIntent =Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(buttonSearchIntent)
        }
        buttonSettings.setOnClickListener {
                val buttonSettingsIntent =Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(buttonSettingsIntent)
        }
    }

    private fun checkTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_PlaylistMasterNight)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            setTheme(R.style.Theme_PlaylistMaster)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}