package com.example.playlistmaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaster.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMedia.setOnClickListener{
            val buttonMediaIntent =Intent(this@MainActivity, MediaLibraryActivity::class.java)
            startActivity(buttonMediaIntent)
        }
        binding.buttonSearch.setOnClickListener{
                val buttonSearchIntent =Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(buttonSearchIntent)
        }
        binding.buttonSettings.setOnClickListener {
                val buttonSettingsIntent =Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(buttonSettingsIntent)
        }
    }
}