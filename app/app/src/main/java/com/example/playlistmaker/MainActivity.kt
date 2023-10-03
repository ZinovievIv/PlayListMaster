package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        val buttonMedia = findViewById<Button>(R.id.buttonMedia)
        val buttonSettings = findViewById<Button>(R.id.buttonSettings)
        val buttonClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                when(v?.id){
                    R.id.buttonSearch -> {
                        val mediaLibraryIntent = Intent(this@MainActivity, MediaLibraryActivity::class.java)
                        startActivity(mediaLibraryIntent)
                    }
                    R.id.buttonSettings -> {
                        val buttonSettingsIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                        startActivity(buttonSettingsIntent)
                    }
                }
            }
        }

        buttonMedia.setOnClickListener{
            val buttonMediaIntent = Intent(this@MainActivity, MediaLibraryActivity::class.java)
            startActivity(buttonMediaIntent)
        }

        buttonSearch.setOnClickListener(buttonClickListener)
        buttonSettings.setOnClickListener(buttonClickListener)



        //buttonMedia.setOnClickListener {
         //   val mediaLibraryIntent = Intent(this@MainActivity, MediaLibraryActivity::class.java)
         //   startActivity(mediaLibraryIntent)
        //}

        //buttonSettings.setOnClickListener {
        //    val buttonSettingsIntent = Intent(this@MainActivity, SettingsActivity::class.java)
        //    startActivity(buttonSettingsIntent)
        //}

    }
}