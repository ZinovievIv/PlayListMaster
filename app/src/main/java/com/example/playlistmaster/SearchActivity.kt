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


class SearchActivity : AppCompatActivity() {

    private var searchText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

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

    companion object {
        private const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
    }


}