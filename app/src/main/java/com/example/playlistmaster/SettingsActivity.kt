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
import androidx.appcompat.app.AppCompatDelegate.NightMode
import com.example.playlistmaster.databinding.ActivitySettingsBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var themeSwitch: SwitchMaterial
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        themeSwitch = binding.themeSwitch
        val sharPref = getSharedPreferences(SETTINGS, MODE_PRIVATE)
        checkTheme()
        setContentView(binding.root)

        val imageButtonClickListener: View.OnClickListener = View.OnClickListener { v ->
            when (v?.id) {
                R.id.arrow_back -> {

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
            }
        }
        binding.buttonShare.setOnClickListener(imageButtonClickListener)
        binding.arrowBack.setOnClickListener(imageButtonClickListener)
        binding.buttonSupport.setOnClickListener(imageButtonClickListener)
        binding.userAgreement.setOnClickListener(imageButtonClickListener)
        binding.themeSwitch.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
            sharPref.edit().putBoolean(THEME_SWITCH_POSITION, checked).apply()
            Log.i("Theme", "$checked")
        }
    }


    private fun checkTheme() {
        themeSwitch.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }
}

