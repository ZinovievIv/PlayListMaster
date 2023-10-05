package com.example.playlistmaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
                val buttonSearchIntent =Intent(this@MainActivity, MediaLibraryActivity::class.java)
                startActivity(buttonSearchIntent)
        }
        buttonSettings.setOnClickListener {
                val buttonSettingsIntent =Intent(this@MainActivity, MediaLibraryActivity::class.java)
                startActivity(buttonSettingsIntent)
        }
    }
}