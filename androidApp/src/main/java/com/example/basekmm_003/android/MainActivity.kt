package com.example.basekmm_003.android

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.basekmm_003.data.remote.WResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import name.PreviewMovieEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MovieDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val movies: Flow<WResponse<List<PreviewMovieEntity>>> = viewModel.getMovies()
                kotlin.runCatching {
                    movies.collect { tv.text = it.toString() }
                }.onSuccess {
                    Log.d("onSuccess", it.toString())
                }.onFailure {
                    Log.d("onFailure", it.toString())
                }
            }
        }
    }
}
