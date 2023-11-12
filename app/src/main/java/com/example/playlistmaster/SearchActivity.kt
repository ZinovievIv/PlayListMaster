package com.example.playlistmaster

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {

    private var trackList = ArrayList<Track>()
    private var searchText = ""
    private val itunesBaseUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val itunesService = retrofit.create(ItunesApi::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkTheme()
        setContentView(R.layout.activity_search)


        val arrowBack = findViewById<ImageView>(R.id.arrow_back)
        val searchBar = findViewById<EditText>(R.id.searchBar)
        val clearButton = findViewById<ImageView>(R.id.clearButton)
        val imagePlaceHolderNoResult = findViewById<ImageView>(R.id.imagePlaceHolderNoResults)
        val textPlaceHolderNoResult = findViewById<TextView>(R.id.textPlaceHolderNoResults)
        val buttonPlaceHolderNoNetwork = findViewById<Button>(R.id.buttonUpdate)
        val textPlaceHolderNoNetwork = findViewById<TextView>(R.id.textPlaceHolderNoNetwork)
        val imagePlaceHolderNoNetwork = findViewById<ImageView>(R.id.imagePlaceHolderNoNetwork)

        val recycle = findViewById<RecyclerView>(R.id.recycleView)
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = TracksAdapter(trackList, this)

        fun request() {
            searchText = searchBar.text.toString()
            if (searchText.isNotEmpty()) {
                itunesService.findTrack(searchText)
                    .enqueue(object : Callback<TrackResponse> {
                        override fun onResponse(
                            call: Call<TrackResponse>,
                            response: Response<TrackResponse>
                        ) {
                            if (response.code() == 200) {
                                Log.i("Network", "${response.code()}")
                                if (response.body()?.results?.isNotEmpty() == true) {
                                    recycle.adapter?.let {
                                        if (it is TracksAdapter) {
                                            it.updateTracks(response.body()?.results!!)
                                        }
                                    }
                                    Log.i("Network", "$trackList")
                                    imagePlaceHolderNoResult.visibility = View.INVISIBLE
                                    textPlaceHolderNoResult.visibility = View.INVISIBLE
                                    buttonPlaceHolderNoNetwork.visibility = View.INVISIBLE
                                    textPlaceHolderNoNetwork.visibility = View.INVISIBLE
                                    imagePlaceHolderNoNetwork.visibility = View.INVISIBLE
                                    recycle.visibility = View.VISIBLE
                                }
                                if (trackList.isEmpty()) {
                                    imagePlaceHolderNoResult.visibility = View.VISIBLE
                                    textPlaceHolderNoResult.visibility = View.VISIBLE
                                    buttonPlaceHolderNoNetwork.visibility = View.INVISIBLE
                                    textPlaceHolderNoNetwork.visibility = View.INVISIBLE
                                    imagePlaceHolderNoNetwork.visibility = View.INVISIBLE
                                    recycle.visibility = View.INVISIBLE
                                } else {
                                }
                            }
                        }

                        override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                            buttonPlaceHolderNoNetwork.visibility = View.VISIBLE
                            textPlaceHolderNoNetwork.visibility = View.VISIBLE
                            imagePlaceHolderNoNetwork.visibility = View.VISIBLE
                            imagePlaceHolderNoResult.visibility = View.INVISIBLE
                            textPlaceHolderNoResult.visibility = View.INVISIBLE
                            recycle.visibility = View.INVISIBLE
                        }
                    })
            }
        }


        buttonPlaceHolderNoNetwork.setOnClickListener {
            request()
        }

        searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                request()
            }
            false
        }

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

  //private fun visibilityPlaceHolders(truble: String) {
  //    when (truble) {
  //        "NoNetwork" -> {
  //            buttonPlaceHolderNoNetwork.visibility = View.VISIBLE
  //            textPlaceHolderNoNetwork.visibility = View.VISIBLE
  //            imagePlaceHolderNoNetwork.visibility = View.VISIBLE
  //            imagePlaceHolderNoResult.visibility = View.INVISIBLE
  //            textPlaceHolderNoResult.visibility = View.INVISIBLE
  //            recycle.visibility = View.INVISIBLE
  //        }
  //        "NoResult" -> {
  //            imagePlaceHolderNoResult.visibility = View.VISIBLE
  //            textPlaceHolderNoResult.visibility = View.VISIBLE
  //            buttonPlaceHolderNoNetwork.visibility = View.INVISIBLE
  //            textPlaceHolderNoNetwork.visibility = View.INVISIBLE
  //            imagePlaceHolderNoNetwork.visibility = View.INVISIBLE
  //            recycle.visibility = View.INVISIBLE
  //        }
  //        "UpdateSearch" -> {
  //            imagePlaceHolderNoResult.visibility = View.INVISIBLE
  //            textPlaceHolderNoResult.visibility = View.INVISIBLE
  //            buttonPlaceHolderNoNetwork.visibility = View.INVISIBLE
  //            textPlaceHolderNoNetwork.visibility = View.INVISIBLE
  //            imagePlaceHolderNoNetwork.visibility = View.INVISIBLE
  //            recycle.visibility = View.VISIBLE
  //        }
  //    }
  //}


    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun checkTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_PlaylistMasterNight)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            setTheme(R.style.Theme_PlaylistMaster)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        private const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
    }

}

