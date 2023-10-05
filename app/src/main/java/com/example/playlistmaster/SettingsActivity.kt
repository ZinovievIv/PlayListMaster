package com.example.playlistmaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val arrowBack = findViewById<ImageButton>(R.id.arrow_back)

        val imageButtonClickListener: View.OnClickListener = View.OnClickListener { v ->
            when (v?.id) {
                R.id.arrow_back -> {
                        val arrowBackIntent = Intent(this@SettingsActivity, MainActivity::class.java)
                        startActivity(arrowBackIntent)
                }
            }
        }
        arrowBack.setOnClickListener(imageButtonClickListener)
    }
}
