package com.example.basekmm_003.android

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.basekmm_003.MovieApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()
    private val sdk: MovieApi by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)

        mainScope.launch {
            kotlin.runCatching {
                val l = sdk.getLatestMovies().toString()
                tv.text = l
            }.onSuccess {
                Log.d("onSuccess", it.toString())
            }.onFailure {
                Log.d("onFailure", it.toString())
            }
        }
    }
}
