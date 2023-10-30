package com.example.playlistmaster

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class SettingsActivity : AppCompatActivity() {
    private var positionSwitch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        checkTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val themeSwitch = findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.themeSwitch)
        themeSwitch.isChecked = positionSwitch
        val buttonArrowBack = findViewById<ImageView>(R.id.arrow_back)
        val buttonShare = findViewById<ImageView>(R.id.button_share)
        val buttonSupport = findViewById<ImageView>(R.id.button_support)
        val buttonUserAgreement = findViewById<ImageView>(R.id.user_agreement)

        val imageButtonClickListener: View.OnClickListener = View.OnClickListener { v ->
            when (v?.id) {
                R.id.arrow_back -> {
                    finish()
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
                R.id.themeSwitch -> {
                    if (themeSwitch.isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
        buttonShare.setOnClickListener(imageButtonClickListener)
        buttonArrowBack.setOnClickListener(imageButtonClickListener)
        buttonSupport.setOnClickListener(imageButtonClickListener)
        buttonUserAgreement.setOnClickListener(imageButtonClickListener)
        themeSwitch.setOnClickListener(imageButtonClickListener)
    }


    private fun checkTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_PlaylistMasterNight)
            positionSwitch = true
        } else {
            setTheme(R.style.Theme_PlaylistMaster)
            positionSwitch = false
        }
    }

}
