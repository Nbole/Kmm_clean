package com.example.basekmm_003.android

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.basekmm_003.data.remote.WResponse
import com.example.basekmm_003.domain.MovieUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import name.PreviewMovieEntity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val sdk: MovieUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        // TODO: Agregar Compose
        // TODO: Agregar el RepeatLifeCycle
        lifecycleScope.launch {
            val l: Flow<WResponse<List<PreviewMovieEntity>?>> = sdk.getLatestMovies()
            kotlin.runCatching {
                l.collect { response ->
                    tv.text = response.toString()
                }
            }.onSuccess {
                Log.d("onSuccess", it.toString())
            }.onFailure {
                Log.d("onFailure", it.toString())
            }
        }
    }
}
