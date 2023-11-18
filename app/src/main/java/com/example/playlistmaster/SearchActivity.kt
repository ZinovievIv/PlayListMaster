package com.example.playlistmaster

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaster.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private var trackList = ArrayList<Track>()
    private var trackListHistory = ArrayList<Track>()
    private var searchText = ""
    private val itunesBaseUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val itunesService = retrofit.create(ItunesApi::class.java)
    private val adapter = TracksAdapter(trackList, this)
    private val adapterHistory = TracksHistoryAdapter(trackListHistory, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recycle = binding.recycleView
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = adapter

        val recyclerHostory = binding.recycleViewHistory
        recycle.layoutManager = LinearLayoutManager(this)
        recyclerHostory.adapter = adapterHistory

        fun request() {
            searchText = binding.searchBar.text.toString()
            if (searchText.isNotEmpty()) {
                itunesService.findTrack(searchText)
                    .enqueue(object : Callback<TrackResponse> {
                        override fun onResponse(
                            call: Call<TrackResponse>,
                            response: Response<TrackResponse>
                        ) {
                            if (response.code() == 200) {
                                Log.i("Network", "${response.code()}")
                                adapter.clearListTracks(trackList)
                                if (response.body()?.results?.isNotEmpty() == true) {
                                    recycle.adapter?.let {
                                        if (it is TracksAdapter) {
                                            it.updateTracks(response.body()?.results!!)
                                        }
                                    }
                                    Log.i("Network", "$trackList")
                                    visibilityPlaceHolders("UpdateSearch")
                                }
                                if (trackList.isEmpty()) {
                                    visibilityPlaceHolders("NoResult")
                                }
                            }
                        }

                        override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                            visibilityPlaceHolders("NoNetwork")
                        }
                    })
            }
        }

        binding.buttonUpdate.setOnClickListener {
            request()
        }

        binding.searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                request()
            }
            false
        }

        binding.clearButton.setOnClickListener {
            binding.searchBar.setText("")
            binding.searchBar.hideKeyboard()
            adapter.clearListTracks(trackList)
            visibilityPlaceHolders("UpdateSearch")
        }

        binding.arrowBack.setOnClickListener {
            finish()

        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    binding.clearButton.visibility = View.GONE
                } else {
                    binding.clearButton.visibility = View.VISIBLE
                }
                searchText = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding.searchBar.addTextChangedListener(textWatcher)
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

    private fun visibilityPlaceHolders(trouble: String) {
        with(binding) {
            when (trouble) {
                "NoNetwork" -> {
                    buttonUpdate.visibility = View.VISIBLE
                    textPlaceHolderNoNetwork.visibility = View.VISIBLE
                    imagePlaceHolderNoNetwork.visibility = View.VISIBLE
                    imagePlaceHolderNoResults.visibility = View.INVISIBLE
                    textPlaceHolderNoResults.visibility = View.INVISIBLE
                    recycleView.visibility = View.INVISIBLE
                }

                "NoResult" -> {
                    imagePlaceHolderNoResults.visibility = View.VISIBLE
                    textPlaceHolderNoResults.visibility = View.VISIBLE
                    buttonUpdate.visibility = View.INVISIBLE
                    textPlaceHolderNoNetwork.visibility = View.INVISIBLE
                    imagePlaceHolderNoNetwork.visibility = View.INVISIBLE
                    recycleView.visibility = View.INVISIBLE
                }

                "UpdateSearch" -> {
                    imagePlaceHolderNoResults.visibility = View.INVISIBLE
                    textPlaceHolderNoResults.visibility = View.INVISIBLE
                    buttonUpdate.visibility = View.INVISIBLE
                    textPlaceHolderNoNetwork.visibility = View.INVISIBLE
                    imagePlaceHolderNoNetwork.visibility = View.INVISIBLE
                    recycleView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }


    companion object {
        private const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
    }
}

