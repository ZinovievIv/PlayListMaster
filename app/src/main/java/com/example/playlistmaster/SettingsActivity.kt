package com.example.playlistmaster

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val buttonArrowBack = findViewById<ImageView>(R.id.arrow_back)
        val buttonShare = findViewById<ImageView>(R.id.button_share)
        val buttonSupport = findViewById<ImageView>(R.id.button_support)
        val buttonUserAgreement = findViewById<ImageView>(R.id.user_agreement)

        val imageButtonClickListener: View.OnClickListener = View.OnClickListener { v ->
            when (v?.id) {
                R.id.arrow_back -> {
                    val arrowBackIntent = Intent(this@SettingsActivity, MainActivity::class.java)
                    startActivity(arrowBackIntent)
                }
                R.id.button_share -> {
                    val browseIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://practicum.yandex.ru/android-developer/")
                    )
                    startActivity(browseIntent)
                }
                R.id.button_support -> {
                    val message = getString(R.string.templeteTextMessage)
                    val messageInTheme = getString(R.string.templeteThemeMessage)
                    val shareIntent = Intent(Intent.ACTION_SENDTO)
                    shareIntent.data = Uri.parse(getString(R.string.eMailAddresTechSupport))
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, messageInTheme)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, message)
                    startActivity(shareIntent)
                }
                R.id.user_agreement -> {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.offer)))
                    startActivity(browserIntent)
                }
            }
        }
        buttonShare.setOnClickListener(imageButtonClickListener)
        buttonArrowBack.setOnClickListener(imageButtonClickListener)
        buttonSupport.setOnClickListener(imageButtonClickListener)
        buttonUserAgreement.setOnClickListener(imageButtonClickListener)
    }
}
