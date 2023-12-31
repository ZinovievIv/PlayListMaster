package com.example.playlistmaster

import android.content.Intent
import android.content.SharedPreferences

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.playlistmaster.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private var trackList = ArrayList<Track>()
    private var searchText = ""
    private val itunesBaseUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val itunesService = retrofit.create(ItunesApi::class.java)
    private val adapter = TracksAdapter(trackList, this)
    private val adapterHistory = TracksHistoryAdapter(SearchHistory.historyTracksList, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferencesHistory = getSharedPreferences(HISTORY_TRACKS, MODE_PRIVATE)
        SearchHistory.readSharedPref(sharedPreferencesHistory)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        visibilityViews(VisibilityView.START_SEARCH_ACTIVITY)
        val view = binding.root
        setContentView(view)

        val recycle = binding.recycleView
        val recyclerHistory = binding.recycleViewHistory

        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = adapter

        recyclerHistory.layoutManager = LinearLayoutManager(this, VERTICAL, true)
        recyclerHistory.adapter = adapterHistory

        fun request() {
            searchText = binding.searchBar.text.toString()
            if (searchText.isNotEmpty()) {
                itunesService.findTrack(searchText)
                    .enqueue(object : Callback<TrackResponse> {
                        override fun onResponse(
                            call: Call<TrackResponse>,
                            response: Response<TrackResponse>
                        ) {
                            if (response.isSuccessful) {
                                adapter.clearListTracks(trackList)
                                val responseBody = response.body()?.results!!
                                    recycle.adapter?.let {
                                        if (it is TracksAdapter) {
                                            it.updateTracks(responseBody)
                                        }
                                    }
                                if (trackList.isEmpty()) {
                                    visibilityViews(VisibilityView.NO_RESULT)
                                }
                            }
                        }

                        override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                            visibilityViews(VisibilityView.NO_NETWORK)
                        }
                    })
            }
        }

        binding.buttonUpdate.setOnClickListener {
            visibilityViews(VisibilityView.UPDATE_SEARCH)
            request()
        }

        binding.searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                request()
                recyclerHistory.visibility = View.INVISIBLE
                binding.searchHistoryNotif.visibility = View.INVISIBLE
                binding.clearhistory.visibility = View.INVISIBLE
                binding.recycleView.visibility = View.VISIBLE
            }
            false
        }

        binding.clearButton.setOnClickListener {
            binding.searchBar.setText("")
            binding.searchBar.hideKeyboard()
            adapter.clearListTracks(trackList)
            visibilityViews(VisibilityView.FOR_CLEAR_SEARCHBAR_BUTTON)
        }

        binding.clearhistory.setOnClickListener{
            adapterHistory.clearListTracks()
            visibilityViews(VisibilityView.START_SEARCH_ACTIVITY)
        }

        binding.arrowBack.setOnClickListener {
            SearchHistory.writeSharedPref(sharedPreferencesHistory)
            finish()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    visibilityViews(VisibilityView.VISIBLE_FOR_ON_TEXT_CHANGED)
                } else {
                    binding.clearButton.visibility = View.VISIBLE
                }
                searchText = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        binding.searchBar.setOnFocusChangeListener{view, hasFocus ->
            binding.recycleViewHistory.visibility = if (hasFocus) View.INVISIBLE else View.VISIBLE
            binding.recycleView.visibility = if (hasFocus) View.VISIBLE else View.INVISIBLE
            binding.searchHistoryNotif.visibility = if (hasFocus) View.INVISIBLE else View.VISIBLE
            binding.clearhistory.visibility = if (hasFocus) View.INVISIBLE else View.VISIBLE
        }
        binding.searchBar.addTextChangedListener(textWatcher)
    }

    override fun onStop() {
        super.onStop()
        val sharedPreferences = getSharedPreferences(HISTORY_TRACKS, MODE_PRIVATE)
        SearchHistory.writeSharedPref(sharedPreferences)
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

    private fun visibilityViews(action: VisibilityView) {
        with(binding) {
            when (action) {
                VisibilityView.NO_NETWORK -> {
                    buttonUpdate.visibility = View.VISIBLE
                    textPlaceHolderNoNetwork.visibility = View.VISIBLE
                    imagePlaceHolderNoNetwork.visibility = View.VISIBLE
                    imagePlaceHolderNoResults.visibility = View.INVISIBLE
                    textPlaceHolderNoResults.visibility = View.INVISIBLE
                    recycleView.visibility = View.INVISIBLE
                }
                VisibilityView.NO_RESULT -> {
                    imagePlaceHolderNoResults.visibility = View.VISIBLE
                    textPlaceHolderNoResults.visibility = View.VISIBLE
                    buttonUpdate.visibility = View.INVISIBLE
                    textPlaceHolderNoNetwork.visibility = View.INVISIBLE
                    imagePlaceHolderNoNetwork.visibility = View.INVISIBLE
                    recycleView.visibility = View.INVISIBLE
                }
                VisibilityView.UPDATE_SEARCH -> {
                    imagePlaceHolderNoResults.visibility = View.INVISIBLE
                    textPlaceHolderNoResults.visibility = View.INVISIBLE
                    buttonUpdate.visibility = View.INVISIBLE
                    textPlaceHolderNoNetwork.visibility = View.INVISIBLE
                    imagePlaceHolderNoNetwork.visibility = View.INVISIBLE
                    recycleView.visibility = View.VISIBLE
                }
                VisibilityView.HISTORY_VIEW -> {
                    recycleViewHistory.visibility = View.VISIBLE
                }
                VisibilityView.START_SEARCH_ACTIVITY -> {
                    if(SearchHistory.historyTracksList.size == 0) {
                        searchHistoryNotif.visibility = View.INVISIBLE
                        clearhistory.visibility = View.INVISIBLE
                        recycleViewHistory.visibility = View.INVISIBLE
                    } else {
                        searchHistoryNotif.visibility = View.VISIBLE
                        clearhistory.visibility = View.VISIBLE
                        recycleViewHistory.visibility = View.VISIBLE
                    }
                }
                VisibilityView.VISIBLE_FOR_ON_TEXT_CHANGED -> {
                    clearButton.visibility = View.GONE
                    recycleViewHistory.visibility = if (binding.searchBar.hasFocus()) View.INVISIBLE
                    else View.VISIBLE
                    recycleView.visibility = if (binding.searchBar.hasFocus()) View.VISIBLE
                    else View.INVISIBLE
                    searchHistoryNotif.visibility = if (binding.searchHistoryNotif.hasFocus()) View.INVISIBLE
                    else View.VISIBLE
                    clearhistory.visibility = if (binding.clearhistory.hasFocus()) View.INVISIBLE
                    else View.VISIBLE
                }
                VisibilityView.FOR_CLEAR_SEARCHBAR_BUTTON -> {
                    recycleView.visibility = View.INVISIBLE
                    if(SearchHistory.historyTracksList.size == 0) {
                        adapterHistory.updateAdapter()
                        searchHistoryNotif.visibility = View.INVISIBLE
                        clearhistory.visibility = View.INVISIBLE
                        recycleViewHistory.visibility = View.INVISIBLE
                        imagePlaceHolderNoNetwork.visibility = View.INVISIBLE
                        textPlaceHolderNoNetwork.visibility = View.INVISIBLE
                        imagePlaceHolderNoResults.visibility = View.INVISIBLE
                        textPlaceHolderNoResults.visibility = View.INVISIBLE
                        buttonUpdate.visibility = View.INVISIBLE
                    } else {
                        adapterHistory.updateAdapter()
                        searchHistoryNotif.visibility = View.VISIBLE
                        clearhistory.visibility = View.VISIBLE
                        recycleViewHistory.visibility = View.VISIBLE
                        imagePlaceHolderNoNetwork.visibility = View.INVISIBLE
                        textPlaceHolderNoNetwork.visibility = View.INVISIBLE
                        imagePlaceHolderNoResults.visibility = View.INVISIBLE
                        textPlaceHolderNoResults.visibility = View.INVISIBLE
                        buttonUpdate.visibility = View.INVISIBLE
                    }
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

    enum class VisibilityView(action: String) {
        NO_NETWORK("noNetwork"),
        NO_RESULT("noResult"),
        UPDATE_SEARCH("updateSearch"),
        HISTORY_VIEW("historyView"),
        START_SEARCH_ACTIVITY("startSearchActivity"),
        VISIBLE_FOR_ON_TEXT_CHANGED("visibleForOnTextChanged"),
        FOR_CLEAR_SEARCHBAR_BUTTON("forClearSearchBarButton"),
    }
}

