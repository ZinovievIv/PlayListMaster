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
import com.example.playlistmaster.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingsBinding
    private var positionSwitch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharePref = getSharedPreferences(SETTINGS, MODE_PRIVATE)
        (applicationContext as App).switchTheme(sharePref.getBoolean(THEME_SWITCH_POSITION, true))
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.themeSwitch.isChecked = positionSwitch

        val imageButtonClickListener: View.OnClickListener = View.OnClickListener { v ->
            when (v?.id) {
                R.id.arrow_back -> {
                    finish()
                }

                R.id.button_share -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, getString(R.string.messageShare))
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
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
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.offer)))
                    startActivity(browserIntent)
                }
                //R.id.themeSwitch -> {
                //    if (binding.themeSwitch.isChecked) {
                //        sharePref.edit().putBoolean(THEME_SWITCH_POSITION, true).apply()
//
                //    } else {
                //        sharePref.edit().putBoolean(THEME_SWITCH_POSITION, false).apply()
                //        //    checkTheme()
                //        //    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                //        //} else {
                //        //    checkTheme()
                //        //    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                //        //}
                //    }
                //}
            }
        }
        binding.buttonShare.setOnClickListener(imageButtonClickListener)
        binding.arrowBack.setOnClickListener(imageButtonClickListener)
        binding.buttonSupport.setOnClickListener(imageButtonClickListener)
        binding.userAgreement.setOnClickListener(imageButtonClickListener)
        binding.themeSwitch.setOnCheckedChangeListener { switcher, checked ->
            Log.i("TEST", "$checked")
                sharePref.edit().putBoolean(THEME_SWITCH_POSITION, checked).apply()
                (applicationContext as App).switchTheme(checked)
        }
    }


    private fun checkTheme() {
        positionSwitch = if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_PlaylistMasterNight)
            true
        } else {
            setTheme(R.style.Theme_PlaylistMaster)
            false
        }
    }
}
