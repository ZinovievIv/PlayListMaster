package com.example.playlistmaster

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SearchActivity : AppCompatActivity() {
    private var trackList = mutableListOf<Track>()
    private var searchText = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkTheme()
        setContentView(R.layout.activity_search)

        trackList.add(Track("Smells Like Teen Spirit", "Nirvana", "5:01"))
        trackList.add(Track("Billie Jean", "Michael Jackson", "4:35"))
        trackList.add(Track("Stayin' Alive", "Bee Gees", "4:10"))
        trackList.add(Track("Whole Lotta Love", "Led Zeppelin", "5:33"))
        trackList.add(Track("Sweet Child O'Mine", "Guns N' Roses", "5:03"))

        val recycle = findViewById<RecyclerView>(R.id.recycleView)
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = TrackAdapter(trackList)


        val arrowBack = findViewById<ImageView>(R.id.arrow_back)
        val searchBar = findViewById<EditText>(R.id.searchBar)
        val clearButton = findViewById<ImageView>(R.id.clearButton)

        clearButton.setOnClickListener {
            searchBar.setText("")
            searchBar.hideKeyboard()
        }

        arrowBack.setOnClickListener {
            finish()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.isNullOrEmpty()){
                    clearButton.visibility = View.GONE
                } else {
                    clearButton.visibility = View.VISIBLE
                }
                searchText = p0.toString()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        }
        searchBar.addTextChangedListener(textWatcher)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PRODUCT_AMOUNT, searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val searchBar = findViewById<EditText>(R.id.searchBar)
        searchBar.setText(savedInstanceState.getString(PRODUCT_AMOUNT))
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun checkTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_PlaylistMasterNight)
        } else {
            setTheme(R.style.Theme_PlaylistMaster)
        }
    }

    companion object {
        private const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
    }

}

