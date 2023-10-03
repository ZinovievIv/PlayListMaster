package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
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
